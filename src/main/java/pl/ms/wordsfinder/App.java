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
import pl.ms.wordsfinder.view.StartupView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by Marcin on 2015-09-02.
 */
@SpringBootApplication
public class App extends AbstractJavaFxApplicationSupport {

    @Value("${app.ui.title}")
    private String windowTitle;

    @Autowired
    private MainView mainView;

    @Autowired
    private StartupView startupView;

    @Autowired
    private WordsServiceFacade wordsServiceFacade;

    @Override
    public void start(Stage primaryStage) throws Exception {
//
//        notifyPreloader(new Preloader.StateChangeNotification(Preloader.StateChangeNotification.Type.BEFORE_INIT));
        primaryStage.setTitle(windowTitle);
        primaryStage.centerOnScreen();

        // show startup progress, preloaders are fancy but I don't like'em
        primaryStage.setScene(startupView.build());
        primaryStage.setResizable(false);
        primaryStage.show();

        Dictonary dictonary = loadFile("/sjp-20150906.zip");
        AnagramService as = new AnagramService();
        wordsServiceFacade.setWordsService(new WordsService(dictonary, as));

//        notifyPreloader(new Preloader.StateChangeNotification(Preloader.StateChangeNotification.Type.BEFORE_START));

        primaryStage.setScene(new Scene(mainView, 640, 480));
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launchApp(App.class, args);
    }

    private Dictonary loadFile(String spath) throws IOException {
        Dictonary wc = new Dictonary();
        try (InputStream resource = App.class.getResourceAsStream(spath)) {
            ZipInputStream stream = new ZipInputStream(resource);
            ZipEntry ze = stream.getNextEntry();
            if (ze.getName().equals("slowa-win.txt")) {
                new BufferedReader(new InputStreamReader(stream, Charset.forName("windows-1250"))).lines().forEach(wc::add);
            }
        }
        return wc;
    }


}