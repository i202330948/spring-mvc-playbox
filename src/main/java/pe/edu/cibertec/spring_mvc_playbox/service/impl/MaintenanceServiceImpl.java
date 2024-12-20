package pe.edu.cibertec.spring_mvc_playbox.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.cibertec.spring_mvc_playbox.dto.*;
import pe.edu.cibertec.spring_mvc_playbox.entity.Game;
import pe.edu.cibertec.spring_mvc_playbox.repository.*;
import pe.edu.cibertec.spring_mvc_playbox.service.MaintenanceService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MaintenanceServiceImpl implements MaintenanceService {

    @Autowired
    GameRepository gameRepository;

    @Autowired
    PlatformRepository platformRepository;

    @Autowired
    GenreRepository genreRepository;

    @Autowired
    ModeRepository modeRepository;


    @Override
    public List<GameDto> findAllGames() throws Exception {

        List<GameDto> games = new ArrayList<GameDto>();
        Iterable<Game> iterable = gameRepository.findAll();
        iterable.forEach(game -> {
            GameDto gameDto = new GameDto(game.getGameId(),
                    game.getTitle(),
                    game.getPlatform().getPlatformName(),
                    game.getGenre().getGenreName(),
                    game.getStock(),
                    game.getPrice(),
                    game.getUrl());
            games.add(gameDto);
        });

        return games;

    }

    @Override
    public Optional<GameDetailDto> getGameById(int id) throws Exception {

        Optional<Game> optional = gameRepository.findById(id);
        return optional.map(game -> new GameDetailDto(game.getGameId(),
                        game.getTitle(),
                        game.getDescription(),
                        game.getReleaseYear(),
                        game.getPlatform().getPlatformName(),
                        game.getGenre().getGenreName(),
                        game.getMode().getModeName(),
                        game.getStock(),
                        game.getPrice(),
                        game.getUrl())
        );

    }

    @Override
    public GameDetailDto findDetailById(int id) throws Exception {

        Optional<Game> optional = gameRepository.findById(id);
        return optional.map(
                game -> new GameDetailDto(game.getGameId(),
                        game.getTitle(),
                        game.getDescription(),
                        game.getReleaseYear(),
                        game.getPlatform().getPlatformName(),
                        game.getGenre().getGenreName(),
                        game.getMode().getModeName(),
                        game.getStock(),
                        game.getPrice(),
                        game.getUrl())
        ).orElse(null);

    }

    @Override
    public GameCreateDto findDetailEditById(int id) throws Exception {

        Optional<Game> optional = gameRepository.findById(id);
        return optional.map(
                game -> new GameCreateDto(game.getGameId(),
                        game.getTitle(),
                        game.getDescription(),
                        game.getReleaseYear(),
                        game.getPlatform().getPlatformId(),
                        game.getGenre().getGenreId(),
                        game.getMode().getModeId(),
                        game.getStock(),
                        game.getPrice(),
                        game.getUrl())
        ).orElse(null);

    }

    @Override
    public boolean updateGame(GameCreateDto gameCreateDto) throws Exception {

        Optional<Game> optional = gameRepository.findById(gameCreateDto.gameId());
        return optional.map(
                game -> {
                    game.setTitle(gameCreateDto.title());
                    game.setDescription(gameCreateDto.description());
                    game.setReleaseYear(gameCreateDto.releaseYear());
                    game.setPlatform(platformRepository.findById(gameCreateDto.platformId()).orElse(null));
                    game.setGenre(genreRepository.findById(gameCreateDto.genreId()).orElse(null));
                    game.setMode(modeRepository.findById(gameCreateDto.modeId()).orElse(null));
                    game.setStock(gameCreateDto.stock());
                    game.setPrice(gameCreateDto.price());
                    game.setUrl(gameCreateDto.url());
                    gameRepository.save(game);
                    return true;
                }
        ).orElse(false);
    }

    @Override
    public boolean deleteGameById(int id) throws Exception {

        Optional<Game> optional = gameRepository.findById(id);
        return optional.map(game -> {
            gameRepository.delete(game);
            return true;
        }).orElse(false);
    }

    @Override
    public boolean addGame(GameCreateDto gameCreateDto) throws Exception {

        Optional<Game> optional = gameRepository.findById(gameCreateDto.gameId());
        if (optional.isEmpty()) {
            Game game = new Game();
            game.setTitle(gameCreateDto.title());
            game.setDescription(gameCreateDto.description());
            game.setReleaseYear(gameCreateDto.releaseYear());
            game.setPlatform(platformRepository.findById(gameCreateDto.platformId()).orElse(null));
            game.setGenre(genreRepository.findById(gameCreateDto.genreId()).orElse(null));
            game.setMode(modeRepository.findById(gameCreateDto.modeId()).orElse(null));
            game.setStock(gameCreateDto.stock());
            game.setPrice(gameCreateDto.price());
            game.setUrl(gameCreateDto.url());
            gameRepository.save(game);
            return true;
        }
        return false;
    }

    @Override
    public boolean createGame(GameCreateDto gameCreateDto) {

        Game game = new Game();
        game.setTitle(gameCreateDto.title());
        game.setDescription(gameCreateDto.description());
        game.setReleaseYear(gameCreateDto.releaseYear());
        game.setPlatform(platformRepository.findById(gameCreateDto.platformId()).orElse(null));
        game.setGenre(genreRepository.findById(gameCreateDto.genreId()).orElse(null));
        game.setMode(modeRepository.findById(gameCreateDto.modeId()).orElse(null));
        game.setStock(gameCreateDto.stock());
        game.setPrice(gameCreateDto.price());
        game.setUrl(gameCreateDto.url());
        gameRepository.save(game);
        return true;
    }

}
