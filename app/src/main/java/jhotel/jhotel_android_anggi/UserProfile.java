package jhotel.jhotel_android_anggi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UserProfile extends AppCompatActivity {

    private static int currentUserId;
    private static String currentname;
    private static String currentemail;
    private static String currentdob;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Intent idCustIntent = getIntent();
        Bundle b = idCustIntent.getExtras();
        if(b!=null){
            currentUserId = b.getInt("id_customer");
            currentname = b.getString("nama");
            currentemail = b.getString("email");
            currentdob = b.getString("dob");
        }

        final TextView idCust = findViewById(R.id.idUser);
        final TextView namaCust = findViewById(R.id.namaUser);
        final TextView emailCust = findViewById(R.id.emailUser);
        final TextView dobCust = findViewById(R.id.dobUser);
        final Button logoutButton = (Button) findViewById(R.id.buttonLogout);

        idCust.setText(String.valueOf(currentUserId));
        namaCust.setText(currentname);
        emailCust.setText(currentemail);
        dobCust.setText(currentdob);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logout = new Intent(UserProfile.this, LoginActivity.class);
                UserProfile.this.startActivity(logout);
            }
        });
    }
}
