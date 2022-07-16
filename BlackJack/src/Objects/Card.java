package Objects;

import Card_Enum.Rank;
import Card_Enum.Suit;

public class Card {
    private Rank rank;
    private final Suit suit;

    /**
     *Creates a rank R of suit S card.
     * @param r the rank of the card
     * @param s the suit of the card
     */
    Card(Rank r, Suit s){
        rank = r;
        suit = s;
    }

    /**
     * Gets the rank of the card
     * @return the rank of the card
     */
    public String getRank(){ return rank.toString(); }

    /**
     * Gets the suit of the card
     * @return the suit of the card
     */
    public String getSuit(){ return suit.toString(); }

    /**
     * Gets the face value of the card
     * @return card's face value
     */
    public int getFaceValue(){
        return this.rank.getValue();
    }

    /**
     * Ses the rank of the card to R
     */
    public void setRank(Rank r) {
        this.rank = r;
    }

    public String toString(){
        return (this.rank.toString() + this.suit.toString());
    }
}
