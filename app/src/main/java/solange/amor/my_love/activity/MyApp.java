package solange.amor.my_love.activity;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

/**
 * BottomNav
 * Created by Suleiman19 on 8/2/16.
 * Copyright (c) 2016. Suleiman Ali Shakir. All rights reserved.
 */

public class MyApp extends Application {

    static {
        AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
    }
}
