package BlackJack;

import Objects.*;

public class BlackJack {

    private final Deck deck;
    protected final Player player;
    protected final Dealer dealer;

    /**
     * Constructor initializes the deck, player, dealer, and UI. Also constructs the deck
     */
    public BlackJack(){
        deck = new Deck();
        player = new Player();
        dealer = new Dealer();

        deck.constructDeck();
    }

    /**
     * Starts the player with two random cards
     */
    public void playerStartingHand(){
        addCard(player);
        addCard(player);

        //if player gets a 22, change to 12 (two aces)
        if(player.hasBusted()){
            player.changeAceValue();
        }
    }

    /**
     * Starts the dealer with two random cards
     */
    public void dealerStartingHand(){
        addCard(dealer);
        addCard(dealer);

        //if dealer gets a 22, change to 12
        if(dealer.hasBusted()){
            dealer.changeAceValue();

            //First ace becomes ace_one. make this card the hidden (2nd) card. show the first card with a value of 11
            Card temp = dealer.getHand().get(0);
            dealer.getHand().set(0, //swap cards
                    dealer.getHand().get(1));
            dealer.getHand().set(1, temp);
        }
    }

    /**
     * Deals one card to the player
     * @param player the player is being dealt the card
     */
    public void addCard(Player player){
        Card dealtCard = deck.dealCard();
        player.addCard(dealtCard);

        //In the case where an ace makes an overflow, change ace value to 1
        if(player.hasBusted()){
            player.changeAceValue();
        }
    }

    /**
     * Deals one card to the dealer
     * @param dealer the dealer is being dealt the card
     */
    public void addCard(Dealer dealer){
        Card dealtCard = deck.dealCard();
        dealer.addCard(dealtCard);

        //In the case where an ace makes an overflow, change ace value to 1
        if(dealer.hasBusted()){
            dealer.changeAceValue();
        }
    }

    /**
     * Compares the player's hand to the dealer's hand
     * @param player the player
     * @param dealer the dealer
     * @return the difference between their hands
     */
    public int compareScores(Player player, Dealer dealer){
        return player.getSum() - dealer.getSum();
    }

    /**
     * The scenario where the player wins! They get double the money they bet. Restart game
     */
    public void winScenario(){
        //update player's bet
        System.out.println("You WIN!");
        //Winning = get double of what the user bets
        player.setMoney(player.getMoney() + player.getBetAmount()*2);
        restartGame();
    }

    /**
     * The scenario where the player loses. Restart game
     */
    public void loseScenario(){
        System.out.println("You LOSE!");
        //Player does not get any money returned
        restartGame();
    }

    /**
     * The condition where the player draws with the dealer. Money is returned. Restart the game
     */
    public void drawScenario(){
        System.out.println("Draw!");
        //Player is returned their money
        player.setMoney(player.getMoney() + player.getBetAmount());
        restartGame();

    }

    /**
     * Discard both player's hands, reshuffles the deck. Initializes draw phase again
     */
    public void restartGame(){
        //clear both player's hands
        player.resetHand();
        dealer.resetHand();

        //reshuffles deck
        deck.getDeck().clear();
        deck.constructDeck();
    }


}
