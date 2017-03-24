package jucaipen.livechat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import jucaipen.livechat.activity.LiveActivity;

/**
 * Created by Administrator on 2017/3/22.
 */

public class WelcomActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom);
    }

    public  void  onRecoder(View view){
        Intent live=new Intent();
        live.setClass(this, LiveActivity.class);
        startActivity(live);
    }


    public  void onWatch(View view){
        Intent live=new Intent();
        live.setClass(this, MainActivity.class);
        startActivity(live);

    }


    public  void oVideo(View view){
        Intent intent=new Intent();
        intent.setClass(this,TestActivity.class);
        startActivity(intent);

    }
}
