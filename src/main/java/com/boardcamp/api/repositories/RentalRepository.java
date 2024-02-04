package com.boardcamp.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boardcamp.api.models.GameModel;
import com.boardcamp.api.models.RentalModel;

public interface RentalRepository extends JpaRepository<RentalModel, Long>{
    
    int countByGame(GameModel game);
}
