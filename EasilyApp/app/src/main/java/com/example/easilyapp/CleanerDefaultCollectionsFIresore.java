package com.example.easilyapp;

import com.google.firebase.firestore.FirebaseFirestore;

public abstract class CleanerDefaultCollectionsFIresore {


    public static boolean cleanCollectionWifhReference(String reference){
        FirebaseFirestore.getInstance().collection(reference).document();
        return false;

    }
}
