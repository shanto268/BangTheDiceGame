/*
 * Cierra Ditmore
 * CS 2365
 */
package bangdicegame;

import static java.lang.Character.toLowerCase;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author cmdma
 */
public class Dice {
    public String roll;
    public String type;
    
    public Dice (){
        this.roll = "";
        this.type = "none";
    }
    
    public void roll_dice(){
        String [] faces = {"Arrow", "Dynamite", "Bull's Eye 1", "Bull's Eye 2", "Beer", "Gatling"};
        String [] loudmouth = {"Dynamite", "Bullet", "Double Bull's Eye 1", "Double Bull's Eye 2", "Arrow", "Double Gatling"};
        String [] coward = {"Dynamite", "Double Beer", "Broken Arrow", "Bull's Eye 1", "Arrow", "Beer"};
        String [] duel = {"Dynamite", "Whiskey", "Fight", "Gatling", "Arrow", "Fight"};
        Random rand = new Random();
        int temp;
       
        temp = rand.nextInt(6);
        
        if ("none".equals(this.type)){
            this.roll = faces[temp];
        }
        else if ("loudmouth".equals(this.type)){
            this.roll = loudmouth[temp];
        }
        else if ("coward".equals(this.type)){
            this.roll = coward[temp];
        }
        else {
            this.roll = duel[temp];
        }
    }
    
    public static int reroll_dice(Dice [] allDice, int rollsRemaining, ArrowPile arrowPile, GameFunctions playerOrder, int totalDice){
        char rerollSelection;
        String diceToReroll;
        
        Scanner input = new Scanner(System.in);
        
        System.out.println("\nYou have " + rollsRemaining + " rolls remaining.");
        System.out.print("Would you like to reroll any dice? (Y/N): ");
        rerollSelection = input.nextLine().charAt(0);
            
        rerollSelection = toLowerCase(rerollSelection);
            
        while (rerollSelection != 'y' && rerollSelection != 'n'){
            System.out.print("Invalid input. Please enter Y/N: ");
            rerollSelection = input.nextLine().charAt(0);
            rerollSelection = toLowerCase(rerollSelection);
        }
            
        if (rerollSelection == 'y'){
            System.out.print("Enter the numbers of the dice you would like to reroll (ex. '1 3 4'): ");
            diceToReroll = input.nextLine();
            
            
            if (diceToReroll.contains("1")){
                if (!"Dynamite".equals(allDice[0].roll) || "Black Jack".equals(playerOrder.get_current_player().name)){
                    allDice[0].roll_dice();
                    if ("Arrow".equals(allDice[0].roll)){
                        System.out.println("You must pick up an arrow before continuing.");
                        Dice.arrow_roll(playerOrder.get_current_player(), arrowPile, playerOrder);
                    }
                    if ("Broken Arrow".equals(allDice[0].roll)){
                        Dice.broken_arrow_roll(playerOrder, arrowPile);
                    }
                    if ("Bullet".equals(allDice[0].roll)){
                        System.out.println("You rolled a bullet and have immediately taken 1 damage.");
                        playerOrder.get_current_player().lose_life();
                    }
                }
                else{
                    System.out.println("You are not allowed to reroll dice " + 1 + ".");
                }
            }
            if (diceToReroll.contains("2")){
                if (!"Dynamite".equals(allDice[1].roll) || "Black Jack".equals(playerOrder.get_current_player().name)){
                    allDice[1].roll_dice();
                    if ("Arrow".equals(allDice[1].roll)){
                        System.out.println("You must pick up an arrow before continuing.");
                        Dice.arrow_roll(playerOrder.get_current_player(), arrowPile, playerOrder);
                    }
                    if ("Broken Arrow".equals(allDice[1].roll)){
                        Dice.broken_arrow_roll(playerOrder, arrowPile);
                    }
                    if ("Bullet".equals(allDice[1].roll)){
                        System.out.println("You rolled a bullet and have immediately taken 1 damage.");
                        playerOrder.get_current_player().lose_life();
                    }
                }
                else{
                    System.out.println("You are not allowed to reroll dice " + 2 + ".");
                }
            }
            if (diceToReroll.contains("3")){
                if (!"Dynamite".equals(allDice[2].roll) || "Black Jack".equals(playerOrder.get_current_player().name)){
                    allDice[2].roll_dice();
                    if ("Arrow".equals(allDice[2].roll)){
                        System.out.println("You must pick up an arrow before continuing.");
                        Dice.arrow_roll(playerOrder.get_current_player(), arrowPile, playerOrder);
                    }
                    if ("Broken Arrow".equals(allDice[2].roll)){
                        Dice.broken_arrow_roll(playerOrder, arrowPile);
                    }
                    if ("Bullet".equals(allDice[2].roll)){
                        System.out.println("You rolled a bullet and have immediately taken 1 damage.");
                        playerOrder.get_current_player().lose_life();
                    }
                }
                else{
                    System.out.println("You are not allowed to reroll dice " + 3 + ".");
                }
            }
            if (diceToReroll.contains("4")){
                if (!"Dynamite".equals(allDice[3].roll) || "Black Jack".equals(playerOrder.get_current_player().name)){
                    allDice[3].roll_dice();
                    if ("Arrow".equals(allDice[3].roll)){
                        System.out.println("You must pick up an arrow before continuing.");
                        Dice.arrow_roll(playerOrder.get_current_player(), arrowPile, playerOrder);
                    }
                    if ("Broken Arrow".equals(allDice[3].roll)){
                        Dice.broken_arrow_roll(playerOrder, arrowPile);
                    }
                    if ("Bullet".equals(allDice[3].roll)){
                        System.out.println("You rolled a bullet and have immediately taken 1 damage.");
                        playerOrder.get_current_player().lose_life();
                    }
                }
                else{
                    System.out.println("You are not allowed to reroll dice " + 4 + ".");
                }
            }
            if (diceToReroll.contains("5")){
                if (!"Dynamite".equals(allDice[4].roll) || "Black Jack".equals(playerOrder.get_current_player().name)){
                    allDice[4].roll_dice();
                    if ("Arrow".equals(allDice[4].roll)){
                        System.out.println("You must pick up an arrow before continuing.");
                        Dice.arrow_roll(playerOrder.get_current_player(), arrowPile, playerOrder);
                    }
                    if ("Broken Arrow".equals(allDice[4].roll)){
                        Dice.broken_arrow_roll(playerOrder, arrowPile);
                    }
                    if ("Bullet".equals(allDice[4].roll)){
                        System.out.println("You rolled a bullet and have immediately taken 1 damage.");
                        playerOrder.get_current_player().lose_life();
                    }
                }
                else{
                    System.out.println("You are not allowed to reroll dice " + 5 + ".");
                }
            }
            if (diceToReroll.contains("6") && totalDice == 6){
                if (!"Dynamite".equals(allDice[5].roll) || "Black Jack".equals(playerOrder.get_current_player().name)){
                    allDice[4].roll_dice();
                    if ("Arrow".equals(allDice[5].roll)){
                        System.out.println("You must pick up an arrow before continuing.");
                        Dice.arrow_roll(playerOrder.get_current_player(), arrowPile, playerOrder);
                    }
                    if ("Broken Arrow".equals(allDice[5].roll)){
                        Dice.broken_arrow_roll(playerOrder, arrowPile);
                    }
                    if ("Bullet".equals(allDice[5].roll)){
                        System.out.println("You rolled a bullet and have immediately taken 1 damage.");
                        playerOrder.get_current_player().lose_life();
                    }
                }
                else{
                    System.out.println("You are not allowed to reroll dice " + 6 + ".");
                }
            }
            
            return (rollsRemaining -= 1);
        }
        
        else {
            return (0);
        }
    }
    
    
    public static void arrow_roll(Character player, ArrowPile pile, GameFunctions playerOrder){
        pile.remove_arrow(playerOrder);
    }
    
    public static Boolean dynamite_roll (Dice [] dice, Character player, GameFunctions playerOrder, ArrowPile arrowPile){
        int count = 0;
        int i;
        
        for (i = 0; i < 5; i ++){
            if ("Dynamite".equals(dice[i].roll)){
                count += 1;
            }
        }
        
        if (count >= 3){
            player.lose_life(playerOrder, arrowPile, true);
            System.out.println("You lost one life point to dynamite, and your turn is over.");
            return true;
        }
        
        else{
            return false;
        }
    }
    
    public static void bullsEye1_roll (GameFunctions playerOrder, ArrowPile arrowPile){
        Character nextPlayer = playerOrder.get_next_player();
        Character previousPlayer = playerOrder.get_previous_player();
        String enteredPlayer;
        
        Scanner input = new Scanner(System.in);
        
        if (nextPlayer == previousPlayer){
            enteredPlayer = "";
            System.out.println("You shot " + nextPlayer.name + ".");
            nextPlayer.lose_life(playerOrder, arrowPile, false);
            
            if ("El Gringo".equals(nextPlayer.name)){
                arrowPile.remove_arrow(playerOrder);
                System.out.println(playerOrder.get_current_player().name + " made El Gringo lose a life point, so they have taken an arrow.");
            }
        }
        
        else {
            System.out.print("Would you like to shoot " + nextPlayer.name + " or " + previousPlayer.name + "? : ");
            enteredPlayer = input.nextLine();
          
            while (!enteredPlayer.equals(nextPlayer.name) && !enteredPlayer.equals(previousPlayer.name)){
                System.out.print("Invalid input. Please enter one of the player names: ");
                enteredPlayer = input.nextLine();
            }
            
            if (enteredPlayer.equals(nextPlayer.name)){
                nextPlayer.lose_life(playerOrder, arrowPile, false);
                
                //NEW CODE
                if ("Sheriff".equals(nextPlayer.role)){
                    playerOrder.get_current_player().numShotSheriff+= 1;
                }
            }
            else if (enteredPlayer.equals(previousPlayer.name)){
                previousPlayer.lose_life(playerOrder, arrowPile, false);
                
                //NEW CODE
                if ("Sheriff".equals(previousPlayer.role)){
                    playerOrder.get_current_player().numShotSheriff += 1;
                }
            }
            
        }
        
        if ("El Gringo".equals(enteredPlayer)){
            arrowPile.remove_arrow(playerOrder);
            System.out.println(playerOrder.get_current_player().name + " made El Gringo lose a life point, so they have taken an arrow.");
        }
    }
    
    public static void bullsEye2_roll (GameFunctions playerOrder, ArrowPile arrowPile){
        Character nextPlayer = playerOrder.get_two_away_player(playerOrder.get_current_player());
        Character previousPlayer = playerOrder.get_two_before_player(playerOrder.get_current_player());
        String enteredPlayer;
        
        Scanner input = new Scanner(System.in);
        
        if (nextPlayer == previousPlayer){
            enteredPlayer = "";
            System.out.println("You shot " + nextPlayer.name + ".");
            nextPlayer.lose_life(playerOrder, arrowPile, false);

            if ("El Gringo".equals(nextPlayer.name)){
                arrowPile.remove_arrow(playerOrder);
                System.out.println(playerOrder.get_current_player().name + " made El Gringo lose a life point, so they have taken an arrow.");
            }
        }
        
        else {

            System.out.print("Would you like to shoot " + nextPlayer.name + " or " + previousPlayer.name + "? : ");
            enteredPlayer = input.nextLine();
            
            while (!enteredPlayer.equals(nextPlayer.name) && !enteredPlayer.equals(previousPlayer.name)){
                System.out.print("Invalid input. Please enter one of the player names: ");
                enteredPlayer = input.nextLine();
            }
            
            if (enteredPlayer.equals(nextPlayer.name)){
                nextPlayer.lose_life(playerOrder, arrowPile, false);
                
                //NEW CODE
                if ("Sheriff".equals(nextPlayer.role)){
                    playerOrder.get_current_player().numShotSheriff += 1;
                }
            }
            else if (enteredPlayer.equals(previousPlayer.name)){
                previousPlayer.lose_life(playerOrder, arrowPile, false);
                
                //NEW CODE
                if ("Sheriff".equals(previousPlayer.role)){
                    playerOrder.get_current_player().numShotSheriff += 1;
                }
            }
        }
        
        if ("El Gringo".equals(enteredPlayer)){
            arrowPile.remove_arrow(playerOrder);
            System.out.println(playerOrder.get_current_player().name + " made El Gringo lose a life point, so they have taken an arrow.");
        }
    }
        
    
    public static void beer_roll (GameFunctions playerOrder){
        String enteredCharacter;
        Boolean servedBeer = false;
        int i;
        
        Scanner input = new Scanner(System.in);
        
        System.out.print("Enter the character name of the person you want to give the beer to (You may enter your own name): ");
        enteredCharacter = input.nextLine();
        
        while (!servedBeer){
            for (i = 0; i < playerOrder.numOfPlayers; i ++){
                if (enteredCharacter.equals(playerOrder.playerOrder[i].name)){
                    playerOrder.playerOrder[i].gain_life();
                    
                    //NEW CODE
                    if ("Sheriff".equals(playerOrder.playerOrder[i].role)){
                        playerOrder.get_current_player().numHelpSheriff += 1;
                    }
                    
                    servedBeer = true;
                }
            }
            if (!servedBeer){
                System.out.print("Could not find character name. Please try again: ");
                enteredCharacter = input.nextLine();
            }
        }
        
        
    }
    
    public static void gatling_roll (Dice [] dice, Character player, GameFunctions playerOrder, ArrowPile arrowPile){
        int count = 0;
        int i;
        
        for (i = 0; i < 5; i ++){
            if ("Gatling".equals(dice[i].roll)){
                count += 1;
            }
        }
        
        if (count >= 3){
            int playersDead = 0;
            
            System.out.println("\nYou successfully used the gatling gun");
        
            for (i = 0; i < playerOrder.numOfPlayers; i++){
                if (playerOrder.playerOrder[i].arrows > playerOrder.playerOrder[i].lifePoints){
                    playersDead++;
                }
            }
        
            if (playersDead >= playerOrder.numOfPlayers){
                playerOrder.game_over = true;
                System.out.println("All players are dead, so the outlaws win.");
            }
            
            if (!playerOrder.game_over){
                for (i = 0; i < playerOrder.numOfPlayers; i++){
                    if ((playerOrder.playerOrder[i] != player) && (!"Paul Regret".equals(playerOrder.playerOrder[i].name))){
                       playerOrder.playerOrder[i].lose_life(playerOrder, arrowPile, false);
                    
                        if ("El Gringo".equals(playerOrder.playerOrder[i].name)){
                            arrowPile.remove_arrow(playerOrder);
                            System.out.println(playerOrder.get_current_player().name + " made El Gringo lose a life point, so they have taken an arrow.");
                        }
                    }
                }
            }
            while(player.arrows > 0){
                arrowPile.add_arrow(player);
            }
        }
    }
    
    public static void broken_arrow_roll (GameFunctions playerOrder, ArrowPile arrowPile){
        String enteredCharacter;
        Boolean removedArrow = false;
        int i;
        
        Scanner input = new Scanner(System.in);
        
        System.out.print("You rolled a broken arrow. Enter the name of the character you want to remove an arrow from (You may enter your own name): ");
        enteredCharacter = input.nextLine();
        
        while (!removedArrow){
            for (i = 0; i < playerOrder.numOfPlayers; i ++){
                if (enteredCharacter.equals(playerOrder.playerOrder[i].name)){
                    if (playerOrder.playerOrder[i].arrows > 0){
                        arrowPile.add_arrow(playerOrder.playerOrder[i]);
                        System.out.println(enteredCharacter + " returned 1 arrow to the pile.");
                    }
                    else{
                        System.out.println(enteredCharacter + " did not have any arrows to return to the pile.");
                    }
                    removedArrow = true;
                }
            }
            if (!removedArrow){
                System.out.print("Could not find character name. Please try again: ");
                enteredCharacter = input.nextLine();
            }
        }
    }
    
    public static void fight_roll (GameFunctions playerOrder){
        Scanner input = new Scanner(System.in);
        String otherPlayer;
        Boolean completedDuel;
        Boolean rolledFight;
        int i;
        
        Dice dice = new Dice();
        dice.type = "duel";
        
        completedDuel = false;
        rolledFight = false;
        
        System.out.println("Who would you like to challenge to a duel? (Enter player name): ");
        otherPlayer = input.nextLine();
        
        while (!completedDuel){
            for (i = 0; i < playerOrder.numOfPlayers; i++){
                if (otherPlayer.equals(playerOrder.playerOrder[i].name) && !otherPlayer.equals(playerOrder.get_current_player().name)){
                    while (!rolledFight){
                        dice.roll_dice();
                        System.out.println(otherPlayer + " rolled their dice and rolled a " + dice.roll);
                        if ("Fight".equals(dice.roll)){
                            System.out.println(otherPlayer + " won the duel, and " + playerOrder.get_current_player().name + " lost a life point.");
                            playerOrder.get_current_player().lose_life();
                            rolledFight = true;
                        }
                        else{
                            dice.roll_dice();
                            System.out.println(playerOrder.get_current_player().name + " rolled their dice and rolled a " + dice.roll);
                            if ("Fight".equals(dice.roll)){
                                System.out.println(playerOrder.get_current_player().name + " won the duel, and " + otherPlayer + " lost a life point.");
                                playerOrder.playerOrder[i].lose_life();
                                rolledFight = true;
                            }
                        }
                    }
                    completedDuel = true;
                }
            }
            if (!completedDuel){
                System.out.print("Could not find character name. Please try again: ");
                otherPlayer = input.nextLine();
            }
        }
    }
}

