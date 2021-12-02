
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;

public class Grapher extends Application {

	Button btnSw1;
	Button btnSw2;
	Stage stage;
	Scene scene1;
	Scene scene2;


    
	public void start(Stage primaryStage) {
	    
	       

	       


	     

	
	        // Creates two linecharts
		final NumberAxis xAxis = new NumberAxis(0, 64, 10);
		final NumberAxis yAxis = new NumberAxis(-255, 255, 10);
		final NumberAxis xAxis2 = new NumberAxis(0, 64, 5);
		final NumberAxis yAxis2 = new NumberAxis(-1, 2, 1);
		final LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis2, yAxis2);
		final LineChart<Number, Number> lineChart2 = new LineChart<Number, Number>(xAxis, yAxis);
		// creates two panes
		GridPane pane = new GridPane();
		GridPane pane2 = new GridPane();
		
		// names the buttons for UI
		btnSw1 = new Button("Encrpyt");
		btnSw2 = new Button("Decrypt");
		
		// Sets the panes with their feilds
		pane.setVgap(5);
		pane.setHgap(5);

		pane.add(btnSw1, 2, 6);
		
		pane2.setVgap(5);
		pane2.setHgap(5);
		pane.add(btnSw2, 2, 6);
		
		
		
		
		

		

		// Set UI properties
		pane.setAlignment(Pos.CENTER);
		pane.add(lineChart, 0, 0);
	
		pane.setHalignment(btnSw1, HPos.RIGHT);
		pane.setPadding(new Insets(10, 10, 10, 10));
		
		pane2.setAlignment(Pos.CENTER);
		pane2.add(lineChart2, 0, 0);
		
		pane.setHalignment(btnSw2, HPos.RIGHT);
		pane.setPadding(new Insets(10, 10, 10, 10));

		// Create a scene and place it in the stage
		scene1 = new Scene(pane);
		scene2 = new Scene(pane2);
		primaryStage.setTitle("Crytography Project"); // Set the stage title
		primaryStage.setScene(scene1); // Place the scene in the stage
		primaryStage.show(); // Display the stage
		
		// button action for encrpty function
		btnSw1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			    public void handle(ActionEvent e) {
			       //// binary signal goes here
			

	        XYChart.Series encoded = new XYChart.Series();
			encoded.setName("Encoded signal");
			for (int i = 0; i < 64; i++) {
				encoded.getData().add(new XYChart.Data(i, encodedSignal[i]));
			}
			lineChart2.getData().add(encoded);
			
			
	        stage.setScene(scene2);
			} 
			});
		
		// button action for Decrpty action
		btnSw2.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			    public void handle(ActionEvent e) {
			
	        // encoded signal goes here
	        XYChart.Series binarySignal = new XYChart.Series();
			binarySignal.setName("Raw Signal");
			
			for (int i = 0; i < 64; i++) {
				binarySignal.getData().add(new XYChart.Data(i, dfileECB[i]));
			}
			lineChart.getData().add(binarySignal);
			
			
	        stage.setScene(scene1);
			} 
			});
		
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}