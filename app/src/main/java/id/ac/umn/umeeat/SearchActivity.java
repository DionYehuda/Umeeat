package id.ac.umn.umeeat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity  {

    private ImageButton toHome, toSearch, toProfile;
    private ImageView ivCancel;
    private TextView tvFilter;
    private User me;
    private UserDAO dao = new UserDAO();
    public static List<User> items = new ArrayList<>();
    SearchAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setBackgroundDrawable(getDrawable(R.drawable.toolbar_frame));
        me = (User) getIntent().getSerializableExtra("myUser");

        toHome = findViewById(R.id.ibMessage);
        toSearch = findViewById(R.id.ibSearch);
        toProfile = findViewById(R.id.ibProfileView);
        ivCancel = findViewById(R.id.ivCancel);
        tvFilter = findViewById(R.id.tvFilter);

        toHome.setOnClickListener(view -> finish());

//        toSearch.setOnClickListener(view -> {
//            finish();
//            Intent intent = new Intent(SearchActivity.this, SearchActivity.class);
//            intent.putExtra("myUser", me);
//            arl.launch(intent);
//        });

        ivCancel.setOnClickListener(view ->{
            dao.getAllUsers(me.getUname(), user ->
            {
                items.add(user);
                adapter.notifyDataSetChanged();
            });
            tvFilter.setText("...");
        });

        toProfile.setOnClickListener(view -> {
            Intent intent = new Intent(SearchActivity.this, ProfileActivity.class);
            intent.putExtra("myUser", me);
            arl.launch(intent);
        });

        RecyclerView recyclerView = findViewById(R.id.searchRecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager (this));
        adapter = new SearchAdapter(this, items, me);
        recyclerView.setAdapter(adapter);

        dao.getAllUsers(me.getUname(), user ->
        {
            items.add(user);
            Log.d("Users", "User: "+user.getUname());
            adapter.notifyDataSetChanged();
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_filter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
//            Untuk jenis kelamin
            case R.id.Gender:
                tvFilter.setText("Gender");
                Toast.makeText(SearchActivity.this, "Mohon pilih jenis kelamin", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.Male:
                tvFilter.setText("Gender:Male");
                dao.searchUserByGender(me.getUname(), user ->
                {
                    items.add(user);
                    Log.d("Users", "User: "+user.getUname());
                    adapter.notifyDataSetChanged();
                }, "Laki-Laki");
                return true;

            case R.id.Female:
                tvFilter.setText("Gender:Female");
                dao.searchUserByGender(me.getUname(), user ->
                {
                    items.add(user);
                    Log.d("Users", "User: "+user.getUname());
                    adapter.notifyDataSetChanged();
                }, "Perempuan");
                return true;

//                Untuk angkatan
            case R.id.Angkatan:
                tvFilter.setText("Angkatan");
                Toast.makeText(SearchActivity.this, "Mohon pilih Angkatan ", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.tahun2017:
                tvFilter.setText("Angkatan:2017");
                dao.searchUserByAngkatan(me.getUname(), user ->
                {
                    items.add(user);
                    Log.d("Users", "User: "+user.getUname());
                    adapter.notifyDataSetChanged();
                }, "2017");
                return true;

            case R.id.tahun2018:
                tvFilter.setText("Angkatan:2018");
                dao.searchUserByAngkatan(me.getUname(), user ->
                {
                    items.add(user);
                    Log.d("Users", "User: "+user.getUname());
                    adapter.notifyDataSetChanged();
                }, "2018");
                return true;

            case R.id.tahun2019:
                tvFilter.setText("Angkatan:2019");
                dao.searchUserByAngkatan(me.getUname(), user ->
                {
                    items.add(user);
                    Log.d("Users", "User: "+user.getUname());
                    adapter.notifyDataSetChanged();
                }, "2019");
                return true;

            case R.id.tahun2020:
                tvFilter.setText("Angkatan:2020");
                dao.searchUserByAngkatan(me.getUname(), user ->
                {
                    items.add(user);
                    Log.d("Users", "User: "+user.getUname());
                    adapter.notifyDataSetChanged();
                }, "2020");
                return true;

            case R.id.tahun2021:
                tvFilter.setText("Angkatan:2021");
                dao.searchUserByAngkatan(me.getUname(), user ->
                {
                    items.add(user);
                    Log.d("Users", "User: "+user.getUname());
                    adapter.notifyDataSetChanged();
                }, "2021");
                return true;

            case R.id.tahun2022:
                tvFilter.setText("Angkatan:2022");
                dao.searchUserByAngkatan(me.getUname(), user ->
                {
                    items.add(user);
                    Log.d("Users", "User: "+user.getUname());
                    adapter.notifyDataSetChanged();
                }, "2022");
                return true;

                // Untuk jurusan
            case R.id.Jurusan:
                tvFilter.setText("Jurusan");
                Toast.makeText(SearchActivity.this, "Mohon pilih Fakultas", Toast.LENGTH_SHORT).show();

            case R.id.FakultasTeknik:
                tvFilter.setText("Jurusan:Fakultas Teknik dan Informasi");
                Toast.makeText(SearchActivity.this, "Mohon pilih Jurusan", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.FakultasBisnis:
                tvFilter.setText("Jurusan:Fakultas Bisnis");
                Toast.makeText(SearchActivity.this, "Mohon pilih Jurusan", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.FakultasIlmuKomunikasi:
                tvFilter.setText("Jurusan:Fakultas Ilmu Komunikasi");
                Toast.makeText(SearchActivity.this, "Mohon pilih Jurusan", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.FakultasSeni:
                tvFilter.setText("Jurusan: Fakultas Seni dan Budaya");
                Toast.makeText(SearchActivity.this, "Mohon pilih Jurusan", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.Informatika:
                tvFilter.setText("Jurusan: Fakultas Teknik dan Informasi-Informatika");
                dao.searchUserByJurusan(me.getUname(), user ->
                {
                    items.add(user);
                    Log.d("Users", "User: "+user.getUname());
                    adapter.notifyDataSetChanged();
                }, "Informatika");
                return true;

            case R.id.TeknikKomputer:
                tvFilter.setText("Jurusan: Fakultas Teknik dan Informasi-Teknik Komputer");
                dao.searchUserByJurusan(me.getUname(), user ->
                {
                    items.add(user);
                    Log.d("Users", "User: "+user.getUname());
                    adapter.notifyDataSetChanged();
                }, "Teknik Komputer");
                return true;

            case R.id.TeknikElektro:
                tvFilter.setText("Jurusan: Fakultas Teknik dan Informasi-Teknik Elektro");
                dao.searchUserByJurusan(me.getUname(), user ->
                {
                    items.add(user);
                    Log.d("Users", "User: "+user.getUname());
                    adapter.notifyDataSetChanged();
                }, "Teknik Elektro");
                return true;

            case R.id.TeknikFisika:
                tvFilter.setText("Jurusan: Fakultas Teknik dan Informasi-Teknik Fisika");
                dao.searchUserByJurusan(me.getUname(), user ->
                {
                    items.add(user);
                    Log.d("Users", "User: "+user.getUname());
                    adapter.notifyDataSetChanged();
                }, "Teknik Fisika");
                return true;

            case R.id.SistemInformasi:
                tvFilter.setText("Jurusan: Fakultas Teknik dan Informasi-Sistem Informasi");
                dao.searchUserByJurusan(me.getUname(), user ->
                {
                    items.add(user);
                    Log.d("Users", "User: "+user.getUname());
                    adapter.notifyDataSetChanged();
                }, "Sistem Informasi");
                return true;

            case R.id.Perhotelan:
                tvFilter.setText("Jurusan : Fakultas Bisnis-Perhotelan");
                dao.searchUserByJurusan(me.getUname(), user ->
                {
                    items.add(user);
                    Log.d("Users", "User: "+user.getUname());
                    adapter.notifyDataSetChanged();
                }, "Perhotelan");
                return true;

            case R.id.Akuntansi:
                tvFilter.setText("Jurusan : Fakultas Bisnis-Akuntansi");
                dao.searchUserByJurusan(me.getUname(), user ->
                {
                    items.add(user);
                    Log.d("Users", "User: "+user.getUname());
                    adapter.notifyDataSetChanged();
                }, "Akuntansi");
                return true;

            case R.id.Managemen:
                tvFilter.setText("Jurusan : Fakultas Bisnis-Manajemen");
                dao.searchUserByJurusan(me.getUname(), user ->
                {
                    items.add(user);
                    Log.d("Users", "User: "+user.getUname());
                    adapter.notifyDataSetChanged();
                }, "Manajemen");
                return true;

            case R.id.KomunikasiStrategis:
                tvFilter.setText("Jurusan : Fakultas Ilmu Komunikasi-Komunikasi Strategis");
                dao.searchUserByJurusan(me.getUname(), user ->
                {
                    items.add(user);
                    Log.d("Users", "User: "+user.getUname());
                    adapter.notifyDataSetChanged();
                }, "Komunikasi Strategis");
                return true;

            case R.id.Jurnalistik:
                tvFilter.setText("Jususan : Fakultas Ilmu Komunikasi-Jurnalistik");
                dao.searchUserByJurusan(me.getUname(), user ->
                {
                    items.add(user);
                    Log.d("Users", "User: "+user.getUname());
                    adapter.notifyDataSetChanged();
                }, "Jurnalistik");
                return true;

            case R.id.DKV:
                tvFilter.setText("Jurusan : Fakultas Seni dan Budaya-DKV");
                dao.searchUserByJurusan(me.getUname(), user ->
                {
                    items.add(user);
                    Log.d("Users", "User: "+user.getUname());
                    adapter.notifyDataSetChanged();
                }, "DKV");
                return true;

            case R.id.Arsitektur:
                tvFilter.setText("Jurusan : Fakultas Seni dan Budaya-Arsitektur");
                dao.searchUserByJurusan(me.getUname(), user ->
                {
                    items.add(user);
                    Log.d("Users", "User: "+user.getUname());
                    adapter.notifyDataSetChanged();
                }, "Arsitektur");
                return true;

            case R.id.Film:
                tvFilter.setText("Jurusan : Fakultas Seni dan Budaya-Film dan Animasi");
                dao.searchUserByJurusan(me.getUname(), user ->
                {
                    items.add(user);
                    Log.d("Users", "User: "+user.getUname());
                    adapter.notifyDataSetChanged();
                }, "Film dan Animasi");
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    ActivityResultLauncher<Intent> arl = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    // There are no request codes
                    Intent logoutIntent = new Intent();
                    setResult(RESULT_OK, logoutIntent);
                    finish();
                }
            });
}