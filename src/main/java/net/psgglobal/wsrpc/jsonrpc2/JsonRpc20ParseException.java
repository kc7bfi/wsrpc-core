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
 * Thrown if JSON cannot be parsed
 */
public class JsonRpc20ParseException extends Exception {
	private static final long serialVersionUID = 1L;

	public JsonRpc20ParseException() {
		super("Parse Exception");
	}

	public JsonRpc20ParseException(String message) {
		super(message);
	}
}
