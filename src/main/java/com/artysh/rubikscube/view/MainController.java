package com.artysh.rubikscube.view;

import com.artysh.rubikscube.controller.KubeController;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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
        ModelAndView model = new ModelAndView("redirect:/game/{id}");
        UUID gameId = controller.initKube(cubeSize.longValue());
        model.addObject("id", gameId.toString());
        return model;
    }

    @RequestMapping(value = "/game/{id}", method = RequestMethod.GET)
    public ModelAndView startGame(@PathVariable(value = "id") String gameId) {
        ModelAndView modelAndView = new ModelAndView("index");
        return modelAndView;
    }

}

