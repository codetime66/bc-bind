/*
 *
 */
package bc.bind.model.api;

import javax.json.JsonObjectBuilder;

/**
 *
 * @author codetime
 */
public interface IDataSet {
    public JsonObjectBuilder create() throws Exception;
}
