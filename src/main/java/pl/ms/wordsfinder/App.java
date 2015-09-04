package pl.ms.wordsfinder;

import javafx.scene.Scene;
import javafx.stage.Stage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.ms.wordsfinder.service.AnagramService;
import pl.ms.wordsfinder.service.WordsService;
import pl.ms.wordsfinder.service.WordsServiceFacade;
import pl.ms.wordsfinder.view.MainView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * Created by Marcin on 2015-09-02.
 */
@SpringBootApplication
public class App extends AbstractJavaFxApplicationSupport {

    /**
     * Note that this is configured in application.properties
     */
    @Value("${app.ui.title}")
    private String windowTitle;

    @Autowired
    private MainView mainView;

    @Autowired
    private WordsServiceFacade wordsServiceFacade;

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle(windowTitle);
        primaryStage.setScene(new Scene(mainView));
        primaryStage.setResizable(true);
        primaryStage.centerOnScreen();

        // load dictonary
        Dictonary dictonary = loadFile("/polish-win1250.txt");
        AnagramService as = new AnagramService();
        wordsServiceFacade.setWordsService(new WordsService(dictonary, as));

        // show page
        primaryStage.show();
    }

    public static void main(String[] args) {
        launchApp(App.class, args);
    }

    private Dictonary loadFile(String spath) throws IOException {
        Dictonary wc = new Dictonary();
        try (InputStream resource = App.class.getResourceAsStream(spath)) {
            new BufferedReader(new InputStreamReader(resource,
                    Charset.forName("windows-1250"))).lines().forEach(wc::add);
        }
        return wc;
    }


}