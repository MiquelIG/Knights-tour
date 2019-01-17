public class ChessBoard {
    private Square[][] playingBoard; // Two dimensional array representing the
    // playing board
    private Knight currentKnight; // Reference to the knight currently on the
    // board
    private final int BOARD_SIZE; // Dimension of the board, e.g., 8 x 8

    // ************************************************
    // Method: ChessBoard(Knight)
    //
    // Purpose: Constructor
    // ************************************************
    public ChessBoard(Knight knight) {
        BOARD_SIZE = 8;
        playingBoard = new Square[BOARD_SIZE][BOARD_SIZE];
        currentKnight = knight;

        createSquares();
        createHeuristics();
    }

    // ************************************************
    // Method: ChessBoard(Knight)
    //
    // Purpose: Constructor
    // ************************************************
    public ChessBoard(Knight knight, int dimension) {
        BOARD_SIZE = dimension;
        playingBoard = new Square[BOARD_SIZE][BOARD_SIZE];
        currentKnight = knight;
        createSquares();
        createHeuristics();
    }

    // ************************************************
    // Method: createSquares()
    //
    // Purpose: Creates squares in array.
    // ************************************************
    public void createSquares() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                playingBoard[row][col] = new Square();
            }
        }
    }

    // ************************************************
    // Method: addKnight()
    //
    // Purpose: Returns the number of rows on the board.
    // ************************************************
    public void addKnight(Knight knight) {
        currentKnight = knight;
    }

    // ************************************************
    // Method: getBoardSize()
    //
    // Purpose: Returns the number of rows on the board.
    // ************************************************
    public int getBoardSize() {
        return BOARD_SIZE;
    }

    // ************************************************
    // Method: getSquareAt()
    //
    // Purpose: Returns the square object at a
    // specified row and column
    // ************************************************
    public Square getSquareAt(int row, int col) {
        return playingBoard[row][col];
    }

    // ************************************************
    // Method: setSquareVisited(int, int)
    //
    // Purpose: Sets the boolean value true on a square
    // to indicate that it has been visited.
    // ************************************************
    public void setSquareVisited(int row, int col) {
        playingBoard[row][col].setVisited(true);
    }

    // ************************************************
    // Method: isSquareVisited(int, int)
    //
    // Purpose: Returns true if the square specified
    // by the parameters has been visited.
    // ************************************************
    public boolean isSquareVisited(int row, int col) {
        return playingBoard[row][col].isVisited();
    }

    // ************************************************
    // Method: setSquareAccessibility(int, int, int)
    //
    // Purpose: Sets the accessibility level of
    // a square located at iRow and iCol
    // ************************************************
    public void setSquareAccessibility(int row, int col, int accessibility) {
        playingBoard[row][col].setAccessibility(accessibility);
    }

    // ************************************************
    // Method: getSquareAccessibility(int, int)
    //
    // Purpose: Returns the accessibility level of
    // a square located at the row and column
    // specified by the parameters
    // ************************************************
    public int getSquareAccessibility(int row, int col) {
        return playingBoard[row][col].getAccessibility();
    }

    // ************************************************
    // Method: decrSquareAccessibility(int, int)
    //
    // Purpose: Decreases the accessibility of the
    // square by 1 at the row and column
    // specified by the parameters.
    // ************************************************
    public void decrSquareAccessibility(int row, int col) {
        playingBoard[row][col].decrAccessibility();
    }

    // ************************************************
    // Method: setSquareMoveNumber(int, int, int)
    //
    // Purpose: Sets a square to the knight's current
    // move counter value at the location
    // specified by the parameters.
    // ************************************************
    public void setSquareMoveNumber(int row, int col, int moveCounter) {
        playingBoard[row][col].setMoveNumber(moveCounter);
    }

    // ************************************************
    // Method: getSquareMoveNumber(int, int)
    //
    // Purpose: Returns the move counter value at the
    // location specified by the parameters.
    // ************************************************
    public int getSquareMoveNumber(int row, int col) {
        return playingBoard[row][col].getMoveNumber();
    }

    // ************************************************
    // Method: markBoardSquare(int, int, int)
    //
    // Purpose: Marks the board square located at the
    // row and column supplied by the parameters
    // with the number of the knight's
    // move counter variable, and sets the
    // visited status to true.
    // ************************************************
    public void markBoardSquare(int curRow, int curCol, int moveCounter) {
        setSquareVisited(curRow, curCol);
        setSquareMoveNumber(curRow, curCol, moveCounter);
    }

    // ************************************************
    // Method: showGameBoard()
    //
    // Purpose: Prints the game board after all
    // possible moves have been exhausted.
    // ************************************************
    public void showGameBoard() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                System.out
                        .printf("%3d", playingBoard[row][col].getMoveNumber());
                if (col == BOARD_SIZE - 1) {
                    System.out.println();
                }
            }
        }
    }

    // ************************************************
    // Method: showHeuristics()
    //
    // Purpose: Prints the accessibility heuristics
    // ************************************************
    public void showHeuristics() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                System.out.printf("%3d", getSquareAccessibility(row, col));
                if (col == BOARD_SIZE - 1) {
                    System.out.println();
                }
            }
        }
    }

    // ************************************************
    // Method: createHeuristics(Knight)
    //
    // Purpose: Uses a dummy Knight object to play
    // through all possible moves for every
    // square on the game board.
    // The number of possible moves from each
    // square is recorded in the
    // m_aiAccessibility array.
    // ************************************************
    private void createHeuristics() {
        int linkCount = 0, // Stores the number of linking squares for a
                // particular square
                testRow, // Knight's current row plus a move number value
                testCol; // Knight's current column plus a move number value

        // Adds a move number value to the knight's current row and tests
        // whether it is a move candidate.
        // If the location is on the game board, it increments the link count
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                for (int moveNum = 0; moveNum < Knight.NUM_ALLOWED_MOVES; moveNum++) {
                    testRow = row + currentKnight.getVerticalMoveValue(moveNum);
                    testCol = col
                            + currentKnight.getHorizontalMoveValue(moveNum);

                    if (checkSquareExistsAndIsUnVisted(testRow, testCol) == true) {
                        linkCount++;
                    }
                }

                // Set accessibility and reset link count for next column
                setSquareAccessibility(row, col, linkCount);
                linkCount = 0;
            }
        }
    }

    // ************************************************
    // Method: checkSquareExistsAndIsUnVisted(int, int)
    //
    // Purpose: Uses a row, column and current instance
    // of the chess board to determine if the
    // row and column are within the bounds
    // of the chess board and has not been
    // visited. Returns true, if the space is
    // valid, otherwise returns false.
    // ************************************************
    public boolean checkSquareExistsAndIsUnVisted(int testRow, int testCol) {
        if ((testRow >= 0 && testRow < BOARD_SIZE)
                && (testCol >= 0 && testCol < BOARD_SIZE)) {
            if (isSquareVisited(testRow, testCol) == false) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    // ************************************************
    // Method: lowerAccessibility(Knight)
    //
    // Purpose: Checks all the squares that are
    // accessible from the current square
    // and reduces their accessibility by 1.
    // ************************************************
    public void lowerAccessibility() {
        int[] possibleMoves;
        int numOfPossibleMoves, curRow = currentKnight.getCurrentRow(), curCol = currentKnight
                .getCurrentCol(), changeRow, changeCol;

        // Get the number of possible moves
        numOfPossibleMoves = currentKnight.findNumOfPossibleMoves(this);

        // Create an array with the size of the number of possible moves
        possibleMoves = new int[numOfPossibleMoves];

        // Get the move numbers of the possible moves
        possibleMoves = currentKnight.findPossibleMoves(this, numOfPossibleMoves);

        for (int moveNum = 0; moveNum < possibleMoves.length; moveNum++) {
            changeRow = curRow + currentKnight.getVerticalMoveValue(possibleMoves[moveNum]);
            changeCol = curCol + currentKnight.getHorizontalMoveValue(possibleMoves[moveNum]);

            if (isSquareVisited(changeRow, changeCol) == false) {
                decrSquareAccessibility(changeRow, changeCol);
            }
        }
    }
}
