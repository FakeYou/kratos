package nl.hanze.t23i.gamemodule.extern;

import java.awt.Component;

/**
 * The game module interface for pluggable game modules.
 * <p>
 * DO NOT EDIT!<br />
 * This interface is not to be edited in any way. This ensures compatibility with other pluggable game module load systems.
 * <p>
 * The class that is interested in being plugged in, in a pluggable game module load system, implements this interface.<br />
 * It is a good idea to use the abstract class <code>AbstractGameModule</code> for this purpose, instead of this interface.
 * This because the abstract class contains a mandatory constructor, a mandatory static field and several constants indicating statuses.
 * 
 * @author Sjors van Oers
 * @author Hanze Hogeschool Groningen - Instituut voor ICT
 *
 */
public interface IGameModule {
	
	/**
	 * Starts the game.
	 * @throws IllegalStateException If the game is already started or the game has finished
	 */
	public void start() throws IllegalStateException;
	
	/**
	 * Returns the name of the player who is to move next.
	 * @return The name of the player who is to move next
	 * @throws IllegalStateException If the game has not yet been started or the game has finished
	 */
	public String getPlayerToMove() throws IllegalStateException;
	
	/**
	 * Does a move for the given player.
	 * @param player The player doing the move
	 * @param move The move the player is doing
	 * @throws IllegalStateException If the game is not yet started, the game has finished or it is not player's turn
	 */
	public void doPlayerMove(String player, String move) throws IllegalStateException;
	
	/**
	 * Returns the message dedicated to the player of the next turn.
	 * <p>
	 * The message is game specific and may be empty.
	 * @return A message to the player of the next turn.
	 * @throws IllegalStateException If the game has not yet been started or the game has finished
	 */
	public String getTurnMessage() throws IllegalStateException;
	
	/**
	 * Returns the details of the last move done during this game.
	 * <p>
	 * These details are game specific and may be empty.
	 * @return A string representing the details of the last move
	 * @throws IllegalStateException If no move has been done during this game
	 */
	public String getMoveDetails() throws IllegalStateException;
	
	/**
	 * Returns the status of the game.
	 * <p>
	 * The return code can either indicate that the game has been started, is in progress or has been finished.
	 * @return <code>-1</code> if the game has not yet started, <code>0</code> if the game is started and has not yet finished, <code>1</code> if the game has finished
	 */
	public int getMatchStatus();
	
	/**
	 * Returns the result of the game for a given player.
	 * <p>
	 * The return code will indicate if the player has won the game, has lost the game or the game has ended in a draw.
	 * @param player The player to whom the result belong to.
	 * @return <code>-1</code> if the player lost the game, <code>0</code> if the game resulted in a draw, <code>1</code> if the player won the game
	 * @throws IllegalStateException If the game has not yet finished
	 */
	public int getPlayerResult(String player) throws IllegalStateException;
	
	/**
	 * Returns the score of a player.
	 * <p>
	 * These results are game specific.
	 * @return An integer representing the score of a player
	 * @throws IllegalStateException If the game has not yet finished
	 */
	public int getPlayerScore(String player) throws IllegalStateException;
	
	/**
	 * Returns a string commenting the result of the game.
	 * <p>
	 * This comment is game specific and may be empty.
	 * @return A string commenting the result of the game
	 * @throws IllegalStateException If the game has not yet finished
	 */
	public String getMatchResultComment() throws IllegalStateException;
	
	/**
	 * Returns a view of this game.
	 * <p>
	 * It is a good idea for this view to be resizable.
	 * @return A view of the game
	 */
	public Component getView();
	
}
