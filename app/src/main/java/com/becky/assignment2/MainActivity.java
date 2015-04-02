package com.becky.assignment2;

import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

    // URL = "https://api.mongolab.com/api/1/databases/beckyzhou/collections/a2?apiKey=ReayiT-IWVJ2es0gd8m2bxmgGRbKyPgD";
    private String URL1 = "https://api.mongolab.com/api/1/databases/";
    private String DB = "beckyzhou";
    private String URL2 = "/collections/";
    private String COL = "a2";
    private String URL3 = "?apiKey=";
    private String KEY = "ReayiT-IWVJ2es0gd8m2bxmgGRbKyPgD";
    private String URL;

    private JSONArray records;
    SQLiteHelper helper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new SQLiteHelper(this);
        updateFields();
        URL = URL1 + DB + URL2 + COL + URL3 + KEY;
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

    public void getInfo(final View v)
    {
        String db = ((EditText)findViewById(R.id.dbName)).getText().toString();
        String col = ((EditText)findViewById(R.id.colName)).getText().toString();
        String api = ((EditText)findViewById(R.id.apiKey)).getText().toString();

        if(!(db.equals("") && col.equals("") && api.equals(""))) {

            URL = URL1 + db + URL2 + col + URL3 + api;
        }

        URL.trim();
        new pull().execute("");    ////////////////
    }

    public void updateFields()
    {
        LinearLayout l = (LinearLayout)findViewById(R.id.display);
        l.removeAllViews();

        Cursor c = helper.get(helper);

        if(!c.moveToFirst())
        {
            Toast.makeText(getApplicationContext(), "No more data", Toast.LENGTH_LONG).show();
            return;
        }
        do
        {
            String first = c.getString(0);
            String last = c.getString(1);
            String email = c.getString(2);
            String stuNum = c.getString(3);

            TextView t = new TextView(MainActivity.this);
            t.setText(first + " " + last + " " + email + " " + stuNum);
            l.addView(t);

        }
        while(c.moveToNext());

    }

    public void toastData(final View v)
    {
        Cursor c = helper.get(helper);

        if(!c.moveToFirst())
        {
            Toast.makeText(getApplicationContext(), "No more data", Toast.LENGTH_LONG).show();
            return;
        }
        do
        {
            String first = c.getString(0);
            String last = c.getString(1);

            Toast.makeText(getApplicationContext(), first + " " + last, Toast.LENGTH_SHORT).show();

        }
        while(c.moveToNext());
    }

    public void deleteAll(final View v) {
        helper.delete(helper);
        updateFields();
    }


    private class pull extends AsyncTask<String, Integer, Long>
    {
        @Override
        protected Long doInBackground(String...params)
        {
            HttpClient hc = new DefaultHttpClient();
            HttpGet hg = new HttpGet(URL);
            HttpResponse hr;

            records = new JSONArray();

            try {
                hr = hc.execute(hg);

                HttpEntity he = hr.getEntity();

                String entityResponse = EntityUtils.toString(he);

                records = new JSONArray(entityResponse);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Long aLong)
        {
            super.onPostExecute(aLong);

            helper.delete(helper);

            try {

                for(int i = 0; i < records.length(); i++)
                {
                    JSONObject obj = records.getJSONObject(i);
                    Log.d("MSG", obj.toString());

                    String first = obj.getString("first_name");
                    String last = obj.getString("last_name");
                    String email = obj.getString("email_address");
                    String stuNum = obj.getString("student_number");

                    helper.put(helper, first, last, email, stuNum);

                }

                updateFields();

            } catch(JSONException e) {
                Log.d("MSG", "JSON Exception: " + e.getMessage());
            } catch(NullPointerException e) {
                Toast.makeText(getApplicationContext(), "Please enter a valid database", Toast.LENGTH_LONG).show();
            }
        }

    }


}
