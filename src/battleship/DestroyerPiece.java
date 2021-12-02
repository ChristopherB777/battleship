

package battleship;

import java.awt.Color;
import java.awt.Graphics2D;


public class DestroyerPiece extends ShipPiece{
    public enum Direction{Right,Down};
    private int shipType =3; 
    private int row;
    private int column;
    private Direction direction;
    DestroyerPiece(Color _color,int _row, int _col, int _box, Direction _dir){  
       super(_color);
       row = _row;
       column = _col; 
       direction = _dir;
       shipType = _box;

    }
    
    public void draw(Graphics2D g,int row,int column,int xdelta,int ydelta)
    {
        g.setColor(getColor());
        int zrow = row;
        int zcol = column;
        for (int i = 0;i<shipType;i++){
            g.fillRect(Window.getX(zcol*xdelta), Window.getY(zrow*ydelta),xdelta,ydelta);    
           if(direction == Direction.Right)
           zcol++;
//           if(zcol > Board.NUM_COLUMNS)
//               return;
           else if(direction == Direction.Down)
           zrow++;
//            if(zrow > Board.NUM_ROWS)
//                return;
        }
    }

}
