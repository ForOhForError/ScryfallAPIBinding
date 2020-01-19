package forohfor.scryfall.api;

import java.util.Date;
import java.util.UUID;

import org.json.simple.JSONObject;

/**
 * Represents a single Set from Magic's history.
 * @author ForOhForError
 */

public class Set {

	private JSONObject json;

	/**
	 * Builds a Set object from JSON data.
	 * @param setData The JSON object representing the set.
	 */
	public Set(JSONObject setData) 
	{
		json = new JSONObject(setData);
	}

	/**
	 * @return A UUID for this set on Scryfall that will not change. 
	 */
	public UUID getId() 
	{
		return UUID.fromString(JSONUtil.getStringData(json, "id"));
	}

	/**
	 * @return The unique three to five-letter code for this set. 
	 */
	public String getCode() 
	{
		return JSONUtil.getStringData(json, "code");
	}

	/**
	 * @return The unique code for this set on MTGO, which may differ from the regular code. 
	 */
	public String getMtgoCode() 
	{
		return JSONUtil.getStringData(json, "mtgo_code");
	}

	/**
	 * @return This set’s ID on TCGplayer’s API, also known as the groupId. 
	 */
	public String getTCGplayerID()
	{
		return JSONUtil.getStringData(json, "tcgplayer_id");
	}

	/**
	 * @return The English name of this set.
	 */
	public String getName() 
	{
		return JSONUtil.getStringData(json, "name");
	}

	/**
	 * @return A computer-readable classification for this set. 
	 * See scryfall's API documentation.
	 */
	public String getSetType() {
		return JSONUtil.getStringData(json, "set_type");
	}

	/**
	 * @return The date the set was released or the first card was printed in the set 
	 * (in GMT-8 Pacific time). 
	 */
	public Date getReleasedAt() {
		return JSONUtil.getIsoDateData(json, "released_at");
	}

	/**
	 * @return The block code for this set, if any. 
	 */
	public String getBlockCode() {
		return JSONUtil.getStringData(json, "block_code");
	}

	/**
	 * @return The block or group name code for this set, if any. 
	 */
	public String getBlockName() {
		return JSONUtil.getStringData(json, "block");
	}
	
	/**
	 * @return The set code for the parent set, if any. 
	 * promo and token sets often have a parent set. 
	 */
	public String getParentSetCode() {
		return JSONUtil.getStringData(json, "parent_set_code");
	}

	/**
	 * @return the cardCount
	 */
	public int getCardCount() {
		return JSONUtil.getIntData(json, "card_count");
	}

	/**
	 * @return True if this set was only released on Magic Online. 
	 */
	public boolean isDigital() {
		return JSONUtil.getBoolData(json, "digital");
	}

	/**
	 * @return True if this set contains only foil cards. 
	 */
	public boolean isFoilOnly() {
		return JSONUtil.getBoolData(json, "foil_only");
	}

	/**
	 * @return A link to this set’s permapage on Scryfall’s website. 
	 */
	public String getScryfallURI() {
		return JSONUtil.getStringData(json, "scryfall_uri");
	}

	/**
	 * @return A link to this set object on Scryfall’s API. 
	 */
	public String getURI() {
		return JSONUtil.getStringData(json, "uri");
	}


	/**
	 * @return A Scryfall API URI that you can request to begin paginating over the cards in this set. 
	 */
	public String getSearchURI() {
		return JSONUtil.getStringData(json, "search_uri");
	}

	/**
	 * @return A URI to an SVG file for this set’s icon on Scryfall’s CDN. 
	 * Hotlinking this image isn’t recommended, because it may change slightly over time. 
	 * You should download it and use it locally for your particular user interface needs. 
	 */
	public String getSetIconURI()
	{
		return JSONUtil.getStringData(json, "icon_svg_uri");
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getName()+" ("+getCode()+")";
	}
}
