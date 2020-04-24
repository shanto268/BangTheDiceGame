/*
 * Meghan Engert
 * CS 2365
 */
package bangdicegame;

/**
 *
 * @author
 */
public class ArrowPile {
    public int remaining;
    
    public ArrowPile(){
        this.remaining = 9;
    }
    
    public void remove_arrow (GameFunctions playerOrder){
        if (this.remaining > 0){
            this.remaining -= 1;
            if (this.pileIsEmpty()){
                this.empty_pile(playerOrder, playerOrder.numOfPlayers);
            }
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
        
        this.remaining = 9;
    }
}
