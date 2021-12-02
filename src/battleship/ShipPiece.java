package battleship;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;


  public abstract class ShipPiece 
 {
    private Color color;
    private int value;
    ShipPiece(Color _color)
    {
        color = _color;
        value = (int)(Math.random()*9+1);
    }
    public Color getColor()
    {
        return (color);
    }
    
    public void highlight(Color _color)
    {
        color = _color;
    }
    public int getValue()
    {
        return(value);
    }

    public abstract void draw(Graphics2D g,int row,int column,int xdelta,int ydelta);

}

