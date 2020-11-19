package com.sms.pruebamnc;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView mGridView;
    private ProgressBar mProgressBar;
    private ArrayList<GridItem> mGridData;
    private String FEED_URL = "https://api.unsplash.com/search/photos?per_page=10&query=dog&client_id=iXHorfs-BfQaP5rudcMTm1z4Zfvm4HTevHWZdPKiYso";
    private AdapterGrid AdapterGrid;
    private OperacionesDB db;
    private Cursor cursor;
    String favoritoGuardado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new OperacionesDB(getApplicationContext());
        db.open();

        mGridView =  findViewById(R.id.my_recycler_view);
        mProgressBar = findViewById(R.id.progressBar);
        mGridData = new ArrayList<>();
        AdapterGrid = new AdapterGrid(this, mGridData);

        GridLayoutManager gridLayoutManager= new GridLayoutManager(this, 2,GridLayoutManager.VERTICAL,false);
        mGridView.setLayoutManager(gridLayoutManager);
        mGridView.setAdapter(AdapterGrid);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cursor = db.obtenerCantidadIdImagen();
                if (cursor.moveToFirst()) {
                    do {
                        favoritoGuardado=cursor.getString(0);//id
                    }
                    while (cursor.moveToNext());
                }
                if(!favoritoGuardado.equals("0")){
                    Intent i = new Intent(getApplicationContext(), Favoritos.class);
                    startActivity(i);
                }else{
                    Toast.makeText(getApplicationContext(),"No tiene imagenes marcadas como favoritos",Toast.LENGTH_SHORT).show();
                }
            }
        });
        new AsyncHttpTask().execute(FEED_URL);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    //Downloading data asynchronously
    @SuppressLint("StaticFieldLeak")
    public class AsyncHttpTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... params) {
            int result = 0;
            try {
                // Create Apache HttpClient
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse httpResponse = httpclient.execute(new HttpGet(params[0]));
                int statusCode = httpResponse.getStatusLine().getStatusCode();

                // 200 represents HTTP OK
                if (statusCode == 200) {
                    String response = streamToString(httpResponse.getEntity().getContent());
                    parseResult(response);
                    result = 1; // Successful
                } else {
                    result = 0; //"Failed
                }
            } catch (Exception e) {
                Log.d(TAG, e.getLocalizedMessage());
            }

            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            // Download complete. Lets update UI

            if (result == 1) {
                AdapterGrid.setGridData(mGridData);
            } else {
                Toast.makeText(MainActivity.this, "Failed to fetch data!", Toast.LENGTH_SHORT).show();
            }

            //Hide progressbar
            mProgressBar.setVisibility(View.GONE);
        }
    }


    String streamToString(InputStream stream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
        String line;
        String result = "";
        while ((line = bufferedReader.readLine()) != null) {
            result += line;
        }

        // Close stream
        if (null != stream) {
            stream.close();
        }
        return result;
    }

    /**
     * Parsing the feed results and get the list
     *
     * @param result
     */
    private void parseResult(String result) {
        try {

            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("results");
            GridItem item;
            for (int i = 0; i < posts.length(); i++) {
                JSONObject post = posts.optJSONObject(i);
                String title = post.optString("created_at");
                String urls = post.optString("urls");
                JSONObject object = new JSONObject(urls);
                String full=object.getString("full");
                String user = post.optString("user");
                JSONObject objectuser = new JSONObject(user);
                String username=objectuser.getString("name");
                String likes=post.getString("likes");
                String id=post.getString("id");
                item = new GridItem();
                item.setTitle(title);
                item.setImage(full);
                item.setLikes(likes);
                item.setId(id);
                item.setUsername((username));
                mGridData.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}