package in.creativelizard.nongpslocation;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import in.creativelizard.androidpermission.CreativePermission;

public class MainActivity extends AppCompatActivity {

    private TextView tvLocation;
    private LocationManager locationManager;
    private Location location;

    private static final int PERMISSION_ALL = 100;
    private CreativePermission myPermission;
    private String[] PERMISSIONS = {Manifest.permission.ACCESS_COARSE_LOCATION};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        getLocation();
    }

    private void getLocation() {

        if (!CreativePermission.hasPermissions(PERMISSIONS)) {
            myPermission.reqPermisions();
        } else {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }

        if(location!=null) {
            tvLocation.setText("Latitude: "+location.getLatitude()+", "+location.getLongitude());
        }
    }

    private void initialize() {
        myPermission = new CreativePermission(this,PERMISSIONS,PERMISSION_ALL);
        tvLocation = (TextView)findViewById(R.id.tvLocation);
        locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == PERMISSION_ALL) {
            if(grantResults[0]==0){
                getLocation();
            }else {
                Toast.makeText(this, "Permission needed to get Location!!", Toast.LENGTH_SHORT).show();
            }

        }
    }

}
