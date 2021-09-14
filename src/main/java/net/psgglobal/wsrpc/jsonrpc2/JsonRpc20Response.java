package net.psgglobal.wsrpc.jsonrpc2;

import com.google.gson.Gson;

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
 * The base JSON-RPC 2.0 requesst
 */
public class JsonRpc20Response {
	private String jsonrpc = "2.0"; // the JSON-RPC version
	private Long id; // the JSON-RPC call Id
	private Object result; // the method results
	private JsonRpc20Error error; // execution error

	/**
	 * Constructor
	 */
	public JsonRpc20Response() {
	}

	/**
	 * Constructor
	 * @param id the request id
	 */
	public JsonRpc20Response(long id) {
		this.id = id;
		result = true;
	}

	/**
	 * Constructor
	 * @param id the request id
	 * @param result the result
	 */
	public JsonRpc20Response(long id, Object result) {
		this.id = id;
		this.result = result;
	}

	/**
	 * Constructor
	 * @param id the request id
	 * @param error the error
	 */
	public JsonRpc20Response(long id, JsonRpc20Error error) {
		this.id = id;
		this.error = error;
	}

	/**
	 * @return the JSON string
	 */
	public String toJSONString() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}

	/**
	 * @param json the JSON string
	 * @return the request object
	 * @throws JsonRpc20ParseException error parsing response
	 */
	public static JsonRpc20Response parse(String json) throws JsonRpc20ParseException {
		Gson gson = new Gson();
		JsonRpc20Response response = gson.fromJson(json, JsonRpc20Response.class);
		if (!"2.0".equals(response.getJsonrpc())) throw new JsonRpc20ParseException("Invalid JSON-RPC version");
		if (response.getId() == null) throw new JsonRpc20ParseException("Request Id must be specified");
		if (response.result == null && response.error == null) throw new JsonRpc20ParseException("A result or error must be specified");
		return response;
	}

	/**
	 * @return true if an error was not returned
	 */
	public boolean wasSuccessful() {
		return error == null;
	}

	/**
	 * @return the jsonrpc
	 */
	public String getJsonrpc() {
		return jsonrpc;
	}

	/**
	 * @param jsonrpc the jsonrpc to set
	 */
	public void setJsonrpc(String jsonrpc) {
		this.jsonrpc = jsonrpc;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the result
	 */
	public Object getResult() {
		return result;
	}

	/**
	 * @return the result
	 */
	public long getLongResult() {
		if (result instanceof Double) return ((Double)result).longValue();
		if (result instanceof Integer) return ((Integer)result).longValue();
		if (result instanceof Long) return ((Long)result).longValue();
		if (result instanceof String) return Long.parseLong((String)result);
		return (long)result;
	}

	/**
	 * @return the result
	 */
	public int getIntegerResult() {
		if (result instanceof Double) return ((Double)result).intValue();
		if (result instanceof Integer) return ((Integer)result).intValue();
		if (result instanceof Long) return ((Long)result).intValue();
		if (result instanceof String) return Integer.parseInt((String)result);
		return (int)result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(Object result) {
		this.result = result;
	}

	/**
	 * @return the error
	 */
	public JsonRpc20Error getError() {
		return error;
	}

	/**
	 * @param error the error to set
	 */
	public void setError(JsonRpc20Error error) {
		this.error = error;
	}
}
