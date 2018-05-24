package jhotel.jhotel_android_anggi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class SelesaiPesananActivity extends AppCompatActivity {
    private static int currentUserId;
    private static String currentname;
    private static String currentemail;
    private static String currentdob;
    private int id_pesanan;
    private int biaya_akhir;
    private int jumlah_hari;
    private String tanggal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selesai_pesanan);

        Intent selesaiPesananIntent = getIntent();
        Bundle b = selesaiPesananIntent.getExtras();
        if(b!=null){
            currentUserId = b.getInt("id_customer");
            currentname = b.getString("nama");
            currentemail = b.getString("email");
            currentdob = b.getString("dob");
        }

        final Button batal = findViewById(R.id.batal);
        final Button selesai = findViewById(R.id.selesai);

        fetchPesanan();

        batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                final String id = String.valueOf(id_pesanan);

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            if (jsonResponse != null) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(SelesaiPesananActivity.this);
                                builder.setMessage("Pesanan Berhasil Dibatalkan").create().show();
                                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent mainInt = new Intent(SelesaiPesananActivity.this, DashboardActivity.class);
                                        Bundle extras = new Bundle();
                                        extras.putInt("id_customer",currentUserId);
                                        extras.putString("nama",currentname);
                                        extras.putString("email",currentemail);
                                        extras.putString("dob",currentdob);
                                        mainInt.putExtras(extras);
                                        SelesaiPesananActivity.this.startActivity(mainInt);
                                    }
                                }, 2000);
                            }
                        } catch (JSONException e) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(SelesaiPesananActivity.this);
                            builder.setMessage("Pesanan Gagal Dibatalkan.").create().show();
                        }
                    }
                };
                PesananBatalRequest batalRequest = new PesananBatalRequest(id, responseListener);
                RequestQueue queue = Volley.newRequestQueue(SelesaiPesananActivity.this);
                queue.add(batalRequest);
                batal.setVisibility(View.INVISIBLE);
                selesai.setVisibility(View.INVISIBLE);
            }
        });

        selesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                final String id = String.valueOf(id_pesanan);

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            if (jsonResponse != null) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(SelesaiPesananActivity.this);
                                builder.setMessage("Pesanan Berhasil Diselesaikan").create().show();
                                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent mainInt = new Intent(SelesaiPesananActivity.this, DashboardActivity.class);
                                        Bundle extras = new Bundle();
                                        extras.putInt("id_customer",currentUserId);
                                        extras.putString("nama",currentname);
                                        extras.putString("email",currentemail);
                                        extras.putString("dob",currentdob);
                                        mainInt.putExtras(extras);
                                        SelesaiPesananActivity.this.startActivity(mainInt);
                                    }
                                }, 2000);
                            }
                        } catch (JSONException e) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(SelesaiPesananActivity.this);
                            builder.setMessage("Pesanan Gagal Diselesaikan").create().show();
                        }
                    }
                };
                PesananSelesaiRequest selesaiRequest = new PesananSelesaiRequest(id, responseListener);
                RequestQueue queue = Volley.newRequestQueue(SelesaiPesananActivity.this);
                queue.add(selesaiRequest);
                batal.setVisibility(View.INVISIBLE);
                selesai.setVisibility(View.INVISIBLE);
            }
        });
    }

    public void fetchPesanan(){
        final String id_customer = String.valueOf(currentUserId);
        final TextView idPesanan = findViewById(R.id.idPesanan);
        final TextView biaya = findViewById(R.id.biaya);
        final TextView tanggal_pesan = findViewById(R.id.tanggal_pesan);
        final TextView hari = findViewById(R.id.hari);

        Response.Listener<String> responseListener = new Response.Listener<String> () {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonResponse = new JSONObject(response);
                    id_pesanan = jsonResponse.getInt("id");
                    biaya_akhir = jsonResponse.getInt("biaya");
                    jumlah_hari = jsonResponse.getInt("jumlahHari");
                    tanggal = jsonResponse.getString("tanggalPesan");

                    idPesanan.setText(String.valueOf(id_pesanan));
                    biaya.setText(String.valueOf(biaya_akhir));
                    hari.setText(String.valueOf(jumlah_hari));
                    tanggal_pesan.setText(tanggal);
                } catch (JSONException e) {
                    android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(SelesaiPesananActivity.this);
                    builder.setMessage("Belum ada pesanan!");
                    android.support.v7.app.AlertDialog alert = builder.create();
                    alert.show();
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent gagalInt = new Intent(SelesaiPesananActivity.this, DashboardActivity.class);
                            Bundle extras = new Bundle();
                            extras.putInt("id_customer",currentUserId);
                            extras.putString("nama",currentname);
                            extras.putString("email",currentemail);
                            extras.putString("dob",currentdob);
                            gagalInt.putExtras(extras);
                            SelesaiPesananActivity.this.startActivity(gagalInt);
                        }
                    }, 2000);

                }
            }
        };
        PesananFetchRequest fetchPesananRequest = new PesananFetchRequest(id_customer, responseListener);
        RequestQueue queue = Volley.newRequestQueue(SelesaiPesananActivity.this);
        queue.add(fetchPesananRequest);
    }
}

