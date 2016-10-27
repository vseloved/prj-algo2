/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coins;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.util.Scanner;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 *
 * @author Mafia
 */
public class Coins extends Application 
{
    protected static final  int[] Denoms = {1, 3, 7, 13, 29, 50};
    @Override
    public void start(Stage primaryStage) throws Exception 
    {
        HBox bottomPane = new HBox();
        bottomPane.setPrefHeight(35);
        bottomPane.setPadding( new Insets(7,7,7,7) );
        
        Pane centralPane = new Pane();
        
        TextField Input = new TextField();
        Label Output = new Label();
        Output.setFont(Font.font("Cambria", 32));
        
        Button btnCalc = new Button();
        btnCalc.setText("Get change");
        
        Button btnICalc = new Button();
        btnICalc.setText("change +1");
        btnICalc.setVisible(false);
        
        btnCalc.setOnAction((ActionEvent event) -> {
            if(!Input.getText().isEmpty())
            {
                int Value = Integer.parseInt( Input.getText() );
                if(Value > 0)
                {
                    Output.setText( ShowResult( GetChange(Value) ) );
                    btnICalc.setVisible(true);
                }
                else
                {
                    Output.setText("");
                    btnICalc.setVisible(false);
                }
            }
            else
            {
                Output.setText("");
                btnICalc.setVisible(false);
            }
        });
        
        btnICalc.setOnAction((ActionEvent event) -> {
            int Value = Integer.parseInt( Input.getText() ) + 1;
            Input.setText(""+Value);
            Output.setText( ShowResult( GetChange(Value) ) );
        });
        
        BorderPane root = new BorderPane();
        root.setCenter(centralPane);
        root.setBottom(bottomPane);
        root.setPadding( new Insets(2,2,2,2) );
        
        bottomPane.getChildren().addAll(Input, btnCalc, btnICalc);
        centralPane.getChildren().add(Output);
        
        bottomPane.setBackground(new Background(new BackgroundFill(Color.BISQUE, CornerRadii.EMPTY, Insets.EMPTY)));        
        centralPane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));       
        root.setBackground(new Background(new BackgroundFill(Color.CADETBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        
        Scene scene = new Scene(root, 800, 600);
        
        primaryStage.setTitle("Salesmen problem");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static int[] GetChange  (int Value)
    {
        int[] result = new int[Denoms.length];
        
        int i, j, k = 0, n = Denoms.length;
        int sum = Value;
        for (i = n-1;i>=0;--i) 
        {
            if (sum >= Denoms[i])
            {
                result[i] = sum / Denoms[i];
                sum = sum % Denoms[i];
                k += result[i];
            }
            else
            {
                result[i] = 0;
            }
        }
        
        Tail(k, Value, n-1, result);
        
        return result;
    }
    
    protected static int Tail(int limit, int amount, int pos, int[] Coins)
    {
        int a = Denoms[pos];
        int m = amount / a;
        if (m >= limit) return -1;  //it already cannot improve result
        int r = amount % a;
        int t = 1;
        int result = 0;
        if(r > 0)
        {
            if(pos > 0)
            {
                while( (t >= 0) && (m >= 0) )
                {
                    t = Tail(limit - m, r, pos - 1, Coins);
                    if( (t > 0) && (m + t < limit) )    // found better result
                    {
                        result = limit = m + t;
                        Coins[pos] = m;
                    }
                    --m;
                    r += a;
                }
            }
            else
                result = 0;
        }
        else
        {
            result = m; // found better result 
            Coins[pos] = m;
            while(--pos >= 0)
            {
                Coins[pos] = 0;
            }
        }
        return result;
    }
    
    protected static String ShowResult(int[] Res)
    {
        if(null == Res) return "No result";
        if(Res.length != Denoms.length) return  "Incorrect result";
        int i, n = 0, s = 0;
        String out = null;
        for(i = Denoms.length - 1; i >= 0; --i)
        {
            if(Res[i] > 0)
            {
                if(null == out)
                {
                    out = " : \n'" + Denoms[i] + "'x" + Res[i];
                }
                else
                {
                    out += " + '" + Denoms[i] + "'x" + Res[i];
                }
                n += Res[i];
                s += Res[i] * Denoms[i];
            }
        }
        if(n > 1)
            out = " --> used " + n + " coins" + out;
        else
            out = " --> used 1 coin" + out;        
        out = "Change from " + s + out;
        
        return out;
    }
    
    public static void main(String[] args)
    {
        launch(args);/*
        Scanner sc = new Scanner(System.in);
        int i;
        System.out.println("Введіть ціле число");
        if(sc.hasNextInt()) 
        {
            i = sc.nextInt();
            System.out.println( ShowResult( GetChange(i) ) );
        }
        else
        {
            System.out.println("Введене число не є цілим!");
        }
        Platform.exit();*/
    }
}
