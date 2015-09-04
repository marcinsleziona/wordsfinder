package pl.ms.wordsfinder.service;

import java.util.List;

/**
 * Created by Marcin on 2015-09-02.
 */
public interface IWordsService {

    /**
     * check whether the word is valid
     * @param word
     * @return
     */
    boolean isValid(String word);

    /**
     * pattern -> te?t, gives: i.e. test, tent, ...
     *
     * @param pattern - can contain '?'
     * @return - list of wordsfinder in alphabetical order
     */
    List<String> findMatchingWords(String pattern);

    /**
     * finds all possible wordsfinder that contain all the provide letters.
     * letters -> a?k, returns: rak, kra, ...
     *
     * @param letters - can contain '?'
     * @return
     */
    List<String> findPossibleWords(String letters);

    /**
     * find all possible wordsfinder that can be build with the letters from 2-digits up to amount of letters
     * letters -> a?k, returns: aa, as, ka, kra, rak, ...
     *
     * @param letters - can contain '?'
     * @return
     */
    List<String> findAllPossibleWords(String letters);
}
