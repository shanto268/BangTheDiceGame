/*
 * Cierra Ditmore
 * CS 2365
 */
package bangdicegame;

/**
 * Allows calls to test each class individually
 */

public class Testing {

    /**
     * method to test character class
     */
    public static void test_character(){
        int [] randomSelection = {1, 2, 3, 4, 5, 6, 7, 8, 9 ,10, 11, 12, 13, 14};
        String [] roles;
        int i;
        
        System.out.println("Array before shuffle:");
        for(i = 0; i < 14; i++){
            System.out.print(randomSelection[i] + ", ");
        }
        
        randomSelection = Character.shuffle_character(randomSelection);
        
        System.out.println("\nArray after shuffle:");
        for(i = 0; i < 14; i++){
            System.out.print(randomSelection[i] + ", ");
        }
        
        roles = Character.select_role(4);
        
        System.out.println("\nThe roles for a 5 player game would be:");
        for(i = 0; i < 5; i++){
            System.out.print(roles[i] + ", ");
        }
        
        Character testCharacter = new Character(randomSelection[1], false);
        testCharacter.role = roles[1];
        
        System.out.println("\n\nThe character's name is: " + testCharacter.name);
        System.out.println("The character's role is: " + testCharacter.role);
        System.out.println("The character's arrows are: " + testCharacter.arrows);
        System.out.println("The character's cheif arrow boolean is: " + testCharacter.chiefArrow);
        System.out.println("The character's AI boolean is: " + testCharacter.isAi);
        System.out.println("The character's isDead boolean is: " + testCharacter.isDead);
        System.out.println("The character's life is: " + testCharacter.lifePoints);
        System.out.println("The character's max life is: " + testCharacter.maxLife);
        System.out.println("The character's numHelpSheriff is: " + testCharacter.numHelpSheriff);
        System.out.println("The character's numShotSheirff is: " + testCharacter.numShotSheriff);
        System.out.println("The character's vector is: " + testCharacter.ProbabilityVector);
        
        testCharacter.gain_arrow();
        System.out.println("The character's arrows after adding 1 are: " + testCharacter.arrows);
        
        testCharacter.gain_life();
        System.out.println("The character's life after adding 1 is: " + testCharacter.lifePoints);
        
        testCharacter.lose_arrow();
        System.out.println("The character's arrows after losing 1 are: " + testCharacter.arrows);
    }
    
    /**
     * method to test dice class
     */
    public static void test_dice(){
        Dice testDice = new Dice();
        ArrowPile testPile = new ArrowPile();
        Character testCharacter = new Character(1, false);
        testCharacter.role = "Deputy";
        Character array [] = {testCharacter};
        GameFunctions Game = new GameFunctions (array, 1, false);
        
        testDice.roll_dice();
        
        System.out.println("After rolling a normal dice, the dice face is: " + testDice.roll);
        
        testDice.type = "loudmouth";
        testDice.roll_dice();
        System.out.println("After rolling a loudmouth dice, the dice face is: " + testDice.roll);
        
        testDice.type = "coward";
        testDice.roll_dice();
        System.out.println("After rolling a coward dice, the dice face is: " + testDice.roll);
        
        testDice.type = "duel";
        testDice.roll_dice();
        System.out.println("After rolling a duel dice, the dice face is: " + testDice.roll);
        
        System.out.println("\n\nThe character's name is: " + testCharacter.name);
        Dice.arrow_roll(testPile, Game);
        System.out.println("The character's arrows after adding 1 are: " + testCharacter.arrows);
        
        Dice.beer_roll(Game);
        System.out.println("The character's life after adding 1 is: " + testCharacter.lifePoints);
        
        Dice.broken_arrow_roll(Game, testPile);
        System.out.println("The character's arrows after discarding 1 are: " + testCharacter.arrows);
        
        Dice.bullsEye1_roll(Game, testPile);
        System.out.println("The character's life after losing 1 is: " + testCharacter.lifePoints);
        
        Dice.bullsEye2_roll(Game, testPile);
        System.out.println("The character's life after losing 1 is: " + testCharacter.lifePoints);  
    }
    
    /**
     * method to test arrowpile class
     */
    public static void test_arrowpile(){
        ArrowPile testPile = new ArrowPile();
        Character testCharacter = new Character(1, false);
        testCharacter.role = "Deputy";
        Character array [] = {testCharacter};
        GameFunctions Game = new GameFunctions (array, 1, false);
        
        testPile.remove_arrow(Game);
        System.out.println("The arrows remaining in the pile are: " + testPile.remaining);
        
        
        testPile.add_arrow(testCharacter);
        System.out.println("The arrows remaining in the pile are: " + testPile.remaining);
    }
    
    /**
     * method to test gamefunctions class
     */
    public static void test_gamefunctions(){
        int [] randomSelection = {1, 2, 3, 4, 5, 6, 7, 8, 9 ,10, 11, 12, 13, 14};
        int aiPlayers;
        int totalPlayers;
        Character [] players = new Character [8];
        String [] roles;
        ArrowPile arrowPile = new ArrowPile();
        
        int i = 0;
        aiPlayers = 4;
        totalPlayers = 5;
               
        
        randomSelection = Character.shuffle_character(randomSelection);
        roles = Character.select_role(aiPlayers);
        
        boolean isAi = false;
        while (aiPlayers >= 0){
            if (i>0){
                isAi = true;
            }
            players[i] = new Character(randomSelection[aiPlayers],isAi);
            players[i].set_role(roles[i]);
            
            //Increases life points if they are the sheriff
            if ("Sheriff".equals(players[i].role)){
                players[i].lifePoints += 2;
                players[i].maxLife += 2;
            }
            
            System.out.println("Player " + i + " name is: " + players[i].name);
            System.out.println("Player " + i + " role is: " + players[i].role);
            System.out.println("Player " + i + " is an AI? " + players[i].isAi);
            System.out.println();
            aiPlayers -= 1;
            i += 1;
        }
        
        GameFunctions Game = new GameFunctions (players, totalPlayers, false);
        
        
        System.out.println("The current player is: " + Game.get_current_player().name);
        System.out.println("The next player is: " + Game.get_next_player().name);
        System.out.println("The previous player is: " + Game.get_previous_player().name);
        System.out.println("The player 2 to the right is: " + Game.get_two_away_player(players[0]).name);
        System.out.println("The player 2 to the left is: " + Game.get_two_before_player(players[0]).name + "\n\n");
        
        Game.draw_boneyard_card(Game);
        Game.draw_boneyard_card(Game);
        Game.draw_boneyard_card(Game);
        
        Game.eliminate_player(players[2], arrowPile, true);
        
        System.out.println("\n\n");
        
        for(i = 0; i < 5; i++){
            System.out.println("Player " + i + " name is: " + players[i].name);
            System.out.println("Player " + i + " role is: " + players[i].role);
            System.out.println("Player " + i + " is an AI? " + players[i].isAi);
        }  
    }
    
    /**
     * method to test ai classes
     */
    public static void test_ai(){
        int [] randomSelection = {1, 2, 3, 4, 5, 6, 7, 8, 9 ,10, 11, 12, 13, 14};
        int aiPlayers;
        int totalPlayers;
        Character [] players = new Character [8];
        String [] roles;
        ArrowPile arrowPile = new ArrowPile();
        
        int i = 0;
        aiPlayers = 4;
        totalPlayers = 5;
               
        
        randomSelection = Character.shuffle_character(randomSelection);
        roles = Character.select_role(aiPlayers);
        
        boolean isAi = false;
        while (aiPlayers >= 0){
            if (i>0){
                isAi = true;
            }
            players[i] = new Character(randomSelection[aiPlayers],isAi);
            players[i].set_role(roles[i]);
            
            //Increases life points if they are the sheriff
            if ("Sheriff".equals(players[i].role)){
                players[i].lifePoints += 2;
                players[i].maxLife += 2;
            }
            
            System.out.println("Player " + i + " name is: " + players[i].name);
            System.out.println("Player " + i + " role is: " + players[i].role);
            System.out.println("Player " + i + " is an AI? " + players[i].isAi);
            System.out.println();
            aiPlayers -= 1;
            i += 1;
        }
        
        GameFunctions Game = new GameFunctions (players, totalPlayers, false);
        SimulateAI AI = new SimulateAI(players, totalPlayers, arrowPile);
        AI.AITurn(Game, i, arrowPile);
    }
        
}
