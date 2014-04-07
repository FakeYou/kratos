import javax.swing.*;
import java.awt.*;

/**
 * Created by Yuri on 2/4/2014.
 */
public class GameBoard extends JPanel{
    int rows;
    int columns;
    int squareSize;
    GameBoardMouseListener listener;
    int[][] gameState;
    GameView view;

    /**
     * Constructor to initialize a new game board
     * @param rows          The amount of rows desired for the new board
     * @param columns       The amount of columns desired for the new board
     * @param squareSize    The size of each square (should be smaller if there's a lot of rows)
     */
    public GameBoard(int rows, int columns, int squareSize, GameView view){
        this.setPreferredSize(new Dimension(rows * squareSize, columns * squareSize));

        this.listener = new GameBoardMouseListener(this);
        this.rows = rows;
        this.columns = columns;
        gameState = new int[rows][columns];
        this.squareSize = squareSize;
        this.view = view;

        this.addMouseListener(this.listener);
    }

    /**
     * Register a move into the variable (does not perform any painting operations yet)
     * @param row Row of the move
     * @param col Column of the move
     * @param player Player that made the move
     */
    public void registerMove(int x, int y, int player){
        int[] rowcol = findRowCol(x, y);
        int row = rowcol[0];
        int col = rowcol[1];

        System.out.println("Row: " + row + " Column: " + col);

        if(this.gameState[row][col] != 0){
            System.out.println("Move already made on this square");
            return;
        }
        this.gameState[row][col] = player;
        paintAllMoves(this.getGraphics());
        view.swapTurn();
    }

    @Override
    protected void paintComponent(Graphics g){
        drawGrid(g);
        drawBoardBorder(g);
        paintAllMoves(g);
    }

    /**
     * Draws the grid on the gameboard.
     * @param g Graphics of the JPanel
     */
    private void drawGrid(Graphics g){
        Graphics2D g2d = (Graphics2D)g;
        System.out.println("Drawing grid");

        // Draw vertical lines
        for(int i = 0; i < rows; i++) {
            g2d.setColor(Color.BLACK);
            g2d.drawLine(i * this.getWidth() / rows, 0, i * this.getWidth() / rows, this.getHeight());
        }

        // Draw horizontal lines
        for(int i = 0; i < columns; i++){
            g2d.setColor(Color.BLACK);
            g2d.drawLine(0, i * this.getHeight() / columns, this.getWidth(), i * this.getHeight() / columns);
        }
    }

    /**
     * Draws a (red) border around the grid
     * @param g Graphics of the JPanel
     */
    private void drawBoardBorder(Graphics g){
        // Border around the panel
        g.setColor(Color.RED);

        // top left to top right
        g.drawLine(0, 0, this.getWidth(), 0);

        // top left to bottom left
        g.drawLine(0, 0, 0, this.getHeight());

        // bottom right to top right
        g.drawLine(this.getWidth() - 1, this.getHeight(), this.getWidth() - 1, 0);

        // bottom right to bottom left
        g.drawLine(this.getWidth(), this.getHeight() - 1, 0, this.getHeight() - 1);
    }

    /**
     * Paint all moves saved in gameState[][]
     * @param g
     */
    private void paintAllMoves(Graphics g){
        // Loop through rows and columns
        for(int row = 0; row < gameState.length; row++){
            columnloop:
            for(int column = 0; column < gameState[0].length; column++){
                switch(gameState[row][column]){
                    case 0:
                        continue columnloop;
                    case 1:
                        g.setColor(Color.black);
                        break;
                    case 2:
                        g.setColor(Color.WHITE);
                        break;
                }

                g.fillOval(column * squareSize + 3, row * squareSize + 3, squareSize - 6, squareSize - 6);

                g.setColor(Color.black);
                g.drawOval(column * squareSize + 3, row * squareSize + 3, squareSize - 6, squareSize - 6);
            }
        }
    }

    /**
     * Deduce the row and column position of the clicked x, y positions (from mouselistener)
     * @param x
     * @param y
     * @return int[row, column]
     */
    private int[] findRowCol(int x, int y){
        int tempx = 0;
        int tempy = 0;

        for(int i = squareSize; i <= this.getWidth(); i = i+squareSize){
            if(x < i){
                break;
            }
            tempx++;
        }

        for(int i = squareSize; i <= this.getHeight(); i = i+squareSize){
            if(y < i){
                break;
            }
            tempy++;
        }

        return new int[] {tempy, tempx};
    }
}
