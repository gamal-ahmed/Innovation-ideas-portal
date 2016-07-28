/**
 * 
 */
package eg.com.etisalat.contest.utility;

/**
 * @author O-Mohamed.Salman
 * 
 */
public class URLUtility {

	public static String URLDecoding(String value) {
		String filtered = "";
		String c;
		if (value.indexOf("%21") != -1) {
			value = value.replaceAll("%21", "!");
		}
		if (value.indexOf("%23") != -1) {
			value = value.replaceAll("%23", "#");
		}
		if (value.indexOf("%24") != -1) {
			value = value.replaceAll("%24", "\\$");
		}
		if (value.indexOf("%25") != -1) {
			value = value.replaceAll("%25", "%");
		}
		if (value.indexOf("%26") != -1) {
			value = value.replaceAll("%26", "&");
		}
		if (value.indexOf("%5C") != -1) {
			value = value.replaceAll("%5C", "\\\\");
		}
		if (value.indexOf("%28") != -1) {
			value = value.replaceAll("%28", "(");
		}
		if (value.indexOf("%29") != -1) {
			value = value.replaceAll("%29", ")");
		}
		if (value.indexOf("%2B") != -1) {
			value = value.replaceAll("%2B", "+");
		}
		if (value.indexOf("%2C") != -1) {
			value = value.replaceAll("%2C", ",");
		}
		if (value.indexOf("%3A") != -1) {
			value = value.replaceAll("%3A", ":");
		}
		if (value.indexOf("%3B") != -1) {
			value = value.replaceAll("%3B", ";");
		}
		if (value.indexOf("%3E") != -1) {
			value = value.replaceAll("%3E", ">");
		}
		if (value.indexOf("%3D") != -1) {
			value = value.replaceAll("%3D", "=");
		}
		if (value.indexOf("%3C") != -1) {
			value = value.replaceAll("%3C", "<");
		}
		if (value.indexOf("%3F") != -1) {
			value = value.replaceAll("%3F", "?");
		}
		if (value.indexOf("%5B") != -1) {
			value = value.replaceAll("%5B", "[");
		}
		if (value.indexOf("%5D") != -1) {
			value = value.replaceAll("%5D", "]");
		}
		if (value.indexOf("%5E") != -1) {
			value = value.replaceAll("%5E", "^");
		}
		if (value.indexOf("%60") != -1) {
			value = value.replaceAll("%60", "`");
		}
		if (value.indexOf("%7B") != -1) {
			value = value.replaceAll("%7B", "{");
		}
		if (value.indexOf("%7C") != -1) {
			value = value.replaceAll("%7C", "|");
		}
		if (value.indexOf("%7D") != -1) {
			value = value.replaceAll("%7D", "}");
		}
		if (value.indexOf("%20") != -1) {
			value = value.replaceAll("%20", " ");
		}
		if (value.indexOf("%40") != -1) {
			value = value.replaceAll("%40", "@");
		}
		if (value.indexOf("%7E") != -1) {
			value = value.replaceAll("%7E", "~");
		}
		return value;
	}

	public static String URLEncoding(String input) {
		if (input != null) {
			StringBuffer filtered = new StringBuffer(input.length());
			char c;
			for (int i = 0; i < input.length(); i++) {
				c = input.charAt(i);
				if (c == '\'') {
					filtered.append("%27");
				} else if (c == '%') {
					filtered.append("%25");
				} else if (c == '=') {
					filtered.append("%3D");
				} else if (c == '\\') {
					filtered.append("%5c");
				} else if (c == '<') {
					filtered.append("%3c");
				} else if (c == '>') {
					filtered.append("%3e");
				} else if (c == '#') {
					filtered.append("%23");
				} else if (c == '+') {
					filtered.append("%2B");
				} else if (c == '&') {
					filtered.append("%26");
				}
				// Hesham 4/4/2007 start
				else if (c == '!') {
					filtered.append("%21");
				} else if (c == '"') {
					filtered.append("%22");
				} else if (c == '$') {
					filtered.append("%24");
				} else if (c == '(') {
					filtered.append("%28");
				} else if (c == ')') {
					filtered.append("%29");
				} else if (c == ',') {
					filtered.append("%2C");
				} else if (c == ':') {
					filtered.append("%3A");
				} else if (c == ';') {
					filtered.append("%3B");
				} else if (c == '?') {
					filtered.append("%3F");
				} else if (c == '[') {
					filtered.append("%5B");
				} else if (c == ']') {
					filtered.append("%5D");
				} else if (c == '^') {
					filtered.append("%5E");
				} else if (c == '{') {
					filtered.append("%7B");
				} else if (c == '|') {
					filtered.append("%7C");
				} else if (c == '}') {
					filtered.append("%7D");
				} else if (c == '~') {
					filtered.append("%7E");
				}
				// Hesham 4/4/2007 end
				else {
					filtered.append(c);
				}
			}
			return filtered.toString();
		} else {
			return null;
		}
	}

}
