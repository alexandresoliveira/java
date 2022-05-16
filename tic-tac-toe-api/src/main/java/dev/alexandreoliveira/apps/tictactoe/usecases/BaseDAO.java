package dev.alexandreoliveira.apps.tictactoe.usecases;

public interface BaseDAO<I> {

  void isValid(I input);
}
