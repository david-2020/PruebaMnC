package com.sms.pruebamnc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Favoritos extends Activity {
    private RecyclerView mGridView;
    private ProgressBar mProgressBar;
    private ArrayList<GridItem> mGridData;
    private AdapterGridFavoritos AdapterGridFavoritos;
    private OperacionesDB db;
    private Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fvoritos);
        db = new OperacionesDB(getApplicationContext());
        db.open();



        mGridView =  findViewById(R.id.my_recycler_view);
        mProgressBar = findViewById(R.id.progressBar);
        mGridData = new ArrayList<>();

        llenarGridFavoritos();
        AdapterGridFavoritos = new AdapterGridFavoritos(this, mGridData);
        AdapterGridFavoritos.setGridData(mGridData);

        GridLayoutManager gridLayoutManager= new GridLayoutManager(this, 2,GridLayoutManager.VERTICAL,false);
        mGridView.setLayoutManager(gridLayoutManager);
        mGridView.setAdapter(AdapterGridFavoritos);
    }

    public  void llenarGridFavoritos(){
        cursor = db.obtenerFavoritos();
        if (cursor.moveToFirst()) {
            do {
                GridItem item = new GridItem();
                item.setTitle("title");
                item.setImage(cursor.getString(1));
                item.setLikes(String.valueOf(cursor.getDouble(3)));
                item.setId(cursor.getString(0));
                item.setUsername(cursor.getString(2));
                mGridData.add(item);
            }
            while (cursor.moveToNext());
        }
    }
}