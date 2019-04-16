package forohfor.scryfall.example;

import java.util.ArrayList;
import java.util.Scanner;
import forohfor.scryfall.api.Card;
import forohfor.scryfall.api.MTGCardQuery;

/**
 * Example usage. Prompts for multiple exact card names and gives their average CMC.
 * @author ForOhForError
 */

public class ListExample {
	public static void main(String[] args) throws Exception {
		ArrayList<String> cardnames = new ArrayList<String>();
		Scanner scan = new Scanner(System.in);
		String query = " ";
		while (!query.equals("")) {
			System.out.print("Enter a card name, or an empty line to populate the list: ");
			query = scan.nextLine();
			if(!query.equals("")){
				cardnames.add(query);
			}
		}
		ArrayList<Card> cards = MTGCardQuery.toCardList(cardnames, false);

		double avgCMC = 0;

		System.out.println("The resulting cards: ");
		
		for(Card card:cards)
		{
			System.out.println(card);
			avgCMC += card.getCmc();
		}

		avgCMC /= cards.size();
		
		System.out.println("Averace CMC is "+avgCMC);
		scan.close();
		System.exit(0);
	}
}
