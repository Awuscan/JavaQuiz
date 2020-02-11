package com.moch.javaquiz;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;


public class MainActivity extends AppCompatActivity implements AsyncResponse {

    private static final String urlWebService = "https://8879ae19.ngrok.io/";
    private static final String urlGetQuestions = "getAllQuestions.php";
    private static final String urlGetNotices = "getAllNotices.php";
    public static JavaQuizDBHandler dbHelper;
    public static jsonHandler jsonHelper;
    private AppBarConfiguration mAppBarConfiguration;
    private long backPressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                //R.id.nav_home,
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        getNewData();
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

    @Override
    public void processFinish(String output) {
        try {
            dbHelper.fillQuestionsTable(jsonHelper.getQuestions(output));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            dbHelper.fillNoticesTable(jsonHelper.getNotices(output));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getNewData() {
        HttpsPostAsyncTask asyncTask1 = new HttpsPostAsyncTask();
        asyncTask1.delegate = this;
        asyncTask1.execute(urlWebService + urlGetQuestions);

        HttpsPostAsyncTask asyncTask2 = new HttpsPostAsyncTask();
        asyncTask2.delegate = this;
        asyncTask2.execute(urlWebService + urlGetNotices);
    }

    public Drawable getDrawable(String file){
        try
        {
            InputStream ims = getAssets().open(file);
            Drawable d = Drawable.createFromStream(ims, null);
            ims.close();
            return d;
        }
        catch(IOException ex)
        {
            return null;
        }
    }
}
