package com.arktech.waqasahmed.talkshub;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TabHost;
import android.widget.VideoView;


public class Home extends FragmentActivity {
    private String TalkUrl = "https://api.ted.com/v1/talks.json?api-key=nm4nq9uyqg558m7z8axbu3be&order=created_at:desc&fields=media_profile_uris,photo_urls,speakers&offset=";
    private HandleJSON obj;
    ViewPager Tab;
    TabPagerAdapter TabAdapter;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //*****************************************************
        TabAdapter = new TabPagerAdapter(getSupportFragmentManager());
        Tab = (ViewPager)findViewById(R.id.pager);
        Tab.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        actionBar = getActionBar();
                        actionBar.setSelectedNavigationItem(position);                    }
                });
        Tab.setAdapter(TabAdapter);
        actionBar = getActionBar();
        //Enable Tabs on Action Bar
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        ActionBar.TabListener tabListener = new ActionBar.TabListener(){
            @Override
            public void onTabReselected(android.app.ActionBar.Tab tab,
                                        FragmentTransaction ft) {
                // TODO Auto-generated method stub
            }
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                Tab.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(android.app.ActionBar.Tab tab,
                                        FragmentTransaction ft) {
                // TODO Auto-generated method stub
            }};
        //Add New Tab
        actionBar.addTab(actionBar.newTab().setText("Talks").setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText("Speaker").setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText("Quotes").setTabListener(tabListener));
        //*****************************************************





        /*Button btuPlay = (Button) findViewById(R.id.PlayVideo);
        btuPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final VideoView vidView = (VideoView)findViewById(R.id.myVideo);
                obj = new HandleJSON(finalUrl);
                obj.fetchJSON();
                while(obj.parsingComplete);
                Uri vidUri;
                vidUri = Uri.parse(obj.getVideoUri());
                vidView.setVideoURI(vidUri);
                vidView.start();
            }
        });*/



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        return super.onOptionsItemSelected(item);
    }
}
