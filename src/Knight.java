public class Knight {
    private int[] horizontalMovesAvail; // Horizontal move values that are
    // available to the knight
    private int[] verticalMovesAvail; // Vertical move values that are available
    // to the knight
    private int currentRow; // Current row location of the knight
    private int currentCol; // Current column location of the knight
    private int previousRow; // Row the knight was previously on before moving
    private int previousCol; // Column the knight was previously on before
    // moving
    private int moveCounter; // Keeps track of how many moves the knight has
    // made (1 - 64)
    public static final int NUM_ALLOWED_MOVES = 8; // Number of moves the knight
    // is allowed to make

    // ************************************************
    // Method: Knight()
    //
    // Purpose: Constructor
    // ************************************************
    public Knight() {
        horizontalMovesAvail = new int[NUM_ALLOWED_MOVES];
        verticalMovesAvail = new int[NUM_ALLOWED_MOVES];
        moveCounter = 1;
        setCurrentRow(0);
        setCurrentCol(0);
        setPreviousRow(0);
        setPreviousCol(0);
        fillMoves();
    }

    // ************************************************
    // Method: Knight()
    //
    // Purpose: Constructor
    // ************************************************
    public Knight(int startRow, int startCol) {
        horizontalMovesAvail = new int[NUM_ALLOWED_MOVES];
        verticalMovesAvail = new int[NUM_ALLOWED_MOVES];
        moveCounter = 1;
        setCurrentRow(startRow);
        setCurrentCol(startCol);
        setPreviousRow(startRow);
        setPreviousCol(startCol);
        fillMoves();
    }

    // ************************************************
    // Method: move()
    //
    // Purpose: Makes a move with the knight piece.
    // Sets the previous row and column, and
    // increments the move counter.
    // ************************************************
    public void move(int moveNumber) {
        if (moveNumber < 0 || moveNumber > NUM_ALLOWED_MOVES - 1) {
            System.out.println("ERROR: Invalid move: " + moveNumber);
            return;
        } else {
            setPreviousRow(currentRow);
            setPreviousCol(currentCol);
            setCurrentRow(currentRow + verticalMovesAvail[moveNumber]);
            setCurrentCol(currentCol + horizontalMovesAvail[moveNumber]);
            incMoveCounter();
        }
    }

    // ************************************************
    // Method: findNumOfPossibleMoves(ChessBoard)
    //
    // Purpose: Finds the number of possible moves on
    // the game board from the starting row
    // and column supplied by the parameters.
    // ************************************************
    public int findNumOfPossibleMoves(ChessBoard chessBoard) {
        int possibleMoveCounter = 0, // Counts the number of possible moves
                startRow = getCurrentRow(), startCol = getCurrentCol(), testRow, testCol;

        // Adds a move number value to the knight's current row and tests
        // whether it is a move candidate.
        // if the location is on the game board and unvisited, the possible move
        // counter is incremented.
        for (int moveNum = 0; moveNum < Knight.NUM_ALLOWED_MOVES; moveNum++) {
            testRow = startRow + getVerticalMoveValue(moveNum);
            testCol = startCol + getHorizontalMoveValue(moveNum);

            if (chessBoard.checkSquareExistsAndIsUnVisted(testRow, testCol) == true) {
                possibleMoveCounter++;
            }
        }

        return possibleMoveCounter;
    }

    // ************************************************
    // Method: findPossibleMoves(ChessBoard, int)
    //
    // Purpose: Finds the moves on the game board from
    // the knight's current position, and returns
    // an array of the possible moves.
    // ************************************************
    public int[] findPossibleMoves(ChessBoard chessBoard, int numPossibleMoves) {
        int[] possibleMoves = new int[numPossibleMoves];
        int storeCurRow = getCurrentRow(), // Stores the knight's current row
                storeCurCol = getCurrentCol(), // Stores the knight's current column
                testRow, // Knight's current row plus a move number value
                testCol, // Knight's current column plus a move number value
                goodMoveCount = 0; // Counts the number of actual moves

        // Adds a move number value to the knight's current row and tests
        // whether it is a move candidate.
        // if the location is on the game board and unvisited, it is added to
        // the array of possible moves.
        for (int moveNum = 0; moveNum < Knight.NUM_ALLOWED_MOVES; moveNum++) {
            testRow = storeCurRow + getVerticalMoveValue(moveNum);
            testCol = storeCurCol + getHorizontalMoveValue(moveNum);

            if (chessBoard.checkSquareExistsAndIsUnVisted(testRow, testCol) == true) {
                possibleMoves[goodMoveCount++] = moveNum;
            }
        }

        return possibleMoves;
    }

    // ************************************************
    // Method: findBestMove(ChessBoard, int[])
    //
    // Purpose: Finds the moves on the game board from
    // the knight's current position, and returns
    // an array of the possible moves.
    // ************************************************
    public int findBestMove(ChessBoard chessBoard, int[] possibleMoves) {
        int storeCurRow = getCurrentRow(), // Stores the knight's current row
                storeCurCol = getCurrentCol(), // Stores the knight's current column
                testRow, // Knight's current row plus a move number value
                testCol, // Knight's current column plus a move number value
                lowestAccessibility, testAccessibility, moveNumWithLowest;

        // If the array of possible moves is greater than one, then there are at
        // least
        // two moves to compare accessibility with.
        if (possibleMoves.length > 1) {
            // Give iLowestAccessibility a starting value to compare the rest to
            testRow = storeCurRow + getVerticalMoveValue(possibleMoves[0]);
            testCol = storeCurCol + getHorizontalMoveValue(possibleMoves[0]);
            lowestAccessibility = chessBoard.getSquareAccessibility(testRow,
                    testCol);
            moveNumWithLowest = possibleMoves[0];

            // Test each move in the array against the lowest value
            /*
             * Change 11/12/2011: iMoveNum in loop header initial value changed
             * from 0 to 1 Reason: The initial lowest value is set to move 0, if
             * iMoveNum was left at 0, then it is unnecessarily comparing it in
             * the loop
             */
            for (int moveNum = 1; moveNum < possibleMoves.length; moveNum++) {
                testRow = storeCurRow
                        + getVerticalMoveValue(possibleMoves[moveNum]);
                testCol = storeCurCol
                        + getHorizontalMoveValue(possibleMoves[moveNum]);

                testAccessibility = chessBoard.getSquareAccessibility(testRow,
                        testCol);

                // If the tested value is lower than the current lowest value
                // store the accessibility value and store the move number with
                // the
                // lowest value found in that array position

                if (testAccessibility < lowestAccessibility
                        || lowestAccessibility < 1) {
                    lowestAccessibility = testAccessibility;
                    moveNumWithLowest = possibleMoves[moveNum];
                }
            }

            return moveNumWithLowest;
        }
        // Return the first value in the array if the array length is 1, because
        // there are
        // no other moves to compare with
        else
            return possibleMoves[0];
    }

    // ************************************************
    // Method: setCurrentRow()
    //
    // Purpose: Receives an int value and sets the
    // current row.
    // ************************************************
    public void setCurrentRow(int row) {
        currentRow = row;
    }

    // ************************************************
    // Method: getCurrentRow()
    //
    // Purpose: Returns the value of the current row.
    // ************************************************
    public int getCurrentRow() {
        return currentRow;
    }

    // ************************************************
    // Method: setCurrentCol(int)
    //
    // Purpose: Receives and int value and sets the
    // current column.
    // ************************************************
    public void setCurrentCol(int col) {
        currentCol = col;
    }

    // ************************************************
    // Method: getCurrentCol()
    //
    // Purpose: Returns the value of the current column.
    // ************************************************
    public int getCurrentCol() {
        return currentCol;
    }

    // ************************************************
    // Method: setPreviousRow(int)
    //
    // Purpose: Receives an int value and sets the
    // previous row.
    // ************************************************
    public void setPreviousRow(int row) {
        previousRow = row;
    }

    // ************************************************
    // Method: getPreviousRow()
    //
    // Purpose: Returns the value of the previous row.
    // ************************************************
    public int getPreviousRow() {
        return previousRow;
    }

    // ************************************************
    // Method: setPreviousCol(int)
    //
    // Purpose: Receives an int value and sets the
    // previous column.
    // ************************************************
    public void setPreviousCol(int col) {
        previousCol = col;
    }

    // ************************************************
    // Method: getPreviousCol()
    //
    // Purpose: Returns the value of the previous column.
    // ************************************************
    public int getPreviousCol() {
        return previousCol;
    }

    // ************************************************
    // Method: incMoveCounter()
    //
    // Purpose: Increments the move counter member
    // variable by 1.
    // ************************************************
    public void incMoveCounter() {
        moveCounter++;
    }

    // ************************************************
    // Method: getMoveCounter()
    //
    // Purpose: Returns the value of the move counter.
    // ************************************************
    public int getMoveCounter() {
        return moveCounter;
    }

    // ************************************************
    // Method: getHorizontalMoveValue()
    //
    // Purpose: Receives a move number and returns the
    // value of the horizontal movement.
    // ************************************************
    public int getHorizontalMoveValue(int moveNumber) {
        return horizontalMovesAvail[moveNumber];
    }

    // ************************************************
    // Method: getVerticalMoveNumber()
    //
    // Purpose: Receives a move number and returns the
    // value of the vertical movement.
    // ************************************************
    public int getVerticalMoveValue(int moveNumber) {
        return verticalMovesAvail[moveNumber];
    }

    // ************************************************
    // Method: fillMoves()
    //
    // Purpose: Maps the possible horizontal and
    // vertical moves of the knight.
    // ************************************************
    private void fillMoves() {
        horizontalMovesAvail[0] = 2; // R
        horizontalMovesAvail[1] = 1; // R
        horizontalMovesAvail[2] = -1; // L
        horizontalMovesAvail[3] = -2; // L
        horizontalMovesAvail[4] = -2; // L
        horizontalMovesAvail[5] = -1; // L
        horizontalMovesAvail[6] = 1; // R
        horizontalMovesAvail[7] = 2; // R

        verticalMovesAvail[0] = -1; // U
        verticalMovesAvail[1] = -2; // U
        verticalMovesAvail[2] = -2; // U
        verticalMovesAvail[3] = -1; // U
        verticalMovesAvail[4] = 1; // D
        verticalMovesAvail[5] = 2; // D
        verticalMovesAvail[6] = 2; // D
        verticalMovesAvail[7] = 1; // D
    }
}