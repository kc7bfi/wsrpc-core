# Calling wsrpc
To make wsrpc calls, the client must instantiate the interface client and them simply call one of the request method.
An example is show below:

```
VideoCommandClient client = new VideoCommandClient("wss:1.1.1.1:8443/MyService/VideoCommand");
try {
	client.continuousPanTilt(4, 0, 50, 0);
} catch (WsRpcException e) {
	e.printStackTrace();
}
```

