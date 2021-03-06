package com.udacity.gradle.builditbigger;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.displayjokes.JokeActivity;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.builditbigger.backend.jokeBeanApi.JokeBeanApi;

import java.io.IOException;

/**
 * Created by shani on 7/11/16.
 */
public class FetchJoke extends AsyncTask<Void, Void, String> {
    //private static MyApi jokeBeanApi = null;
    private static JokeBeanApi jokeBeanApi = null;
    private Context mContext;
    private ProgressDialog mProgressDialog;
    private static final String JOKE_EXTRA = "joke_extra";

    public FetchJoke(Context context){
        mContext = context;
    }

    protected String doInBackground(Void ...params) {
        if(jokeBeanApi == null) {  // Only do this once
            JokeBeanApi.Builder builder = new JokeBeanApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });

            jokeBeanApi = builder.build();
        }
        try {
            return jokeBeanApi.serveJokes().execute().getJoke();
        } catch (IOException e) {
            return e.getMessage();
        }
    }


    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Intent intent = new Intent(mContext,JokeActivity.class);
        intent.putExtra(JOKE_EXTRA,result);
        mContext.startActivity(intent);
    }


}