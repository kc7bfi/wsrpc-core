package demo;

import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTVerificationException;

import demo.wsrpc.LockManagerReactor;

@ServerEndpoint(value = "/VideoBroker/Recorder")
public class LockManagerService extends LockManagerReactor<LockManagerSession> {

	@Override
	public LockManagerSession createAgent(Session wsSession) {
		return new LockManagerSession();
	}

	@Override
	public JWT valiateJWTToken(String jwtToken) throws JWTVerificationException {
		return null; // JWT autehntication not used
	}

}
