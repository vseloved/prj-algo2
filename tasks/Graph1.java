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
public class Graph1 {
    public Vertex1[] points;
    public double[][] connection;
    public int count;
    public int ActualSize;
    
    public void SetSize (int Size)
    {
        if ( (Size>0) && (Size!=count) )
        {
            points = new Vertex1 [Size];
            connection = new double [Size][Size];
            count = Size;
            System.out.println("New Size = " + Size);
            System.out.println(points.length);
        }
        else
        {
            System.out.println("No Size changed...");
        }
    }
    public Graph1()
    {
        count=0;
    }
}