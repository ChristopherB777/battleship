package battleship;
import java.awt.*;

public class Board {
    private static int numPiecesAdded = 0;
    public final static int NUM_ROWS = 10;
    public final static int NUM_COLUMNS = 10;          
    private static int points;
    private static DestroyerPiece board[][] = new DestroyerPiece[NUM_ROWS][NUM_COLUMNS];
    public static DestroyerPiece.Direction rotation;
    public static SparseArray sparseArray = new SparseArray(10,10);
    static DestroyerPiece.Direction dir = DestroyerPiece.Direction.Right; 
    public static int ShipType;
    
   // DestroyerPiece
  
     public static void AddPiece(int xpixel,int ypixel) {


        int ydelta = Window.getHeight2()/NUM_ROWS;
        int xdelta = Window.getWidth2()/NUM_COLUMNS;
        int xpixelOffset = xpixel - Window.getX(0);
        int ypixelOffset = ypixel - Window.getY(0);
        if (xpixelOffset < 0  ||  xpixelOffset > Window.getWidth2())
            return;
        if (ypixelOffset < 0  ||  ypixelOffset > Window.getHeight2())
            return;

        int column = xpixelOffset/xdelta;       
        int row = ypixelOffset/ydelta;    

        board[row][column] = new DestroyerPiece(Color.LIGHT_GRAY,row,column,(int)(Math.random()*4+2),rotation);

    }
    public static void Rotate()
    {
        if(rotation == DestroyerPiece.Direction.Right)
            rotation = DestroyerPiece.Direction.Down;
        else
            rotation = DestroyerPiece.Direction.Right;
    }

    
    public static void Load()
    {
        
    int val = 0;
        for (int zrow=0;zrow<NUM_ROWS;zrow++){
            for (int zcol=0;zcol<NUM_COLUMNS;zcol++){
            if(board[zrow][zcol] != null){
                if(board[zrow][zcol].getColor() == Color.LIGHT_GRAY){
                    val = 1;
                    dir = board[zrow][zcol].getDir();
                }
               // else if
                sparseArray.add(new SparseArrayEntry(zrow,zcol,val,dir));
            }
            }
        }
        
        for (int r = 0;r < sparseArray.getNumRows();r++)
        {
            for (int c = 0;c < sparseArray.getNumCols();c++)
            {
                System.out.print(sparseArray.getValueAt(r, c) + " ");
            }
            System.out.println("");
        }
    System.out.println("=============================================");
    Board.Reset();
        
    }
   


    
    public static void unLoad(){
        Reset();
        for (int r = 0;r < sparseArray.getNumRows();r++)
        {
            for (int c = 0;c < sparseArray.getNumCols();c++)
            {
                if(sparseArray.getValueAt(r,c) == 1){
                board[r][c] = new DestroyerPiece(Color.LIGHT_GRAY,r,c,(int)(Math.random()*4+2),sparseArray.getDirAt(r, c));
                }
            }
        }
    }

    
     
    public static void Reset() {
        numPiecesAdded = 0;
//clear the board.
        for (int zrow=0;zrow<NUM_ROWS;zrow++)
            for (int zcol=0;zcol<NUM_COLUMNS;zcol++)
                board[zrow][zcol] = null; 
    
        rotation = DestroyerPiece.Direction.Right;

        sparseArray.clear();
    }
    
    public SparseArray getArray()
    {
        return(sparseArray);
    }
    
    public static void Draw(Graphics2D g) {
//draw grid
        int ydelta = Window.getHeight2()/NUM_ROWS;
        int xdelta = Window.getWidth2()/NUM_COLUMNS;
        
        g.setColor(Color.black);
        for (int zi = 1;zi<NUM_ROWS;zi++)
        {
            g.drawLine(Window.getX(0),Window.getY(zi*ydelta),
                    Window.getX(Window.getWidth2()),Window.getY(zi*ydelta));
        }
        
        for (int zi = 1;zi<NUM_COLUMNS;zi++)
        {
            g.drawLine(Window.getX(zi*xdelta),Window.getY(0),
                    Window.getX(zi*xdelta),Window.getY(Window.getHeight2()));
        }  
        
        for (int zrow=0;zrow<NUM_ROWS;zrow++)
        {
            for (int zcol=0;zcol<NUM_COLUMNS;zcol++)        
            {
                if (board[zrow][zcol] != null)
                    board[zrow][zcol].draw(g, zrow, zcol,xdelta, ydelta);
            }
        }        
        
    }
      
}