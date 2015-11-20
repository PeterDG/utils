package oanda;

import com.jayway.restassured.response.Response;
import util.JSONSupport;
import util.Parameters;
import util.RESTSupport;

/**
 * Created by Peter on 11/7/15.
 */
public class ApiTesting {

    public ApiTesting() {
    }

    public void getPrice(){
        RESTSupport rest = new RESTSupport(Parameters.accessPointHost, Parameters.accessPointPort, Parameters.pricesService);
        Response response = rest.get("Authorization","Bearer "+Parameters.accessPointToken,"instruments",Parameters.queryInstruments);
        JSONSupport json = new JSONSupport(response);
        json.jsonPath.prettyPrint();
    }
}
