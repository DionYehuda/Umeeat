package id.ac.umn.umeeat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class HomeActivity extends AppCompatActivity {

    private static final LinkedList<String> friendUName = new LinkedList<>();
    private static final LinkedList<String> lastChat = new LinkedList<>();
    private static boolean initRun = true;
    private Toolbar hmToolbar;
    private Button btnChat;
    private ImageButton toHome, toSearch, toProfile;
    private TextView greet;
    private RecyclerView chatListView;
    private ChatAdapter chatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle("Home");
        getSupportActionBar().hide();
        hmToolbar = findViewById(R.id.hmToolbar);
        hmToolbar.setTitle("Home");
        hmToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        chatListView =findViewById(R.id.chatrecyclerview);

        toHome = findViewById(R.id.ibMessage);
        toSearch = findViewById(R.id.ibSearch);
        toProfile = findViewById(R.id.ibProfileView);

        if(initRun)
        {
            friendUName.add("Ayang");
            lastChat.add("okee, hari kamis ya");
            initRun = false;
        }

        chatAdapter = new ChatAdapter(this, friendUName, lastChat);
        chatListView.setAdapter(chatAdapter);
        chatListView.setLayoutManager(new LinearLayoutManager(this));

        String userName = getIntent().getStringExtra("MyUsername");
        greet = findViewById(R.id.greeting);
        greet.setText("Hey, "+userName+"!\nWho are you eating with today?");

        toHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        toSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        toProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
    }
}
