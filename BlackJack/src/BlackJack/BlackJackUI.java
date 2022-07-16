package BlackJack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class BlackJackUI implements ActionListener {
    JFrame frame;
    JPanel playerHandPanel, dealerHandPanel;
    JButton hitButton, standButton, betSubmitButton;
    JLabel[] playerCards, dealerCards;
    JLabel betLabel, playerSumLabel, dealerSumLabel, playerMoneyLabel;
    JTextField betTextField;
    ActionListener actionListener;

    /**
     * Initializes the fame - dimensions, color, and other attributes
     */
    private void createFrame(){
        frame = new JFrame("Black Jack");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //closes when window is closed
        frame.setSize(900, 750); //width x height of frame
        frame.getContentPane().setBackground(new Color(17, 153, 2)); //sets background of the frame to green
        frame.setLocationRelativeTo(null);
        frame.setLayout(null); //no layout
    }

    /**
     * Initializes the panels in which the cards will be held.
     */
    private void createHandPanels(){
        dealerHandPanel = new JPanel();
        dealerHandPanel.setBounds(100, 100, 700, 150);
        dealerHandPanel.setLayout(new GridLayout(1, 6, 10, 10));
        dealerHandPanel.setBackground(new Color(17, 153, 2)); //sets background of the panel to match frame
        frame.add(dealerHandPanel);

        playerHandPanel = new JPanel();
        playerHandPanel.setBounds(100, 450, 700, 150);
        playerHandPanel.setLayout(new GridLayout(1, 6, 10, 10));
        playerHandPanel.setBackground(new Color(17, 153, 2)); //sets background of the panel to match frame's green
        frame.add(playerHandPanel);
    }

    /**
     * Creates labels to hold and display both the player and dealer's hand sums
     */
    private void createSumLabels(){
        playerSumLabel = new JLabel("Player Hand Total: ");
        playerSumLabel.setBounds(25, 640, 300, 70);
        playerSumLabel.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        playerSumLabel.setVisible(false);
        frame.add(playerSumLabel);

        dealerSumLabel = new JLabel("Dealer Hand Total: ");
        dealerSumLabel.setBounds(25, 20, 300, 70);
        dealerSumLabel.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        dealerSumLabel.setVisible(false);
        frame.add(dealerSumLabel);
    }

    /**
     * Allow the user to see the sum of their hand and the dealer's hand
     * @param visibility true to allow user to see the sum, false to hide the sums
     */
    public void setSumLabelVisibility(boolean visibility){
        playerSumLabel.setVisible(visibility);
        dealerSumLabel.setVisible(visibility);
    }

    /**
     * Creates a label to display the player's money
     */
    private void createMoneyLabel(){
        playerMoneyLabel = new JLabel("Money: $");
        playerMoneyLabel.setBounds(600, 640, 300, 70);
        playerMoneyLabel.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        frame.add(playerMoneyLabel);
    }

    /**
     * Sets the sum of the player's hand for display
     * @param playerSum the sum of the player's hand
     */
    public void setPlayerSumLabel(int playerSum){
        playerSumLabel.setText("Player Hand Total: " + playerSum);
    }

    /**
     * Sets the sum of the dealer's hand for display
     * @param dealerSum the sum of the dealer's hand
     */
    public void setDealerSumLabel(int dealerSum){
        dealerSumLabel.setText("Dealer Hand Total: " + dealerSum);
    }

    /**
     * Updates the player's money label
     * @param playerMoney the amount of money the player has
     */
    public void setPlayerMoneyLabel(int playerMoney){
        playerMoneyLabel.setText("Money: $" + playerMoney);
    }

    /**
     * Initializes a set of labels (to hold images of cards) for both dealer and player's hands
     */
    private void createCardLabels(){
        playerCards = new JLabel[6];
        dealerCards = new JLabel[6];
        for(int x = 0; x < playerCards.length; x++){
            playerCards[x] = new JLabel();
            dealerCards[x] = new JLabel();

            playerHandPanel.add(playerCards[x]);
            dealerHandPanel.add(dealerCards[x]);
        }



    }

    /**
     * Creates the label that says "Enter bet amount: "
     */
    private void createBetLabel(){
        betLabel = new JLabel("Enter bet amount:");
        betLabel.setBounds(125, 300, 300, 70);
        betLabel.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        betLabel.setVisible(false);
        frame.add(betLabel);
    }

    /**
     * Creates the text field to hold bet input
     */
    private void createBetTextField(){
        betTextField = new JTextField(10);
        betTextField.setBounds(350, 300, 200, 70);
        betTextField.setFont(new Font("Times New Roman", Font.BOLD, 30));
        betTextField.setVisible(false);
        frame.add(betTextField);
    }

    /**
     * Creates the submit button to submit bet input
     */
    private void createBetSubmitButton(){
        betSubmitButton = new JButton("SUBMIT");
        betSubmitButton.setFocusable(false);
        betSubmitButton.addActionListener(actionListener);
        betSubmitButton.setBounds(570, 300, 120, 70);
        betSubmitButton.setVisible(false);
        frame.add(betSubmitButton);
    }

    /**
     * Creates the bet scene (bet label, text field, and button). IS NOT VISIBLE
     */
    private void createBetScene(){
        createBetLabel();
        createBetTextField();
        createBetSubmitButton();
    }

    /**
     * Makes the betting scene (bet label, text field, and button) visible to the user
     * @param visibility true will make the scene visible, false will turn it off
     */
    public void setBetSceneVisibility(boolean visibility){
        betLabel.setVisible(visibility);
        betTextField.setVisible(visibility);
        betSubmitButton.setVisible(visibility);
    }

    /**
     * Initializes the hit and stand buttons: text, actionListener, and position/size
     */
    private void createHitStandButtons(){
        hitButton = new JButton("HIT");
        hitButton.setFocusable(false);
        hitButton.addActionListener(actionListener);
        hitButton.setBounds(250, 300, 100, 70);
        hitButton.setVisible(false);
        frame.add(hitButton);


        standButton = new JButton("STAND");
        standButton.setFocusable(false);
        standButton.addActionListener(actionListener);
        standButton.setBounds(500, 300, 100, 70);
        standButton.setVisible(false);
        frame.add(standButton);
    }

    /**
     * Sets the hit and stand buttons to be visible to the user
     * @param visibility if true then buttons are visible, else false then the buttons are invisible
     */
    public void setHitStandSceneVisibility(boolean visibility){
        hitButton.setVisible(visibility);
        standButton.setVisible(visibility);
    }

    /**
     * When a player obtains a card, add an image of the card to their hand-panel area
     * @param cardRank the name of the card's rank used for file path
     * @param cardSuit the name of the card's suit used for file path
     * @param handOfCards the array of card labels/images that needs to be updated
     * @param position the position where a card is inserted
     */
    public void updateHandDisplay(String cardRank, String cardSuit, JLabel[] handOfCards, int position){
        //Create the card UI/image
        ImageIcon cardImage = new ImageIcon("images/" + cardSuit + "/" + cardRank + ".png"); //gets the image of the card via filepath
        handOfCards[position].setIcon(cardImage);

    }

    /**
     * Clear the hand panels of the card images
     */
    public void clearHandDisplay(){
        for(int x = 0; x < playerCards.length; x++){
            playerCards[x].setIcon(null);
            dealerCards[x].setIcon(null);
        }
    }


    /**
     * Displays the outcome of the game to the user and asks if they would like to play again
     * @param message the message in the dialogue box showing the outcome of the game as well as earnings
     * @param icon the icon that is displayed in the message
     * @param outcome the title of the dialogue box
     * @return 0 if the player wants to continue the game, otherwise 1 if not
     */
    public int outcomeMessageDialog(String message, ImageIcon icon, String outcome){
         return JOptionPane.showOptionDialog(frame, message, outcome, JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, icon, null, 0);
    }

    /**
     * Creates a dialog message that warns the user of an error or misinput
     * @param message the message that will be displayed to the user
     * @param title the title of the dialog box
     */
    public void warningMessageDialog(String message, String title){
        JOptionPane.showMessageDialog(frame, message, title, JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Constructor. Creates the frame and adds the starting necessary components
     */
    public BlackJackUI(ActionListener actionListener){
        this.actionListener = actionListener;
        createFrame();

        //For hand sums and player money
        createSumLabels();
        createMoneyLabel();

        //For player and dealer's hands
        createHandPanels();
        createCardLabels();

        //For betting
        createBetScene();

        //For hitting and standing
        createHitStandButtons();

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {}
}
