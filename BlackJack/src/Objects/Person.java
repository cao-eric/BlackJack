package Objects;

import Card_Enum.Rank;

import java.util.ArrayList;

public abstract class Person{
    protected int sum;
    protected ArrayList<Card> hand;

    /**
     * Constructor for player, starts with an empty hand
     */
    public Person(){
        sum = 0;
        hand = new ArrayList<>();
    }

    /**
     * Gets the sum of the player's hand
     * @return the sum of player's hand
     */
    public int getSum() { return sum; }

    /**
     * Gets the hand of cards of the player
     * @return player's hand
     */
    public ArrayList<Card> getHand() { return hand; }

    /**
     * Adds a card to the person's hand, increasing their sum
     * @param c the card added to the player's hand
     */
    public void addCard(Card c){
        hand.add(c); //card gets added to hand
        sum += c.getFaceValue(); //person's total sum increases by face value of the card
    }

    /**
     * Checks for busts (score over 21)
     * @return true if the person
     */
    public boolean hasBusted() { return this.getSum() > 21; }

    /**
     * Changes ace rank value from 11 to 1 in case of overflow
     */
    public void changeAceValue(){
        for(Card c : hand){
            if(c.getFaceValue() == 11){ //if the card is an ace with a face value of 11
                c.setRank(Rank.ACE_ONE); //set its rank/face value to 1
                sum -= 10; //sum of cards deducted by 10 (Ace 11 -> Ace 1)
                break; //get out of loop
            }
        }
    }

    /**
     * Players' hands are cleared and sum is reset to 0
     */
    public void resetHand(){
        hand.clear();
        sum = 0;
    }
}
