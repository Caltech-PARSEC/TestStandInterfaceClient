import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
        Scene scene = new Scene(root, 300, 600);

        /* Put the scene on the stage. */
        stage.setScene(scene);
        stage.setTitle("Valve Sliders");

        /* Setup a grid to add the sliders to. */
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setVgap(20);
        grid.setHgap(20);

        /* Put sliders on the grid. */
        System.out.println("valves: " + numValves);
        System.out.print("Enter the number of valves: ");
        numValves = in.nextInt();
        in.nextLine(); // Read the rest of the line to get the '\n'.
        System.out.println("valves: " + numValves);

        for (int i = 0; i < numValves; i++) {
            System.out.println("i: " + i);

            /* Setup a new slider. */
            Slider slide = new Slider();
            slide.setMin(0);
            slide.setMax(100);
            slide.setValue(20);
            slide.setShowTickLabels(true);
            slide.setShowTickMarks(true);
            slide.setBlockIncrement(1);

            /* Setup the corresponding label. */
            //Label label = new Label(Double.toString(slide.getValue()));

            grid.add(slide, 0, i);
            //grid.add(label, 1, i);
        }

        System.out.println("Done adding sliders.");

        scene.setRoot(grid);

        /* Display the stage. */
        stage.show();
    }
}
