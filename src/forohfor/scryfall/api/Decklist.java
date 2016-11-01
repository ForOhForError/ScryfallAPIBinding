package forohfor.scryfall.api;

import java.util.ArrayList;
import java.util.Collection;

public class Decklist {

	/** Modes for creating a decklist */
	
	/** Have the decklist contain only the latest version of each card*/
	public static final int LIST_LATEST_VERSION = 0;
	
	/** Have the decklist contain all versions of each card */
	public static final int LIST_ALL_DUPLICATES = 10;
	
	private ArrayList<Card> deck;
	
	public Decklist(Collection<String> cardnames)
	{
		this(cardnames,LIST_LATEST_VERSION);
	}
	
	public Decklist(Collection<String> cardnames, int mode)
	{
		StringBuilder query = new StringBuilder("");
		deck = new ArrayList<Card>();
		
		if(mode==LIST_ALL_DUPLICATES)
		{
			query.append("++");
		}
		
		for(String cardname:cardnames)
		{
			query.append("!\""+cardname+"\" ");
		}
		deck = MTGCardQuery.search(query.toString());
		deck = filterResults(deck,mode);
	}
	
	private static ArrayList<Card> filterResults(ArrayList<Card> raw, int mode)
	{
		ArrayList<Card> filtered = new ArrayList<Card>();
		
		
		return filtered;
	}
	
	public ArrayList<Card> getCards()
	{
		return new ArrayList<Card>(deck);
	}
	
	public int size()
	{
		return deck.size();
	}
	
}