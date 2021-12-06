

package battleship;

import java.awt.Color;
import java.awt.Graphics2D;


public class DestroyerPiece{
    public enum Direction{Right,Down};
    private int shipType =3; 
    private int row;
    private int column;
    private Color color;
    private Direction direction;
    DestroyerPiece(Color _color,int _row, int _col, int _box){  
       color = _color;      
       row = _row;
       column = _col; 
       shipType = _box;

    }
    
    public void draw(Graphics2D g,int row,int column,int xdelta,int ydelta)
    {
        g.setColor(color);
         g.fillRect(Window.getX(column*xdelta), Window.getY(row*ydelta),xdelta,ydelta);    

        
    }
    
    public void changeColor(Color _color)
    {
        color = _color;
    }
    
    public Color getColor()
    {
        return(color);
    }
    
    public Direction getDir()
    {
        return(direction);
    }
    public int getType()
    {
        return(shipType);
    }

}
