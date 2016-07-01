/*
 *
 */
package bc.cipher.api;

/**
 *
 * @author codetime
 */
public interface ISignVerify {
   public boolean perform(String sigFile, String original, String pubFileName) throws Exception;    
   public IKeyPairGen getKeyPairGen();   
}
