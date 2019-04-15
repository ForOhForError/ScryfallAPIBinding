package forohfor.scryfall.api;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Top-level class, provides a static way to search for cards.
 * @author ForOhForError
 */

public class MTGCardQuery {

	private static final String API_URI = "https://api.scryfall.com";
	private static JSONParser JSON_PARSER = new JSONParser();

	/**
	 * Returns a list of card objects containing all cards matching any
	 * of the card names passed as an argument. Works in as few API calls
	 * as possible.
	 * @param cardnames The collection of cardnames to get a list of objects from
	 * @param listDuplicates If true, the returned list will contain all
	 * editions of any card in the input collection.
	 * @return A list of card objects that match the query. 
	 */
	public static ArrayList<Card> toCardList(Collection<String> cardnames, boolean listDuplicates)
	{
		StringBuilder query = new StringBuilder("");
		
		if(listDuplicates)
		{
			query.append("++");
		}
		for(String cardname:cardnames)
		{
			query.append("!\""+cardname+"\" "+" or ");
		}
		query.append("!\" \"");
		
		return search(query.toString());
	}
	
	/**
	 * @return A list of all sets in magic's history.
	 */
	public static ArrayList<Set> getSets()
	{
		ArrayList<Set> s = new ArrayList<>();
		try{
			URL url = new URL("https://api.scryfall.com/sets");
			URLConnection conn = url.openConnection();

			BufferedReader in = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "UTF-8"));


			String json = "";
			String line = "";
			while(line != null)
			{
				json+=line;
				line = in.readLine();
			}

			JSONObject root = null;
			try {
				root = (JSONObject)MTGCardQuery.JSON_PARSER.parse(json);
			} catch (ParseException e) {
			}

			in.close();

			JSONArray sets = (JSONArray)root.get("data");

			for (int i = 0; i < sets.size(); i++)
			{
				JSONObject setData = ((JSONObject)sets.get(i));
				s.add(new Set(setData));
			}
		}catch(IOException e){

		}
		return s;
	}
	
	/**
	 * Returns a list of card objects that match the query. 
	 * The query should be formatted using scryfall's syntax:
	 * https://www.scryfall.com/docs/syntax
	 * @param query The query to match cards to
	 * @return A list of card objects that match the query. 
	 */
	public static ArrayList<Card> search(String query)
	{
		String escapedQuery = "";
		try{
			escapedQuery = URLEncoder.encode(query,"UTF-8");
		}catch(IOException e){}
		String uri = API_URI+"/cards/search?q="+escapedQuery;

		return getCardsFromURI(uri);
	}

	/**
	 * Returns a single card object representing the card with the given ID
	 * @param id The URI to pull data fromScryfall ID of the card
	 * @return A single card object representing the card with the given ID
	 */
	public static Card getCardByScryfallId(String id) throws IOException
	{
		URL url = new URL("https://api.scryfall.com/cards/"+id);
		URLConnection conn = url.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(
				conn.getInputStream(), "UTF-8"));

		String json = "";
		String line = "";
		while(line != null)
		{
			json+=line;
			line = in.readLine();
		}

		JSONObject root = null;
		try {
			root = (JSONObject)MTGCardQuery.JSON_PARSER.parse(json);
		} catch (ParseException e) {
		}

		in.close();

		return new Card(root);
	}
	
	/**
	 * Returns a single card object from the given URI
	 * @param uri The URI to pull data from
	 * @return A single card object from the uri
	 */
	public static Card getCardFromURI(String uri) throws IOException
	{
		String escapedQuery = "";
		try{
			escapedQuery = URLEncoder.encode(uri,"UTF-8");
		}catch(IOException e){}
		URL url = new URL(escapedQuery);
		URLConnection conn = url.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(
				conn.getInputStream(), "UTF-8"));

		String json = "";
		String line = "";
		while(line != null)
		{
			json+=line;
			line = in.readLine();
		}

		JSONObject root = null;
		try {
			root = (JSONObject)MTGCardQuery.JSON_PARSER.parse(json);
		} catch (ParseException e) {
		}

		in.close();

		return new Card(root);
	}

	/**
	 * Returns a list of card objects from the given URI
	 * @param uri The URI to pull data from
	 * @return A list of card objects from the uri
	 */
	public static ArrayList<Card> getCardsFromURI(String uri)
	{
		ArrayList<Card> cards = new ArrayList<Card>();
		try{
			URL url = new URL(uri);
			URLConnection conn = url.openConnection();

			BufferedReader in = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "UTF-8"));


			String json = "";
			String line = "";
			while(line != null)
			{
				json+=line;
				line = in.readLine();
			}

			JSONObject root = null;
			try {
				root = (JSONObject)MTGCardQuery.JSON_PARSER.parse(json);
			} catch (ParseException e) {
			}

			in.close();

			JSONArray jsonCards = (JSONArray)root.get("data");

			for (int i = 0; i < jsonCards.size(); i++)
			{
				JSONObject cardData = ((JSONObject)jsonCards.get(i));
				cards.add(new Card(cardData));
			}

			if(root.containsKey("has_more") && ((Boolean)root.get("has_more")).booleanValue()){
				String next = (String)root.get("next_page");
				try {
					//Requested wait time between queries
					Thread.sleep(50);
				} catch (InterruptedException e) { }
				cards.addAll(getCardsFromURI(next));
			}
		}catch(IOException e){

		}

		return cards;
	}
}
