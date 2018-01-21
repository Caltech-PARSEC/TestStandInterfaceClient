import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.lang.Math;
import java.util.Scanner;

public class ValveSliders extends Application {
    private int numValves;
    private Scanner in;

    public ValveSliders() {
        numValves = 4;
        in = new Scanner(System.in);
    }

    public ValveSliders(int num) {
        /* There should be at least one slider. */
        numValves = Math.max(num, 1);
        in = new Scanner(System.in);
    }

    public int getNumValves() {
        return numValves;
    }

    @Override
    public void start(Stage stage) {
        /* Setup the group and the scene. */
        Group root = new Group();
        Scene scene = new Scene(root, 400, 600);

        /* Put the scene on the stage. */
        stage.setScene(scene);
        stage.setTitle("Valve Sliders");

        /* Setup a grid to add the sliders to. */
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setVgap(20);
        grid.setHgap(20);

        /* Get the number of valves from the user. */
        System.out.print("Enter the number of valves: ");
        numValves = in.nextInt();
        in.nextLine(); // Read the rest of the line to get the '\n'.
        System.out.println("valves: " + numValves);

        /* Put sliders on the grid. */
        for (int i = 0; i < numValves; i++) {
            /* Setup a new slider. */
            Slider slide = new Slider();
            slide.setMin(0);
            slide.setMax(100);
            slide.setValue(20);
            slide.setShowTickLabels(true);
            slide.setShowTickMarks(true);
            slide.setBlockIncrement(1);

            /* Setup the corresponding labels. */
            Label nameLabel = new Label("Valve " + i);
            Label valLabel  = new Label();
            //Label valLabel = new Label(Double.toString(slide.getValue()));
            valLabel.textProperty().bind(
                Bindings.format("%.1f", slide.valueProperty())
            );

            /* Put the slider and label next to each other on the grid. */
            grid.add(nameLabel, 0, i);
            grid.add(slide, 1, i);
            grid.add(valLabel, 2, i);
        }

        ScrollPane scroll = new ScrollPane();
        scroll.setContent(grid);
        scene.setRoot(scroll);

        /* Display the stage. */
        stage.show();
    }
}
