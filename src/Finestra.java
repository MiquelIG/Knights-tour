import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Finestra extends JFrame {
    private Tour knightsTour; // Tour object
    private int boardSize; // Board dimension
    private GUISquare guiSquares[][]; // Array of JPanels representing squares
    private boolean isTourRunning = false; // Flag: true if tour is running,
    // false if tour is not running
    private boolean isTourFinished = false; // Flag: true if tour completed run,
    // false if tour did not finish
    private boolean useNewGameParams = true; // Flag: true if user wants new
    // game parameters, false if
    // user wants the same as
    // previous game
    private Timer timer; // Times the movement of the knight
    private final int MAX_BOARD_SIZE = 100; // Maximum board size

    // ****************************************************
    // Method: Finestra
    //
    // Purpose: Constructor. Set default close operation
    // for the window and launches a new game.
    // ****************************************************
    public Finestra() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        newGame();
        createTimer();
    }

    // ****************************************************
    // Method: newGame
    //
    // Purpose: Sets parameters for a new knight's tour game.
    // ****************************************************
    private void newGame() {
        // If the board size is greater than zero, then the board
        // must be cleared before starting again.
        if (boardSize > 0) {
            // Remove current squares from the board
            removeSquares();

            // Repaint the window
            repaint();
        }

        // If user selected to enter new game parameters
        // prompt the user for the new board size and set
        // the board size member
        if (useNewGameParams == true) {
            // Prompt user for board size and set member
            boardSize = promptBoardSize();
        }

        // If user wants new game parameter, resize window
        // otherwise leave window the same size
        if (useNewGameParams == true) {
            // Set size of window
            setSize(boardSize * 35, boardSize * 35);
        }

        // Set grid layout, rows and columns are equal to the board size,
        // with a 1 pixel gap between them (board size divided by board size =
        // 1)
        setLayout(new GridLayout(boardSize, boardSize, boardSize / boardSize,
                boardSize / boardSize));

        // Create new tour object with board size
        knightsTour = new Tour(boardSize);

        // Draw the squares in the window
        drawSquares();
    }

    // ****************************************************
    // Method: drawSquares
    //
    // Purpose: Draws the squares on the screen.
    // ****************************************************
    private void drawSquares() {
        // Create new array with size of the board
        guiSquares = new GUISquare[boardSize][boardSize];

        // Nested loop iterates through all squares
        for (int row = 0; row < guiSquares.length; row++) {
            for (int col = 0; col < guiSquares.length; col++) {
                // Create new GUISquare object
                guiSquares[row][col] = new GUISquare();

                // Add mouse GUISquare in the array
                guiSquares[row][col].addMouseListener(new SquareClickHandler());

                // Alternate square color
                setCorrectSquareColor(row, col);

                // Add square to Frame
                add(guiSquares[row][col]);
            }
        }

        // Cause container to lay out its subcomponents again.
        // It should be invoked when this container's subcomponents are modified
        // (added to or removed from the container, or layout-related
        // information changed)
        // after the container has been displayed.
        validate();
    }

    // ****************************************************
    // Method: setCorrectSquareColor
    //
    // Purpose: Determines the square color based on its
    // position on the board.
    // ****************************************************
    private void setCorrectSquareColor(int row, int col) {
        if ((row + col) % 2 == 0) {
            guiSquares[row][col].setBackground(Color.lightGray);
        } else {
            guiSquares[row][col].setBackground(Color.darkGray);
        }
    }

    // ****************************************************
    // Method: removeSquares
    //
    // Purpose: Removes squares from their container
    // ****************************************************
    private void removeSquares() {
        // Nested loop iterates through all squares
        for (int row = 0; row < guiSquares.length; row++) {
            for (int col = 0; col < guiSquares.length; col++) {
                // Call remove to remove square from Frame
                remove(guiSquares[row][col]);
            }
        }
    }

    // ****************************************************
    // Method: promptBoardSize
    //
    // Purpose: Prompts user to enter desired board size.
    // Validates input and returns a valid number.
    // ****************************************************
    private int promptBoardSize() {
        String userBoardSize; // String value to hold user input
        int boardSize = 0; // Integer value to be returned
        boolean isUserInputInvalid = true; // True indicates that the input is
        // invalid

        // Do the following while user input is invalid
        do {
            // Prompt user for input and store in String variable
            userBoardSize = JOptionPane
                    .showInputDialog("Enter an integer to set the number of rows and columns on the board:");

            // If user input is a null value, user clicked cancel or closed the
            // window,
            // so exit the program.
            if (userBoardSize == null) {
                System.exit(0);
            } else {
                try {
                    // Test if string is empty and assign boolean value to
                    // bInvalidString
                    isUserInputInvalid = userBoardSize.isEmpty();

                    // parseInt throws NumberFormatException if the String is
                    // empty or equal to ""
                    boardSize = Integer.parseInt(userBoardSize);

                    // If parseInt did not throw an exception, program will
                    // continue here.
                    // If board size is zero, string is still invalid, so
                    // display error
                    if (boardSize <= 0 || boardSize >= MAX_BOARD_SIZE) {
                        String message;

                        isUserInputInvalid = true;

                        message = boardSize <= 0 ? "You must enter a valid integer value."
                                : String.format("Board size can not exceed %d",
                                MAX_BOARD_SIZE);

                        JOptionPane.showMessageDialog(this, message,
                                "Invalid Number", JOptionPane.WARNING_MESSAGE);
                    }
                }
                // Handle NumberFormatException thrown from parseInt
                catch (NumberFormatException numberFormatException) {
                    // Display error message
                    JOptionPane.showMessageDialog(this,
                            "You must enter a valid integer value.",
                            "Invalid Number", JOptionPane.WARNING_MESSAGE);
                }
            }

        } while (isUserInputInvalid == true);

        // At this point the input is valid, so set board size and return
        boardSize = Integer.parseInt(userBoardSize);

        return boardSize;
    }

    // ****************************************************
    // Method: createTimer
    //
    // Purpose: Create action listener that performs
    // a knight's move at an interval.
    // ****************************************************
    private void createTimer() {
        int delay = 50; // Delay in milliseconds

        // Set flag indicating tour is finished to false.
        isTourFinished = false;

        // Create anonymous action listener variable and
        // override it's actionPerformed method to call
        // moveKnight
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                // Set correct square color
                setCorrectSquareColor(knightsTour.getKnight().getCurrentRow(),
                        knightsTour.getKnight().getCurrentCol());

                // Move the knight
                moveKnight();

                // Set flash color
                flashColor();
            }
        };

        // Set class timer member to a timer instance
        // and pass delay, and taskPerformer as the action listener
        timer = new Timer(delay, taskPerformer);
    }

    // ****************************************************
    // Method: flashColor
    //
    // Purpose: Changes the color of the current square.
    // ****************************************************
    private void flashColor() {
        int currentRow = knightsTour.getKnight().getCurrentRow();
        int currentCol = knightsTour.getKnight().getCurrentCol();
        GUISquare currentSquare = guiSquares[currentRow][currentCol];
        Color flashColor = Color.orange;

        currentSquare.setBackground(flashColor);
    }

    // ****************************************************
    // Method: startTimer
    //
    // Purpose: Starts the timer
    // ****************************************************
    private void startTimer() {
        timer.start();
    }

    // ****************************************************
    // Method: stopTimer
    //
    // Purpose: Stops the currently running timer
    // ****************************************************
    private void stopTimer() {
        timer.stop();
    }

    // ****************************************************
    // Method: moveKnight
    //
    // Purpose: Moves the knight, and sets the text on the
    // label of the current square
    // ****************************************************
    private void moveKnight() {
        GUISquare currentSquare; // Stores the current square (position of
        // knight)
        int currentRow; // Current row position of knight
        int currentCol; // Current column position of knight

        // Get current row and column of knight
        currentRow = knightsTour.getKnight().getCurrentRow();
        currentCol = knightsTour.getKnight().getCurrentCol();

        // Set current square
        currentSquare = guiSquares[currentRow][currentCol];

        // Set the text label on the current square
        currentSquare.setText(Integer.toString(knightsTour.getKnight()
                .getMoveCounter()));

        // Move the knight
        knightsTour.move();

        // If the knight has no more moves,
        // stop the timer, set running flag to false
        // and tour finished flag to true.
        if (knightsTour.hasMove() == false) {
            timer.stop();
            isTourRunning = false;
            isTourFinished = true;
        }
    }

    // ****************************************************
    // Method: askNewParam
    //
    // Purpose: Asks the user if they want new parameters
    // for the next game.
    // ****************************************************
    public void askNewParam() {
        int newParamAnswer; // Integer value of dialog response (Yes = 0, No =
        // 1, Cancel = 3)

        // Store response from dialog
        newParamAnswer = JOptionPane.showConfirmDialog(this,
                "Do you want to use the same board?", "Paused",
                JOptionPane.YES_NO_CANCEL_OPTION);

        // If yes, user does not get new game parameters
        if (newParamAnswer == 0) {
            useNewGameParams = false;
        }
        // If no, user gets new game parameters
        else if (newParamAnswer == 1) {
            useNewGameParams = true;
        }
        // If cancel, program exits
        else {
            System.exit(0);
        }
    }

    // ****************************************************
    // Method: askNewGame
    //
    // Purpose: Asks the user if they want a new game
    // this method is called if the user clicks on
    // a square while the tour is running.
    // ****************************************************
    public void tourPaused() {
        int newGameAnswer; // Integer value of dialog response (Yes = 0, No = 1,
        // Cancel = 3)

        // Stop the time, which pauses the tour
        stopTimer();

        // Set tour running flag to false
        isTourRunning = false;

        // Store response from dialog
        newGameAnswer = JOptionPane
                .showConfirmDialog(this, "Start a new tour?", "Paused",
                        JOptionPane.YES_NO_CANCEL_OPTION);

        // If yes
        if (newGameAnswer == 0) {
            // User is asked if they want new game parameters.
            // Preferences are stored in class member variables
            askNewParam();

            // Call resetTour on the knight, to clear current state
            knightsTour.resetTour();

            // Setup a new game
            newGame();
        }
        // If no, set running to true
        // and start the timer again
        else if (newGameAnswer == 1) {
            // If user does not want a new game
            // set tour running flag back to true
            // and restart the timer.

            isTourRunning = true;
            startTimer();
        }
        // If cancel, program exits
        else {
            System.exit(0);
        }
    }

    // ************************************************
    // Method: showHeuristics()
    //
    // Purpose: Displays the accessibility heuristics
    // ************************************************
    public void showHeuristics() {
        for (int rowNumber = 0; rowNumber < boardSize; rowNumber++) {
            for (int columnNumber = 0; columnNumber < boardSize; columnNumber++) {
                guiSquares[rowNumber][columnNumber].setText(Integer
                        .toString(knightsTour
                                .getSquare(rowNumber, columnNumber)
                                .getAccessibility()));
            }
        }
    }

	/*--NOTE--
	 * Quadrants have not proven useful
	//************************************************
	//	Method: showQuadrants()
	//
	//	Purpose: Displays the accessibility heuristics
	//************************************************
	public void showQuadrants()
	{
		for(int iRow = 0; iRow < m_boardSize; iRow++)
		{
			for(int iCol = 0; iCol < m_boardSize; iCol++)
			{
				m_guiSquares[iRow][iCol].setText(Integer.toString(m_knightsTour.getSquare(iRow, iCol).getQuadrant()));
			}
		}
	}
	 */

    // ****************************************************
    // Class: SquareClickHandler
    //
    // Purpose: Mouse listener for squares. Listens for mouse clicks.
    // ****************************************************
    class SquareClickHandler extends MouseAdapter {
        // ****************************************************
        // Method: mouseClicked
        //
        // Purpose: Overrides MouseAdapter's mouseClicked
        // method to handle mouse click events
        // ****************************************************
        @Override
        public void mouseClicked(MouseEvent meEvent) {
            // If the tour is NOT running when the mouse is clicked
            if (isTourRunning == false) {
                // If the tour has finished, i.e., user has not interrupted the
                // tour
                // with subsequent clicks and the tour is allowed to complete
                if (isTourFinished == true) {
                    // Set tour finished back to false
                    isTourFinished = false;

                    // Ask the user if they want new game parameters
                    askNewParam();

                    // Setup a new game
                    newGame();
                }

                // Nested loops iterate through all squares
                for (int rowNumber = 0; rowNumber < boardSize; rowNumber++) {
                    for (int columnNumber = 0; columnNumber < boardSize; columnNumber++) {
                        // Compare the square to the event source, to figure
                        // out which square was clicked
                        if (meEvent.getSource() == guiSquares[rowNumber][columnNumber]) {
                            // If the current square in the loop and the event
                            // source are equal, then
                            // the current square in the loop is the event
                            // source.

                            // Set the start position
                            knightsTour.setStartPosition(rowNumber,
                                    columnNumber);

                            // Set tour running flag to true
                            isTourRunning = true;

                            // Start the timer
                            startTimer();

                            // No need to continue the loop at this point
                            break;
                        }
                    }
                }
            }
            // If the tour IS running when the mouse is clicked
            else {
                tourPaused();
            }
        }
    }
}
