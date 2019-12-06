# ScryfallAPIBinding

Provides programmatic access to ScryFall's API in Java.

##Usage:

Simply use `forohfor.scryfall.api.MTGCardQuery.search()` using a search query following ScryFall's search syntax.
For example, 

`forohfor.scryfall.api.MTGCardQuery.search("t:Legendary t:Mutant t:Ninja t:Turtle")`

returns a list of one card object, representing Mistform Ultimus.

See the search syntax notes [here](https://www.scryfall.com/docs/syntax).

##Use with gradle and maven

For the moment, the API should be compatible with gradle and maven through [jitpack](https://jitpack.io/). 

While this is not my ultimate goal, it works for my use case for the moment, while I focus on other projects.

##Notes

Please respect the rate limits mentioned in Scryfall's [API Overview](https://scryfall.com/docs/api-overview).

I am not affiliated with Scryfall.
