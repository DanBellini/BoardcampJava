package com.boardcamp.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boardcamp.api.dtos.GameDto;
import com.boardcamp.api.models.GameModel;
import com.boardcamp.api.services.GameService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping
public class GameController {
    
    final GameService gameService;

    public GameController(GameService gameService){
        this.gameService = gameService;
    }

    @GetMapping("/games")
    public ResponseEntity<List<GameModel>> getAllGames () {
        List<GameModel> games = gameService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(games);
    }
    

    @PostMapping("/games")
    public ResponseEntity<GameModel> postMethodName(@RequestBody @Valid GameDto body) {
        GameModel game = gameService.save(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(game);
    }
    
}
