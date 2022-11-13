package id.ac.umn.umeeat;

import android.os.Bundle;

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
}