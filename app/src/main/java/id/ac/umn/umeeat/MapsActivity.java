package id.ac.umn.umeeat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import id.ac.umn.umeeat.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private UiSettings mapUISet;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private Button btnkirim, btnkembali;
    protected String strlongitude = "", strlatitude = "", msg = "";
    private User me;
    private String friendname;
    private DatabaseReference chatRef;
    private int itemcount;
    private String chatID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = new Bundle();

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        chatID = (String) getIntent().getSerializableExtra("ChatID");
        chatRef = db.getReference("Chat").child(chatID);

        me = (User) getIntent().getSerializableExtra("me");
        friendname = (String) getIntent().getSerializableExtra("friendname");
        itemcount = (int) getIntent().getSerializableExtra("itemcount");

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

//        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PackageManager.PERMISSION_GRANTED);
//        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            return;
        }

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5, 1000, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.addMarker(new MarkerOptions().position(loc).title("Location"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 20));
                strlatitude = String.valueOf(location.getLatitude());
                strlongitude = String.valueOf(location.getLongitude());


//                b.putString("lat", strlatitude);
//                b.putString("lon", strlongitude);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        });
//        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5, 1000, locationListener);


        btnkembali = findViewById(R.id.btnmapkembali);
        btnkembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
//
        btnkirim = findViewById(R.id.btnmapkirim);
        btnkirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference newChat;
                String newchat = me.getUname()+":Maps:" + "longitude:" + strlongitude + ":latitude:" + strlatitude;
                newChat = chatRef.child(itemcount+"");
                newChat.setValue(newchat);
//                chatActivity.listMessage.add(me.getUname()+":Maps:" + "longitude:" + strlongitude + " latitude:" + strlatitude);
//                Intent intent = new Intent(MapsActivity.this, ChatActivity.class);
//
//                intent.putExtras(b);
//                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mapUISet = mMap.getUiSettings();
        mapUISet.setZoomControlsEnabled(true);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            return;
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
        }
        mMap.setMyLocationEnabled(true);
    }

}