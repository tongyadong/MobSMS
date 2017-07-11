package learn.tony.mobsms;

import android.app.Application;

import com.mob.MobSDK;

/**
 * Created by humax on 17/7/7
 */

public class MyApp extends Application {

    private final String app_key = "1f40bd4dbc4f5";
    private final String app_secret = "b057c50231a1b469c59baca8a850546c";

    @Override
    public void onCreate() {
        super.onCreate();
        MobSDK.init(this,app_key,app_secret);
    }
}
