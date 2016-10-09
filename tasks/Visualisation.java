/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salesman;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.Vector;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *
 * @author Mafia
 */
public class Visualisation 
{
    protected int resolutionH;
    protected int resolutionW;
    protected Canvas Ground;
    protected Graph1 Target;
    protected int MinH;
    protected int MinW;
    protected int MaxH;
    protected int MaxW;
    protected double Mx; // size multiplier
    protected int MidX;
    protected int MidY;
    protected int ScrX;
    protected int ScrY;
    protected Vector<Integer> TraceWay;
    protected Vector<Integer> FinalWay;
    
    public Visualisation() 
    {
        Ground = new Canvas();
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        resolutionH = gd.getDisplayMode().getWidth();
        resolutionW = gd.getDisplayMode().getHeight();
        Ground.setHeight(resolutionH);
        Ground.setWidth(resolutionW);
        Target = null;
        ScrX = resolutionW / 2;
        ScrY = resolutionH / 2;
    }
    
    public void SetGraph(Graph1 graph)
    {
        Target = graph;
        int i;
        int count = Target.ActualSize;
        Vertex1 V;
        int tmp;
        double MxH = 1, MxW = 1;
        
        if(count > 0)
        {
            MinW = MaxW = Target.points[0].W;
            MinH = MaxH = Target.points[0].L;
            for(i = 1; i < count; ++i)
            {
                V = Target.points[i];
                if(V.W < MinW) MinW = V.W;
                if(V.W > MaxW) MaxW = V.W;
                if(V.L < MinH) MinH = V.L;
                if(V.L > MaxH) MaxH = V.L;
            }
            MidX = (MaxW + MinW) / 2;
            MidY = (MaxH + MinH) / 2;
            
            tmp = (MaxH - MinH);
            if(tmp > 0)
            {
                MxH = (Ground.getHeight() - 20) / tmp;
            }
            
            tmp = (MaxW - MinW);
            if(tmp > 0)
            {
                MxW = (Ground.getWidth() - 20) / tmp;
            }
            
            Mx = (MxH < MxW) ? MxH : MxW;
        }
        else
        {
            MinH = 0;
            MinW = 0;
            MaxH = (int) Ground.getHeight();
            MaxW = (int) Ground.getWidth();
            Mx = 1;
        }
    }
    
    public void Resize(double NewWidth, double NewHeight)
    {
        if(Target != null)
        {
            int count = Target.ActualSize;
            if(count > 1)
            {
                double MxH, MxW;
                MxH = (MaxH > MinH) ? (NewHeight - 20) / (MaxH - MinH) : 1.0;
                MxW = (MaxW > MinW) ? (NewWidth - 20) / (MaxW - MinW) : 1.0;
                Mx = (MxH < MxW) ? MxH : MxW;
            }
        }
        ScrX = (int) (NewWidth * 0.5);
        ScrY = (int) (NewHeight * 0.5);

        GraphicsContext GC = Ground.getGraphicsContext2D();
        GC.clearRect(0, 0, Ground.getWidth(), Ground.getHeight());
        Ground.setWidth(NewWidth);
        Ground.setHeight(NewHeight);
        Draw();
    }
    
    public void Draw()
    {
        if( (null == Target) || (0 == Target.ActualSize) )
        {
            System.out.println("Empty Graph...");
            return;
        }
        
        int i, j;
        int size = Target.ActualSize;
        Vertex1 V1, V2;
        int ScX, ScY, LnX, LnY;
        GraphicsContext GR = Ground.getGraphicsContext2D();
        GR.clearRect(0, 0, Ground.getWidth(), Ground.getHeight());
        int VRad = 4;
        
        GR.setFill(Color.WHITE);
        GR.fill();
        
        GR.setStroke(Color.YELLOW);
        GR.setLineWidth(0.1);
        
        for(i = 1; i < size; ++i)
        {
            V1 = Target.points[i];
            ScX = (int) ((V1.W - MidX) * Mx + ScrX);
            ScY = (int) ((MidY - V1.L) * Mx + ScrY);
            for(j = 0; j < i; ++j)
            {
                V2 = Target.points[j];
                LnX = (int) ((V2.W - MidX) * Mx + ScrX);
                LnY = (int) ((MidY - V2.L) * Mx + ScrY);
                GR.strokeLine(ScX, ScY, LnX, LnY);
            }        
        }
        
        int waysize;
        
        if(TraceWay != null)
        {
            GR.setStroke(Color.GREEN);
            GR.setLineWidth(0.5);

            waysize = TraceWay.size();
            if(waysize % 2 == 1)
            {
                --waysize;
                System.out.println("Vertex should be in pairs!!!");
            }
            for(i = 0; i < waysize; ++i)
            {
                j = TraceWay.get(i);
                if( (j < 0) || (j >= size) )
                {
                    System.out.println("Wrong vertex index !!!");
                    break;
                }
                V1 = Target.points[j];
                ++i;
                j = TraceWay.get(i);
                if( (j < 0) || (j >= size) )
                {
                    System.out.println("Wrong vertex index !!!");
                    break;
                }
                V2 = Target.points[j];

                ScX = (int) ((V1.W - MidX) * Mx + ScrX);
                ScY = (int) ((MidY - V1.L) * Mx + ScrY);
                LnX = (int) ((V2.W - MidX) * Mx + ScrX);
                LnY = (int) ((MidY - V2.L) * Mx + ScrY);
                GR.strokeLine(ScX, ScY, LnX, LnY);
            }
        }
        
        if(FinalWay != null)
        {
            GR.setStroke(Color.BLUE);
            GR.setLineWidth(1.0);

            waysize = FinalWay.size();
            if(waysize % 2 == 1)
            {
                --waysize;
                System.out.println("Vertex should be in pairs!!!");
            }
            for(i = 0; i < waysize; ++i)
            {
                j = FinalWay.get(i);
                if( (j < 0) || (j >= size) )
                {
                    System.out.println("Wrong vertex index !!!");
                    break;
                }
                V1 = Target.points[j];
                ++i;
                j = FinalWay.get(i);
                if( (j < 0) || (j >= size) )
                {
                    System.out.println("Wrong vertex index !!!");
                    break;
                }
                V2 = Target.points[j];

                ScX = (int) ((V1.W - MidX) * Mx + ScrX);
                ScY = (int) ((MidY - V1.L) * Mx + ScrY);
                LnX = (int) ((V2.W - MidX) * Mx + ScrX);
                LnY = (int) ((MidY - V2.L) * Mx + ScrY);
                GR.strokeLine(ScX, ScY, LnX, LnY);
            }
        }
        
        GR.setStroke(Color.RED);
        GR.setFill(Color.CORAL);
        
        for(i = 0; i < size; ++i)
        {
            V1 = Target.points[i];
            ScX = (int) ((V1.W - MidX) * Mx + ScrX - 0.5 * VRad);
            ScY = (int) ((MidY - V1.L) * Mx + ScrY - 0.5 * VRad);
            GR.fillOval(ScX, ScY, VRad, VRad);
        }
    }
    
    public void SetTraceWay(Vector<Integer> Way)
    {
        TraceWay = Way;
    }
    
    public void SetFinalWay(Vector<Integer> Way)
    {
        FinalWay = Way;
    }
    
    public void AddTraceLine(int From, int To)
    {
        if( (From >= 0) && (To >= 0) && (From < Target.ActualSize) && (To < Target.ActualSize) )
        {
            if(null == TraceWay) TraceWay = new Vector<Integer>();
            TraceWay.add(From);
            TraceWay.add(To);
        }
        else
        {
            System.out.println("Wrong indexes !!!");
        }
    }
    
    public void AddFinalLine(int From, int To)
    {
        if( (From >= 0) && (To >= 0) && (From < Target.ActualSize) && (To < Target.ActualSize) )
        {
            if(null == FinalWay) FinalWay = new Vector<Integer>();
            FinalWay.add(From);
            FinalWay.add(To);
        }
        else
        {
            System.out.println("Wrong indexes !!!");
        }
    }
    
    public void ClearTrace()
    {
        if(null == TraceWay) 
        {
            TraceWay = new Vector<Integer>();
        }
        else 
        {
            TraceWay.clear();
        }
    }
    
    public void ClearFinal()
    {
        if(null == FinalWay) 
        {
            FinalWay = new Vector<Integer>();
        }
        else 
        {
            FinalWay.clear();
        }
    }
    
    public Canvas GetCanvas()
    {
        return Ground;
    }
}
