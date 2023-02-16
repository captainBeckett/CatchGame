
/** This class models the Doctor in the game. A Doctor has
 *  a position and can move to a new position.
 */
public class Doctor {

    private int row, col;
    public Object move;

    /**
     * Initializes the variables for a Doctor.
     *
     * @param theRow The row this Doctor starts at.
     * @param theCol The column this Doctor starts at.
     */
    public Doctor(int theRow, int theCol) {
        this.row = theRow;
        this.col = theCol;
    }

    /**
     * Move the Doctor. If the player clicks on one of the squares immediately
     * surrounding the Doctor, the peg is moved to that location. Clicking on
     * the Doctor does not move the peg, but instead allows the Doctor to wait
     * in place for a turn. Clicking on any other square causes the Doctor to
     * teleport to a random square (perhaps by using a �sonic screwdriver�).
     * Teleportation is completely random.
     *
     * @param newRow The row the player clicked on.
     * @param newCol The column the player clicked on.
     */
    public void move(int newRow, int newCol) {
        //move doctor to clicked square if adjacent
        if(newRow >= this.row - 1 && newRow <= this.row + 1 
            && newCol >= this.col - 1 && newCol <= this.col + 1){
                this.row = newRow;
                this.col = newCol;
        }else{
            //generate randomized new position if clicked square is not adjacent
            for(int i = 0; i <= 1; i++){
                int lowest = 1;
                int highest = 11;
                int randNum = (int)(Math.random()*(highest - lowest + 1) + lowest);
                if(i == 0){
                    this.row = randNum;
                }else if(i == 1){
                    this.col = randNum;
                }
            }
        }
    }


    /**
     * Returns the row of this Doctor.
     *
     * @return This Doctor's row.
     */
    public int getRow() {
        return this.row;
    }

    /**
     * Returns the column of this Doctor.
     *
     * @return This Doctor's column.
     */
    public int getCol() {
        return this.col;
    }

}
