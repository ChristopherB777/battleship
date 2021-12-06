package battleship;
import java.util.*;

public class SparseArray 
{
    private int numRows;
    private int numCols;
    private List<SparseArrayEntry> entries;
    
    public SparseArray()
    {
        entries = new ArrayList<SparseArrayEntry>();
    }
    public SparseArray(int _numRows, int _numCols)
    {
        numRows = _numRows;
        numCols = _numCols;
        entries = new ArrayList<SparseArrayEntry>();
    }
    public void add(SparseArrayEntry entry)
    {
        entries.add(entry);
    }
    

    
    public void clear()
    {
        entries.clear();
    }
    

    
    public int getNumRows()
    {
        return (numRows);
    }
    public int getNumCols()
    {
        return (numCols);
    }
    
    
    public DestroyerPiece.Direction getDirAt(int row, int col)
    {
        for(SparseArrayEntry aArray : entries)
        {
            if(aArray.getRow() == row && aArray.getCol() == col)
                return aArray.getDir();
        }
        return DestroyerPiece.Direction.Down;
    }
    
    
    
    public int getTypeAt(int row, int col)
    {
        for(SparseArrayEntry aArray : entries)
        {
            if(aArray.getRow() == row && aArray.getCol() == col)
                return aArray.getType();
        }
        return 0;
    }
    
    

    public int getValueAt(int row, int col)
    {
        for(SparseArrayEntry aArray : entries)
        {
            if(aArray.getRow() == row && aArray.getCol() == col)
                return aArray.getValue();
        }
        return 0;
    }
}




class SparseArrayEntry
{
    private int row;
    public int col;
    private int value;
    private int type;
    private DestroyerPiece.Direction dir;

    
    public SparseArrayEntry(int r, int c, int v, DestroyerPiece.Direction d, int t)
    {
        row = r;
        col = c;
        value = v;
        dir = d;
        type = t;
    }
    public int getRow()
    {
        return (row);
    }
    public int getCol()
    {
        return (col);
    }
    public int getValue()
    {
        return (value);
    }    
    
    public int getType()
    {
        return(type);
    }
    
    public DestroyerPiece.Direction getDir()
    {
        return(dir);
    }
    

    
}

