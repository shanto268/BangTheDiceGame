/*
 * Cierra Ditmore
 * CS 2365
 * Note: double check your remove_arrow method
 */
package bangdicegame;

/**
 *
 * @author cmdma
 */
public class ArrowPile {
    public int remaining;
    
    public ArrowPile(){
        this.remaining = 9;
    }
    
    public void remove_arrow (GameFunctions playerOrder){
        if (this.remaining > 0){
            this.remaining -= 1;
            playerOrder.get_current_player().gain_arrow();
            if (this.pileIsEmpty()){
                this.empty_pile(playerOrder, playerOrder.numOfPlayers);
            }
        }
        else {
            System.out.println("Remove_arrow Error, no arrows remaining");
        }
    }
    
    //for ai 
    public void remove_arrow(Character selfPlayer, Character [] playerOrder){
        if (this.remaining > 0){
            this.remaining -= 1; //decrease pile
            selfPlayer.gain_arrow(); //player gets arrow
        }
        else if (this.pileIsEmpty()){
        		System.out.println("======= The Indians Attacked!!! =======");
               //everyone loses as many lives as many arrows that they had and loses all their arrows
            	for (int i=0;i<playerOrder.length;i++) {
            		if (playerOrder[i]!=null) {
	            		int numArrow = playerOrder[i].arrows;
	            		playerOrder[i].lose_life(numArrow);
	            		playerOrder[i].arrows = 0;
            		}
            	}  
            	this.remaining = 9; //set pile back to normal again
       } 
        else {
            System.out.println("Remove_arrow Error, no arrows remaining");
        }
    }
    
    public void add_arrow (Character player){
        this.remaining += 1;
        player.lose_arrow();
    }
    
    public Boolean pileIsEmpty(){
        if (this.remaining == 0){
            return true;
        }
        else {
            return false;
        }
    }
    
    public void empty_pile (GameFunctions players, int totalPlayers){
        int i;
        int playersDead;
        
        playersDead = 0;
        
        System.out.println("\nThe last arrow was drawn and the indians attacked.\n");
        
        for (i = 0; i < totalPlayers; i++){
            if (players.playerOrder[i].arrows > players.playerOrder[i].lifePoints){
                playersDead++;
            }
        }
        
        if (playersDead >= players.numOfPlayers){
            players.game_over = true;
            System.out.println("All players are dead, so the outlaws win.");
        }
        
        
        if (!players.game_over){
            for (i = 0; i < totalPlayers; i++){
                while (players.playerOrder[i].arrows > 0){
                    if ("Jourdonnais".equals(players.playerOrder[i].name)){
                        players.playerOrder[i].lose_life(players, this, true);
                        players.playerOrder[i].arrows = 0;
                    }
                    else if (players.playerOrder[i].lifePoints > 0){
                        players.playerOrder[i].lose_arrow();
                        players.playerOrder[i].lose_life(players, this, true);
                    }
                }
            }
        }
        
        this.remaining = 9;
    }
}