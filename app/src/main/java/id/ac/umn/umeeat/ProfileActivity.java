package id.ac.umn.umeeat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {
    TextView uname, ang, jur, gen, desc;
    User me;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Profile");
        me = (User) getIntent().getSerializableExtra("myUser");

        uname = findViewById(R.id.tvUsername);
        ang = findViewById(R.id.tvAngkatan);
        jur = findViewById(R.id.tvJurusan);
        gen = findViewById(R.id.tvGender);
        desc = findViewById(R.id.tvDesc);

        uname.setText(me.getUname());
        ang.setText(me.getYear());
        jur.setText(me.getJurusan());
        gen.setText(me.getGender());
        desc.setText(me.getDesc());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_app, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        Intent intent;
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                Intent logoutIntent = new Intent();
                setResult(RESULT_OK, logoutIntent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
