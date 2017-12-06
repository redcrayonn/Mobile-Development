package inholland.tabletapplication.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import inholland.tabletapplication.R;

public class AdminMainActivity extends AppCompatActivity {
    //https://stackoverflow.com/questions/12994957/how-to-create-a-generic-android-xml-layout-for-all-activities
    private Button usersButton;
    private Button gblocksButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        usersButton = (Button) findViewById(R.id.navigationUsers);
        gblocksButton = (Button) findViewById(R.id.navigationGblocks);
    }

    public void navigateToGblocks(){

    }
    public void navigateToUsers(View view){
        Intent myIntent = new Intent(this, AdminUsersActivity.class);
        startActivity(myIntent);
    }
    public void logOut(){
        //clear cache
        //dispose token
        //navigate to login screen
    }
}
