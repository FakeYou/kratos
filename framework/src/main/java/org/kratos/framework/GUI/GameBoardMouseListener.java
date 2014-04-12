package org.kratos.framework.gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Yuri on 4/4/2014.
 */
public class GameBoardMouseListener implements MouseListener {

    GameBoard gameBoard;

    public GameBoardMouseListener(GameBoard board){
        this.gameBoard = board;
    }

    public void mouseEntered(MouseEvent e){

    }
    public void mouseExited(MouseEvent e){

    }
    public void mouseReleased(MouseEvent e){
        int x = e.getX();
        int y = e.getY();

        int[] coords = gameBoard.findRowCol(x, y);

        gameBoard.click(coords[0], coords[1]);
    }
    public void mousePressed(MouseEvent e){

    }

    public void mouseClicked(MouseEvent e){

    }
}
