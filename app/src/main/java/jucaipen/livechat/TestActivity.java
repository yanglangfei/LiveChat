package jucaipen.livechat;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;

import java.util.ArrayList;
import java.util.List;

import jucaipen.livechat.adapter.MessageAdapter;

/**
 * Created by Administrator on 2017/3/21.
 */

public class TestActivity extends Activity {
    public  static  final  String FC="http://wshdl.load.cdn.zhanqi.tv/zqlive/2869_tUPFz.flv";
    private ImageView ivTeacherLogo;
    private ImageView userLogo1;
    private ImageView userLogo2;
    private ImageView userLogo3;
    private ImageView userLogo4;
    private TextView tvTeacherNick;
    private ListView lvMsg;
    private MessageAdapter adapter;
    private List<String> msg = new ArrayList<>();
    private LinearLayout topPanel;
    private int lastX;
    private int currentX;
    private TXCloudVideoView mPlayerView;
    private  boolean isLeft=true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        initView();
        initData();
    }

    private void initData() {
        for (int i = 0; i < 50; i++) {
            msg.add("message" + i);
        }
        adapter.notifyDataSetChanged();
    }

    private void initView() {
         mPlayerView = (TXCloudVideoView) findViewById(R.id.video_view);
         TXLivePlayer player=new TXLivePlayer(this);
         player.setPlayerView(mPlayerView);
         player.setRenderMode(TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION);
        player.startPlay(FC,TXLivePlayer.PLAY_TYPE_LIVE_FLV);

        ivTeacherLogo = (ImageView) findViewById(R.id.ivTeacherLogo);
        tvTeacherNick = (TextView) findViewById(R.id.tvTeacherNick);
        topPanel = (LinearLayout) findViewById(R.id.topPanel);
        lvMsg = (ListView) findViewById(R.id.lvMsg);
        adapter = new MessageAdapter(this, msg);
        lvMsg.setAdapter(adapter);

        userLogo1 = (ImageView) findViewById(R.id.userLogo1);
        userLogo2 = (ImageView) findViewById(R.id.userLogo2);
        userLogo3 = (ImageView) findViewById(R.id.userLogo3);
        userLogo4 = (ImageView) findViewById(R.id.userLogo4);

        Glide.with(this).load(R.drawable.skin6).asBitmap().into(new BitmapImageViewTarget(ivTeacherLogo) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(getResources(), resource);
                drawable.setCircular(true);
                ivTeacherLogo.setImageDrawable(drawable);
            }
        });


        Glide.with(this).load(R.drawable.skin6).asBitmap().into(new BitmapImageViewTarget(userLogo1) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(getResources(), resource);
                drawable.setCircular(true);
                userLogo1.setImageDrawable(drawable);
            }
        });


        Glide.with(this).load(R.drawable.skin6).asBitmap().into(new BitmapImageViewTarget(userLogo2) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(getResources(), resource);
                drawable.setCircular(true);
                userLogo2.setImageDrawable(drawable);
            }
        });


        Glide.with(this).load(R.drawable.skin6).asBitmap().into(new BitmapImageViewTarget(userLogo3) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(getResources(), resource);
                drawable.setCircular(true);
                userLogo3.setImageDrawable(drawable);
            }
        });


        Glide.with(this).load(R.drawable.skin6).asBitmap().into(new BitmapImageViewTarget(userLogo4) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(getResources(), resource);
                drawable.setCircular(true);
                userLogo4.setImageDrawable(drawable);
            }
        });


    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                lastX = (int) event.getX();
                break;
            case MotionEvent.ACTION_UP:
                currentX = (int) event.getX();
                if (currentX > lastX &&isLeft) {
                    //右滑                 10       150
                    if (currentX - lastX > 2) {
                        isLeft=false;
                        ObjectAnimator animator = ObjectAnimator.ofFloat(topPanel, "translationX", 0, 800);
                        animator.setDuration(2000);
                        animator.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                //topPanel.setVisibility(View.GONE);

                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        });
                        animator.start();
                    }
                } else if (currentX < lastX &&!isLeft) {
                    //左滑
                    if (lastX - currentX > 2) {
                        isLeft=true;
                        ObjectAnimator animator = ObjectAnimator.ofFloat(topPanel, "translationX", 800, 0);
                        animator.setDuration(2000);
                        animator.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {

                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        });
                        animator.start();
                    }
                }
                break;

        }
        return super.onTouchEvent(event);


    }

}
