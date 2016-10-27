package forohfor.scryfall.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.TreeSet;

/**
 * Utility class for determining the legality of cards in
 * the penny dreadful format.
 * @author ForOhForError
 */

public class PennyDreadfulLegalityChecker {
	private static TreeSet<String> legalCards = null;
	private static final String LIST_URI = "http://pdmtgo.com/legal_cards.txt";

	/**
	 * Initializes the legal cards list.
	 */
	private static void init()
	{
		try{
			legalCards = new TreeSet<String>();
			URL url = new URL(LIST_URI);
			URLConnection conn = url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "UTF-8"));
			String line = "";
			while(line != null)
			{
				legalCards.add(line.trim());
				line = in.readLine();
			}
		}catch(IOException e){}
	}
	
	/**
	 * Returns the legality of the given card name in
	 * the penny dreadful format
	 * @param cardName
	 */
	public static String getLegality(String cardName)
	{
		if(legalCards == null)
		{
			init();
		}
		if(legalCards != null && legalCards.contains(cardName))
		{
			return "legal";
		}
		return "not_legal";
	}
}
