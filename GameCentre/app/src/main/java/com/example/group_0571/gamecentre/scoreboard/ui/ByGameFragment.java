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
import com.example.group_0571.gamecentre.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.List;

/**
 * ByGameFragment held within ScoreboardActivity.
 * Displays score data from internal storage sorted by game types.
 */
public class ByGameFragment extends Fragment {

    /**
     * Initialize a private scoreboard instance due to multiple calls to getScoresByGame.
     */
    private Scoreboard scoreboard;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_by_game, container, false);
        // Retrieve scoreboard instance
        if (getActivity() != null)
            scoreboard = ((ScoreboardActivity) getActivity()).getScoreboard();

        // Setup listViews and corresponding Array Adapters, each game has its own Array Adapter
        setupListView(view, new int[]{R.id.list_sliding_tiles, R.id.by_game_sliding_tiles_empty_list_text,
                R.string.sliding_tiles_game});
        setupListView(view, new int[]{R.id.list_pipe_game, R.id.by_game_pipe_game_empty_list_text,
                R.string.pipes_game});
        setupListView(view, new int[]{R.id.list_tictactoe_game, R.id.by_game_tictactoe_game_empty_list_text,
                R.string.tictactoe_game});
        return view;
    }

    /**
     * Sets a ListAdapter to the ListView found within view.
     *
     * @param view     the view to obtain objects from
     * @param configId integer array representing the id of ListView, TextView and GameName respectively.
     */
    private void setupListView(View view, int[] configId) {
        ListView listView = view.findViewById(configId[0]);
        TextView emptyTextView = view.findViewById(configId[1]);
        listView.setDivider(null);
        ListAdapter listAdapter = new GameScoreDataAdapter(getContext(), getString(configId[2]));
        listView.setAdapter(listAdapter);
        listView.setEmptyView(emptyTextView);
    }

    /**
     * Private ArrayAdapter class to display R.layout.fragment_list_item.
     * Adopted from https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView
     */
    private class GameScoreDataAdapter extends ArrayAdapter<User> {
        /**
         * String representing the current game's name
         */
        private String currentGame;

        /**
         * Basic Constructor for GameScoreDataAdapter
         *
         * @param context     app Context
         * @param currentGame name of game (String)
         */
        GameScoreDataAdapter(Context context, String currentGame) {
            super(context, 0);
            this.currentGame = currentGame;
            List<User> gameData = scoreboard.getScoresByGame(currentGame);
            addAll(gameData != null ? gameData : new ArrayList<User>());
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
                int index = userElem.getGame().indexOf(currentGame);
                scoreTextView.setText(String.valueOf(userElem.getScore().get(index)));
            }
            return view;
        }
    }
}
