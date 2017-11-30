package solange.amor.my_love.controller;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by TOSHIBA on 7/09/2017.
 */

public class StartApp {

    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private static String sharedName = "SplahScreen";
    private static String firstTime = "FirsTime";

    public StartApp(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(sharedName, 0);
        editor = sharedPreferences.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(firstTime, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return sharedPreferences.getBoolean(firstTime, true);
    }

}
