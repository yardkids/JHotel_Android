package jhotel.jhotel_android_anggi;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

/**
 * Created by ASUS on 03/05/2018.
 */

public class MenuRequest extends StringRequest {
    private static final String Menu_URL = "http://192.168.1.101:8080/vacantrooms";

    public MenuRequest(Response.Listener<String> listener) {
        super(Method.GET, Menu_URL, listener, null);
    }
}
