package com.arktech.waqasahmed.talkshub;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
 * Created by WaqasAhmed on 3/8/2015.
 */
public class TalksFragment extends ListFragment {

    private int Offset;
    private Button LoadMore;
    private ListView talkList;
    private String TalkUrl = "https://api.ted.com/v1/talks.json?api-key=nm4nq9uyqg558m7z8axbu3be&order=created_at:desc&fields=media_profile_uris,photo_urls,speakers&offset=";
    private HandleJSON obj;
    private ProgressDialog m_ProgressDialog = null;
    private ArrayList<TalksItems> TalkItemsArray = new ArrayList<>();
    private TalkListAdapter talkListAdapter;
    private Runnable getTalks;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View talks = inflater.inflate(R.layout.talk_list_frag, container, false);
        talkList = (ListView) talks.findViewById(android.R.id.list);
        LoadMore = new Button(getActivity());
        LoadMore.setText("Load More");
        LoadMore.setHeight(40);
        LoadMore.setWidth(150);

        LoadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FetchTalks(Offset + 20);
            }
        });

        talkList.addFooterView(LoadMore);
        return talks;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.talkListAdapter = new TalkListAdapter(getActivity(), android.R.layout.simple_list_item_1, TalkItemsArray);
        talkList.setAdapter(this.talkListAdapter);
        FetchTalks(0);
    }


    private void FetchTalks(final int offset){
        getTalks = new Runnable(){
            @Override
            public void run() {
                Offset=offset;
                obj = new HandleJSON(TalkUrl + Offset);
                obj.fetchJSON("Talks");
                while (obj.parsingComplete);
                TalkItemsArray = obj.getTalkItems();
                getActivity().runOnUiThread(returnRes);
            }
        };
        Thread thread = new Thread(null, getTalks, "MagentoBackground");
        thread.start();
        //m_ProgressDialog = ProgressDialog.show(getActivity(),
                //"Please wait...", "Retrieving data ...", true);
    }




    private Runnable returnRes = new Runnable() {

        @Override
        public void run() {
            int size = TalkItemsArray.size();
            if(TalkItemsArray != null && TalkItemsArray.size() > 0){
                talkListAdapter.notifyDataSetChanged();
                for(int i=0; i<size; i++)
                    talkListAdapter.add(TalkItemsArray.get(i));
            }
            //m_ProgressDialog.dismiss();
            Log.i("ARRAY", "" + talkListAdapter.getCount() + size);
            talkListAdapter.notifyDataSetChanged();
        }
    };

    private class TalkListAdapter extends  ArrayAdapter<TalksItems>{
        LayoutInflater inflater;
        private ArrayList<TalksItems> items;
        public TalkListAdapter(Context context, int textViewResourceId, ArrayList<TalksItems> items) {
            super(context, textViewResourceId, items);
            inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.items = items;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if(convertView ==  null)
                view = inflater.inflate(R.layout.custom_list_talk, null);
            else
                view = convertView;

            TalksItems TI = items.get(position);
            if(TI != null){
                TextView talkName = (TextView)view.findViewById(R.id.txtTalkName);
                TextView speakerName = (TextView)view.findViewById(R.id.txtSpeakerName);
                TextView talkDescription = (TextView)view.findViewById(R.id.txtTalkDescription);
                ImageView talkImage = (ImageView)view.findViewById(R.id.talkImage);

                talkName.setText(TI.getTalkName());
                speakerName.setText("Speaker: " + TI.getSpeakerName());
                talkDescription.setText(TI.getTalkDescription());
                talkImage.setImageBitmap(TI.getImageBitmap());
            }
            return view;
        }
    }

}
