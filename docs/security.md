# Security
Security is implemented using SSL (wss: protocol).
When writing the client, you must configure the `SslEngineConfigurator` in the client's constructor before connecting to the server.
The `sslEngineConfigurator` member is defined in the Agent class.

To configure the `SslEngineConfigurator`, create and define the `SslContextConfigurator` and use it to create the `SslEngineConfigurator`. An example is shown below.

```
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
```