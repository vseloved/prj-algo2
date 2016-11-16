/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dijkstra;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
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
            Map<String,Integer> Names = new HashMap<>();
            int count = 1;
            int StartLine = 0;
            String buff, name1, name2;
            while (sc.hasNext()) 
            {
                buff = sc.next();
                Data.add( buff );
                if(StartLine < 2)
                {
                    if(Character.isDigit(buff.codePointAt(0)))
                    {
                        StartLine = 1;
                    }
                    else
                    {
                        if(buff.charAt(0) == '#')
                        {
                            //get link option
                            StartLine = 2;
                            if("#list".equals(buff)) StartLine = 3;
                            if("#matrix".equals(buff)) StartLine = 4;
                            if("#namedlist".equals(buff)) StartLine = 5;
                        }
                        if(StartLine == 1)
                        {
                            ++count;
                            StartLine = 0;
                        }
                    }
                }
            }
            Graph.SetSize(count);
            int index = 0, jndex = 0, pos, pos1, pos2;
            double Value;
            while( (jndex < Data.size()) && (index < count) )
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
                
                Names.put(point.Name, index);
                        
                ++index;
                System.out.println( point.Name + " " + point.W + " " + point.L );
            }
            Graph.ActualSize = count = index;
            while(index < Graph.count)
            {
                Graph.points[index ++] = new Vertex1();
            }
            pos = jndex;
            if(StartLine < 2)
            {
                //adds all possible connections with decart distance
                for(index = 1; index < count; ++index)
                {
                    for(jndex = 0; jndex < index; ++jndex)
                    {
                        Graph.connection[index][jndex] = Graph.connection[jndex][index] = Graph.points[index].Distance(Graph.points[jndex]);
                    }
                }
            }
            else
            {
                for(index = 1; index < count; ++index)
                {
                    for(jndex = 0; jndex < index; ++jndex)
                    {
                        Graph.connection[index][jndex] = Graph.connection[jndex][index] = -1; // no connection
                    }
                }
                switch(StartLine)
                {
                    case 5:
                    {
                        jndex = pos + 1; // scip operation word
                        while(jndex < Data.size() )
                        {
                            buff = Data.elementAt(jndex);
                            ++jndex;
                            while(!Character.isDigit( Data.elementAt(jndex).codePointAt(0) ) )
                            {
                                buff += " " + Data.elementAt(jndex);
                                ++jndex;
                            }
                            
                            Value = Double.parseDouble( Data.elementAt(jndex) );
                            ++jndex;
                            
                            pos = buff.indexOf("-");
                            if(pos > 0)
                            {
                                name1 = buff.substring(0, pos);
                                name2 = buff.substring(pos + 1);
                                if( Names.containsKey(name1) && Names.containsKey(name2) )
                                {
                                    pos1 = Names.get(name1);
                                    pos2 = Names.get(name2);
                                    Graph.connection[pos1][pos2] = Value;
                                }
                                else
                                {
                                    System.out.println("no such pair found: (" + name1 +" -> " + name2 + " : " + Value + ");");
                                }
                            }
                            else
                            {
                                System.out.println("Error in processing pair: (" + buff + " " + Value + ")!");
                            }
                        }
                    }break;
                    
                    default: break;//do nothing
                }
            }
        } 
        catch (FileNotFoundException ex) 
        {
            Logger.getLogger(DataLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
