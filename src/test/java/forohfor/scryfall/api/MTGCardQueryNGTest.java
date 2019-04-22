package forohfor.scryfall.api;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import org.testng.annotations.Test;

public class MTGCardQueryNGTest
{
  /**
   * Test of toCardList method, of class MTGCardQuery.
   */
  @Test
  public void testToCardList()
  {
    System.out.println("toCardList");
    Collection<String> cardnames = new ArrayList<>();
    ArrayList<Set> sets = MTGCardQuery.getSets();
    Set set = sets.get(new Random().nextInt(sets.size()));
    ArrayList<Card> cards = MTGCardQuery.getCardsFromSet(set);
    assertNotNull(cards);
    assertFalse(cards.isEmpty());
    cards.forEach(card ->
    {
      cardnames.add(card.getName());
    });
    ArrayList<Card> result = MTGCardQuery.toCardList(cardnames, false);
    assertNotNull(result);
    assertFalse(result.isEmpty());
  }

  /**
   * Test of getSets method, of class MTGCardQuery.
   */
  @Test
  public void testGetSets()
  {
    System.out.println("getSets");
    ArrayList<Set> sets = MTGCardQuery.getSets();
    assertNotNull(sets);
    assertFalse(sets.isEmpty());
  }

  /**
   * Test of search method, of class MTGCardQuery.
   */
  @Test
  public void testSearch()
  {
    System.out.println("search");
    String query = "e:mmq";
    ArrayList<Card> result = MTGCardQuery.search(query);
    assertNotNull(result);
    assertFalse(result.isEmpty());
  }

  /**
   * Test of getCardByScryfallId method, of class MTGCardQuery.
   *
   * @throws java.lang.Exception
   */
  @Test
  public void testGetCardByScryfallId() throws Exception
  {
    System.out.println("getCardByScryfallId");
    ArrayList<Set> sets = MTGCardQuery.getSets();
    Set set = sets.get(new Random().nextInt(sets.size()));
    ArrayList<Card> cards = MTGCardQuery.getCardsFromSet(set);
    assertNotNull(cards);
    assertFalse(cards.isEmpty());

    Card card = cards.get(new Random().nextInt(cards.size()));
    Card result = MTGCardQuery.getCardByScryfallId(card.getScryfallUUID());

    assertNotNull(result);
    assertEquals(card.getName(), result.getName());
  }

  /**
   * Test of getCardFromURI method, of class MTGCardQuery.
   * @throws java.lang.Exception
   */
  @Test
  public void testGetCardFromURI() throws Exception
  {
    System.out.println("getCardFromURI");
    ArrayList<Set> sets = MTGCardQuery.getSets();
    Set set = sets.get(new Random().nextInt(sets.size()));
    ArrayList<Card> cards = MTGCardQuery.getCardsFromSet(set);
    assertNotNull(cards);
    assertFalse(cards.isEmpty());

    Card card = cards.get(new Random().nextInt(cards.size()));
    Card result = MTGCardQuery.getCardFromURI(card.getScryfallUri());

    assertNotNull(result);
    assertEquals(card.getName(), result.getName());
  }

  /**
   * Test of getCardsFromURI method, of class MTGCardQuery.
   */
  @Test
  public void testGetCardsFromURI()
  {
    System.out.println("getCardsFromURI");
    ArrayList<Set> sets = MTGCardQuery.getSets();
    Set set = sets.get(new Random().nextInt(sets.size()));
    ArrayList<Card> cards = MTGCardQuery.getCardsFromURI(set.getSearchUri());
    assertNotNull(cards);
    assertFalse(cards.isEmpty());
  }

  /**
   * Test of getCardsFromSet method, of class MTGCardQuery.
   */
  @Test
  public void testGetCardsFromSet()
  {
    System.out.println("getCardsFromSet");
    ArrayList<Set> sets = MTGCardQuery.getSets();
    Set set = sets.get(new Random().nextInt(sets.size()));
    System.out.println("Getting cards for set: " + set.getName());
    System.out.println("From: " + set.getSearchUri());
    ArrayList<Card> cards = MTGCardQuery.getCardsFromSet(set);
    assertNotNull(cards);
    assertFalse(cards.isEmpty());
  }
}
