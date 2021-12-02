

public class TinyE {
    public static enum Mode {ECB, CBC, CTR};
    private final String DELTA = "0x9E3779B9";
    
    /** ECB
     * @param plaintext **/
    public static int[] encryptECB(int[] plaintext, int[] key, int delta) {
        int[] cipher = new int[plaintext.length];
        
        for (int p = 0; p < plaintext.length; p += 2) {
            int sum = 0;
            cipher[p] = plaintext[p];
            cipher[p + 1] = plaintext[p + 1];
            for (int i = 0; i < 32; i++) {
                sum += delta;
                
                cipher[p] = cipher[p] + (((cipher[p + 1] << 4) + key[0]) ^ (cipher[p + 1] + sum) ^ ((cipher[p + 1] >> 5) + key[1]));
                cipher[p + 1] = cipher[p + 1] + (((cipher[p] << 4) + key[2]) ^ (cipher[p] + sum) ^ ((cipher[p] >> 5) + key[3]));
            }
        }
        return cipher;
    }
    
    public static int[] decryptECB(int[] ciphertext, int[] key, int delta) {
        int[] plain = new int[ciphertext.length];
        for (int p = 0; p < ciphertext.length; p += 2) {
            int sum = delta << 5;
            
            plain[p] = ciphertext[p];
            plain[p + 1] = ciphertext[p + 1];
            
            for (int i = 0; i < 32; i++) {
                plain[p + 1] = plain[p + 1] - (((plain[p] << 4) + key[2]) ^ (plain[p] + sum) ^ ((plain[p] >> 5) + key[3]));
                plain[p] = plain[p] - (((plain[p + 1] << 4) + key[0]) ^ (plain[p + 1] + sum) ^ ((plain[p + 1] >> 5) + key[1]));
                sum -= delta;
            }
        }
        
        return plain;
    }
    
    /** CBC **/
    public static int[] encryptCBC(int[] plaintext, int[] key, int delta, int[] iv) {
        // XOR IV and plaintext[0], [1]
        plaintext[0] = plaintext[0] ^ iv[0];
        plaintext[1] = plaintext[1] ^ iv[1];
        
        int[] cipher = new int[plaintext.length];
        
        for (int p = 0; p < plaintext.length; p += 2) {
            if (p != 0) {
                plaintext[p] = plaintext[p] ^ cipher[p];
                plaintext[p+1] = plaintext[p+1] ^ cipher[p+1];
            }
            int sum = 0;
            cipher[p] = plaintext[p];
            cipher[p + 1] = plaintext[p + 1];
            for (int i = 0; i < 32; i++) {
                sum += delta;
                
                cipher[p] = cipher[p] + (((cipher[p + 1] << 4) + key[0]) ^ (cipher[p + 1] + sum) ^ ((cipher[p + 1] >> 5) + key[1]));
                cipher[p + 1] = cipher[p + 1] + (((cipher[p] << 4) + key[2]) ^ (cipher[p] + sum) ^ ((cipher[p] >> 5) + key[3]));
            }
        }
        return cipher;
    }
    
    public static int[] decryptCBC(int[] ciphertext, int[] key, int delta, int[] iv) {
        int[] plain = new int[ciphertext.length];
        
        for (int p = 0; p < ciphertext.length; p += 2) {
            int sum = delta << 5;
            
            plain[p] = ciphertext[p];
            plain[p + 1] = ciphertext[p + 1];
            
            for (int i = 0; i < 32; i++) {
                plain[p + 1] = plain[p + 1] - (((plain[p] << 4) + key[2]) ^ (plain[p] + sum) ^ ((plain[p] >> 5) + key[3]));
                plain[p] = plain[p] - (((plain[p + 1] << 4) + key[0]) ^ (plain[p + 1] + sum) ^ ((plain[p + 1] >> 5) + key[1]));
                sum -= delta;
            }
            
            if (p == 0) {
                plain[0] = plain[0] ^ iv[0];
                plain[1] = plain[1] ^ iv[1];
            } else {
                // XOR IV with ciphertext[0], [1]
                plain[p] = plain[p] ^ ciphertext[p-2];
                plain[p+1] = plain[p+1] ^ ciphertext[p-1];
            }
        }        
        return plain;
    }               
}