package org.kratos.reversi;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Yuri on 4/4/2014.
 */
public class GameBoardMouseListener implements MouseListener {

    ReversiBoard reversiBoard;

    public GameBoardMouseListener(ReversiBoard board){
        this.reversiBoard = board;
    }

    public void mouseEntered(MouseEvent e){

    }
    public void mouseExited(MouseEvent e){

    }
    public void mouseReleased(MouseEvent e){

    }
    public void mousePressed(MouseEvent e){

    }

    public void mouseClicked(MouseEvent e){
        int x = e.getX();
        int y = e.getY();

        if(reversiBoard.view.ableToMakeMove()){
            reversiBoard.registerMove(x, y, 2);
            System.out.println("X: " + x + " Y: " + y);
        } else {
            System.out.println("It's not your turn!");
        }
    }


}
