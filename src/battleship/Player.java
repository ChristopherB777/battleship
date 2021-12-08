package battleship;

import java.awt.Color;

public class Player {
    private static Player currentTurn;
    public static Player players[] = new Player[2];
    private SparseArray array;
    private static SparseArray player1 = new SparseArray(10,10);
    private static SparseArray player2 = new SparseArray(10,10);
 

    
    Player(SparseArray _player)
    {
        array = _player;
        
 
        
    }
    
    public static void Reset()
    {
        players[0] = new Player(player1);
        players[1] = new Player(player2);
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
        return (currentTurn);
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
    

    
}
