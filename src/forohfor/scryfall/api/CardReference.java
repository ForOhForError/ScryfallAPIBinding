package forohfor.scryfall.api;
import java.io.IOException;

/**
 * Represents a reference to a single printing of a card. Used for cards with multiple
 * parts to refer to the pages of the other parts.
 * @author ForOhForError
 */

public class CardReference {
	/**
	 * Constructs a new card with the given name, API URI, and scryfall id.
	 */
	public CardReference(String cardName, String cardUri, String scryfallUUID) {
		super();
		this.cardName = cardName;
		this.cardUri = cardUri;
		this.scryfallUUID = scryfallUUID;
		this.card = null;
	}
	private String cardName;
	private String cardUri;
	private String scryfallUUID;
	private Card card;
	
	/**
	 * Returns the name of the card this object references.
	 */
	public String getCardName() {
		return cardName;
	}
	
	/**
	 * Returns the API URI of the card this object references.
	 */
	public String getCardUri() {
		return cardUri;
	}
	
	/**
	 * Returns the card this object references. This is created by querying the
	 * API when first called on this object. If there is no connection, will return null.
	 */
	public Card getCard() {
		if(card == null){
			try
			{
				card = MTGCardQuery.getCardFromURI(cardUri);
			}
			catch(IOException e)
			{
				System.err.println("IO Error on card part resolution");
			}
		}
		return card;
	}

	/**
	 * Returns scryfall's internal ID for the card this object references.
	 */
	public String getScryfallUUID() {
		return scryfallUUID;
	}
	
	
}
