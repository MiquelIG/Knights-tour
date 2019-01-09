public class Square {
    private boolean isVisited; // True if the square has been visited, otherwise
    // false
    private int moveNumber; // The current number from the accumulated number of
    // moves taken so far
    private int accessibility; // The number of possible moves from the square

    // ************************************************
    // Method: Square()
    //
    // Purpose: Constructor
    // ************************************************
    public Square() {
        isVisited = false;
        moveNumber = 0;
    }

    // ************************************************
    // Method: setVisited()
    //
    // Purpose: Receives a boolean value and sets the
    // visited attribute of the square
    // ************************************************
    public void setVisited(boolean visited) {
        isVisited = visited;
    }

    // ************************************************
    // Method: getVisited()
    //
    // Purpose: Returns the value of the visited attribute.
    // ************************************************
    public boolean isVisited() {
        return isVisited;
    }

    // ************************************************
    // Method: setMoveNumber()
    //
    // Purpose: Receives the current number of moves
    // taken by the player and marks the square
    // with the number.
    // ************************************************
    public void setMoveNumber(int moveNumber) {
        this.moveNumber = moveNumber;
    }

    // ************************************************
    // Method: getMoveNumber()
    //
    // Purpose: Returns the value of the move number
    // of the square.
    // ************************************************
    public int getMoveNumber() {
        return moveNumber;
    }

    // ************************************************
    // Method: setAccessibility()
    //
    // Purpose: Receives an int value and sets the
    // accessibility value of the square.
    // ************************************************
    public void setAccessibility(int accessibility) {
        this.accessibility = accessibility;
    }

    // ************************************************
    // Method: getAccessibility()
    //
    // Purpose: Returns the accessibility value of the
    // square.
    // ************************************************
    public int getAccessibility() {
        return accessibility;
    }

    // ************************************************
    // Method: decrAccessibility()
    //
    // Purpose: Decrements the accessibility value of
    // the square by 1.
    // ************************************************
    public void decrAccessibility() {
        accessibility--;
    }
}