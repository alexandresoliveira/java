package dev.alexandreoliveira.apps.tictactoe.usecases.game.tictactoe.movement.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;

@SpringBootTest
public class GameTicTacToeMovementDAOImplTest {

  @Autowired
  GameTicTacToeMovementDAOImpl gameTicTacToeMovementDAO;

  @TestConfiguration
  static class GameTicTacToeMovementDAOTestConfiguration {

  }
}
