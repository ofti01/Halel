package com.lotfi.halel;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.os.Handler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lotfi-pc on 04/08/2017.
 */
public class SubsActivity extends AppCompatActivity {
    private Handler handler;
    private Dialog progressDialog;
     Button but1,but2;
    EditText edit1,edit2,edit3,edit4;
    private FirebaseDatabase mFirebaseDatabse;
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe);
        FirebaseApp.initializeApp(this);
        mFirebaseDatabse = FirebaseDatabase.getInstance();
        mDatabaseReference=mFirebaseDatabse.getReference();
        but1=(Button)findViewById(R.id.button5);
        ImageButton imgbut=(ImageButton)findViewById(R.id.imageButton2);
        imgbut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                finish();
            }
        });
        final TextView txt=(TextView) findViewById(R.id.textView10);
        but2=(Button)findViewById(R.id.button6);
        edit1=(EditText)findViewById(R.id.editText);
        edit2=(EditText)findViewById(R.id.editText2);
        edit3=(EditText)findViewById(R.id.editText3);
        edit4=(EditText)findViewById(R.id.editText4);
       but1.setBackground(getResources().getDrawable(R.drawable.btncolor));
       edit1.requestFocus(2);
     // edit1.setSelection(2); edit2.setSelection(2);
    //  edit3.setSelection(2); edit4.setSelection(2);
       but2.setBackground(getResources().getDrawable(R.drawable.btncolor2));
        but2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent secondeActivite = new Intent(SubsActivity.this, MainActivity.class);
                startActivity(secondeActivite);
            }
        });

        but1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                   txt.setText("");
                if(!validatepseudo(edit1.getText().toString())) {txt.append("** Pseudo est plus petit");
                    txt.append("\n");};
                if(!validatepassword(edit2.getText().toString(),edit3.getText().toString())) {txt.append("** Vérifier Password");
                    txt.append("\n");};
                if(!validateEmailAddress(edit4.getText().toString())) {txt.append("** Vérifier adresse Email");
                txt.append("\n");}


                if(validatepseudo(edit1.getText().toString()) && validatepassword(edit2.getText().toString(),edit3.getText().toString()) && validateEmailAddress(edit4.getText().toString()) )new MyNewTask().execute(2);
            }
        });


    }

    private void createUser()
    {


        final String pseudoo = edit1.getText().toString();
        final String email = edit4.getText().toString();
        mDatabaseReference.child("Users").orderByChild("pseudo").equalTo(pseudoo).addListenerForSingleValueEvent(
                new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot1) {

                        Log.i("SubsActivity", "dataSnapshot value = " + dataSnapshot1.getValue());

                        if (dataSnapshot1.exists()) {

                            // User Exists
                            // Do your stuff here if user already exists
                            Toast.makeText(getApplicationContext(), "Pseudo est déja existe !", Toast.LENGTH_SHORT).show();


                        } else {
                            mDatabaseReference.child("Users").orderByChild("email").equalTo(email).addListenerForSingleValueEvent(
                                    new ValueEventListener() {

                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {

                                            Log.i("SubsActivity", "dataSnapshot value = " + dataSnapshot.getValue());

                                            if (dataSnapshot.exists()) {

                                                // Email exists

                                                Toast.makeText(getApplicationContext(), "Email est déja existe !.", Toast.LENGTH_SHORT).show();


                                            } else {
Users user = new Users(edit1.getText().toString(),edit4.getText().toString(),edit2.getText().toString());
        mDatabaseReference.child("Users").child(user.getpseudo()).setValue(user);
                                                clearText();
                                                 Intent intent = new Intent(SubsActivity.this, MainActivity.class);
                                                SubsActivity.this.startActivity(intent);
                                                Toast.makeText(getApplicationContext(), "Inscription réussie", Toast.LENGTH_LONG).show();

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



    }

    private void clearText(){
        edit1.setText("");
        edit2.setText("");
        edit3.setText("");
        edit4.setText("");
    }

    private boolean validateEmailAddress(String emailAddress){
        String  expression="^[\\w\\-]([\\.\\w])+[\\w]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = emailAddress;
        Pattern pattern = Pattern.compile(expression,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        return matcher.matches();
    }

    private boolean validatepseudo(String str){
        if (str.length()>=6) return true;
        else return false;
    }
    private boolean validatepassword(String str,String srt){
        if (str.length()>=6&& srt.length()>=6){ if (str.equals(srt)) return true;
                                                else return false;}
        else return false;
    }

    private class MyNewTask extends AsyncTask<Integer,String,String>
    {
        @Override
        protected void onPreExecute() {
            progressDialog = new Dialog(SubsActivity.this,R.style.progress_dialog);
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

                String m = progress % 2 == 0 ? "pharmacies tun" : "contact";

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
}