package pe.edu.cibertec.spring_mvc_playbox.service;

import pe.edu.cibertec.spring_mvc_playbox.dto.*;

import java.util.List;
import java.util.Optional;

public interface MaintenanceService {

    List<GameDto> findAllGames() throws Exception;

    Optional<GameDetailDto> getGameById(int id) throws Exception;

    GameDetailDto findDetailById(int id) throws Exception;

    GameCreateDto findDetailEditById(int id) throws Exception;

    boolean updateGame(GameCreateDto gameCreateDto) throws Exception;

    boolean deleteGameById(int id) throws Exception;

    boolean addGame(GameCreateDto gameCreateDto) throws Exception;

    boolean createGame(GameCreateDto gameCreateDto) throws Exception;

}
