package com.lotfi.halel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lotfi.halel.Location.Halel;
import com.lotfi.halel.Location.SavelocActivity;
import com.lotfi.halel.Maps.SearchActivity;
import com.lotfi.halel.Preferences.Preferences;
import com.lotfi.halel.Preferences.Prefs;

import static com.lotfi.halel.R.layout.dialog;

public class MainActivity extends AppCompatActivity {

    public static Preferences cp;
    public static Prefs prefs;
    public static int i = 0;
    private Button button_Login;
    private EditText emailText;
    private EditText passText;
    private TextView txtlog;
    private static final String TAG ="gggg" ;
     AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView txtview=(TextView)findViewById(R.id.textView);
        TextView txtview2=(TextView)findViewById(R.id.textView2);
        Button btn=(Button) findViewById(R.id.button);
        Button btn2=(Button) findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent secondeActivite = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(secondeActivite);
            }  });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent secondeActivite = new Intent(MainActivity.this, SavelocActivity.class);
                startActivity(secondeActivite);
            }  });
        txtview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                login();
            }  });

        txtview2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent secondeActivite = new Intent(MainActivity.this, SubsActivity.class);
                startActivity(secondeActivite);
            }  });

}


    private void login() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);

        View mView = getLayoutInflater().inflate(R.layout.dialog_login , null);


        button_Login = (Button)mView.findViewById(R.id.btnLogin);
        emailText = (EditText)mView.findViewById(R.id.etEmail);
        passText = (EditText)mView.findViewById(R.id.etPassword);
        txtlog=(TextView) mView.findViewById(R.id.textView5);
        mBuilder.setView(mView);
        dialog = mBuilder.create();
        dialog.show();



        button_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logClick();
            }});

    }

    private void logClick() {
        final boolean[] logi = new boolean[1];
        if(!emailText.getText().toString().isEmpty() && !passText.getText().toString().isEmpty()){
                  /*  Toast.makeText(MainActivity.this,
                            R.string.success_login_msg,
                            Toast.LENGTH_SHORT).show();*/
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
            logi[0] =false;
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        final Users user= postSnapshot.getValue(Users.class);
                        if(user.getEmail().equals(emailText.getText())&& user.getmotpass().equals(passText.getText())) logi[0] =true;


                        ;

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Getting Post failed, log a message
                    Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                    // ...
                }
            });
            if (!logi[0])  {txtlog.setText("Erreur login ou password");
            logClick();}
            else dialog.dismiss();

            //dialog.dismiss();

            //      Intent intent = new Intent(MainActivity.this , Main3Activity.class );
            //   startActivity(intent);
        }else{
            Toast.makeText(MainActivity.this,
                    R.string.error_login_msg,
                    Toast.LENGTH_SHORT).show();
        }

    }




}
