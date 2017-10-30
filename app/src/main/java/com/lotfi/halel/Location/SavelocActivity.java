package com.lotfi.halel.Location;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jaouan.compoundlayout.CompoundLayout;
import com.lotfi.halel.MainActivity;
import com.lotfi.halel.Maps.SearchActivity;
import com.lotfi.halel.R;
import com.lotfi.halel.SubsActivity;
import com.lotfi.halel.Users;

/**
 * Created by Lotfi_pc on 23/08/2017.
 */

public class SavelocActivity extends AppCompatActivity  {


    private Dialog progressDialog;
    EditText edit1,edit2,edit3,edit4;
    private FirebaseDatabase mFirebaseDatabse;
    private DatabaseReference mDatabaseReference;
    EditText edtxt_lat;
    EditText edtxt_long;

    String type="coffee";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savel);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FirebaseApp.initializeApp(this);
        mFirebaseDatabse = FirebaseDatabase.getInstance();
        mDatabaseReference=mFirebaseDatabse.getReference();
        toolbar.setNavigationIcon(getResources().getDrawable(R.mipmap.back_arrow));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Intent intent = getIntent();


        edit1=(EditText) findViewById(R.id.editText2);
         edit2=(EditText) findViewById(R.id.editText8);
         edit3=(EditText) findViewById(R.id.editText5);
        edit4=(EditText) findViewById(R.id.editText7);
        if (intent.getStringExtra("latitude") != null) edit1.setText(intent.getStringExtra("latitude"));
        if (intent.getStringExtra("longitude") != null) edit2.setText(intent.getStringExtra("longitude"));
        TextView txti=(TextView) findViewById(R.id.txti);
        Button btn=(Button)findViewById(R.id.button);
        bindCompoundListener((CompoundLayout) findViewById(R.id.profile_1), R.string.coffee);
        bindCompoundListener((CompoundLayout) findViewById(R.id.profile_2), R.string.resto);
        bindCompoundListener((CompoundLayout) findViewById(R.id.profile_3), R.string.viande);
        bindCompoundListener((CompoundLayout) findViewById(R.id.profile_4), R.string.poulet);
        txti.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent secondeActivite = new Intent(SavelocActivity.this, MapsActivity.class);
                startActivity(secondeActivite);

            } });

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                new MyNewTask().execute(2);
            } });
    }


/*
    public void onClickTrova(View v) {

        Log.v("Latitudine", edtxt_lat.getText().toString());
        Log.v("Longitudine", edtxt_long.getText().toString());

        Toast.makeText(this, "Ricerca coordinate in corso...", Toast.LENGTH_SHORT).show();
        LatLng myCoordinate = new LatLng(Double.valueOf(edtxt_lat.getText().toString()),Double.valueOf(edtxt_long.getText().toString()));
        gMap.addMarker(new MarkerOptions().position(myCoordinate)
                .title("Le tue coordinate"));
        gMap.moveCamera(CameraUpdateFactory.newLatLng(myCoordinate));

    }*/
    private void bindCompoundListener(final CompoundLayout compoundLayout, @StringRes final int subtitle) {
        compoundLayout.setOnCheckedChangeListener(new CompoundLayout.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundLayout compoundLayout, boolean checked) {
                if (checked) {
                    if(subtitle==R.string.coffee) type="coffee";
                    else if (subtitle==R.string.resto) type="resto";
                    else if (subtitle==R.string.viande) type="viande";
                    else  type="resto";

                    final Animation fadeOutAnimation = AnimationUtils.loadAnimation(SavelocActivity.this, android.R.anim.fade_out);
                    fadeOutAnimation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                       /*     if(subtitle==R.string.coffee) type="coffee";
                            else if (subtitle==R.string.resto) type="resto";
                            else if (subtitle==R.string.viande) type="viande";
                            else  type="resto";*/
                         //   subtitleTextView.setText(getString(subtitle));
                          //  descriptionLayout.startAnimation(AnimationUtils.loadAnimation(SavelocActivity.this, android.R.anim.fade_in));
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                        }
                    });
                  //  descriptionLayout.startAnimation(fadeOutAnimation);
                }
            }
        });
    }
    private class MyNewTask extends AsyncTask<Integer,String,String>
    {
        @Override
        protected void onPreExecute() {
            progressDialog = new Dialog(SavelocActivity.this,R.style.progress_dialog);
            progressDialog.setContentView(R.layout.dialog);
            progressDialog.setCancelable(true);
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            TextView msg = (TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg);
            msg.setText("En attente");
            progressDialog.show();
        }


        @Override
        protected String doInBackground(Integer... integers)
        {

            createUser();
            int progress = 0;
            int total = integers[0];

            while (progress <= total) {

                try {

                    Thread.sleep(1000); // 2 seconde

                } catch(InterruptedException e) {

                }

                String m = progress % 2 == 0 ? "pharmacies" : "contact";

                // exibimos o progresso
                this.publishProgress(String.valueOf(progress), String.valueOf(total), m);

                progress++;
            }
            //  createUser();
            return "Done!";
        }

        @Override
        protected void onPostExecute(String result)
        {

            super.onPostExecute(result);
            Log.i("result","" +result);

            if(result!=null)

                progressDialog.dismiss();



        }

    }
    private void createUser()
    {


        final String nomc = edit1.getText().toString();
        final String tel = edit4.getText().toString();
      /*  mDatabaseReference.child("Halal").orderByChild("nomc").equalTo(nomc).addListenerForSingleValueEvent(
                new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot1) {

                        Log.i("SubsActivity", "dataSnapshot value = " + dataSnapshot1.getValue());

                        if (dataSnapshot1.exists()) {

                            // User Exists
                            // Do your stuff here if user already exists
                            Toast.makeText(getApplicationContext(), "Halal est déja existe !", Toast.LENGTH_SHORT).show();


                        } else {
                            mDatabaseReference.child("Users").orderByChild("Tel").equalTo(tel).addListenerForSingleValueEvent(
                                    new ValueEventListener() {

                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {

                                            Log.i("SubsActivity", "dataSnapshot value = " + dataSnapshot.getValue());

                                            if (dataSnapshot.exists()) {

                                                // Email exists

                                                Toast.makeText(getApplicationContext(), "Halal est déja existe !.", Toast.LENGTH_SHORT).show();


                                            } else {*/
                                                Halel hal = new Halel(0,0,edit3.getText().toString(),edit4.getText().toString(),type);
                                                hal.setlati(Double.parseDouble(edit1.getText().toString()));
                                               hal.setlongi(Double.parseDouble(edit2.getText().toString()));
                                                mDatabaseReference.child("halal").child(hal.gettel()).setValue(hal);
                                                mDatabaseReference.child("halal").child(hal.gettel()).setValue(hal);
                                             //   clearText();
                                                Intent intent = new Intent(SavelocActivity.this, MainActivity.class);
                                                SavelocActivity.this.startActivity(intent);
                                             //   Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();
/*
                                            }
                                        }
                                        @Override
                                        public void onCancelled (DatabaseError databaseError){

                                        }
                                    }

                            );

                        }
                    }
                    @Override
                    public void onCancelled (DatabaseError databaseError){

                    }
                }

        );
*/


    }

    private void clearText(){
        edit1.setText("");
        edit2.setText("");
        edit3.setText("");
        edit4.setText("");
    }

}