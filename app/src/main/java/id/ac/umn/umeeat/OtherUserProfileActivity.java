package id.ac.umn.umeeat;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class OtherUserProfileActivity extends AppCompatActivity {
    TextView uname, ang, jur, gen, desc;
    String username, angkatan, jurusan, gender, description;
    User friend;
    UserDAO dao = new UserDAO();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_user_profile);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Profile");

//        username = "Ayang";
//        angkatan = "2021";
//        jurusan = "Ilmu Komunikasi";
//        gender = "Male";
//        description = "Suka Pedas hehe";

        dao.userIterate(getIntent().getStringExtra("friendUname"), new UserCallback() {
            @Override
            public void onCallback(User user) {
                friend = user;

                uname.setText(friend.getUname());
                ang.setText(friend.getYear());
                jur.setText(friend.getJurusan());
                gen.setText(friend.getGender());
                desc.setText(friend.getDesc());
            }
        });

        uname = findViewById(R.id.tvOtherUsername);
        ang = findViewById(R.id.tvOtherAngkatan);
        jur = findViewById(R.id.tvOtherJurusan);
        gen = findViewById(R.id.tvOtherGender);
        desc = findViewById(R.id.tvOtherDesc);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
