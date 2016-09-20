package com.arktech.waqasahmed.talkshub;

import android.graphics.Bitmap;
import android.net.Uri;

/**
 * Created by WaqasAhmed on 3/8/2015.
 */
public class TalksItems {
    private String TalkName;
    private String SpeakerName;
    private String TalkDescription;
    private Bitmap ImageBmp;
    private Uri VideoUrl;


    public TalksItems(){}

    public void setTalkName(String talkName) {
        String[] namePortion = talkName.split(":");
        TalkName = namePortion[1].trim();
    }

    public String getTalkName() {
        return TalkName;
    }

    public void setSpeakerName(String speakerName) {
        String[] namePortion = speakerName.split(":");
        SpeakerName = namePortion[0].trim();
    }

    public String getSpeakerName() {
        return SpeakerName;
    }

    public void setTalkDescription(String talkDescription) {
        TalkDescription = talkDescription;
    }
    public String getTalkDescription() {
        return TalkDescription;
    }

    public void setImageBitmap(Bitmap imageURL) {
        ImageBmp = imageURL;
    }

    public Bitmap getImageBitmap() {
        return ImageBmp;
    }

    public void setVideoUrl(Uri videoUrl) {
        VideoUrl = videoUrl;
    }

    public Uri getVideoUrl() {
        return VideoUrl;
    }
}
