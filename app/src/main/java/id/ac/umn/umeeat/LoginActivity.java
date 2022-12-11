package id.ac.umn.umeeat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private Button btnLogin;
    private EditText etUsername, etPassword;
    private TextView tvRegister;
    String username;
    String password;
    FirebaseAuth mAuth;
    UserDAO dao = new UserDAO();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = findViewById(R.id.btnDoLogin);
        etUsername = findViewById(R.id.etUname);
        etPassword = findViewById(R.id.etPass);
        tvRegister = findViewById(R.id.tvRegister);
        mAuth = FirebaseAuth.getInstance();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        btnLogin.setOnClickListener(view -> {
            username = etUsername.getText().toString();
            password = etPassword.getText().toString();

            if(username.isEmpty() || password.isEmpty())
                Toast.makeText(LoginActivity.this, "Username dan password tidak boleh kosong!", Toast.LENGTH_SHORT).show();
            else{


                dao.loginIterate(username, password, user -> {
                    if(user != null){
                        mAuth.signInWithEmailAndPassword(user.getEmail(), user.getPass()).addOnCompleteListener(this, task -> {
                            if(task.isSuccessful())
                            {
                                Intent homeact = new Intent(LoginActivity.this, HomeActivity.class);
                                homeact.putExtra("myUser", user);
                                arl.launch(homeact);
                            }
                            else
                            {
                                Toast.makeText(LoginActivity.this, "Gagal Log In, coba lagi dalam beberapa saat.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else
                        Toast.makeText(LoginActivity.this, "Username/ password salah!", Toast.LENGTH_SHORT).show();
                });
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    ActivityResultLauncher<Intent> arl = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    // There are no request codes
                    finish();
                }
            });
}