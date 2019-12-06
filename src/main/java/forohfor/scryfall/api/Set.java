package forohfor.scryfall.api;

import java.util.Date;

import org.json.simple.JSONObject;

/**
 * Represents a single Set from Magic's history.
 * @author ForOhForError
 */

public class Set {
	private String id;
	private String code;
	private String name;
	private String searchUri;
	private String setType;
	private Boolean digitalOnly;
	private Boolean hasFoils;
	private Date releasedAt;
	private String blockCode;
	private String blockName;
	private int cardCount;
	private String setIconURI;
	private String parentSetCode;


	/**
	 * Builds a Set object from JSON data.
	 * @param setData The JSON object representing the set.
	 */
	public Set(JSONObject setData) {
		id = JSONUtil.getStringData(setData,"id");
		code = JSONUtil.getStringData(setData,"code");
		name = JSONUtil.getStringData(setData,"name");
		searchUri = JSONUtil.getStringData(setData,"search_uri");
		setType = JSONUtil.getStringData(setData,"set_type");
		digitalOnly = JSONUtil.getBoolData(setData,"digital");
		hasFoils = JSONUtil.getBoolData(setData,"foil");
		blockCode = JSONUtil.getStringData(setData,"block_code");
		blockName = JSONUtil.getStringData(setData,"block");
		cardCount = JSONUtil.getIntData(setData,"card_count");
		releasedAt = JSONUtil.getIsoDateData(setData,"released_at");
		setIconURI = JSONUtil.getStringData(setData, "icon_svg_uri");
		parentSetCode = JSONUtil.getStringData(setData, "parent_set_code");
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

	/**
	 * @return True if this set was only released on MTGO
	 */
	public Boolean isDigitalOnly() {
		return digitalOnly;
	}

	/**
	 * @return True if this set contains foil(s)
	 */
	public Boolean hasFoils() {
		return hasFoils;
	}

	/**
	 * @return The date this set was released at
	 */
	public Date getReleasedAt() {
		return releasedAt;
	}

	/**
	 * @return the blockCode
	 */
	public String getBlockCode() {
		return blockCode;
	}
	
	/**
	 * @return the block name
	 */
	public String getBlockName() {
		return blockName;
	}

	/**
	 * @return the cardCount
	 */
	public int getCardCount() {
		return cardCount;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return name+" ("+code+")";
	}

	/**
	 * @return the set icon's URI
	 */
	public String getSetIconURI()
	{
		return setIconURI;
	}

	/**
	 * @return the UUID of this set
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the code of this set's parent set
	 */
	public String getParentSetCode() {
		return parentSetCode;
	}	
}
