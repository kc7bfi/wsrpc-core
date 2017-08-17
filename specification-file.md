# Specification files
A specification file is a standard JSON file that describes the interface.
A specification file contains a number of sections which, all but the header section, are optional.
The sections are:

1. The header definition
2. Constants definition
3. Class definitions
4. List definitions
5. Request definitions
6. Notice definitions

### Header definition
The header section contains the following properties:

| property | Description | Usage |
| -------- | ----------- | ----- |
| name | This is the name of the interface | Mandatory |
| package | This is the Java package name for the generated Java classes | Mandatory |
| supportBinaryData | If true, then binary websocket IO will be used | Defaults to false |
| constants | the constants definition | Optional |

Example:

```
{
	"name": "VideoControl",
	"package": "net.psgglobal.wsrpc.videocontrol",
	"constants":{
		...
	},
	"classes": [
		...
	]
}
```

### Constants defintion
The presence of a `constants` property will cause a Java class to be generated.
A single class will be generated for all constants.
The constants section contains the following properties:

| property | Description | Usage |
| -------- | ----------- | ----- |
| name | This will be come the Java class name with the string "Const" appended to it | Mandatory |
| javadoc | This javadocs will be added to the main class generated for this constants definitions | Mandatory |
| members | An array of constants to be defined | Must include at least one constant member |

The constants `members` array contains a list of member objects, each object having the following properties:

| property | Description | Usage |
| -------- | ----------- | ----- |
| name | This will be the name of the constant | Mandatory |
| type | This is the type of the constant | Mandatory |
| value | This is the value of the constant | Mandatory |
| javadoc | This is the definition of the constant | Optional |

Example:

```
"constants": {
	"name": "VideoConsts",
	"javadoc": "Standard video constants",
	"members":[
		{ "name": "OpenCommand", "type": "String", "value": "OP1", "javadoc": "Command to open the iris" }
	]
}
```

### Classes defintion
For each member in the `classes` array, a Java class file will be generated with the name of the class as the class name.
Each entry in the classes array contains the following properties:

| property | Description | Usage |
| -------- | ----------- | ----- |
| name | This will be come the Java class name with the string "Const" appended to it | Mandatory |
| javadoc | This javadocs will be added to the main class generated for this class definitions | Mandatory |
| members | An array of class members for this class | Must include at least one class member |

The class `members` array contains a list of member objects, each object having the following properties:

| property | Description | Usage |
| -------- | ----------- | ----- |
| name | This will be the name of the class member | Mandatory |
| type | This is the type of the class member | Mandatory |
| javadoc | This is the definition of the constant | Optional |

Example:

```
"classes": {
	"name": "PTZPossition",
	"javadoc": "PTZ Position",
	"members":[
		{ "name": "panPosition", "type": "int", "javadoc": "the pan position in degrees" },
		{ "name": "tiltPosition", "type": "int", "javadoc": "the tilt position in degrees" },
		{ "name": "zoomPosition", "type": "int", "javadoc": "the zoom position in percent of full" }
	]
}
```

