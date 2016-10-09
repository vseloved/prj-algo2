/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salesman;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Mafia
 */
public class DataLoader {
    public void LoadGraph1(File file, Graph1 Graph)
    {
        try(Scanner sc = new Scanner(file))  
        {
            Vector<String> Data = new Vector<>();
            while (sc.hasNext()) 
            {
                Data.add( sc.next() );
            }
            int count = Data.size()/ 3;
            Graph.SetSize(count);
            int index = 0, jndex = 0;
            while(jndex < Data.size())
            {
                Vertex1 point = new Vertex1();
                point.Name = Data.elementAt(jndex);
                ++jndex;
                while(!Character.isDigit( Data.elementAt(jndex).codePointAt(0) ) )
                {
                    point.Name += " " + Data.elementAt(jndex);
                    ++jndex;
                }
                point.W = Integer.parseInt(Data.elementAt(jndex));
                ++jndex;
                point.L = Integer.parseInt(Data.elementAt(jndex));
                ++jndex;
                Graph.points[index] = point;
                Graph.connection[index][index] = 0;
                ++index;
                System.out.println( point.Name + " " + point.W + " " + point.L );
            }
            Graph.ActualSize = count = index;
            while(index < Graph.count)
            {
                Graph.points[index ++] = new Vertex1();
            }
            
            for(index = 1; index < count; ++index)
            {
                for(jndex = 0; jndex < index; ++jndex)
                {
                    Graph.connection[index][jndex] = Graph.connection[jndex][index] = Graph.points[index].Distance(Graph.points[jndex]);
                }
            }
        } 
        catch (FileNotFoundException ex) 
        {
            Logger.getLogger(DataLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
