# WebSocket RPC (wsrpc)
We have been a long time user of CORBA and are now at the point of wanting to switch it out for some other, newer, technology.
We use REST for many of our interfaces, but it was not a good solution for all our needs.
What we needed was a RPC system that included the following features:

1. A channel oriented, stateful protocol.
2. Bi-directional communication over a single channel.
3. Easily implemented authentication.
4. Ability for the server to send unsolicited information to the client.
5. A code generator to simplify the implementation.

Because I did not find anything that met our companies needs. I developed our own system which we call **WebSocket RPC** or **wsrpc** for short.
In addition to the features above, it also includes:

1. The ability to send text and/or binary data over the WebSocket.
2. Uses JSON Web Token (JTW) technology for authentication.
3. Can be used in simple RPC mode or in extended mode where the server can respond early to the client while it continues to proess the request.
4. Uses RxJava for asynchronous communications.

# Getting Started

1. Write an interface [specification file](docs/specification-file.md). 
2. Setup you maven project and [generate the code](docs/generate-code.md).
3. Write the [server side code](docs/server-code.md).
4. Write the [client side code](docs/client-code.md).
5. Make wsrpc calls from [your code](docs/calling-wsrpc.md).

