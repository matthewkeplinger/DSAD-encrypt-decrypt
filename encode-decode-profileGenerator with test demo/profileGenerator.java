package encode;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class profileGenerator {
   private static final int SET_SIZE_REQUIRED = 64;
   private static final int NUMBER_RANGE = 254;

    public static int[] generate() {
        Integer[] arr = new Integer[64];
        Random random = new Random();

        Set set = new HashSet<Integer>(SET_SIZE_REQUIRED);

        while(set.size()< SET_SIZE_REQUIRED) {
            while (set.add(random.nextInt(NUMBER_RANGE)) != true)
                ;
        }
        assert set.size() == SET_SIZE_REQUIRED;
        
        arr = (Integer[]) set.toArray(new Integer[set.size()]);
        int[] profile = new int[arr.length];
        for(int i=0;i<arr.length;i++)
            profile[i]=arr[i];
        return profile;
        
    
    }
   

    public static int getSET_SIZE_REQUIRED() {
        return SET_SIZE_REQUIRED;
    }

    public static int getNUMBER_RANGE() {
        return NUMBER_RANGE;
    }
   
}
