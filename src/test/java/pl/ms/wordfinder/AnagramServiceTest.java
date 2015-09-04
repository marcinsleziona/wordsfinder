package pl.ms.wordfinder;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pl.ms.wordfinder.service.AnagramService;

/**
 * Created by Marcin on 2015-09-02.
 */
public class AnagramServiceTest {

    private AnagramService anagramService;

    @Before
    public void setUp() {
        anagramService = new AnagramService();
    }

    @Test
    public void shouldGenerate3AnagramsFor3Letters() {
        Assert.assertEquals(6, anagramService.findAnagrams("abc").size());
    }

    @Test
    public void shouldGenerate24AnagramsFor4Letters() {
        Assert.assertEquals(24, anagramService.findAnagrams("abcd").size());
    }

    @Test
    public void shouldGenerate100AnagramsFor5Letters() {
        Assert.assertEquals(120, anagramService.findAnagrams("abcde").size());
        anagramService.findAnagrams("abcde").stream().forEach(System.out::println);
    }

    @Test
    public void shouldGenerate4CombinationsFor3Letters() {
        Assert.assertEquals(4, anagramService.findCombinations("abc").size());
    }

    @Test
    public void shouldGenerate4CombinationsFor4Letters() {
        Assert.assertEquals(11, anagramService.findCombinations("abcd").size());
    }

    @Test
    public void shouldGenerate4CombinationsFor5Letters() {
        Assert.assertEquals(26, anagramService.findCombinations("abcde").size());
    }
}
