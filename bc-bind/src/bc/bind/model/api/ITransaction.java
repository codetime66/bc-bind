/*
 *
 */
package bc.bind.model.api;

import javax.json.JsonObjectBuilder;

/**
 *
 * @author codetime
 */
public interface ITransaction {
    public JsonObjectBuilder create() throws Exception;
}
