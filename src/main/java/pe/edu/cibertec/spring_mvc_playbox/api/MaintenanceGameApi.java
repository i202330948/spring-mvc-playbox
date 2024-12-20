package pe.edu.cibertec.spring_mvc_playbox.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.edu.cibertec.spring_mvc_playbox.dto.GameCreateDto;
import pe.edu.cibertec.spring_mvc_playbox.dto.GameDetailDto;
import pe.edu.cibertec.spring_mvc_playbox.dto.GameDto;
import pe.edu.cibertec.spring_mvc_playbox.response.*;
import pe.edu.cibertec.spring_mvc_playbox.service.MaintenanceService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/maintenance-game")
public class MaintenanceGameApi {

    @Autowired
    MaintenanceService maintenanceService;

    @GetMapping("/all")
    public FindGamesResponse findGames() {

        try {
            List<GameDto> games = maintenanceService.findAllGames();
            if (!games.isEmpty())
                return new FindGamesResponse("01", null, games);
            else
                return new FindGamesResponse("02", "Games not found", null);

        } catch (Exception e) {
            e.printStackTrace();
            return new FindGamesResponse("99", "An error ocurred, please try again", null);

        }

    }

    @GetMapping("/detail")
    public FindGameResponse findGame(@RequestParam(value = "id", defaultValue = "0") String id) {

        try {
            Optional<GameDetailDto> optional = maintenanceService.getGameById((Integer.parseInt(id)));
            return optional.map(game ->
                    new FindGameResponse("01", null, game)
            ).orElse(
                    new FindGameResponse("02", "Game not found", null)
            );

        } catch (Exception e) {
            e.printStackTrace();
            return new FindGameResponse("99", "An error ocurred, please try again", null);

        }

    }

    @PutMapping("/update")
    public UpdateGameResponse updateGame(@RequestBody GameCreateDto gameCreateDto) {

        try {
            if (maintenanceService.updateGame(gameCreateDto))
                return new UpdateGameResponse("01", null);
            else
                return new UpdateGameResponse("02", "Game not found");

        } catch (Exception e) {
            e.printStackTrace();
            return new UpdateGameResponse("99", "An error ocurred, please try again");

        }

    }

    @DeleteMapping("/delete/{id}")
    public DeleteGameResponse deleteGame(@PathVariable String id) {

        try {
            if (maintenanceService.deleteGameById(Integer.parseInt(id)))
                return new DeleteGameResponse("01", null);
            else
                return new DeleteGameResponse("02", "Game not found");

        } catch (Exception e) {
            e.printStackTrace();
            return new DeleteGameResponse("99", "An error ocurred, please try again");

        }

    }

    @PostMapping("/create")
    public CreateGameResponse createGame(@RequestBody GameCreateDto gameCreateDto) {

        try {
            if (maintenanceService.addGame(gameCreateDto))
                return new CreateGameResponse("01", null);
            else
                return new CreateGameResponse("02", "Game already exists");

        } catch (Exception e) {
            e.printStackTrace();
            return new CreateGameResponse("99", "An error ocurred, please try again");

        }

    }

}
