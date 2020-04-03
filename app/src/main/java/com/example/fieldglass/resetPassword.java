package com.example.fieldglass;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


public class resetPassword extends AppCompatActivity {

    private Button Reset;
    private EditText ResetEmail;
    private FirebaseAuth Auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        //Find attributes
        Reset= findViewById(R.id.Reset);
        ResetEmail = findViewById(R.id.ResetEmail);
        Auth = FirebaseAuth.getInstance();

        Reset.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Get Values
                String email = ResetEmail.getText().toString().trim();

                //Check for entered email address
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter your email!", Toast.LENGTH_SHORT).show();
                    return;
                }

                else if (!TextUtils.isEmpty(email)){
                    //Email Entered
                    Auth.sendPasswordResetEmail(email)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(getApplicationContext(), "Email sent", Toast.LENGTH_SHORT).show();
                                        finish();
                                        return;
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(), "Error, Please Re-enter email", Toast.LENGTH_SHORT).show();
                                }
                            }
                    });
                }
            }
        }));
    }
}
