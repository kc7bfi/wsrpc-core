package demo;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import demo.wsrpc.LockManagerAgent;
import demo.wsrpc.RequestLockRequest;
import net.psgglobal.wsrpc.core.WsRpcException;

public class LockManagerSession extends LockManagerAgent {

	private static Map<Long, LockManagerSession> TheLocks = Collections.synchronizedMap(new HashMap<>());

	private long myLockId;

	@Override
	public void onAgentAdded(LockManagerAgent managedAgent) {
	}

	@Override
	public void onAgentRemoved(LockManagerAgent managedAgent) {
	}

	@Override
	public boolean haveLock() throws WsRpcException {
		return TheLocks.containsKey(myLockId);
	}

	@Override
	public void freeLock() throws WsRpcException {
		TheLocks.remove(myLockId);
		myLockId = 0;
	}

	@Override
	public void onRequestLock(RequestLockRequest request) {
		// see if we can get the lock right away
		LockManagerSession holder = TheLocks.get(request.getLockId());
		if (holder == null) {
			TheLocks.put(request.getLockId(), this);
			this.sendSilentResponse(true, request.getRequestId());
		}
		if (!request.isWaitForLock()) this.sendSilentResponse(false, request.getRequestId());

		// tell them we are waiting
		try {
			this.sendWaitingForLock(request.getRequestId(), holder.getWsSessionId());
		} catch (IOException e) {
			e.printStackTrace();
		}

		// ask the other session for the locks
		try {
			boolean granted = holder.syncAskForLock(request.getLockId(), this.getWsSessionId(), 30000);
			this.sendSilentResponse(granted, request.getRequestId());
		} catch (IOException e) {
			this.sendSilentInternalError("Error asking for lock " + e, request.getRequestId());
		}
	}

	@Override
	public boolean isLocked(long lockId) throws WsRpcException {
		return TheLocks.containsKey(lockId);
	}

}
