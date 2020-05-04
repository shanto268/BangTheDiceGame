/*
 * Cierra Ditmore
 * CS 2365
 */
package bangdicegame;

import static java.lang.Character.toLowerCase;
import java.util.Random;
import java.util.Scanner;

/**
 * Builds and takes care of dice actions for human player
 */

public class Dice {

    /**
     *  string to hold the roll of the dice. ex: dynamite, arrow, etc
     */
    public String roll;

    /**
     *  type of the dice (loudmouth, duel, etc)
     */
    public String type;
    
    /**
     *  creates the dice object
     */
    public Dice (){
        this.roll = "";
        this.type = "none";
    }
    
    /**
     *  rolls the dice, selects which faces to roll from depending on the type of the dice
     *  randomly selects one String from the necessary array and assigns it to that dice
     */
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
    
    /**
     * Rerolls the dice according to player selection
     *
     * @param allDice   array of all the dice that are capable of being rolled
     * @param rollsRemaining    the amount of rolls remaining for that character
     * @param arrowPile arrowPile object
     * @param game  game object
     * @param totalDice total dice that were rolled (3,5, or 6)
     * @return number of times left to reroll dice
     */
    public static int reroll_dice(Dice [] allDice, int rollsRemaining, ArrowPile arrowPile, GameFunctions game, int totalDice){
        char rerollSelection; //Y or N for reroll choice
        String diceToReroll; //string input for numbers of dice to reroll
        
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
            
            //rerolls dice 1, and takes care of any instant actions from arrows, broken arrows, and bullets
            //prevents dynamite from being rerolled unless the character is Black jack (special ability)
            if (diceToReroll.contains("1")){
                if (!"Dynamite".equals(allDice[0].roll) || "Black Jack".equals(game.get_current_player().name)){
                    allDice[0].roll_dice();
                    if ("Arrow".equals(allDice[0].roll)){
                        System.out.println("You must pick up an arrow before continuing.");
                        Dice.arrow_roll(arrowPile, game);
                    }
                    if ("Broken Arrow".equals(allDice[0].roll)){
                        Dice.broken_arrow_roll(game, arrowPile);
                    }
                    if ("Bullet".equals(allDice[0].roll)){
                        System.out.println("You rolled a bullet and have immediately taken 1 damage.");
                        game.get_current_player().lose_life(game, arrowPile, true);
                    }
                }
                else{
                    System.out.println("You are not allowed to reroll dice " + 1 + ".");
                }
            }
            //rerolls dice 2, and takes care of any instant actions from arrows, broken arrows, and bullets
            //prevents dynamite from being rerolled unless the character is Black jack (special ability)
            if (diceToReroll.contains("2")){
                if (!"Dynamite".equals(allDice[1].roll) || "Black Jack".equals(game.get_current_player().name)){
                    allDice[1].roll_dice();
                    if ("Arrow".equals(allDice[1].roll)){
                        System.out.println("You must pick up an arrow before continuing.");
                        Dice.arrow_roll(arrowPile, game);
                    }
                    if ("Broken Arrow".equals(allDice[1].roll)){
                        Dice.broken_arrow_roll(game, arrowPile);
                    }
                    if ("Bullet".equals(allDice[1].roll)){
                        System.out.println("You rolled a bullet and have immediately taken 1 damage.");
                        game.get_current_player().lose_life(game, arrowPile, true);
                    }
                }
                else{
                    System.out.println("You are not allowed to reroll dice " + 2 + ".");
                }
            }
            //rerolls dice 3, and takes care of any instant actions from arrows, broken arrows, and bullets
            //prevents dynamite from being rerolled unless the character is Black jack (special ability)
            if (diceToReroll.contains("3")){
                if (!"Dynamite".equals(allDice[2].roll) || "Black Jack".equals(game.get_current_player().name)){
                    allDice[2].roll_dice();
                    if ("Arrow".equals(allDice[2].roll)){
                        System.out.println("You must pick up an arrow before continuing.");
                        Dice.arrow_roll(arrowPile, game);
                    }
                    if ("Broken Arrow".equals(allDice[2].roll)){
                        Dice.broken_arrow_roll(game, arrowPile);
                    }
                    if ("Bullet".equals(allDice[2].roll)){
                        System.out.println("You rolled a bullet and have immediately taken 1 damage.");
                        game.get_current_player().lose_life(game, arrowPile, true);
                    }
                }
                else{
                    System.out.println("You are not allowed to reroll dice " + 3 + ".");
                }
            }
            //rerolls dice 4, and takes care of any instant actions from arrows, broken arrows, and bullets
            //prevents dynamite from being rerolled unless the character is Black jack (special ability)
            if (diceToReroll.contains("4")){
                if (!"Dynamite".equals(allDice[3].roll) || "Black Jack".equals(game.get_current_player().name)){
                    allDice[3].roll_dice();
                    if ("Arrow".equals(allDice[3].roll)){
                        System.out.println("You must pick up an arrow before continuing.");
                        Dice.arrow_roll(arrowPile, game);
                    }
                    if ("Broken Arrow".equals(allDice[3].roll)){
                        Dice.broken_arrow_roll(game, arrowPile);
                    }
                    if ("Bullet".equals(allDice[3].roll)){
                        System.out.println("You rolled a bullet and have immediately taken 1 damage.");
                        game.get_current_player().lose_life(game, arrowPile, true);
                    }
                }
                else{
                    System.out.println("You are not allowed to reroll dice " + 4 + ".");
                }
            }
            //rerolls dice 5, and takes care of any instant actions from arrows, broken arrows, and bullets
            //prevents dynamite from being rerolled unless the character is Black jack (special ability)
            if (diceToReroll.contains("5")){
                if (!"Dynamite".equals(allDice[4].roll) || "Black Jack".equals(game.get_current_player().name)){
                    allDice[4].roll_dice();
                    if ("Arrow".equals(allDice[4].roll)){
                        System.out.println("You must pick up an arrow before continuing.");
                        Dice.arrow_roll(arrowPile, game);
                    }
                    if ("Broken Arrow".equals(allDice[4].roll)){
                        Dice.broken_arrow_roll(game, arrowPile);
                    }
                    if ("Bullet".equals(allDice[4].roll)){
                        System.out.println("You rolled a bullet and have immediately taken 1 damage.");
                        game.get_current_player().lose_life(game, arrowPile, true);
                    }
                }
                else{
                    System.out.println("You are not allowed to reroll dice " + 5 + ".");
                }
            }
            //rerolls dice 6, and takes care of any instant actions from arrows, broken arrows, and bullets
            //prevents dynamite from being rerolled unless the character is Black jack (special ability)
            if (diceToReroll.contains("6") && totalDice == 6){
                if (!"Dynamite".equals(allDice[5].roll) || "Black Jack".equals(game.get_current_player().name)){
                    allDice[5].roll_dice();
                    if ("Arrow".equals(allDice[5].roll)){
                        System.out.println("You must pick up an arrow before continuing.");
                        Dice.arrow_roll(arrowPile, game);
                    }
                    if ("Broken Arrow".equals(allDice[5].roll)){
                        Dice.broken_arrow_roll(game, arrowPile);
                    }
                    if ("Bullet".equals(allDice[5].roll)){
                        System.out.println("You rolled a bullet and have immediately taken 1 damage.");
                        game.get_current_player().lose_life(game, arrowPile, true);
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
    
    /**
     * takes care of actions associated with rolling an arrow
     *
     * @param pile arrowPile object
     * @param game game object
     */
    public static void arrow_roll(ArrowPile pile, GameFunctions game){
        pile.remove_arrow(game);
    }
    
    /**
     * takes care of actions associated with rolling dynamite
     *
     * @param dice  array of all dice rolled
     * @param player    current player
     * @param game  gameFunctions object
     * @param arrowPile arrow pile object
     * @return true if dynamite executed
     */
    public static Boolean dynamite_roll (Dice [] dice, Character player, GameFunctions game, ArrowPile arrowPile){
        int count = 0;
        int i;
        
        //counts how many times a dynamite was rolled across all dice
        for (i = 0; i < 5; i ++){
            if ("Dynamite".equals(dice[i].roll)){
                count += 1;
            }
        }
        
        //if they rolled 3 or more, lose life and end turn
        if (count >= 3){
            player.lose_life(game, arrowPile, true);
            System.out.println("You lost one life point to dynamite, and your turn is over.");
            return true;
        }
        
        else{
            return false;
        }
    }
    
    /**
     * takes care of rolling a bull's eye 1
     *
     * @param game gameFunctions object
     * @param arrowPile arrowPile object
     */
    public static void bullsEye1_roll (GameFunctions game, ArrowPile arrowPile){
        Character nextPlayer = game.get_next_player(); //player to right of current player
        Character previousPlayer = game.get_previous_player(); //player to left of current player
        String enteredPlayer; //string for entered player
        
        Scanner input = new Scanner(System.in);
        
        //if there is only 1 other player alive, automatically shoots them
        if (nextPlayer == previousPlayer){
            enteredPlayer = "";
            System.out.println("You shot " + nextPlayer.name + ".");
            nextPlayer.lose_life(game, arrowPile, false);
            
            //El gringo special ability - make player that hurt them take an arrow
            if ("El Gringo".equals(nextPlayer.name)){
                arrowPile.remove_arrow(game);
                System.out.println(game.get_current_player().name + " made El Gringo lose a life point, so they have taken an arrow.");
            }
        }
        
        else {
            //User input for which player to shoot
            System.out.print("Would you like to shoot " + nextPlayer.name + " or " + previousPlayer.name + "? : ");
            enteredPlayer = input.nextLine();
          
            while (!enteredPlayer.equals(nextPlayer.name) && !enteredPlayer.equals(previousPlayer.name)){
                System.out.print("Invalid input. Please enter one of the player names: ");
                enteredPlayer = input.nextLine();
            }
            
            //makes entered player lose a life point
            if (enteredPlayer.equals(nextPlayer.name)){
                nextPlayer.lose_life(game, arrowPile, false);
                
                if ("Sheriff".equals(nextPlayer.role)){
                    game.get_current_player().numShotSheriff+= 1;
                }
            }
            else if (enteredPlayer.equals(previousPlayer.name)){
                previousPlayer.lose_life(game, arrowPile, false);
                
                if ("Sheriff".equals(previousPlayer.role)){
                    game.get_current_player().numShotSheriff += 1;
                }
            }
            
        }
        
        //El gringo special ability - make player that hurt them take an arrow
        if ("El Gringo".equals(enteredPlayer)){
            arrowPile.remove_arrow(game);
            System.out.println(game.get_current_player().name + " made El Gringo lose a life point, so they have taken an arrow.");
        }
    }
    
    /**
     * takes care of bull's eye 2 roll
     *
     * @param game gameFunctions object
     * @param arrowPile arrowPile object
     */
    public static void bullsEye2_roll (GameFunctions game, ArrowPile arrowPile){
        Character nextPlayer = game.get_two_away_player(game.get_current_player()); //player 2 to the right of current player
        Character previousPlayer = game.get_two_before_player(game.get_current_player()); //player 2 to the left of current player
        String enteredPlayer;
        
        Scanner input = new Scanner(System.in);
        
        //if the two players are the same, automaically shoots them
        if (nextPlayer == previousPlayer){
            enteredPlayer = "";
            System.out.println("You shot " + nextPlayer.name + ".");
            nextPlayer.lose_life(game, arrowPile, false);

            //El gringo special ability - make player that hurt them take an arrow
            if ("El Gringo".equals(nextPlayer.name)){
                arrowPile.remove_arrow(game);
                System.out.println(game.get_current_player().name + " made El Gringo lose a life point, so they have taken an arrow.");
            }
        }
        
        else {
            //User input
            System.out.print("Would you like to shoot " + nextPlayer.name + " or " + previousPlayer.name + "? : ");
            enteredPlayer = input.nextLine();
            
            while (!enteredPlayer.equals(nextPlayer.name) && !enteredPlayer.equals(previousPlayer.name)){
                System.out.print("Invalid input. Please enter one of the player names: ");
                enteredPlayer = input.nextLine();
            }
            
            //makes entered player lose a life point
            if (enteredPlayer.equals(nextPlayer.name)){
                nextPlayer.lose_life(game, arrowPile, false);
                
                //NEW CODE
                if ("Sheriff".equals(nextPlayer.role)){
                    game.get_current_player().numShotSheriff += 1;
                }
            }
            else if (enteredPlayer.equals(previousPlayer.name)){
                previousPlayer.lose_life(game, arrowPile, false);
                
                //NEW CODE
                if ("Sheriff".equals(previousPlayer.role)){
                    game.get_current_player().numShotSheriff += 1;
                }
            }
        }
        
        //El gringo special ability - make player that hurt them take an arrow
        if ("El Gringo".equals(enteredPlayer)){
            arrowPile.remove_arrow(game);
            System.out.println(game.get_current_player().name + " made El Gringo lose a life point, so they have taken an arrow.");
        }
    }
        
    /**
     * takes care of beer roll
     *
     * @param game gameFunctions object
     */
    public static void beer_roll (GameFunctions game){
        String enteredCharacter; //entered character name
        Boolean servedBeer = false; //to keep track of if the beer has been successfully served
        int i; //counter
        
        Scanner input = new Scanner(System.in);
        
        //user input
        System.out.print("Enter the character name of the person you want to give the beer to (You may enter your own name): ");
        enteredCharacter = input.nextLine();
        
        //loops until beer is given to someone
        while (!servedBeer){
            for (i = 0; i < game.numOfPlayers; i ++){
                //adds 1 to player's life points
                if (enteredCharacter.equals(game.playerOrder[i].name) && !game.playerOrder[i].isDead){
                    game.playerOrder[i].gain_life();
                    
                    if ("Sheriff".equals(game.playerOrder[i].role)){
                        game.get_current_player().numHelpSheriff += 1;
                    }
                    
                    servedBeer = true;
                }
            }
            //allows user to reenter name if there was an arrow
            if (!servedBeer){
                System.out.print("Could not find character name, or that player is unable to drink beer. Please try again: ");
                enteredCharacter = input.nextLine();
            }
        }
        
        
    }
    
    /**
     * resolves gatlings that were rolled
     *
     * @param dice array of all dice rolled
     * @param player current player
     * @param game gameFunctions object
     * @param arrowPile arrowPile object
     */
    public static void gatling_roll (Dice [] dice, Character player, GameFunctions game, ArrowPile arrowPile){
        int count = 0; //number of gatlings rolled
        int i; //counter
        
        //determines how many gatlings were rolled
        for (i = 0; i < 5; i ++){
            if ("Gatling".equals(dice[i].roll)){
                count += 1;
            }
        }
        
        //if they rolled 3 or more, resolves gatling
        if (count >= 3){
            int playersDead = 0;
            
            System.out.println("\nYou successfully used the gatling gun");
        
            //counts how many players will be killed from gatling
            for (i = 0; i < game.numOfPlayers; i++){
                if (game.playerOrder[i].lifePoints == 1){
                    playersDead++;
                }
            }
        
            //if all players will be killed, ends the game
            if (playersDead >= game.numOfPlayers){
                game.game_over = true;
                System.out.println("All players are dead, so the outlaws win.");
            }
            
            //otherwise, makes each player lose 1 life point
            if (!game.game_over){
                for (i = 0; i < game.numOfPlayers; i++){
                    if ((game.playerOrder[i] != player) && (!"Paul Regret".equals(game.playerOrder[i].name)) && !game.playerOrder[i].isDead){
                       game.playerOrder[i].lose_life(game, arrowPile, false);
                    
                       //El gringo special ability
                        if ("El Gringo".equals(game.playerOrder[i].name)){
                            arrowPile.remove_arrow(game);
                            System.out.println(game.get_current_player().name + " made El Gringo lose a life point, so they have taken an arrow.");
                        }
                    }
                }
            }
            //sets player that used gatlings arrows back to 0
            while(player.arrows > 0){
                arrowPile.add_arrow(player);
            }
        }
    }
    
    /**
     * resolves broken arrow roll
     *
     * @param game gameFunctions object
     * @param arrowPile arrowPile object
     */
    public static void broken_arrow_roll (GameFunctions game, ArrowPile arrowPile){
        String enteredCharacter; //entered character name
        Boolean removedArrow = false; //if arrow has been removed or not
        int i; //counter
        
        Scanner input = new Scanner(System.in);
        
        //User input
        System.out.print("You rolled a broken arrow. Enter the name of the character you want to remove an arrow from (You may enter your own name): ");
        enteredCharacter = input.nextLine();
        
        //goes until an arrow has been successfully removed
        while (!removedArrow){
            for (i = 0; i < game.numOfPlayers; i ++){
                if (enteredCharacter.equals(game.playerOrder[i].name)){
                    //removes arrow from player if they have at least 1
                    if (game.playerOrder[i].arrows > 0){
                        arrowPile.add_arrow(game.playerOrder[i]);
                        System.out.println(enteredCharacter + " returned 1 arrow to the pile.");
                    }
                    //otherwise says there are none to remove
                    else{
                        System.out.println(enteredCharacter + " did not have any arrows to return to the pile.");
                    }
                    removedArrow = true;
                }
            }
            //error statement if player not found
            if (!removedArrow){
                System.out.print("Could not find character name. Please try again: ");
                enteredCharacter = input.nextLine();
            }
        }
    }
    
    /**
     * resolves a fight roll (duel)
     *
     * @param game gameFunctions object
     * @param arrowPile arrowPile object
     */
    public static void fight_roll (GameFunctions game, ArrowPile arrowPile){
        Scanner input = new Scanner(System.in);
        
        String otherPlayer; //entered player
        Boolean completedDuel; //if duel is completed
        Boolean rolledFight; //if a player rolled a fight dice
        int i; //counter
        
        //initializes new dice for passing between players for duel
        Dice dice = new Dice();
        dice.type = "duel";
        
        completedDuel = false;
        rolledFight = false;
        
        //User input
        System.out.print("Who would you like to challenge to a duel? (Enter player name): ");
        otherPlayer = input.nextLine();
        
        while (!completedDuel){
            for (i = 0; i < game.numOfPlayers; i++){
                if (otherPlayer.equals(game.playerOrder[i].name) && !otherPlayer.equals(game.get_current_player().name) && !game.playerOrder[i].isDead){
                    //goes until one of the players rolls a "fight"
                    while (!rolledFight){
                        dice.roll_dice();
                        System.out.println(otherPlayer + " rolled their dice and rolled a " + dice.roll);
                        //subtracts life point from loser
                        if ("Fight".equals(dice.roll)){
                            System.out.println(otherPlayer + " won the duel, and " + game.get_current_player().name + " lost a life point.");
                            game.get_current_player().lose_life(game, arrowPile, false);
                            rolledFight = true;
                        }
                        else{
                            dice.roll_dice();
                            System.out.println(game.get_current_player().name + " rolled their dice and rolled a " + dice.roll);
                            if ("Fight".equals(dice.roll)){
                                System.out.println(game.get_current_player().name + " won the duel, and " + otherPlayer + " lost a life point.");
                                game.playerOrder[i].lose_life(game, arrowPile, false);
                                rolledFight = true;
                            }
                        }
                    }
                    completedDuel = true;
                }
            }
            //error message if entered player could not be found
            if (!completedDuel){
                System.out.print("Could not find character name. Please try again: ");
                otherPlayer = input.nextLine();
            }
        }
    }
}