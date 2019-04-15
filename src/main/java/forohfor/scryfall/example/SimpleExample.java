package forohfor.scryfall.example;
import java.util.ArrayList;

import forohfor.scryfall.api.Card;
import forohfor.scryfall.api.MTGCardQuery;


public class SimpleExample {
	public static void main(String[] args) throws Exception {
		String query = "Sky Hussar";
		ArrayList<Card> cards = MTGCardQuery.search(query);

		System.out.println("The resulting cards: ");

		for(Card card:cards)
		{
			System.out.println(card);
		}
	}
}
