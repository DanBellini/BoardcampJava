package com.boardcamp.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.boardcamp.api.dtos.GameDto;
import com.boardcamp.api.models.GameModel;
import com.boardcamp.api.repositories.GameRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class GameIntegrationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private GameRepository gameRepository;

	@AfterEach
	void cleanUpDatabase(){
		gameRepository.deleteAll();
	}

	private final String GAME_ENDPOINT = "/games";

	@SuppressWarnings("null")
	@Test
	void givenRepositoryIsEmpty_whenGetRequestFindAll_thenReturnArrayEmpty() {
		//given

		//when
		ResponseEntity<List<GameModel>> response = restTemplate.exchange(
			GAME_ENDPOINT,
			HttpMethod.GET,
			null,
			new ParameterizedTypeReference<List<GameModel>>() {}
			);

		//then
		assertTrue(response.getBody().isEmpty());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(0, gameRepository.count());
	}

	@SuppressWarnings("null")
	@Test
	void givenRepositoryIsNotEmpty_whenGetRequestFindAll_thenReturnArrayGameModel(){
		//given
		GameModel game1 = createAndSaveGame("xadrez", "image.com", 3, 1000);
		GameModel game2 = createAndSaveGame("Dama", "image2.com", 5, 600);
		GameModel game3 = createAndSaveGame("Monopoly", "image3.com", 2, 2000);

		//when
		ResponseEntity<List<GameModel>> response = restTemplate.exchange(
			GAME_ENDPOINT,
			HttpMethod.GET,
			null,
			new ParameterizedTypeReference<List<GameModel>>() {}
		);		 
		
		//then
		List<GameModel> games = response.getBody();

		assertEquals(3, response.getBody().size());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(3, gameRepository.count());

		assertTrue(games.contains(game1));
		assertTrue(games.contains(game2));
		assertTrue(games.contains(game3));
	}

	@Test
	void givenValidGame_whenCreatingGame_thenCreateGame(){
		//given
		GameDto game = new GameDto("xadrez", "image.test", 3, 1000);
		HttpEntity<GameDto> body = new HttpEntity<>(game);

		//when
		ResponseEntity<GameModel> response = restTemplate.exchange(
			GAME_ENDPOINT, 
			HttpMethod.POST,
			body,
			GameModel.class);

		//then
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(1, gameRepository.count());
	}

	@Test
	void givenRepeatedGame_whenCreatingGame_thenThrowsErrorConflict(){
		//given

		createAndSaveGame("xadrez", "image2.test", 2, 1200);

		GameDto game = new GameDto("xadrez", "image.test", 1, 1000);
		HttpEntity<GameDto> body = new HttpEntity<>(game);
		//when
		ResponseEntity<String> response = restTemplate.exchange(
			GAME_ENDPOINT,
			HttpMethod.POST,
			body,
			String.class
			);

		//then
		assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
		assertEquals(1, gameRepository.count());
	}

	@Test
	void givenInvalidGameName_whenCreatingGame_thenThrowsErrorBadRequest(){
		//given
		GameDto game = new GameDto("", "image.test", 1, 1000);
		HttpEntity<GameDto> body = new HttpEntity<>(game);

		//when
		ResponseEntity<String> response = restTemplate.exchange(
			GAME_ENDPOINT,
			HttpMethod.POST,
			body,
			String.class
			);

		//then
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals(0, gameRepository.count());
	};

	@Test
	void givenInvalidStockTotal_whenCreatingGame_thenThrowsErrorBadRequest(){
		//given
		GameDto game = new GameDto("xadrez", "image.test", 0, 1000);
		HttpEntity<GameDto> body = new HttpEntity<>(game);

		//when
		ResponseEntity<String> response = restTemplate.exchange(
			GAME_ENDPOINT,
			HttpMethod.POST,
			body,
			String.class
			);

		//then
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals(0, gameRepository.count());
	} 

	@Test
	void givenInvalidPricePerDay_whenCreatingGame_thenThrowsErrorBadRequest(){
		//given
		GameDto game = new GameDto("xadrez", "image.test", 3, 0);
		HttpEntity<GameDto> body = new HttpEntity<>(game);

		//when
		ResponseEntity<String> response = restTemplate.exchange(
			GAME_ENDPOINT,
			HttpMethod.POST,
			body,
			String.class
			);

		//then
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals(0, gameRepository.count());
	} 


	//helper methods

	private GameModel createAndSaveGame (String name, String image, int stockTotal, int pricePerDay){
		GameModel game = new GameModel(null, name, image, stockTotal, pricePerDay);
		return gameRepository.save(game);
	};
}
