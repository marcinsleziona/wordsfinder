package pl.ms.wordsfinder;

import pl.ms.wordsfinder.service.AnagramService;
import pl.ms.wordsfinder.service.IWordsService;
import pl.ms.wordsfinder.service.WordsService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by Marcin on 2015-09-03.
 */
public class Start {

    private Dictonary dictonary;

    public static void main(String[] args) throws IOException, URISyntaxException {
        Start w = new Start();
        w.dictonary = w.loadFile("/sjp-20150906.zip");

        AnagramService as = new AnagramService();
        IWordsService ws = new WordsService(w.dictonary, as);

        String word;

        word = "okna";
        long time0 = System.currentTimeMillis();
        System.out.println(word + " " + ws.isValid(word));
        System.out.println(System.currentTimeMillis() - time0);

        word = "okr?";
        long time1 = System.currentTimeMillis();
        System.out.println(word + " " + ws.findMatchingWords(word));
        System.out.println(System.currentTimeMillis() - time1);

        word = "t?la";
        long time2 = System.currentTimeMillis();
        System.out.println(word + " " + ws.findPossibleWords(word).stream().collect(Collectors.groupingBy(String::length)));
        System.out.println(System.currentTimeMillis() - time2);

        word = "r??k";
        long time3 = System.currentTimeMillis();
        System.out.println(word + " " + ws.findAllPossibleWords(word).stream().collect(Collectors.groupingBy(String::length)));
        System.out.println(System.currentTimeMillis() - time3);

        System.exit(0);
    }

    private Dictonary loadFile(String spath) throws IOException {
        Dictonary wc = new Dictonary();
        try (InputStream resource = App.class.getResourceAsStream(spath)) {
            ZipInputStream stream = new ZipInputStream(resource);
            ZipEntry ze = stream.getNextEntry();
            if(ze.getName().equals("slowa-win.txt")) {
                new BufferedReader(new InputStreamReader(stream, Charset.forName("windows-1250"))).lines().forEach(wc::add);
            }
        }
        return wc;
    }
}