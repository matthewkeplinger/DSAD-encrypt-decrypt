
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

	private TextField signal = new TextField();
	private TextField key = new TextField();
	private TextField iv = new TextField();
	private TextField output = new TextField();
	private TextField profile = new TextField();
	private TextField signal2 = new TextField();
	private TextField key2 = new TextField();
	private TextField iv2 = new TextField();
	private TextField output2 = new TextField();
	private TextField profile2 = new TextField();
	
	
	public static byte[] getByteByString(String binaryString) {
        int splitSize = 8;

        if (binaryString.length() % splitSize == 0) {
            int index = 0;
            int position = 0;

            byte[] resultByteArray = new byte[binaryString.length() / splitSize];
            String text = binaryString;

            while (index < text.length()) {
                String binaryStringChunk = text.substring(index, Math.min(index + splitSize, text.length()));
                Integer byteAsInt = Integer.parseInt(binaryStringChunk, 2);
                resultByteArray[position] = byteAsInt.byteValue();
                index += splitSize;
                position++;
            }
            return resultByteArray;
        } else {
            System.out.println("Cannot convert binary string to byte[], because of the input length. '" + binaryString + "' % 8 != 0");
            return null;
        }
    }

    public static byte[] convertFromIntsToBytes(int[] ints) {
        byte[] bytes = new byte[ints.length * 4];

        for (int i = 0; i < ints.length; i++) {
            for (int j = 0; j < 4; j++) {
                bytes[4 * i + j] = (byte) ((ints[i] >> (24 - 8 * j)) & 0xFF);
            }
        }

        return bytes;
    }

    public static int[] convertFromBytesToInts(byte[] bs) {
        int length = (int) Math.ceil(bs.length / 4.0);
        length += length % 2;
        int[] ints = new int[length];
        int index;
        for (int i = 0; i < ints.length; i++) {
            ints[i] = 0;
            for (int j = 0; j < 4; j++) {
                index = 4 * i + j;
                if (index < bs.length) {
                    ints[i] = (bs[4 * i + j] & 0xFF) + (ints[i] << 8);
                } else {
                    ints[i] = ints[i] << 8;
                }
            }
        }

        return ints;
    }
    public static int integerfrmbinary(String str){
    double j=0;
    for(int i=0;i<str.length();i++){
        if(str.charAt(i)== '1'){
         j=j+ Math.pow(2,str.length()-1-i);
     }

    }
    return (int) j;
}
    
    
    
	public void start(Stage primaryStage) {
	        int delta = 0x9E3779B9;
	        String mess = "";
	        String mess2 = "";

	        // loading profiles in arraylist
	        ArrayList<int[]> profiles = new ArrayList();
	        int[] p1 = new int[]{0, 3, 4, 132, 6, 135, 9, 15, 27, 156, 29, 158, 31, 33, 163, 166, 167, 42, 175, 47, 49, 181, 54, 55, 184, 186, 187, 60, 188, 61, 63, 193, 72, 73, 74, 75, 206, 79, 209, 212, 215, 88, 90, 218, 91, 92, 94, 96, 224, 227, 229, 231, 238, 110, 112, 115, 116, 119, 248, 122, 251, 252, 124, 127};
	        int[] p2 = new int[]{128, 1, 129, 3, 132, 135, 136, 9, 137, 10, 12, 15, 145, 146, 23, 152, 24, 156, 159, 161, 171, 44, 45, 175, 47, 176, 49, 179, 180, 53, 55, 184, 57, 58, 190, 195, 70, 71, 72, 207, 80, 84, 214, 87, 215, 89, 90, 92, 224, 96, 98, 226, 99, 105, 106, 235, 238, 112, 114, 243, 244, 120, 121, 125};
	        int[] p3 = new int[]{2, 131, 3, 4, 133, 135, 136, 10, 11, 15, 143, 145, 17, 21, 151, 25, 157, 158, 33, 164, 37, 38, 172, 173, 45, 174, 51, 183, 56, 58, 187, 64, 192, 196, 70, 72, 204, 207, 82, 211, 84, 88, 89, 217, 90, 91, 92, 93, 95, 224, 97, 226, 232, 104, 110, 241, 118, 246, 119, 120, 249, 250, 122, 123};
	        int[] p4 = new int[]{1, 129, 3, 133, 135, 14, 144, 145, 19, 147, 151, 153, 154, 155, 35, 164, 36, 165, 40, 41, 43, 172, 45, 174, 51, 53, 181, 54, 190, 191, 194, 66, 195, 198, 199, 72, 73, 203, 206, 79, 81, 82, 83, 212, 84, 213, 90, 224, 227, 229, 104, 105, 106, 108, 238, 241, 114, 116, 245, 246, 119, 121, 249, 253};
	        int[] p5 = new int[]{0, 130, 132, 134, 7, 139, 12, 141, 145, 147, 148, 21, 22, 150, 151, 26, 154, 158, 159, 163, 165, 41, 170, 43, 171, 44, 46, 47, 176, 50, 54, 56, 57, 186, 59, 188, 61, 62, 66, 67, 195, 68, 198, 72, 74, 203, 207, 80, 210, 84, 215, 87, 218, 92, 96, 100, 101, 230, 109, 237, 245, 118, 124, 126};
	        int[] p6 = new int[]{1, 3, 6, 8, 138, 13, 143, 17, 145, 18, 22, 153, 25, 154, 28, 156, 158, 36, 37, 38, 40, 42, 174, 51, 57, 185, 186, 187, 188, 60, 62, 66, 69, 201, 207, 208, 81, 212, 84, 86, 215, 218, 221, 94, 95, 96, 99, 228, 229, 231, 105, 108, 241, 242, 243, 244, 117, 247, 120, 250, 123, 252, 253, 125};
	        int[] p7 = new int[]{130, 131, 5, 135, 136, 9, 138, 14, 143, 144, 17, 145, 151, 152, 153, 156, 34, 35, 167, 42, 43, 172, 44, 45, 174, 47, 177, 49, 52, 183, 56, 57, 187, 63, 64, 206, 78, 79, 84, 212, 214, 86, 216, 220, 94, 223, 225, 104, 232, 105, 233, 107, 108, 237, 110, 111, 112, 113, 241, 242, 116, 245, 120, 125};
	        profiles.add(p1);
	        profiles.add(p2);
	        profiles.add(p3);
	        profiles.add(p4);
	        profiles.add(p5);
	        profiles.add(p6);
	        profiles.add(p7);
	       

	       


	     

	

		final NumberAxis xAxis = new NumberAxis(0, 64, 10);
		final NumberAxis yAxis = new NumberAxis(-255, 255, 10);
		final NumberAxis xAxis2 = new NumberAxis(0, 64, 5);
		final NumberAxis yAxis2 = new NumberAxis(-1, 2, 1);
		final LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis2, yAxis2);
		final LineChart<Number, Number> lineChart2 = new LineChart<Number, Number>(xAxis, yAxis);
		GridPane pane = new GridPane();
		GridPane pane2 = new GridPane();
		btnSw1 = new Button("Encrpyt");
		btnSw2 = new Button("Decrypt");
		pane.setVgap(5);
		pane.setHgap(5);
		pane.add(new Label("Enter Signal file:"), 1, 1);
		pane.add(signal, 2, 1);
		pane.add(new Label("Enter key file:"), 1, 2);
		pane.add(key, 2, 2);
		pane.add(new Label("Enter IV file:"), 1, 3);
		pane.add(iv, 2, 3);
		pane.add(new Label("Output:"), 1, 5);
		pane.add(output, 2, 4);
		pane.add(new Label("Select profile(1-7) :"), 1, 4);
		pane.add(profile, 2, 5);
		pane.add(btnSw1, 2, 6);
		
		pane2.setVgap(5);
		pane2.setHgap(5);
		pane2.add(new Label("Enter Signal file:"), 1, 1);
		pane2.add(signal2, 2, 1);
		pane2.add(new Label("Enter key file:"), 1, 2);
		pane2.add(key2, 2, 2);
		pane2.add(new Label("Enter IV file:"), 1, 3);
		pane2.add(iv2, 2, 3);
		pane2.add(new Label("Output:"), 1, 5);
		pane2.add(output2, 2, 4);
		pane2.add(new Label("Select profile(1-7) :"), 1, 4);
		pane2.add(profile2, 2, 5);
		pane2.add(btnSw2, 2, 6);
		
		
		
		
		
		
		
		

		

		// Set UI properties
		pane.setAlignment(Pos.CENTER);
		pane.add(lineChart, 0, 0);
		signal.setAlignment(Pos.BOTTOM_RIGHT);
		key.setAlignment(Pos.BOTTOM_RIGHT);
		iv.setAlignment(Pos.BOTTOM_RIGHT);
		output.setAlignment(Pos.BOTTOM_RIGHT);
		output.setEditable(false);
		pane.setHalignment(btnSw1, HPos.RIGHT);
		pane.setPadding(new Insets(10, 10, 10, 10));
		
		pane2.setAlignment(Pos.CENTER);
		pane2.add(lineChart2, 0, 0);
		signal.setAlignment(Pos.BOTTOM_RIGHT);
		key.setAlignment(Pos.BOTTOM_RIGHT);
		iv.setAlignment(Pos.BOTTOM_RIGHT);
		output.setAlignment(Pos.BOTTOM_RIGHT);
		output.setEditable(false);
		pane.setHalignment(btnSw2, HPos.RIGHT);
		pane.setPadding(new Insets(10, 10, 10, 10));

		// Create a scene and place it in the stage
		scene1 = new Scene(pane);
		scene2 = new Scene(pane2);
		primaryStage.setTitle("Crytography Project"); // Set the stage title
		primaryStage.setScene(scene1); // Place the scene in the stage
		primaryStage.show(); // Display the stage
		btnSw1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			    public void handle(ActionEvent e) {
			       
			 // load in files
	        FileInputStream in;
			try {
				in = new FileInputStream(signal.getText());
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        FileInputStream keyF;
			try {
				keyF = new FileInputStream(key.getText());
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        FileInputStream ivF;
			try {
				ivF = new FileInputStream(iv.getText());
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        int profileChoice = Integer.parseInt(profile.getText()); 
	        // convert files to bytese
	        byte[] byteFile = new byte[in.available()];
	        in.read(byteFile);
	        byte[] byteKey = new byte[keyF.available()];
	        keyF.read(byteKey);
	        byte[] byteiv = new byte[ivF.available()];
	        ivF.read(byteiv);
	        // bytes to ints for encrypting         
	        int[] intFile = convertFromBytesToInts(byteFile);
	        int[] intKey = convertFromBytesToInts(byteKey);
	        int[] intIv = convertFromBytesToInts(byteiv);

	        // encrypt file
	        int[] fileECB = TinyE.encryptECB(intFile, intKey, delta);

	        // encrypted signal converted tostring then int array for encoding
	        int[] encodedSignal = Encode.encode(profiles.get(profileChoice), fileECB);
	        XYChart.Series encoded = new XYChart.Series();
			encoded.setName("Encoded signal");
			for (int i = 0; i < 64; i++) {
				encoded.getData().add(new XYChart.Data(i, encodedSignal[i]));
			}
			lineChart2.getData().add(encoded);
			
			for (int i = 0; i < 64; i++) {
	            mess = mess + Integer.toBinaryString(encodedSignal[i]);
	        }
	        output.setText(mess);
	        stage.setScene(scene2);
			} 
			});
		
		btnSw2.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			    public void handle(ActionEvent e) {
			       
			 // load in files
	        FileInputStream in;
			try {
				in = new FileInputStream(signal2.getText());
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        FileInputStream keyF;
			try {
				keyF = new FileInputStream(key2.getText());
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        FileInputStream ivF;
			try {
				ivF = new FileInputStream(iv2.getText());
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        int profileChoice = Integer.parseInt(profile2.getText()); 
	        // convert files to bytese
	        byte[] byteFile = new byte[in.available()];
			
	        in.read(byteFile);
	        byte[] byteKey = new byte[keyF.available()];
	        keyF.read(byteKey);
	        byte[] byteiv = new byte[ivF.available()];
	        ivF.read(byteiv);
	        // bytes to ints for encrypting         
	        int[] intFile = convertFromBytesToInts(byteFile);
	        int[] intKey = convertFromBytesToInts(byteKey);
	        int[] intIv = convertFromBytesToInts(byteiv);

	        int[] decodedSignal = Encode.decode(profiles.get(profileChoice), intFile);
	        
	        // decrypt decoded signal
	        int [] dfileECB = TinyE.decryptECB(decodedSignal, intKey, delta);
	        XYChart.Series binarySignal = new XYChart.Series();
			binarySignal.setName("Raw Signal");
			
			for (int i = 0; i < 64; i++) {
				binarySignal.getData().add(new XYChart.Data(i, dfileECB[i]));
			}
			lineChart.getData().add(binarySignal);
			
			for (int i = 0; i < 64; i++) {
	            mess2 = mess2 + Integer.toBinaryString(dfileECB[i]);
	        }
	        output.setText(mess2);
	        stage.setScene(scene1);
			} 
			});
		
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}