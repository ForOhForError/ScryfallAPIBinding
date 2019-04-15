package forohfor.scryfall.example;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import forohfor.scryfall.api.Card;
import forohfor.scryfall.api.MTGCardQuery;

/**
 * Example usage. Prompts for a card search and pops up an image if there's only 1 match.
 * Repeats until a blank line is entered.
 * 
 * For example, the search "t:Legendary t:mutant t:ninja t:turtle" will bring up the image
 * of Mistform Ultimus.
 * @author ForOhForError
 */

public class SearchExample {
	public static void main(String[] args) throws Exception {
		Scanner scan = new Scanner(System.in);
		String query = " ";
		while (!query.equals("")) {
			System.out.print("Enter card search: ");
			query = scan.nextLine();
			ArrayList<Card> cards = MTGCardQuery.search(query);
			
			System.out.println("The resulting cards: ");
			
			for(Card card:cards)
			{
				System.out.println(card);
			}
			
			if (cards.size() == 1) {
				Card c = cards.get(0);
				URL url = new URL(c.getImageURI());
				BufferedImage img = ImageIO.read(url);
				ImageIcon icon = new ImageIcon(img);
				JFrame j = new JFrame();
				JLabel jl = new JLabel();
				jl.setIcon(icon);
				j.add(jl);
				j.pack();
				j.setVisible(true);
			}else{
				System.out.println("No match or more than 1 match");
			}
		}
		scan.close();
		System.exit(0);
	}
}
