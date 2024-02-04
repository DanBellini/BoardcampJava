package com.boardcamp.api.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.boardcamp.api.dtos.GameDto;
import com.boardcamp.api.exceptions.game_errors.GameNameConflictException;
import com.boardcamp.api.models.GameModel;
import com.boardcamp.api.repositories.GameRepository;

@Service
public class GameService {
    final GameRepository gameRepository;

    GameService(GameRepository gameRepository){
        this.gameRepository = gameRepository;
    }

    public List<GameModel> findAll(){
        return gameRepository.findAll();
    }

    public GameModel save (GameDto dto){
        if(gameRepository.existsByName(dto.getName())){
            throw new GameNameConflictException("This game already exist!");
        }

        GameModel game = new GameModel(dto);
        return gameRepository.save(game);
    }
}
