import java.util.ArrayList;
import java.util.Formattable;
import java.util.HashSet;
import java.util.Iterator;

public class Main {
    public static final int n=9;
    public static final int sqrtN=(int)Math.sqrt(n);
    public static Cell[][] field = new Cell[n][n];
    public static void main(String[] args) {
        initField();
        initStartPosition();
        System.out.println("Start position:\n******************");
        printSolution();
        // Solving Algorithm
        do {
        // Step 1: Filling last candidate cells
        FillLastCandidateCells();
        // Step 2: Filling the last one free possible position for numbers
        FillLastPossibleCells();
        }while (Cell.lastCandidateCells.size()>0);

        // Step 3: Back-tracking algorithm
        solveSudoku(field, 0, 0);
        System.out.println("Solution:\n******************");
        printSolution();
    }

    //
    public static void initField(){
        for (int i=0; i<n; i++)
            for (int j=0; j<n; j++)
                field[i][j] = new Cell(i, j);
        Cell.lastCandidateCells=new ArrayList<>();
    }
    public static void initStartPosition(){
        // Enter your sudoku here (no number = 0)
        int sudoku[][]={
                {8, 0, 0, 0, 1, 0, 0 ,7, 0},
                {0, 0, 4, 0, 5, 0, 0 ,0, 2},
                {0, 0, 9 ,0, 0 ,4 ,0 ,3 ,0},
                {0, 5 ,0 ,2 ,0 ,0, 0 ,0 ,0},
                {0, 0, 0, 0, 0, 0, 6, 8, 0},
                {9, 0 ,0 ,0, 6, 3, 0 ,0 ,0},
                {0, 0 ,0 ,0 ,0 ,0 ,3, 4, 0},
                {7, 0, 0 ,5, 2 ,0 ,0, 0, 1},
                {0 ,8, 0, 0 ,0 ,0 ,0 ,0 ,0}
        };

        for (int i=0; i<n; i++)
            for (int j = 0; j < n; j++) {
                if (sudoku[i][j]!=0)
                field[i][j].setValue(sudoku[i][j]);
            }
    }

    public static void FillLastCandidateCells()
    {
        while (Cell.lastCandidateCells.size()!=0)
        {
            Cell cell=Cell.lastCandidateCells.get(0);
            cell.setValue(cell.getCandidateList().get(0));
        }
    }

    public static void FillLastPossibleCells()
    {
        for (int num = 1; num <= n; num++) {
            for (int k = 0; k < n; k++) {
                Cell cell = FindLastRowCell(k,num); // return Cell or null
                if (cell!=null)
                    cell.setValue(num);
                cell = FindLastColumnCell(k,num);
                if (cell!=null)
                    cell.setValue(num);
                cell = FindLastBlockCell(k,num);
                if (cell!=null)
                    cell.setValue(num);
            }

        }
    }

    public static Cell FindLastRowCell(int k, int num)
    {
        int count=0;
        Cell cell=null;
        for (int i = 0; i < n; i++) {
            if (field[k][i].getValue()==num) return null;
            if (field[k][i].getCandidateList().contains(num))
            {
                cell=field[k][i];
                count++;
            }
            if (count>1) return null;
        }
        return cell;
    }
    public static Cell FindLastColumnCell(int k, int num)
    {
        int count=0;
        Cell cell=null;
        for (int i = 0; i < n; i++) {
            if (field[i][k].getValue()==num) return null;
            if (field[i][k].getCandidateList().contains(num))
            {
                cell=field[i][k];
                count++;
            }
            if (count>1) return null;
        }
        return cell;
    }
    public static Cell FindLastBlockCell(int k, int num)
    {
        int count=0;
        Cell cell=null;
        int i=(k/sqrtN)*sqrtN;
        int j=(k%sqrtN)*sqrtN;
        for (int x=i; x<i+sqrtN; x++)
            for (int y=j; y<j+sqrtN; y++) {
                if (field[x][y].getValue()==num) return null;
                if (field[x][y].getCandidateList().contains(num))
                {
                    cell=field[x][y];
                    count++;
                }
                if (count>1) return null;
            }
        return cell;
    }


    static boolean solveSudoku(Cell grid[][], int row, int col)
    {
        if (row == n - 1 && col == n)
            return true;
        if (col == n) {
            row++;
            col = 0;
        }
        if (grid[row][col].getValue() != 0)
            return solveSudoku(grid, row, col + 1);
        for (int num: grid[row][col].getCandidateList()) {
            if (isSafe(grid, row, col, num)) {
                grid[row][col].declareNumber(num);
                if (solveSudoku(grid, row, col + 1))
                    return true;
            }
            grid[row][col].declareNumber(0);
        }
        return false;
    }

    static boolean isSafe(Cell[][] grid, int row, int col, int num)
    {
        //checking row
        for (int x = 0; x < n; x++)
            if (grid[row][x].getValue() == num)
                return false;
        //checking column
        for (int x = 0; x < n; x++)
            if (grid[x][col].getValue() == num)
                return false;
        //checkinng block
        int startRow = row - row % sqrtN;
        int startCol = col - col % sqrtN;
        for (int i = 0; i < sqrtN; i++)
            for (int j = 0; j < sqrtN; j++)
                if (grid[i + startRow][j + startCol].getValue() == num)
                    return false;
        return true;
    }
    public static void printSolution()
    {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int value=field[i][j].getValue();
                if(value!=0) System.out.print(value+" ");
                else System.out.print("_ ");
            }
            System.out.println();
        }
    }

}