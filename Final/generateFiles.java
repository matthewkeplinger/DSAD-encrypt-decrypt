
import java.io.*;
        
public class generateFiles {
    
     public static void main(String... aArgs) throws FileNotFoundException, IOException {
         byte[] signal = new byte[8];
        signal[0]= (byte)0b10100011;
        signal[1]= (byte)0b01110000;
        signal[2]= (byte)0b11000000;
        signal[3]= (byte)0b01111000;
        signal[4]= (byte)0b10100011;
        signal[5]= (byte)0b10000000;
        signal[6]= (byte)0b00011000;
        signal[7]= (byte)0b10001000;
       
        byte [] iv= new byte [8];
        iv[0] = (byte)0b01110101;
        iv[1] = (byte)0b01011101;
        iv[2] = (byte)0b01110011;
        iv[3] = (byte)0b11111100;
        iv[4] = (byte)0b11100011;
        iv[5] = (byte)0b00011101;
        iv[6] = (byte)0b01000001;
        iv[7] = (byte)0b01110000;
        
        byte [] k = new byte [16];
        k[0] = (byte)0b01000101;
        k[1] = (byte)0b01011101;
        k[2] = (byte)0b00010011;
        k[3] = (byte)0b01000100;
        k[4] = (byte)0b01100011;
        k[5] = (byte)0b01111101;
        k[6] = (byte)0b01001101;
        k[7] = (byte)0b01111101;
        k[8] = (byte)0b01001101;
        k[9] = (byte)0b00000101;
        k[10] = (byte)0b11110101;
        k[11] = (byte)0b01001111;
        k[12] = (byte)0b01111101;
        k[13] = (byte)0b01110101;
        k[14] = (byte)0b11111101;
        k[15] = (byte)0b01110111;
         
         
         DataOutputStream key1 = new DataOutputStream(new FileOutputStream("key.dat"));
         DataOutputStream iv1 = new DataOutputStream(new FileOutputStream("iv.dat"));
         DataOutputStream out = new DataOutputStream(new FileOutputStream("signal.dat"));
         out.write(signal);
         key1.write(k);
         iv1.write(iv);
         iv1.flush();
         iv1.close();
         key1.flush();
         key1.close();
         out.flush();
         out.close();
         
     }
}
