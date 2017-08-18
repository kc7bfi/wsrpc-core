# Server code
The server code consists of two file:

1. The server endpoint
2. The server session

## Server Endpoint
The server endpoint must include the following elements:

1. It must extend the reactor class. 
This class takes the name of the interface appended with "Reactor".
The reactor is a Java generic which takes as its argument the server session.
2. It must implement the `createAgent` method. 
This method is responsible for creating the server session.
3. It must implement the `valiateJWTToken` method.
This method is responsible for authenticating the JWT authentication token.

An example of the server endpoint is shown below:

```
@ServerEndpoint(value = "/VideoCommand")
public class VideoCommandService extends VideoCommandReactor<VideoCommandSession> {

	private VideoCommandSession recorderSession;

	/**
	 * Constructor
	 */
	public VideoCommandService() {
	}

	@Override
	public VideoCommandSession createAgent(Session wsSession) {
		recorderSession = new VideoCommandSession(wsSession);
		return recorderSession;
	}

	@Override
	public JWT valiateJWTToken(String jwtToken) throws JWTVerificationException {
		try {
			JWTVerifier verifier = JWT.require(Algorithm.HMAC256("MySecret")).withIssuer("MyIssuer").build();
			JWT jwtAuthentication = (JWT) verifier.verify(jwtToken);
			if (jwtAuthentication.getExpiresAt() == null) throw new JWTVerificationException("Must include expiry date");
			return jwtAuthentication;
		} catch (Exception e) {
			throw new JWTVerificationException("Could not verify JWT token: " + e.getMessage());
		}
	}
}
```

## Server Session
The server session must include the following elements:

1. It must extend the agent class. 
This class takes the name of the interface appended with "Agent".
The reactor is a Java generic which takes as its argument the server session.
2. It must implement the `onAgentAdded` and `onAgentRemoved` methods. 
These methods are called when a server session is created and terminated.
3. It must override all the abstract RPC calls generated from the interface.

An example of the server session is shown below:

```
public class VideoCommandSession extends VideoCommandAgent {

	/**
	 * Constructor.
	 * @param wsSession the WebSocket session
	 */
	public VideoCommandSession(Session wsSession) {
		this.wsSession = wsSession;
	}

	@Override
	public void onAgentAdded(VideoCommandAgent managedAgent) {
	}

	@Override
	public void onAgentRemoved(VideoCommandAgent managedAgent) {
	}

	@Override
	public void continuousPanTilt(long videoId, int streamId, int panSpeed, int tiltSpeed) throws WsRpcException {
		try {
			sendVideoCommand();
		} catch (Exception e) {
			throw new WsRpcInternalErrorException("continuousPanTilt Error: " + e.getMessage());
		}
	}
}
```

