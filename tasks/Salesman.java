/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salesman;


import java.awt.Component;
import java.io.File;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;

/**
 *
 * @author Mafia
 */
public class Salesman extends Application {
    protected Graph1 Cities;
    protected Visualisation Painter;
    
    @Override
    public void start(Stage primaryStage) {
        Cities = new Graph1();
        Painter = new Visualisation();
        Canvas paint = Painter.GetCanvas();
        
        HBox bottomPane = new HBox();
        bottomPane.setPrefHeight(35);
        bottomPane.setPadding( new Insets(7,7,7,7) );
        
        Pane centralPane = new Pane();
        
        
        Button btnSolve1 = new Button();
        btnSolve1.setText("greedy way");
        btnSolve1.setVisible(false);
        
        Button btnLoad = new Button();
        btnLoad.setText("Load Data");
        
        btnLoad.setOnAction((ActionEvent event) -> {
            System.out.println("Loading Data ...");
            DataLoader loader = new DataLoader();
            File file = SelectFile(primaryStage);
            if(file != null)
            {
                loader.LoadGraph1(file, Cities);
                System.out.println("Data loaded...");
                System.out.println("Cities count = " + Cities.ActualSize);
                Painter.SetGraph(Cities);
                Painter.Draw();
                btnSolve1.setVisible(true);
            }
            else
            {
                System.out.println("No file selected...");
            }
        });
        
        btnSolve1.setOnAction((ActionEvent event) -> {
            int StartPos = (int) ( (Cities.ActualSize - 0.01) * Math.random() );
            solver SL = new solver();
            SL.Trip = Cities;
            System.out.println("Starting from " + StartPos + " take way " + SL.ShortestWay(StartPos) + " length...");
            int index;
            Painter.ClearFinal();
            for(index = 1; index < Cities.ActualSize; ++index)
            {
                Painter.AddFinalLine(SL.Pass[index - 1], SL.Pass[index]);
            }
            Painter.AddFinalLine(SL.Pass[index - 1], SL.Pass[0]);
            Painter.Draw();
        });
        
        centralPane.getChildren().add(paint);
        centralPane.widthProperty().addListener(observable -> {
            Painter.Resize(centralPane.getWidth(), centralPane.getHeight());
        });
        centralPane.heightProperty().addListener(observable -> {
            Painter.Resize(centralPane.getWidth(), centralPane.getHeight());
        });
        
        BorderPane root = new BorderPane();
        root.setCenter(centralPane);
        root.setBottom(bottomPane);
        root.setPadding( new Insets(2,2,2,2) );
        
        bottomPane.getChildren().addAll(btnLoad, btnSolve1);
        
        bottomPane.setBackground(new Background(new BackgroundFill(Color.BISQUE, CornerRadii.EMPTY, Insets.EMPTY)));        
        centralPane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));       
        root.setBackground(new Background(new BackgroundFill(Color.CADETBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        
        Scene scene = new Scene(root, 800, 600);
        
        primaryStage.setTitle("Salesmen problem");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private File SelectFile(Window parent)
    {
        FileChooser filechoser = new FileChooser();
        filechoser.setTitle("Select data file");
        filechoser.getExtensionFilters().add(new ExtensionFilter("Text files", "*.txt"));       
        return filechoser.showOpenDialog(parent);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
