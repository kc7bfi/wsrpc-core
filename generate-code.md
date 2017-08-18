# Generate code
To generate the wsrpc class files from your wsrpc specification, you will need to add:
1. The wsrpc-maven-plugin to your maven project
2. Add the needed dependencies to your mavem project

## Adding the wsrpc-maven-plugin
The wsrpc-magen-plugin is added to your pom.xml file.
The `inputDir` property specifies the directory where the specification files live.
All files in this directory ending with `.json` will be processed. 
An example is shown below.

```
<plugin>
	<groupId>net.psgglobal.wsrpc</groupId>
	<artifactId>wsrpc-maven-plugin</artifactId>
	<version>1.0-SNAPSHOT</version>
	<configuration>
		<inputDir>${project.sourceDirectory}/main/resources/wsrpc</inputDir>
	</configuration>
	<executions>
		<execution>
			<phase>generate-sources</phase>
			<goals>
				<goal>generate</goal>
			</goals>
		</execution>
	</executions>
</plugin>
```

## Needed dependencies
You will need the following dependencies to compile your generated files:

```
<dependency>
	<groupId>net.psgglobal.wsrpc</groupId>
	<artifactId>wsrpc-core</artifactId>
	<version>1.0-SNAPSHOT</version>
</dependency>
```