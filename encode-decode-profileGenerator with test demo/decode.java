/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package encode;


public class decode {
    
    public static int[] decode(int[] encodedSignal, int[] profile){
        int[] binarySignal = new int[64];
        for(int i=0;i<encodedSignal.length;i++){
            if(encodedSignal[i]<0)
                binarySignal[i]=0;
            else
                binarySignal[i]=1;
        }
        
        
        return binarySignal;
    }
}
    

 