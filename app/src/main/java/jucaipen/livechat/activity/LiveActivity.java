package jucaipen.livechat.activity;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.util.TimeUtils;
import android.widget.Toast;

import com.tencent.av.TIMAvManager;
import com.tencent.av.sdk.AVRoomMulti;
import com.tencent.ilivesdk.ILiveCallBack;
import com.tencent.ilivesdk.ILiveConstants;
import com.tencent.ilivesdk.core.ILiveLoginManager;
import com.tencent.ilivesdk.core.ILivePushOption;
import com.tencent.ilivesdk.core.ILiveRecordOption;
import com.tencent.ilivesdk.core.ILiveRoomManager;
import com.tencent.ilivesdk.core.ILiveRoomOption;
import com.tencent.ilivesdk.view.AVRootView;
import com.tencent.livesdk.ILVLiveConfig;
import com.tencent.livesdk.ILVLiveManager;
import com.tencent.livesdk.ILVLiveRoomOption;
import com.tencent.rtmp.ITXLivePushListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePushConfig;
import com.tencent.rtmp.TXLivePusher;
import com.tencent.rtmp.ui.TXCloudVideoView;

import java.util.Date;
import java.util.List;

import jucaipen.livechat.R;

/**
 * Created by Administrator on 2017/3/22.
 */

public class LiveActivity extends Activity {
    // 直播用户唯一标识符
    private String id="test02";
    String rtmpUrl = "rtmp://8563.livepush.myqcloud.com/live/8563_435bbba552?bizid=8563&txSecret=19bc5d0fcff21725ade32d6424f0d685&txTime=58D5427F";
    //  后台计算获取到的
    String userSign="eJxljlFPwjAUhd-3K5a*aqTtujFMeCCIC8FFYWDC01LWotfB1m11zhr-uzBJbOJ5-b57z-lyXNdF64fkhmdZ*V7oVH8qidxbF2F0-QeVApFynXq1*Adlp6CWKd9rWfeQ*L5PMbYdELLQsIeLoWWjMbV4I-K0L-l9wE7XdOiFI1uBlx7Gs*V0fj8U7G1btXFsoFIdBxrtNsZEMxYk4o4eHrOcZMdGRnkwgUnxhDFfzc2aXLXBIj-Ac7koP7pVNTU8hMGmeN02Ca7j3WA5HluVGo7yMmiEPZ*FzLNoK*sGyqIXKCY*oR4*Bznfzg-DZV4P";
    private boolean bLogin;
    private AVRootView avRootView;
    private  TXCloudVideoView video_view;
    private ILiveRoomManager manager;
    private long chanelId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live);
        initView();
    }

    private void initView() {
        int[] sdkver = TXLivePusher.getSDKVersion();
        if (sdkver != null && sdkver.length >= 3) {
            Log.i("111","rtmp sdk version is:" + sdkver[0] + "." + sdkver[1] + "." + sdkver[2]);
        }
        avRootView = (AVRootView) findViewById(R.id.av_root_view);
        video_view= (TXCloudVideoView) findViewById(R.id.video_view);
        ILVLiveManager.getInstance().setAvVideoView(avRootView);
        //登录
        ILiveLoginManager.getInstance().iLiveLogin(id, userSign, new ILiveCallBack() {
            @Override
            public void onSuccess(Object data) {
                bLogin=true;
                Toast.makeText(LiveActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                int roomId = 102; // 直播间id
                final ILVLiveManager instance = ILVLiveManager.getInstance();

                final ILVLiveConfig config=new ILVLiveConfig();
                instance.init(config);
                ILVLiveRoomOption hostOption = new ILVLiveRoomOption(null).
                        controlRole("Host")//角色设置
                        .authBits(AVRoomMulti.AUTH_BITS_DEFAULT)//权限设置
                        .cameraId(ILiveConstants.BACK_CAMERA)//摄像头前置后置
                        .avsupport(true)
                        .videoRecvMode(AVRoomMulti.VIDEO_RECV_MODE_SEMI_AUTO_RECV_CAMERA_VIDEO);//是否开始半自动接收
                //创建房间
                instance.createRoom(roomId,hostOption, new ILiveCallBack() {
                    @Override
                    public void onSuccess(Object data) {
                        Toast.makeText(LiveActivity.this, "房间创建成功", Toast.LENGTH_SHORT).show();

                        manager=ILiveRoomManager.getInstance();
                        ILivePushOption option=new ILivePushOption();
                        option.channelName("推流1");
                        option.encode(TIMAvManager.StreamEncode.HLS);

                        manager.startPushStream(option, new ILiveCallBack<TIMAvManager.StreamRes>() {
                            @Override
                            public void onSuccess(TIMAvManager.StreamRes data) {
                                chanelId=data.getChnlId();
                                List<TIMAvManager.LiveUrl> urls = data.getUrls();

                                for(TIMAvManager.LiveUrl u : urls){
                                    Log.i("111", "onSuccess: "+u.getUrl());
                                }

                                Toast.makeText(LiveActivity.this, "success", Toast.LENGTH_SHORT).show();
                                ILiveRecordOption options=new ILiveRecordOption();
                                options.fileName("录制文件"+ new Date().getTime());
                                options.classId(123);
                                options.recordType(TIMAvManager.RecordType.VIDEO);

                                manager.startRecordVideo(options, new ILiveCallBack() {
                                    @Override
                                    public void onSuccess(Object data) {
                                        Toast.makeText(LiveActivity.this, "开始录制", Toast.LENGTH_SHORT).show();

                                    }

                                    @Override
                                    public void onError(String module, int errCode, String errMsg) {
                                        Toast.makeText(LiveActivity.this, "录制失败", Toast.LENGTH_SHORT).show();

                                    }
                                });



                            }

                            @Override
                            public void onError(String module, int errCode, String errMsg) {
                                Toast.makeText(LiveActivity.this, "error", Toast.LENGTH_SHORT).show();

                            }
                        });



                       /* TXLivePusher pusher=new TXLivePusher(LiveActivity.this);
                        TXLivePushConfig configs=new TXLivePushConfig();
                        pusher.setConfig(configs);

                        pusher.setPushListener(new ITXLivePushListener() {
                            @Override
                            public void onPushEvent(int i, Bundle bundle) {

                                Log.i("111", "onPushEvent: "+i+"    b=="+bundle.toString());

                            }
                            @Override
                            public void onNetStatus(Bundle bundle) {
                                Log.i("111", "onNetStatus:   b=="+bundle.toString());
                            }
                        });
                        pusher.setBeautyFilter(7,3);
                        pusher.startCameraPreview(video_view);
                        pusher.startPusher(rtmpUrl);*/



                    }

                    @Override
                    public void onError(String module, int errCode, String errMsg) {
                        Log.i("111", "onError:  module=="+module+"   errCode=="+errCode+"  errMsg=="+errMsg);

                        Toast.makeText(LiveActivity.this, "房间创建失败："+errMsg+"    "+errCode, Toast.LENGTH_SHORT).show();

                    }
                });
            }
            @Override
            public void onError(String module, int errCode, String errMsg) {
                Toast.makeText(LiveActivity.this, "登录失败："+module, Toast.LENGTH_SHORT).show();

            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        manager.stopRecordVideo(new ILiveCallBack<List<String>>() {
            @Override
            public void onSuccess(List<String> data) {
                for(String url : data){
                    Log.i("111","url==========="+url);

                }

            }

            @Override
            public void onError(String module, int errCode, String errMsg) {

            }
        });

        manager.stopPushStream(chanelId, new ILiveCallBack() {
            @Override
            public void onSuccess(Object data) {

            }

            @Override
            public void onError(String module, int errCode, String errMsg) {

            }
        });


    }
}
