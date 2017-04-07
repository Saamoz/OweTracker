package com.saamoz.owetracker;

public class Owe {
    private int _id;
    private int _value;
    private String _desc;

    public Owe(String _desc, int _value) {
        this._desc = _desc;
        this._value = _value;
    }

    public int get_id() {
        return _id;
    }

    public int get_value() {
        return _value;
    }

    public String get_desc() {
        return _desc;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void set_value(int _value) {
        this._value = _value;
    }

    public void set_desc(String _desc) {
        this._desc = _desc;
    }
}
