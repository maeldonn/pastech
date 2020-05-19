package com.example.pastech.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.pastech.R;

public class Tile implements Parcelable {
    private int mPosition;
    private String mType;
    private String mNumber;
    private String mContent;

    public Tile(int position, String type, String number, String content) {
        this.mPosition = position;
        this.mType = type;
        this.mNumber = number;
        this.mContent = content;
    }

    public int getPosition() {
        return mPosition;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        switch(type) {
            case "Message":
                mType = "message";
                break;

            case "Appel":
                mType = "phone";
                break;

            case "Bluetooth":
                mType = "bluetooth";
                break;

            case "Urgence":
                mType = "sos";
                break;

            default:
                mType= "empty";
                break;
        }
    }

    public String getNumber() {
        return mNumber;
    }

    public void setNumber(String number) {
        mNumber = number;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public Tile(Parcel in) {
        super();
        readFromParcel(in);
    }

    public static final Parcelable.Creator<Tile> CREATOR = new Parcelable.Creator<Tile>() {
        public Tile createFromParcel(Parcel in) {
            return new Tile(in);
        }

        public Tile[] newArray(int size) {
            return new Tile[size];
        }

    };

    public void readFromParcel(Parcel in) {
        mPosition = in.readInt();
        mType = in.readString();
        mNumber = in.readString();
        mContent = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mPosition);
        dest.writeString(mType);
        dest.writeString(mNumber);
        dest.writeString(mContent);
    }

    public int setTileImage() {
        switch(mType) {
            case "message":
                return R.drawable.message_tile;

            case "phone":
                return R.drawable.phone_tile;

            case "bluetooth":
                return R.drawable.bluetooth_tile;

            case "sos":
                return R.drawable.sos_tile;

            default:
                return R.drawable.empty_tile;
        }
    }

    public int setFragmentTileImage() {
        switch(mType) {
            case "message":
                return R.drawable.message_settings_tile;

            case "phone":
                return R.drawable.phone_settings_tile;

            case "bluetooth":
                return R.drawable.bluetooth_settings_tile;

            case "sos":
                return R.drawable.sos_settings_tile;

            default:
                return R.drawable.empty_settings_tile;
        }
    }
}
