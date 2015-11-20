import java.io.IOException;

import oanda.ApiTesting;


public class JavaApiTesting {
    public static void main (String[]args) throws IOException {
        Init init = new Init();

        ApiTesting apiTesting = new ApiTesting();
        apiTesting.getPrice();


    }

}
