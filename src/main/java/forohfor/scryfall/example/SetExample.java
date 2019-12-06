package forohfor.scryfall.example;
import forohfor.scryfall.api.MTGCardQuery;
import forohfor.scryfall.api.Set;


public class SetExample {
	public static void main(String[] args) throws Exception {
		for(Set set: MTGCardQuery.getSets())
		{
			System.out.println(set.getName());
			if(set.getParentSetCode() != null)
			{
				System.out.println("\tchild of "+set.getParentSetCode());
			}
		}
	}
}
