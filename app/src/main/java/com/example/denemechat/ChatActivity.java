package com.example.denemechat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class ChatActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    EditText edMessage;
    ArrayList<Chat> chatMessages = new ArrayList<Chat>();
    FirebaseDatabase database;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        edMessage = findViewById(R.id.edMesajYaz);
        recyclerView = findViewById(R.id.recyclerview_chat);
        recyclerViewAdapter = new RecyclerViewAdapter(chatMessages);



        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerViewAdapter);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();

        getData();
    }

    public void sendMessage(View view){
        String messageSend = edMessage.getText().toString();

        //databaseReference.child("Chat 1").child("Test").setValue(messageSend);

        UUID uuıd = UUID.randomUUID();
        String key = uuıd.toString();

        FirebaseUser user = mAuth.getCurrentUser();
        String userMail = user.getEmail();
        String userName = user.getDisplayName();
        String userImageUrl = user.getPhotoUrl().toString();



        databaseReference.child("Chats").child(key).child("email").setValue(userMail);
        databaseReference.child("Chats").child(key).child("userMassage").setValue(messageSend);
        databaseReference.child("Chats").child(key).child("userName").setValue(userName);
        databaseReference.child("Chats").child(key).child("userImageUrl").setValue(userImageUrl);
        databaseReference.child("Chats").child(key).child("userMessageTime").setValue(ServerValue.TIMESTAMP);

        getData();

        edMessage.setText("");
    }

    public void getData(){
        DatabaseReference myRef = database.getReference("Chats");
        Query query = myRef.orderByChild("userMessageTime");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chatMessages.clear();

                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    HashMap<String, String> hashMap = (HashMap<String, String>) ds.getValue();
                    String eMail = hashMap.get("email");
                    String userMassage = hashMap.get("userMassage");
                    String userName = hashMap.get("userName");
                    String imgURL = mAuth.getCurrentUser().getPhotoUrl().toString();
                    //String messageTime = hashMap.get("userMessageTime").toString();
                    Chat yeniChat = new Chat(userName, userMassage,eMail,imgURL);

                    chatMessages.add(yeniChat);
                    recyclerViewAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),databaseError.getMessage().toString(),Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.option_menu, menu);



        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.option_menu_sign_out){

            mAuth.signOut();
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }else if (item.getItemId() == R.id.option_menu_profil){

            Intent intent = new Intent(getApplicationContext(),ProfilActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


}
