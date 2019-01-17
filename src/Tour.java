
public class Tour {
    private ChessBoard chessBoard; // Chess board on which the knight moves
    private Knight knightPiece; // Knight that is moved
    private boolean foundMove = false; // Flag: true if Knight has a move
    // available, otherwise false

    // ****************************************************
    // Method: Tour
    //
    // Purpose: Constructor. Receives chess board and
    // knight objects to set the members.
    // ****************************************************
    public Tour(ChessBoard chessBoard, Knight knight) {
        knightPiece = knight;
        this.chessBoard = chessBoard;
        this.chessBoard.addKnight(knightPiece);
    }

    // ****************************************************
    // Method: Tour
    //
    // Purpose: Constructor. Empty parameters.
    // Creates knight, and passes knight to
    // the chess board.
    // ****************************************************
    public Tour() {
        knightPiece = new Knight();
        chessBoard = new ChessBoard(knightPiece);
    }

    // ****************************************************
    // Method: Tour
    //
    // Purpose: Constructor. Creates a knight and passes
    // it to the chess board with the int parameter
    // that sets the dimension of the chess board.
    // ****************************************************
    public Tour(int dimension) {
        knightPiece = new Knight();
        chessBoard = new ChessBoard(knightPiece, dimension);
    }

    // ****************************************************
    // Method: Tour
    //
    // Purpose: Constructor. Creates a default chess board
    // with dimension 8 x 8 and starts the knight
    // at the square using the two int parameters.
    // ****************************************************
    public Tour(int startRow, int startCol) {
        knightPiece = new Knight(startRow, startCol);
        chessBoard = new ChessBoard(knightPiece);
    }

    // ****************************************************
    // Method: Tour
    //
    // Purpose: Constructor. Creates knight and chessboard
    // and sets knight start position, and the size
    // of the chess board.
    // ****************************************************
    public Tour(int startRow, int startCol, int dimension) {
        knightPiece = new Knight(startRow, startCol);
        chessBoard = new ChessBoard(knightPiece, dimension);
    }

    // ****************************************************
    // Method: resetTour
    //
    // Purpose: Clears the knight and chess board members
    // by pointing them to null.
    // ****************************************************
    public void resetTour() {
        knightPiece = null;
        chessBoard = null;
    }

    // ****************************************************
    // Method: setStartPosition
    //
    // Purpose: Sets the start position of
    // the knight on the chess board.
    // ****************************************************
    public void setStartPosition(int startRow, int startCol) {
        knightPiece.setCurrentRow(startRow);
        knightPiece.setCurrentCol(startCol);
        knightPiece.setPreviousRow(startRow);
        knightPiece.setPreviousCol(startCol);
    }

    // ****************************************************
    // Method: getChessBoard
    //
    // Purpose: Returns a reference to the chess board.
    // ****************************************************
    public ChessBoard getChessBoard() {
        return chessBoard;
    }

    // ****************************************************
    // Method: getKnight
    //
    // Purpose: Returns a reference to the knight player.
    // ****************************************************
    public Knight getKnight() {
        return knightPiece;
    }

    // ****************************************************
    // Method: getKnight
    //
    // Purpose: Returns a reference to the knight player.
    // ****************************************************
    public Square getSquare(int row, int col) {
        return chessBoard.getSquareAt(row, col);
    }

    // ****************************************************
    // Method: hasMove
    //
    // Purpose: Returns boolean class member flag, which
    // is true if the knight has an available move,
    // and false if the knight does not have a move.
    // If the knight does not have a move, then
    // the tour is obviously finished.
    // ****************************************************
    public boolean hasMove() {
        return foundMove;
    }

    // ****************************************************
    // Method: playGame
    //
    // Purpose: Plays the knight's tour game, and outputs
    // the board to the console.
    // ****************************************************
    public void playGame() {
        do {
            move();

        } while (foundMove == true);

        System.out.println("Game Board");
        chessBoard.showGameBoard();

        System.out.printf("Number of moves: %d\n", knightPiece.getMoveCounter());

        System.out.println();
    }

    // ****************************************************
    // Method: move
    //
    // Purpose: Moves the knight based on the underlying
    // chess board heuristics.
    // ****************************************************
    public void move() {
        int numPossibleMoves, bestMove;
        int[] possibleMoves;

        foundMove = false;
        // Mark visited status and move number on square at current position
        chessBoard.markBoardSquare(knightPiece.getCurrentRow(),
                knightPiece.getCurrentCol(), knightPiece.getMoveCounter());

        // Lower accessibility values of surrounding squares
        chessBoard.lowerAccessibility();

        // Find the number of possible moves from the current position
        numPossibleMoves = knightPiece.findNumOfPossibleMoves(chessBoard);

        // If the number of possible moves is greater than zero, there there is
        // at least one possible move
        if (numPossibleMoves > 0) {
            foundMove = true;

            // Find the possible moves from the current position and return into
            // an array
            possibleMoves = knightPiece.findPossibleMoves(chessBoard,
                    numPossibleMoves);
            System.out.println(possibleMoves.length);

            // Using the array of possible moves, find the best move based on
            // the accessibility heuristic
            bestMove = knightPiece.findBestMove(chessBoard, possibleMoves);

            // Move the knight to the best move
            for(int i=1;i<numPossibleMoves-1;i++){
                System.out.println(possibleMoves[i]);
            }
            knightPiece.move(bestMove);
        }
    }
}