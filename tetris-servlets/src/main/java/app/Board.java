package app;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static app.BoardSize.BOARD_HEIGHT;
import static app.BoardSize.BOARD_WIDTH;

public class Board {

    private final static int EMPTY = 0;
    public final static int WHITE = 1;
    public final static int BLACK = 2;

    //board dimensions (the playing area)
    private final int boardHeight = BOARD_HEIGHT;
    private final int boardWidth = BOARD_WIDTH;

    // field
    private int[][] board = new int[boardHeight][boardWidth];

    // array with all the possible shapes
    private Shape[] shapes = new Shape[7];

    // currentShape and next one
    private static Shape currentShape, nextShape;

    // game looper
    private Timer looper;

    private int FPS = 60;

    private int delay = 1000/FPS;

    private boolean gamePaused = false;

    private boolean gameOver = false;

    private boolean isGameRunning = false;

    private int score = 0;

    // buttons press lapse
    private Timer buttonLapse = new Timer(300, new ActionListener(){

        @Override
        public void actionPerformed(ActionEvent e) {
            buttonLapse.stop();
        }});

    // score

    public Board(){
        // create game looper
        looper = new Timer(delay, new GameLooper());

        // create shapes
        shapes[0] = new Shape(new int[][]{
                {1, 1, 1, 1}   // I shape;
        }, this, 1);

        shapes[1] = new Shape(new int[][]{
                {1, 1, 1},
                {0, 1, 0},   // T shape;
        },  this, 2);

        shapes[2] = new Shape(new int[][]{
                {1, 1, 1},
                {1, 0, 0},   // L shape;
        },  this, 3);

        shapes[3] = new Shape(new int[][]{
                {1, 1, 1},
                {0, 0, 1},   // J shape;
        }, this, 4);

        shapes[4] = new Shape(new int[][]{
                {0, 1, 1},
                {1, 1, 0},   // S shape;
        },  this, 5);

        shapes[5] = new Shape(new int[][]{
                {1, 1, 0},
                {0, 1, 1},   // Z shape;
        },  this, 6);

        shapes[6] = new Shape(new int[][]{
                {1, 1},
                {1, 1},   // O shape;
        }, this, 7);

    }

    public int get(int i, int j) {
        return this.board[i][j];
    }

    public int[][] getBoard(){
        return board;
    }

    public int getBoardHeight() {
        return boardHeight;
    }

    public int getBoardWidth() {
        return boardWidth;
    }

    public int getScore() {
        return score;
    }

    private void update(){
        if(gamePaused || gameOver)
        {
            return;
        }

        currentShape.update();
    }

    public void startGame(){
        stopGame();
        setNextShape();
        setCurrentShape();
        gameOver = false;
        isGameRunning = true;
        looper.start();
    }

    public void stopGame(){
        score = 0;

        for(int row = 0; row < board.length; row++)
        {
            for(int col = 0; col < board[row].length; col ++)
            {
                board[row][col] = 0;
            }
        }
        looper.stop();
    }

    public void setCurrentShape(){
        currentShape = nextShape;

        setNextShape();

        for(int row = 0; row < currentShape.getCoords().length; row ++)
        {
            for(int col = 0; col < currentShape.getCoords()[0].length; col ++)
            {
                if(currentShape.getCoords()[row][col] != 0)
                {
                    if(board[currentShape.getY() + row][currentShape.getX() + col] != 0)
                    {
                        gameOver = true;
                        isGameRunning = false;
                    }
                }
            }
        }
    }

    public void setNextShape(){
        int index = (int)(Math.random()*shapes.length);
        nextShape = new Shape(shapes[index].getCoords(), this, shapes[index].getColor());
    }

    class GameLooper implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            update();
        }
    }

    public void addScore(){
        score += boardWidth;
    }

    public boolean isGameOver(){
        return this.gameOver;
    }

    public boolean isGameRunning(){
        return this.isGameRunning;
    }

    public void moveCurrentShape(int moveDirection){
        if(moveDirection > 0){
            currentShape.setDeltaX(1);
        }
        if(moveDirection < 0){
            currentShape.setDeltaX(-1);
        }
    }

    public void rotateCurrentShape(int rotateDirection){
        if(rotateDirection != 0){
            currentShape.rotateShape();
        }
    }

    public int[][] getPrintedBoard() {
        int currentShapeX;
        int currentShapeY;
        int[][] currentShapeCoord;
        int[][] printedBoard = new int[boardHeight][boardWidth];
        int shapeColor = currentShape.getColor();

        currentShapeX = currentShape.getX();
        currentShapeY = currentShape.getY();
        currentShapeCoord = currentShape.getCoords();

        for (int i = 0; i < board.length; i++)
        {
            for (int j = 0; j < board[i].length; j++){
                printedBoard[i][j] = board[i][j];
            }
        }

        for (int row = 0; row < currentShapeCoord.length; row++) {
            for (int col = 0; col < currentShapeCoord[0].length; col++) {
                if (currentShapeCoord[row][col] != 0)
                    printedBoard[currentShapeY + row][currentShapeX + col] = shapeColor;
            }
        }

        return printedBoard;
    }
}

