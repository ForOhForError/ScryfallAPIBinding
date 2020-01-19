package forohfor.scryfall.api;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.json.simple.JSONObject;

/**
 * Represents one face of a multi-faced card. Notably, fewer fields than a full card.
 * @author ForOhForError
 */

public class CardFace {
	private JSONObject json;
	
	public CardFace(JSONObject cardData)
	{
		json = new JSONObject(cardData);
	}

	/**
	 * @return The underlying JSON data for this face.
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
	 * @return The name of the illustrator of this face. 
	 * Newly spoiled cards may not have this field yet. 
	 */
	public String getArtist() {
		return JSONUtil.getStringData(json, "artist");
	}

	/**
	 * @return The colors in this face’s color indicator, if any.
	 */
	public List<String> getColorIndicator()
	{
		return Arrays.asList(JSONUtil.getStringArrayData(json, "color_indicator"));
	}

	/**
	 * @return This face’s colors, if the game defines colors for the individual face of this card. 
	 */
	public List<String> getColors()
	{
		return Arrays.asList(JSONUtil.getStringArrayData(json, "colors"));
	}

	/**
	 * @return The flavor text, if any. 
	 */
	public String getFlavorText() {
		return JSONUtil.getStringData(json, "flavor_text");
	}

	/**
	 * @return A unique identifier for the face artwork that remains consistent across reprints. 
	 * Newly spoiled cards may not have this field yet. 
	 */
	public UUID getIllustrationUUID()
	{
		return UUID.fromString(JSONUtil.getStringData(json, "illustration_id"));
	}

		/**
	 * @return A map listing available imagery for this face. 
	 * See Scryfall's Card Imagery article for more information. 
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, String> getImageURIs() {
		return new HashMap<String, String>(JSONUtil.getObject(json, "image_uris"));
	}

	/**
	 * Returns the image URI of this face in the given format. 
	 * @param format the format to get the uri for. Case insensitive.
	 */
	public String getImageURI(String format) {
		return JSONUtil.getStringData(
			JSONUtil.getObject(json,"image_uris"),
			format.toLowerCase()
		);
	}

	/**
	 * @return This face's loyalty if any. Note that some cards have loyalties that are not numeric, such as X. 
	 */
	public String getLoyalty() {
		return JSONUtil.getStringData(json, "loyalty");
	}

	/**
	 * @return The mana cost for this face. 
	 * This value will be any empty string "" if the cost is absent.
	 * Remember that per the game rules, a missing mana cost and a mana cost
	 * of {0} are different values.
	 */
	public String getManaCost() {
		return JSONUtil.getStringData(json, "mana_cost");
	}

	/**
	 * @return The name of this particular face.
	 */
	public String getName() {
		return JSONUtil.getStringData(json, "name");
	}

	/**
	 * @return The Oracle text for this face, if any. 
	 */
	public String getOracleText() {
		return JSONUtil.getStringData(json, "oracle_text");
	}

	/**
	 * @return This face's power, if any. 
	 * Note that some cards have powers that are not numeric, such as *. 
	 */
	public String getPower() {
		return JSONUtil.getStringData(json, "power");
	}

	/**
	 * @return The localized name printed on this face, if any. 
	 */
	public String getPrintedName() {
		return JSONUtil.getStringData(json, "printed_name");
	}

	/**
	 * @return The localized text printed on this face, if any.
	 */
	public String getPrintedText() {
		return JSONUtil.getStringData(json, "printed_text");
	}

	/**
	 * @return The localized type line printed on this face, if any. 
	 */
	public String getPrintedTypeLine() {
		return JSONUtil.getStringData(json, "printed_type_line");
	}

	/**
	 * @return This face's toughness, if any. 
	 * Note that some cards have toughnesses that are not numeric, such as *. 
	 */
	public String getToughness() {
		return JSONUtil.getStringData(json, "toughness");
	}

	/**
	 * @return The full type line of this face. 
	 */
	public String getTypeLine() {
		return JSONUtil.getStringData(json, "type_line");
	}

	/**
	 * @return This card’s watermark, if any.
	 */
	public String getWatermark()
	{
		return JSONUtil.getStringData(json, "watermark");
	}
}
