package com.example.pastech.Controller;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.pastech.Model.Tile;
import com.example.pastech.R;

import java.util.ArrayList;


public class SettingsFragment extends Fragment {
    private static final String TILE_LIST = "tileList";

    private ArrayList<Tile> mTileList;

    private OnFragmentInteractionListener mListener;

    private FrameLayout fragmentContainer;
    private ImageView exitButton;
    private ImageView tile1;
    private ImageView tile2;
    private ImageView tile3;
    private ImageView tile4;
    private ImageView tile5;
    private ImageView tile6;

    public SettingsFragment() {
        // Required empty public constructor
    }

    public static SettingsFragment newInstance(ArrayList<Tile> tileList) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(TILE_LIST, tileList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTileList  = getArguments().getParcelableArrayList(TILE_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        fragmentContainer = (FrameLayout) view.findViewById(R.id.edit_fragment_container);
        exitButton = (ImageView) view.findViewById(R.id.exit_button);
        tile1 = (ImageView) view.findViewById(R.id.fragment_tile_1);
        tile2 = (ImageView) view.findViewById(R.id.fragment_tile_2);
        tile3 = (ImageView) view.findViewById(R.id.fragment_tile_3);
        tile4 = (ImageView) view.findViewById(R.id.fragment_tile_4);
        tile5 = (ImageView) view.findViewById(R.id.fragment_tile_5);
        tile6 = (ImageView) view.findViewById(R.id.fragment_tile_6);

        setTileImages();

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendBack(mTileList);
            }
        });

        tile1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment(mTileList.get(0));
            }
        });

        tile2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment(mTileList.get(1));
            }
        });

        tile3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment(mTileList.get(2));
            }
        });

        tile4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment(mTileList.get(3));
            }
        });

        tile5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment(mTileList.get(4));
            }
        });

        tile6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment(mTileList.get(5));
            }
        });

        return view;
    }

    public void sendBack(ArrayList<Tile> sendBackList) {
        if (mListener != null) {
            mListener.onFragmentInteraction(sendBackList);
        }
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
            imageList.get(i).setImageResource(mTileList.get(i).setFragmentTileImage());
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(ArrayList<Tile> sendBackList);
    }

    public void openFragment(Tile tile) {
        EditFragment fragment = EditFragment.newInstance(tile);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.addToBackStack(null);
        transaction.add(R.id.edit_fragment_container, fragment, "EDIT_FRAGMENT").commit();
    }

}
