
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Encode {

    public static int[] encode(int[] profile, int[] signal) {
        CircularList<Integer> prof = new CircularList();
        
        for (int i : profile) {
            prof.add(i);
        }
        String sfileECB = "";
        for (int i = 0; i < signal.length; i++) {
            sfileECB += Integer.toBinaryString(signal[i]);
        }
        int[] binaryEncrypted = new int[sfileECB.length()];
        for (int i = 0; i < binaryEncrypted.length; i++) {
            binaryEncrypted[i] = Integer.parseInt(Character.toString(sfileECB.charAt(i)));
        }
        
        int[] ans = new int[binaryEncrypted.length];

        for (int i = 0; i < ans.length; i++) {
            if (binaryEncrypted[i] == 1) {
                ans[i] = prof.get(i);
            } else {
                ans[i] = (-prof.get(i));
            }
        }
        return ans;

    }

    public static int[] decode(int[] profile, int[] encodedSignal) {             
        int[] binarySignal = new int[encodedSignal.length];
        for (int i = 0; i < binarySignal.length; i++) {
            if (encodedSignal[i] < 0) {
                binarySignal[i] = 0;
            } else {
                binarySignal[i] = 1;
            }
        }
        String sdecodedSignal = "";
        for (int i = 0; i < binarySignal.length; i++) {
            sdecodedSignal += binarySignal[i];
        }
        String sdecodedSignal1 = sdecodedSignal.substring(0, sdecodedSignal.length()/2);
        String sdecodedSignal2 = sdecodedSignal.substring(sdecodedSignal.length()/2, sdecodedSignal.length());
        
        int[] decodedSignal1 = new int[2];
        decodedSignal1[0] = (int)Long.parseLong(sdecodedSignal1,2);
        decodedSignal1[1] = (int)Long.parseLong(sdecodedSignal2,2);
        return decodedSignal1;
    }
}

class CircularList<E> extends ArrayList<E> {

    @Override
    public E get(int index) {
        return super.get(index % size());
    }
}
