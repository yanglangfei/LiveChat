package jucaipen.livechat.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import jucaipen.livechat.MainActivity;
import jucaipen.livechat.R;

/**
 * Created by Administrator on 2017/3/21.
 */

public class ChatFragment extends Fragment {

    private View view;
    private DrawerLayout dl;
    private LinearLayout chat_lay;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view= inflater.inflate(R.layout.chat_fragment,container,false);
        initView();
        return view;
    }

    private void initView() {
        chat_lay= (LinearLayout) view.findViewById(R.id.chat_lay);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        dl= (DrawerLayout) getActivity().findViewById(R.id.dl);
        dl.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                Toast.makeText(getActivity(), "open", Toast.LENGTH_SHORT).show();
                chat_lay.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.skin6));
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                chat_lay.setBackgroundColor(Color.TRANSPARENT);
                Toast.makeText(getActivity(), "close", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }
}
