package app;

import static app.BoardSize.BOARD_HEIGHT;
import static app.BoardSize.BOARD_WIDTH;

public class BoardDto {
    // board dimensions (the playing area)
    private final int boardHeight = BOARD_HEIGHT;
    private final int boardWidth = BOARD_WIDTH;

    private boolean isGameRunning;
    private boolean isGameOver;

    private int score;

    // field
    private int[][] board;


    public BoardDto(boolean isGameRunning, boolean isGameOver, int[][] board, int score) {
        this.isGameRunning = isGameRunning;
        this.isGameOver = isGameOver;
        this.board = board;
        this.score = score;
    }
}
