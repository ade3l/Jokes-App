package com.example.jokesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

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
    private Context context;
    private Context getAppContext(){
        return context;
    }
    public void genJoke(View view){
        apiGetter task=new apiGetter();
        task.execute("https://official-joke-api.appspot.com/random_ten");
    }

    public class apiGetter extends AsyncTask<String,Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            try {
                URL url=new URL(strings[0]);
                HttpURLConnection conn=(HttpURLConnection)url.openConnection();
                InputStream in=conn.getInputStream();
                InputStreamReader reader=new InputStreamReader(in);
                int data=reader.read();
                while(data!=-1){
                    char current=(char)data;
                    result+=current;
                    data=reader.read();
                }
                Log.i("mine",result);
                return result;
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
            setList();        }




    }
    void setList(){
        MyAdapter adapter=new MyAdapter(getAppContext(),setups,punchlines);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(this));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recycler=findViewById(R.id.recycler);
        context=this;
        View view = null;
        genJoke(view);
    }
}