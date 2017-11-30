package solange.amor.my_love.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import solange.amor.my_love.activity.MainActivity;

/**
 * Created by Brandon_Macay on 18-sep-17.
 */

public class splash_activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();

    }

}
