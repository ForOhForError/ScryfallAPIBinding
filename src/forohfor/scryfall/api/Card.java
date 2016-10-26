package forohfor.scryfall.api;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Represents a single printing of one card. "Special" cards (transforming/meld cards, for instance),
 * will have one Card object for each state of the card.
 * @author ForOhForError
 */

public class Card {
	private String name;
	private String manaCost;
	private int cmc;
	private String typeLine;
	private String oracleText;
	private String[] colors;
	private String[] colorIdentity;
	private String layout;
	private HashMap<String, String> legalities;
	private boolean reserved;
	private String scryfallUUID;
	private Integer multiverseID;
	private Integer mtgoID;
	private String setCode;
	private String setName;
	private String collectorNumber;
	private boolean multiPart;
	private ArrayList<CardReference> allParts;
	private String rarity;
	private boolean digitalOnly;
	private String flavorText;
	private String artist;
	private String frame;
	private String border;
	private boolean timeShifted;
	private boolean colorShifted;
	private boolean futureShifted;
	private String priceUsd;
	private String priceTix;
	private String scryfallUri;
	private String imageURI;

	/**
	 * Constructs a Card object from a JSON object
	 * @param cardData
	 */
	public Card(JSONObject cardData) {
		name = JSONUtil.getStringData(cardData,"name");
		manaCost = JSONUtil.getStringData(cardData,"mana_cost");
		cmc = JSONUtil.getLongData(cardData, "converted_mana_cost").intValue();
		typeLine = JSONUtil.getStringData(cardData,"type_line");
		oracleText = JSONUtil.getStringData(cardData,"oracle_text");
		colors = JSONUtil.getStringArrayData(cardData,"colors");
		colorIdentity = JSONUtil.getStringArrayData(cardData,"color_identity");
		layout = JSONUtil.getStringData(cardData,"layout");
		reserved = JSONUtil.getBoolData(cardData, "reserved").booleanValue();
		scryfallUUID = JSONUtil.getStringData(cardData,"id");
		multiverseID = JSONUtil.getIntData(cardData,"multiverse_id");
		mtgoID = JSONUtil.getIntData(cardData,"mtgo_id");
		setCode = JSONUtil.getStringData(cardData,"set");
		setName = JSONUtil.getStringData(cardData,"set_name");
		collectorNumber = JSONUtil.getStringData(cardData,"collector_number");
		rarity = JSONUtil.getStringData(cardData,"rarity");
		digitalOnly = JSONUtil.getBoolData(cardData, "digital").booleanValue();
		flavorText = JSONUtil.getStringData(cardData,"flavor_text");
		artist = JSONUtil.getStringData(cardData,"artist");
		frame = JSONUtil.getStringData(cardData,"frame");
		border = JSONUtil.getStringData(cardData,"border_color");
		timeShifted = JSONUtil.getBoolData(cardData, "timeshifted").booleanValue();
		colorShifted = JSONUtil.getBoolData(cardData, "colorshifted").booleanValue();
		futureShifted = JSONUtil.getBoolData(cardData, "futureshifted").booleanValue();
		priceUsd = JSONUtil.getStringData(cardData,"usd");
		priceTix = JSONUtil.getStringData(cardData,"tix");
		scryfallUri = JSONUtil.getStringData(cardData,"uri");
		imageURI = JSONUtil.getStringData(cardData,"image_uri");
		legalities = JSONUtil.getStringMap(cardData,"legalities");

		if(cardData.containsKey("all_parts"))
		{
			multiPart = true;
			allParts = getAllParts(cardData,"all_parts");
		}
	}

	/**
	 * Utility method for reading each part of multipart cards.
	 */
	private static ArrayList<CardReference> getAllParts(JSONObject data, String key)
	{
		Object obj = data.get(key);
		if(obj==null){
			return null;
		}

		ArrayList<CardReference> refs = new ArrayList<CardReference>();

		JSONArray arr = (JSONArray)obj;
		for(Object o:arr)
		{
			JSONObject j = (JSONObject)o;
			refs.add(new CardReference((String)j.get("name"),(String)j.get("uri"),(String)j.get("id")));
		}
		return refs;
	}

	/**
	 * Returns a list of CardReference objects referencing all parts of a "special" 
	 * multipart card. This will include a reference to this card.
	 * Will be null if isMultiPart() == false.
	 */
	public ArrayList<CardReference> getPartReferences(){
		return new ArrayList<CardReference>(allParts);
	}

	/**
	 * Returns the legality of this card in the given format. 
	 * @param format the format to check. Case insensitive.
	 */
	public String getLegality(String format) {
		return legalities.get(format.toLowerCase());
	}

	/**
	 * Returns this card's name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns this card's mana cost.
	 */
	public String getManaCost() {
		return manaCost;
	}

	/**
	 * Returns this card's converted mana cost.
	 */
	public int getCmc() {
		return cmc;
	}

	/**
	 * Returns this card's full type line.
	 */
	public String getTypeLine() {
		return typeLine;
	}

	/**
	 * Returns this card's oracle text.
	 */
	public String getOracleText() {
		return oracleText;
	}

	/**
	 * Returns a list of this card's colors.
	 */
	public String[] getColors() {
		return colors;
	}

	/**
	 * Returns a list of the colors in this card's color identity.
	 */
	public String[] getColorIdentity() {
		return colorIdentity;
	}

	/**
	 * Returns this card's layout type.
	 */
	public String getLayout() {
		return layout;
	}

	/**
	 * Returns true if this card is on the reserved list, and false otherwise.
	 */
	public boolean isReserved() {
		return reserved;
	}

	/**
	 * Returns scryfall's internal id for this card.
	 */
	public String getScryfallUUID() {
		return scryfallUUID;
	}

	/**
	 * Returns this card's multiverse id.
	 */
	public Integer getMultiverseID() {
		return multiverseID;
	}

	/**
	 * Returns this card's MTGO id.
	 */
	public Integer getMtgoID() {
		return mtgoID;
	}

	/**
	 * Returns the 3-character set code for this printing of the card.
	 */
	public String getSetCode() {
		return setCode;
	}

	/**
	 * Returns the set name for this printing of the card.
	 */
	public String getSetName() {
		return setName;
	}

	/**
	 * Returns the collector number for this printing of the card.
	 */
	public String getCollectorNumber() {
		return collectorNumber;
	}

	/**
	 * Returns true if this card has multiple parts, and false otherwise.
	 * The other parts are referenced in the list returned by getPartReferences().
	 */
	public boolean isMultiPart() {
		return multiPart;
	}

	/**
	 * Returns the rarity for this printing of the card.
	 */
	public String getRarity() {
		return rarity;
	}

	/**
	 * Returns true if this printing of the card is only available on MTGO, and
	 * false otherwise.
	 */
	public boolean isDigitalOnly() {
		return digitalOnly;
	}

	/**
	 * Returns the flavor text for this printing of the card.
	 */
	public String getFlavorText() {
		return flavorText;
	}

	/**
	 * Returns the artist text for this printing of the card.
	 */
	public String getArtist() {
		return artist;
	}

	/**
	 * Returns the type of frame used for this printing of the card.
	 */
	public String getFrame() {
		return frame;
	}

	/**
	 * Returns color of border used for this printing of the card.
	 */
	public String getBorder() {
		return border;
	}

	/**
	 * Returns true if this card is "Time Shifted" from the past, and
	 * false otherwise.
	 */
	public boolean isTimeShifted() {
		return timeShifted;
	}

	/**
	 * Returns true if this card is "Color Shifted" from another card, and
	 * false otherwise.
	 */
	public boolean isColorShifted() {
		return colorShifted;
	}

	/**
	 * Returns true if this card is "Future Shifted" from the future (spooky),
	 * and false otherwise.
	 */
	public boolean isFutureShifted() {
		return futureShifted;
	}

	/**
	 * Returns this printing's price in USD. Formatted as a String "Dollars.Cents".
	 */
	public String getPriceUsd() {
		return priceUsd;
	}

	/**
	 * Returns this printing's price in MTGO tickets. Formatted as a String "Tickets.Hundreths".
	 */
	public String getPriceTix() {
		return priceTix;
	}

	/**
	 * Returns a URI for the scryfall API page for this card.
	 */
	public String getScryfallUri() {
		return scryfallUri;
	}

	/**
	 * Returns a URI for scryfall's image of this card.
	 */
	public String getImageURI() {
		return imageURI;
	}

	/**
	 * Returns a simplification of this card, used when printing it.
	 */
	public String toString() {
		return "Card [name=" + name + ", setCode=" + setCode + "]";
	}

}
