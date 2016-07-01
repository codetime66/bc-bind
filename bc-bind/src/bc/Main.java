/*
 * 
 * 
 * 
 */
package bc;

import bc.bind.model.Derivative;
import bc.bind.model.Entry;
import bc.bind.model.FinancialInstrument;
import bc.cipher.api.CipherFactory;
import bc.cipher.api.IKeyPairGen;
import bc.cipher.api.IMDGenerator;

/**
 *
 * @author codetime
 */
public class Main {

    static final String KEY_PAIR_GEN = "-k";
    static final String SHORTNAME = "-s";
    static final String ENTRY = "-e";
    static final String RECIPIENT = "-r";
    static final String ISSUER = "-i";
    static final String HELP = "-h";

    public static void main(String[] args) {
        try {
            Main m = new Main();
            if (args.length == 1 && HELP.equals(args[0])) {
                m.printHelpMsg();
            } else if (args.length < 3) {
                System.err.println("Wrong usage. Type \"java bc.Main -h\" to get some help. \n");
                System.exit(1);
            }

            switch (args[0]) {
                case KEY_PAIR_GEN:
                    if (SHORTNAME.equals(args[1])) {
                        m.createKeyPair(args[2]);
                        System.out.println("keys generated.");
                    } else {
                        m.printHelpMsg();
                    }
                    break;
                case ENTRY:
                    if (args.length == 5 && ISSUER.equals(args[1]) && RECIPIENT.equals(args[3])) {
                        m.buildEntry(args[2], args[4]);
                        System.out.println("new entry issued.");
                    } else {
                        m.printHelpMsg();
                    }
                    break;
                default:
                    m.printHelpMsg();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void createKeyPair(String shortname) throws Exception {
        IKeyPairGen keypairGen = (IKeyPairGen) CipherFactory.getInstance("KeyPairGen");
        keypairGen.perform(shortname);
    }

    public String getPrivateKey(String shortname) throws Exception {
        IKeyPairGen keypairGen = (IKeyPairGen) CipherFactory.getInstance("KeyPairGen");
        return keypairGen.readKeyFile("PRIVKEY" + shortname);
    }

    public String getPublicKey(String shortname) throws Exception {
        IKeyPairGen keypairGen = (IKeyPairGen) CipherFactory.getInstance("KeyPairGen");
        return keypairGen.readKeyFile("PUBKEY" + shortname);
    }

    public String getDigest(String original_msg) throws Exception {
        IMDGenerator md = (IMDGenerator) CipherFactory.getInstance("MDGenerator");
        return md.perform(original_msg);
    }

    public void buildEntry(String issuer, String recipient) throws Exception {
        //creating an asset DataSet
        FinancialInstrument financialInstrument = new FinancialInstrument("SWAP",
                "10020.00-1",
                "16D00000001",
                "1000",
                "10",
                "10000",
                "2016-06-17");

        Derivative derivative = new Derivative(financialInstrument,
                "10020.00-1",
                "DI",
                "100",
                "1000",
                "05000.00-5",
                "USD",
                "100",
                "1000");

        //json model toStream
        System.out.println("derivative.toStream: " + derivative.toStream(derivative.build(derivative.create())));

        //building a entry based on a asset DataSet
        System.out.println("Entry:");
        Entry entryAsset = new Entry("10020.00-1-PubKey",
                "datasethash",
                "datasetsign",
                derivative,
                "05000.00-5-PubKey");
        System.out.println("entryAsset.toStream: " + entryAsset.toStream(entryAsset.build(entryAsset.create())));
        //---------------------------------------------

    }

    public String getHelpMsg() {
        StringBuilder sb = new StringBuilder();
        sb.append("usage: java bc.Main <opt> [args] \n");
        sb.append("options: \n");
        sb.append("-h            to show this help \n");
        sb.append("-k            to generate a new pair of keys: \n");
        sb.append("                -s the short name to identify de key pair \n");
        sb.append("-e            to generate a new entry: \n");
        sb.append("                -i the entry issuer \n");
        sb.append("                -r the entry recipient \n");
        return sb.toString();
    }

    public void printHelpMsg() {
        System.out.println(getHelpMsg());
        System.exit(0);
    }
}
