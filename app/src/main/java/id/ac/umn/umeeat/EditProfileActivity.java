package id.ac.umn.umeeat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EditProfileActivity extends AppCompatActivity {
    EditText etDesc;
    User me;
    Button btnSave;
    UserDAO dao = new UserDAO();
    String descIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        etDesc = findViewById(R.id.acDesc);
        me =(User) getIntent().getSerializableExtra("me");
        etDesc.setText(me.getDesc());
        btnSave = findViewById(R.id.btnDoEdit);
        btnSave.setOnClickListener(view -> {
            descIn = etDesc.getText().toString();
            dao.updateDesc(me.getUname(), descIn);
            me.setDesc(descIn);
            Intent updateIntent = new Intent();
            updateIntent.putExtra("me", me);
            setResult(RESULT_OK, updateIntent);
            finish();
        });
    }

}