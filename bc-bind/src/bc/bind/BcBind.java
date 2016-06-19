/*
*
 */
package bc.bind;

import bc.bind.model.Block;
import bc.bind.model.Derivative;
import bc.bind.model.FinancialInstrument;
import bc.bind.model.Operation;

/**
 *
 * @author codetime
 */
public class BcBind {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {

            BuildData bd = new BuildData();

            Block block = new Block("002",
                    "001",
                    new String[]{"1", "2", "3"});

            FinancialInstrument fi = new FinancialInstrument("SWAP",
                    "10020.00-1",
                    "16D00000001",
                    "1000",
                    "10",
                    "10000",
                    "2016-06-17");

            Derivative derivative = new Derivative(fi,
                    "10020.00-1",
                    "DI",
                    "100",
                    "1000",
                    "05000.00-5",
                    "USD",
                    "100",
                    "1000");

            block.create(derivative);
            bd.toStream(block.build());

            Operation op = new Operation("0001",
                    "10020.00-1",
                    "05000.00-5",
                    "16D00000001",
                    "SM",
                    "10",
                    "1000",
                    "10000",
                    "2016-06-17");

            block.create(op);
            bd.toStream(block.build());

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
