package demo;

import java.io.IOException;

import org.glassfish.tyrus.client.SslContextConfigurator;
import org.glassfish.tyrus.client.SslEngineConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import demo.wsrpc.AskForLockRequest;
import demo.wsrpc.LockManagerActor;
import demo.wsrpc.LockStolenNotice;
import demo.wsrpc.WaitingForLockNotice;

public class LockManagerClient  extends LockManagerActor {
	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	private static final int DEFAULT_CONNECT_TIMEOUT = 2000;

	/**
	 * Constructor
	 * @param serverUrl server URL
	 */
	public LockManagerClient(String serverUrl) {
		super(serverUrl);

		// configure for SSL
		SslContextConfigurator sslContextConfigurator = new SslContextConfigurator();
		sslContextConfigurator.setTrustStoreFile("trustfile");
		sslContextConfigurator.setTrustStorePassword("Secret");
		sslEngineConfigurator = new SslEngineConfigurator(sslContextConfigurator, true, false, false);
		sslEngineConfigurator.setHostVerificationEnabled(false); //skip host verification

		connect(DEFAULT_CONNECT_TIMEOUT);
	}

	@Override
	public String getJWTAuthenticationToken() {
		return null; // not used
	}

	@Override
	public void onAskForLock(AskForLockRequest request) {
		this.sendSilentResponse(true, request.getRequestId());
	}

	@Override
	public void onLockStolen(LockStolenNotice notice) {
		logger.info("Lock {} stolen by {}", notice.getLockId(), notice.getHolder());
	}

	@Override
	public void onWaitingForLock(WaitingForLockNotice notice) {
		logger.info("Waiting on lock {} held by {}", notice.getLockId(), notice.getHolder());
	}

	public static void main(String[] args) {
		LockManagerClient client = new LockManagerClient("wss://1.1.1.1:8443/MyService/LockManager");
		try {
			boolean ok = client.requestLock(1, false);
			System.out.println("Lock was granted? " + ok);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
