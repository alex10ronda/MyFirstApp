package com.example.alejandroruiz.myfirstapp;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class HttpExampleActivity extends Activity {
    private static final String DEBUG_TAG = "HttpExample";
    private EditText urlText;
    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_example);
        urlText = (EditText) findViewById(R.id.myUrl);
        textView = (TextView) findViewById(R.id.myText);

    }

    public void myClickHandler(View view){
        String stringUrl = urlText.getText().toString();
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){
            new DownloadWebpageTask().execute(stringUrl);
        }else{
            textView.setText("No network connection available");
        }
    }

    private class DownloadWebpageTask extends AsyncTask<String,Void,String>{


        @Override
        protected String doInBackground(String... urls) {

            try{
                return downloadUrl(urls[0]);
            }catch (IOException e){
                return "Unable to retrieve web page.URL may be invalid";
            }
        }

        @Override
        protected void onPostExecute(String result){
            textView.setText(result);
        }

        private String downloadUrl(String myurl) throws IOException{
            InputStream is = null;
            int len = 500;
            String email="No";
            try{
                URL url = new URL(myurl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(1000 /*miliseconds*/);
                conn.setConnectTimeout(1500 /*miliseconds*/);
                conn.setRequestMethod("GET");

                conn.setDoInput(true);
                //Starts the query
                conn.connect();
                int response = conn.getResponseCode();
                Log.d(DEBUG_TAG, "The response is: " + response);
                is = conn.getInputStream();

                String contentAsString = readIt(is,len);
                JSONObject jsonArray = new JSONObject(contentAsString);

                email = jsonArray.getString("email");
                Log.d("mensaje", jsonArray.getString("email"));


            } catch (JSONException e) {
                e.printStackTrace();
            } finally{
                if(is!=null){
                    is.close();
                }
            }
            return email;
        }
        public String readIt(InputStream stream, int len) throws IOException,UnsupportedEncodingException{
            Reader reader = null;
            reader = new InputStreamReader(stream, "UTF-8");
            char[] buffer = new char[len];
            reader.read(buffer);
            return new String(buffer);
        }
    }


}
