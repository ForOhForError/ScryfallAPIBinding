package forohfor.scryfall.api;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * Top-level class, provides a static way to search for cards.
 *
 * @author ForOhForError
 */
public class MTGCardQuery
{
  private static final String API_URI = "https://api.scryfall.com";
  private static final String CARDS_SEARCH_ENDPOINT = "/cards/search";
  private static JSONParser JSON_PARSER = new JSONParser();
  private static final ClientHttpRequestFactory FACTORY
          = new BufferingClientHttpRequestFactory(
                  new SimpleClientHttpRequestFactory());
  private static final Logger LOG
          = Logger.getLogger(MTGCardQuery.class.getName());
  private static final int DELAY = 100;
  private static final RestTemplate RT = new RestTemplate(FACTORY);
  //Set to true to see request/response logging.
  private static final boolean DEBUG = false;

  static
  {
    RT.setInterceptors(Collections
            .singletonList(new RequestResponseLoggingInterceptor()));
    if (DEBUG)
    {
      Logger root = Logger.getLogger("");
      Level targetLevel = Level.FINE;
      root.setLevel(targetLevel);
      for (Handler handler : root.getHandlers())
      {
        handler.setLevel(targetLevel);
      }
      System.out.println("level set: " + targetLevel.getName());
    }
  }

  /**
   * Returns a list of card objects containing all cards matching any of the
   * card names passed as an argument. Works in as few API calls as possible.
   *
   * @param cardnames The collection of card names to get a list of objects from
   * @param listDuplicates If true, the returned list will contain all editions
   * of any card in the input collection.
   * @return A list of card objects that match the query.
   */
  public static ArrayList<Card> toCardList(Collection<String> cardnames,
          boolean listDuplicates)
  {
    StringBuilder query = new StringBuilder("");

    if (listDuplicates)
    {
      query.append("++");
    }
    cardnames.forEach((cardname) ->
    {
      query.append("!\"").append(cardname).append("\"  or ");
    });
    query.append("!\" \"");

    return search(query.toString());
  }

  /**
   * @return A list of all sets in magic's history.
   */
  public static ArrayList<Set> getSets()
  {
    ArrayList<Set> s = new ArrayList<>();
    try
    {
      String json = RT.getForEntity(API_URI + "/sets", String.class).getBody();

      JSONObject root;
      root = null;
      try
      {
        root = (JSONObject) MTGCardQuery.JSON_PARSER.parse(json);
      }
      catch (ParseException e)
      {
        LOG.log(Level.SEVERE, e.getLocalizedMessage(), e);
      }
      if (root != null)
      {
        JSONArray sets = (JSONArray) root.get("data");

        for (int i = 0; i < sets.size(); i++)
        {
          JSONObject setData = ((JSONObject) sets.get(i));
          s.add(new Set(setData));
        }
      }
    }
    catch (RestClientException e)
    {
      LOG.log(Level.SEVERE, e.getLocalizedMessage(), e);
    }
    return s;
  }

  /**
   * Returns a list of card objects that match the query. The query should be
   * formatted using scryfall's syntax: https://www.scryfall.com/docs/syntax
   *
   * @param query The query to match cards to
   * @return A list of card objects that match the query.
   */
  public static ArrayList<Card> search(String query)
  {
    String escapedQuery = "";
    try
    {
      escapedQuery = URLEncoder.encode(query, "UTF-8");
    }
    catch (IOException e)
    {
      LOG.log(Level.SEVERE, e.getLocalizedMessage(), e);
    }
    String uri = API_URI + CARDS_SEARCH_ENDPOINT + "?q=" + escapedQuery;

    return getCardsFromURI(uri);
  }

  /**
   * Returns a single card object representing the card with the given ID
   *
   * @param id The URI to pull data fromScryfall ID of the card
   * @return A single card object representing the card with the given ID
   * @throws java.io.IOException
   */
  public static Card getCardByScryfallId(String id) throws IOException
  {
    return RT.getForEntity(API_URI + "/cards/" + id, Card.class).getBody();
  }

  /**
   * Returns a single card object from the given URI
   *
   * @param uri The URI to pull data from
   * @return A single card object from the uri
   * @throws java.io.IOException
   */
  public static Card getCardFromURI(String uri) throws IOException
  {
    return RT.getForEntity(uri, Card.class).getBody();
  }

  /**
   * Returns a list of card objects from the given URI
   *
   * @param uri The URI to pull data from
   * @return A list of card objects from the uri
   */
  public static ArrayList<Card> getCardsFromURI(String uri)
  {
    ArrayList<Card> cards = new ArrayList<>();
    try
    {
      String json = RT.exchange(new URI(uri), HttpMethod.GET, HttpEntity.EMPTY, 
              String.class).getBody();

      JSONObject root;
      root = null;
      try
      {
        root = (JSONObject) MTGCardQuery.JSON_PARSER.parse(json);
      }
      catch (ParseException e)
      {
        LOG.log(Level.SEVERE, e.getLocalizedMessage(), e);
      }
      if (root != null)
      {
        JSONArray jsonCards = (JSONArray) root.get("data");

        for (int i = 0; i < jsonCards.size(); i++)
        {
          JSONObject cardData = ((JSONObject) jsonCards.get(i));
          cards.add(new Card(cardData));
        }

        if (root.containsKey("has_more") && ((Boolean) root.get("has_more")))
        {
          String next = (String) root.get("next_page");
          try
          {
            //Requested wait time between queries
            Thread.sleep(DELAY);
          }
          catch (InterruptedException e)
          {
            LOG.log(Level.SEVERE, e.getLocalizedMessage(), e);
          }
          cards.addAll(getCardsFromURI(next));
        }
      }
    }
    catch (RestClientException | URISyntaxException e)
    {
      LOG.log(Level.SEVERE, e.getLocalizedMessage(), e);
    }

    return cards;
  }

  /**
   * Returns a list of card objects from the given URI
   *
   * @param set The set to pull data from
   * @return A list of card objects from the uri
   */
  public static ArrayList<Card> getCardsFromSet(Set set)
  {
    return getCardsFromURI(set.getSearchUri());
  }
}