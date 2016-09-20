package com.arktech.waqasahmed.talkshub;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by WaqasAhmed on 2/19/2015.
 */
public class SpeakerFragment extends ListFragment {

    private int Offset;
    private Button LoadMore;
    private ListView SpeakerList;
    private String SpeakersUrl = "https://api.ted.com/v1/speakers.json?api-key=nm4nq9uyqg558m7z8axbu3be&order=created_at:desc&fields=talks,photo_url&offset=";
    private HandleJSON obj;
    private ProgressDialog m_ProgressDialog = null;
    private ArrayList<SpeakerItems> SpeakerItemsArray = new ArrayList<>();
    private SpeakerListAdapter speakerListAdapter;
    private Runnable getSpeakers;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View speaker = inflater.inflate(R.layout.speakers_frag, container, false);
        SpeakerList = (ListView) speaker.findViewById(android.R.id.list);
        LoadMore = new Button(getActivity());
        LoadMore.setText("Load More");
        LoadMore.setHeight(40);
        LoadMore.setWidth(150);

        LoadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FetchSpeakers(Offset + 20);
            }
        });
        SpeakerList.addFooterView(LoadMore);
        return speaker;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.speakerListAdapter = new SpeakerListAdapter(getActivity(), android.R.layout.simple_list_item_1, SpeakerItemsArray);
        SpeakerList.setAdapter(this.speakerListAdapter);
        FetchSpeakers(0);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){

        }
    }

    private void FetchSpeakers(final int offset){
        getSpeakers = new Runnable(){
            @Override
            public void run() {
                Offset=offset;
                obj = new HandleJSON(SpeakersUrl + Offset);
                obj.fetchJSON("Speakers");
                while (obj.parsingComplete);
                SpeakerItemsArray = obj.getSpeakerItems();
                getActivity().runOnUiThread(returnRes);
            }
        };
        Thread thread = new Thread(null, getSpeakers, "MagentoBackground");
        thread.start();
        //m_ProgressDialog = ProgressDialog.show(getActivity(),
                //"Please wait...", "Retrieving data ...", true);
    }


    private Runnable returnRes = new Runnable() {

        @Override
        public void run() {
            int size = SpeakerItemsArray.size();
            if(SpeakerItemsArray != null && SpeakerItemsArray.size() > 0){
                speakerListAdapter.notifyDataSetChanged();
                for(int i=0; i<size; i++)
                    speakerListAdapter.add(SpeakerItemsArray.get(i));
            }
            //m_ProgressDialog.dismiss();
            Log.i("ARRAY", "" + speakerListAdapter.getCount() + size);
            speakerListAdapter.notifyDataSetChanged();
        }
    };







    private class SpeakerListAdapter extends ArrayAdapter<SpeakerItems> {
        LayoutInflater inflater;
        private ArrayList<SpeakerItems> items;
        public SpeakerListAdapter(Context context, int textViewResourceId, ArrayList<SpeakerItems> items) {
            super(context, textViewResourceId, items);
            inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.items = items;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if(convertView ==  null)
                view = inflater.inflate(R.layout.custom_list_speaker, null);
            else
                view = convertView;

            SpeakerItems SI = items.get(position);
            if(SI != null){
                TextView speakerName = (TextView)view.findViewById(R.id.txtSName);
                TextView speakerDesc = (TextView)view.findViewById(R.id.txtSDesc);
                TextView speakerWho = (TextView)view.findViewById(R.id.txtSWhoThey);
                ImageView speakerImage = (ImageView)view.findViewById(R.id.speakerImage);

                speakerName.setText(SI.getSpeakerName());
                speakerDesc.setText("Description: " + SI.getSpeakerDescription());
                speakerWho.setText(SI.getWhoTheyAre());
                speakerImage.setImageBitmap(SI.getSpeakerImage());
            }
            return view;
        }
    }


}
