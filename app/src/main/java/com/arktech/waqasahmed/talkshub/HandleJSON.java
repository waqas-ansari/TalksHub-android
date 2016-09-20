package com.arktech.waqasahmed.talkshub;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

/**
 * Created by WaqasAhmed on 1/31/2015.
 */
public class HandleJSON {
    private String VideoUri = "VideoUri";
    private ArrayList<TalksItems> talksItemsArray;
    private ArrayList<SpeakerItems> speakerItemsArray;
    private String urlString = null;

    public volatile boolean parsingComplete = true;

    public HandleJSON(String url) {
        this.urlString = url;
    }

    public ArrayList<TalksItems> getTalkItems() { return talksItemsArray; }
    public ArrayList<SpeakerItems> getSpeakerItems() { return speakerItemsArray; }


    public void ReadAndParseTalk(String in){
        try {
            JSONObject reader = new JSONObject(in);
            JSONArray TalksArray = reader.getJSONArray("talks");
            talksItemsArray = new ArrayList<>();
            TalksItems items;
            int length = TalksArray.length();
            for(int i=0; i < length; i++){
                JSONObject object = TalksArray.getJSONObject(i).getJSONObject("talk");

                Uri talkVideo = Uri.parse(object.getJSONObject("media_profile_uris").getJSONObject("internal").getJSONObject("320k").getString("uri"));
                URL talkImage = new URL(object.getJSONArray("photo_urls").getJSONObject(1).getString("url"));
                items = new TalksItems();
                items.setTalkName(object.getString("name"));
                items.setSpeakerName(object.getString("name"));
                items.setTalkDescription(object.getString("description"));
                Bitmap bmp = BitmapFactory.decodeStream(talkImage.openConnection().getInputStream());
                items.setImageBitmap(bmp);
                items.setVideoUrl(talkVideo);
                talksItemsArray.add(items);
            }
            Thread.sleep(5000);
            parsingComplete = false;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void ReadAndParseSpeaker(String in){
        try {
            JSONObject reader = new JSONObject(in);
            JSONArray SpeakerArray = reader.getJSONArray("speakers");
            speakerItemsArray = new ArrayList<>();
            talksItemsArray = new ArrayList<>();
            SpeakerItems items;
            TalksItems SpeakerTalks;
            int length = SpeakerArray.length();
            for(int i=0; i<length; i++){
                JSONObject object = SpeakerArray.getJSONObject(i).getJSONObject("speaker");
                URL speakerImage = new URL(object.getString("photo_url").toString());
                items = new SpeakerItems();
                items.setSpeakerName(object.getString("firstname") + " " + object.getString("middleinitial") + " " + object.getString("lastname"));
                items.setSpeakerDescription(object.getString("description"));
                items.setWhoTheyAre(object.getString("whotheyare"));
                items.setWhyListen(object.getString("whylisten"));
                Bitmap bmp = BitmapFactory.decodeStream(speakerImage.openConnection().getInputStream());
                items.setSpeakerImage(bmp);

                /*JSONArray TalkArray = object.getJSONArray("talks");
                int TalkLength = TalkArray.length();
                for(int j=0; j<TalkLength; j++){
                    JSONObject talkObject = TalkArray.getJSONObject(i);
                    Uri talkVideo = Uri.parse(talkObject.getJSONObject("media_profile_uris").getJSONObject("internal").getJSONObject("320k").getString("uri"));
                    URL talkImage = new URL(talkObject.getJSONArray("photo_urls").getJSONObject(1).getString("url"));
                    SpeakerTalks = new TalksItems();
                    SpeakerTalks.setTalkName(talkObject.getString("name"));
                    SpeakerTalks.setSpeakerName(talkObject.getString("name"));
                    SpeakerTalks.setTalkDescription(talkObject.getString("description"));
                    Bitmap bmpTalk = BitmapFactory.decodeStream(talkImage.openConnection().getInputStream());
                    SpeakerTalks.setImageBitmap(bmpTalk);
                    SpeakerTalks.setVideoUrl(talkVideo);
                    talksItemsArray.add(SpeakerTalks);
                }
                items.setSpeakerTalks(talksItemsArray);*/
                speakerItemsArray.add(items);
            }
            Thread.sleep(5000);
            parsingComplete = false;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }



    public void fetchJSON(final String fetchWhat){
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    URL url = new URL(urlString);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10000 /* milliseconds */);
                    conn.setConnectTimeout(15000 /* milliseconds */);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    // Starts the query
                    conn.connect();
                    InputStream stream = conn.getInputStream();

                    String data = convertStreamToString(stream);

                    if(fetchWhat == "Talks")
                        ReadAndParseTalk(data);
                    else if(fetchWhat == "Speakers")
                        ReadAndParseSpeaker(data);

                    stream.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }


    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }


}
