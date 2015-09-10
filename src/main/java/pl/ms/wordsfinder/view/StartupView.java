package pl.ms.wordsfinder.view;

import javafx.application.Application;
import javafx.application.Preloader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by Marcin on 2015-09-07.
 */
@Component
public class StartupView {

    public Scene build() throws Exception {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(50, 50, 50, 50));
        grid.setVgap(5);
        grid.setHgap(5);
        //grid.setGridLinesVisible(true);
        grid.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);

        //Defining the label
        Label initialized = new Label();
        initialized.setText("Application is being initialized");
        initialized.setFont(Font.font ("Verdana", 16));
        initialized.setMinWidth(300);
        GridPane.setConstraints(initialized, 0, 0, 1, 1);
        grid.getChildren().add(initialized);

//        //Defining the bar
//        ProgressBar bar = new ProgressBar();
//        GridPane.setConstraints(bar, 0, 1, 1, 1);
//        bar.setMinWidth(200);
//        grid.getChildren().add(bar);

        //Defining the pleaseWait
        Label pleaseWait = new Label();
        pleaseWait.setText("Please wait ...");
        GridPane.setConstraints(pleaseWait, 0, 1, 1, 1);
        grid.getChildren().add(pleaseWait);

        return new Scene(grid, 350, 200);
    }
}