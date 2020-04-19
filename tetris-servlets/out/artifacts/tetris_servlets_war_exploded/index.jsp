<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="app.Board" %>

<%!
    private String getColor(int color){
        if (color == 0){
            return "gray";
        }
        else {
            return "white";
        }
    }
%>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Tetris</title>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script type="text/javascript" src="script.js"></script>
    <link type="text/css" rel="stylesheet" href="main.css">
</head>
<body>
<div class="main">

    <div style="margin-top: 10px; text-align: center; ">
        <h1 style="font-size: 60px">TETRIS</h1>
    </div>

    <div style="margin-bottom: 1rem">
        <span id="scoreLabel" style="text-align: center; font-size: 25px">Score:</span>
        <span id="score" style="text-align: center; font-size: 25px">0</span>
    </div>

    <div class="field" style="display: flex; flex-direction: column">
        <%
            String stop = request.getParameter("stop");

            if (stop != null) {
                session.removeAttribute("board");
            }

            Board board = (Board) session.getAttribute("board");

            if (board == null) {
                board = new Board();
                session.setAttribute("board", board);
            }

            for (int i = 0; i < board.getBoardHeight(); i++) {
        %>
        <div class="row" style="display: flex; flex-direction: row">

            <%
                for (int j = 0; j < board.getBoardWidth(); j++) {
                    int index = i * board.getBoardHeight() + j;
            %>

            <div class="cell" id="<%=index%>" onclick=""
                 style="background-color: <%= getColor(board.get(i, j))%>"></div>
            <%
                }
            %>
        </div>
        <%
            }
        %>
    </div>

    <div style="margin-top: 10px; text-align: center; ">
        <button id="left-control-button"   style="display: none; font-size: 25px; width: 25%" onclick="moveLeft()"><--</button>
        <button id="right-control-button"  style="display: none; font-size: 25px; width: 25%; margin-right: 1rem" onclick="moveRight()">--></button>
        <button id="rotate-control-button" style="display: none; font-size: 25px; " onclick="rotateFigure()">Rotate</button>
    </div>

    <div style="margin-top: 1rem; text-align: center; ">
        <button style="font-size: 30px; width: 100%" id="start-game-button"
                onclick="render(); hideStartButton(); showControlButtons()">Start game</button>
    </div>

        <div id="play-again-button" style="margin-top: 1rem; display: none; ">
            <form action="index.jsp" method="get">
                <input type="hidden" name="stop">
                <input style="font-size: 30px;" type="submit" value="Play again">
            </form>
        </div>
</div>

<script>
    function moveLeft() {
        direction = -1;

        $.ajax({
            url: "tetris",
            type: "POST",
            data: ({
                moveDirection: direction
            })
        });
    }

    function moveRight() {
        direction = 1;

        $.ajax({
            url: "tetris",
            type: "POST",
            data: ({
                moveDirection: direction
            })
        });
    }

    function rotateFigure() {
        $.ajax({
            url: "tetris",
            type: "POST",
            data: ({
                rotateDirection: 1
            })
        });
    }

    function hideStartButton() {
        var x = document.getElementById("start-game-button");
        x.style.display = "none";
    }

    function showControlButtons() {
        var left = document.getElementById("left-control-button");
        var right = document.getElementById("right-control-button");
        var rotate = document.getElementById("rotate-control-button");

        left.style.display = "inline-block";
        right.style.display = "inline-block";
        rotate.style.display = "inline-block";
    }
</script>
</body>
</html>
