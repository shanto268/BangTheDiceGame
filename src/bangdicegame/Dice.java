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
    
    public Dice (){
        this.roll = "";
    }
    
    public void roll_dice(){
        String [] faces = {"Arrow", "Dynamite", "Bull's Eye 1", "Bull's Eye 2", "Beer", "Gatling"};
        Random rand = new Random();
        int temp;
        
        temp = rand.nextInt(6);
        
        this.roll = faces[temp];
    }
    
    public static int reroll_dice(Dice [] allDice, int rollsRemaining, ArrowPile arrowPile, GameFunctions playerOrder){
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
                }
                else{
                    System.out.println("You are not allowed to reroll dice " + 5 + ".");
                }
            }
            
            return (rollsRemaining -= 1);
        }
        
        else {
            return (0);
        }
    }
    
    
    public static void arrow_roll(Character player, ArrowPile pile, GameFunctions playerOrder){
        player.gain_arrow();
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
    
    public static void bullsEye1_roll (GameFunctions playerOrder, ArrowPile arrowPile, Boolean doubleDamage){
        Character nextPlayer = playerOrder.get_next_player();
        Character previousPlayer = playerOrder.get_previous_player();
        String enteredPlayer;
        
        Scanner input = new Scanner(System.in);
        
        if (nextPlayer == previousPlayer){
            enteredPlayer = "";
            System.out.println("You shot " + nextPlayer.name + ".");
            nextPlayer.lose_life(playerOrder, arrowPile, false);
            
            if (doubleDamage){
                nextPlayer.lose_life(playerOrder, arrowPile, false);
            }
            
            if ("El Gringo".equals(nextPlayer.name)){
                playerOrder.get_current_player().gain_arrow();
                arrowPile.remove_arrow(playerOrder);
                System.out.println(playerOrder.get_current_player().name + " made El Gringo lose a life point, so they have taken an arrow.");
            }
        }
        
        else if ("Calamity Janet".equals(playerOrder.get_current_player().name)){
            Dice.calamity_rose1_roll(playerOrder, arrowPile);
            enteredPlayer = "";
        }
        
        else if ("Rose Doolan".equals(playerOrder.get_current_player().name)){
            Dice.calamity_rose1_roll(playerOrder, arrowPile);
            enteredPlayer = "";
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
                
                if (doubleDamage){
                    nextPlayer.lose_life(playerOrder, arrowPile, false);
                }
            }
            else if (enteredPlayer.equals(previousPlayer.name)){
                previousPlayer.lose_life(playerOrder, arrowPile, false);
                
                if (doubleDamage){
                    previousPlayer.lose_life(playerOrder, arrowPile, false);
                }
            } 
        }
        
        if ("El Gringo".equals(enteredPlayer)){
            playerOrder.get_current_player().gain_arrow();
            arrowPile.remove_arrow(playerOrder);
            System.out.println(playerOrder.get_current_player().name + " made El Gringo lose a life point, so they have taken an arrow.");
        }
    }
    
    public static void bullsEye2_roll (GameFunctions playerOrder, ArrowPile arrowPile, Boolean doubleDamage){
        Character nextPlayer = playerOrder.get_two_away_player(playerOrder.get_current_player());
        Character previousPlayer = playerOrder.get_two_before_player(playerOrder.get_current_player());
        String enteredPlayer;
        
        Scanner input = new Scanner(System.in);
        
        if (nextPlayer == previousPlayer){
            enteredPlayer = "";
            System.out.println("You shot " + nextPlayer.name + ".");
            nextPlayer.lose_life(playerOrder, arrowPile, false);
            
            if (doubleDamage){
                    nextPlayer.lose_life(playerOrder, arrowPile, false);
                }
            
            if ("El Gringo".equals(nextPlayer.name)){
                playerOrder.get_current_player().gain_arrow();
                arrowPile.remove_arrow(playerOrder);
                System.out.println(playerOrder.get_current_player().name + " made El Gringo lose a life point, so they have taken an arrow.");
            }
        }
        
        else if ("Calamity Janet".equals(playerOrder.get_current_player().name)){
            Dice.calamity_rose1_roll(playerOrder, arrowPile);
            enteredPlayer = "";
        }
        
        else if ("Rose Doolan".equals(playerOrder.get_current_player().name)){
            Dice.rose_roll_2(playerOrder, arrowPile);
            enteredPlayer = "";
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
                
                if (doubleDamage){
                    nextPlayer.lose_life(playerOrder, arrowPile, false);
                }
            }
            else if (enteredPlayer.equals(previousPlayer.name)){
                previousPlayer.lose_life(playerOrder, arrowPile, false);
                
                if (doubleDamage){
                    previousPlayer.lose_life(playerOrder, arrowPile, false);
                }
            }
        }
        
        if ("El Gringo".equals(enteredPlayer)){
            playerOrder.get_current_player().gain_arrow();
            arrowPile.remove_arrow(playerOrder);
            System.out.println(playerOrder.get_current_player().name + " made El Gringo lose a life point, so they have taken an arrow.");
        }
    }
    
    public static void calamity_rose1_roll (GameFunctions playerOrder, ArrowPile arrowPile){
        Character nextPlayer = playerOrder.get_next_player();
        Character previousPlayer = playerOrder.get_previous_player();
        Character next2Player = playerOrder.get_two_away_player(playerOrder.get_current_player());
        Character previous2Player = playerOrder.get_two_before_player(playerOrder.get_current_player());
        String enteredPlayer;
        
        Scanner input = new Scanner(System.in);
            
        if (playerOrder.numOfPlayers >= 5){
            System.out.print("Would you like to shoot " + nextPlayer.name + ", " + previousPlayer.name + ", " + next2Player.name + ", or " + previous2Player.name + "? : ");
            enteredPlayer = input.nextLine();
            
            while (!enteredPlayer.equals(nextPlayer.name) && !enteredPlayer.equals(previousPlayer.name) && !enteredPlayer.equals(next2Player.name) && !enteredPlayer.equals(previous2Player.name)){
                System.out.print("Invalid input. Please enter one of the player names: ");
                enteredPlayer = input.nextLine();
            }
        }
        else if (playerOrder.numOfPlayers >= 4){
            System.out.print("Would you like to shoot " + nextPlayer.name + ", " + previousPlayer.name + ", or " + previous2Player.name + "? : ");
            enteredPlayer = input.nextLine();
           
            while (!enteredPlayer.equals(nextPlayer.name) && !enteredPlayer.equals(previousPlayer.name) && !enteredPlayer.equals(previous2Player.name)){
                System.out.print("Invalid input. Please enter one of the player names: ");
                enteredPlayer = input.nextLine();
            }
        }
        else {
            System.out.print("Would you like to shoot " + nextPlayer.name + " or " + previousPlayer.name + "? : ");
            enteredPlayer = input.nextLine();
          
            while (!enteredPlayer.equals(nextPlayer.name) && !enteredPlayer.equals(previousPlayer.name)){
                System.out.print("Invalid input. Please enter one of the player names: ");
                    enteredPlayer = input.nextLine();
            } 
        }     
            
        if (enteredPlayer.equals(nextPlayer.name)){
            nextPlayer.lose_life(playerOrder, arrowPile, false);
        }
        else if (enteredPlayer.equals(previousPlayer.name)){
            previousPlayer.lose_life(playerOrder, arrowPile, false);
        } 
        else if (enteredPlayer.equals(next2Player.name)){
            next2Player.lose_life(playerOrder, arrowPile, false);
        }
        else if (enteredPlayer.equals(previous2Player.name)){
            previous2Player.lose_life(playerOrder, arrowPile, false);
        }
        
        if ("El Gringo".equals(enteredPlayer)){
            playerOrder.get_current_player().gain_arrow();
            arrowPile.remove_arrow(playerOrder);
            System.out.println(playerOrder.get_current_player().name + " made El Gringo lose a life point, so they have taken an arrow.");
        }
    }
    
    public static void rose_roll_2 (GameFunctions playerOrder, ArrowPile arrowPile){
        Character nextPlayer = playerOrder.get_two_away_player(playerOrder.get_current_player());
        Character previousPlayer = playerOrder.get_two_before_player(playerOrder.get_current_player());
        Character next2Player = playerOrder.get_two_away_player(playerOrder.get_next_player());
        Character previous2Player = playerOrder.get_two_before_player(playerOrder.get_previous_player());
        String enteredPlayer;
        
        Scanner input = new Scanner(System.in);
        
        if (playerOrder.numOfPlayers >= 7){
            System.out.print("Would you like to shoot " + nextPlayer.name + ", " + previousPlayer.name + ", " + next2Player.name + ", or " + previous2Player.name + "? : ");
            enteredPlayer = input.nextLine();
            
            while (!enteredPlayer.equals(nextPlayer.name) && !enteredPlayer.equals(previousPlayer.name) && !enteredPlayer.equals(next2Player.name) && !enteredPlayer.equals(previous2Player.name)){
                System.out.print("Invalid input. Please enter one of the player names: ");
                enteredPlayer = input.nextLine();
            }
        }
        else if (playerOrder.numOfPlayers >= 6){
            System.out.print("Would you like to shoot " + nextPlayer.name + ", " + previousPlayer.name + ", or " + previous2Player.name + "? : ");
            enteredPlayer = input.nextLine();
           
            while (!enteredPlayer.equals(nextPlayer.name) && !enteredPlayer.equals(previousPlayer.name) && !enteredPlayer.equals(previous2Player.name)){
                System.out.print("Invalid input. Please enter one of the player names: ");
                enteredPlayer = input.nextLine();
            }
        }
        else {
            System.out.print("Would you like to shoot " + nextPlayer.name + " or " + previousPlayer.name + "? : ");
            enteredPlayer = input.nextLine();
          
            while (!enteredPlayer.equals(nextPlayer.name) && !enteredPlayer.equals(previousPlayer.name)){
                System.out.print("Invalid input. Please enter one of the player names: ");
                    enteredPlayer = input.nextLine();
            } 
        }     
            
        if (enteredPlayer.equals(nextPlayer.name)){
            nextPlayer.lose_life(playerOrder, arrowPile, false);
        }
        else if (enteredPlayer.equals(previousPlayer.name)){
            previousPlayer.lose_life(playerOrder, arrowPile, false);
        } 
        else if (enteredPlayer.equals(next2Player.name)){
            next2Player.lose_life(playerOrder, arrowPile, false);
        }
        else if (enteredPlayer.equals(previous2Player.name)){
            previous2Player.lose_life(playerOrder, arrowPile, false);
        }
        
        if ("El Gringo".equals(enteredPlayer)){
            playerOrder.get_current_player().gain_arrow();
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
                    servedBeer = true;
                }
            }
            while (!servedBeer){
                System.out.print("Could not find character name. Please try again: ");
                enteredCharacter = input.nextLine();
            }
        }
        
        
    }
    
    public static void gatling_roll (Dice [] dice, Character player, GameFunctions playerOrder, ArrowPile arrowPile){
        int count = 0;
        int tempCount = 0;
        int i;
        Scanner input = new Scanner(System.in);
        String enteredPlayer;
        Boolean removedArrow = false;
        
        for (i = 0; i < 5; i ++){
            if ("Gatling".equals(dice[i].roll)){
                count += 1;
                tempCount += 1;
            }
        }
        
        if ("Kit Carlson".equals(player.name)){
            while (tempCount > 0){
                System.out.print("Kit Carlson, you rolled a gatling, which player would you like to remove an arrow from? (You may enter your own name): ");
                enteredPlayer = input.nextLine();
                
                while (!removedArrow){
                    for (i = 0; i < playerOrder.numOfPlayers; i ++){
                        if (enteredPlayer.equals(playerOrder.playerOrder[i].name)){
                            playerOrder.playerOrder[i].lose_arrow();
                            removedArrow = true;
                        }
                    }
                    while (!removedArrow){
                        System.out.print("Could not find character name. Please try again: ");
                        enteredPlayer = input.nextLine();
                    }
                }
                removedArrow = false;
            }
        }
        
        if ("Willy the Kid".equals(playerOrder.get_current_player().name)){
            if (count == 2){
                count = 3;
            }
        }
        
        if (count >= 3){
            for (i = 0; i < playerOrder.numOfPlayers; i++){
                if ((playerOrder.playerOrder[i] != player) && (!"Paul Regret".equals(playerOrder.playerOrder[i].name))){
                    playerOrder.playerOrder[i].lose_life(playerOrder, arrowPile, false);
                    
                    if ("El Gringo".equals(playerOrder.playerOrder[i].name)){
                        playerOrder.get_current_player().gain_arrow();
                        arrowPile.remove_arrow(playerOrder);
                        System.out.println(playerOrder.get_current_player().name + " made El Gringo lose a life point, so they have taken an arrow.");
                    }
                }
            }
            while(player.arrows > 0){
                arrowPile.add_arrow(player);
            }
            System.out.println("You successfully used the gatling gun");
        }
    }
}
