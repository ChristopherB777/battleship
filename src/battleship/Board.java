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
    public static boolean TurnChange;
    public static int times = 1;
    
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

        if (TurnChange)
            return;
        
        
        int zrow = row;
        int zcol = column;
        int shipType = (int)(Math.random()*4+2);
        Player currentTurn = Player.GetCurrentTurn();
        if(times <= 2 && ((rotation == DestroyerPiece.Direction.Right && zcol+shipType > NUM_COLUMNS )||(rotation == DestroyerPiece.Direction.Down && zrow+shipType > NUM_ROWS)))
            return;
        
        if (currentTurn.getShipsPlace() < 5){
            if (row >= 10 && board[row][column] == null){
                if (rotation == DestroyerPiece.Direction.Right){
                    for (int i = 0;i<shipType;i++){
                        if (board[zrow][zcol] == null){
                            board[zrow][zcol] = new DestroyerPiece(Color.LIGHT_GRAY,row,column,shipType);
                            zcol++;
                    if(Player.GetCurrentTurn() == Player.players[0])
                        Player.players[1].pieceNum++;
                    else
                         Player.players[0].pieceNum++;
                        }
                    }
                    currentTurn.addShipsPlace();
                }
                else if (rotation == DestroyerPiece.Direction.Down){
                    for (int i = 0;i<shipType;i++){
                        if (board[zrow][zcol] == null){
                            board[zrow][zcol] = new DestroyerPiece(Color.LIGHT_GRAY,row,column,shipType);
                            zrow++;
                    if(Player.GetCurrentTurn() == Player.players[0])
                        Player.players[1].pieceNum++;
                    else
                         Player.players[0].pieceNum++;
                        }
                    }
                    currentTurn.addShipsPlace();
                }  
            }  
            if (currentTurn.getShipsPlace() == 5){
                Player.switchTurn();
                Board.Load();
                times++;
                    if (times <= 3){
                        TurnChange = true;
                        Board.unLoad();
                    }
            }
        }
        else
        {
            if(Player.getplayer1().isWinner() || Player.getplayer2().isWinner())
                 return;
            else  if (row < 10 && board[row][column] == null)
            {
                Color hitColor = null;
                if (Player.GetCurrentTurn().getArray().getValueAt(row+10,column) == 1)//hit
                {   
                    hitColor = Color.red;
                    board[zrow][zcol] = new DestroyerPiece(Color.red,zrow,zcol,1);
                    
                    sparseArray.add(new SparseArrayEntry(zrow,zcol,3,dir,1));
                    bgSound = new sound("explode1.wav");   
                    if (bgSound.donePlaying) 
                    bgSound.stopPlaying = true;
                    
                    Board.Load();
                    
                    if(Player.GetCurrentTurn() == Player.players[0])
                        Player.players[1].pieceNum--;
                    else
                         Player.players[0].pieceNum--;
                    boolean iswin = checkWin();
                    if(iswin)
                    {
                        Player.GetCurrentTurn().setWinner();
                        return;
                    }
//                    else
//                    Player.switchTurn();
//                    Board.unLoad();
                }
                else 
                {          
                    hitColor = Color.white;
                    board[row][column] = new DestroyerPiece(Color.white,row,column,1);
                    currentTurn.addMiss();
                    
                    bgSound = new sound("splash.wav");   
                    if (bgSound.donePlaying) 
                    bgSound.stopPlaying = true;
                    
                    Board.Load();
                    
//                    Player.switchTurn();
//                    Board.unLoad();
                    
                }
                times++;
                TurnChange = true;
                Player.switchTurn();
                Board.unLoad();
                if (hitColor == Color.red)
                    board[row+10][column].changeColor(hitColor);
                else
                    board[row+10][column] = new DestroyerPiece(hitColor,row,column,1);
                return;
            }
        }   
    }
    public static void Rotate()
    {
        if(rotation == DestroyerPiece.Direction.Right)
            rotation = DestroyerPiece.Direction.Down;
        else
            rotation = DestroyerPiece.Direction.Right;
    }
    
    
    
    public static boolean checkWin()
    {
        if (Player.getOtherTurn().pieceNum == 0)
            return (true);
        else 
            return(false);
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
                    ShipType = board[zrow][zcol].getType();
                }
                else if (board[zrow][zcol].getColor() == Color.white){
                    val = 2;
                    dir = board[zrow][zcol].getDir();
                    ShipType = board[zrow][zcol].getType();
                }
                else if (board[zrow][zcol].getColor() == Color.red){
                    val = 3;
                    dir = board[zrow][zcol].getDir();
                    ShipType = board[zrow][zcol].getType();
                }
                
                sparseArray.add(new SparseArrayEntry(zrow,zcol,val,dir,ShipType));
                }
            }
        }
        



    Player.GetCurrentTurn().swapArray(sparseArray);
    Board.Reset();   
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
                if( Player.getOtherTurn().getArray().getValueAt(r,c) == 1)
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
                else if ( Player.getOtherTurn().getArray().getValueAt(r,c) == 2)
                        board[r][c] = new DestroyerPiece(Color.white,r,c,1);
                else if ( Player.getOtherTurn().getArray().getValueAt(r,c) == 3)
                        board[r][c] = new DestroyerPiece(Color.red,r,c,1);
            }
        }

//        for (int r = 0;r < Player.GetCurrentTurn().getArray().getNumRows();r++)
//        {
//            for (int c = 0;c < Player.GetCurrentTurn().getArray().getNumCols();c++)
//            {
//                System.out.print(Player.GetCurrentTurn().getArray().getValueAt(r, c) + " ");
//            }
//            System.out.println("");
//        }
//        System.out.println("====================================================================");
//        
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
        
        if (TurnChange)
        {
            g.setColor(Color.BLUE);
            g.fillRect(28,400,602,415); 
            g.setColor(Color.white);
            StringCentered(g,250,200,"Press Space","Papyrus",50);
        }
        
        if (Player.GetCurrentTurn().isWinner())
        {
            g.setColor(Color.RED);
            if (Player.GetCurrentTurn() == Player.players[0])
            {
                StringCentered(g,300,554,"Player 1 is the Winner","Papyrus",60);
                StringCentered(g,300,500,"with "+Player.players[0].getMiss() + " miss","Papyrus",60);
            }
            else
            {
                StringCentered(g,300,554,"Player 2 is the Winner","Papyrus",60);
                StringCentered(g,300,500,"with "+Player.players[1].getMiss() + " miss","Papyrus",60);
            }
        }
        else
        {
            g.setColor(Color.white);
            if (Player.GetCurrentTurn() == Player.players[0])
                StringCentered(g,300,757,"Player 1 turn","Papyrus",45);
            else
                StringCentered(g,300,757,"Player 2 turn","Papyrus",45);
        }
        
    }
    public static void StringCentered(Graphics2D g,int xpos,int ypos,String text,String font,int size)
    {
        g.setFont (new Font (font,Font.PLAIN, size)); 
        int width = g.getFontMetrics().stringWidth(text);
        int height = g.getFontMetrics().getHeight();
        xpos = xpos - width/2;
        ypos = ypos - height/4;
        xpos = Window.getX(xpos);
        ypos = Window.getYNormal(ypos);
        g.drawString(text, xpos, ypos);           
    }       
}