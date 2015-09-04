package pl.ms.wordsfinder;

import pl.ms.wordsfinder.service.AnagramService;
import pl.ms.wordsfinder.service.IWordsService;
import pl.ms.wordsfinder.service.WordsService;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

/**
 * Created by Marcin on 2015-09-03.
 */
public class Start {

        private Dictonary dictonary;

        public static void main(String[] args) throws IOException, URISyntaxException {
            Start w = new Start();
            w.dictonary = w.loadFile(App.class.getResource("/polish-win1250.txt").toURI());

            AnagramService as = new AnagramService();
            IWordsService ws = new WordsService(w.dictonary, as);

            String word;

            word = "okna";
            long time0 = System.currentTimeMillis();
            System.out.println(word + " "+ws.isValid(word));
            System.out.println(System.currentTimeMillis() - time0);

            word = "okr?";
            long time1 = System.currentTimeMillis();
            System.out.println(word + " "+ws.findMatchingWords(word));
            System.out.println(System.currentTimeMillis() - time1);

            word = "t?la";
            long time2 = System.currentTimeMillis();
            System.out.println(word + " "+ws.findPossibleWords(word).stream().collect(Collectors.groupingBy(String::length)));
            System.out.println(System.currentTimeMillis() - time2);

            word = "r??k";
            long time3 = System.currentTimeMillis();
            System.out.println(word + " "+ws.findAllPossibleWords(word).stream().collect(Collectors.groupingBy(String::length)));
            System.out.println(System.currentTimeMillis() - time3);

            System.exit(0);
        }

        private Dictonary loadFile(URI spath) throws IOException {
            Dictonary wc = new Dictonary();
            Files.readAllLines(Paths.get(spath), Charset.forName("windows-1250")).forEach(wc::add);
            return wc;
        }


}