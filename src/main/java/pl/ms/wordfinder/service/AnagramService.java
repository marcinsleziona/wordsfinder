package pl.ms.wordfinder.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Marcin on 2015-09-02.
 */
public class AnagramService {

    public Set<String> findAnagrams(String inputWord) {
        Set<String> ret = new HashSet<>();
        findAnagrams(ret, "", inputWord);
        return ret;
    }

    private void findAnagrams(Set<String> ret, String prefix, String str) {
        int n = str.length();
        if (n == 0) {
            ret.add(prefix);
        } else {
            for (int i = 0; i < n; i++)
                findAnagrams(ret, prefix + str.charAt(i), str.substring(0, i) + str.substring(i + 1, n));
        }
    }

    public Set<String> findCombinations(String inputWord) {
        Set<String> ret = new HashSet<>();
        findCombinations(ret, "", inputWord);
        return ret;
    }

    private void findCombinations(Set<String> ret, String prefix, String str) {
        int n = str.length();
        if(prefix.length() >= 2) {
            ret.add(toKey(prefix));
        }
        if (n != 0) {
            for (int i = 0; i < n; i++)
                findCombinations(ret, prefix + str.charAt(i), str.substring(0, i) + str.substring(i + 1, n));
        }
    }

    private String toKey(String word) {
        char[] ctab = word.toCharArray();
        Arrays.sort(ctab);
        return String.valueOf(ctab);
    }
}
