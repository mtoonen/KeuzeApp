/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package meine.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Meine Toonen
 */
public class Encrypter {
     private final Log log = LogFactory.getLog(this.getClass());

    private static final int SALT_SIZE = 4;
/**
     * @param request gebruikt voor initialisatie PRNG met IP en poort van client
     * @return salt in hex string
     */
    public static String generateHexSalt() {
        long seed = System.currentTimeMillis();
        long ip = 1;

        seed = seed * ip;

        Random random = new Random(seed);
        StringBuilder salt = new StringBuilder(SALT_SIZE * 2);
        for (int i = 0; i < SALT_SIZE; i++) {
            int b = random.nextInt(16);
            salt.append(Integer.toHexString(b));
            b = random.nextInt(16);
            salt.append(Integer.toHexString(b));
        }
        return salt.toString();
    }

    public static String getHexSha1(String saltHex, String phrase) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        saltHex = saltHex.trim();
        if (saltHex.length() % 2 != 0) {
            throw new IllegalArgumentException("Invalid salt hex length (must be divisible by 2): " + saltHex.length());
        }

        byte[] salt = new byte[saltHex.length() / 2];
        try {
            for (int i = 0; i < saltHex.length() / 2; i++) {
                int hexIdx = i * 2;
                int highNibble = Integer.parseInt(saltHex.substring(hexIdx, hexIdx + 1), 16);
                int lowNibble = Integer.parseInt(saltHex.substring(hexIdx + 1, hexIdx + 2), 16);
                salt[i] = (byte) (highNibble << 4 | lowNibble);
            }
        } catch (NumberFormatException nfe) {
            throw new IllegalArgumentException("Invalid hex characters in salt parameter: " + saltHex);
        }
        return getHexSha1(salt, phrase);
    }

    public static String getHexSha1(byte[] salt, String phrase) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        byte[] phraseUTF8 = phrase.getBytes("UTF8");
        byte[] saltedPhrase = new byte[salt.length + phraseUTF8.length];

        System.arraycopy(salt, 0, saltedPhrase, 0, salt.length);
        System.arraycopy(phraseUTF8, 0, saltedPhrase, salt.length, phraseUTF8.length);

        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] digest = md.digest(saltedPhrase);

        /* Converteer byte array naar hex-weergave */
        StringBuilder sb = new StringBuilder(digest.length * 2);
        for (int i = 0; i < digest.length; i++) {
            sb.append(Integer.toHexString(digest[i] >> 4 & 0xf)); /* and mask met 0xf nodig door sign-extenden van bytes... */
            sb.append(Integer.toHexString(digest[i] & 0xf));
        }
        return sb.toString();
    }


    /*public static void main(String[] args) throws Exception {
        String salt = "00112233";
        String phrase = "test";
        System.out.println(getHexSha1(salt, phrase).equals("af30b67b3c0e3fcd1d80ba679770f3947f6edd8d"));
        phrase = "tëst";
        System.out.println(getHexSha1(salt, phrase).equals("d49a8431ec274a1433b7fdda34e4de0b2784b812"));
    }*/
}
