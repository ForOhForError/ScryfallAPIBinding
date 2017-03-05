package forohfor.scryfall.api;

import org.json.simple.JSONObject;

/**
 * Represents a single Set from Magic's history.
 * @author ForOhForError
 */

public class Set {
	private String code;
	private String name;
	private String searchUri;
	private String setType;
	
	/**
	 * Builds a Set object from JSON data.
	 * @param setData The JSON object representing the set.
	 */
	public Set(JSONObject setData) {
		code = JSONUtil.getStringData(setData,"code");
		name = JSONUtil.getStringData(setData,"name");
		searchUri = JSONUtil.getStringData(setData,"search_uri");
		setType = JSONUtil.getStringData(setData,"set_type");
	}

	/**
	 * @return The set code for this set.
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @return The name of this set.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return The URI containing the data of all cards in this set
	 */
	public String getSearchUri() {
		return searchUri;
	}

	/**
	 * @return The type of the set. See scryfall's API documentation.
	 */
	public String getSetType() {
		return setType;
	}
}
