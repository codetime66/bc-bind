/*
*
 */
package bc.bind;

import bc.bind.model.DataSet;
import bc.bind.model.Derivative;
import bc.bind.model.FinancialInstrument;
import bc.bind.model.Operation;

/**
 *
 * @author codetime
 */
public class EntryBind {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {

            BuildData bd = new BuildData();

            DataSet dataSet = new DataSet("002",
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

            dataSet.create(derivative);
            bd.toStream(dataSet.build());

            Operation op = new Operation("0001",
                    "10020.00-1",
                    "05000.00-5",
                    "16D00000001",
                    "SM",
                    "10",
                    "1000",
                    "10000",
                    "2016-06-17");

            dataSet.create(op);
            bd.toStream(dataSet.build());

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
