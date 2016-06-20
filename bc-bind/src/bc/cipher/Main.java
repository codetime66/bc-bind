/*

*/
package bc.cipher;

import bc.cipher.api.Cipher;
import bc.cipher.api.CipherFactory;

public class Main {

    public static void main(String args[]) {

        try {
            Cipher cipher = (Cipher) CipherFactory.getInstance(args[0]);
            System.out.println(cipher.perform(args[1].getBytes(), args[2]));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}