package solange.amor.my_love;

/**
 * Created by Demo android on 9/29/2016.
 */

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.facebook.drawee.backends.pipeline.Fresco;


public class MyApplication extends MultiDexApplication {
    public static Context context;

    private static MyApplication mInstance;

    public void onCreate() {
        super.onCreate();
        this.context = this;
        mInstance = this;


        Fresco.initialize(this.context);




    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }


}
