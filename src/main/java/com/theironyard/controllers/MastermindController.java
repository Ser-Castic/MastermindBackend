package com.theironyard.controllers;

import com.theironyard.entities.Mastermind;
import com.theironyard.services.MastermindRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@RestController
public class MastermindController {

    private boolean reset = true;
    int [] answer = new int[4]; // for storing the current answer

    @Autowired
    MastermindRepository games;

    @PostConstruct
    public void init() {
        if (reset) { //If this is either the start of the game or a new game, this is run to create the answer
            Mastermind master = new Mastermind();
            master.setGuesses(new int[]{numberGenerator(), numberGenerator(), numberGenerator(), numberGenerator()});//creates the answer
            master.setChecks(new int[]{0, 0, 0, 0});
            answer = master.getGuesses(); // sets the current answer
            games.save(master);
            reset = false;
        }
    }

    public static int numberGenerator() { // gets a number between 1 - 8 randomly
        return (int) (Math.random() * 8) + 1;
    }

    public static int[] checkGuess(int[] answer, int[] guess) {
        answer = Arrays.copyOf(answer, answer.length);
        guess = Arrays.copyOf(guess, guess.length);

        int [] results = new int[answer.length];


        for (int i = 0; i < results.length; i ++) {
            if (answer[i] == guess[i]) {
                results[i] = 2;
                answer[i] = 0;
                guess[i] = 0;
            }
        }

        for (int i = 0; i < results.length; i ++) {
            int answerIndex = findIndexOfValue(answer, guess[i]);

            if (answerIndex > -1 && answer[answerIndex] > 0) {
                results[i] = 1;
                answer[answerIndex] = 0;
            }
        }

        return results;
    }

    public static int findIndexOfValue(int [] array, int value) {
        for (int i = 0;i < array.length;i++) {
            if (array[i] == value) {
                return i;
            }
        }

        return -1;
    }

    @CrossOrigin
    @RequestMapping(path = "/", method = RequestMethod.GET)
    public int[] home() {
        init();
        return answer; // index(round), guess(Answer), checks(bogus array), round 1 is a wash
    }

    @CrossOrigin
    @RequestMapping(path = "/guess", method = RequestMethod.POST)
    public Iterable<Mastermind> guessCheck(@RequestBody int[] guess) {//request JSON object in the form of int array
        Mastermind guessObject = new Mastermind();
        int[] response = checkGuess(answer, guess);//sets the checks array based on checkAgainstAnswer method
        guessObject.setGuesses(guess);
        guessObject.setChecks(response);
        games.save(guessObject); // saves to repo
        return games.findAll(); // returns all instances
    }

    @CrossOrigin
    @RequestMapping (path = "/new-game", method = RequestMethod.POST)
    public void newGame (){
        reset = true;
        games.deleteAll();
    }

}
