Class definition for KRAcH programming project

```mermaid
classDiagram
	class Card
	Card : -String rank
	Card : -String suit
	Card : getValue() int
	Card : getImage() String

	class Deck
	Deck : ArrayList<Card> cards
	Deck : shuffleDeck() void
	Deck : dealCard() Card

	Deck "1" --> "*" Card
	
	class Player
	Player : -String name
	Player : -int balance
	Player : -ArrayList<Card> hand
	Player : +GetBalance() int
	Player : +SetBalance() void
	Player : placeBet() int
	Player : playTurn () void

	class Game
	Game : -ArrayList<Player> players
	Game : -int pot
	Game : -int minimumBet
	Game : -int playerRaise
	Game : -Deck deck
	Game : +GetPot() int
	Game : +SetPot() void
	Game : +GetMinimumBet() int
	Game : +SetMinimumBet() void
	Game : raise ??
	Game : initializeGame() void
	Game : playRound() void
	Game : determineWinner() void
	Game : resetRound() void
	Game : leaveGame(player) void	

	Game "1" --> "1..*" Player
	Game "1" --> "1" Deck

	class Application
	Application : Game game

	Application "1" --> "1" Game
	
```
