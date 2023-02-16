
/** This class manages the interactions between the different pieces of
 *  the game: the Board, the Daleks, and the Doctor. It determines when
 *  the game is over and whether the Doctor won or lost.
 */
public class CatchGame {

    /**
     * Instance variables go up here
     * Make sure to create a Board, 3 Daleks, and a Doctor
     */
    private Board gameBoard;
    private Doctor doc;
    private Dalek dalek1;
    private Dalek dalek2;
    private Dalek dalek3;
    private Dalek[] daleks;
    private boolean hasDoctorCrashed;
    private int[] randNums = new int[8];

    /**
     * The constructor for the game.
     * Use it to initialize your game variables.
     * (create people, set positions, etc.)
     */
    public CatchGame(){
        //generate random starting positions on gameBoard
        for(int i = 0; i <= 7; i++){
            int lowest = 1;
            int highest = 11;
            int randNum = (int)(Math.random()*(highest - lowest + 1) + lowest);
            //add random number to array
            //make sure no number appears twice in a row to gurantee different positions on gameBoard
            if(i != 0){
                if(randNum == randNums[i-1]){
                    i--;
                }else{
                    randNums[i] = randNum;
                }
            }else{
                randNums[i] = randNum;
            }
        }
        this.gameBoard = new Board(12,12);
        this.doc = new Doctor(randNums[0], randNums[1]);
        this.dalek1 = new Dalek(randNums[2], randNums[3]);
        this.dalek2 = new Dalek(randNums[4], randNums[5]);
        this.dalek3 = new Dalek(randNums[6], randNums[7]);
        this.daleks = new Dalek[3];
        this.hasDoctorCrashed = false;
    }

    //method that returns true if all three daleks have crashed
    public boolean haveDaleksCrashed(){
        if(this.dalek1.hasCrashed() && this.dalek2.hasCrashed() && this.dalek3.hasCrashed()){
            return true;
        }else{
            return false;
        }
    }

    /**
     * The playGame method begins and controls a game: deals with when the user
     * selects a square, when the Daleks move, when the game is won/lost.
     */
    public void playGame() {
        // insert daleks into array
        this.daleks[0] = this.dalek1;
        this.daleks[1] = this.dalek2;
        this.daleks[2] = this.dalek3;

        //draw doctor's starting position
        this.gameBoard.putPeg(Board.GREEN, this.doc.getRow(), this.doc.getCol());
        //draw daleks' starting positions
        for(int i = 0; i < 3; i++){
            this.gameBoard.putPeg(Board.BLACK, this.daleks[i].getRow(), this.daleks[i].getCol());
        }

        //make sure no crashes are present to start the game
        

        //game logic
        //movement of doctor and daleks if game is still in play
        while(hasDoctorCrashed == false && haveDaleksCrashed() == false){
            //determine the board position that the user has clicked on
            Coordinate click = this.gameBoard.getClick();
            int row = click.getRow();
            int col = click.getCol();

            //draw new doctor position on board    
            this.gameBoard.removePeg(this.doc.getRow(), this.doc.getCol());
            this.doc.move(row, col);
            this.gameBoard.putPeg(Board.GREEN, this.doc.getRow(), this.doc.getCol());

            //move daleks towards doctor
            for(int i = 0; i < 3; i++){
                this.gameBoard.removePeg(this.daleks[i].getRow(), this.daleks[i].getCol());
                this.daleks[i].advanceTowards(doc);
            }

            //draw daleks' new positions
            /*this for loop comes after the previous for loop to avoid removing the peg of an advanced dalek if it lands on the old position of another dalek 
             *different subsequent for loops will solve this glitch
            */
            for(int i = 0; i < 3; i++){
                this.gameBoard.putPeg(Board.BLACK, this.daleks[i].getRow(), this.daleks[i].getCol());
            }

            //determine if doctor has crashed
            for(int i = 0; i < 3; i++){
                if(this.daleks[i].getRow() == this.doc.getRow() && this.daleks[i].getCol() == this.doc.getCol()){
                    this.hasDoctorCrashed = true;
                    this.daleks[i].crash();
                    this.gameBoard.removePeg(this.daleks[i].getRow(), this.daleks[i].getCol());
                }
            }

            //determine if daleks crashed into each other
            //all three daleks crashed into each other
            if(this.dalek1.getRow() == this.dalek2.getRow() && this.dalek1.getCol() == this.dalek2.getCol() 
                && this.dalek1.getRow() == this.dalek3.getRow() && this.dalek1.getCol() == this.dalek3.getCol()
                && this.dalek2.getRow() == this.dalek3.getRow() && this.dalek2.getCol() == this.dalek3.getCol()
            ){
                this.dalek1.crash();
                this.dalek2.crash();
                this.dalek3.crash();
            //dalek 1 and 2 crashed into each other    
            }else if(this.dalek1.getRow() == this.dalek2.getRow() && this.dalek1.getCol() == this.dalek2.getCol()){
                this.dalek1.crash();
                this.dalek2.crash();
            //dalek 1 and 3 crashed into each other
            }else if(this.dalek1.getRow() == this.dalek3.getRow() && this.dalek1.getCol() == this.dalek3.getCol()){
                this.dalek1.crash();
                this.dalek3.crash();
            //dalek 2 and 3 crashed into each other    
            }else if(this.dalek2.getRow() == this.dalek3.getRow() && this.dalek2.getCol() == this.dalek3.getCol()){
                this.dalek2.crash();
                this.dalek3.crash();
            }

            //change dalek to red if crashed
            for(int i = 0; i < 3; i++){
                if(this.daleks[i].hasCrashed()){
                    this.gameBoard.putPeg(Board.RED, this.daleks[i].getRow(), this.daleks[i].getCol());            
                }
            }
        
        }


        //if doctor crashed, change pin color to yellow and end game
        if(hasDoctorCrashed == true){
            this.gameBoard.removePeg(this.doc.getRow(), this.doc.getCol());
            this.gameBoard.putPeg(Board.YELLOW, this.doc.getRow(), this.doc.getCol());
            this.gameBoard.displayMessage("You lose! Click anywhere on the board to play again.");
        }else if(haveDaleksCrashed() == true){
            //if all three daleks have crashed, end game
            this.gameBoard.displayMessage("You win! Click anywhere on the board to play again.");
        }
        
        //restart game if player clicks on board after game is over
        Coordinate restartGame = this.gameBoard.getClick();
    }
}
