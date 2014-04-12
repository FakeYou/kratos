package org.kratos.reversi;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kratos.framework.Kratos;
import org.kratos.framework.game.Player;

/**
 * Created by FakeYou on 4/12/14.
 */
public class ReversiTest {

    private Kratos kratos;

    @Before
    public void setUp() {
        kratos = new Kratos();
        kratos.setUsername("tester");
        kratos.getMatch().setOpponent(new Player("opponent", false));
        kratos.getMatch().start("Reversi", "tester");
    }

    @Test
    public void constructorTest() {
        Reversi reversi = new Reversi(kratos);
        Board board = reversi.getBoard();

        Assert.assertEquals(Board.Square.WHITE, board.getSquare(3, 3));
        Assert.assertEquals(Board.Square.BLACK, board.getSquare(3, 4));
        Assert.assertEquals(Board.Square.BLACK, board.getSquare(4, 3));
        Assert.assertEquals(Board.Square.WHITE, board.getSquare(4, 4));
    }

    @Test
    public void doLegalMoveTestFalse() {
        Reversi reversi = new Reversi(kratos);
        Board board = reversi.getBoard();

        // Doing an illegal move
        Boolean success = reversi.doMove(1, 1);
        Assert.assertEquals(false, success);
    }

    @Test
    public void doLegalMoveTestTrue() {
        Reversi reversi = new Reversi(kratos);
        Board board = reversi.getBoard();

        // Doing a legal move
        Boolean success = reversi.doMove(2, 3);
        Assert.assertEquals(true, success);
    }

    @Test
    public void doLegalMoveTestMulti(){
        Reversi reversi = new Reversi(kratos);
        Board board = reversi.getBoard();
        board.clear();

        board.setSquare(1, 0, board.getOpponentSquare());
        board.setSquare(2, 0, board.getPlayerSquare());
        board.setSquare(1, 1, board.getOpponentSquare());
        board.setSquare(2, 2, board.getPlayerSquare());

        Boolean success = reversi.doMove(0, 0);
        Assert.assertEquals(true, success);
    }

    @Test
    public void flipTilesSingleChain() {
        Reversi reversi = new Reversi(kratos);
        Board board = reversi.getBoard();

        reversi.doMove(2, 3);
        board.setSquare(2, 3, Board.Square.BLACK);
        reversi.flipTiles(2, 3, Board.Square.BLACK);

        Assert.assertEquals(Board.Square.BLACK, board.getSquare(2, 3));
        Assert.assertEquals(Board.Square.BLACK, board.getSquare(3, 3));
        Assert.assertEquals(Board.Square.BLACK, board.getSquare(4, 3));
    }

    @Test
    public void flipTilesMutlipleChains() {
        Reversi reversi = new Reversi(kratos);
        Board board = reversi.getBoard();
        board.clear();

        board.setSquare(1, 0, board.getOpponentSquare());
        board.setSquare(2, 0, board.getPlayerSquare());
        board.setSquare(1, 1, board.getOpponentSquare());
        board.setSquare(2, 1, board.getOpponentSquare());
        board.setSquare(2, 2, board.getPlayerSquare());

        reversi.doMove(0, 0);
        board.setSquare(0, 0, Board.Square.BLACK);
        reversi.flipTiles(0, 0, Board.Square.BLACK);

        Assert.assertEquals(Board.Square.BLACK, board.getSquare(0, 0));
        Assert.assertEquals(Board.Square.BLACK, board.getSquare(1, 0));
        Assert.assertEquals(Board.Square.BLACK, board.getSquare(2, 0));
        Assert.assertEquals(Board.Square.BLACK, board.getSquare(1, 1));
        Assert.assertEquals(Board.Square.WHITE, board.getSquare(2, 1));
        Assert.assertEquals(Board.Square.BLACK, board.getSquare(2, 2));

        reversi.doMove(0, 1);
        board.setSquare(0, 1, Board.Square.WHITE);
        reversi.flipTiles(0, 1, Board.Square.WHITE);

        board.debug();
    }
}
