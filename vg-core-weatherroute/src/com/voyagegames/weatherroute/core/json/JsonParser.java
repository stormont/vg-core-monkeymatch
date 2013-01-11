package com.voyagegames.weatherroute.core.json;

import java.util.ArrayList;
import java.util.List;

import com.voyagegames.weatherroute.core.ILogger;

public class JsonParser {
	
	private enum JsonSyntax {
		START,
		OPENING_BRACE,
		CLOSING_BRACE,
		OPENING_BRACKET,
		CLOSING_BRACKET,
		COLON,
		COMMA,
		KEY,
		VALUE,
		END
	}
	
	public static final String ARRAY_RESULT = "array_result";
	
	private static final String TAG = JsonParser.class.getName();
	
	private final ILogger mLogger;
	
	private JsonSyntax mLastSyntax;
	private String mCurrentKey;
	
	public JsonParser(final ILogger logger) {
		this.mLogger = logger;
		this.mLastSyntax = JsonSyntax.START;
	}
	
	public JsonObject parse(final String jsonStr) {
		if (jsonStr == null) {
			throw new IllegalArgumentException("JSON string is null");
		}
		
		final int length = jsonStr.length();
		final JsonString str = new JsonString(jsonStr, 0, length);
		final JsonObject obj = new JsonObject();
		
		str.skipWhitespace();

		if (parse(obj, str)) {
			return obj;
		}
		
		return null;
	}
	
	public JsonObject parse(final JsonString json) {
		final JsonObject jsonObj = new JsonObject();
		json.skipWhitespace();

		if (parse(jsonObj, json)) {
			return jsonObj;
		}
		
		return null;
	}
	
	private boolean parse(final JsonObject obj, final JsonString json) {
		if (mLastSyntax == JsonSyntax.END) {
			return true;
		}
		
		if (json.end()) {
			mLogger.log(TAG, "Malformed JSON; end found before closing brace");
			return false;
		}
		
		final char curChar = json.currentChar();
		
		switch (mLastSyntax) {
		case START:
			if (curChar == '{') {
				mLastSyntax = JsonSyntax.OPENING_BRACE;
			} else if (curChar == '[') {
				mLastSyntax = JsonSyntax.OPENING_BRACKET;
			} else {
				mLogger.log(TAG, "Opening brace or bracket not found");
				return false;
			}
			
			break;
		case OPENING_BRACE:
			if (parseKey(obj, json)) {
				mLastSyntax = JsonSyntax.KEY;
				break;
			} else if (curChar == '}') {
				mLastSyntax = JsonSyntax.END;
				break;
			} else {
				mLogger.log(TAG, "Key or closing brace not found");
				return false;
			}
		case OPENING_BRACKET:
			final List<JsonValue> values = new ArrayList<JsonValue>();

			if (!parseArray(json, values)) {
				mLogger.log(TAG, "Top-level JSON array was malformed");
				return false;
			}
			
			final JsonValue result = new JsonValue(ARRAY_RESULT);
			
			result.setArray(values);
			obj.values.put(result.key, result);
			mLastSyntax = JsonSyntax.END;
			break;
		case COMMA:
			if (parseKey(obj, json)) {
				mLastSyntax = JsonSyntax.KEY;
				break;
			} else if (curChar == '}') {
				mLastSyntax = JsonSyntax.END;
				break;
			} else {
				mLogger.log(TAG, "Key or closing brace not found after comma");
				return false;
			}
		case KEY:
			if (curChar == ':') {
				mLastSyntax = JsonSyntax.COLON;
				prepareJson(json);
				
				if (json.end()) {
					mLogger.log(TAG, "JSON unexpectedly ended during key parsing");
					return false;
				}
				
				final JsonValue value = new JsonValue(mCurrentKey);
				
				if (!parseValue(json, value)) {
					return false;
				}
				
				if (obj.values.containsKey(value.key)) {
					mLogger.log(TAG, "JSON keys already contain found key: " + value.key);
					return false;
				}

				obj.values.put(value.key, value);
				mLastSyntax = JsonSyntax.VALUE;
				break;
			} else {
				mLogger.log(TAG, "Colon not found");
				return false;
			}
		case VALUE:
			if (curChar == ',') {
				mLastSyntax = JsonSyntax.COMMA;
				break;
			} else if (curChar == '}') {
				mLastSyntax = JsonSyntax.END;
				break;
			} else {
				mLogger.log(TAG, "Comma or closing brace not found");
				return false;
			}
		default:
			mLogger.log(TAG, "Unexpected parsing error");
			return false;
		}
		
		prepareJson(json);
		return parse(obj, json);
	}
	
	private void prepareJson(final JsonString json) {
		json.incrementNdx();
		json.skipWhitespace();
	}
	
	private boolean parseKey(final JsonObject obj, final JsonString json) {
		if (json.end()) {
			mLogger.log(TAG, "Malformed JSON; expected key, but found end");
			return false;
		}
		
		char curChar = json.currentChar();
		
		if (curChar != '"') {
			mLogger.log(TAG, "Opening quote not found");
			return false;
		}

		final StringBuilder builder = new StringBuilder();
		
		boolean escapeFound = false;
		
		json.incrementNdx();
		
		if (json.end()) {
			mLogger.log(TAG, "Malformed JSON; found end before key completed");
			return false;
		}
		
		curChar = json.currentChar();
		
		while (!(json.end() || (curChar == '"' && escapeFound == false))) {
			builder.append(curChar);
			
			if (curChar == '\\') {
				escapeFound = true;
				json.incrementNdx();
				
				if (json.end()) {
					mLogger.log(TAG, "Malformed JSON; found end before key completed");
					return false;
				}
				
				curChar = json.currentChar();
				continue;
			}
			
			escapeFound = false;
			json.incrementNdx();
			
			if (json.end()) {
				mLogger.log(TAG, "Malformed JSON; found end before key completed");
				return false;
			}
			
			curChar = json.currentChar();
		}
		
		if (json.end()) {
			mLogger.log(TAG, "Malformed JSON; found end before key ended");
			return false;
		}
		
		mCurrentKey = builder.toString();
		mLastSyntax = JsonSyntax.KEY;
		return true;
	}
	
	private boolean parseValue(final JsonString json, final JsonValue value) {
		if (json.end()) {
			mLogger.log(TAG, "Malformed JSON; found end before value ended");
			return false;
		}
		
		final char curChar = json.currentChar();
		final String firstFour = json.substring(4);
		
		if (curChar == '{') {
			final JsonObject subObj = new JsonObject();
			final JsonParser parser = new JsonParser(mLogger);
			
			if (!parser.parse(subObj, json)) {
				mLogger.log(TAG, "Internal JSON object was malformed");
				return false;
			}
			
			value.setObject(subObj);
			json.decrementNdx();
			mLastSyntax = JsonSyntax.VALUE;
			return true;
		} else if (curChar == '[') {
			final List<JsonValue> values = new ArrayList<JsonValue>();

			prepareJson(json);
			
			if (!parseArray(json, values)) {
				mLogger.log(TAG, "Internal JSON array was malformed");
				return false;
			}
			
			value.setArray(values);
			return true;
		} else if (curChar == '"') {
			final StringBuilder builder = new StringBuilder();
			
			json.incrementNdx();
			
			if (json.end()) {
				mLogger.log(TAG, "Malformed JSON; found end before String value completed");
				return false;
			}
			
			char c = json.currentChar();
			boolean lastWasBackslash = false;
			
			while (!json.end() && !(c == '"' && !lastWasBackslash)) {
				if (lastWasBackslash) {
					builder.append("\\" + c);
					lastWasBackslash = false;
				} else {
					if (c == '\\') {
						lastWasBackslash = true;
					} else {
						lastWasBackslash = false;
						builder.append(c);
					}
				}
				
				json.incrementNdx();
				
				if (json.end()) {
					mLogger.log(TAG, "Malformed JSON; found end before String value completed");
					return false;
				}
				
				c = json.currentChar();
			}
			
			final String v = builder.toString();
			
			value.setString(v);
			mLastSyntax = JsonSyntax.VALUE;
			return true;
		} else if ((curChar >= '0' && curChar <= '9') || curChar == '-') {
			final StringBuilder builder = new StringBuilder();
			final int ndx = json.ndx();
			char c = curChar;
			boolean decimalFound = false;
			
			while (!json.end() && ((c >= '0' && c <= '9') || (c == '.' && !decimalFound) || (c == '-'))) {
				if (c == '.') {
					decimalFound = true;
				} else if (c == '-') {
					if (ndx != json.ndx()) {
						mLogger.log(TAG, "Malformed JSON; hyphen found within number value");
						return false;
					}
				}
				
				builder.append(c);
				json.incrementNdx();
				
				if (json.end()) {
					mLogger.log(TAG, "Malformed JSON; found end before Number value completed");
					return false;
				}
				
				c = json.currentChar();
			}
			
			final String v = builder.toString();
			final double d = Double.parseDouble(v);

			value.setNumber(d);
			json.decrementNdx();
			mLastSyntax = JsonSyntax.VALUE;
			return true;
		} else if (firstFour.contentEquals("null")) {
			value.setNull();
			postPrepareJson(json, 4);
			return true;
		} else if (firstFour.contentEquals("true")) {
			value.setBoolean(true);
			postPrepareJson(json, 4);
			return true;
		} else if (json.substring(5).contentEquals("false")) {
			value.setBoolean(false);
			postPrepareJson(json, 5);
			return true;
		}

		mLogger.log(TAG, "JSON is malformed; unexpected value found");
		debug(null, json);
		return false;
	}
	
	private boolean parseArray(final JsonString json, final List<JsonValue> values) {
		if (json.end()) {
			mLogger.log(TAG, "JSON unexpectedly ended during Array parsing");
			return false;
		}

		boolean firstPass = true;
		char c = json.currentChar();

		while (c != ']') {
			if (c == ',') {
				if (firstPass == true) {
					mLogger.log(TAG, "JSON Array parsing contains comma before first value");
					return false;
				}
				
				prepareJson(json);
				
				if (json.end()) {
					mLogger.log(TAG, "JSON unexpectedly ended during Array parsing after comma");
					return false;
				}
			}
			
			final JsonValue v = new JsonValue(null);
			
			if (!parseValue(json, v)) {
				return false;
			}

			values.add(v);
			firstPass = false;
			prepareJson(json);
			
			if (json.end()) {
				mLogger.log(TAG, "JSON unexpectedly ended during Array parsing loop");
				return false;
			}
			
			c = json.currentChar();
		};
		
		return true;
	}
	
	private void postPrepareJson(final JsonString json, final int increment) {
		json.incrementNdx(increment - 1);
		mLastSyntax = JsonSyntax.VALUE;
	}
	
	private void debug(final JsonObject obj, final JsonString json) {
		mLogger.log(TAG, "ndx: " + json.ndx());
		mLogger.log(TAG, "Parsed so far:");
		mLogger.log(TAG, json.json.substring(0, json.ndx()));
		mLogger.log(TAG, "Remaining:");
		mLogger.log(TAG, json.json.substring(json.ndx()));
		
		if (obj != null) {
			mLogger.log(TAG, obj.debug());
		}
	}

}
