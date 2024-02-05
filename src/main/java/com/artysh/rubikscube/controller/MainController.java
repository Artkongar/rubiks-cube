package com.artysh.rubikscube.controller;

import com.artysh.rubikscube.dto.CubeSidesDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final KubeController controller;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "create";
    }

    @RequestMapping(value = "/startGame", method = RequestMethod.POST)
    public ModelAndView initGame(@RequestParam(value = "cube-size") Integer cubeSize) {
        ModelAndView model = new ModelAndView("redirect:/game");
        UUID gameId = controller.initKube(cubeSize.longValue());
        model.addObject("id", gameId.toString());
        return model;
    }

    @RequestMapping(value = "/game", method = RequestMethod.GET)
    public ModelAndView startGame(@RequestParam(value = "id") String gameId) {
        ModelAndView modelAndView = new ModelAndView("index");
        CubeSidesDto cubeSidesDto = controller.getKubeSides(UUID.fromString(gameId));
        modelAndView.addObject("sides", cubeSidesDto.getSides());
        return modelAndView;
    }

}

