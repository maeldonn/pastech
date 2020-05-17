package com.example.pastech.Controller;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.pastech.Model.Tile;
import com.example.pastech.R;



public class EditFragment extends Fragment {
    private static final String TILE = "tile";

    private Tile mTile;

    private OnFragmentInteractionListener mListener;

    private Button okButton;

    public EditFragment() {
        // Required empty public constructor
    }

    public static EditFragment newInstance(Tile tile) {
        EditFragment fragment = new EditFragment();
        Bundle args = new Bundle();
        args.putParcelable(TILE, tile);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTile = getArguments().getParcelable(TILE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit, container, false);

        okButton = (Button) view.findViewById(R.id.ok_button);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendBack(mTile);
            }
        });

        return view;
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

    public void sendBack(Tile sendBackTile) {
        if (mListener != null) {
            mListener.onFragmentInteraction(sendBackTile);
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Tile sendBackTile);
    }
}
