package org.kratos.reversi;

import junit.framework.TestCase;
import org.kratos.framework.Kratos;
import org.kratos.framework.game.Player;

/**
 * Created by FakeYou on 4/12/14.
 */
public class ReversiTest extends TestCase {

    private Kratos kratos;

    public void setUp() {
        kratos = new Kratos();
        kratos.setUsername("tester");
        kratos.getMatch().setOpponent(new Player("opponent", false));
        kratos.getMatch().start("Reversi", "tester");
    }

    public void testConstructor() {
        Reversi reversi = new Reversi(kratos);
        Board board = reversi.getBoard();

        assertEquals(Board.Square.WHITE, board.getSquare(3, 3));
        assertEquals(Board.Square.BLACK, board.getSquare(3, 4));
        assertEquals(Board.Square.BLACK, board.getSquare(4, 3));
        assertEquals(Board.Square.WHITE, board.getSquare(4, 4));
    }

    public void testDoLegalMoveFalse() {
        Reversi reversi = new Reversi(kratos);
        Board board = reversi.getBoard();

        // Doing an illegal move
        Boolean success = reversi.doMove(1, 1);
        assertFalse(success);
    }

    public void testDoLegalMoveTrue() {
        Reversi reversi = new Reversi(kratos);
        Board board = reversi.getBoard();

        // Doing a legal move
        Boolean success = reversi.doMove(2, 3);
        assertTrue(success);
    }

    public void testLegalMoveMulti(){
        Reversi reversi = new Reversi(kratos);
        Board board = reversi.getBoard();
        board.clear();

        board.setSquare(1, 0, board.getOpponentSquare());
        board.setSquare(2, 0, board.getPlayerSquare());
        board.setSquare(1, 1, board.getOpponentSquare());
        board.setSquare(2, 2, board.getPlayerSquare());

        Boolean success = reversi.doMove(0, 0);
        assertTrue(success);
    }

    public void testFlipTilesSingleChain() {
        Reversi reversi = new Reversi(kratos);
        Board board = reversi.getBoard();

        reversi.doMove(2, 3);
        board.setSquare(2, 3, Board.Square.BLACK);
        reversi.flipTiles(2, 3, Board.Square.BLACK);

        assertEquals(Board.Square.BLACK, board.getSquare(2, 3));
        assertEquals(Board.Square.BLACK, board.getSquare(3, 3));
        assertEquals(Board.Square.BLACK, board.getSquare(4, 3));
    }

    public void testFlipTilesMutlipleChains() {
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

        assertEquals(Board.Square.BLACK, board.getSquare(0, 0));
        assertEquals(Board.Square.BLACK, board.getSquare(1, 0));
        assertEquals(Board.Square.BLACK, board.getSquare(2, 0));
        assertEquals(Board.Square.BLACK, board.getSquare(1, 1));
        assertEquals(Board.Square.WHITE, board.getSquare(2, 1));
        assertEquals(Board.Square.BLACK, board.getSquare(2, 2));

        reversi.doMove(0, 1);
        board.setSquare(0, 1, Board.Square.WHITE);
        reversi.flipTiles(0, 1, Board.Square.WHITE);
    }
}
