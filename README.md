# Tic-Tac-Toe-MinimaxAlgorithm
 An implementation of Minimax AI Algorithm on Tic-Tac-Toe (or Noughts and Crosses) game. Try it: Tic-tac-toe - Minimax.
 <p align="center">
  <img width="414" alt="image" src="https://user-images.githubusercontent.com/99815527/196591181-2435536b-7efc-443c-8655-c10c8efc4f2b.png"></image>
 </p>
 
## What is Minimax?
Minimax is a artificial intelligence applied in two player games, such as tic-tac-toe, checkers, chess and go. This games are known as zero-sum games, because in a mathematical representation: one player wins (+1) and other player loses (-1) or both of anyone not to win (0).

## How does it works?
The algorithm search, recursively, the best move that leads the *Max* player to win or not lose (draw). It consider the current state of the game and the available moves at that state, then for each valid move it plays (alternating *min* and *max*) until it finds a terminal state (win, draw or lose).
## Finding the bestMove: 
We shall be introducing a new function called bestMove(). This function evaluates all the available moves using minimax() and then returns the best move the maximizer can make. The pseudocode is as follows : 
```
public Move bestMove(int[][] arr,boolean turn ){
        int best = -1000;
        Move result = new Move(-1,-1);
        
        for (int i = 0; i < arr.length; i++) {
                for (int j = 0; j < arr[i].length; j++){  
                    if(arr[i][j] == 0){
                        arr[i][j] = 2;
                        int score = minimax(arr, !turn);
                        arr[i][j] = 0;
                        if(score>best){
                            best = score;
                            result.setX(i);
                            result.setY(j);
                        }
                    }
                }
        }
        return result;
    }
```
## Minimax:
To check whether or not the current move is better than the best move we take the help of minimax() function which will consider all the possible ways the game can go and returns the best value for that move, assuming the opponent also plays optimally 

The code for the maximizer and minimizer in the minimax() function is similar to findBestMove(), the only difference is, instead of returning a move, it will return a value. Here is the pseudocode :  
```
public int minimax(int[][] arr, boolean isTurn){
        int s = checkResult(arr);
        if(s == 10) return s;
        if(s == -10) return s;
        if(checkMove(arr) == false)
            return 0;
        if (isTurn == true){
            int best = -10000;
            for (int i = 0; i < arr.length; i++) {
                for (int j = 0; j < arr[i].length; j++){
                    if(arr[i][j]==0){
                        arr[i][j] =2;
                        best = Math.max(best, minimax(arr, !isTurn));
                        arr[i][j] =0;
                    }
                }
            }
            return best;
        }
        else {
            int best = 10000;
            for (int i = 0; i < arr.length; i++) {
                for (int j = 0; j < arr[i].length; j++){
                    if(arr[i][j]==0){
                        arr[i][j] =1;
                        best = Math.min(best, minimax(arr, !isTurn));
                        arr[i][j] =0;
                    }
                }
            }
            return best;
        }
    }
```
## Checking for GameOver state:
To check whether the game is over and to make sure there are no moves left we use checkMove() function. It is a simple straightforward function which checks whether a move is available or not and returns true or false respectively. Pseudocode is as follows :
```
 public boolean checkMove(int[][] arr){
        for (int i = 0; i < arr.length; i++) {
                for (int j = 0; j < arr[i].length; j++) {
                    if(arr[i][j]==0){
                        return true;
                    }      
            }
        } 
         return false;
    }
```
## Implements MouseListener:
```
public void mousePressed(MouseEvent e) {
            for (int i = 0; i < arBT.length; i++) {
                for (int j = 0; j < arBT[i].length; j++) {
                    if (e.getButton() == 1 && e.getSource()==arBT[i][j] && turn == false && arrEnd[i][j] == false){
                        arBT[i][j].setText("X");
                        arr[i][j] = 1;
                        arrEnd[i][j] = true;
                        turn = true;
                        btReset.setEnabled(true);
                    }             
                } 
            }
            if(turn == true && checkMove(arr)==true){
                Move p = bestMove(arr, turn);
                int i = p.getX();
                int j = p.getY();
                arBT[i][j].setText("O");
                arrEnd[i][j]=true;
                arr[i][j] = 2;
                turn = false;
            }
            int check = checkResult(arr);
            if(check == 10 && endGame == false){
                JOptionPane.showConfirmDialog(null, "Máy Thắng !!!","Kết thúc trò chơi",JOptionPane.DEFAULT_OPTION);
                end();
                endGame = true;
            }else if(check == -10 && endGame == false){
                JOptionPane.showConfirmDialog(null, "Người Thắng !!!","Kết thúc trò chơi",JOptionPane.DEFAULT_OPTION);
                end();
                endGame = true;
            }
            if(endGame == false){
                if(checkMove(arr) == false){
                    JOptionPane.showConfirmDialog(null, "Hoà nhau !!!","Kết thúc trò chơi",JOptionPane.DEFAULT_OPTION);
                endGame = true;
                }
            }
        }
```
## Explanation :
![4db4c04a-890f-405c-9399-6e1dd0448a18](https://user-images.githubusercontent.com/99815527/196593617-888c0884-e14f-4f07-8e31-a165ec3bc003.png)




