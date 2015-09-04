package pl.ms.wordsfinder;

import java.util.*;

/**
 * Created by Marcin on 2015-09-02.
 */
public class Dictonary {

    private HashMap<Character, HashMap<Integer, HashSet<String>>> words = new HashMap<>();

    // Create a key that is the word's letters sorted alphabetically (and forced to one case)
    // Add the word to the list of wordsfinder accessed by the hash key in H
    private HashMap<Integer, HashMap<String, List<String>>> wordKeys = new HashMap<>();

    private Set<Character> letters = new HashSet<>();

    public void add(String word) {
        Character c = word.charAt(0);
        Integer length = word.length();

        HashMap<Integer, HashSet<String>> lwords = words.get(c);
        if (lwords == null) {
            lwords = new HashMap<>();
        }
        HashSet<String> lcwords = lwords.get(length);
        if (lcwords == null) {
            lcwords = new HashSet<>();
        }
        lcwords.add(word);
        lwords.put(length, lcwords);
        words.put(c, lwords);

        // add wordsfinder for anagrams
        HashMap<String, List<String>> lwkwords = wordKeys.get(length);
        if (lwkwords == null) {
            lwkwords = new HashMap<>();
        }
        String key = toKey(word);
        List<String> klwkwords = lwkwords.get(key);
        if (klwkwords == null) {
            klwkwords = new ArrayList<>();
        }
        klwkwords.add(word);
        lwkwords.put(key, klwkwords);
        wordKeys.put(length, lwkwords);

        // add all letters
        for(char ch : word.toCharArray()) {
            letters.add(ch);
        }
    }

    private String toKey(String word) {
        char[] ctab = word.toCharArray();
        Arrays.sort(ctab);
        return String.valueOf(ctab);
    }

//    /**
//     * ostrzeganych - 37
//     */
//    private HashSet<String> getByPrefix1(String prefix) {
//        HashMap<Integer, HashSet<String>> lwords = wordsfinder.get(prefix.charAt(0));
//
//        return lwords
//                .entrySet()
//                .stream()
//                .filter(e -> e.getKey() >= prefix.length())
//                .map(Map.Entry::getValue)
//                .flatMap(Set::stream).collect(Collectors.toCollection(HashSet::new))
//                .stream()
//                .filter(e -> e.startsWith(prefix))
//                .collect(Collectors.toCollection(HashSet::new));
//    }

//    /**
//     * ostrzeganych - 28
//     */
//    private HashSet<String> getByPrefix2(String prefix) {
//        HashMap<Integer, HashSet<String>> lwords = wordsfinder.get(prefix.charAt(0));
//
//        HashSet<String> wordsfinder = lwords.entrySet().stream().filter(e -> e.getKey() >= prefix.length()).map(Map.Entry::getValue).flatMap(Set::stream).collect(Collectors.toCollection(HashSet::new));
//        return wordsfinder.stream().filter(e -> e.startsWith(prefix)).collect(Collectors.toCollection(HashSet::new));
//    }

    /**
     * ostrzeganych - 10
     */
    public HashSet<String> getByPrefix3(String prefix) {
        HashSet<String> ret = new HashSet<>();
        HashMap<Integer, HashSet<String>> lwords = words.get(prefix.charAt(0));
        if(lwords == null) {
            return ret;
        }
        for (Map.Entry<Integer, HashSet<String>> entry : lwords.entrySet()) {
            if (entry.getKey() < prefix.length()) {
                continue;
            }
            entry.getValue().stream().filter(e -> e.startsWith(prefix)).forEachOrdered(ret::add);
        }
        return ret;
    }

//    /**
//     * ostrzeganych - 11
//     */
//    private HashSet<String> getByPrefix4(String prefix) {
//        HashSet<String> ret = new HashSet<>();
//        HashMap<Integer, HashSet<String>> lwords = wordsfinder.get(prefix.charAt(0));
//        for (Map.Entry<Integer, HashSet<String>> entry : lwords.entrySet()) {
//            if (entry.getKey() < prefix.length()) {
//                continue;
//            }
//            ret.addAll(entry.getValue().stream().filter(str -> str.startsWith(prefix)).collect(Collectors.toList()));
//        }
//        return ret;
//    }


    public HashSet<String> getWords(Character c, Integer length) {
        HashSet<String> lengthliners = new HashSet<>();
        if (c == '?') {
            for (HashMap<Integer, HashSet<String>> entry : words.values()) {
                HashSet<String> hs = entry.get(length);
                if (hs != null) {
                    lengthliners.addAll(hs);
                }
            }
        } else {
            HashMap<Integer, HashSet<String>> lwords = words.get(c);
            HashSet<String> hs = lwords.get(length);
            if (hs != null) {
                lengthliners.addAll(hs);
            }
        }
        return lengthliners;
    }

    public HashSet<String> getWordsByKey(String key) {
        HashSet<String> res = new HashSet<>();
        HashMap<String, List<String>> lkwords = wordKeys.get(key.length());
        if(lkwords == null) {
            return res;
        }
        List<String> klkwords = lkwords.get(key);
        if(klkwords == null) {
            return res;
        }
        res.addAll(klkwords);
        return res;
    }

    public Collection<Character> getLetters() {
        return Collections.unmodifiableCollection(letters);
    }
}
