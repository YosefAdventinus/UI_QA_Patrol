package com.example.ui_qa_patrol.models;

import java.util.ArrayList;

public class UserDummyList {

    private ArrayList<UserDummy> arrayList = new ArrayList<>();

    public ArrayList<UserDummy> getArrayList() {
        arrayList.add(new UserDummy("K230319MS", "12345", "Yosef Adventinus Agi Supriyanto - K230319MS"));
        arrayList.add(new UserDummy("K230320MS", "12345","Ivan Febrianto - K230320MS"));
        arrayList.add(new UserDummy("K230321MS", "12345", "Ian Putra Ismaya - K230321MS"));
        arrayList.add(new UserDummy("K230322MS", "12345", "Randy Nasuta - K230322MS"));

        return arrayList;
    }
}
