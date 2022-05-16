package dev.alexandreoliveira.apps.tictactoe.usecases;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface TicTacToeService<I, O> {

  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Throwable.class})
  O execute(I input);
}
