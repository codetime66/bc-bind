/*

 */
package bc.cipher;

import bc.cipher.api.Cipher;
import bc.cipher.api.CipherFactory;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Base64;

public class Main {

    public static void main(String args[]) {

        try {

            String cipherImpl = args[0];

            Cipher cipher = (Cipher) CipherFactory.getInstance(cipherImpl);

            switch (cipherImpl) {
                //java -cp /home/codetime/projects/bc-bind/target/bc-1.0-SNAPSHOT.jar:/home/codetime/glassfish4/glassfish/modules/javax.json.jar bc.cipher.Main KeyPairGen <PARTICIPANT_SHORT_NAME>                
                case "KeyPairGen":
                    System.out.println(cipher.perform(args[1].getBytes()));                    
                    break;
                    
                //java -cp /home/codetime/projects/bc-bind/target/bc-1.0-SNAPSHOT.jar:/home/codetime/glassfish4/glassfish/modules/javax.json.jar bc.cipher.Main MDGenerator <ORIGINAL_MSG>                                        
                case "MDGenerator":
                    System.out.println(cipher.perform(args[1].getBytes()));
                    break;
                    
                //java -cp /home/codetime/projects/bc-bind/target/bc-1.0-SNAPSHOT.jar:/home/codetime/glassfish4/glassfish/modules/javax.json.jar bc.cipher.Main SignGen <ORIGINAL_MSG> <PRIVATE_KEY>                    
                case "SignGen":
                    System.out.println(cipher.perform(readKeyFile(args[1]).getBytes(), args[2]));
                    break;
                    
                //java -cp /home/codetime/projects/bc-bind/target/bc-1.0-SNAPSHOT.jar:/home/codetime/glassfish4/glassfish/modules/javax.json.jar bc.cipher.Main RSAEncryption <ORIGINAL_MSG> <PUBLIC_KEY>                    
                case "RSAEncryption":
                    System.out.println(cipher.perform(readKeyFile(args[1]).getBytes(), args[2]));
                    break;
                    
                //java -cp /home/codetime/projects/bc-bind/target/bc-1.0-SNAPSHOT.jar:/home/codetime/glassfish4/glassfish/modules/javax.json.jar bc.cipher.Main RSADecryption <ENCRYPT_MSG> <PRIVATE_KEY>                                        
                case "RSADecryption":
                    System.out.println(cipher.perform(readKeyFile(args[1]).getBytes(), args[2]));
                    break;
                    
                //java -cp /home/codetime/projects/bc-bind/target/bc-1.0-SNAPSHOT.jar:/home/codetime/glassfish4/glassfish/modules/javax.json.jar bc.cipher.Main SignVerify <SIGN_MSG> <ORIGINAL_MSG> <PARTICIPANT_SHORT_NAME>                    
                case "SignVerify":
                    String privFileName = "PRIVKEY" + new String(args[3]);
                    String pubFileName = "PUBKEY" + new String(args[3]);
                    //
                    Cipher cipherSign = (Cipher) CipherFactory.getInstance("SignGen");
                    String sigFile = cipherSign.perform(readKeyFile(args[1]).getBytes(), privFileName);
                    System.out.println("signed file: " + sigFile);
                    //
                    byte[] sigToVerify = base64Decode(sigFile);
                    //byte[] sigToVerify = base64Decode("MCwCFFhTO4diy6t/1eNMUPQf1bOrL+HiAhRL9nQWdrqTrkKUjmQwOb2Q0ai8zg==");
                    //byte[] sigToVerify = base64Decode("MCwCFFhTO4diy6t/1eNMUPQf1bOrL+HiAhRL9nQWdrqTrkKUjmQwOb2Q0ai8zz==");
                    //                    
                    System.out.println(cipher.perform(sigToVerify, readKeyFile(args[2]).getBytes(), pubFileName));
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static byte[] base64Decode(String key64) {
        return Base64.getDecoder().decode(key64);
    }

    static String readKeyFile(String fileName) throws Exception {
        Reader in = new BufferedReader(
                new InputStreamReader(new FileInputStream(fileName), "UTF8"));
        StringBuffer buf = new StringBuffer();
        int ch;
        while ((ch = in.read()) > -1) {
            buf.append((char) ch);
        }
        in.close();
        return buf.toString();
    }

}
