package forohfor.scryfall.api;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import javax.imageio.ImageIO;

import org.json.simple.JSONObject;

/**
 * Represents one face of a multi-faced card. Notably, fewer fields than a full card.
 * @author ForOhForError
 */

public class CardFace {
	private String name;
	private String typeLine;
	private String oracleText;
	private String manaCost;
	private String[] colors;
	private String power;
	private String toughness;
	private String loyalty;
	private String flavorText;
	private HashMap<String, String> imageURIs;
	
	public CardFace(JSONObject cardData)
	{
		name = JSONUtil.getStringData(cardData,"name");
		manaCost = JSONUtil.getStringData(cardData,"mana_cost");
		typeLine = JSONUtil.getStringData(cardData,"type_line");
		oracleText = JSONUtil.getStringData(cardData,"oracle_text");
		colors = JSONUtil.getStringArrayData(cardData,"colors");
		flavorText = JSONUtil.getStringData(cardData,"flavor_text");
		power = JSONUtil.getStringData(cardData,"power");
		toughness = JSONUtil.getStringData(cardData,"toughness");
		loyalty = JSONUtil.getStringData(cardData,"loyalty");
		imageURIs = JSONUtil.getStringMap(cardData,"image_uris");
	}
	
	/**
	 * Returns the image of this card in the given format. 
	 * @param format the format to check. Case insensitive.
	 */
	public String getImageURI(String format) {
		return imageURIs.get(format.toLowerCase());
	}
	
	/**
	 * @return This card's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return This card's type line
	 */
	public String getTypeLine() {
		return typeLine;
	}

	/**
	 * @return This card's oracle text
	 */
	public String getOracleText() {
		return oracleText;
	}

	/**
	 * @return This card's mana cost
	 */
	public String getManaCost() {
		return manaCost;
	}

	/**
	 * @return This card's colors
	 */
	public String[] getColors() {
		return colors;
	}

	/**
	 * @return This card's power
	 */
	public String getPower() {
		return power;
	}

	/**
	 * @return This card's toughness
	 */
	public String getToughness() {
		return toughness;
	}

	/**
	 * @return This card's loyalty
	 */
	public String getLoyalty() {
		return loyalty;
	}

	/**
	 * @return This card's flavorText
	 */
	public String getFlavorText() {
		return flavorText;
	}

	/**
	 * @return This card's set of imageURIs
	 */
	public HashMap<String, String> getImageURIs() {
		return imageURIs;
	}
	
	/**
	 * @return The uri for the preferred image for this card
	 */
	public String getCannonicalImageURI()
	{
		return getImageURI("png");
	}
	
	/**
	 * @return The preferred image for this card
	 */
	public BufferedImage getCannonicalImage()
	{
		try
		{
			return ImageIO.read(new URL(getCannonicalImageURI()));
		}
		catch(IOException e)
		{
			return null;
		}
	}
}
