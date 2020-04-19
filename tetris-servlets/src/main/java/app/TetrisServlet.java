package app;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/tetris")
public class TetrisServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        resp.setCharacterEncoding("UTF-8");

        PrintWriter pw = resp.getWriter();

        Board board = (Board) session.getAttribute("board");

        if (board == null){
            pw.print("Game is not started yet");
            return;
        }


        if (board.isGameOver()){
            String boardAsJson = new Gson().toJson(
                    new BoardDto(false, true, board.getPrintedBoard(), board.getScore()));

            pw.print(boardAsJson);

            return;
        }

        if (!board.isGameRunning()){
            board.startGame();
            String boardAsJson = new Gson().toJson(
                    new BoardDto(true, false, board.getPrintedBoard(), board.getScore()));

            pw.print(boardAsJson);
            return;
        }

        try{

            String boardAsJson = new Gson().toJson(
                    new BoardDto(true, false, board.getPrintedBoard(), board.getScore()));

            pw.print(boardAsJson);

        }catch (RuntimeException e){
            pw.print("Error: " + e.getMessage());
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        resp.setCharacterEncoding("UTF-8");

        PrintWriter pw = resp.getWriter();

        Board board = (Board) session.getAttribute("board");

        if (board == null){
            pw.print("Game is not started yet");
            return;
        }


        if (board.isGameOver()){
            return;
        }

        if (!board.isGameRunning()){

            return;
        }

        try{
            int moveDirection = 0;
            int rotateDirection = 0;

            try {
                moveDirection = Integer.parseInt(req.getParameter("moveDirection"));
            }catch (Exception e){

            }
            try {
                rotateDirection = Integer.parseInt(req.getParameter("rotateDirection"));
            }catch (Exception e){

            }

            board.rotateCurrentShape(rotateDirection);
            board.moveCurrentShape(moveDirection);

        }catch (RuntimeException e){
            pw.print("Error: " + e.getMessage());
        }
    }
}
