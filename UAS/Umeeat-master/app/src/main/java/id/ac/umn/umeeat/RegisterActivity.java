package id.ac.umn.umeeat;

import static android.content.Intent.ACTION_SEND;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity {
    AutoCompleteTextView year, jurusan, gender;
    TextInputEditText email, pass, nama, uname, desc;
    Button buttonRegis;
    String[] dd1, dd2, dd3;
    String yearIn, jurusanIn, genderIn, emailIn, passIn, namaIn, unameIn, descIn;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Register");

        year = findViewById(R.id.acAngkatan);
        dd1 = getResources().getStringArray(R.array.angkatan);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, R.layout.dropdown_menu, dd1);
        year.setAdapter(adapter1);

        year.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                yearIn = year.getText().toString();
            }
        });

        jurusan = findViewById(R.id.acJurusan);
        dd2 = getResources().getStringArray(R.array.jurusan);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, R.layout.dropdown_menu, dd2);
        jurusan.setAdapter(adapter2);

        jurusan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                jurusanIn = jurusan.getText().toString();
            }
        });

        gender = findViewById(R.id.acGender);
        dd3 = getResources().getStringArray(R.array.gender);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, R.layout.dropdown_menu, dd3);
        gender.setAdapter(adapter3);

        gender.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                genderIn = gender.getText().toString();
            }
        });

        email = findViewById(R.id.acEmail);
        pass = findViewById(R.id.acPass);
        nama = findViewById(R.id.acNama);
        uname = findViewById(R.id.acUname);
        desc = findViewById(R.id.acDesc);
        buttonRegis = findViewById(R.id.btnDoRegis);

        buttonRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailIn = email.getText().toString();
                passIn = pass.getText().toString();
                namaIn = nama.getText().toString();
                unameIn = uname.getText().toString();
                descIn = desc.getText().toString();

                if(emailIn.isEmpty() || passIn.isEmpty() || namaIn.isEmpty() || yearIn.isEmpty() || jurusanIn.isEmpty() || unameIn.isEmpty() || descIn.isEmpty() || genderIn.isEmpty())
                    Toast.makeText(RegisterActivity.this, "Data input tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                else {
                    Intent toLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                    // Intent toApp = new Intent(RegisterActivity.this, ProfileActivity.class);
                    Intent toApp = new Intent("data-regis");

                    Bundle b = new Bundle();
                    b.putString("email", emailIn);
                    b.putString("pass", passIn);
                    b.putString("nama", namaIn);
                    b.putString("year", yearIn);
                    b.putString("jurusan", jurusanIn);
                    b.putString("uname", unameIn);
                    b.putString("desc", descIn);
                    b.putString("gender", genderIn);

                    toLogin.putExtras(b);
                    toApp.putExtras(b);
                    LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(toApp);
                    startActivity(toLogin);
                }
            }
        });
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
