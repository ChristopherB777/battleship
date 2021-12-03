package battleship;

import java.awt.Color;

public class Player {
    private static Player currentTurn;
    private static Player players[] = new Player[2];
    private SparseArray array;
        public static SparseArray player1 = new SparseArray(10,10);
    public static SparseArray player2 = new SparseArray(10,10);

    
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
    
    public void switchTurn()
    {
        if(currentTurn == players[0])
            currentTurn =players[1];
        else
        currentTurn = players[0]; 
    }
    
    public SparseArray swapArray(SparseArray _array)
    {
        currentTurn.array = _array;
        return(currentTurn.array);
    }
    
    public static Player GetCurrentTurn() {
        return (currentTurn);
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
