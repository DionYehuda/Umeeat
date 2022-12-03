package id.ac.umn.umeeat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SearchActivity extends AppCompatActivity  {

    private Toolbar toolbar;
    private ImageButton toHome, toSearch, toProfile;
    private String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getDrawable(R.drawable.toolbar_frame));
        userName = getIntent().getStringExtra("MyUsername");

        toHome = findViewById(R.id.ibMessage);
        toSearch = findViewById(R.id.ibSearch);
        toProfile = findViewById(R.id.ibProfileView);

        toHome.setOnClickListener(view -> {
            Intent intent = new Intent(getBaseContext(), HomeActivity.class);
            intent.putExtra("MyUsername", userName);
            startActivity(intent);
        });

        toSearch.setOnClickListener(view -> {
            Intent intent = new Intent(getBaseContext(), SearchActivity.class);
            intent.putExtra("MyUsername", userName);
            startActivity(intent);
        });

        toProfile.setOnClickListener(view -> {
            Intent intent = new Intent(getBaseContext(), ProfileActivity.class);
            intent.putExtra("MyUsername", userName);
            startActivity(intent);
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
}