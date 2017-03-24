package jucaipen.livechat.application;
import android.app.Application;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.ilivesdk.ILiveSDK;

/**
 * Created by Administrator on 2017/3/21.
 */

public class MyApplication extends Application {
    private int appid = 1400027389;
    private int accountType = 11516;

    @Override
    public void onCreate() {
        super.onCreate();
        //  初始化直播SDK
        ILiveSDK.getInstance().initSdk(this, appid, accountType);
        CrashReport.initCrashReport(getApplicationContext(), "ff37cba77f", true);


    }
}
