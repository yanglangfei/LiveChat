package jucaipen.livechat;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;
import com.tencent.ilivesdk.ILiveConstants;
import com.tencent.ilivesdk.view.AVRootView;
import com.tencent.livesdk.ILVLiveManager;
/**
 * Created by Administrator on 2017/3/23.
 */

public class ViewActivity extends Activity {
    private AVRootView root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        /*ILVLiveManager.getInstance().setAvVideoView(root);

        int maxAvVideoNum = ILiveConstants.MAX_AV_VIDEO_NUM;
        Toast.makeText(this, "max:"+maxAvVideoNum, Toast.LENGTH_SHORT).show();*/
    }
}
