package jucaipen.livechat.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.List;
import jucaipen.livechat.R;
/**
 * Created by Administrator on 2017/3/22.
 */

public class MessageAdapter extends BaseAdapter {
    private final List<String> msg;
    private Context content;

    public MessageAdapter(Context content, List<String> msg) {
        this.content=content;
        this.msg=msg;

    }

    @Override
    public int getCount() {
        return msg.size();
    }

    @Override
    public Object getItem(int position) {
        return msg.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView=LayoutInflater.from(content).inflate(R.layout.activity_chat_item,null);
        }
        final ImageView ivLogo= (ImageView) convertView.findViewById(R.id.ivLogo);
        TextView tvMsg= (TextView) convertView.findViewById(R.id.tvMsg);
        tvMsg.setText(msg.get(position));
        Glide.with(content).load(R.drawable.skin6).asBitmap().into(new BitmapImageViewTarget(ivLogo){
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable drawable= RoundedBitmapDrawableFactory.create(content.getResources(),resource);
                drawable.setCircular(true);
                ivLogo.setImageDrawable(drawable);
            }
        });



        return convertView;
    }
}
