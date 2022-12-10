package id.ac.umn.umeeat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageButton;
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
    private User me;
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

        toHome.setOnClickListener(view -> {
            finish();
        });

//        toSearch.setOnClickListener(view -> {
//            finish();
//            Intent intent = new Intent(SearchActivity.this, SearchActivity.class);
//            intent.putExtra("myUser", me);
//            arl.launch(intent);
//        });

        toProfile.setOnClickListener(view -> {
            Intent intent = new Intent(SearchActivity.this, ProfileActivity.class);
            intent.putExtra("myUser", me);
            arl.launch(intent);
        });

        List<String> items = new ArrayList<>();
        items.add("Male1");
        items.add("Male2");
        items.add("Male3");

        RecyclerView recyclerView = findViewById(R.id.searchRecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager (this));
        SearchAdapter adapter = new SearchAdapter(items);
        recyclerView.setAdapter(adapter);
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
            case R.id.Male:
                Toast.makeText(SearchActivity.this, "algoritma filter", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.Female:
                Toast.makeText(SearchActivity.this, "algoritma filter", Toast.LENGTH_SHORT).show();
                return true;
            default:
                Toast.makeText(SearchActivity.this, "belum ada fungsi", Toast.LENGTH_SHORT).show();
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