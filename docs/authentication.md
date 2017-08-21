# Authentication
Authentication is implemented by the client passing a JSON Web Token (JWT) to the server.
It is up to the application to determine the minimum data elements to send in the JWT.

The Client must override the Actor's member function `getJWTAuthenticationToken` returning the client's JWT authentication token.
An example is shown below:

```
@Override
public String getJWTAuthenticationToken() {
	Builder jwtBuilder = JWT.create().withIssuer("MyIssuer").withSubject(MyUsername);
	jwtBuilder.withArrayClaim("roles", myRolesList.toArray(new String[myRolesList.size()]));
	jwtBuilder.withExpiresAt(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()));
	return jwtBuilder.sign(Algorithm.HMAC256("MySecret"));
}
```

The server must override the Reactor's member function `valiateJWTToken` returning the decoded JWT token if the received token is valid
else throwing a `JWTVerificationException` indicating an invalid token and/or improper authentication/authorization. An example is shown below:

```
@Override
public JWT valiateJWTToken(String jwtToken) throws JWTVerificationException {
	try {
		JWTVerifier verifier = JWT.require(Algorithm.HMAC256("MySecret")).withIssuer("MyIssuer").build();
		JWT jwtAuthentication = (JWT) verifier.verify(jwtToken);
		if (jwtAuthentication.getExpiresAt() == null) throw new JWTVerificationException("Must include expiry date");
		return jwtAuthentication;
	} catch (Exception e) {
		throw new JWTVerificationException("Could not verify JWT token: " + e);
	}
}
```
