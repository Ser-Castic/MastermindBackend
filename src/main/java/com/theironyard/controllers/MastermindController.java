package com.theironyard.controllers;

import com.theironyard.entities.Mastermind;
import com.theironyard.services.MastermindRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
//@RestController
@Controller
public class MastermindController {

    @Autowired
    MastermindRepository games;

//    @PostConstruct
//    public void init() {
//        if(games.count() == 0) { //If this is either the start of the game or a new game, this is run to create the answer
//            Mastermind master = new Mastermind();
//            master.setRound(0);
//            master.setGuesses(new int[]{numberGenerator(), numberGenerator(), numberGenerator(), numberGenerator()}); //creates the answer
//        }
//    }

    public static int numberGenerator() { // gets a number between 1 - 8 randomly
        return ((int)Math.random() * 8) + 1;
    }

//    public static int[] guessChecker() {
//
//
//    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(Model model) {
        model.addAttribute(games.findAll());
        return "home.html";
    }


}
