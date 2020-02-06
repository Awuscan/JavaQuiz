package com.moch.javaquiz;

import android.os.Bundle;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements AsyncResponse {

    private AppBarConfiguration mAppBarConfiguration;
    private long backPressedTime;

    private static final String urlWebService = "https://8879ae19.ngrok.io/getAllQuestions.php";
    public JavaQuizDBHandler dbHelper;
    public jsonHandler jsonHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,
                R.id.nav_quiz,
                R.id.nav_lectures,
                R.id.nav_labs)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        dbHelper = new JavaQuizDBHandler(this);
        jsonHelper = new jsonHandler();

        Map<String, String> postData = new HashMap<>();
        HttpsPostAsyncTask asyncTask = new HttpsPostAsyncTask(postData);
        asyncTask.delegate = this;
        asyncTask.execute(urlWebService);



    }
    @Override
    protected void onResume() {
        super.onResume();

        //etQuizQuestions();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            System.exit(1);
        } else {
            Toast.makeText(this, getResources().getText(R.string.exit), Toast.LENGTH_SHORT).show();
        }

        backPressedTime = System.currentTimeMillis();
    }

    public void getQuizQuestions() {

    }

    @Override
    public void processFinish(String output) {
        try {
            List<Question> questions = jsonHelper.getQuestions(output);
            dbHelper.fillQuestionsTable(questions);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
