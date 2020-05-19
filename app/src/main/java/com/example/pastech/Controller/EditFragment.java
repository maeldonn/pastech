package com.example.pastech.Controller;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.pastech.Model.Tile;
import com.example.pastech.Model.TileManager;
import com.example.pastech.R;



public class EditFragment extends Fragment {
    private static final String TILE = "tile";

    private Tile mTile;
    private TileManager mTileManager;

    private OnFragmentInteractionListener mListener;

    private ImageView okButton;
    private ImageView clearButton;
    private ImageView cancelButton;
    private Spinner spinnerType;
    private EditText editTextNumber;
    private EditText editTextContent;
    private TextView textViewNumber;
    private TextView textViewContent;

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

        mTileManager = new TileManager(getContext());

        okButton = (ImageView) view.findViewById(R.id.ok_button);
        clearButton = (ImageView) view.findViewById(R.id.clear_button);
        cancelButton = (ImageView) view.findViewById(R.id.cancel_button);
        spinnerType = (Spinner) view.findViewById(R.id.spinner_type);
        editTextNumber = (EditText) view.findViewById(R.id.editText_number);
        editTextContent = (EditText) view.findViewById(R.id.editText_content);
        textViewNumber = (TextView) view.findViewById(R.id.textview_number);
        textViewContent = (TextView) view.findViewById(R.id.textview_content);

        initFields();

        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position) {
                    case 1:
                        editTextNumber.setEnabled(true);
                        textViewNumber.setVisibility(View.VISIBLE);
                        editTextContent.setEnabled(true);
                        textViewContent.setVisibility(View.VISIBLE);
                        break;

                    case 2:
                        editTextNumber.setEnabled(true);
                        textViewNumber.setVisibility(View.VISIBLE);
                        editTextContent.setEnabled(false);
                        editTextContent.setText("");
                        textViewContent.setVisibility(View.INVISIBLE);
                        break;

                    case 4:
                        editTextNumber.setEnabled(true);
                        textViewNumber.setVisibility(View.VISIBLE);
                        editTextContent.setEnabled(false);
                        editTextContent.setText("");
                        textViewContent.setVisibility(View.INVISIBLE);
                        break;

                    default:
                        editTextNumber.setEnabled(false);
                        editTextNumber.setText("");
                        textViewNumber.setVisibility(View.INVISIBLE);
                        editTextContent.setEnabled(false);
                        editTextContent.setText("");
                        textViewContent.setVisibility(View.INVISIBLE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = (TextView)spinnerType.getSelectedView();
                String type = textView.getText().toString();
                mTile.setType(type);
                mTile.setNumber(editTextNumber.getText().toString());
                mTile.setContent(editTextContent.getText().toString());
                mTileManager.updateTile(mTile);
                mTileManager.close();
                sendBack(mTile);
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTile.setType("Aucun");
                mTile.setNumber("");
                mTile.setContent("");
                mTileManager.updateTile(mTile);
                mTileManager.close();
                sendBack(mTile);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTileManager.close();
                sendBack(mTile);
            }
        });


        return view;
    }

    public void initFields() {
        switch(mTile.getType()) {
            case "message":
                spinnerType.setSelection(1);
                break;

            case "phone":
                spinnerType.setSelection(2);
                break;

            case "bluetooth":
                spinnerType.setSelection(3);
                break;

            case "sos":
                spinnerType.setSelection(4);
                break;

            default:
                spinnerType.setSelection(0);
                break;
        }
        editTextNumber.setText(mTile.getNumber());
        editTextContent.setText(mTile.getContent());
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
