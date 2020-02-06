package com.moch.javaquiz;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


interface AsyncResponse {
    void processFinish(String output);
}

public class jsonHandler implements AsyncResponse{

    private static final String urlWebService = "https://8879ae19.ngrok.io/getAllQuestions.php";

    private static final String COLUMN_QUESTION = "question";
    private static final String COLUMN_CATEGORY = "category";
    private static final String COLUMN_OPTION1 = "option1";
    private static final String COLUMN_OPTION2 = "option2";
    private static final String COLUMN_OPTION3 = "option3";
    private static final String COLUMN_OPTION4 = "option4";
    private static final String COLUMN_ANSWER1 = "answer1";
    private static final String COLUMN_ANSWER2 = "answer2";
    private static final String COLUMN_ANSWER3 = "answer3";
    private static final String COLUMN_ANSWER4 = "answer4";

    HttpsPostAsyncTask asyncTask;
    JavaQuizDBHandler db;

    public void save(JavaQuizDBHandler db) {
        this.db = db;

        Map<String, String> postData = new HashMap<>();

        asyncTask = new HttpsPostAsyncTask(postData);
        asyncTask.delegate = this;
        asyncTask.execute(urlWebService);

    }

    private List<Question> getQuestions(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        List<Question> questions = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            Question question = new Question(
                    obj.getString(COLUMN_QUESTION),
                    obj.getString(COLUMN_CATEGORY),
                    obj.getString(COLUMN_OPTION1),
                    obj.getString(COLUMN_OPTION2),
                    obj.getString(COLUMN_OPTION3),
                    obj.getString(COLUMN_OPTION4),
                    stringToBool(obj.getString(COLUMN_ANSWER1)),
                    stringToBool(obj.getString(COLUMN_ANSWER2)),
                    stringToBool(obj.getString(COLUMN_ANSWER3)),
                    stringToBool(obj.getString(COLUMN_ANSWER4)));
            questions.add(question);
        }
        return questions;
    }

    private boolean stringToBool(String s) {
        return !s.equals("0");
    }

    @Override
    public void processFinish(String output) {
        try {
            db.fillQuestionsTable(getQuestions(output));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

class HttpsPostAsyncTask extends AsyncTask<String, Void, Void> {
    public AsyncResponse delegate = null;
    // This is the JSON body of the post
    private JSONObject postData;
    // This is a constructor that allows you to pass in the JSON body
    public HttpsPostAsyncTask(Map<String, String> postData) {
        if (postData != null) {
            this.postData = new JSONObject(postData);
        }
    }

    // This is a function that we are overriding from AsyncTask. It takes Strings as parameters because that is what we defined for the parameters of our async task
    @Override
    protected Void doInBackground(String... params) {

        try {
            // This is getting the url from the string we passed in
            URL url = new URL(params[0]);

            // Create the urlConnection
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();

            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestMethod("POST");

            // OPTIONAL - Sets an authorization header
            urlConnection.setRequestProperty("Authorization", "someAuthString");

            // Send the post body
            if (this.postData != null) {
                OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream());
                writer.write(postData.toString());
                writer.flush();
            }

            int statusCode = urlConnection.getResponseCode();

            if (statusCode ==  200) {

                InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());

                String response = convertInputStreamToString(inputStream);
                onPostExecute(response);
                Log.d("response", response);

            } else {
                Log.d("response", "niepowodzenie");
            }

            urlConnection.disconnect();
        } catch (Exception e) {
            Log.d("response", e.getLocalizedMessage());

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

