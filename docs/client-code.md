# Client code
The client code must include the following elements:

1. It must extend the actor class. 
This class takes the name of the interface appended with "Actor".
2. If the interface supports uses SSL (the `wss` protocol), then the client must configure the SSL Context.
3. The client must initiate the connection to the server.
The Agent supports connection management that will attempt to reconnect to the server should the client become disconnected.
4. The client must override the `getJWTAuthenticationToken` method. 
This method is called when the agent needs a JWT authentication token to send to the server.

An example of the client code is shown below:

```
@ClientEndpoint
public class VideoCommandClient extends VideoCommandActor {
	private static final int DEFAULT_CONNECT_TIMEOUT = 2000;

	/**
	 * Constructor
	 * @param serverUrl server URL
	 */
	public VideoCommandClient(String serverUrl) {
		super(serverUrl);

		// configure for SSL
		SslContextConfigurator sslContextConfigurator = new SslContextConfigurator();
		sslContextConfigurator.setTrustStoreFile("trustfile");
		sslContextConfigurator.setTrustStorePassword("MySecret");
		sslEngineConfigurator = new SslEngineConfigurator(sslContextConfigurator, true, false, false);
		sslEngineConfigurator.setHostVerificationEnabled(false); //skip host verification

		connect(DEFAULT_CONNECT_TIMEOUT);
	}

	@Override
	public String getJWTAuthenticationToken() {
		return AppSecurityContext.getInstance().getJWTAuthenticationToken();
	}
}
```

