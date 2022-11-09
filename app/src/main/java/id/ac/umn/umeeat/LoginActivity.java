package id.ac.umn.umeeat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class LoginActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Button btnLogin;
    private EditText etUsername, etPassword;
    private TextView tvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = findViewById(R.id.btnLogin);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        tvRegister = findViewById(R.id.tvRegister);
        toolbar = findViewById(R.id.lgnToolbar);

        toolbar.setTitleTextColor(getResources().getColor(R.color.black));
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.arrow_back_hitam));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                if(username.isEmpty() || password.isEmpty()){
                    Toast.makeText(LoginActivity.this,
                            "Username dan password tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }else{
                    if(username.equals("Dion") && password.equals("12345")){
                        Intent homeact = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(homeact);
                    }else{
                        Toast.makeText(LoginActivity.this, username+password, Toast.LENGTH_SHORT).show();
                    }
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

}
