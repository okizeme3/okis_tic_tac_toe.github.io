import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.text.CollationElementIterator;
import java.awt.color.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;


public class TicTacToe{
    public static final Color purple = new Color(102,0,153);
    
  int boardWidth = 600;
  int boardHeight = 650; //50px for the text box 
  
  JFrame frame = new JFrame("Tic-Tac-Toe");
  JLabel textLable = new JLabel();
  JPanel textPanel = new JPanel();
  JPanel boardPanel = new JPanel();
  JPanel restartPanel = new JPanel();

  JButton[][] board = new JButton[3][3];
  String playerX = "X";
  String playerO = "O";
  String currentPlayer = playerX;
  String filepath = "E:\\java projects\\TicTacToe\\sound\\button press.wav";
  String fanfare = "E:\\java projects\\TicTacToe\\sound\\you win.wav";
  String failure = "E:\\java projects\\TicTacToe\\sound\\failure.wav";
  
  JButton restartButton = new JButton("restart");
  
  boolean tie = false;
  boolean gameOver = false;
  int turns = 0;
  int winX = 0;
  int winO = 0; 
  
  
  TicTacToe(){
    
    frame.setVisible(true);
    frame.setSize(boardWidth, boardHeight);
    frame.setLocationRelativeTo(null);
    frame.setResizable(false);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLayout(new BorderLayout());

    restartButton.setBackground(Color.black);
    restartButton.setForeground(Color.green);
    restartButton.setFont(new Font("Arial", Font.BOLD, 30));
    restartButton.setOpaque(true);

    restartPanel.setLayout(new BorderLayout());
    restartPanel.add(restartButton);
    frame.add(restartButton, BorderLayout.SOUTH);

    textLable.setBackground(Color.black);
    textLable.setForeground(TicTacToe.purple);
    textLable.setFont(new Font("Arial", Font.BOLD, 50));
    textLable.setHorizontalAlignment(JLabel.CENTER);
    textLable.setText("X:"+winX+"   Tic-Tac-Toe   "+"O:" + winO);
    textLable.setOpaque(true);

    textPanel.setLayout(new BorderLayout());
    textPanel.add(textLable);
    frame.add(textLable, BorderLayout.NORTH);

    boardPanel.setLayout(new GridLayout(3,3));
    boardPanel.setBackground(Color.black);
    frame.add(boardPanel);

    

    for (int r = 0; r<3; r++){
        for (int c = 0; c<3; c++){
            JButton tile = new JButton();
            board[r][c] = tile;
            boardPanel.add(tile);

            tile.setBackground(Color.black);
            tile.setForeground(TicTacToe.purple);
            tile.setFont(new Font("Arial", Font.BOLD, 120));
            tile.setFocusable(false);
            //tile.setText(currentPlayer);

            tile.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (gameOver) {
                        return;
                    }
                    JButton tile = (JButton) e.getSource();
                    if(tile.getText().equals("")){
                        playSound(filepath);
                        tile.setText(currentPlayer);
                        turns++;
                        checkWinner();
                        if(!gameOver) {
                            currentPlayer = currentPlayer.equals(playerX) ? playerO : playerX;
                            textLable.setText("X:"+winX+ "   "+currentPlayer + "'s turn.   "+"O:" + winO);
                        } 
                    }
                    
                }
            });
        }
    }
    restartButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent a){
            if (a.getSource() == restartButton){
                playSound(filepath);
                turns = 0;
                gameOver= false;
                tie = false;
                currentPlayer = playerX;
                textLable.setText("X:"+winX+"   Tic-Tac-Toe   "+"O:" + winO);
                for (int r = 0; r<3; r++){
                    for (int c = 0; c<3; c++){
                        resetColor(board[r][c]);
                        resetBoard(board[r][c]);
                    }
                }
            }
        }
    });
  }
  
  void checkWinner(){
    //horizontal
    for (int r = 0; r<3; r++){
        if(board[r][0].getText().equals("")){
            continue;
        }
        if (board[r][0].getText() == board[r][1].getText() &&
        board[r][1].getText() == board[r][2].getText()) {
            for (int i = 0; i<3; i++){
                setWinner(board[r][i]);
            }
            gameOver = true;
            addScore();
            return;
        }
    }
        //vertical
        for (int c = 0; c < 3; c++){
            if (board[0][c].getText().equals("")) {
                continue;
            }
            if(board[0][c].getText() == board[1][c].getText() &&
            board[1][c].getText() == board[2][c].getText()){
                for (int s =0; s<3; s++){
                    setWinner(board[s][c]);
                }
                gameOver = true;
                addScore();
                return;
            }
        }
        //diagonal left to right
        if (board[0][0].getText() == board[1][1].getText() &&
        board[1][1].getText() == board[2][2].getText() &&
        board[0][0].getText() != ""){
            for (int d = 0; d<3; d++){
                setWinner(board[d][d]);
            } 
            gameOver = true;
            addScore();
            return;
        }
        //antidiagonal right to left
        if (board[2][0].getText() == board[1][1].getText() &&
        board[1][1].getText() == board[0][2].getText() &&
        board[2][0].getText() != ""){
             setWinner(board[0][2]);
             setWinner(board[1][1]);
             setWinner(board[2][0]);
        
            gameOver = true;
            addScore();
            return;
        }

        if (turns == 9){
            for (int r = 0; r<3; r++){
                for (int c = 0; c<3; c++){
                    SetTie(board[r][c]);
                }
            }
            gameOver = true;
            addScore();
            return;
        }
  }
  void setWinner(JButton tile){
        playFanfare(fanfare);
        tile.setForeground(Color.green);
        tile.setBackground(Color.darkGray);
        textLable.setText("X:"+winX+" "+currentPlayer + " is the winner! "+"O:" + winO );
  }
  void SetTie(JButton tile){
    playFail(failure);
    tile.setForeground(Color.yellow);
    tile.setBackground(Color.darkGray);
    textLable.setText("X:"+winX+"         Tie!         "+"O:" + winO);
    tie = true;
  }
  void resetColor(JButton tile){
    tile.setForeground(TicTacToe.purple);
    tile.setBackground(Color.black);
  }
  void resetBoard(JButton tile){
    tile.setText("");
  }
  void playSound(String L){
    try {
        File soundPath = new File(L);
        
        if (soundPath.exists()){
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(soundPath);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInput);
            clip.start();
        }
    } catch (Exception e) {
        System.out.println("fail");
    }
  }
  void playFanfare(String s){
    try {
        File musicPath = new File(s);
        
        if (musicPath.exists()){
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInput);
            clip.start();
        }
    } catch (Exception e) {
        System.out.println("fail");
    }
  }
  void playFail(String s){
    try {
        File failPath = new File(s);
        
        if (failPath.exists()){
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(failPath);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInput);
            clip.start();
        }
    } catch (Exception e) {
        System.out.println("fail");
    }
  }
  void addScore(){
    if( gameOver && currentPlayer == playerX && tie == false){
        winX++;
        textLable.setText("X:"+winX+" "+currentPlayer + " is the winner! "+"O:" + winO );
    }
    if (gameOver&&currentPlayer == playerO) {
        winO++;
        textLable.setText("X:"+winX+" "+currentPlayer + " is the winner! "+"O:" + winO );
    }
  }
}
