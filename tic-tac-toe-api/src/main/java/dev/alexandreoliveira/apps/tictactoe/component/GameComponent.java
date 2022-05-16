package dev.alexandreoliveira.apps.tictactoe.component;

import java.util.List;

import org.springframework.stereotype.Component;

import dev.alexandreoliveira.apps.tictactoe.database.entity.MovementEntity;

@Component
public class GameComponent {
	
	private Boolean isFinish = false;
	private String winner = null;

	public void checkBoard(List<MovementEntity> movements) {
		
		isFinish = false;
		winner = null;

		String[][] board = new String[3][3];

		isFinish = movements.size() == 9;

		movements.stream().forEach(m -> {
			board[m.getX()][m.getY()] = m.getPlayer();
		});

		checkRows(board);
		checkColumns(board);
		checkDiagonals(board);
	}

	private void checkRows(String[][] board) {
		for (int i = 0; i < 3; i++) {
			if (checkIfIsEqual(board[i][0], board[i][1], board[i][2])) {
				winner = board[i][0];
				isFinish = true;
			}
		}
	}

	private void checkColumns(String[][] board) {
		for (int i = 0; i < 3; i++) {
			if (checkIfIsEqual(board[0][i], board[1][i], board[2][i])) {
				winner = board[0][i];
				isFinish = true;
			}
		}
	}

	private void checkDiagonals(String[][] board) {
		if (checkIfIsEqual(board[0][0], board[1][1], board[2][2])) {
			winner = board[0][0];
			isFinish = true;
		} else if (checkIfIsEqual(board[0][2], board[1][1], board[2][0])) {
			winner = board[0][2];
			isFinish = true;
		}
	}

	private Boolean checkIfIsEqual(String c1, String c2, String c3) {
		return ((c1 != null) && (c1.equals(c2)) && (c2.equals(c3)));
	}

	public Boolean getIsFinish() {
		return isFinish;
	}

	public String getWinner() {
		return winner;
	}
}
