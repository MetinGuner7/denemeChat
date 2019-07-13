package com.example.denemechat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class MainActivity extends AppCompatActivity {

    Button btnGiris, btnUyeOl;
    EditText edKullaniciAdi, edSifre, edMail;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGiris = findViewById(R.id.btnGiris);
        btnUyeOl = findViewById(R.id.btnUyeOl);
        edMail = findViewById(R.id.edKayitMail);
        edKullaniciAdi = findViewById(R.id.edKayitKullaniciAdi);
        edSifre = findViewById(R.id.edSifre);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null){
            Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
            startActivity(intent);
        }
    }

    public void signUp (View view){

        mAuth.createUserWithEmailAndPassword(edMail.getText().toString(), edSifre.getText().toString() )
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();

                            //DisplayNAme eklemek için güncelledik. Kullanıcı adını burda tutmak için
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(""+ edKullaniciAdi.getText().toString())
                                    .setPhotoUri(Uri.parse("https://www.google.com/url?sa=i&source=images&cd=&cad=rja&uact=8&ved=2ahUKEwiW7YTF26fjAhXULc0KHaQ4AlcQjRx6BAgBEAU&url=%2Furl%3Fsa%3Di%26source%3Dimages%26cd%3D%26ved%3D%26url%3Dhttps%253A%252F%252Fforum.oxforddictionaries.com%252Ftn%252Fprofile%252Fbnkhumane858%26psig%3DAOvVaw118lJA0jktsR3tFjchssOJ%26ust%3D1562757457849215&psig=AOvVaw118lJA0jktsR3tFjchssOJ&ust=1562757457849215"))
                                    .build();

                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {

                                            }
                                        }
                                    });

                            Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                            startActivity(intent);
                        }
                    }
                });
    }

    public void singnIn(View view){
        mAuth.signInWithEmailAndPassword(edKullaniciAdi.getText().toString(), edSifre.getText().toString())
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                            startActivity(intent);
                        }

                    }
                });
    }
}
