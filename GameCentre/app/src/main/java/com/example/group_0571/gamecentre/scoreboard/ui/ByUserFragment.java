package com.example.group_0571.gamecentre.scoreboard.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.group_0571.gamecentre.R;
import com.example.group_0571.gamecentre.User;

import java.util.ArrayList;
import java.util.List;

/**
 * ByUserFragment contained within the ScoreboardActivity class.
 * Displays User objects sorted from highest to lowest maximum scores.
 */
public class ByUserFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_by_user, container, false);
        List<User> scoresByUser = new ArrayList<>();
        if (getActivity() != null)
            scoresByUser = ((ScoreboardActivity) getActivity()).getScoreboard().getScoresByUser();

        // Create, fill and store adapter into listView
        ListView listView = view.findViewById(R.id.listView);
        listView.setDivider(null);
        ListAdapter listAdapter = new UserScoreDataAdapter(getContext(), scoresByUser);
        listView.setAdapter(listAdapter);
        listView.addHeaderView(inflater.inflate(R.layout.fragment_header_item, listView, false));
        listView.setEmptyView(view.findViewById(R.id.by_user_empty_list_text));
        return view;
    }

    /**
     * Private ArrayAdapter class that displays R.layout.fragment_list_item.
     * Adopted from: https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView
     */
    private class UserScoreDataAdapter extends ArrayAdapter<User> {

        /**
         * Basic Constructor for UserScoreDataAdapter
         *
         * @param context  app Context
         * @param userData data to populate into corresponding ListView
         */
        UserScoreDataAdapter(Context context, List<User> userData) {
            super(context, 0, userData);
        }

        @Override
        @NonNull
        public View getView(int pos, View view, @NonNull ViewGroup parent) {
            User userElem = getItem(pos);
            if (view == null) {
                view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_list_item, parent, false);
            }

            // Get our TextView handles
            TextView usernameTextView = view.findViewById(R.id.username);
            TextView scoreTextView = view.findViewById(R.id.score);
            // Update TextView for sorted by game entries
            if (userElem != null) {
                usernameTextView.setText(userElem.getUsername());
                scoreTextView.setText(String.valueOf(userElem.getMaxScore()));
            }

            return view;
        }
    }
}
