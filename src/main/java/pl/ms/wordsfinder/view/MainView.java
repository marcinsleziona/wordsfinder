package pl.ms.wordsfinder.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.ms.wordsfinder.service.WordsServiceFacade;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Marcin on 2015-09-03.
 */
@Component
public class MainView extends VBox {

    private final TextField word;
    private final Button checkWord;
    private final Button findWords;
    private final Button findWordsExact;
    private final TextArea textArea;

    @Autowired
    private WordsServiceFacade wordsServiceFacade;

    public MainView() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);
        grid.setGridLinesVisible(true);
        grid.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(33);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(33);
        ColumnConstraints column3 = new ColumnConstraints();
        column3.setPercentWidth(33);
        grid.getColumnConstraints().addAll(column1, column2, column3); // each get 30% of width

        RowConstraints row1 = new RowConstraints();
        row1.setVgrow(Priority.NEVER);
        RowConstraints row2 = new RowConstraints();
        row2.setVgrow(Priority.NEVER);
        RowConstraints row3 = new RowConstraints();
        row3.setVgrow(Priority.ALWAYS);
        row3.setPrefHeight(500);
        grid.getRowConstraints().addAll(row1, row2, row3);

        //Defining the word
        word = new TextField();
        word.setPromptText("Enter your word. You can use '?'");
        word.setPrefColumnCount(10);
        word.getText();
        GridPane.setConstraints(word, 0, 0, 3, 1);
        grid.getChildren().add(word);

        //Defining the checkWord
        checkWord = new Button("Check Word");
        checkWord.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(wordsServiceFacade.getWordsService().isValid(word.getText())) {
                    textArea.setText("OK");
                } else {
                    textArea.setText("INVALID");
                }
            }
        });
        GridPane.setConstraints(checkWord, 0, 1);
        grid.getChildren().add(checkWord);

        //Defining the findWords button
        findWords = new Button("Find Words");
        findWords.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                StringBuffer sb = new StringBuffer();
                Map<Integer, List<String>> map = wordsServiceFacade.getWordsService().findPossibleWords(word.getText()).stream().collect(Collectors.groupingBy(String::length));
                map.forEach((integer, strings) -> sb.append(integer).append(System.getProperty("line.separator")).append(strings).append(System.getProperty("line.separator")));
                textArea.setText(sb.toString());
            }
        });
        GridPane.setConstraints(findWords, 1, 1);
        grid.getChildren().add(findWords);

        //Defining the findWordsExact button
        findWordsExact = new Button("Find Words (All Letters)");
        findWordsExact.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                StringBuffer sb = new StringBuffer();
                Map<Integer, List<String>> map = wordsServiceFacade.getWordsService().findAllPossibleWords(word.getText()).stream().collect(Collectors.groupingBy(String::length));
                map.forEach((integer, strings) -> sb.append(integer).append(System.getProperty("line.separator")).append(strings).append(System.getProperty("line.separator")));
                textArea.setText(sb.toString());
            }
        });
        GridPane.setConstraints(findWordsExact, 2, 1);
        grid.getChildren().add(findWordsExact);

        //Defining the errorMessage
        textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setWrapText(true);
        GridPane.setConstraints(textArea, 0, 2, 3, 1);
        grid.getChildren().add(textArea);

        getChildren().add(grid);
    }
}
