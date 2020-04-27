/*
 * Cierra Ditmore
 * CS 2365
 */
package bangdicegame;

import java.util.Scanner;

/**
 *purpose:
 *lets user control a player
 *must communicate with table
 */
public class GameFunctions {
    public Character [] playerOrder;
    public int currentPlayer, numOfPlayers, originalNumOfPlayers;
    public Boolean game_over, realPlayerDead;
    
    public GameFunctions (Character [] players, int totalPlayers){
        this.playerOrder = players;
        this.currentPlayer = 0;
        this.numOfPlayers = totalPlayers;
        this.originalNumOfPlayers = totalPlayers;
        this.game_over = false;
        this.realPlayerDead = false;
    }
  
    public Character next_turn (){
        this.currentPlayer = (this.currentPlayer + 1)%(this.numOfPlayers);
        return this.playerOrder[this.currentPlayer];
    }
    
    public Character get_current_player (){
        return this.playerOrder[this.currentPlayer];
    }
    
    //gets player 1 to the right
    public Character get_next_player (){
        int temp;
        temp = (this.currentPlayer + 1)%this.numOfPlayers;
        
        if (temp < 0){
            temp = this.numOfPlayers + temp;
        }
        
        return this.playerOrder[temp];
    }
    
    //gets player 1 to the left
    public Character get_previous_player (){
        int temp;
        temp = (this.currentPlayer - 1)%this.numOfPlayers;
        
        if (temp < 0){
            temp = this.numOfPlayers + temp;
        }
        
        return this.playerOrder[temp];
    }
    
    //gets player 2 to the right
    public Character get_two_away_player (Character currentPlayer){
        int temp;
        temp = (this.currentPlayer + 2)%this.numOfPlayers;
        
        if (temp < 0){
            temp = this.numOfPlayers + temp;
        }
        
        Character nextCharacter = this.playerOrder[temp];
        
        if (nextCharacter != currentPlayer){
            return nextCharacter;
        }
        else {
            return this.get_next_player();
        }
    }
    
    //gets player 2 to the left
    public Character get_two_before_player (Character currentPlayer){
        int temp;
        temp = (this.currentPlayer - 2)%this.numOfPlayers;
        
        if (temp < 0){
            temp = this.numOfPlayers + temp;
        }
        
        Character previousCharacter = this.playerOrder[temp];
        
        if (previousCharacter != currentPlayer){
            return previousCharacter;
        }
        else {
            return this.get_previous_player();
        }
    }
    
    public static void player_turn(GameFunctions playerOrder, Dice allDice[], ArrowPile arrowPile){
        int i;
        boolean dynamiteExecuted, gatlingExecuted, doubleDamage;
        int numBullsEye1, numBullsEye2, numBeer, numGatling, rollsRemaining;
        char tempDoubleDamage;
        Scanner input = new Scanner(System.in);
        
        i = 0;
        
        doubleDamage = false;
        dynamiteExecuted = false;
        gatlingExecuted = false;
        rollsRemaining = 3;
        numBullsEye1 = numBullsEye2 = numBeer = numGatling = 0;
        
        //Lucky Duke Special ability: Extra Reroll
        if ("Lucky Duke".equals(playerOrder.get_current_player().name)){
            rollsRemaining = 4;
        }
        
        if ("Sid Ketchum".equals(playerOrder.get_current_player().name)){
                System.out.println("Since it is Sid Ketchum's turn, he may serve 1 person a beer.");
                Dice.beer_roll(playerOrder);
            }
            
            i = 0;
            
            //Rolls all 5 dice
            while (i < 5){
                allDice[i].roll_dice();
                i++;
            }
            
            //Determines if the first roll contains any arrows
            for (i = 0; i < 5; i ++){
                if (!playerOrder.game_over && playerOrder.get_current_player().lifePoints > 0){
                    if ("Arrow".equals(allDice[i].roll)){
                        System.out.println("You rolled an arrow. You must pick up an arrow before continuing.");
                        Dice.arrow_roll(playerOrder.get_current_player(), arrowPile, playerOrder);
                    }
                    if ("Dynamite".equals(allDice[i].roll)){
                        if (!dynamiteExecuted){
                            dynamiteExecuted = Dice.dynamite_roll(allDice, playerOrder.get_current_player(), playerOrder, arrowPile);  
                        }
                    }
                }
            }
            
            
            //Allows for rerolls, if they roll 3 dynamite, takes care of that
            while (rollsRemaining > 0 && !dynamiteExecuted && !playerOrder.game_over && playerOrder.get_current_player().lifePoints > 0){
                System.out.println("\nYour roll: ");
                for (i = 0; i < 5; i++){
                    System.out.println("Dice " + (i+1) + ": " + allDice[i].roll);
                }
                rollsRemaining = Dice.reroll_dice(allDice, rollsRemaining, arrowPile, playerOrder);
                
                for (i = 0; i < 5; i++){
                    if ("Dynamite".equals(allDice[i].roll)){
                        if (!dynamiteExecuted){
                            dynamiteExecuted = Dice.dynamite_roll(allDice, playerOrder.get_current_player(), playerOrder, arrowPile);
                        }
                    }
                }
            }
            
            //Displays final roll
            System.out.println("\nYour final roll: ");
                for (i = 0; i < 5; i++){
                    System.out.println("Dice " + (i+1) + ": " + allDice[i].roll);
                }
            
            
            System.out.println();
            
            //Completes all of the dice rolls
            for (i = 0; i < 5; i ++){
                if (!playerOrder.game_over && playerOrder.get_current_player().lifePoints > 0){
                    if ("Dynamite".equals(allDice[i].roll)){
                        if (!dynamiteExecuted){
                            Dice.dynamite_roll(allDice, playerOrder.get_current_player(), playerOrder, arrowPile);
                            dynamiteExecuted = true;
                        }
                    }
                    else if ("Bull's Eye 1".equals(allDice[i].roll)){
                        numBullsEye1 += 1;   
                    }
                    else if ("Bull's Eye 2".equals(allDice[i].roll)){
                        numBullsEye2 += 1;
                    }
                    else if ("Beer".equals(allDice[i].roll)){
                        numBeer += 1;
                    }
                    else if("Gatling".equals(allDice[i].roll)){
                        numGatling += 1;
                    }
                }
            }
            
            if ("Suzy Lafayette".equals(playerOrder.get_current_player().name)){
                if (numBullsEye1 == 0 && numBullsEye2 == 0){
                    playerOrder.get_current_player().gain_life();
                    playerOrder.get_current_player().gain_arrow();
                }
            }
            
            while (numBullsEye1 > 0){
                if ("Slab the Killer".equals(playerOrder.get_current_player().name)){
                    if (numBeer > 0){
                        System.out.print("Would you like to double the damage of your Bull's Eye 1 for 1 beer? (Y/N): ");
                        input.nextLine();
                        tempDoubleDamage = input.nextLine().charAt(0);
                        while (tempDoubleDamage != 'y' && tempDoubleDamage != 'Y' && tempDoubleDamage != 'n' && tempDoubleDamage != 'N'){
                            System.out.print("Invalid input. Please try again (Y/N): ");
                            tempDoubleDamage = input.nextLine().charAt(0);
                        }
                        if (tempDoubleDamage == 'y' || tempDoubleDamage == 'Y'){
                            doubleDamage = true;
                            numBeer -= 1;
                        }
                    }
                }
                
                Dice.bullsEye1_roll(playerOrder, arrowPile, doubleDamage);
                numBullsEye1 -= 1;
                
                doubleDamage = false;
            }
            while (numBullsEye2 > 0){
                if ("Slab the Killer".equals(playerOrder.get_current_player().name)){
                    if (numBeer > 0){
                        System.out.print("Would you like to double the damage of your Bull's Eye 2 for 1 beer? (Y/N): ");
                        input.nextLine();
                        tempDoubleDamage = input.nextLine().charAt(0);
                        while (tempDoubleDamage != 'y' && tempDoubleDamage != 'Y' && tempDoubleDamage != 'n' && tempDoubleDamage != 'N'){
                            System.out.print("Invalid input. Please try again (Y/N): ");
                            tempDoubleDamage = input.nextLine().charAt(0);
                        }
                        if (tempDoubleDamage == 'y' || tempDoubleDamage == 'Y'){
                            doubleDamage = true;
                            numBeer -= 1;
                        }
                    }
                }
                
                Dice.bullsEye2_roll(playerOrder, arrowPile, doubleDamage);
                numBullsEye2 -= 1;
                
                doubleDamage = false;
            }
            while (numBeer > 0){
                Dice.beer_roll(playerOrder);
                numBeer -= 1;
            }
            while (numGatling > 0){
                if (!gatlingExecuted){
                    Dice.gatling_roll(allDice, playerOrder.get_current_player(), playerOrder, arrowPile);
                    gatlingExecuted = true;
                }
                numGatling -= 1;
            }
            System.out.println();
            
    }
    
    public void eliminate_player (Character player, ArrowPile arrowPile, Boolean killedByPlayer){
        int i;
        int j;
        
        if (player == this.playerOrder[0]){
            this.realPlayerDead = true;
        }
        
        while(player.arrows > 0){
                arrowPile.add_arrow(player);
            }
        
        for (i = 0; i < this.numOfPlayers; i++){
            if (this.playerOrder[i] == player){
                for (j = i; j < this.numOfPlayers - 1; j++){
                    this.playerOrder[j] = this.playerOrder[j+1];
                }
            }
        }
        
        this.playerOrder[this.numOfPlayers] = null;
        
        this.numOfPlayers -= 1;
        
        this.game_over = this.determine_game_over(this, player, killedByPlayer);
        
        if (!this.game_over){
            for (i = 0; i < this.numOfPlayers; i++){
                if ("Vulture Sam".equals(this.playerOrder[i].name)){
                    this.playerOrder[i].gain_life();
                    this.playerOrder[i].gain_life();
                }
            }
        }
    }
    
  
    public boolean determine_game_over (GameFunctions playerOrder, Character deadPlayer, Boolean killedByPlayer){
        if (playerOrder.originalNumOfPlayers == 3){
            if (killedByPlayer){
                if (playerOrder.numOfPlayers == 2){
                    if ("Deputy".equals(playerOrder.get_current_player().role) && "Renegade".equals(deadPlayer.role)){
                        System.out.println("The sheriff has killed the renegade and has won the game.");
                        return true;
                    }
                    else if ("Renegade".equals(playerOrder.get_current_player().role) && "Outlaw".equals(deadPlayer.role)){
                        System.out.println("The renegade has killed the outlaw and has won the game.");
                        return true;
                    }
                    else if ("Outlaw".equals(playerOrder.get_current_player().role) && "Deputy".equals(deadPlayer.role)){
                        System.out.println("The outlaw has killed the deputy and has won the game.");
                        return true;
                    }
                    else {
                        System.out.println(deadPlayer.name + " has been killed. The winner will be decided by who is the last alive.");
                        return false;
                    }
                }
                else {
                    System.out.println(deadPlayer.name + " has been killed. The winner of the game is " + playerOrder.get_current_player().name);
                    return true;
                }
            }
            else {
                if (playerOrder.numOfPlayers == 2){
                    System.out.println(deadPlayer.name + " has died. The winner will be decided by who is the last alive.");
                    return false;
                }
                else {
                    System.out.println(deadPlayer.name + " has died. The winner of the game is " + playerOrder.get_current_player().name);
                    return true;
                }
            }
        }
        
        else {
            int sheriffAlive = 0;
            int outlawAlive = 0;
            int renegadeAlive = 0;
            int i;
        
            for (i = 0; i < playerOrder.numOfPlayers; i++){
                if ("Sheriff".equals(playerOrder.playerOrder[i].role)){
                    sheriffAlive += 1;
               }
                else if ("Outlaw".equals(playerOrder.playerOrder[i].role)){
                    outlawAlive += 1;
                }
                else if ("Renegade".equals(playerOrder.playerOrder[i].role)){
                    renegadeAlive += 1;
                }
            }
        
            if ((sheriffAlive == 1) && (outlawAlive == 0) && (renegadeAlive == 0)){
                System.out.println("All renegades and outlaws are dead, so the sheriff and deputies win.");
                return true;
            }
            else if (sheriffAlive == 0){
                if ((playerOrder.numOfPlayers == 1) && (renegadeAlive == 1)){
                    System.out.println("The renegade is the last one alive, so they win.");
                    return true;
                }
                else {
                    System.out.println("The sheriff is dead, so the outlaws win.");
                    return true;
                }
            }
            else if (playerOrder.numOfPlayers == 0){
                System.out.println("All players are dead, so the outlaws win.");
                    return true;
            }
            else {
                return false;
            }
        }
    }
}
