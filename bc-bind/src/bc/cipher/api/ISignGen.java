/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bc.cipher.api;

/**
 *
 * @author codetime
 */
public interface ISignGen {
   public String perform(String s_message, String keyFileName) throws Exception;    
   public IKeyPairGen getKeyPairGen();   
}
