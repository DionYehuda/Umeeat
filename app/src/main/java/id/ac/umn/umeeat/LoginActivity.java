package id.ac.umn.umeeat;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private Button btnLogin;
    private EditText etUsername, etPassword;
    private TextView tvRegister;
    String usernameIn, passwordIn, username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = findViewById(R.id.btnDoLogin);
        etUsername = findViewById(R.id.etUname);
        etPassword = findViewById(R.id.etPass);
        tvRegister = findViewById(R.id.tvRegister);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        Bundle b = getIntent().getExtras();
        usernameIn = b.getString("uname");
        passwordIn = b.getString("pass");

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = etUsername.getText().toString();
                password = etPassword.getText().toString();
//                Intent homeact = new Intent(LoginActivity.this, HomeActivity.class);
//                homeact.putExtra("MyUsername", username);
//                startActivity(homeact);

                if(username.isEmpty() || password.isEmpty())
                    Toast.makeText(LoginActivity.this, "Username dan password tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                else{
                    if(username.equals(usernameIn) && password.equals(passwordIn)){
                        Intent homeact = new Intent(LoginActivity.this, HomeActivity.class);
                        homeact.putExtra("MyUsername", username);
                        startActivity(homeact);
                    }
                    else
                        Toast.makeText(LoginActivity.this, "Username/ password salah!", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(getApplicationContext(), usernameIn + passwordIn, Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}