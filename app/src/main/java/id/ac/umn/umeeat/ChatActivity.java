package id.ac.umn.umeeat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private Toolbar chtToolbar;
    private EditText etChat;
    public List<String> listSent;
    protected List<String> listReceived;
    LinearLayoutManager linearLayoutManager;
    AdapterChat adapterChat;
    RecyclerView rvChat;
    private String usernameIn, friendname;
    private FrameLayout btnSent, btnGambar, btnMaps;
    private ImageView gambar;
    private User me;
    private DatabaseReference chatRef, chatRoom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Bundle b = getIntent().getExtras();
        friendname = (String) b.get("friendname");
        me = (User) getIntent().getSerializableExtra("me");

        //database
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        chatRef = db.getReference("Chat");


        listReceived = new ArrayList<>();
        listSent = new ArrayList<>();

//        Seed();
        rvChat = findViewById(R.id.rvChat);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvChat.setLayoutManager(linearLayoutManager);
        adapterChat = new AdapterChat(this, listSent, listReceived, me, friendname);
        rvChat.setAdapter(adapterChat);
        etChat = findViewById(R.id.etChat);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle(friendname);
        getChat(user -> {
            adapterChat = new AdapterChat(getApplicationContext(), listSent, listReceived, me, friendname);
            rvChat.setAdapter(adapterChat);
        });

        btnSent = findViewById(R.id.btnSent);
        btnSent.setOnClickListener(view -> {
            if(!etChat.getText().toString().isEmpty()) {
                DatabaseReference newChat;
                String newchat = me.getUname() + ":" + etChat.getText().toString();
                newChat = chatRoom.child(adapterChat.getItemCount()+"");
                newChat.setValue(newchat);
//                listSent.add("me:" + etChat.getText().toString());
                etChat.setText("");
                adapterChat.notifyDataSetChanged();
            }
        });

        gambar = findViewById(R.id.gambar);
        btnGambar = findViewById(R.id.layoutPhoto);
        btnGambar.setOnClickListener(view -> {
            Intent takePictureIntent = new Intent (MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null)
                startActivityForResult(takePictureIntent, 1);
        });

        btnMaps = findViewById(R.id.layoutLocation);
        btnMaps.setOnClickListener(view -> {
            Intent openMapIntent = new Intent(ChatActivity.this, MapsActivity.class);
            startActivity(openMapIntent);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle extras = data.getExtras();
        Bitmap imageBitmap = (Bitmap) extras.get("data");
        adapterChat = new AdapterChat(getApplicationContext(), listSent, listReceived, imageBitmap);
        rvChat.setAdapter(adapterChat);
        adapterChat.notifyDataSetChanged();
//        gambar.setImageBitmap(imageBitmap);
    }

//    protected void Seed(){
//        listSent.add("me:Halo boleh kenalan ga?");
//        listReceived.add("other:Boleh");
//        listSent.add("me:Mau makan bareng ga hari kamis?");
//        listReceived.add("other:boleeh, dimana?");
//        listSent.add("me:di MCD SDC aja oke ga?");
//        listReceived.add("other:okee, hari kamis ya");
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_chat, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            case R.id.otheruserprofile:
                Intent intent = new Intent(ChatActivity.this, OtherUserProfileActivity.class);
                intent.putExtra("friendUname", friendname);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void getChat(UserCallback myCallback){
        chatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listSent.clear();
                HomeActivity.friends.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    String chatId = snap.getKey();
                    if(chatId.equals(me.getUname()+"&"+friendname) || chatId.equals(friendname+"&"+me.getUname()))
                    {
                        chatRoom = snap.getRef();
                        snap.getChildren();
                        for (DataSnapshot snap1 : snap.getChildren())
                        {
                            listSent.add(snap1.getValue(String.class));
                        }
                        myCallback.onCallback(me);
                        return;
                    }
                }
                chatRoom = snapshot.getRef().child(me.getUname()+"&"+friendname);
                myCallback.onCallback(me);
                return;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}