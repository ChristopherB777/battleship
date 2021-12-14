package battleship;
import java.awt.*;
import javax.sound.sampled.*;


public class Board {
    private static int numPiecesAdded = 0;
    public final static int NUM_ROWS = 20;
    public final static int NUM_COLUMNS = 10;          
    private static int points;
    private static DestroyerPiece board[][] = new DestroyerPiece[NUM_ROWS][NUM_COLUMNS];
    public static DestroyerPiece.Direction rotation;
    public static SparseArray sparseArray = new SparseArray(20,10);
    static DestroyerPiece.Direction dir = DestroyerPiece.Direction.Right; 
    public static int ShipType;
    public static sound bgSound = null;    

    
   // DestroyerPiece
    
    
//    public static void addPlanes()
//    {
//        int row = (int)(Math.random()*4+9);
//        int col = (int)(Math.random()*1+9);
//        board[row][col] = new DestroyerPiece(Color.LIGHT_GRAY,row,col,1);
//        row = 0;
//        col = 0;
//    }
  
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


        
        
        int zrow = row;
        int zcol = column;
        int shipType = (int)(Math.random()*4+2);
        if((rotation == DestroyerPiece.Direction.Right && zcol+shipType > NUM_COLUMNS )||(rotation == DestroyerPiece.Direction.Down && zrow+shipType > NUM_ROWS))
            return;
       // Player currentTurn = Player.GetCurrentTurn();
        int times = 0;
        
        if (Player.GetCurrentTurn().getShipsPlace() < 5){
            if (row >= 10 && board[row][column] == null){
                if (rotation == DestroyerPiece.Direction.Right){
                    for (int i = 0;i<shipType;i++){
                        if (board[zrow][zcol] == null){
                            board[zrow][zcol] = new DestroyerPiece(Color.LIGHT_GRAY,row,column,shipType);
                            zcol++;
                            Player.GetCurrentTurn().pieceNum++;
                        }
                    }
                    Player.GetCurrentTurn().addShipsPlace();
                }
                else if (rotation == DestroyerPiece.Direction.Down){
                    for (int i = 0;i<shipType;i++){
                        if (board[zrow][zcol] == null){
                            board[zrow][zcol] = new DestroyerPiece(Color.LIGHT_GRAY,row,column,shipType);
                            zrow++;
                            Player.GetCurrentTurn().pieceNum++;
                        }
                    }
                    Player.GetCurrentTurn().addShipsPlace();
                }  
            }  
            if (Player.GetCurrentTurn().getShipsPlace() == 5){
                Player.switchTurn();
                Board.Load();
                times++;
                    if (times == 1)
                        Board.unLoad();
            }
            
        }
        else
        {
            if (row < 10 && board[row][column] == null)
            {
                if (Player.GetCurrentTurn().getArray().getValueAt(row+10,column) == 1)//hit
                {   
                    board[zrow][zcol] = new DestroyerPiece(Color.red,zrow,zcol,1);
                    bgSound = new sound("explode1.wav");   
                    if (bgSound.donePlaying) 
                    bgSound.stopPlaying = true;
                    
                    Board.Load();
                    Player.switchTurn();
                    
                    
                    Board.unLoad();
                    
                   board[zrow+10][zcol] = new DestroyerPiece(Color.red,zrow,zcol,1);
                }
                else 
                {                
                    board[row][column] = new DestroyerPiece(Color.white,row,column,1);
                    
                    Player.GetCurrentTurn().addMiss();
                    
                    bgSound = new sound("splash.wav");   
                    if (bgSound.donePlaying) 
                    bgSound.stopPlaying = true;
                    
                    Board.Load();
                    Player.switchTurn();
                    Board.unLoad();
                    
            //        board[row][column] = new DestroyerPiece(Color.white,row,column,1);
                }
            }
        }
    //    System.out.println(Player.GetCurrentTurn().getArray().getValueAt(row+10,column));
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
        
        for (int zrow=0;zrow<NUM_ROWS;zrow++){
            for (int zcol=0;zcol<NUM_COLUMNS;zcol++){
            if(board[zrow][zcol] != null){
                if(board[zrow][zcol].getColor() == Color.LIGHT_GRAY){
                    dir = board[zrow][zcol].getDir();
                    ShipType = board[zrow][zcol].getType();
                sparseArray.add(new SparseArrayEntry(zrow,zcol,1,dir,ShipType));
                }
                else if (board[zrow][zcol].getColor() == Color.white){
            sparseArray.add(new SparseArrayEntry(zrow,zcol,2,DestroyerPiece.Direction.Right,1));
                }
                else if (board[zrow][zcol].getColor() == Color.red){
                sparseArray.add(new SparseArrayEntry(zrow,zcol,3,DestroyerPiece.Direction.Right,1));
                }
                }
            }
        }

    Player.GetCurrentTurn().swapArray(sparseArray);
    //Board.Reset();   
    sparseArray.clear();
    //   sparseArray.add(new SparseArrayEntry(1,1,1,DestroyerPiece.Direction.Down,1)); 
//        for (int r = 0;r < Player.GetCurrentTurn().getArray().getNumRows();r++)
//        {
//            for (int c = 0;c < Player.GetCurrentTurn().getArray().getNumCols();c++)
//            {
//                System.out.print(Player.GetCurrentTurn().getArray().getValueAt(r, c) + " ");
//            }
//            System.out.println("");
//        }
    
    }
   


    
    public static void unLoad(){
        Reset();
        //Player.switchTurn();
        for (int r = 0;r < Player.getOtherTurn().getArray().getNumRows();r++)
        {
            for (int c = 0;c <  Player.getOtherTurn().getArray().getNumCols();c++)
            {
                if( Player.getOtherTurn().getArray().getValueAt(r,c) == 2){
                        board[r][c] = new DestroyerPiece(Color.white,r,c,1);
                }
                else if( Player.getOtherTurn().getArray().getValueAt(r,c) == 3){
                        board[r][c] = new DestroyerPiece(Color.red,r,c,1);
                }
                else if( Player.getOtherTurn().getArray().getValueAt(r,c) == 1)
                {
                    int zrow = r;
                    int zcol = c;
                    int shipType =  Player.getOtherTurn().getArray().getTypeAt(r, c);
                    for (int i = 0;i<shipType;i++)
                    {
                        board[zrow][zcol] = new DestroyerPiece(Color.LIGHT_GRAY,zrow,zcol,shipType);
                        if(Player.getOtherTurn().getArray().getDirAt(r, c) == DestroyerPiece.Direction.Right)
                        zcol++;
                        else if(Player.getOtherTurn().getArray().getDirAt(r, c) == DestroyerPiece.Direction.Down)
                        zrow++;
                    }
                }
            }
        }
}
    //sparseArray.getDirAt(r, c)
     
    public static void Reset() {
        numPiecesAdded = 0;
//clear the board.
        for (int zrow=0;zrow<NUM_ROWS;zrow++)
            for (int zcol=0;zcol<NUM_COLUMNS;zcol++)
                board[zrow][zcol] = null; 
    
        rotation = DestroyerPiece.Direction.Right;
        ShipType = 0;
    }
    
//    public SparseArray getArray()
//    {
//        return(sparseArray);
//    }
    
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