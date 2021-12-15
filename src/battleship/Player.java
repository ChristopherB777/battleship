package battleship;

import java.awt.Color;

public class Player {
    private static Player currentTurn;
    public static Player players[] = new Player[2];
    private SparseArray array;
    private static SparseArray player1 = new SparseArray(20,10);
    private static SparseArray player2 = new SparseArray(20,10);
    private int numPiecesAdded;
    private int miss;
    public int pieceNum;
    private boolean winner;

    
    Player(SparseArray _player, int shipnum)
    {
        array = _player;
        pieceNum = shipnum;
    }
    
    public static void Reset()
    {
        players[0] = new Player(player1,0);
        players[1] = new Player(player2,0);
        currentTurn = players[0];
        
        
    }
    
    public static void switchTurn()
    {
        if(currentTurn == players[0]){
            currentTurn =players[1];
            System.out.println("player2");
        }
        else{
        currentTurn = players[0]; 
        System.out.println("player1");
        }
    }
    
    public void swapArray(SparseArray _array)
    {
        
        for (int r = 0;r <_array.getNumRows();r++)
        {
            for (int c = 0;c < _array.getNumCols();c++)
            {
                array.add(new SparseArrayEntry(r,c,_array.getValueAt(r, c),_array.getDirAt(r, c),_array.getTypeAt(r, c)));

            }
        }
    }
    
    public static Player GetCurrentTurn() {
        if(currentTurn == players[0])
            return(currentTurn =players[0]);
        else
        return(players[1]);
    }
    
    public static Player getOtherTurn()
    {
        if(currentTurn == players[0])
            return(players[1]);
        else
        return (players[0]);
    }
    
    
    public SparseArray getArray()
    {
        return(array);
    }
    
    public static Player getplayer1()
    {
        return(players[0]);
    }
    public static Player getplayer2()
    {
        return(players[1]);
    }   
    
    public void addShipsPlace()
    {
        numPiecesAdded++;
    }
    public int getPieceNum()
    {
        return(pieceNum);
    }
    
    public int getShipsPlace()
    {
        return (numPiecesAdded);
    }
    public void addMiss()
    {
        miss++;
    }
    public int getmMiss()
    {
        return (miss);
    }
    
    public boolean isWinner()
    {
        return(winner);
    }
    
    public void setWinner()
    {
        winner = true;
    }
    
}
