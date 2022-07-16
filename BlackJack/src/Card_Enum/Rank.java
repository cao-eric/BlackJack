package Card_Enum;

public enum Rank {

    /**
     * Name of the ranks followed by their value
     * ONE is a special value for aces
     */
    ACE_ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5),
    SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10),
    JACK(10), QUEEN(10), KING(10), ACE(11);

    private int value;

    /**
     * The ranks of a card
     * @param value the value of the card rank
     */
    Rank(int value){
        this.value = value;
    }


    /**
     * Gets value of the rank
     * @return the value of the rank
     */
    public int getValue(){
        return this.value;
    }
}
