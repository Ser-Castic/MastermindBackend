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

//    @PostConstruct
//    public void init() {
//        if(games.count() == 0) { //If this is either the start of the game or a new game, this is run to create the answer
//            Mastermind master = new Mastermind();
//            master.setRound(0);
//            master.setGuesses(new int[]{numberGenerator(), numberGenerator(), numberGenerator(), numberGenerator()}); //creates the answer
//            games.save(master);
//        }
//    }

    public static int numberGenerator() { // gets a number between 1 - 8 randomly
        return ((int)Math.random() * 8) + 1;
    }

    //visualization
    //{1, 2, 4, 5} answer
    //{1, 4, 6, 7} guess
//    public int[] checkAgainstAnswer(int[] guessArray) { // this checks
//        Mastermind answerMaster = games.findOne(0); // sets first position in repo to this object
//        int [] answerArray = answerMaster.getGuesses();// gets the guess array field and sets it to this variable
//        int [] result = new int[guessArray.length]; // created result array to be returned
//        boolean areEqual = Arrays.equals(answerArray, guessArray);
//
//        if(areEqual) {
//            for (int i = 0; i < guessArray.length; i++) {
//                result[i] = 2;
//            }
//            return result;
//        }


//        for (int i = 0; i < answerArray.length; i++) { // loop through both arrays comparing
//            for (int x = 0; x < guessArray.length; x++) {
//                if (answerArray[i] == guessArray[x] && flag == true) {
//                    result[i] = 2;
//                } else if ()
//            }
//        }

//    }

    @CrossOrigin
    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home() {
        return "Pants";
    }


}
