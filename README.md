# ScryfallAPIBinding

Provides programmatic access to ScryFall's API in Java.

## Usage:

Simply use `forohfor.scryfall.api.MTGCardQuery.search()` using a search query following ScryFall's search syntax.
For example, 

`forohfor.scryfall.api.MTGCardQuery.search("t:Legendary t:Mutant t:Ninja t:Turtle")`

returns a list of one card object, representing Mistform Ultimus.

See the search syntax notes [here](https://www.scryfall.com/docs/syntax).

## Use with gradle and maven

The library is available on Central OSSRH. An example dependency entry for maven is below:

```
<dependency>
    <groupId>io.github.forohforerror</groupId>
    <artifactId>ScryfallAPIBinding</artifactId>
    <version>1.9.1</version>
</dependency>
```

## Notes

Please respect the rate limits mentioned in Scryfall's [API Overview](https://scryfall.com/docs/api-overview).

I am not affiliated with Scryfall.
