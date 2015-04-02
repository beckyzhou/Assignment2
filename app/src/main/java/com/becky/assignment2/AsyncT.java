package com.becky.assignment2;

import android.os.AsyncTask;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Created by Becky on 2015-04-01.
 */
public class AsyncT extends AsyncTask<String, Integer, Long>
{
    protected Long doInBackground(String...arg0)
    {
        String URL = "https://api.mongolab.com/api/1/databases/beckyzhou/collections/a2?apiKey=ReayiT-IWVJ2es0gd8m2bxmgGRbKyPgD";
        HttpClient hc = new DefaultHttpClient();
        HttpPost hp = new HttpPost(URL);

        return null;
    }
}
