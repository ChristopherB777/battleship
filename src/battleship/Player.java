package battleship;

import java.awt.Color;

public class Player {
    private static Player currentTurn;
    private static Player players[] = new Player[2];
    private SparseArray array;
        public static SparseArray player1 = new SparseArray(10,10);
    public static SparseArray player2 = new SparseArray(10,10);

    
    public static void Reset()
    {
        players[0] = new Player(player1);
        players[1] = new Player(player2);
        currentTurn = players[0];
        
    }
    
    public void switchTurn()
    {
        
    }
    
    
    
    Player(SparseArray _player)
    {
        array = _player;
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
