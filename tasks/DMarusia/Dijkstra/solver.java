/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dijkstra;

import javafx.scene.Node;

/**
 *
 * @author Mafia
 */
public class solver implements Runnable {
    Visualisation Screen;
    Graph1 Trip;
    int[] Pass;
    int From;
    
    Node MyNode;

@Override
    public void run()
    {
        ShortestWay(From);
    }
       
    public double ShortestWay(int start)
    {
        double result=0;
        if(Trip == null)return 0;
        if( (start < 0) || (start >=Trip.ActualSize) ) return 0;
        if(MyNode != null)MyNode.setVisible(false);
        
        int count = Trip.ActualSize;
        Pass = new int [count];
        Pass [0] = start;
        int [] Flags = new int[count];
        int [] WorkPass = new int[count];
        double [] Length = new double[count];
        int i, j, k;
        double MaxLen = 0;
        double MinLen;
        double CurrLen;
        double StepLen;
        int MinPos;
        
        for (i=0; i < count; ++i)
        {
            Flags[i] = 2;
            Length[i] = -1;
        }
        Flags[start] = 0;
        Length[start] = 0;
        Pass[start] = start;
        
        for(i = 0; i < count; ++i)
        {
            for(j = 0; j < count; ++j)
            {
                if(MaxLen < Trip.connection[i][j])
                    MaxLen = Trip.connection[i][j];
            }
        }
        MaxLen = MaxLen + 1;
        
        k = start;
        Screen.ClearFinal();
        for(i = 1; i < count; ++i)
        {
            CurrLen = Length[k];
            MinPos = -1;
            MinLen = CurrLen + MaxLen;
            for(j = 0; j < count; ++j)
            {
                if(Flags[j] > 0)
                {
                    if(Trip.connection[k][j] > 0)
                    {
                        StepLen = CurrLen + Trip.connection[k][j];
                        if( (Flags[j] > 1) || (StepLen < Length[j]) )
                        {
                            Length[j] = StepLen;
                            Flags[j] = 1;
                            WorkPass[j] = k;
                        }
                    }
                    if( (Flags[j] == 1) && (Length[j] < MinLen) )
                    {
                        MinLen = Length[j];
                        MinPos = j;
                    }
                }
            }
            
            if(MinPos < 0)
            {
                System.out.println("Unreachable poins found: from city \"" + Trip.points[start].Name +"\" to city(ies):");
                for(j = 0; j < count; ++j)
                {
                    if(Flags[j] == 2)
                    {
                        System.out.print(Trip.points[j].Name + ", ");
                    }
                }
                if(MyNode != null)MyNode.setVisible(true);
                break;
            }
            
            Pass[MinPos] = WorkPass[MinPos];
            Flags[MinPos] = 0;
            result = MinLen;
            k = MinPos;
            
            Screen.ClearTrace();
            for(j = 0; j < count; ++j)
            {
                if(Flags[j] == 1) // visited point
                {
                    Screen.AddTraceLine(WorkPass[j], j);
                }
            }
            Screen.AddFinalLine(WorkPass[MinPos], MinPos);
            Screen.Draw();
            
            try {
                Thread.sleep( (count - i) * 30 );                 //100 milliseconds is one second.
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
                if(MyNode != null)MyNode.setVisible(true);
            }
        }
        
        if(MyNode != null)MyNode.setVisible(true);
        return result;
    }
    
    public void SetFrom(int from)
    {
        From = from;
    }
    
    public solver(Visualisation Draw)
    {
        Screen = Draw;
        MyNode = null;
    }
    
}
