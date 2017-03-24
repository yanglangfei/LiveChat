package jucaipen.livechat.fragment;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.tencent.TIMCallBack;
import com.tencent.TIMConnListener;
import com.tencent.TIMConversation;
import com.tencent.TIMConversationType;
import com.tencent.TIMElem;
import com.tencent.TIMElemType;
import com.tencent.TIMGroupManager;
import com.tencent.TIMImage;
import com.tencent.TIMImageElem;
import com.tencent.TIMLogListener;
import com.tencent.TIMManager;
import com.tencent.TIMMessage;
import com.tencent.TIMMessageListener;
import com.tencent.TIMOfflinePushSettings;
import com.tencent.TIMTextElem;
import com.tencent.TIMUser;
import com.tencent.TIMUserStatusListener;
import com.tencent.TIMValueCallBack;

import java.util.ArrayList;
import java.util.List;
import jucaipen.livechat.R;
import jucaipen.livechat.adapter.MessageAdapter;

/**
 * Created by Administrator on 2017/3/21.
 */

public class VideoFragment extends Fragment implements View.OnClickListener, TIMMessageListener, TIMConnListener, TIMLogListener, TIMUserStatusListener {
    public  static  final  String FC="http://wshdl.load.cdn.zhanqi.tv/zqlive/2869_tUPFz.flv";
    private ListView msg_lv;
    private static final String TAG = "VideoFragment";
    private View view;
    private Button btnSend;
    private EditText etMsg;
    private MessageAdapter adapter;
    private List<String>  msg=new ArrayList<>();
    private TIMManager chatManager;
    private int appId=1400027389;
    private TIMConversation conversation;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.video_fragment, container, false);
        initView();
        initData();
        return view;
    }

    private void initData() {
        for(int i=0;i<10;i++){
            msg.add("msg"+i);
        }
        adapter.notifyDataSetChanged();
    }


    private void initView() {
        btnSend= (Button) view.findViewById(R.id.btnSend);
        btnSend.setOnClickListener(this);
        etMsg= (EditText) view.findViewById(R.id.etMsg);
        //获取通讯管理器
        chatManager = TIMManager.getInstance();
        //添加消息监听
        chatManager.addMessageListener(this);
        //设置与服务器连接状态
        chatManager.setConnectionListener(this);
        //添加Log监听
        chatManager.setLogListener(this);
        // 在线状态监听
        chatManager.setUserStatusListener(this);
        //打印log
        chatManager.disableStorage();
        TIMUser user=new TIMUser();
        user.setIdentifier("test");
        user.setAppIdAt3rd(appId+"");
        user.setAccountType("11516");
        String userSign="eJxlj1FPgzAUhd-5FQ2vM9oWCGKyB8AxyECHm8G3htBCClmHbaeQxf*u4hIx3tfvOznnng0AgLlPd9dlVR1PQhM99swEd8CE5tUv7HtOSamJJek-yIaeS0bKWjM5QeQ4DoZw7nDKhOY1vxiaKT2jinZkqviJ219Z7Fq33lzhzQSzVR4mwU1UJw9F2oW5rZ5d3FQ2jvNHtxwXUR3JNVbjIIbWv29ffR6cQrGJizaO8qLMskCm*D15WdH9NtsN68b3Dt3iSfibsdg2y*WsUvMDuwzyoIU89OejNyYVP4pJwBA5CFvw*0zjw-gEc1tcVA__";
        chatManager.login(appId, user, userSign, new TIMCallBack() {
            @Override
            public void onError(int i, String s) {
                Log.i(TAG, "onError: 登录失败："+i+"    "+s);
                Toast.makeText(getActivity(), "登录失败："+s+"   i="+i, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess() {
                Log.i(TAG, "onSuccess: 登录成功");
                Toast.makeText(getActivity(), "登陆成功", Toast.LENGTH_SHORT).show();
                TIMOfflinePushSettings pushSetting=new TIMOfflinePushSettings();
                pushSetting.setEnabled(true);
                chatManager.configOfflinePushSettings(pushSetting);

                TIMGroupManager groupManager = TIMGroupManager.getInstance();
                groupManager.applyJoinGroup("@TGS#aWVIZCOEY", "", new TIMCallBack() {
                    @Override
                    public void onError(int i, String s) {
                        Toast.makeText(getActivity(), "添加群失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess() {
                        Toast.makeText(getActivity(), "加群成功", Toast.LENGTH_SHORT).show();
                        //获取指定的群组
                        conversation = chatManager.getConversation(TIMConversationType.Group, "@TGS#aWVIZCOEY");
                    }
                });
            }
        });



      /*  //加入房间
        int roomId = 0;  // 房间id

        ILVLiveRoomOption options = new ILVLiveRoomOption(null)
                .autoCamera(false)  //是否打开摄像头
                .controlRole("NormalMember")  //设置角色为普通用户
                .authBits(AVRoomMulti.AUTH_BITS_JOIN_ROOM | AVRoomMulti.AUTH_BITS_RECV_AUDIO | AVRoomMulti.AUTH_BITS_RECV_CAMERA_VIDEO | AVRoomMulti.AUTH_BITS_RECV_SCREEN_VIDEO) //权限设置
                .videoRecvMode(AVRoomMulti.VIDEO_RECV_MODE_SEMI_AUTO_RECV_CAMERA_VIDEO) //是否开始半自动接收
                .autoMic(false); //是否自动打开mic
        ILVLiveManager.getInstance().joinRoom(roomId, options, new ILiveCallBack() {
            @Override
            public void onSuccess(Object data) {
                Toast.makeText(getActivity(), "加入房间成功", Toast.LENGTH_SHORT).show();
                //  监听新消息
                ILVLiveConfig config=new ILVLiveConfig();
                config.setLiveMsgListener(new ILVLiveConfig.ILVLiveMsgListener() {
                    @Override
                    public void onNewTextMsg(ILVText text, String SenderId, TIMUserProfile userProfile) {
                         //接收到新的文本消息
                        Toast.makeText(getActivity(), "接收到新文本消息："+text.getText(), Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onNewCustomMsg(ILVCustomCmd cmd, String id, TIMUserProfile userProfile) {
                      //接收到自定义指令
                        Toast.makeText(getActivity(), "接收到新的自定义指令："+cmd.toString(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNewOtherMsg(TIMMessage message) {
                        //接收到新的指令
                        Toast.makeText(getActivity(), "接收到新的指令："+message.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                Toast.makeText(getActivity(), "加入房间失败："+module, Toast.LENGTH_SHORT).show();

            }
        });*/

        msg_lv = (ListView) view.findViewById(R.id.msg_lv);
        adapter=new MessageAdapter(getActivity(),msg);
        msg_lv.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        //发送消息
        String s = etMsg.getText().toString();
        if(s==null){
            Toast.makeText(getActivity(), "消息不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        TIMMessage msgObj=new TIMMessage();

        TIMTextElem txt=new TIMTextElem();
        txt.setText(s);
        if(msgObj.addElement(txt)!=0){
            return ;
        }

        conversation.sendMessage(msgObj, new TIMValueCallBack<TIMMessage>() {
            @Override
            public void onError(int i, String s) {
                Toast.makeText(getActivity(), "发送消息失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(TIMMessage timMessage) {
                Toast.makeText(getActivity(), "发送消息成功", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onNewMessages(List<TIMMessage> list) {
        // 接收到新消息
        for(TIMMessage msg : list){
            Log.i(TAG, "sender=="+msg.getSender()+"  id====="+msg.getMsgId()+"   time==="+msg.timestamp());
            for(int i=0;i<msg.getElementCount();i++){
                TIMElem element = msg.getElement(i);
                if(element.getType()== TIMElemType.Text){
                    // 处理文本信息
                   TIMTextElem txts= (TIMTextElem) element;
                    String str = txts.getText();
                    Log.i(TAG, "接受到文本消息: "+str);
                    Toast.makeText(getActivity(), "接收到文本消息："+str, Toast.LENGTH_SHORT).show();
                }else if(element.getType()==TIMElemType.Image){
                    //处理图片信息
                    TIMImageElem imageElem= (TIMImageElem) element;
                    for(TIMImage image : imageElem.getImageList()){
                        String url = image.getUrl();

                    }


                }else if(element.getType()==TIMElemType.File){
                    //处理文件


                }
            }
        }



        return false;
    }


    @Override
    public void onPause() {
        super.onPause();
        //移除消息监听
        chatManager.removeMessageListener(this);

    }

    @Override
    public void onConnected() {
        Toast.makeText(getActivity(), "连接到server", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDisconnected(int i, String s) {
        Toast.makeText(getActivity(), "断开server", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onWifiNeedAuth(String s) {

    }

    @Override
    public void log(int i, String s, String s1) {
        Log.i(TAG, "log: "+i+"    s=="+s+"    s1==="+s1);

    }

    @Override
    public void onForceOffline() {
        //TODO 弹框   取消登录   -----  再次登录
        Toast.makeText(getActivity(), "被踢下线", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onUserSigExpired() {

    }
}
