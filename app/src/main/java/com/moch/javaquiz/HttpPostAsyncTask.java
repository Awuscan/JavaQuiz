package com.moch.javaquiz;

import android.os.AsyncTask;
import android.util.Log;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

interface AsyncResponse {
    void processFinish(String output);
}

class HttpsPostAsyncTask extends AsyncTask<String, Void, Void> {
    public AsyncResponse delegate = null;

    public HttpsPostAsyncTask() {}

    @Override
    protected Void doInBackground(String... params) {

        try {
            URL url = new URL(params[0]);

            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();

            int statusCode = urlConnection.getResponseCode();

            if (statusCode ==  200) {
                InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                String response = convertInputStreamToString(inputStream);

                Log.d("Response", response);

                onPostExecute(response);
            }

            urlConnection.disconnect();
        } catch (Exception e) {
            Log.d("Response", e.getLocalizedMessage());
        }

        return null;
    }

    private void onPostExecute(String result) {
        delegate.processFinish(result);
    }

    private String convertInputStreamToString(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

}