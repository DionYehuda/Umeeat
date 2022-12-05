package id.ac.umn.umeeat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class ProfileActivity extends AppCompatActivity {
    TextView uname, ang, jur, gen, desc;
    String username, angkatan, jurusan, gender, description;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Profile");
        username = getIntent().getStringExtra("MyUsername");
        uname = findViewById(R.id.tvUsername);
        uname.setText(username);

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("data-regis"));
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            username = intent.getStringExtra("uname");
            angkatan = intent.getStringExtra("year");
            jurusan = intent.getStringExtra("jurusan");
            gender = intent.getStringExtra("gender");
            description = intent.getStringExtra("desc");

            Toast.makeText(getApplicationContext(), "test " + username + angkatan, Toast.LENGTH_SHORT).show();

            uname = findViewById(R.id.tvUsername);
            ang = findViewById(R.id.tvAngkatan);
            jur = findViewById(R.id.tvJurusan);
            gen = findViewById(R.id.tvGender);
            desc = findViewById(R.id.tvDesc);

            uname.setText(username);
            ang.setText(angkatan);
            jur.setText(jurusan);
            gen.setText(gender);
            desc.setText(description);
        }
    };

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
                intent = new Intent(id.ac.umn.umeeat.ProfileActivity.this, MainActivity.class);
                Bundle b = new Bundle();
                b.putString("uname", "");
                b.putString("pass", "");

                intent.putExtras(b);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
