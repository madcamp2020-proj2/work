package com.example.madcamp_week2.Frag1;

import java.util.ArrayList;

public class ContactsList {
    private ArrayList<Contacts> contacts;

    public ArrayList<Contacts> getContactList() {
        return contacts;
    }

    private static ArrayList<Contacts> instance = null;

    //getinstance : 싱글턴패턴 : 하나의 인스턴스만 가지고 공유해서 쓴다
    public static ArrayList<Contacts> getInstance() {
        if (null == instance) {
            synchronized (ContactsList.class) {
                if(instance == null)
                    instance = new ArrayList<Contacts>();
            }
        }
        return instance;
    }
}


