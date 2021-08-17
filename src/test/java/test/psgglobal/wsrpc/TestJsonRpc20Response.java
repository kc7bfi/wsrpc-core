package test.psgglobal.wsrpc;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import net.psgglobal.wsrpc.jsonrpc2.JsonRpc20ParseException;
import net.psgglobal.wsrpc.jsonrpc2.JsonRpc20Response;

public class TestJsonRpc20Response {

	@Test
	public void testDefaultResponse() throws JsonRpc20ParseException {
		JsonRpc20Response response = JsonRpc20Response.parse("{\"jsonrpc\":\"2.0\",\"id\":898465,\"result\":true }");
		assertEquals(response.getResult().getClass().getName(), Boolean.class.getName());
		assertEquals(response.getResult(), true);
	}
}
