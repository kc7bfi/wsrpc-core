package net.psgglobal.wsrpc.jsonrpc2;

/*
This file is part of wsrpc.

wsrpc is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

wsrpc is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with wsrpc.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * @author david.robison
 * The base JSON-RPC 2.0 error
 */
public class JsonRpc20Error {
	private int code; // the error code
	private String message; // the error message
	private Object data; // the the error data

	/**
	 * Constructor
	 */
	public JsonRpc20Error() {
	}

	/**
	 * Constructor
	 * @param code the error code
	 * @param message the error message
	 */
	public JsonRpc20Error(int code, String message) {
		this(code, message, null);
	}

	/**
	 * Constructor
	 * @param code the error code
	 * @param message the error message
	 * @param data the error data
	 */
	public JsonRpc20Error(int code, String message, Object data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}

	/**
	 * @return a new internal error object
	 */
	public static JsonRpc20Error parseError() {
		return internalError("Parse Error");
	}

	/**
	 * @param message the error message
	 * @return a new internal error object
	 */
	public static JsonRpc20Error parseError(String message) {
		return new JsonRpc20Error(-32700, message);
	}

	/**
	 * @return a new internal error object
	 */
	public static JsonRpc20Error invalidRequest() {
		return invalidRequest("Invalid Request");
	}

	/**
	 * @param message the error message
	 * @return a new internal error object
	 */
	public static JsonRpc20Error invalidRequest(String message) {
		return new JsonRpc20Error(-32600, message);
	}

	/**
	 * @return a new internal error object
	 */
	public static JsonRpc20Error methodNotFound() {
		return methodNotFound("Method Not Found");
	}

	/**
	 * @param message the error message
	 * @return a new internal error object
	 */
	public static JsonRpc20Error methodNotFound(String message) {
		return new JsonRpc20Error(-32601, message);
	}

	/**
	 * @return a new internal error object
	 */
	public static JsonRpc20Error invalidParams() {
		return invalidParams("Invalid Params");
	}

	/**
	 * @param message the error message
	 * @return a new internal error object
	 */
	public static JsonRpc20Error invalidParams(String message) {
		return new JsonRpc20Error(-32602, message);
	}

	/**
	 * @return a new internal error object
	 */
	public static JsonRpc20Error internalError() {
		return internalError("Internal Error");
	}

	/**
	 * @param message the error message
	 * @return a new internal error object
	 */
	public static JsonRpc20Error internalError(String message) {
		return new JsonRpc20Error(-32603, message);
	}

	/**
	 * @param message the error message
	 * @return a new internal error object
	 */
	public static JsonRpc20Error serverError(int code, String message) {
		return new JsonRpc20Error(code, message);
	}

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the data
	 */
	public Object getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(Object data) {
		this.data = data;
	}
}
