package pl.ms.wordsfinder.service;

import pl.ms.wordsfinder.Dictonary;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Marcin on 2015-09-02.
 */
public class WordsService implements IWordsService {

    private static final int NUMBER_OF_THREADS = 3;

    private Dictonary dictonary;
    private AnagramService anagramService;

    public WordsService(Dictonary dictonary, AnagramService anagramService) {
        this.dictonary = dictonary;
        this.anagramService = anagramService;
    }

    @Override
    public boolean isValid(String word) {
        return !(word == null || word.trim().length() <= 1) && !dictonary.getByPrefix3(word).isEmpty();
    }

    @Override
    public List<String> findMatchingWords(String tofind) {
        if (tofind == null || tofind.trim().length() <= 1) {
            return new ArrayList<>();
        }
        Character c = tofind.charAt(0);
        HashSet<String> lengthliners = dictonary.getWords(c, tofind.length());
        Set<String> res = lengthliners.stream().filter(p -> checkWord(p, tofind)).collect(Collectors.toSet());
        List<String> lres = new ArrayList<>(res);
        Collections.sort(lres);
        return lres;
    }

    private boolean checkWord(String word, String tofind) {
        for (int idx = 0; idx < tofind.length(); idx++) {
            if (tofind.charAt(idx) == '?') {
                continue;
            }
            if (tofind.charAt(idx) == word.charAt(idx)) {
                continue;
            }
            return false;
        }
        return true;
    }

//    @Override
//    private List<String> findPossibleWords(String letters) {
//        List<String> anagrams = new ArrayList(anagramService.findAnagrams(letters));
//
//        // init workers
//        WordsWorker[] wws = new WordsWorker[NUMBER_OF_THREADS];
//        for (int idx = 0; idx < wws.length; idx++) {
//            wws[idx] = new WordsWorker(this);
//        }
//
//        // submit word over the workers
//        for (int idx = 0; idx < anagrams.size(); idx++) {
//            wws[idx % NUMBER_OF_THREADS].addWord(anagrams.get(idx));
//        }
//
//        //Stream.of(wws).map(WordsWorker::getWords).collect(Collectors.toList()).forEach(System.out::println);
//
//        // run and get all results
//        ExecutorService executor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
//
//        // start
//        List<Future<HashSet<String>>> results = new ArrayList<>();
//        for (WordsWorker worker : wws) {
//            Future<HashSet<String>> future = executor.submit(worker);
//            results.add(future);
//        }
//
//        // collect reports
//        List<String> wordsfinder = new ArrayList<>();
//        for (Future<HashSet<String>> result : results) {
//            HashSet<String> workReport;
//            try {
//                workReport = result.get();
//            } catch (InterruptedException e) {
//                continue;
//            } catch (ExecutionException e) {
//                continue;
//            }
//
//            wordsfinder.addAll(workReport);
//        }
//
//        Set<String> res = wordsfinder.stream().sorted().collect(Collectors.toSet());
//        List<String> lres = new ArrayList<>(res);
//        Collections.sort(lres);
//        return lres;
//    }

    @Override
    public List<String> findPossibleWords(String letters) {
        if (letters == null || letters.trim().length() <= 1) {
            return new ArrayList<>();
        }
        Set<String> res = new HashSet<>();
        Set<String> keys = buildKeys(letters);

        //TODO - run getWordsByKey in parallel threads
        keys.stream().forEach(p -> dictonary.getWordsByKey(p).stream().forEachOrdered(res::add));
        List<String> lres = new ArrayList<>(res);
        Collections.sort(lres);
        return lres;
    }

    @Override
    public List<String> findAllPossibleWords(String letters) {
        if (letters == null || letters.trim().length() <= 1) {
            return new ArrayList<>();
        }
        Set<String> res = new HashSet<>();
        Set<String> keys = new HashSet<>();
        anagramService.findCombinations(letters).stream().forEach(p -> buildKeys(p).stream().forEachOrdered(keys::add));

        //TODO - run getWordsByKey in parallel threads
        keys.stream().forEach(p -> dictonary.getWordsByKey(p).stream().forEachOrdered(res::add));
        List<String> lres = new ArrayList<>(res);
        Collections.sort(lres);
        return lres;
    }

    /**
     * replace '?' with every character and then build keys
     */
    private Set<String> buildKeys(String word) {
        int length = word.length();
        Set<String> words = new HashSet<>();
        words.add(word);

        for (int idx = 0; idx < length; idx++) {
            Set<String> words_ = new HashSet<>();
            for (String word_ : words) {
                if (word_.charAt(idx) == '?') {
                    for (Character c : dictonary.getLetters()) {
                        char[] chartab = word_.toCharArray();
                        chartab[idx] = c;
                        words_.add(String.valueOf(chartab));
                    }
                } else {
                    words_.add(word_);
                }
            }
            words = words_;
        }

        return words.stream().map(this::toKey).collect(Collectors.toSet());
    }

    private String toKey(String word) {
        char[] ctab = word.toCharArray();
        Arrays.sort(ctab);
        return String.valueOf(ctab);
    }
}
