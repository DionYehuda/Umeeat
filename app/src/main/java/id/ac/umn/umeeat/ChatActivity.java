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

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private Toolbar chtToolbar;
    private EditText etChat;
    protected List<String> listSent;
    protected List<String> listReceived;
    LinearLayoutManager linearLayoutManager;
    AdapterChat adapterChat;
    RecyclerView rvChat;
    private String usernameIn;
    private FrameLayout btnSent, btnGambar;
    private ImageView gambar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        listReceived = new ArrayList<>();
        listSent = new ArrayList<>();
        Seed();
        rvChat = findViewById(R.id.rvChat);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvChat.setLayoutManager(linearLayoutManager);
        adapterChat = new AdapterChat(this, listSent, listReceived);
        rvChat.setAdapter(adapterChat);
        adapterChat.notifyDataSetChanged();
        etChat = findViewById(R.id.etChat);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        Bundle b = getIntent().getExtras();
        getSupportActionBar().setTitle("Ayang");

        btnSent = findViewById(R.id.btnSent);
        btnSent.setOnClickListener(view -> {
            if(!etChat.getText().toString().isEmpty()) {
                listSent.add("me:" + etChat.getText().toString());
                etChat.setText("");
                adapterChat = new AdapterChat(getApplicationContext(), listSent, listReceived);
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

    protected void Seed(){
        listSent.add("me:Halo boleh kenalan ga?");
        listReceived.add("other:Boleh");
        listSent.add("me:Mau makan bareng ga hari kamis?");
        listReceived.add("other:boleeh, dimana?");
        listSent.add("me:di MCD SDC aja oke ga?");
        listReceived.add("other:okee, hari kamis ya");
    }

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
}