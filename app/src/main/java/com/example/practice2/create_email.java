package com.example.practice2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class create_email extends AppCompatActivity {

    FirebaseAuth auth;
    Button submit,reset;
    EditText email,password,name,age1;
    String age[]={"< 17 ", "17-25" , "26-40" , "40<"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_email);
        submit=findViewById(R.id.submit);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        reset=findViewById(R.id.reset);
        name=findViewById(R.id.name);
        age1=findViewById(R.id.age1);

        FirebaseApp.initializeApp(create_email.this);
        auth=FirebaseAuth.getInstance();


        final DatabaseReference[] reference = new DatabaseReference[1];

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String e = email.getText().toString().trim();
                String p = password.getText().toString().trim();
                String n= name.getText().toString().trim();
                String a=age1.getText().toString().trim();



                auth.createUserWithEmailAndPassword(e,p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            reference[0] = FirebaseDatabase.getInstance().getReference();
                            String key=reference[0].getRoot().push().getKey();
                            reference[0].getRoot().child("Data").child(key).child("Name").setValue(n);
                            reference[0].getRoot().child("Data").child(key).child("Age").setValue(a);
                            reference[0].getRoot().child("Data").child(key).child("Email").setValue(e);

                            Toast.makeText(create_email.this, "is succesfull", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(create_email.this, "is failed"+e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });


            }
        });
        age1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog dialog =new AlertDialog.Builder(create_email.this).setSingleChoiceItems(age, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                        Toast.makeText(create_email.this, ""+age[i], Toast.LENGTH_SHORT).show();
                        age1.setText(age[i]);
                    }
                }).setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();
                dialog.show();
            }
        });

    }
}