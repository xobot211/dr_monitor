package com.example.dr_monitor;

import android.text.Editable;

public class DataClass implements Comparable<DataClass> {
    public String id;
    public String name;
    public String time;
    public String time_sort;
    public int d;
    public int m;

    public DataClass() {
    }

    public int getD() {
        return d;
    }

    public String getName() {
        return name;
    }

    public int getM() {
        return m;
    }

    public DataClass(String id, String name, String time, String time_sort, int d, int m) {
        this.id = id;
        this.name = name;
        this.time=time;
        this.time_sort=time_sort;
        this.d=d;
        this.m=m;

    }

    @Override
    public int compareTo(DataClass o) {
        if (this.getM()>o.getM())return 1;
        else if(this.getM()<o.getM()) return -1;
        else return Integer.compare(this.getD(),o.getD());
    }
}
