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
public class Vertex1 {
    public String Name;
    public int W;
    public int L;
    public double Distance(Vertex1 V)
    {
        double result;
        result = (W-V.W)*(W-V.W)+(L-V.L)*(L-V.L);
        result = Math.sqrt(result);
        return result;
    }

    public Vertex1() 
    {
        Name = "";
        W = 0;
        L = 0;
    }
    
}
