package com.example.pastech.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.pastech.Model.Tile;
import com.example.pastech.Model.TileManager;
import com.example.pastech.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SettingsFragment.OnFragmentInteractionListener {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentContainer = (FrameLayout) findViewById(R.id.fragment_container);
        settingsButton = (ImageView) findViewById(R.id.settings_button);
        tile1 = (ImageView) findViewById(R.id.tile_1);
        tile2 = (ImageView) findViewById(R.id.tile_2);
        tile3 = (ImageView) findViewById(R.id.tile_3);
        tile4 = (ImageView) findViewById(R.id.tile_4);
        tile5 = (ImageView) findViewById(R.id.tile_5);
        tile6 = (ImageView) findViewById(R.id.tile_6);

        mTileManager = new TileManager(this);
        mTileList = new ArrayList<>();

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
                mTileList.get(0).onClickAction();
            }
        });

        tile2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTileList.get(1).onClickAction();
            }
        });

        tile3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTileList.get(2).onClickAction();
            }
        });

        tile4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTileList.get(3).onClickAction();
            }
        });

        tile5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTileList.get(4).onClickAction();
            }
        });

        tile1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTileList.get(5).onClickAction();
            }
        });
    }

    public void init() {
        if (mTileManager.isEmpty()) {
            for (int i = 1 ; i <= 6 ; i++) {
                mTileList.add(new Tile(i,"empty","",""));
            }
            for (Tile tile : mTileList) {
                mTileManager.insertTile(tile);
            }

        } else {
            mTileList = mTileManager.getListOfTiles();
        }
        setTileImages();
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

    public void openFragment(ArrayList<Tile> tileList) {
        SettingsFragment fragment = SettingsFragment.newInstance(tileList);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.addToBackStack(null);
        transaction.add(R.id.fragment_container, fragment, "SETTINGS_FRAGMENT").commit();
    }


    @Override
    public void onFragmentInteraction(ArrayList<Tile> sendBackList) {
        mTileList = sendBackList;
        onBackPressed();
    }
}
