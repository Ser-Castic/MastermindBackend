package com.theironyard.controllers;

import com.theironyard.entities.Mastermind;
import com.theironyard.services.MastermindRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Mastermind home() {
        init();
        return games.findOne(1); // index(round), guess(Answer), checks(bogus array), round 1 is a wash
    }

    @CrossOrigin
    @RequestMapping(path = "/guess", method = RequestMethod.POST)
    public Iterable<Mastermind> guessCheck(@RequestBody int [] guess) {//request JSON object in the form of int array
        Mastermind answerObject = games.findOne(1);// sets answer Object to check against the guess
        Mastermind guessObject = new Mastermind();
        int [] response = checkAgainstAnswer(answerObject.getGuesses(), guess);//sets the checks array based on checkAgainstAnswer method
        guessObject.setGuesses(guess);
        guessObject.setChecks(response);
        games.save(guessObject); // saves to repo
        return games.findAll(); // returns all instances
    }

    @CrossOrigin
    @RequestMapping(path = "/new-game", method = RequestMethod.GET)
    public String newGame() {
        games.deleteAll();
        return "redirect:/";
    }

}
