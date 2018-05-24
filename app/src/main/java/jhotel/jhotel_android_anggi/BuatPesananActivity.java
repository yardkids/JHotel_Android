package jhotel.jhotel_android_anggi;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

public class BuatPesananActivity extends AppCompatActivity {
    private static int currentUserId;
    private static String currentname;
    private static String currentemail;
    private static String currentdob;
    private int banyakHari, idHotel;
    private double tariff;
    private String roomNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buat_pesanan);

        Intent buatPesananIntent = getIntent();
        Bundle b = buatPesananIntent.getExtras();
        if(b!=null){
            currentUserId = b.getInt("id_customer");
            currentname = b.getString("nama");
            currentemail = b.getString("email");
            currentdob = b.getString("dob");
            idHotel = b.getInt("id_hotel");
            tariff = b.getDouble("dailyTariff");
            roomNumber = b.getString("nomor_kamar");
        }

        final TextView textViewRoomNumber = findViewById(R.id.room_number);
        final TextView textViewTariff = findViewById(R.id.tariff);
        final TextView textViewTotalBiaya = findViewById(R.id.total_biaya);
        final EditText durasiInput = findViewById(R.id.durasi_hari);
        final Button pesanButton = findViewById(R.id.pesan);
        final Button hitungButton = findViewById(R.id.hitung);
        pesanButton.setVisibility(View.INVISIBLE);
        textViewRoomNumber.setText(roomNumber);
        textViewTariff.setText(String.valueOf(tariff));
        textViewTotalBiaya.setText("0");

        hitungButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                banyakHari = Integer.parseInt(durasiInput.getText().toString());
                textViewTotalBiaya.setText(String.valueOf(tariff*banyakHari));
                hitungButton.setVisibility(View.INVISIBLE);
                pesanButton.setVisibility(View.VISIBLE);
            }
        });

        pesanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String jumlah_hari = String.valueOf(banyakHari);
                final String id_customer = String.valueOf(currentUserId);
                final String id_hotel = String.valueOf(idHotel);
                final String nomor_kamar = roomNumber;
                Response.Listener<String> responseListener = new Response.Listener<String> () {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            if(jsonResponse!=null) {
                                AlertDialog builder = new AlertDialog.Builder(BuatPesananActivity.this).create();
                                builder.setMessage("Pesan Sukses");
                                builder.show();
                                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent mainInt = new Intent(BuatPesananActivity.this, DashboardActivity.class);
                                        Bundle extras = new Bundle();
                                        extras.putInt("id_customer",currentUserId);
                                        extras.putString("nama",currentname);
                                        extras.putString("email",currentemail);
                                        extras.putString("dob",currentdob);
                                        mainInt.putExtras(extras);
                                        BuatPesananActivity.this.startActivity(mainInt);
                                    }
                                }, 2000);
                            }
                        } catch (JSONException e) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(BuatPesananActivity.this);
                            builder.setMessage("Pesan Gagal.").create().show();
                        }
                    }
                };
                PesananRequest buatPesanRequest = new PesananRequest
                        (jumlah_hari,id_customer,id_hotel,nomor_kamar, responseListener);
                RequestQueue queue = Volley.newRequestQueue(BuatPesananActivity.this);
                queue.add(buatPesanRequest);
            }
        });
    }
}
