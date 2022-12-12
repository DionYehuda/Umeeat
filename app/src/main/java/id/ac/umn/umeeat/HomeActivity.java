package id.ac.umn.umeeat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class HomeActivity extends AppCompatActivity {

//    private static final LinkedList<Chat> friendChats = new LinkedList<>();
    public static final LinkedList<User> friends = new LinkedList<>();
    private static final LinkedList<String> lastChat = new LinkedList<>();
//    private static boolean initRun = true;
    private ImageButton toHome, toSearch, toProfile;
    private TextView greet;
    private RecyclerView chatListView;
    private ChatAdapter chatAdapter;
    private User me;
    private UserDAO dao = new UserDAO();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setBackgroundDrawable(getDrawable(R.drawable.toolbar_frame));
        getSupportActionBar().setTitle("Home");
        chatListView =findViewById(R.id.chatrecyclerview);

        toHome = findViewById(R.id.ibMessage);
        toSearch = findViewById(R.id.ibSearch);
        toProfile = findViewById(R.id.ibProfileView);

        me = (User) getIntent().getSerializableExtra("myUser");

        chatAdapter = new ChatAdapter(this, friends, lastChat, me);
        chatListView.setAdapter(chatAdapter);
        chatListView.setLayoutManager(new LinearLayoutManager(this));

        greet = findViewById(R.id.greeting);
        greet.setText("Hey, "+me.getUname()+"!\nWho are you eating with today?");

//        if(initRun)
//        {
            dao.chatIterate(me.getUname(), user -> {
                friends.add(user);
                lastChat.add("okee, hari kamis ya");
                chatAdapter.notifyDataSetChanged();
            });
//        }

//        toHome.setOnClickListener(view -> {
//            finish();
//            Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
//            intent.putExtra("myUser", me);
//            arl.launch(intent);
//        });

        toSearch.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
            intent.putExtra("myUser", me);
            arl.launch(intent);
        });

        toProfile.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
            intent.putExtra("myUser", me);
            arl.launch(intent);
        });
    }

    ActivityResultLauncher<Intent> arl = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    // There are no request codes
                    Intent logoutIntent = new Intent();
                    setResult(RESULT_OK, logoutIntent);
                    finish();
                }
            });
}
