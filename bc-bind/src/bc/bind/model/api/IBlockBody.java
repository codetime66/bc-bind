/*
 *
 */
package bc.bind.model.api;

import javax.json.JsonObjectBuilder;

/**
 *
 * @author codetime
 */
public interface IBlockBody {
    public JsonObjectBuilder create() throws Exception;
}
