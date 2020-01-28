package forohfor.scryfall.api;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Represents a single printing of one card. "Special" cards (transforming/meld cards, for instance),
 * will have one Card object for each state of the card.
 * @author ForOhForError
 */

public class Card {
	private ArrayList<CardReference> allParts;
	private ArrayList<CardFace> cardFaces;

	private JSONObject json;

	/**
	 * Constructs a Card object from a JSON object
	 * @param cardData
	 */
	public Card(JSONObject cardData)
	{
		json = new JSONObject(cardData);

		if(cardData.containsKey("all_parts"))
		{
			allParts = getReferences(cardData,"all_parts");
		}
		if(cardData.containsKey("card_faces"))
		{
			cardFaces = getFaces(cardData,"card_faces");
		}
	}

	/**
	 * @return The underlying JSON data for this card.
	 */
	public JSONObject getJSONData()
	{
		return json;
	}

	/**
	 * @return The image for this card, in the "normal" format
	 */
	public BufferedImage getImage()
	{
		return getImage("normal");
	}

	/**
	 * @param format - the image format to fetch
	 * @return The image for this card, in the given format
	 */
	public BufferedImage getImage(String format)
	{
		try
		{
			return ImageIO.read(new URL(getImageURI(format)));
		}
		catch(IOException e)
		{
			return null;
		}
	}
	
	/**
	 * Utility method for reading each part of multipart cards.
	 */
	private static ArrayList<CardReference> getReferences(JSONObject data, String key)
	{
		Object obj = data.get(key);

		ArrayList<CardReference> refs = new ArrayList<CardReference>();
		if(obj!=null){
			JSONArray arr = (JSONArray)obj;
			for(Object o:arr)
			{
				JSONObject j = (JSONObject)o;
				refs.add(new CardReference(j));
			}
		}
		return refs;
	}
	
	/**
	 * Utility method for reading each part of multifaced cards.
	 */
	private static ArrayList<CardFace> getFaces(JSONObject data, String key)
	{
		Object obj = data.get(key);

		ArrayList<CardFace> refs = new ArrayList<CardFace>();
		if(obj!=null){
			JSONArray arr = (JSONArray)obj;
			for(Object o:arr)
			{
				JSONObject j = (JSONObject)o;
				refs.add(new CardFace(j));
			}
		}
		return refs;
	}

	/**
	 * @return True if this card has card faces
	 */
	public boolean isMultifaced()
	{
		return cardFaces != null && cardFaces.size() > 0;
	}

	/**
	 * @return True if this card is part of a multi-part collection
	 */
	public boolean isMultipart()
	{
		return allParts != null && allParts.size() > 0;
	}

	/**
	 * BEGIN: Core Card Fields
	 */

	/**
	 * @return This card’s Arena ID, if any. A large percentage of cards 
	 * are not available on Arena and do not have this ID. 
	 */
	public Integer getArenaID()
	{
		return JSONUtil.getIntData(json, "arena_id");
	}

	/**
	 * @return A unique ID for this card in Scryfall’s database. 
	 */
	public UUID getScryfallUUID()
	{
		return UUID.fromString(JSONUtil.getStringData(json, "id"));
	}

	/**
	 * @return A language code for this printing. 
	 */
	public String getLang()
	{
		return JSONUtil.getStringData(json, "lang");
	}

	/**
	 * @return This card’s Magic Online ID (also known as the Catalog ID), if any. 
	 * A large percentage of cards are not available on Magic Online and do not have this ID. 
	 */
	public Integer getMtgoID()
	{
		return JSONUtil.getIntData(json, "mtgo_id");
	}

	/**
	 * @return This card’s foil Magic Online ID (also known as the Catalog ID), if any. 
	 * A large percentage of cards are not available on Magic Online and do not have this ID. 
	 */
	public Integer getMtgoFoilID()
	{
		return JSONUtil.getIntData(json, "mtgo_foil_id");
	}

	/**
	 * @return This card’s multiverse IDs on Gatherer, if any, as an array of integers. 
	 * Note that Scryfall includes many promo cards, tokens, and other esoteric objects 
	 * that do not have these identifiers. 
	 */
	public List<Integer> getMultiverseIDs()
	{
		return Arrays.asList(JSONUtil.getIntArrayData(json, "multiverse_ids"));
	}

	/**
	 * @return This card’s ID on TCGplayer’s API, also known as the productId. 
	 */
	public Integer getTCGplayerID()
	{
		return JSONUtil.getIntData(json, "tcgplayer_id");
	}

	/**
	 * @return A unique ID for this card’s oracle identity. 
	 * This value is consistent across reprinted card editions, 
	 * and unique among different cards with the same name 
	 * (tokens, Unstable variants, etc). 
	 */
	public UUID getOracleID()
	{
		return UUID.fromString(JSONUtil.getStringData(json, "oracle_id"));
	}

	/**
	 * @return A link to where you can begin paginating all re/prints 
	 * for this card on Scryfall’s API. 
	 */
	public String getPrintsSearchURI()
	{
		return JSONUtil.getStringData(json, "prints_search_uri");
	}

	/**
	 * @return A link to this card’s rulings list on Scryfall’s API. 
	 */
	public String getRulingsURI()
	{
		return JSONUtil.getStringData(json, "rulings_uri");
	}

	/**
	 * @return A link to this card’s permapage on Scryfall’s website. 
	 */
	public String getScryfallURI()
	{
		return JSONUtil.getStringData(json, "scryfall_uri");
	}

	/**
	 * @return A link to this card object on Scryfall’s API.
	 */
	public String getURI()
	{
		return JSONUtil.getStringData(json, "uri");
	}

	/**
	 * BEGIN: Gameplay Fields
	 */

	/**
	 * @return If this card is closely related to other cards, this property will be a list of Card Reference Objects. 
	 */
	public ArrayList<CardReference> getAllParts()
	{
		return allParts;
	}

	/**
	 * @return All faces of this card, if multifaced.
	 */
	public List<CardFace> getCardFaces()
	{
		return cardFaces;
	}

	/**
	 * @return The card’s converted mana cost. Note that some funny cards have fractional mana costs.
	 */
	public Double getCmc()
	{
		return JSONUtil.getDoubleData(json, "cmc");
	}

	/**
	 * @return This card’s colors, if the overall card has colors defined by the rules. 
	 * Otherwise the colors will be on the CardFace objects. 
	 */
	public List<String> getColors()
	{
		return Arrays.asList(JSONUtil.getStringArrayData(json, "colors"));
	}

	/**
	 * @return This card’s color identity. 
	 */
	public List<String> getColorIdentity()
	{
		return Arrays.asList(JSONUtil.getStringArrayData(json, "color_identity"));
	}

	/**
	 * @return The colors in this card’s color indicator, if any. 
	 * A null value for this field indicates the card does not have one. 
	 */
	public List<String> getColorIndicator()
	{
		return Arrays.asList(JSONUtil.getStringArrayData(json, "color_indicator"));
	}

	/**
	 * @return This card’s overall rank/popularity on EDHREC. Not all cards are ranked.
	 */
	public Integer getEDHRecRank()
	{
		return JSONUtil.getIntData(json, "edhrec_rank");
	}

	/**
	 * @return True if this printing exists in a foil version. 
	 */
	public boolean hasFoil()
	{
		return JSONUtil.getBoolData(json, "foil");
	}

	/**
	 * @return This card’s hand modifier, if it is Vanguard card. 
	 * This value will contain a delta, such as -1. 
	 */
	public String getHandModifier()
	{
		return JSONUtil.getStringData(json, "hand_modifier");
	}

	/**
	 * @return A code for this card’s layout.
	 */
	public String getLayout() {
		return JSONUtil.getStringData(json, "layout");
	}

	/**
	 * @return A map describing the legality of this card across play formats.
	 * Possible legalities are legal, not_legal, restricted, and banned. 
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, String> getLegalities() {
		return new HashMap<String, String>(JSONUtil.getObject(json, "legalities"));
	}

	/**
	 * @return The legality of this card in the given play format.
	 * Possible legalities are legal, not_legal, restricted, and banned. 
	 * @param format the format to check. Case insensitive.
	 */
	public String getLegality(String format) {
		return JSONUtil.getStringData(
			JSONUtil.getObject(json,"legalities"),
			format.toLowerCase()
		);
	}

	/**
	 * @return true if the card is strictly legal in the given play format. 
	 * Cards that are restricted in the format will return false.
	 * @param format the format to check. Case insensitive.
	 */
	public boolean isLegal(String format) {
		return "legal".equals(getLegality(format));
	}

	/**
	 * @return This card’s life modifier, if it is Vanguard card. 
	 * This value will contain a delta, such as +2. 
	 */
	public String getLifeModifier() {
		return JSONUtil.getStringData(json, "life_modifier");
	}

	/**
	 * @return This loyalty if any. Note that some cards have loyalties that are not numeric, such as X. 
	 */
	public String getLoyalty() {
		return JSONUtil.getStringData(json, "loyalty");
	}

	/**
	 * @return The mana cost for this card. 
	 * This value will be any empty string "" if the cost is absent.
	 * Remember that per the game rules, a missing mana cost and a mana cost
	 * of {0} are different values.
	 * Multi-faced cards will report this value in card faces. 
	 */
	public String getManaCost() {
		return JSONUtil.getStringData(json, "mana_cost");
	}

	/**
	 * @return The name of this card. 
	 * If this card has multiple faces, this field will contain
	 * both names separated by //.
	 */
	public String getName() {
		return JSONUtil.getStringData(json, "name");
	}

	/**
	 * @return True if this printing exists in a nonfoil version. 
	 */
	public boolean hasNonfoil()
	{
		return JSONUtil.getBoolData(json, "nonfoil");
	}

	/**
	 * @return The Oracle text for this card, if any. 
	 */
	public String getOracleText() {
		return JSONUtil.getStringData(json, "oracle_text");
	}

	/**
	 * @return True if this card is oversized.
	 */
	public boolean isOversized()
	{
		return JSONUtil.getBoolData(json, "oversized");
	}

	/**
	 * @return This card’s power, if any. 
	 * Note that some cards have powers that are not numeric, such as *. 
	 */
	public String getPower() {
		return JSONUtil.getStringData(json, "power");
	}

	/**
	 * @return True if this card is on the Reserved List. 
	 */
	public boolean isReserved() {
		return JSONUtil.getBoolData(json, "reserved");
	}

	/**
	 * @return This card’s toughness, if any. 
	 * Note that some cards have toughnesses that are not numeric, such as *. 
	 */
	public String getToughness() {
		return JSONUtil.getStringData(json, "toughness");
	}

	/**
	 * @return The full type line of this card. 
	 */
	public String getTypeLine() {
		return JSONUtil.getStringData(json, "type_line");
	}

	/**
	 * BEGIN: Print Fields
	 */

	/**
	 * @return The name of the illustrator of this card. 
	 * Newly spoiled cards may not have this field yet. 
	 */
	public String getArtist() {
		return JSONUtil.getStringData(json, "artist");
	}

	/**
	 * @return True if this card is found in boosters. 
	 */
	public boolean isInBoosters()
	{
		return JSONUtil.getBoolData(json, "booster");
	}

	/**
	 * @return This card’s border color: black, borderless, gold, silver, or white. 
	 */
	public String getBorderColor() {
		return JSONUtil.getStringData(json, "border_color");
	}

	/**
	 * @return The Scryfall ID for the card back design present on this card.
	 */
	public UUID CardBackUUID() {
		return UUID.fromString(JSONUtil.getStringData(json, "card_back_id"));
	}

	/**
	 * @return This card’s collector number. 
	 * Note that collector numbers can contain non-numeric characters, 
	 * such as letters or ★.
	 */
	public String getCollectorNumber() {
		return JSONUtil.getStringData(json, "collector_number");
	}

	/**
	 * @return True if this is a digital card on Magic Online.
	 */
	public boolean isDigital() {
		return JSONUtil.getBoolData(json, "digital");
	}

	/**
	 * @return The flavor text, if any. 
	 */
	public String getFlavorText() {
		return JSONUtil.getStringData(json, "flavor_text");
	}

	/**
	 * @return This card’s frame effects, if any.
	 */
	public List<String> getFrameEffects() {
		return Arrays.asList(JSONUtil.getStringArrayData(json, "frame_effects"));
	}

	/**
	 * @return This card’s frame layout. 
	 */
	public String getFrame() {
		return JSONUtil.getStringData(json, "frame");
	}

	/**
	 * @return True if this card’s artwork is larger than normal. 
	 */
	public boolean isFullArt() {
		return JSONUtil.getBoolData(json, "full_art");
	}

	/**
	 * @return A list of games that this card print is available in: 
	 * paper, arena, and/or mtgo. 
	 */
	public List<String> getGames()
	{
		return Arrays.asList(JSONUtil.getStringArrayData(json, "games"));
	}

	/**
	 * @return True if this card’s imagery is high resolution. 
	 */
	public boolean hasHighresImage() {
		return JSONUtil.getBoolData(json, "highres_image");
	}

	/**
	 * @return A unique identifier for the card artwork that remains consistent across reprints. 
	 * Newly spoiled cards may not have this field yet. 
	 */
	public UUID getIllustrationUUID()
	{
		return UUID.fromString(JSONUtil.getStringData(json, "illustration_id"));
	}

	/**
	 * @return A map listing available imagery for this card. 
	 * See Scryfall's Card Imagery article for more information. 
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, String> getImageURIs() {
		return new HashMap<String, String>(JSONUtil.getObject(json, "image_uris"));
	}

	/**
	 * Returns the image URI of this card in the given format. 
	 * @param format the format to get the uri for. Case insensitive.
	 */
	public String getImageURI(String format) {
		return JSONUtil.getStringData(
			JSONUtil.getObject(json,"image_uris"),
			format.toLowerCase()
		);
	}

	/**
	 * @return A map containing daily price information for this card, 
	 * including usd, usd_foil, eur, and tix prices, as strings. 
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, String> getPrices() {
		return new HashMap<String, String>(JSONUtil.getObject(json, "prices"));
	}

	/**
	 * @return The localized name printed on this card, if any. 
	 */
	public String getPrintedName() {
		return JSONUtil.getStringData(json, "printed_name");
	}

	/**
	 * @return The localized text printed on this card, if any.
	 */
	public String getPrintedText() {
		return JSONUtil.getStringData(json, "printed_text");
	}

	/**
	 * @return The localized type line printed on this card, if any. 
	 */
	public String getPrintedTypeLine() {
		return JSONUtil.getStringData(json, "printed_type_line");
	}

	/**
	 * @return True if this card is a promotional print. 
	 */
	public boolean isPromo() {
		return JSONUtil.getBoolData(json, "promo");
	}

	/**
	 * @return An array of strings describing what categories of promo cards this card falls into.
	 */
	public List<String> getPromoTypes()
	{
		return Arrays.asList(JSONUtil.getStringArrayData(json, "promo_types"));
	}

	/**
	 * @return A map providing URIs to this card’s listing on major marketplaces. 
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, String> getPurchaseURIs() {
		return new HashMap<String, String>(JSONUtil.getObject(json, "purchase_uris"));
	}

	/**
	 * @return This card’s rarity. One of common, uncommon, rare, or mythic.
	 */
	public String getRarity() {
		return JSONUtil.getStringData(json, "rarity");
	}

	/**
	 * @return A map providing URIs to this card’s listing on other 
	 * Magic: The Gathering online resources. 
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, String> getRelatedURIs() {
		return new HashMap<String, String>(JSONUtil.getObject(json, "related_uris"));
	}

	/**
	 * @return The date this set was released at
	 */
	public Date getReleasedAt() {
		return JSONUtil.getIsoDateData(json, "released_at");
	}

	/**
	 * @return True if this card is a reprint.
	 */
	public boolean isReprint() {
		return JSONUtil.getBoolData(json, "reprint");
	}

	/**
	 * @return A link to this card’s set on Scryfall’s website. 
	 */
	public String getScryfallSetURI() {
		return JSONUtil.getStringData(json, "scryfall_set_uri");
	}

	/**
	 * @return A link to this card’s set on Scryfall’s website. 
	 */
	public String getSetName() {
		return JSONUtil.getStringData(json, "set_name");
	}

	/**
	 * @return A link to where you can begin paginating this card’s set on the Scryfall API.
	 */
	public String getSetSearchURI() {
		return JSONUtil.getStringData(json, "set_search_uri");
	}

	/**
	 * @return The type of set this printing is in.
	 */
	public String getSetType() {
		return JSONUtil.getStringData(json, "set_type");
	}

	/**
	 * @return A link to this card’s set object on Scryfall’s API. 
	 */
	public String getSetURI() {
		return JSONUtil.getStringData(json, "set_uri");
	}

	/**
	 * @return This card’s set code.
	 */
	public String getSetCode()
	{
		return JSONUtil.getStringData(json, "set");
	}

	/**
	 * @return True if this card is a Story Spotlight. 
	 */
	public boolean isStorySpotlight() {
		return JSONUtil.getBoolData(json, "story_spotlight");
	}

	/**
	 * @return True if this card is textless.
	 */
	public boolean isTextless() {
		return JSONUtil.getBoolData(json, "textless");
	}

	/**
	 * @return True if this card is a variation of another printing. 
	 */
	public boolean isVariation() {
		return JSONUtil.getBoolData(json, "variation");
	}

	/**
	 * @return The printing ID of the printing this card is a variation of. 
	 */
	public UUID getVariationOfUUID()
	{
		return UUID.fromString(JSONUtil.getStringData(json, "variation_of"));
	}

	/**
	 * @return This card’s watermark, if any.
	 */
	public String getWatermark()
	{
		return JSONUtil.getStringData(json, "watermark");
	}

	/**
	 * @return The date this card was previewed.
	 */
	public Date getPreviewDate()
	{
		return JSONUtil.getIsoDateData(
			JSONUtil.getObject(json, "preview"),
			"previewed_at"
		);
	}

	/**
	 * @return A link to the preview for this card.
	 */
	public String getPreviewSourceURI()
	{
		return JSONUtil.getStringData(
			JSONUtil.getObject(json, "preview"),
			"source_uri"
		);
	}

	/**
	 * @return The name of the source that previewed this card. 
	 */
	public String getPreviewSource()
	{
		return JSONUtil.getStringData(
			JSONUtil.getObject(json, "preview"),
			"source"
		);
	}

	/**
	 * Returns a simplification of this card, used when printing it.
	 */
	public String toString() {
		return "Card [" + getName() + ": " + getSetCode() + "]";
	}

	/** 
	 * Hashcode method; works off of name and set code. Auto-generated.
	 */
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
		result = prime * result + ((getSetCode() == null) ? 0 : getSetCode().hashCode());
		return result;
	}

	/** 
	 * Equals method; checks for name and set code equality. Auto-generated.
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		if (getName() == null) {
			if (other.getName() != null)
				return false;
		} else if (!getName().equals(other.getName()))
			return false;
		if (getSetCode() == null) {
			if (other.getSetCode() != null)
				return false;
		} else if (!getSetCode().equals(other.getSetCode()))
			return false;
		return true;
	}
}
