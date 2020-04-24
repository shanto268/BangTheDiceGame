/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bangdicegame;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.*;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.*;

/**
 *
 * @author Johnathan
 */
public class BangGUIController{
    
    public static String gameSetup() throws FileNotFoundException{
        /* To use the output from this function, use the following lines:
        String input = BangGUIController.gameSetup();
        String sub = input.substring(0, 1);
        int num = Integer.parseInt(sub);
        sub = input.substring(2);
        boolean b = Boolean.parseBoolean(sub);
        */
        
        String output = "";
        FileInputStream istream = new FileInputStream("build/classes/Images/BangLogo.jpg");
        Image image = new Image(istream);
        ImageView BangImage = new ImageView(image);
        BangImage.setFitHeight(175);
        BangImage.setPreserveRatio(true);
        
        //Creates the list for the dropdown box
        ObservableList<String> players = FXCollections.observableArrayList();
        players.add("4");
        players.add("5");
        players.add("6");
        players.add("7");
        players.add("8");
        
        //Formats the dialog box
        Dialog<ButtonType> setup = new Dialog<>();
        //setup.setTitle("Game Setup"); Taking this out and leaving in the StageStyle removes some visual pleasantness, but fixes a potential bug by the user using the red "X" to close the dialog box.
        setup.setHeaderText("Game\nSetup"); //Set to null if StageStyle is removed.
        setup.setGraphic(BangImage);
        ButtonType buttonTypeOk = new ButtonType("Okay", ButtonData.OK_DONE);
        setup.getDialogPane().getButtonTypes().add(buttonTypeOk);
        setup.initStyle(StageStyle.UNDECORATED); //Remove this line and re-enable the setTitle/reset HeaderText if visuals are more important than the potential bug.
        
        //Creates the labels and the interactive boxes
        Label label1 = new Label("How many players? ");
        Label label2 = new Label("Include DLC? ");
        ChoiceBox<String> box1 = new ChoiceBox(players);
        CheckBox box2 = new CheckBox();
        
        //Places the labels and boxes in their proper place
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.add(label1, 1, 1);
        grid.add(box1, 2, 1);
        grid.add(label2, 1, 2);
        grid.add(box2, 2, 2);
        setup.getDialogPane().setContent(grid);
        box1.getSelectionModel().selectFirst();
        
        //Displays the dialog box and waits for the user to hit "Okay"
        setup.showAndWait();
        
         
        Integer num = Integer.valueOf(box1.getValue());
        boolean box2Select = box2.isSelected();
        String selected = String.valueOf(box2Select);
        boolean b = Boolean.parseBoolean(selected);
        output = num + " " + b;
        return output;
    }
 
    
}
