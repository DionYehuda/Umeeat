package id.ac.umn.umeeat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class EditProfileActivity extends AppCompatActivity {
    EditText etdesc;
    User me;
    Button btnsave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        etdesc = findViewById(R.id.acDesc);
        me =(User) getIntent().getSerializableExtra("me");
        etdesc.setText(me.getDesc());
        btnsave = findViewById(R.id.btnDoEdit);
//        btnsave.setOnClickListener();
    }

}