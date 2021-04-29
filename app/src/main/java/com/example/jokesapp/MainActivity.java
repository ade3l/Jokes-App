package com.example.jokesapp;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<String> setups=new ArrayList<>(),punchlines=new ArrayList<>();
    RecyclerView recycler;
    SwipeRefreshLayout swipeRefreshLayout;
    Boolean is_first_set,data_changed,waiting;

    private Context context;

    private Context getAppContext(){
        return context;
    }
    public void genJoke(){
        apiGetter task=new apiGetter();
        task.execute("https://official-joke-api.appspot.com/random_ten");
    }

    public class apiGetter extends AsyncTask<String,Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            StringBuilder result = new StringBuilder();
            try {
                URL url=new URL(strings[0]);
                HttpURLConnection conn=(HttpURLConnection)url.openConnection();
                InputStream in=conn.getInputStream();
                InputStreamReader reader=new InputStreamReader(in);
                int data=reader.read();
                while(data!=-1){
                    char current=(char)data;
                    result.append(current);
                    data=reader.read();
                }

                return result.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return"failed1";
            } catch (IOException e) {
                e.printStackTrace();
                return"failed2";

            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            setups.clear();punchlines.clear();
            try {
                JSONArray data=new JSONArray(s);
                for(int i=0;i<data.length();i++){
                    JSONObject object=data.getJSONObject(i);
                    Log.i("mine",object.getString("setup"));
                    setups.add(object.getString("setup"));
                    punchlines.add(object.getString("punchline"));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            data_changed=true;
            if(is_first_set||waiting) {
                data_changed=true;
                setList();
            }
        }




    }
    void setList(){
        int anim_time=500;
        if (data_changed) {
            data_changed = false;
            is_first_set = false;
            if (is_first_set) {
                is_first_set = false;
                MyAdapter adapter = new MyAdapter(getAppContext(), setups, punchlines);
                recycler.setAdapter(adapter);
                recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recycler.setX(-1500);
                recycler.animate().translationXBy(1500).setDuration(anim_time);
            } else {
                genJoke();
                recycler.animate().translationXBy(1500).setDuration(500);
                recycler.postDelayed(() -> {
                    recycler.setX(0);
                    MyAdapter adapter = new MyAdapter(getAppContext(), setups, punchlines);
                    recycler.setAdapter(adapter);
                    recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recycler.setX(-1500);
                    recycler.animate().translationXBy(1500).setDuration(anim_time);

                }, anim_time);
            }
            genJoke();

        }
        else{
            waiting=true;
        }



//        recycler.animate().translationX(+2000).setDuration(1000);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        is_first_set=true;
        waiting=false;
        recycler=findViewById(R.id.recycler);
        context=this;
        genJoke();

         swipeRefreshLayout=findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(
                () -> {



                    setList();
                    swipeRefreshLayout.setRefreshing(false);
                }
        );
    }


}