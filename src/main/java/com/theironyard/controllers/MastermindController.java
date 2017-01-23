package com.theironyard.controllers;

import com.theironyard.entities.Mastermind;
import com.theironyard.services.MastermindRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@RestController
//@Controller
public class MastermindController {

    @Autowired
    MastermindRepository games;

    @PostConstruct
    public void init() {
        if(games.count() == 0) { //If this is either the start of the game or a new game, this is run to create the answer
            Mastermind master = new Mastermind();
            master.setRound(0);
            master.setGuesses(new int[]{numberGenerator(), numberGenerator(), numberGenerator(), numberGenerator()});//creates the answer
            master.setChecks(new int[]{0,0,0,0});
            games.save(master);
        }
    }

    public static int numberGenerator() { // gets a number between 1 - 8 randomly
        return (int)(Math.random() * 8) + 1;
    }

    public static int[] checkAgainstAnswer(int[] answerArray, int[] guessArray) { // this checks
        answerArray = Arrays.copyOf(answerArray, answerArray.length);

        int[] result = new int[answerArray.length];

        for (int i = 0; i < result.length; i++) {
            if (answerArray[i] == guessArray[i]) {
                result[i] = 2;
                answerArray[i] = 0;
                continue;
            }

            int answerIndex = Arrays.binarySearch(answerArray, guessArray[i]);

            if(answerIndex > -1) {
                result[i] = 1;
                answerArray[answerIndex] = 0;
            }
        }
        return result;
    }

    @CrossOrigin
    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home() {
        return "Pants";
    }
}
