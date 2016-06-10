/*
*
*/
package bc.bind;

import bc.bind.model.Block;
import bc.bind.model.Derivative;
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
            
            Block block = new Block();
            
            Derivative derivative = new Derivative();
            
            block.create(derivative);
            bd.toStream(block.build());
            
            Operation op = new Operation();
            
            block.create(op);
            bd.toStream(block.build());

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
