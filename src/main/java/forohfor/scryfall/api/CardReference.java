package forohfor.scryfall.api;

import java.io.IOException;
import java.util.UUID;

import org.json.simple.JSONObject;

/**
 * Represents a reference to a single printing of a card. Used for cards with multiple
 * parts to refer to the pages of the other parts.
 * @author ForOhForError
 */

public class CardReference {

	private JSONObject json;
	private Card card;

	/**
	 * Constructs a new card with the given name, API URI, and scryfall id.
	 */
	public CardReference(JSONObject object) {
		super();
		json = new JSONObject(object);
	}

	/**
	 * @return The underlying JSON data for this reference.
	 */
	public JSONObject getJSONData()
	{
		return json;
	}


	/**
	 * @return A unique ID for this card in Scryfall’s database. 
	 */
	public UUID getScryfallUUID()
	{
		return UUID.fromString(JSONUtil.getStringData(json, "id"));
	}

	/**
	 * @return A field explaining what role this card plays in this relationship:
	 * one of token, meld_part, meld_result, or combo_piece. 
	 */
	public String getComponent()
	{
		return JSONUtil.getStringData(json, "component");
	}

	/**
	 * @return The name of this particular related card. 
	 */
	public String getName() {
		return JSONUtil.getStringData(json, "name");
	}

	/**
	 * @return The full type line of this card. 
	 */
	public String getTypeLine() {
		return JSONUtil.getStringData(json, "type_line");
	}

	/**
	 * @return A URI where you can retrieve a full object describing this card on Scryfall’s API.
	 */
	public String getURI()
	{
		return JSONUtil.getStringData(json, "uri");
	}
	
	/**
	 * Returns the card this object references. This is created by querying the
	 * API when first called on this object. If there is no connection, will return null.
	 */
	public Card getCard() {
		if(card == null){
			try
			{
				card = MTGCardQuery.getCardFromURI(getURI());
			}
			catch(IOException e)
			{
				System.err.println("IO Error on card part resolution");
			}
		}
		return card;
	}
	
}
