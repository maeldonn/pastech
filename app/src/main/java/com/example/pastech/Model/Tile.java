package com.example.pastech.Model;

public class Tile {
    private int mPosition;
    private String mType;
    private int mNumber;
    private String mContent;

    public Tile(int position, String type, int number, String content) {
        this.mPosition = position;
        this.mType = type;
        this.mNumber = number;
        this.mContent = content;
    }

    public int getPosition() {
        return mPosition;
    }

    public void setPosition(int position) {
        mPosition = position;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public int getNumber() {
        return mNumber;
    }

    public void setNumber(int number) {
        mNumber = number;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }
}
