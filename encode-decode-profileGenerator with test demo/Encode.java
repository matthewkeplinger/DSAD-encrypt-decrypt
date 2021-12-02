/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package encode;

/**
 *
 * @author nicho
 */
public class Encode {
    public static int[]encode(int[]profile, int[] signal){
        int[] ans = new int[64];
        
        for(int i =0;i<profile.length;i++){
            if(signal[i]==1){
                ans[i]=profile[i];
            }
            else
                ans[i]=(-profile[i]);
        }
        return ans;
        
    
    }    
}
