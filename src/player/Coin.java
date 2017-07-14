package player;

/**
 * Player.java - Class for defining a player in the pokemon game
 *
 * @author Céline Mikiël Yohann
 * @version 1.0
 */
public class Coin {

    /**
     * Enum for the Coin's face
     */
    private enum CoinFace {

        HEAD(0), TAIL(1);
        private int value;

        CoinFace(int coinVal) {
            value = coinVal;
        }

    }

    private CoinFace faceUp;

    /**
     * Default Contructor - will flip coin
     */
    public Coin() {
        flip();
    }

    /**
     * Method to get coin's face up
     *
     * @return String representation of face up
     */
    public String getFaceUp() {
        return faceUp.name();
    }

    /**
     * Method to check if face up is head
     *
     * @return True if face up is head, False otherwise
     */
    public boolean isHead() {
        return faceUp == CoinFace.HEAD;
    }

    /**
     * Method to flip the coin
     */
    public void flip() {
        faceUp = CoinFace.values()[(int) (Math.random() * 2)];
        System.out.println("faceup" + faceUp);
    }

}
