import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;


public class Cell {
    static ArrayList<Cell> lastCandidateCells;
    private int i;
    private int j;
    private int value;
    private ArrayList<Integer> remainingCandidates;

    public Cell(int i, int j){
        this.i=i;
        this.j=j;
        this.value=0;
        this.remainingCandidates = new ArrayList<>();
        for (int num=1; num<=Main.n; num++){
            this.remainingCandidates.add(num);
        }
    }

    public ArrayList<Integer> getCandidateList()
    {
        return this.remainingCandidates;
    }
    public int getValue()
    {
        return this.value;
    }

    public int getI(){
        return this.i;
    }

    public int getJ(){
        return this.j;
    }

    private int getStartBlock_Index(int x){
        int sqrtN = (int)Math.sqrt(Main.n);
        return (x/sqrtN)*sqrtN;
    }

    public void setValue(int value)
    {
        this.value=value;
        this.remainingCandidates = new ArrayList<>();
        this.removeRowCandidates(value);
        this.removeColumnCandidates(value);
        this.removeBlockCandidates(value);
        if (lastCandidateCells.contains(this))
            lastCandidateCells.remove(this);
    }

    public void declareNumber(int value)
    {
        this.value=value;
    }

    private void removeRowCandidates(int value){
        for (int j = 0; j < Main.n; j++) {
            Main.field[this.i][j].removeCandidate(value);
        }
    }
    private void removeColumnCandidates(int value){
        for (int i = 0; i < Main.n; i++) {
            Main.field[i][this.j].removeCandidate(value);
        }
    }
    private void removeBlockCandidates(int value){
        int x = getStartBlock_Index(this.i);
        int y = getStartBlock_Index(this.j);
        for(int i=x; i<x+3; i++)
            for (int j=y; j<y+3; j++) {
                Main.field[i][j].removeCandidate(value);
            }
    }

    private void removeCandidate(int value)
    {
        Iterator itr = this.remainingCandidates.iterator();
        while (itr.hasNext())
        {
            int data = (Integer)itr.next();
            if (data == value)
                itr.remove();
        }
        if (this.remainingCandidates.size()==1 && !lastCandidateCells.contains(this))
            lastCandidateCells.add(this);
    }

    public void printCandidates(){
        for (int candidate : this.remainingCandidates)
            System.out.print(candidate + " ");
        System.out.println();
    }
}
