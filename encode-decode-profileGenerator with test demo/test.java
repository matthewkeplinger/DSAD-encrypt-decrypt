/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package encode;

public class test {
    
    public static void main(String args[]){
        int[] profile = profileGenerator.generate();
        int[] signal = new int[64];
        
        for(int i=0;i<15;i++)
            signal[i]=1;
        for(int i=33;i<35;i++)
            signal[i] =1;
       int[] encodedSignal = Encode.encode(profile, signal);
       int[] decodedSignal = decode.decode(encodedSignal, profile);
       for(int i=0;i<signal.length;i++){
           System.out.println(signal[i]+" "+profile[i]+" "+encodedSignal[i]+" "+decodedSignal[i]);
           

       }
       
    }
    
}
