package pl.ms.wordsfinder.service;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;

/**
 * Created by Marcin on 2015-09-02.
 */
public class WordsWorker implements Callable<HashSet<String>> {

    private IWordsService wordsService;
    private Set<String> words;

    public WordsWorker(IWordsService wordsService) {
        this.wordsService = wordsService;
        this.words = new HashSet<>();
    }

    public void addWord(String word) {
        words.add(word);
    }

    public Set<String> getWords() {
        return words;
    }

    public HashSet<String> call() throws Exception {
        HashSet<String> ret = new HashSet<>();
        words.stream().map(wordsService::findMatchingWords).forEachOrdered(ret::addAll);
        return ret;
    }

}
