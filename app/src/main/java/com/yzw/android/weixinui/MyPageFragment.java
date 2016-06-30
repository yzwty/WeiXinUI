package com.yzw.android.weixinui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by 小志伟 on 2016/6/27.
 */

public class MyPageFragment extends Fragment{
    private int pageNum;
    private TextView tv;
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.mypager_fragment,container,false);
        tv=(TextView)view.findViewById(R.id.tv);
        Bundle bundle=getArguments();
        pageNum= bundle.getInt("PAG_NUM");
        if (pageNum==1){
            tv .setText("我的好友");
        }else if(pageNum==2){
            tv .setText("朋友圈");
        }else{
            tv .setText("微信号：123456");
        }

        return view;
    }
}
