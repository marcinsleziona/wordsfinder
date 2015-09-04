package pl.ms.wordsfinder.service;

import org.springframework.stereotype.Component;

/**
 * Created by Marcin on 2015-09-03.
 */
@Component
public class WordsServiceFacade {

    private IWordsService wordsService;

    public void setWordsService(IWordsService wordsService) {
        this.wordsService = wordsService;
    }

    public IWordsService getWordsService() {
        return wordsService;
    }
}
