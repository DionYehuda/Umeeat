package id.ac.umn.umeeat;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

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
    private TextView greet;
    private RecyclerView chatListView;
    private ChatAdapter chatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        hmToolbar = findViewById(R.id.hmToolbar);
        hmToolbar.setTitle("Home");
        hmToolbar.setTitleTextColor(getResources().getColor(R.color.black));
        chatListView =findViewById(R.id.chatrecyclerview);

        if(initRun)
        {
            friendUName.add("Ayang");
            lastChat.add("okee, hari kamis ya");
            initRun = false;
        }

        chatAdapter = new ChatAdapter(this, friendUName, lastChat);
        chatListView.setAdapter(chatAdapter);
        chatListView.setLayoutManager(new LinearLayoutManager(this));

//        btnChat = findViewById(R.id.btnChat);
//        btnChat.setOnClickListener(view -> startActivity(new Intent(HomeActivity.this, ChatActivity.class)));

        String userName = getIntent().getStringExtra("MyUsername");
        greet = findViewById(R.id.greeting);
        greet.setText("Hey, "+userName+"!\nWho are you eating with today?");
    }
}
