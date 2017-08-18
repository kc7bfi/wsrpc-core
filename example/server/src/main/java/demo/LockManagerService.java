package demo;

import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/VideoBroker/Recorder")
public class LockManagerService extends LockManagerReactor<LockManagerSession> {

}
