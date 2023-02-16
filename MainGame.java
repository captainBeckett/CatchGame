/** This class creates a game and starts the game play.
 */
public class MainGame {

    public static void main(String args[]) {
        //rerun game after game over
        while(true){        
            CatchGame game = new CatchGame();
            game.playGame();
        }
    }

}
