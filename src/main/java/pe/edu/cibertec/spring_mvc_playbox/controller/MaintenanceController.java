package pe.edu.cibertec.spring_mvc_playbox.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pe.edu.cibertec.spring_mvc_playbox.dto.*;

import pe.edu.cibertec.spring_mvc_playbox.entity.*;
import pe.edu.cibertec.spring_mvc_playbox.repository.GenreRepository;
import pe.edu.cibertec.spring_mvc_playbox.repository.ModeRepository;
import pe.edu.cibertec.spring_mvc_playbox.repository.PlatformRepository;
import pe.edu.cibertec.spring_mvc_playbox.service.MaintenanceService;
import pe.edu.cibertec.spring_mvc_playbox.service.OrderService;
import pe.edu.cibertec.spring_mvc_playbox.service.UserService;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Controller
@RequestMapping("/maintenance")
public class MaintenanceController {

    @Autowired
    MaintenanceService maintenanceService;

    @Autowired
    UserService userService;

    @Autowired
    OrderService orderService;

    @Autowired
    PlatformRepository platformRepository;

    @Autowired
    GenreRepository genreRepository;

    @Autowired
    ModeRepository modeRepository;

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/restricted")
    public String restricted(Model model) {
        return "restricted";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("userDto", new UserDto(
                null,
                "",
                "",
                "",
                "",
                "",
                "USER",
                null,
                null
        ));
        return "register";
    }

    @PostMapping("/register-confirm")
    public String registerUser(@ModelAttribute UserDto user, Model model) {
            userService.registerNewUser(user);
            model.addAttribute("userDto", user);
            return "login";
    }

    @GetMapping("/start")
    public String start(Model model) {

        try {
            List<GameDto> games = maintenanceService.findAllGames();
            model.addAttribute("games", games);
            model.addAttribute("error", null);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Ocurrió un error al obtener los datos");

        }
        return "maintenance";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Integer id, Model model) {

        try {
            GameDetailDto gameDetailDto = maintenanceService.findDetailById(id);
            model.addAttribute("game", gameDetailDto);
            model.addAttribute("error", null);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Ocurrió un error al obtener los datos");

        }
        return "maintenance-detail";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {

        try {
            GameCreateDto gameCreateDto = maintenanceService.findDetailEditById(id);
            model.addAttribute("game", gameCreateDto);

            model.addAttribute("platforms", platformRepository.findAll());
            model.addAttribute("genres", genreRepository.findAll());
            model.addAttribute("modes", modeRepository.findAll());
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Ocurrió un error al obtener los datos");

        }
        return "maintenance-edit";
    }

    @PostMapping("/edit-confirm")
    public String edit(@ModelAttribute GameCreateDto game, Model model) {

        try {
            maintenanceService.updateGame(game);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Ocurrió un error al obtener los datos");

        }
        return "redirect:/maintenance/start";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, Model model) {

        try {
            maintenanceService.deleteGameById(id);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Ocurrió un error al obtener los datos");

        }
        return "redirect:/maintenance/start";
    }

    @GetMapping("/create")
    public String create(Model model) {

        try {
            model.addAttribute("gameCreateDto", new GameCreateDto(null, "", "", null, null, null, null, null, null, null));

            Iterable<Platform> platformsIterable = platformRepository.findAll();
            List<Platform> platforms = StreamSupport.stream(platformsIterable.spliterator(), false)
                    .collect(Collectors.toList());
            model.addAttribute("platforms", platforms);

            Iterable<Genre> genresIterable = genreRepository.findAll();
            List<Genre> genres = StreamSupport.stream(genresIterable.spliterator(), false)
                    .collect(Collectors.toList());
            model.addAttribute("genres", genres);

            Iterable<Mode> modesIterable = modeRepository.findAll();
            List<Mode> modes = StreamSupport.stream(modesIterable.spliterator(), false)
                    .collect(Collectors.toList());
            model.addAttribute("modes", modes);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Ocurrió un error al obtener los datos");

        }
        return "maintenance-create";
    }

    @PostMapping("/create-confirm")
    public String create(@ModelAttribute GameCreateDto game, Model model) {

        try {
            maintenanceService.createGame(game);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Ocurrió un error al obtener los datos");

        }
        return "redirect:/maintenance/start";
    }

    @PostMapping("/buy/{gameId}")
    public String buyGame(@PathVariable Integer gameId,
                          Authentication authentication,
                          RedirectAttributes redirectAttributes) {  // Cambiado Model por RedirectAttributes
        try {
            String username = authentication.getName();
            Optional<User> user = userService.findByUsername(username);
            if (user.isPresent()) {
                OrderCreateDto orderCreateDto = new OrderCreateDto(
                        null,
                        user.get().getUserId(),
                        gameId,
                        new Date(),
                        1,
                        null
                );
                boolean result = orderService.createOrder(orderCreateDto);
                if (result) {
                    return "redirect:/maintenance/start?success=true";
                }
            }
            redirectAttributes.addAttribute("error", "true");
            return "redirect:/maintenance/start";
        } catch (Exception e) {
            redirectAttributes.addAttribute("error", "true");
            return "redirect:/maintenance/start";
        }
    }

}
