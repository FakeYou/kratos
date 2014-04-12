package org.kratos.framework.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Yuri on 2/4/2014.
 */
public abstract class GameBoard extends JPanel {
    private int rows;
    private int columns;
    private int squareWidth;
    private int squareHeight;

    private GameBoardMouseListener listener;

    public GameBoard(int columns, int rows){
        this.setPreferredSize(new Dimension(500, 500));

        this.listener = new GameBoardMouseListener(this);
        this.rows = rows;
        this.columns = columns;
        this.squareWidth = getPreferredSize().width / columns;
        this.squareHeight = getPreferredSize().height / rows;

        this.addMouseListener(this.listener);
    }

    public abstract void click(int column, int row);
    public abstract void paintSquare(int column, int row);
    public abstract void paint();

    @Override
    protected void paintComponent(Graphics g){
        drawGrid(g);
        drawBoardBorder(g);
    }

    public void paintSquares() {
        for(int row = 0; row < rows; row++) {
            for(int column = 0; column < columns; column++) {
                paintSquare(column, row);
            }
        }
    }

    private void drawGrid(Graphics g){
        g.setColor(Color.LIGHT_GRAY);

        // Draw vertical lines
        for(int i = 0; i < rows; i++) {
            g.drawLine(i * this.getWidth() / rows, 0, i * this.getWidth() / rows, this.getHeight());
        }

        // Draw horizontal lines
        for(int i = 0; i < columns; i++){
            g.drawLine(0, i * this.getHeight() / columns, this.getWidth(), i * this.getHeight() / columns);
        }
    }

    private void drawBoardBorder(Graphics g){
        // Border around the panel
        g.setColor(Color.LIGHT_GRAY);

        g.drawLine(0, 0, this.getWidth(), 0); // top left to top right
        g.drawLine(0, 0, 0, this.getHeight()); // top left to bottom left
        g.drawLine(this.getWidth() - 1, this.getHeight(), this.getWidth() - 1, 0); // bottom right to top right
        g.drawLine(this.getWidth(), this.getHeight() - 1, 0, this.getHeight() - 1); // bottom right to bottom left
    }

    public int[] findRowCol(int x, int y){
        int[] coords = new int[2];

        double width = getWidth();
        double height = getHeight();

        coords[0] = (int) Math.floor((double) x / width * columns);
        coords[1] = (int) Math.floor((double) y / height * rows);

        return coords;
    }

    public int getSquareWidth() {
        return squareWidth;
    }

    public int getSquareHeight() {
        return squareHeight;
    }
}
