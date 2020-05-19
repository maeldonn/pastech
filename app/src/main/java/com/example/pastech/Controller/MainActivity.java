package com.example.pastech.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pastech.Model.Tile;
import com.example.pastech.Model.TileManager;
import com.example.pastech.R;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;

import java.util.ArrayList;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

public class MainActivity extends AppCompatActivity implements SettingsFragment.OnFragmentInteractionListener, EditFragment.OnFragmentInteractionListener {

    private FrameLayout fragmentContainer;
    private ImageView settingsButton;
    private ImageView tile1;
    private ImageView tile2;
    private ImageView tile3;
    private ImageView tile4;
    private ImageView tile5;
    private ImageView tile6;

    private ArrayList<Tile> mTileList;
    private TileManager mTileManager;
    private SettingsFragment fragment;
    private Context mContext;
    private LocationRequest mLocationRequest;
    private Location mLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentContainer = (FrameLayout) findViewById(R.id.settings_fragment_container);
        settingsButton = (ImageView) findViewById(R.id.settings_button);
        tile1 = (ImageView) findViewById(R.id.tile_1);
        tile2 = (ImageView) findViewById(R.id.tile_2);
        tile3 = (ImageView) findViewById(R.id.tile_3);
        tile4 = (ImageView) findViewById(R.id.tile_4);
        tile5 = (ImageView) findViewById(R.id.tile_5);
        tile6 = (ImageView) findViewById(R.id.tile_6);

        mTileManager = new TileManager(this);
        mTileList = new ArrayList<>();
        mContext = getApplicationContext();
        mLocationRequest = LocationRequest.create().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY).setInterval(10000).setFastestInterval(1000);

        startLocationUpdates();
        init();

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment(mTileList);
            }
        });

        tile1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmOnClickAction(mTileList.get(0));
            }
        });

        tile2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmOnClickAction(mTileList.get(1));
            }
        });

        tile3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmOnClickAction(mTileList.get(2));
            }
        });

        tile4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmOnClickAction(mTileList.get(3));
            }
        });

        tile5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmOnClickAction(mTileList.get(4));
            }
        });

        tile6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmOnClickAction(mTileList.get(5));
            }
        });
    }

    public void init() {
        if (mTileManager.isEmpty()) {
            for (int i = 1; i <= 6; i++) {
                mTileList.add(new Tile(i, "empty", "", ""));
            }
            for (Tile tile : mTileList) {
                mTileManager.insertTile(tile);
            }
        } else {
            mTileList = mTileManager.getListOfTiles();
        }
        setTileImages();
        mTileManager.close();
    }

    public void setTileImages() {
        ArrayList<ImageView> imageList = new ArrayList<>();
        imageList.add(tile1);
        imageList.add(tile2);
        imageList.add(tile3);
        imageList.add(tile4);
        imageList.add(tile5);
        imageList.add(tile6);

        for (int i = 0; i < 6; i++) {
            imageList.get(i).setImageResource(mTileList.get(i).setTileImage());
        }
    }

    protected void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 3);
            return;
        }

        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(2000);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();
        SettingsClient settingsClient = LocationServices.getSettingsClient(MainActivity.this);
        settingsClient.checkLocationSettings(locationSettingsRequest);

        getFusedLocationProviderClient(MainActivity.this).requestLocationUpdates(mLocationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        // do work here
                        mLocation = locationResult.getLastLocation();
                    }
                },
                Looper.myLooper());
    }

    public void confirmOnClickAction(final Tile tile) {
        // TODO : Résoudre le bug
        if (!tile.getType().equals("empty")) {
            Log.i("TEST", tile.getType());
            final Dialog confirmDialog = new Dialog(MainActivity.this);
            confirmDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            confirmDialog.setContentView(R.layout.confirm_dialog);
            confirmDialog.setTitle("Confirmer ?");

            ImageView dialogOk = (ImageView) confirmDialog.findViewById(R.id.dialog_ok);
            ImageView dialogCancel = (ImageView) confirmDialog.findViewById(R.id.dialog_cancel);

            dialogOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    confirmDialog.cancel();
                    onClickAction(tile);
                }
            });

            dialogCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    confirmDialog.cancel();
                }
            });

            confirmDialog.show();
        }
    }

    public void onClickAction(final Tile tile) {
        switch (tile.getType()) {
            case "message":
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
                    break;
                }
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(tile.getNumber(), null, tile.getContent(), null, null);
                Toast.makeText(mContext, "Message envoyé.", Toast.LENGTH_LONG).show();
                break;

            case "phone":
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + tile.getNumber()));
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 2);
                    break;
                }
                startActivity(callIntent);
                break;

            case "bluetooth":
                Toast.makeText(mContext, "Cette fonction sera disponible prochainement !", Toast.LENGTH_LONG).show();
                break;

            case "sos":
                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS}, 1);
                    break;
                }
                SmsManager smsManager1 = SmsManager.getDefault();
                if (mLocation == null) {
                    smsManager1.sendTextMessage(tile.getNumber(), null, "J'ai besoin d'aide.", null, null);
                } else {
                    smsManager1.sendTextMessage(tile.getNumber(), null, "J'ai besoin d'aide. Je me situe ici :\nLongitude: " + mLocation.getLongitude() + "\nLattitude: " + mLocation.getLatitude(), null, null);
                }
                Toast.makeText(mContext, "Message d'urgence envoyé.", Toast.LENGTH_LONG).show();
                break;

            default:
                break;
        }
    }

    public void openFragment(ArrayList<Tile> tileList) {
        fragment = SettingsFragment.newInstance(tileList);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.addToBackStack(null);
        transaction.add(R.id.settings_fragment_container, fragment, "SETTINGS_FRAGMENT").commit();
    }


    @Override
    public void onFragmentInteraction(ArrayList<Tile> sendBackList) {
        mTileList = sendBackList;
        onBackPressed();
        setTileImages();
    }

    @Override
    public void onFragmentInteraction(Tile sendBackTile) {
        mTileList.set(sendBackTile.getPosition()-1, sendBackTile);
        onBackPressed();
        fragment.setTileImages();
    }
}
