package jhotel.jhotel_android_anggi;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ASUS on 02/05/2018.
 */

public class LoginRequest extends StringRequest {
    private static final String Login_URL = "http://192.168.1.101:8080/logincust";

    private Map<String, String> params;

    public static final String my_shared_preferences = "my_shared_preferences";
    public static final String session_status = "session_status";


    public LoginRequest(String email, String password,
                        Response.Listener<String> listener) {
        super(Method.POST, Login_URL, listener, null);
        params = new HashMap<>();
        params.put("email",email);
        params.put("password", password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
