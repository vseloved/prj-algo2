/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salesman;

/**
 *
 * @author Mafia
 */
public class solver
{
    Graph1 Trip;
    int[] Pass;
    public double ShortestWay(int start)
    {
        double result=0;
        if(Trip == null)return 0;
        if( (start < 0) && (start >=Trip.ActualSize) ) return 0;
        int count = Trip.ActualSize;
        Pass = new int [count];
        Pass [0] = start;
        int Counter = 1;
        boolean [] Flags = new boolean[count];
        int i, j, k;
        double MaxLen = 0;
        double MinLen;
        int MinPos;
        
        for (i=0; i < count; ++i)
        {
            Flags[i] = true;
        }
        Flags[start] = false;
        
        for(i = 1; i < count; ++i)
        {
            for(j = 0; j < i; ++j)
            {
                if(MaxLen < Trip.connection[i][j])
                    MaxLen = Trip.connection[i][j];
            }
        }
        
        k = start;
        MinPos = start;
        for(i = 1; i < count; ++i)
        {
            MinPos = -1;
            MinLen = MaxLen;
            for(j = 0; j < count; ++j)
            {
                if(Flags[j] && (MinLen >= Trip.connection[k][j]))
                {
                    MinPos = j;
                    MinLen = Trip.connection[k][j];
                }
            }
            Pass[i] = MinPos;
            Flags[MinPos] = false;
            result += MinLen;
            k = MinPos;
        }
        result += Trip.connection[Pass[count-1]][start];
        
        return result;
    }
    public solver()
    {
        
    }
}
