package com.minesweeperAPI.main.java.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.minesweeperAPI.main.java.domain.Game;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Pablo on 19/2/2017.
 */
@RestController
@RequestMapping("/api/v1")
public class GameController {

    private ObjectMapper mapper;
    private List<Game> games;

    GameController() {
        games = new ArrayList<Game>();
        mapper = new ObjectMapper();
    }

    @RequestMapping(value = "/games", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity createNewGame(@RequestParam(value="rows", defaultValue = "0") String rows,
                                  @RequestParam(value="columns", defaultValue = "0") String columns,
                                  @RequestParam(value="mines", defaultValue = "0") String mines) throws JsonProcessingException {
        String jsonResponse;
        if ("0".equals(rows) || "0".equals(columns) || "0".equals(mines)) {
            Game game = new Game();
            games.add(game);
            jsonResponse = mapper.writeValueAsString(game);
            return ResponseEntity.ok(jsonResponse);
        } else {
            Game game = new Game(Integer.parseInt(rows), Integer.parseInt(columns), Integer.parseInt(mines));
            games.add(game);
            jsonResponse = mapper.writeValueAsString(game);
            return ResponseEntity.ok(jsonResponse);
        }
    }

    @RequestMapping(value = "/games/{gameUuid}/flip", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity flipBlock(@PathVariable String gameUuid,
                          @RequestParam(value="row") String row,
                          @RequestParam(value="column") String column) throws JsonProcessingException {

        if ("0".equals(row) || "0".equals(column)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        String jsonResponse;
        Optional<Game> maybeGame = findGame(gameUuid);
        Game game;
        if (maybeGame.isPresent()) {
            game = maybeGame.get();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Integer result = game.flipBlock(Integer.parseInt(row), Integer.parseInt(column));
        FlipBlockResponse response = null;

        if (result == Game.END_OF_GAME_VICTORY_CODE) {
            response = new FlipBlockResponse(game.getUuid(), "Victory", result,
                    Integer.parseInt(row), Integer.parseInt(column));
        }
        if (result == Game.END_OF_GAME_DEFEAT_CODE) {
            response = new FlipBlockResponse(game.getUuid(), "Defeated", result,
                    Integer.parseInt(row), Integer.parseInt(column));
        }
        if (response == null) {
            response = new FlipBlockResponse(game.getUuid(), "Flipped", result,
                    Integer.parseInt(row), Integer.parseInt(column));
        }
        jsonResponse = mapper.writeValueAsString(response);
        return ResponseEntity.ok(jsonResponse);
    }

    private Optional<Game> findGame(String gameUuid) {
        for (Game aGame:games) {
            if (aGame.getUuid().equals(gameUuid)) {
                return Optional.of(aGame);
            }
        }
        return Optional.ofNullable(null);
    }

}
