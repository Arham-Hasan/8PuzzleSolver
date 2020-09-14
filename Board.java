import java.util.Arrays;
import java.util.LinkedList;


public final class Board {
    private final int [][] board;
    private final int size;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles){
        this.board = Arrays.copyOf(tiles, tiles.length);
        this.size = board.length;
    }

    // string representation of this board
    public String toString(){
        StringBuilder str = new StringBuilder();
        str.append(dimension() + "\n");
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++)
                str.append(String.format("%2d ", board[row][col] ));
            str.append("\n");
        }

        return str.toString();
    }

    // board dimension n
    public int dimension(){
        return size;
    }

    // number of tiles out of place
    public int hamming(){
        int counter=0;
        for(int i =0; true; i++){
            for(int j=0; j < size;j++){
                if(i==size-1 && j==size-1){
                    if(board[i][j] != 0)counter ++;
                    return counter;
                }
                else if(board[i][j] != 1+(i*size)+j && board[i][j]!= 0){
                    counter++;
                }
            }
        }
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan(){
        int number = 0,goalRow = 0,goalCol = 0,sum = 0,difference = 0;
        for(int i=0; i < size; i++){
            for(int j=0; j < size; j++){
                if(board[i][j]!=0){
                    number = board[i][j];
                    goalRow = (number-1)/size;
                    goalCol = (number-1)%size;
                    difference = Math.abs(i - goalRow) + Math.abs(j - goalCol);
                    sum = sum + difference;
                }
            }
        }
        return sum;
    }

    // is this board the goal board?
    public boolean isGoal(){
        for(int i=0; i < size; i++){
            for(int j=0; j < size; j++){
                if(i==size-1 && j==size-1){
                    if(board[i][j] == 0) return true;
                    return false;
                }
                if(board[i][j] != 1+(size*i)+j) return false;
            }
        }
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y){
        if (y==this) return true;
        if ( !(y instanceof Board) || ((Board)y).board.length != this.board.length) return false;
        for (int row = 0; row < size; row++)
            for (int col = 0; col < size; col++)
                if (((Board) y).board[row][col] != board[row][col]) return false;

        return true;
    }

    private int[][] copyArray(int[][] grid){
        int[][] copy = new int[size][size];
        for(int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                copy[i][j] = grid[i][j];
            }
        }
        return copy;
    }

    // all neighboring boards
    public Iterable<Board> neighbors(){
        LinkedList<Board> neighbors = new LinkedList<Board>();
        int goalRow=0,goalCol=0;
        int[][] duplicate;
        int[][] coordinates={{1,0},{0,1},{-1,0},{0,-1}};
        int[] blankPosition = spaceRowCol();
        for(int i=0; i < 4; i++){
            goalRow = blankPosition[0] + coordinates[i][0];
            goalCol = blankPosition[1] + coordinates[i][1];

            if(goalRow>=0 && goalRow<size && goalCol>=0 && goalCol<size){
                duplicate = copyArray(this.board);
                duplicate[blankPosition[0]][blankPosition[1]] = duplicate[goalRow][goalCol];
                duplicate[goalRow][goalCol] = 0;
                neighbors.add(new Board(duplicate));
            }
        }
        return neighbors;
    }

    private int[] spaceRowCol() {
        int[] coordinates = new int[2];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == 0) {
                    coordinates[0] = i;
                    coordinates[1] = j;
                    return coordinates;
                }
            }
        }
        return coordinates;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin(){
         int[][] duplicate = copyArray(this.board);
         int temp=0;
        if(duplicate[0][0]!= 0 && duplicate[0][1]!=0){
            temp = duplicate[0][0];
            duplicate[0][0]=duplicate[0][1];
            duplicate[0][1]=temp;
            return new Board(duplicate);
        }
        temp = duplicate[1][0];
        duplicate[1][0]=duplicate[1][1];
        duplicate[1][1]=temp;
        return new Board(duplicate);

    }

}
