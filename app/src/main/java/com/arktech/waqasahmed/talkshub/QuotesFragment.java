package com.arktech.waqasahmed.talkshub;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by WaqasAhmed on 2/19/2015.
 */
public class QuotesFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View quote = inflater.inflate(R.layout.quotes_frag, container, false);
        return quote;
    }
}
