    function render() {
        $.ajax({
            url: "tetris",
            type: "GET",

            success: function (data) {
                try {
                    data = JSON.parse(data);

                    var board = data.board;
                    var boardWidth = data.boardWidth;
                    var boardHeight = data.boardHeight;

                    var score = data.score;

                    for (var k = 0; k < boardHeight; k++) {
                        for (var l = 0; l < boardWidth; l++) {
                            var index = String(k * boardHeight + l);
                            var cell = document.getElementById(index);

                            if(board[k][l] === 0){
                                cell.style.backgroundColor = "gray"
                            }
                            else {
                                cell.style.backgroundColor = "white"
                            }
                        }
                    }

                    document.getElementById("score").innerText = score;

                    if (data.isGameOver) {
                        var x = document.getElementById("play-again-button");
                        x.style.display = "block";

                        var left = document.getElementById("left-control-button");
                        var right = document.getElementById("right-control-button");
                        var rotate = document.getElementById("rotate-control-button");

                        left.style.display = "none";
                        right.style.display = "none";
                        rotate.style.display = "none";

                        return;
                    }

                    setTimeout(render, 50);

                } catch (e) {
                    alert(data);
                }
            }
        });
    }
