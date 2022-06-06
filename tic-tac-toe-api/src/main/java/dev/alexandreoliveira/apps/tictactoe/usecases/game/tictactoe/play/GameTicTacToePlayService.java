package dev.alexandreoliveira.apps.tictactoe.usecases.game.tictactoe.play;

import dev.alexandreoliveira.apps.tictactoe.infra.database.entities.MovementEntity;
import dev.alexandreoliveira.apps.tictactoe.usecases.TicTacToeService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("gamePlayTicTacToeService")
public class GameTicTacToePlayService implements TicTacToeService<GameTicTacToePlayInputDto, GameTicTacToePlayOutputDto> {

  private final GameTicTacToePlayDAO dao;
  private Boolean isFinish = false;
  private String winner = null;

  public GameTicTacToePlayService(
    @Qualifier("gameTicTacToePlayDAOImpl")
    GameTicTacToePlayDAO dao
  ) {
    this.dao = dao;
  }

  @Override
  public GameTicTacToePlayOutputDto execute(GameTicTacToePlayInputDto input) {
    dao.isValid(input);

    String[][] board = new String[3][3];

    List<MovementEntity> movements = dao.findMovementsByGameId(input.getGameId());

    isFinish = movements.size() == 9;

    movements.forEach(m -> {
      isValidPosition(
        m.getX(),
        m.getY(),
        m.getPlayer()
      );
      board[m.getX()][m.getY()] = m.getPlayer();
    });

    checkRows(board);
    checkColumns(board);
    checkDiagonals(board);

    return new GameTicTacToePlayOutputDto(
      isFinish,
      winner
    );
  }

  private void isValidPosition(
    Integer x,
    Integer y,
    String player
  ) {
    final String errorMessage = String.format(
      "Posição inválida para o movimento do player '%s' nas posições x=%d, y=%d",
      player,
      x,
      y
    );

    if (x < 0 || x > 2) {
      throw new GameTicTacToePlayException(errorMessage);
    }

    if (y < 0 || y > 2) {
      throw new GameTicTacToePlayException(errorMessage);
    }
  }

  private void checkRows(String[][] board) {
    for (int i = 0; i < 3; i++) {
      if (checkIfIsEqual(
        board[i][0],
        board[i][1],
        board[i][2]
      )) {
        winner = board[i][0];
        isFinish = true;
      }
    }
  }

  private void checkColumns(String[][] board) {
    for (int i = 0; i < 3; i++) {
      if (checkIfIsEqual(
        board[0][i],
        board[1][i],
        board[2][i]
      )) {
        winner = board[0][i];
        isFinish = true;
      }
    }
  }

  private void checkDiagonals(String[][] board) {
    if (checkIfIsEqual(
      board[0][0],
      board[1][1],
      board[2][2]
    )) {
      winner = board[0][0];
      isFinish = true;
      return;
    }

    if (checkIfIsEqual(
      board[0][2],
      board[1][1],
      board[2][0]
    )) {
      winner = board[0][2];
      isFinish = true;
    }
  }

  private Boolean checkIfIsEqual(
    String c1,
    String c2,
    String c3
  ) {
    return ((c1 != null) && (c1.equals(c2)) && (c2.equals(c3)));
  }
}
