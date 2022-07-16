package Objects;

import Card_Enum.Rank;
import Card_Enum.Suit;
import java.util.ArrayList;

public class Deck {
    private final ArrayList<Card> deck;

    /**
     * Constructor for deck, initializes array list of cards
     */
    public Deck(){
        deck = new ArrayList<>();
    }

    /**
     * Gets the deck / array list of cards
     * @return the deck of 52 cards
     */
    public ArrayList<Card> getDeck(){
        return deck;
    }

    /**
     * Creates a deck of 52 cards
     */
    public void constructDeck() {
        for(Rank r : Rank.values()){ //iterates through rank enum
            for(Suit s : Suit.values()){ //iterates through suit enum
                Card card = new Card(r, s); //creates a card with a unique rank and suit
                this.deck.add(card); //add card to the deck
            }
        }
    }

    /**
     * Generates a random number
     * @return a random number between [0,52)
     */
    private int generateRandomNumber(){
        return (int)((Math.random()*(deck.size()-4)) + 4);
    }

    /**
     * Removes a random card from the deck and returns it
     * @return a randomly selected card
     */
    public Card dealCard(){
        return deck.remove(this.generateRandomNumber()); //remove a random card from the array list
    }
}
