
package com.example.madcamp_week2.Frag2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.madcamp_week2.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Fragment2 extends Fragment {
    private FloatingActionButton load;
    private FloatingActionButton upload;
    private FloatingActionButton serverLoad;
    private FloatingActionButton multiLoad;

    public Fragment2() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        load = (FloatingActionButton) findViewById(R.id.fab);
        upload = (FloatingActionButton) findViewById(R.id.fabUpload);
        serverLoad = (FloatingActionButton) findViewById(R.id.fabLoad);
        multiLoad = (FloatingActionButton) findViewById(R.id.fabMulti);
        imageView = findViewById(R.id.imageView);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_2, container, false);
    }
}