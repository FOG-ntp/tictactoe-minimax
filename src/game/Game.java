
package game;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author nguye
 */
public class Game extends JFrame implements MouseListener{
    
    private JPanel mainP, p1, p2;
    private JButton[][] arBT;
    private int[][] arr;
    private boolean[][] arrEnd;
    private JButton btReset;
    private boolean turn;
    private boolean endGame;
    
    public Game(){
        
        arBT = new JButton[3][3];
        arr = new int[3][3];
        arrEnd = new boolean[3][3];
        
        taoGUI();
        
        setTitle("Game Caro");
        setSize(290,350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void addP1(){
        mainP.add(p1 = new JPanel(),BorderLayout.CENTER);
        p1.setLayout(new GridLayout(3,3,5,5));
        for (int i = 0; i < arBT.length; i++) {
            for (int j = 0; j < arBT[i].length; j++) {
                arBT[i][j] = new JButton();
                arBT[i][j].addMouseListener(this);
                arBT[i][j].setPreferredSize(new Dimension(80,80));
                arBT[i][j].setFont(new Font("Times New Roman",Font.BOLD,40));
                p1.add(arBT[i][j]);
            }
        }
    }
    
    
    public void reset(){
        for (int i = 0; i < arBT.length; i++) {
            for (int j = 0; j < arBT[i].length; j++) {
                arBT[i][j].setText("");
                arr[i][j]=0;
                arrEnd[i][j] = false;
                turn=false;
                endGame = false;
            }
            
        }
    }

    private void taoGUI() {
        add(mainP = new JPanel());
        addP1();
        mainP.add(p2 = new JPanel(),BorderLayout.SOUTH);
        p2.setPreferredSize(new Dimension(300,100));
        p2.add(btReset = new JButton("Chơi lại"));
        btReset.setEnabled(false);
        btReset.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
              reset();
              btReset.setEnabled(false);
            }
            
        });
    }
    
    public int checkResult(int[][] arr){
        for (int i = 0; i < arr.length; i++) {//row
            int a = arr[i][0];
            if(a==arr[i][1] && arr[i][1] == arr[i][2]){
                if(a == 1 ){
                    return -10;
                }
                else if (a == 2){
                    return 10;
                }
            }
        } 
        for (int i = 0; i < arr.length; i++) {//column
            int a = arr[0][i];
            if(a==arr[1][i] && arr[1][i] == arr[2][i]){
                 if(a == 1 ){
                    return -10;
                }
                else if (a == 2){
                    return 10;
                }
            }
        }
        if (arr[0][0] == arr[1][1] && arr[0][0] == arr[2][2]){
            if (arr[0][0]==1){
                return -10;
            }else if (arr[0][0] ==2){
                return 10;
            }
        }
        if (arr[2][0] == arr[1][1] && arr[2][0] == arr[0][2]){
            if (arr[2][0]==1){
                return -10;
            }else if (arr[2][0] ==2){
                return 10;
            }
        }
        return 0;
    }
    
    
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
    
    @Override
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
        
        public void end(){
            for (int i = 0; i < arBT.length; i++) {
                for (int j = 0; j < arBT[i].length; j++) {
                    arrEnd[i][j] = true;  
                }  
            }
        }
        
        
        public static void main(String[] args) {
        new Game();
    }
    @Override
    public void mouseClicked(MouseEvent e) {
    
    }

    
    

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
