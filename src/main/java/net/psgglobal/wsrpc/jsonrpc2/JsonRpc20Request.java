package net.psgglobal.wsrpc.jsonrpc2;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

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
 * The base JSON-RPC 2.0 request
 */
public class JsonRpc20Request {
	private String jsonrpc = "2.0"; // the JSON-RPC version
	private String method; // the method
	private Long id; // the JSON-RPC call Id
	private Object params; // the method parameters

	/**
	 * Constructor
	 */
	public JsonRpc20Request() {
	}

	/**
	 * Constructor
	 * @param method the request method
	 * @param id the request id
	 */
	public JsonRpc20Request(String method, Long id) {
		this(method, id, null);
	}

	/**
	 * Constructor
	 * @param method the request method
	 * @param id the request id
	 * @param params the request params
	 */
	public JsonRpc20Request(String method, Long id, Object params) {
		this.method = method;
		this.id = id;
		this.params = params;
	}

	/**
	 * @param paramName the parameter name
	 * @return the value
	 */
	@SuppressWarnings("unchecked")
	public Object getMapParam(String paramName) {
		return ((LinkedTreeMap<String, Object>)params).get(paramName);
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
	 * @throws JsonRpc20ParseException Error parsing request
	 */
	public static JsonRpc20Request parse(String json) throws JsonRpc20ParseException {
		Gson gson = new Gson();
		JsonRpc20Request request = gson.fromJson(json, JsonRpc20Request.class);
		if (!"2.0".equals(request.getJsonrpc())) throw new JsonRpc20ParseException("Invalid JSON-RPC version");
		if (request.getMethod() == null) throw new JsonRpc20ParseException("Request method must be specified");
		if (request.getId() == null) throw new JsonRpc20ParseException("Request Id must be specified");
		return request;
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
	 * @return the method
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * @param method the method to set
	 */
	public void setMethod(String method) {
		this.method = method;
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
	 * @return the params
	 */
	public Object getParams() {
		return params;
	}

	/**
	 * @param params the params to set
	 */
	public void setParams(Object params) {
		this.params = params;
	}
}
