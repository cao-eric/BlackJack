package BlackJack;

import Objects.Card;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class BlackJackController implements ActionListener {
    BlackJackUI blackJackUI;
    BlackJack blackJackGame;

    /**
     * Controller constructor initializes both the module and the UI
     */
    public BlackJackController(){
        blackJackGame = new BlackJack();
        blackJackUI = new BlackJackUI(this);
    }

    /**
     * Start the game by displaying player's money and the betting scene
     */
    public void startGame(){
        //Set the player money and betting scene on the UI
        blackJackUI.setPlayerMoneyLabel(blackJackGame.player.getMoney());
        blackJackUI.setSumLabelVisibility(false); //no sums to be displayed yet
        blackJackUI.setHitStandSceneVisibility(false); //no hit or stand button to be display
        blackJackUI.setBetSceneVisibility(true);
    }

    /**
     * Deal two cards to the player. If the player does not instantly win, deal 2 cards to the dealer. Only reveal one
     * of the dealer's cards. Afterwards, Display hitting or standing scene.
     */
    public void dealStartingCards(){
        //Swing Worker creates a thread that allows a delay between every card dealt
        SwingWorker startingCardsSwingThread = new SwingWorker() {
            @Override
            protected String doInBackground() throws Exception
            {
                //Deal the player their starting cards and update the player UI with new sum
                blackJackGame.playerStartingHand();
                addCardToPlayerUI(blackJackGame.player.getHand().get(0), 0); //1st card to 1st position
                Thread.sleep(300);
                addCardToPlayerUI(blackJackGame.player.getHand().get(1), 1); //2nd card to 2nd position
                Thread.sleep(300);
                blackJackUI.setPlayerSumLabel(blackJackGame.player.getSum());
                blackJackUI.setSumLabelVisibility(true);

                //if player draws a perfect 21 -> win
                if(blackJackGame.player.hasSumOf21()){
                    playerWins();
                }
                else{ //Deal the dealer their starting hand
                    blackJackGame.dealerStartingHand();
                    addCardToDealerUI(blackJackGame.dealer.getHand().get(0), 0); //only reveal dealer's first card
                    blackJackUI.setDealerSumLabel(blackJackGame.dealer.revealOneCard());
                    //proceed to hitting phase
                    blackJackUI.setHitStandSceneVisibility(true);
                }


                String res = "Starting hand thread finished execution";
                System.out.println(res);
                return res;
            }

        };

        startingCardsSwingThread.execute();

    }

    /**
     * Dealer has to draw cards until they reach a minimum of 17. If they bust, player wins. Else compare their hands at
     * the end.
     */
    public void dealerDrawsCards(){

        //Thread to add delay for when dealer receives cards in UI
        SwingWorker dealerDrawSwingThread = new SwingWorker() {
            @Override
            protected String doInBackground() throws Exception
            {
                //dealer reveals second card -> update UI with it
                addCardToDealerUI(blackJackGame.dealer.getHand().get(1), 1 );
                blackJackUI.setDealerSumLabel(blackJackGame.dealer.getSum());
                Thread.sleep(300);

                //while dealer has a score less than 17
                while(blackJackGame.dealer.scoreLessThan17()){
                    //deal them a card
                    blackJackGame.addCard(blackJackGame.dealer);
                    //update the UI with the most recently dealt card
                    addCardToDealerUI(blackJackGame.dealer.getHand().get(blackJackGame.dealer.getHand().size()-1),
                            blackJackGame.dealer.getHand().size()-1);
                    Thread.sleep(300);
                    blackJackUI.setDealerSumLabel(blackJackGame.dealer.getSum()); //update the UI with new dealer sum as well
                    Thread.sleep(10);
                }


                //if the dealer busts, player wins
                if(blackJackGame.dealer.hasBusted()){
                    playerWins();
                }
                else{ //else compare the sum of each others hands
                    compareHands();
                }

                String res = "Dealer drawing thread finished execution";
                System.out.println(res);
                return res;
            }

        };
        dealerDrawSwingThread.execute();

    }

    /**
     * If both the player and dealer did not bust, compare their hands to see who has the greater sum to determine the
     * outcome of the game.
     */
    public void compareHands(){
        //if score is positive, player wins, 0 if draw, negative if player loses
        int score = blackJackGame.compareScores(blackJackGame.player, blackJackGame.dealer);
        if(score > 0){
            playerWins();
        }
        else if(score < 0){
            playerLoses();
        }
        else{
            playerDraws();
        }
    }

    /**
     * Player wins, module gives player money and update the UI by displaying that. Asks if player would like to play again
     */
    public void playerWins(){
        blackJackGame.winScenario();
        ImageIcon pogchampIcon = new ImageIcon("images/pogchamp.png");
        int playAgain = blackJackUI.outcomeMessageDialog("You win: $" + (blackJackGame.player.getBetAmount()*2) +
                        "! Would you like to play again?",
                        pogchampIcon, "VICTORY");
        //playAgain is 0 if player wants to play again
        if(playAgain == 0){
            blackJackUI.clearHandDisplay(); //clear the hand panels
            startGame(); //go back to the start of the game
        }
        else{ //else close the game
            java.lang.System.exit(0);
        }
    }

    /**
     * Player lost and does not get any money. Update the UI to display the loss and ask if the player would like to play again
     */
    public void playerLoses(){
        blackJackGame.loseScenario();
        ImageIcon loseIcon = new ImageIcon("images/L.png");
        int playAgain = blackJackUI.outcomeMessageDialog("You lost: $" + blackJackGame.player.getBetAmount() +
                ". Would you like to play again?", loseIcon, "LOSS");
        //playAgain is 0 if player wants to play again
        if(playAgain == 0){
            blackJackUI.clearHandDisplay(); //clear the hand panels
            startGame(); //go back to the start of the game
        }
        else{ //else close the game
            java.lang.System.exit(0);
        }
    }

    /**
     * Player has a stand off with the dealer and is returned their money. Update the UI to display this and ask if the player would like to play again
     */
    public void playerDraws(){
        blackJackGame.drawScenario();
        int playAgain = blackJackUI.outcomeMessageDialog("Stand off! Money is returned: $" + blackJackGame.player.getBetAmount() +
                ". Would you like to play again?",
                null, "DRAW");
        //playAgain is 0 if player wants to play again
        if(playAgain == 0){
            blackJackUI.clearHandDisplay(); //clear the hand panels
            startGame(); //go back to the start of the game
        }
        else{ //else close the game
            java.lang.System.exit(0);
        }
    }

    /**
     * Calls for UI to be updated with a new card dealt to person
     * @param card the card that will appear on the UI
     * @param position is where the card will pop up on the screen
     */
    public void addCardToPlayerUI(Card card, int position){
        blackJackUI.updateHandDisplay(card.getRank(), card.getSuit(), blackJackUI.playerCards, position);
    }

    /**
     * Calls for UI to be update dealer's hand with another card
     * @param card is the card that is dealt
     * @param position the position in which the card is placed
     */
    public void addCardToDealerUI(Card card, int position){

        blackJackUI.updateHandDisplay(card.getRank(), card.getSuit(), blackJackUI.dealerCards, position);
    }

    /**
     * Used to check if the user input for bet amount is a positive integer
     * @param str the user input into the bet text field
     * @return true if the user input is a positive integer, false if contains other characters. Not concerned about overflow
     */
    public boolean stringIsInteger(String str){
        //if nothing is inputted, then not integer
        if(str == null || str.equals("")){ return false; }

        //goes through each character of the string
        for(int x = 0; x < str.length(); x++){
            if(!java.lang.Character.isDigit(str.charAt(x))){ //checks if the character is a digit, if not -> false
                return false;
            }
        }
        //otherwise each character is a digit and the string can be parsed into an integer
        return true;
    }

    /**
     * Is called when a user presses a button on the UI. Execute the necessary actions afterwards.
     * @param e the button that is pressed.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        //IF USER PRESSES SUBMIT BET BUTTON
        if(e.getSource() == blackJackUI.betSubmitButton){

            //Check if input is a valid integer
            if(stringIsInteger(blackJackUI.betTextField.getText())){
                //Get bet input and clear text field
                try{
                    int betAmount = Integer.parseInt(blackJackUI.betTextField.getText());
                    blackJackUI.betTextField.setText("");

                    //Send the bet amount to module and update player's money on UI
                    blackJackGame.player.setPlayerBet(betAmount);
                    blackJackUI.setPlayerMoneyLabel(blackJackGame.player.getMoney());

                    //Turn off betting scene and deal cards
                    blackJackUI.setBetSceneVisibility(false);
                    dealStartingCards();
                }
                catch(Exception exception){ //catches overflow error
                    System.out.println("Input overflow error" + exception);
                    blackJackUI.warningMessageDialog("INPUT WAS NOT A VALID NUMBER. TRY AGAIN.", "Input Error");
                    blackJackUI.betTextField.setText(""); //clear the text box
                }

            }
            else{ //WHEN A INVALID POSITIVE INTEGER IS ENTERED
                //display warning message
                blackJackUI.warningMessageDialog("INPUT WAS NOT A VALID NUMBER. TRY AGAIN.", "Input Error");
                blackJackUI.betTextField.setText(""); //clear the text box
            }
        }

        //IF USER PRESSES HIT BUTTON
        if(e.getSource() == blackJackUI.hitButton){
            //Add a card to the player's hand
            blackJackGame.addCard(blackJackGame.player);

            //Update their score and hand in the UI
            blackJackUI.setPlayerSumLabel(blackJackGame.player.getSum());
            addCardToPlayerUI(blackJackGame.player.getHand().get(blackJackGame.player.getHand().size()-1), //the most recently added card
                    blackJackGame.player.getHand().size()-1); //the position of the most recently added card

            //checkWinCondition (player hits 21)
            //if player hits an exact 21, STOP!
            if(blackJackGame.player.hasSumOf21()){
                blackJackUI.setHitStandSceneVisibility(false);
                //dealer draws their cards
                dealerDrawsCards();
            }
            else if(blackJackGame.player.hasBusted()){ //else if the player busts (over 21)
                playerLoses();
            }
        }

        //IF USER PRESSES STAND BUTTON
        if(e.getSource() == blackJackUI.standButton){
            //update UI by taking out hit and stand buttons
            blackJackUI.setHitStandSceneVisibility(false);
            //dealer draws their cards
            dealerDrawsCards();
        }

    }

    public static void main(String[] args){
        BlackJackController controller = new BlackJackController();
        controller.startGame();
    }

}
