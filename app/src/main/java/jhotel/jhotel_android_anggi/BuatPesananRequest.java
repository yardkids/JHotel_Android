package jhotel.jhotel_android_anggi;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ASUS on 05/05/2018.
 */

public class BuatPesananRequest extends StringRequest {
    private static final String Pesanan_URL = "http://192.168.1.101:8080/bookpesanan";
    private Map<String, String> params;

    public BuatPesananRequest(String jumlah_hari, int id_customer, int id_hotel,
                              String nomor_kamar, Response.Listener<String> listener) {
        super(Method.POST, Pesanan_URL, listener, null);
        params = new HashMap<>();
        params.put("jumlah_hari", jumlah_hari);
        params.put("id_customer", Integer.toString(id_customer));
        params.put("id_hotel", Integer.toString(id_hotel));
        params.put("nomor_kamar", nomor_kamar);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
