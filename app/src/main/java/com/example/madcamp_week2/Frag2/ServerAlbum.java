package com.example.madcamp_week2.Frag2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.madcamp_week2.R;

import java.util.ArrayList;

public class ServerAlbum extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_album);

        Bundle extras = getIntent().getExtras();
        ArrayList<String> ulrList = extras.getStringArrayList("urlData");
        RecyclerView recyclerView = findViewById(R.id.recycle_view);
        ServerAlbumAdapter adapter = new ServerAlbumAdapter(ulrList, this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);
    }
}