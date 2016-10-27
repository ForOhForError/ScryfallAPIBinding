# ScryfallAPIBinding

Provides programmatic access to ScryFall's API in Java.

##Usage:

Simply use `forohfor.scryfall.api.MTGCardQuery.search()` using a search query following ScryFall's search syntax.
For example, 

`forohfor.scryfall.api.MTGCardQuery.search("t:Legendary t:Mutant t:Ninja t:Turtle")`

returns a list of one card object, representing Mistform Ultimus.

See the search syntax notes [here](https://www.scryfall.com/docs/syntax).

##Penny Dreadful:

The library now supports checking for card legality in the [penny dreadful](http://pdmtgo.com/) format.

Call `forohfor.scryfall.api.MTGCardQuery.enablePennyDreadfulLegality()` to have penny dreadful legality added as a format to all cards.

##Notes

Please respect the rate limits mentioned in Scryfall's [API Overview](https://scryfall.com/docs/api-overview).

I am not affiliated with Scryfall.
