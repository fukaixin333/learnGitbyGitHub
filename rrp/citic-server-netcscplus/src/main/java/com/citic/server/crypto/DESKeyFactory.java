package com.citic.server.crypto;

public class DESKeyFactory implements CryptoConstants {
	
	/**
	 * 通过添加奇偶位的方式生成64-bit密钥
	 * 
	 * @param raw 7-bit核心密钥"引子"
	 * @return 64-bit密钥
	 */
	public static byte[] generateDESKeyByAddingParity(byte[] raw) {
		return addParity(raw);
	}
	
	/**
	 * 通过调整奇偶位的方式生成64-bit密钥
	 * 
	 * @param raw The key array, to be parity-adjusted.
	 * @param offset The starting index into the key bytes.
	 * @return
	 */
	public static byte[] generateDESKeyByAdjustingParity(byte[] raw, int offset) {
		return adjustParity(raw, offset);
	}
	
	public static byte[] generateDESedeKeyByAddingParity(byte[] raw1, byte[] raw2, byte[] raw3) {
		byte[] key = new byte[24];
		System.arraycopy(addParity(raw1), 0, key, 0, 8);
		System.arraycopy(addParity(raw2), 0, key, 8, 8);
		System.arraycopy(addParity(raw3), 0, key, 16, 8);
		return key;
	}
	
	/**
	 * Convert a 56-bit value into a valid DES key.<br />
	 * The input and output bytes are big-endian, where the most significant byte is in element 0.
	 * <p>
	 * The DES encrypter/decrypter requires a 64-bit key where only 56-bit are significant.The other
	 * 8-bit are parity bits used to ensure that the key has not been corrupted.To make the 64-bit
	 * key, the 56-bit value is broken up into 7-bit chunks. Each 7-bit chunk is moved into an 8-bit
	 * slot taking up the most significant bit positions. The least significant bit (the parity bit)
	 * is set to either 1 or 0 in order to make the quantity of 1 bits in the byte an odd number.
	 * <p>
	 * This method could be used to convert a 7-character string password to a valid DES key.
	 * 
	 * @param key
	 * @return
	 */
	protected static byte[] addParity(byte[] key) {
		byte[] result = new byte[8];
		
		// Keeps track of the bit position in the result
		int resultIx = 1;
		
		// Used to keep track of the number of 1 bits in each 7-bit chunk
		int bitCount = 0;
		
		// Process each of the 56 bits
		for (int i = 0; i < 56; i++) {
			// Get the bit at bit position i
			boolean bit = (key[6 - i / 8] & (1 << (i % 8))) > 0;
			
			// If set, set the corresponding bit in the result
			if (bit) {
				result[7 - resultIx / 8] |= (1 << (resultIx % 8)) & 0xFF;
				bitCount++;
			}
			
			// Set the parity bit after every 7 bits
			if ((i + 1) % 7 == 0) {
				if (bitCount % 2 == 0) {
					// Set low-order bit (parity bit) if bit count is even
					result[7 - resultIx / 8] |= 1;
				}
				resultIx++;
				bitCount = 0;
			}
			resultIx++;
		}
		return result;
	}
	
	/**
	 * Adjust the parity for a raw key array. <br />
	 * This essentially means that each byte in the array will have an odd number of '1' bits (the
	 * last bit in each byte is unused).
	 *
	 * @param key The key array, to be parity-adjusted.
	 * @param offset The starting index into the key bytes.
	 * @return
	 */
	protected static byte[] adjustParity(byte[] key, int offset) {
		byte[] encodedKey = new byte[8];
		for (int i = offset, j = 0; i < 8; i++, j++) {
			encodedKey[j] = (byte) (key[i] ^ ((DES_PARITY[key[i] & 0xFF] == 8) ? 1 : 0));
		}
		return encodedKey;
	}
}
