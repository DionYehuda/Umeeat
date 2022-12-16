package id.ac.umn.umeeat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegisterActivity extends AppCompatActivity {
    AutoCompleteTextView year, jurusan, gender;
    TextInputEditText email, pass, nama, uname, desc;
    Button buttonRegis;
    String[] dd1, dd2, dd3;
    String uidIn, yearIn, jurusanIn, genderIn, emailIn, passIn, namaIn, unameIn, descIn;
    UserDAO dao = new UserDAO();


    FirebaseAuth mAuth; //add user
    FirebaseUser mUser; //get userid by mUser.getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Register");

        year = findViewById(R.id.acAngkatan);
        dd1 = getResources().getStringArray(R.array.angkatan);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, R.layout.dropdown_menu, dd1);
        year.setAdapter(adapter1);

        year.setOnItemClickListener((parent, view, position, id) -> yearIn = year.getText().toString());

        jurusan = findViewById(R.id.acJurusan);
        dd2 = getResources().getStringArray(R.array.jurusan);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, R.layout.dropdown_menu, dd2);
        jurusan.setAdapter(adapter2);

        jurusan.setOnItemClickListener((parent, view, position, id) -> jurusanIn = jurusan.getText().toString());

        gender = findViewById(R.id.acGender);
        dd3 = getResources().getStringArray(R.array.gender);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, R.layout.dropdown_menu, dd3);
        gender.setAdapter(adapter3);

        gender.setOnItemClickListener((parent, view, position, id) -> genderIn = gender.getText().toString());

        email = findViewById(R.id.acEmail);
        pass = findViewById(R.id.acPass);
        nama = findViewById(R.id.acNama);
        uname = findViewById(R.id.acUname);
        desc = findViewById(R.id.acDesc);
        buttonRegis = findViewById(R.id.btnDoRegis);

        buttonRegis.setOnClickListener(v -> {
            emailIn = email.getText().toString().trim() + "@student.umn.ac.id";
            passIn = pass.getText().toString().trim();
            namaIn = nama.getText().toString().trim();
            yearIn = year.getText().toString();
            jurusanIn = jurusan.getText().toString();
            unameIn = uname.getText().toString().trim();
            descIn = desc.getText().toString().trim();
            genderIn = gender.getText().toString();
            Activity curr = this;
            if (emailIn.isEmpty() || passIn.isEmpty() || namaIn.isEmpty() || yearIn.isEmpty() || jurusanIn.isEmpty() || unameIn.isEmpty() || descIn.isEmpty() || genderIn.isEmpty())
                Toast.makeText(RegisterActivity.this, "Data input tidak boleh kosong!", Toast.LENGTH_SHORT).show();
            else {
                if(unameIn.matches("^[0-9a-zA-Z_]+$")){
                    dao.checkIfUnique(unameIn, unique -> {
                        if(unique)
                        {
                            mAuth.createUserWithEmailAndPassword(emailIn, passIn).addOnCompleteListener(curr, task -> {
                                if (task.isSuccessful()) {
                                    mUser = mAuth.getCurrentUser();
                                    mUser.updateProfile(new UserProfileChangeRequest.Builder().setDisplayName(unameIn).build());
                                    uidIn = mUser.getUid();
                                    if(mUser != null)
                                    {
                                        Toast.makeText(RegisterActivity.this, "Email Verifikasi telah dikirim, cek di student email untuk konfirmasi\n!Kalau gaada di inbox, coba cek spam!", Toast.LENGTH_LONG).show();
                                        mUser.sendEmailVerification().addOnCompleteListener(task1 -> {
                                            if(task1.isSuccessful())
                                            {
                                                User user = new User(emailIn, passIn, namaIn, yearIn, jurusanIn, unameIn, descIn, genderIn);
                                                dao.add(user, uidIn).addOnSuccessListener(succ -> {
                                                    finish();
                                                    FirebaseAuth.getInstance().signOut();
                                                    Intent toLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                                                    startActivity(toLogin);
                                                }).addOnFailureListener(er -> Toast.makeText(RegisterActivity.this, "Unable to add user. Please check and try again", Toast.LENGTH_LONG).show());
                                            }
                                            else
                                            {
                                                overridePendingTransition(0, 0);
                                                finish();
                                                overridePendingTransition(0, 0);
                                                startActivity(getIntent());
                                            }
                                        });
                                    }
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                        else
                        {
                            Toast.makeText(RegisterActivity.this, "Username sudah digunakan", Toast.LENGTH_LONG).show();
                        }
                    });
                }else
                    Toast.makeText(RegisterActivity.this, "Username tidak valid \n Hanya gunakan alphanumeric dan underscore", Toast.LENGTH_LONG).show();
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
}
