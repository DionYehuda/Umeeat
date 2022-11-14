package id.ac.umn.umeeat;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SearchActivity extends AppCompatActivity  {

    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//        toolbar = findViewById(R.id.hmToolbar);
//        toolbar.setTitle("Search");
//        toolbar.setTitleTextColor(getResources().getColor(R.color.black));
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