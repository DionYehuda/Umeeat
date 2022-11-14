package id.ac.umn.umeeat;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

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
    private FrameLayout btnSent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        listReceived = new ArrayList<>();
        listSent = new ArrayList<>();
        Seed();
        rvChat = findViewById(R.id.rvChat);
        linearLayoutManager = new LinearLayoutManager(this, linearLayoutManager.VERTICAL, false);
        rvChat.setLayoutManager(linearLayoutManager);
        adapterChat = new AdapterChat(this, listSent, listReceived);
        rvChat.setAdapter(adapterChat);
        adapterChat.notifyDataSetChanged();
        etChat = findViewById(R.id.etChat);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        Bundle b = getIntent().getExtras();
        getSupportActionBar().setTitle("placeholder");

        btnSent = findViewById(R.id.btnSent);
        btnSent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listSent.add("me:" + etChat.getText().toString());
                etChat.setText("");
                adapterChat = new AdapterChat(getApplicationContext(), listSent, listReceived);
                rvChat.setAdapter(adapterChat);
            }
        });

//        chtToolbar = findViewById(R.id.chtToolbar);
//        chtToolbar.setTitleTextColor(getResources().getColor(R.color.black));
//        chtToolbar.setTitle("Ayang");
//        chtToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_ios_24));
//        chtToolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });

    }
    protected void Seed(){
        listSent.add("me:Halo boleh kenalan ga?");
        listReceived.add("other:Boleh");
        listSent.add("me:Mau makan bareng ga hari kamis?");
        listReceived.add("other:boleeh, dimana?");
        listSent.add("me:di MCD SDC aja oke ga?");
        listReceived.add("other:okee, hari kamis ya");
    }
}