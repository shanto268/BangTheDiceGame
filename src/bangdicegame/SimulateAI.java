/*
 * Sadman Ahmed Shanto
 * CS 2365
 */
package bangdicegame;

/*
 * Purpose: 
 * arranges all players in an array 
 * lets both user and ai to communicate with itself
 * determines when game is ended
 */

public class SimulateAI {
    public Character [] playerOrder;
    int totalPlayers;
    		
    public SimulateAI (Character [] players, int totalPlayers){
        this.playerOrder = players;
        this.totalPlayers = totalPlayers;
    }

	public void AITurn() {
		for(int i=1;i<this.totalPlayers;i++) {
			AI aiPlayer = new AI(this.playerOrder, this.totalPlayers, i);
			aiPlayer.getPlayerRole();
			aiPlayer.getPlayerBehavior();
		}
	}
}