/*
 *
 */
package bc.cipher.api;

import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 *
 * @author codetime
 */
public interface IKeyPairGen {
   public String[] perform(String fileName) throws Exception;    
   public String savePrivateKey(PrivateKey priv) throws GeneralSecurityException;   
   public String savePublicKey(PublicKey publ) throws GeneralSecurityException;   
   public String base64Encode(byte[] packed);   
   public byte[] base64Decode(String key64);      
   public byte[] base64Decode(byte[] key64);   
   public void writeKeyFile(String key, String fileName) throws Exception;   
   public PublicKey loadPublicKey(String stored) throws GeneralSecurityException;   
   public PrivateKey loadPrivateKey(String key64) throws GeneralSecurityException;   
   public String readKeyFile(String fileName) throws Exception;   
}
