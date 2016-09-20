package com.arktech.waqasahmed.talkshub;

import android.graphics.Bitmap;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by WaqasAhmed on 3/15/2015.
 */
public class SpeakerItems {
    private String SpeakerName;
    private String SpeakerDescription;
    private String WhoTheyAre;
    private String WhyListen;
    private Bitmap SpeakerImage;
    private ArrayList<TalksItems> SpeakerTalks;

    public  SpeakerItems() {}

    public String getSpeakerName() { return SpeakerName; }
    public void setSpeakerName(String speakerName) {
        SpeakerName = speakerName;
    }


    public String getSpeakerDescription() {
        return SpeakerDescription;
    }

    public void setSpeakerDescription(String speakerDescription) {
        SpeakerDescription = speakerDescription;
    }

    public String getWhoTheyAre() {
        return WhoTheyAre;
    }

    public void setWhoTheyAre(String whoTheyAre) {
        WhoTheyAre = whoTheyAre;
    }

    public Bitmap getSpeakerImage() {
        return SpeakerImage;
    }

    public void setSpeakerImage(Bitmap speakerImage) {
        SpeakerImage = speakerImage;
    }

    public ArrayList<TalksItems> getSpeakerTalks() {
        return SpeakerTalks;
    }

    public void setSpeakerTalks(ArrayList<TalksItems> speakerTalks) {
        SpeakerTalks = speakerTalks;
    }

    public String getWhyListen() {
        return WhyListen;
    }

    public void setWhyListen(String whyListen) {
        WhyListen = whyListen;
    }
}
