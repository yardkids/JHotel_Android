package jhotel.jhotel_android_anggi;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class BuatPesananActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buat_pesanan);

        final TextView RoomNumber = (TextView) findViewById(R.id.room_number);
        final TextView Tariff = (TextView) findViewById(R.id.tariff);
        final EditText Durasi = findViewById(R.id.durasi_hari);
        final TextView Total = (TextView) findViewById(R.id.total_biaya);
        final Button Hitung = findViewById(R.id.hitung);
        final Button Pesan = findViewById(R.id.pesan);
        Pesan.setVisibility(View.INVISIBLE);

        final String nomorKamar = RoomNumber.getText().toString();
        final String dailyTariff = Tariff.getText().toString();
        final String jumlahHari = Durasi.getText().toString();
        Total.setText(0);

        Hitung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int a = Integer.parseInt(dailyTariff);
                    int b = Integer.parseInt(jumlahHari);
                    int total = a*b;
                    Total.setText(total);
                }
                catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Input tidak boleh kosong dan harus angka!", Toast.LENGTH_LONG).show();
                }
                Hitung.setVisibility(View.GONE);
                Pesan.setVisibility(View.VISIBLE);
            }
        });

        Pesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> responseListener = new Response.Listener<String> () {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            if(jsonResponse!=null) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(BuatPesananActivity.this);
                                builder.setMessage("Pesanan Sukses")
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(BuatPesananActivity.this);
                            builder.setMessage("Pesanan Failed.")
                                    .create()
                                    .show();
                        }
                    }
                };
                BuatPesananRequest buatPesananRequest = new BuatPesananRequest(jumlahHari, 1, 1, nomorKamar,responseListener);
                RequestQueue queue = Volley.newRequestQueue(BuatPesananActivity.this);
                queue.add(buatPesananRequest);

            }
        });

    }
}
