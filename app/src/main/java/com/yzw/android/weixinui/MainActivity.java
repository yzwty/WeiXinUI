package com.yzw.android.weixinui;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.ViewConfiguration;
import android.view.Window;

import com.astuetz.PagerSlidingTabStrip;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class MainActivity extends FragmentActivity {

    private PagerSlidingTabStrip pagerTabs;
    private ViewPager pager;
    public int all_pag=4;
    Window window;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // requestWindowFeature(Window.FEATURE_NO_TITLE);
        ActionBar actionBar=getActionBar();
        /*
        //隐藏Label标签
        actionBar.setDisplayShowTitleEnabled(false);
        */
        //隐藏logo和icon
        actionBar.setDisplayShowHomeEnabled(false);

        setContentView(R.layout.activity_main);
        pagerTabs=(PagerSlidingTabStrip) findViewById(R.id.pager_tabs);
        pager=(ViewPager) findViewById(R.id.pager);
        pager.setOffscreenPageLimit(3);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        pagerTabs.setViewPager(pager);
        setOverflowShowingAlways();

    }
    /**
     * 对PagerSlidingTabStrip的各项属性进行赋值。
     */


    @Override
    public void onBackPressed() {
        if (pager.getCurrentItem() == 0) {
            super.onBackPressed();
        }else{
            pager.setCurrentItem(0);
        }


    }
    private  class MyPagerAdapter extends FragmentPagerAdapter{

        private final String[] titles=getResources().getStringArray(R.array.pager_names);

        private  MyPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }
        @Override
        public CharSequence getPageTitle(int position){
            return titles[position];
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle=new Bundle();
            Fragment fragment=null;
            if (position==0){
            fragment=new ListRefreshFragment();
            }else {
                fragment=new MyPageFragment();
            }
            bundle.putInt("PAG_NUM",position);
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return titles.length;
        }
    }


/*
menu
 */
//加载menu文件
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }
//让隐藏在overflow当中的Action按钮的图标显示出来
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    Method m = menu.getClass().getDeclaredMethod(
                            "setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }
//屏蔽掉物理Menu键，不然在有物理Menu键的手机上，overflow按钮会显示不出来。
    private void setOverflowShowingAlways() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class
                    .getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField!=null){
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
