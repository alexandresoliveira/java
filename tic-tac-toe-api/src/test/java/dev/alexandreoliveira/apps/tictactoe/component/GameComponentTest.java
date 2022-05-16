package dev.alexandreoliveira.apps.tictactoe.component;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import dev.alexandreoliveira.apps.tictactoe.database.entity.MovementEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@TestMethodOrder(OrderAnnotation.class)
public class GameComponentTest {

	@Test
	@Order(1)
	public void checkBoardWhenZeroMovements() {
		var gameComponent = new GameComponent();
		gameComponent.checkBoard(Collections.emptyList());
		assertThat(gameComponent.getIsFinish()).isFalse();
		assertThat(gameComponent.getWinner()).isNullOrEmpty();
	}

	@ParameterizedTest
	@MethodSource("checkBoardWhenWinner")
	@Order(2)
	public void checkBoardWhenWinner(List<MovementEntity> movements) {
		var gameComponent = new GameComponent();
		gameComponent.checkBoard(movements);
		assertThat(gameComponent.getIsFinish()).isTrue();
		assertThat(gameComponent.getWinner()).isNotEmpty();
	}

	static Stream<Arguments> checkBoardWhenWinner() {
		// @formatter:off
		List<MovementEntity> finish1 = 
			Arrays.asList(
				new MovementEntity(null, "X", 0, 0), 
				new MovementEntity(null, "X", 1, 0), 
				new MovementEntity(null, "X", 2, 0)
			);
		
		List<MovementEntity> finish2 =
			Arrays.asList(
				new MovementEntity(null, "O", 1, 2), 
				new MovementEntity(null, "O", 1, 1), 
				new MovementEntity(null, "O", 1, 0)
			);
		
		List<MovementEntity> finish3 = 
			Arrays.asList(
				new MovementEntity(null, "X", 2, 2), 
				new MovementEntity(null, "X", 1, 1), 
				new MovementEntity(null, "X", 0, 0)
			);
		
		List<MovementEntity> finish4 = 
			Arrays.asList(
				new MovementEntity(null, "O", 0, 2), 
				new MovementEntity(null, "O", 1, 1), 
				new MovementEntity(null, "O", 2, 0)
			);
		
	    return 
	    	Stream.of(
	    		Arguments.of(finish1),
	    		Arguments.of(finish2),
	    		Arguments.of(finish3),
	    		Arguments.of(finish4)
	    	);
	    // @formatter:on
	}

	@Test
	@Order(3)
	public void checkBoardDrawGame() {
		// @formatter:off
		List<MovementEntity> drawBoard = 
			Arrays.asList(
				new MovementEntity(null, "X", 0, 0), 
				new MovementEntity(null, "X", 0, 1), 
				new MovementEntity(null, "O", 0, 2),
				
				new MovementEntity(null, "O", 1, 0), 
				new MovementEntity(null, "O", 1, 1), 
				new MovementEntity(null, "X", 1, 2),
				
				new MovementEntity(null, "X", 2, 0), 
				new MovementEntity(null, "X", 2, 1), 
				new MovementEntity(null, "O", 2, 2)
			);
		// @formatter:on

		var gameComponent = new GameComponent();
		gameComponent.checkBoard(drawBoard);
		assertThat(gameComponent.getIsFinish()).isTrue();
		assertThat(gameComponent.getWinner()).isNullOrEmpty();
	}
}
