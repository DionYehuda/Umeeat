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
    private User me;
    private UserDAO dao = new UserDAO();
    public static List<User> items = new ArrayList<>();
    SearchAdapter adapter = new SearchAdapter(items);
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
                Log.d("Users", "User: "+user.getUname());
                adapter.notifyDataSetChanged();
            });
        });

        toProfile.setOnClickListener(view -> {
            Intent intent = new Intent(SearchActivity.this, ProfileActivity.class);
            intent.putExtra("myUser", me);
            arl.launch(intent);
        });

        RecyclerView recyclerView = findViewById(R.id.searchRecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager (this));

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
                Toast.makeText(SearchActivity.this, "Mohon pilih jenis kelamin", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.Male:
                dao.searchUserByGender(me.getUname(), user ->
                {
                    items.add(user);
                    Log.d("Users", "User: "+user.getUname());
                    adapter.notifyDataSetChanged();
                }, "Laki-Laki");
                return true;

            case R.id.Female:
                dao.searchUserByGender(me.getUname(), user ->
                {
                    items.add(user);
                    Log.d("Users", "User: "+user.getUname());
                    adapter.notifyDataSetChanged();
                }, "Perempuan");
                return true;

//                Untuk angkatan
            case R.id.Angkatan:
                Toast.makeText(SearchActivity.this, "Mohon pilih Angkatan ", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.tahun2017:
                dao.searchUserByAngkatan(me.getUname(), user ->
                {
                    items.add(user);
                    Log.d("Users", "User: "+user.getUname());
                    adapter.notifyDataSetChanged();
                }, "2017");
                return true;

            case R.id.tahun2018:
                dao.searchUserByAngkatan(me.getUname(), user ->
                {
                    items.add(user);
                    Log.d("Users", "User: "+user.getUname());
                    adapter.notifyDataSetChanged();
                }, "2018");
                return true;

            case R.id.tahun2019:
                dao.searchUserByAngkatan(me.getUname(), user ->
                {
                    items.add(user);
                    Log.d("Users", "User: "+user.getUname());
                    adapter.notifyDataSetChanged();
                }, "2019");
                return true;

            case R.id.tahun2020:
                dao.searchUserByAngkatan(me.getUname(), user ->
                {
                    items.add(user);
                    Log.d("Users", "User: "+user.getUname());
                    adapter.notifyDataSetChanged();
                }, "2020");
                return true;

            case R.id.tahun2021:
                dao.searchUserByAngkatan(me.getUname(), user ->
                {
                    items.add(user);
                    Log.d("Users", "User: "+user.getUname());
                    adapter.notifyDataSetChanged();
                }, "2021");
                return true;

            case R.id.tahun2022:
                dao.searchUserByAngkatan(me.getUname(), user ->
                {
                    items.add(user);
                    Log.d("Users", "User: "+user.getUname());
                    adapter.notifyDataSetChanged();
                }, "2022");
                return true;

                // Untuk jurusan
            case R.id.Jurusan:
                Toast.makeText(SearchActivity.this, "Mohon pilih Fakultas", Toast.LENGTH_SHORT).show();

            case R.id.FakultasTeknik:
                Toast.makeText(SearchActivity.this, "Mohon pilih Jurusan", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.FakultasBisnis:
                Toast.makeText(SearchActivity.this, "Mohon pilih Jurusan", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.FakultasIlmuKomunikasi:
                Toast.makeText(SearchActivity.this, "Mohon pilih Jurusan", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.FakultasSeni:
                Toast.makeText(SearchActivity.this, "Mohon pilih Jurusan", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.Informatika:
                dao.searchUserByJurusan(me.getUname(), user ->
                {
                    items.add(user);
                    Log.d("Users", "User: "+user.getUname());
                    adapter.notifyDataSetChanged();
                }, "Informatika");
                return true;

            case R.id.TeknikKomputer:
                dao.searchUserByJurusan(me.getUname(), user ->
                {
                    items.add(user);
                    Log.d("Users", "User: "+user.getUname());
                    adapter.notifyDataSetChanged();
                }, "Teknik Komputer");
                return true;

            case R.id.TeknikElektro:
                dao.searchUserByJurusan(me.getUname(), user ->
                {
                    items.add(user);
                    Log.d("Users", "User: "+user.getUname());
                    adapter.notifyDataSetChanged();
                }, "Teknik Elektro");
                return true;

            case R.id.TeknikFisika:
                dao.searchUserByJurusan(me.getUname(), user ->
                {
                    items.add(user);
                    Log.d("Users", "User: "+user.getUname());
                    adapter.notifyDataSetChanged();
                }, "Teknik Fisika");
                return true;

            case R.id.SistemInformasi:
                dao.searchUserByJurusan(me.getUname(), user ->
                {
                    items.add(user);
                    Log.d("Users", "User: "+user.getUname());
                    adapter.notifyDataSetChanged();
                }, "Sistem Informasi");
                return true;

            case R.id.Perhotelan:
                dao.searchUserByJurusan(me.getUname(), user ->
                {
                    items.add(user);
                    Log.d("Users", "User: "+user.getUname());
                    adapter.notifyDataSetChanged();
                }, "Perhotelan");
                return true;

            case R.id.Akuntansi:
                dao.searchUserByJurusan(me.getUname(), user ->
                {
                    items.add(user);
                    Log.d("Users", "User: "+user.getUname());
                    adapter.notifyDataSetChanged();
                }, "Akuntansi");
                return true;

            case R.id.Managemen:
                dao.searchUserByJurusan(me.getUname(), user ->
                {
                    items.add(user);
                    Log.d("Users", "User: "+user.getUname());
                    adapter.notifyDataSetChanged();
                }, "Manajemen");
                return true;

            case R.id.KomunikasiStrategis:
                dao.searchUserByJurusan(me.getUname(), user ->
                {
                    items.add(user);
                    Log.d("Users", "User: "+user.getUname());
                    adapter.notifyDataSetChanged();
                }, "Komunikasi Strategis");
                return true;

            case R.id.Jurnalistik:
                dao.searchUserByJurusan(me.getUname(), user ->
                {
                    items.add(user);
                    Log.d("Users", "User: "+user.getUname());
                    adapter.notifyDataSetChanged();
                }, "Jurnalistik");
                return true;

            case R.id.DKV:
                dao.searchUserByJurusan(me.getUname(), user ->
                {
                    items.add(user);
                    Log.d("Users", "User: "+user.getUname());
                    adapter.notifyDataSetChanged();
                }, "DKV");
                return true;

            case R.id.Arsitektur:
                dao.searchUserByJurusan(me.getUname(), user ->
                {
                    items.add(user);
                    Log.d("Users", "User: "+user.getUname());
                    adapter.notifyDataSetChanged();
                }, "Arsitektur");
                return true;

            case R.id.Film:
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