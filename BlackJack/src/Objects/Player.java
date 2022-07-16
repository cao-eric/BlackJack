package Objects;

import java.util.ArrayList;

public class Player extends Person{
    private int money;
    private int betAmount;

    /**
     * Constructor for the player. Start with a sum of 0, no cards, and $10k
     */
    public Player(){
        sum = 0;
        hand = new ArrayList<>();
        money = 10000;
    }

    /**
     * Gets the amount of money the player has
     * @return the player's money
     */
    public int getMoney() { return money; }

    /**
     * Sets the player's money to m
     * @param m the amount the player's money is set to
     */
    public void setMoney(int m) { this.money = m; }

    /**
     * Gets the amount of money the player bet
     * @return the amount of money the player bet
     */
    public int getBetAmount() { return betAmount; }

    /**
     * Set the bet amount and deduct it from the player's money
     * @param bet the amount the user bet
     */
    public void setPlayerBet(int bet){
        betAmount = bet;
        setMoney(getMoney()-betAmount); //deduct bet from player's total
    }

    /**
     * checks if the player has exactly 21
     * @return true if player has 21, else false
     */
    public boolean hasSumOf21(){ return (getSum() == 21); }

}
