/*

 */
package bc.cipher;

import bc.cipher.api.Cipher;
import bc.cipher.api.CipherFactory;

public class Main {

    public static void main(String args[]) {

        try {
            Cipher cipher = (Cipher) CipherFactory.getInstance(args[0]);
            int ac = args.length;
            switch (ac) {
                case 2:
                    //java -cp /home/codetime/projects/bc-bind/target/bc-1.0-SNAPSHOT.jar:/home/codetime/glassfish4/glassfish/modules/javax.json.jar bc.cipher.Main KeyPairGen <PARTICIPANT_SHORT_NAME>
                    //java -cp /home/codetime/projects/bc-bind/target/bc-1.0-SNAPSHOT.jar:/home/codetime/glassfish4/glassfish/modules/javax.json.jar bc.cipher.Main MDGenerator <ORIGINAL_MSG>                    
                    System.out.println(cipher.perform(args[1].getBytes()));
                    break;
                case 3:
                    //java -cp /home/codetime/projects/bc-bind/target/bc-1.0-SNAPSHOT.jar:/home/codetime/glassfish4/glassfish/modules/javax.json.jar bc.cipher.Main SignGen <ORIGINAL_MSG> <PRIVATE_KEY>
                    System.out.println(cipher.perform(args[1].getBytes(), args[2]));
                    break;
                case 4:
                    //java -cp /home/codetime/projects/bc-bind/target/bc-1.0-SNAPSHOT.jar:/home/codetime/glassfish4/glassfish/modules/javax.json.jar bc.cipher.Main SignVerify <SIGN_MSG> <ORIGINAL_MSG> <PARTICIPANT_SHORT_NAME>
                    String privFileName = "PRIV"+new String(args[3]);
                    String pubFileName = "PUB"+new String(args[3]);        
                    Cipher cipherSign = (Cipher) CipherFactory.getInstance("SignGen");                    
                    String msgSign = cipherSign.perform(args[1].getBytes(), privFileName);                    
                    System.out.println(cipher.perform(msgSign.getBytes(), args[2].getBytes(), pubFileName));
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
