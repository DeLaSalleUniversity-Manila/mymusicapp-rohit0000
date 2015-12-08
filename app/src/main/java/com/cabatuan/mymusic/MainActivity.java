package com.cabatuan.mymusic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private String[] filenames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupPlaylist();
    }


    private void setupPlaylist() {
        ListView lv = (ListView) findViewById(R.id.song_list);
        filenames = getResources().getStringArray(R.array.filenames);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, R.layout.my_list_item, filenames);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int index, long id) {
                Intent intent = new Intent(MainActivity.this, MusicService.class);
                intent.putExtra("filename", filenames[index]);
                intent.setAction(MusicService.ACTION_PLAY);
                startService(intent);
            }
        });
    }


    public void onClickStop(View view) {
        Intent intent = new Intent(this, MusicService.class);
        intent.setAction(MusicService.ACTION_STOP);
        startService(intent);
    }
}
