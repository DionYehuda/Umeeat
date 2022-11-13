package id.ac.umn.umeeat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class HomeActivity extends AppCompatActivity {

    private Toolbar hmToolbar;
    private Button btnChat;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        hmToolbar = findViewById(R.id.hmToolbar);
        hmToolbar.setTitle("Home");
        hmToolbar.setTitleTextColor(getResources().getColor(R.color.black));
        btnChat = findViewById(R.id.btnChat);
        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, ChatActivity.class));
            }
        });
    }
}
