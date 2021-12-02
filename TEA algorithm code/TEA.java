package com.java24hours;
public class TEA {
	private final static int DELTA = 0x9E3779B9;
	private final static int ECHO  = 32;
	private final static int CHARLIE = 0xC6EF3720;

	private final int[] S = new int[4];

	/**
	 * Initialize the cipher for encryption or decryption.
	 * @param key a 16 byte (128-bit) key
	 */
	public TEA(byte[] key) {
		if (key == null)
			throw new RuntimeException("Invalid key: Key was null");
		if (key.length < 16)
			throw new RuntimeException("Invalid key: Length was less than 16 bytes");
		for (int off=0, i=0; i<4; i++) {
			S[i] = ((key[off++] & 0xff)) |
			((key[off++] & 0xff) <<  8) |
			((key[off++] & 0xff) << 16) |
			((key[off++] & 0xff) << 24);
		}
	}

	/**
	 * Encrypt an array of bytes.
	 * @param clear the cleartext to encrypt
	 * @return the encrypted text
	 */
	public byte[] encrypt(byte[] clear) {
		int paddedSize = ((clear.length/8) + (((clear.length%8)==0)?0:1)) * 2;
		int[] buffer = new int[paddedSize + 1];
		buffer[0] = clear.length;
		pack(clear, buffer, 1);
		ENCRYPT2(buffer);
		return unpack(buffer, 0, buffer.length * 4);
	}

	/**
	 * Decrypt an array of bytes.
         * @param crypt
	 * @return the decrypted text
	 */
	public byte[] decrypt(byte[] crypt) {
		assert crypt.length % 4 == 0;
		assert (crypt.length / 4) % 2 == 1;
		int[] buffer = new int[crypt.length / 4];
		pack(crypt, buffer, 0);
		DECRYPT2(buffer);
		return unpack(buffer, 1, buffer[0]);
	}

	void ENCRYPT2(int[] buf) {
		assert buf.length % 2 == 1;
		int i, v0, v1, sum, n;
		i = 1;
		while (i<buf.length) {
			n = ECHO;
			v0 = buf[i];
			v1 = buf[i+1];
			sum = 0;
			while (n-->0) {
				sum += DELTA;
				v0  += ((v1 << 4 ) + S[0] ^ v1) + (sum ^ (v1 >>> 5)) + S[1];
				v1  += ((v0 << 4 ) + S[2] ^ v0) + (sum ^ (v0 >>> 5)) + S[3];
			}
			buf[i] = v0;
			buf[i+1] = v1;
			i+=2;
		}
	}
	
	void DECRYPT2(int[] buf) {
		assert buf.length % 2 == 1;
		int i, v0, v1, sum, n;
		i = 1;
		while (i<buf.length) {
			n = ECHO;
			v0 = buf[i]; 
			v1 = buf[i+1];
			sum = CHARLIE;
			while (n--> 0) {
				v1  -= ((v0 << 4 ) + S[2] ^ v0) + (sum ^ (v0 >>> 5)) + S[3];
				v0  -= ((v1 << 4 ) + S[0] ^ v1) + (sum ^ (v1 >>> 5)) + S[1];
				sum -= DELTA;
			}
			buf[i] = v0;
			buf[i+1] = v1;
			i+=2;
		}
	}
	
	void pack(byte[] src, int[] dest, int destOffset) {
		assert destOffset + (src.length / 4) <= dest.length;
		int i = 0, shift = 24;
		int j = destOffset;
		dest[j] = 0;
		while (i<src.length) {
			dest[j] |= ((src[i] & 0xff) << shift);
			if (shift==0) {
				shift = 24;
				j++;
				if (j<dest.length) dest[j] = 0;
			}
			else {
				shift -= 8;
			}
			i++;
		}
	}
	
	byte[] unpack(int[] src, int srcOffset, int destLength) {
		assert destLength <= (src.length - srcOffset) * 4;
		byte[] dest = new byte[destLength];
		int i = srcOffset;
		int count = 0;
		for (int j = 0; j < destLength; j++) {
			dest[j] = (byte) ((src[i] >> (24 - (8*count))) & 0xff);
			count++;
			if (count == 4) {
				count = 0;
				i++;
			}
		}
		return dest;
	}
}
