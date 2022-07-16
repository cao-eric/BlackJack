package Objects;

import java.util.ArrayList;

public class Dealer extends Person{
    public Dealer(){
        sum = 0;
        hand = new ArrayList<>();
    }

    /**
     * Dealer only shows the first card they receive
     * @return the value of dealer's first card
     */
    public int revealOneCard() { return this.hand.get(0).getFaceValue(); }

    /**
     * Checks to see if dealer has reached minimum of 17
     * @return true if the total sum of hand is 17 or greater
     */
    public boolean scoreLessThan17(){
        int MIN_SCORE = 17;

        return this.getSum() < MIN_SCORE;
    }
}
