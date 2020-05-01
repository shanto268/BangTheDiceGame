/*
 * Cierra Ditmore
 * CS 2365
 */
package bangdicegame;

import java.util.Random;
import java.util.Scanner;

/**
 *purpose:
 *lets user control a player
 *must communicate with table
 */
public class GameFunctions {
    public Character [] playerOrder;
    public int currentPlayer, numOfPlayers, numAlivePlayers, boneyardTotal;
    public Boolean game_over, realPlayerDead, expansions, outbreak;
    int boneyardDeck [];
    
    public GameFunctions (Character [] players, int totalPlayers, Boolean expansions){
        int [] deck = {0,0,1,1,1,1,1,1,2,2,2};
        this.playerOrder = players;
        this.currentPlayer = 0;
        this.numOfPlayers = totalPlayers;
        this.numAlivePlayers = totalPlayers;
        this.game_over = false;
        this.realPlayerDead = false;
        this.expansions = expansions;
        this.boneyardDeck = GameFunctions.shuffle_boneyard_deck(deck);
        this.boneyardTotal = 0;
        this.outbreak = false;
    }
  
    public void next_turn (){
        this.currentPlayer = (this.currentPlayer + 1)%(this.numOfPlayers);
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
        
        while (this.playerOrder[temp].isDead){
            temp = (temp + 1)%this.numOfPlayers;
        
            if (temp < 0){
                temp = this.numOfPlayers + temp;
            }
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
        
        while (this.playerOrder[temp].isDead){
            temp = (temp - 1)%this.numOfPlayers;
        
            if (temp < 0){
                temp = this.numOfPlayers + temp;
            }
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
        
        while (nextCharacter.isDead){
            temp = (temp + 1)%this.numOfPlayers;
            if (temp < 0){
                temp = this.numOfPlayers + temp;
            }
            nextCharacter = this.playerOrder[temp];
        }
        
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
        
        while (previousCharacter.isDead){
            temp = (temp - 1)%this.numOfPlayers;
            if (temp < 0){
                temp = this.numOfPlayers + temp;
            }
            previousCharacter = this.playerOrder[temp];
        }
        
        if (previousCharacter != currentPlayer){
            return previousCharacter;
        }
        else {
            return this.get_previous_player();
        }
    }
    
    public static void player_turn(GameFunctions game, Dice allDice[], ArrowPile arrowPile){
        int i;
        boolean dynamiteExecuted, gatlingExecuted;
        int numBullsEye1, numBullsEye2, numBeer, numGatling, numFight, numWhiskey, rollsRemaining, diceToRoll;
        String diceSelection;
        
        Scanner input = new Scanner(System.in);
        
        i = 0;
        
        dynamiteExecuted = false;
        gatlingExecuted = false;
        diceToRoll = 5;
        rollsRemaining = 3;
        numBullsEye1 = numBullsEye2 = numBeer = numGatling = numFight = numWhiskey = 0;
        
        if (game.expansions){
            allDice[3].type = "duel";
            allDice[4].type = "duel";
        }
        
        if ("Zombie".equals(game.get_current_player().role) && !game.get_current_player().isDead){
            diceToRoll = 3;
        }
        
        //Lucky Duke Special ability: Extra Reroll
        if ("Lucky Duke".equals(game.get_current_player().name)){
            rollsRemaining = 4;
        }
        
        if ("Jose Delgado".equals(game.get_current_player().name) && !game.outbreak){
            System.out.print("Jose Delgado, would you like to use the loudmouth dice as your 6th dice? (Y/N): ");
            diceSelection = input.nextLine().toLowerCase();
            
            while (diceSelection.charAt(0) != 'y' && diceSelection.charAt(0) != 'n'){
                System.out.print("Invalid input. Enter either Y or N: ");
                diceSelection = input.nextLine().toLowerCase();
            }
            
            if (diceSelection.charAt(0) == 'y'){
                diceToRoll = 6;                
            }
            
            System.out.print("Would you like to use the coward dice? (Y/N): ");
            diceSelection = input.nextLine().toLowerCase();
            
            while (diceSelection.charAt(0) != 'y' && diceSelection.charAt(0) != 'n'){
                System.out.print("Invalid input. Enter either Y or N: ");
                diceSelection = input.nextLine().toLowerCase();
            }
            if (diceSelection.charAt(0) == 'y'){
                allDice[2].type = "coward";                
            }
        }
        
        else if ("Tequila Joe".equals(game.get_current_player().name) && !game.outbreak){
            System.out.print("Tequila Joe, would you like to use the coward dice as your 6th dice? (Y/N): ");
            diceSelection = input.nextLine().toLowerCase();
            
            while (diceSelection.charAt(0) != 'y' && diceSelection.charAt(0) != 'n'){
                System.out.print("Invalid input. Enter either Y or N: ");
                diceSelection = input.nextLine().toLowerCase();
            }
            
            if (diceSelection.charAt(0) == 'y'){
                diceToRoll = 6;
                allDice[5].type = "coward";
            }
            
            System.out.print("Would you like to use the loudmouth dice? (Y/N): ");
            diceSelection = input.nextLine().toLowerCase();
            
            while (diceSelection.charAt(0) != 'y' && diceSelection.charAt(0) != 'n'){
                System.out.print("Invalid input. Enter either Y or N: ");
                diceSelection = input.nextLine().toLowerCase();
            }
            if (diceSelection.charAt(0) == 'y'){
                allDice[2].type = "loudmouth";              
            }
        }
        
        else if (!game.outbreak){
            System.out.print("Would you like to use the 'loudmouth' dice, 'coward' dice, or 'neither'?: ");
            diceSelection = input.nextLine();
            diceSelection = diceSelection.toLowerCase();
        
            while (!"loudmouth".equals(diceSelection) && !"coward".equals(diceSelection) && !"neither".equals(diceSelection)){
                System.out.print("Invalid input. Enter either 'loudmouth', 'coward', or 'neither': ");
                diceSelection = input.nextLine();
                diceSelection = diceSelection.toLowerCase();
            }
        
            if ("loudmouth".equals(diceSelection)){
                allDice[2].type = "loudmouth";
            }
            else if ("coward".equals(diceSelection)){
                allDice[2].type = "coward";
            }
        }
           
        i = 0;
            
        //Rolls all dice
        while (i < diceToRoll){
            allDice[i].roll_dice();
            i++;
        }
            
        //Determines if the first roll contains any arrows
        for (i = 0; i < diceToRoll; i ++){
            if (!game.game_over && game.get_current_player().lifePoints > 0){
                if ("Arrow".equals(allDice[i].roll)){
                    System.out.println("You rolled an arrow. You must pick up an arrow before continuing.");
                    Dice.arrow_roll(game.get_current_player(), arrowPile, game);
                }
                if ("Broken Arrow".equals(allDice[i].roll)){
                    Dice.broken_arrow_roll(game, arrowPile);
                }
                if ("Bullet".equals(allDice[i].roll)){
                    System.out.println("You rolled a bullet and have immediately taken 1 damage.");
                    game.get_current_player().lose_life(game, arrowPile, true);
                }
                if ("Dynamite".equals(allDice[i].roll)){
                    if (!dynamiteExecuted){
                        dynamiteExecuted = Dice.dynamite_roll(allDice, game.get_current_player(), game, arrowPile);  
                    }
                }
            }
        }
            
            
        //Allows for rerolls, if they roll 3 dynamite, takes care of that
        while (rollsRemaining > 0 && !dynamiteExecuted && !game.game_over && game.get_current_player().lifePoints > 0){
            System.out.println("\nYour roll: ");
            for (i = 0; i < diceToRoll; i++){
                System.out.println("Dice " + (i+1) + ": " + allDice[i].roll);
            }
            rollsRemaining = Dice.reroll_dice(allDice, rollsRemaining, arrowPile, game, diceToRoll);
            
            if ("Belle Star".equals(game.get_current_player().name)){
                int numDynamite = 0;
                int dynamitePosition = 0;
                char decision;
                    
                for (i = 0; i < diceToRoll; i++){
                    if ("Dynamite".equals(allDice[i].roll)){
                        numDynamite += 1;
                        dynamitePosition = i;
                    }
                } 
                if (numDynamite > 0){
                    System.out.print("Would you like to exchange 1 dynamite to a gatling? (Y/N): ");
                    decision = input.nextLine().toLowerCase().charAt(0);
                    
                    while(decision != 'y' && decision != 'n'){
                        System.out.print("Invalid input. Please enter Y or N: ");
                        decision = input.nextLine().toLowerCase().charAt(0);
                    }
                    
                    if (decision == 'y'){
                        allDice[dynamitePosition].roll = "Gatling";
                        System.out.println("Dice " + (dynamitePosition+1) + " is now a gatling.");
                    }
                    
                }
            }
            
            for (i = 0; i < diceToRoll; i++){
                if ("Dynamite".equals(allDice[i].roll)){
                    if (!dynamiteExecuted){
                        dynamiteExecuted = Dice.dynamite_roll(allDice, game.get_current_player(), game, arrowPile);
                    }
                }
            }
        }
            
        //Displays final roll
        System.out.println("\nYour final roll: ");
            for (i = 0; i < diceToRoll; i++){
                System.out.println("Dice " + (i+1) + ": " + allDice[i].roll);
            }
            
            
        System.out.println();
           
        //Completes all of the dice rolls
        for (i = 0; i < diceToRoll; i ++){
            if (!game.game_over && game.get_current_player().lifePoints > 0){
                if ("Dynamite".equals(allDice[i].roll)){
                    if (!dynamiteExecuted){
                        Dice.dynamite_roll(allDice, game.get_current_player(), game, arrowPile);
                        dynamiteExecuted = true;
                    }
                }
                else if("Whiskey".equals(allDice[i].roll) && !"Zombie".equals(game.get_current_player().role)){
                    numWhiskey += 1;
                    if ("Greg Digger".equals(game.get_current_player().name)){
                        numWhiskey += 1;
                    }
                }
                else if ("Bull's Eye 1".equals(allDice[i].roll)){
                    numBullsEye1 += 1;   
                }
                else if ("Double Bull's Eye 1".equals(allDice[i].roll)){
                    numBullsEye1 += 2;
                }  
                else if ("Bull's Eye 2".equals(allDice[i].roll)){
                    numBullsEye2 += 1;
                }
                else if ("Double Bull's Eye 2".equals(allDice[i].roll)){
                    numBullsEye2 += 2;
                }
                else if ("Beer".equals(allDice[i].roll) && !"Zombie".equals(game.get_current_player().role)){
                    numBeer += 1;
                }
                else if ("Double Beer".equals(allDice[i].roll) && !"Zombie".equals(game.get_current_player().role)){
                    numBeer += 2;
                }
                else if("Gatling".equals(allDice[i].roll)){
                    numGatling += 1;
                }
                else if("Double Gatling".equals(allDice[i].roll)){
                    numGatling += 2;
                }
                else if("Fight".equals(allDice[i].roll)){
                    numFight += 1;
                }
            }
        }
           
        if ("Suzy Lafayette".equals(game.get_current_player().name)){
            if (numBullsEye1 == 0 && numBullsEye2 == 0){
                game.get_current_player().gain_life();
                game.get_current_player().gain_arrow();
            }
        }
        
        while (numWhiskey > 0){
            game.get_current_player().gain_life();
            numWhiskey -=1;
        }
           
        while (numBullsEye1 > 0){
            Dice.bullsEye1_roll(game, arrowPile);
            numBullsEye1 -= 1;
        }
        
        while (numBullsEye2 > 0){
            Dice.bullsEye2_roll(game, arrowPile);
            numBullsEye2 -= 1;
        }
        
        while (numBeer > 0){
            Dice.beer_roll(game);
            numBeer -= 1;
        }
        while (numGatling > 0){
            if (!gatlingExecuted){
                Dice.gatling_roll(allDice, game.get_current_player(), game, arrowPile);
                gatlingExecuted = true;
            }
            numGatling -= 1;
        }
        while (numFight > 0){
            Dice.fight_roll(game, arrowPile);
            numFight -= 1;
        }
        System.out.println();   
    }
    
    public static int [] shuffle_boneyard_deck (int [] deck){
        int temp1, temp2;
        Random rand = new Random();
        int random;
        for (int i = 0; i < 11; i++){
            random = rand.nextInt(11);
            temp1 = deck[random];
            temp2 = deck[i];
            deck[i] = temp1;
            deck[random] = temp2;
        }
        
        return deck;
    }
    
    public void draw_boneyard_card (GameFunctions game){
        int i = 0;
        int j;
        boolean drawnCard = false;
        
        for (j = 0; j < 11; j++){
            if (game.boneyardDeck[j] != -1 && !drawnCard){
                i = game.boneyardDeck[j];
                game.boneyardDeck[j] = -1;
                drawnCard = true;
            }
        }
        
        System.out.println("A boneyard card worth " + i + " was drawn from the deck.");
        this.boneyardTotal += i;
        System.out.println("The total amount drawn from the boneyard deck is " + this.boneyardTotal);
        
        if (this.boneyardTotal > this.numAlivePlayers){
            GameFunctions.zombie_outbreak(game);
        }
        
    }
    
    public static void zombie_outbreak (GameFunctions game){
        int i;
        game.outbreak = true;
        boolean zombieMaster = false;
        
        System.out.println("A zombie outbreak has begun. All dead players have come back to life!");
        
        for (i = 0; i < game.numOfPlayers; i++){
            if (game.playerOrder[i].isDead){
                game.playerOrder[i].lifePoints = game.numAlivePlayers;
                game.playerOrder[i].role = "Zombie";
                game.playerOrder[i].isDead = false;
            }
            else {
                game.playerOrder[i].role = "Survivor";
            }
        }
        
        for (i = 0; i < game.numOfPlayers; i++){
            if ("Renegade".equals(game.playerOrder[i].role) && !zombieMaster){
                game.playerOrder[i].role = "Zombie Master";
                System.out.println(game.playerOrder[i].name + " has turned into the zombie master.");
                zombieMaster = true;
            }
        }
        
        game.realPlayerDead = false;
        game.numAlivePlayers = game.numOfPlayers;
    }
    
    public void eliminate_player (Character player, ArrowPile arrowPile, Boolean killedByPlayer){
        int i;
        int j;
        
        if (player == this.playerOrder[0]){
            this.realPlayerDead = true;
        }
        
        player.isDead = true;
        
        while(player.arrows > 0){
            arrowPile.add_arrow(player);
        }
        
        this.numAlivePlayers -= 1;
        
        //NEW CODE
        
        /*
        for (i = 0; i < this.numOfPlayers; i++){
            if (this.playerOrder[i] == player){
                for (j = i; j < this.numOfPlayers - 1; j++){
                    this.playerOrder[j] = this.playerOrder[j+1];
                }
            }
        }
        
        
        this.currentPlayer = (this.currentPlayer-1)%this.numOfPlayers;
        if (currentPlayer < 0){
            currentPlayer = this.numOfPlayers + currentPlayer;
        }
        
        this.playerOrder[this.numOfPlayers] = null;
        
        this.numOfPlayers -= 1;
        */
        
        this.game_over = this.determine_game_over(this, player, killedByPlayer);
        
        if (!this.game_over){
            for (i = 0; i < this.numOfPlayers; i++){
                if ("Vulture Sam".equals(this.playerOrder[i].name) && !this.playerOrder[i].isDead && !"Zombie".equals(this.playerOrder[i].role)){
                    System.out.println("Vulture Sam gains two life points because another player died.");
                    this.playerOrder[i].gain_life();
                    this.playerOrder[i].gain_life();
                }
            }
        }
    }
    
  
    public boolean determine_game_over (GameFunctions game, Character deadPlayer, Boolean killedByPlayer){
        int sheriffAlive = 0;
        int outlawAlive = 0;
        int renegadeAlive = 0;
        int zombieAlive = 0;
        int survivorAlive = 0;
        int i;
        
        if (!game.outbreak){
            for (i = 0; i < game.numOfPlayers; i++){
                if (!game.playerOrder[i].isDead){
                    if ("Sheriff".equals(game.playerOrder[i].role)){
                        System.out.println("A Sheriff is alive, which is: " + game.playerOrder[i].name + " line 342: GameFunctions");
                        sheriffAlive += 1;
                    }
                    else if ("Outlaw".equals(game.playerOrder[i].role)){
                        System.out.println("An outlaw is alive, which is: " + game.playerOrder[i].name + " line 342: GameFunctions");
                        outlawAlive += 1;
                    }
                    else if ("Renegade".equals(game.playerOrder[i].role)){
                        System.out.println("A renegade is alive, which is: " + game.playerOrder[i].name + " line 342: GameFunctions");
                        renegadeAlive += 1;
                    }
                }
            }
        
            if ((sheriffAlive == 1) && (outlawAlive == 0) && (renegadeAlive == 0)){
                System.out.println("All renegades and outlaws are dead, so the sheriff and deputies win.");
                return true;
            }
            else if (sheriffAlive == 0){
                if ((game.numOfPlayers == 1) && (renegadeAlive == 1)){
                    System.out.println("The renegade is the last one alive, so they win.");
                    return true;
                }
                else {
                    System.out.println("The sheriff is dead, so the outlaws win.");
                    return true;
                }
            }
            else if (game.numOfPlayers == 0){
                System.out.println("All players are dead, so the outlaws win.");
                return true;
            }
            else {
                return false;
            }
        }
        
        else {
            for (i = 0; i < game.numOfPlayers; i++){
                if (!game.playerOrder[i].isDead){
                    if ("Zombie".equals(game.playerOrder[i].role) || "Zombie Master".equals(game.playerOrder[i].role)){
                        zombieAlive += 1;
                    }
                    else{
                        survivorAlive += 1;
                    }
                }
            }
            if (zombieAlive == game.numAlivePlayers){
                System.out.println("The zombies have killed all of the survivors, so they win.");
                return true;
            }
            else if (survivorAlive == game.numAlivePlayers){
                System.out.println("The survivors have killed all of the zombies, so they win.");
                return true;
            }
            else{
                return false;
            }
            
        }
    }
}