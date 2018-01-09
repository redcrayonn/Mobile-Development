package nl.inholland.imready.app.view.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.widget.ImageView;

import nl.inholland.imready.R;
import nl.inholland.imready.app.view.SceneTransitionConstants;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // on load instantly go to login activity or home if user data is saved on phone
        Intent intent = new Intent(this, LoginActivity.class);

        // setup view(s) for animation
        ImageView logo = findViewById(R.id.logo);

        // setup animation options
        ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(this,
                new Pair<>(logo, SceneTransitionConstants.VIEW_NAME_LOGO));

        // go to next view
        findViewById(R.id.logo).postDelayed(() -> {
            startActivity(intent, activityOptions.toBundle());
            finish();
        }, 1000);

    }
}
