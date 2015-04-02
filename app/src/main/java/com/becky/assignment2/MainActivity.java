package com.becky.assignment2;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class MainActivity extends ActionBarActivity {

    String URL = "https://api.mongolab.com/api/1/databases/beckyzhou/collections/a2?apiKey=ReayiT-IWVJ2es0gd8m2bxmgGRbKyPgD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class getFromMongo extends AsyncTask
    {
        String resp;
        JSONArray jarray;
        JSONObject jobject;

        @Override
        protected Object doInBackground(Object[] params) {

            HttpClient client = new DefaultHttpClient();    //
            HttpResponse response;                          // stores response
            HttpGet get = new HttpGet(URL);                 // gets the url
            HttpEntity entity;
            try {
                response = client.execute(get);
                entity = response.getEntity();

                // a json array
                resp = EntityUtils.toString(entity);
                jarray = new JSONArray(resp);
                jobject = jarray.getJSONObject(0);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            }
        }
    }

    public void click(View v)
    {
        new getFromMongo().execute();
    }
}
