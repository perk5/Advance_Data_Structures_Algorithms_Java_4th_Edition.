import java.util.*;

public class TicTacToe {

    protected final int x = 1;
    protected final int o = -1;
    protected final int Empty = 0;
    protected final int[][] matrix = new int[3][3];
    boolean check = true;
    int player;

    TicTacToe(){
        clearBoard();
    }

    public void clearBoard(){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                matrix[i][j] = Empty;   
            } 
        }
        player = x;
    }

    public void printBoard(){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                System.out.print(matrix[i][j] + " ");   
            } 
            System.out.println();
        }
    }
    
    public void GameEntries(int value1, int value2) throws IllegalArgumentException{

        if(value1 < 0 || value1 > 2 || value2 < 0 || value2 > 2){
            throw new IllegalArgumentException("Out of Range..");
        }

        if(matrix[value1][value2] == 0){
            matrix[value1][value2] = player;
            
            
            if(check){
                CheckWin(player);
                player = - player;
            }
            
        }else{
            throw new IllegalArgumentException("Space Already occupied...");
        }
    }


    public boolean CheckWin(int mark){
        if(matrix[0][0] + matrix[0][1] + matrix[0][2] == mark * 3 
            || matrix[1][0] + matrix[1][1] + matrix[1][2] == mark * 3
            || matrix[2][0] + matrix[2][1] + matrix[2][2] == mark * 3
            || matrix[0][0] + matrix[1][0] + matrix[2][0] == mark * 3
            || matrix[0][1] + matrix[1][1] + matrix[2][1] == mark * 3
            || matrix[0][2] + matrix[1][2] + matrix[2][2] == mark * 3
            || matrix[0][2] + matrix[1][1] + matrix[2][0] == mark * 3
            || matrix[0][0] + matrix[1][1] + matrix[2][2] == mark * 3
        ){
            if(mark > 0){
                printBoard();
                System.out.println("X" + " wins");
                
            }else{
                printBoard();
                System.out.println("O" + " wins");
                
            }
            check = false;
            return true;
            
        }
        return false;
    }

    public static void main(String args[]){
        TicTacToe game = new TicTacToe();
        
        game.GameEntries(0,0);
        game.GameEntries(2,2);
        game.GameEntries(0,1);
        game.GameEntries(2,1);
        game.GameEntries(0,2);
        game.GameEntries(1,1);
        game.GameEntries(2,0);
        game.GameEntries(1,0);
        game.GameEntries(1,2);
        if(game.check != false){
            System.out.println("Game Tied");
        }
        

    }
}
