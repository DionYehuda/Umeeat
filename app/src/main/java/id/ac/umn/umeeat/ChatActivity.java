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
import android.widget.Toast;

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
import java.util.Arrays;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private Toolbar chtToolbar;
    private EditText etChat;
    public List<String> listSent;
    protected List<String> listReceived;
    LinearLayoutManager linearLayoutManager;
    AdapterChat adapterChat;
    RecyclerView rvChat;
    private String usernameIn;
    private FrameLayout btnSent, btnGambar, btnMaps;
    private ImageView gambar;
    private User me;
    private DatabaseReference chatRef;
    private String chatID;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        index = 2;

        Bundle b = getIntent().getExtras();
        String friendname = (String) b.get("friendname");
        me = (User) getIntent().getSerializableExtra("me");

        //database
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        chatID = me.getUname() + "&" + friendname;
        if(chatID == null)
            chatID = friendname + "&" + me.getUname();
        chatRef = db.getReference("Chat").child(chatID);


        listReceived = new ArrayList<>();
        listSent = new ArrayList<>();

//        Seed();
        rvChat = findViewById(R.id.rvChat);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvChat.setLayoutManager(linearLayoutManager);
        adapterChat = new AdapterChat(this, listSent, listReceived, me, friendname);
        rvChat.setAdapter(adapterChat);
        adapterChat.notifyDataSetChanged();
        etChat = findViewById(R.id.etChat);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle(me.getUname());
        getChat(me, friendname);


        btnSent = findViewById(R.id.btnSent);
        btnSent.setOnClickListener(view -> {
            if(!etChat.getText().toString().isEmpty()) {
                DatabaseReference newChat;
                String newchat = me.getUname() + ":" + etChat.getText().toString();
                newChat = chatRef.child(index+"");
                newChat.setValue(newchat);
                index++;
//                listSent.add("me:" + etChat.getText().toString());
                etChat.setText("");
                adapterChat = new AdapterChat(getApplicationContext(), listSent, listReceived, me, friendname);
                rvChat.setAdapter(adapterChat);
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
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void getChat(User me, String frienduname){
        chatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    listSent.add(snap.getValue(String.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}