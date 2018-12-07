package com.example.group_0571.gamecentre.scoreboard.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.group_0571.gamecentre.scoreboard.Scoreboard;
import com.example.group_0571.gamecentre.utils.FileUtil;
import com.example.group_0571.gamecentre.R;
import com.example.group_0571.gamecentre.User;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * ScoreboardActivity that fosters the ByGameFragment and ByUserFragment.
 * Displaying the necessary scores from internal storage.
 */
public class ScoreboardActivity extends AppCompatActivity {
    /**
     * The file holding an List<Score> object.
     */
    public static final String SCORES_PATH = "scores.ser";

    /**
     * The local list of Scores.
     */
    private Scoreboard scoreboard;

    @Override
    @SuppressWarnings("unchecked")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.scoreboard);
        setContentView(R.layout.activity_scoreboard);

        try {
            scoreboard = new Scoreboard(
                    (ArrayList<User>) FileUtil.getInstance().readObjectFromFile(openFileInput(SCORES_PATH)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        // Initialize ViewPager and link to TabLayout
        initializeViewPager();
    }

    /**
     * Getter for scoreboardManager
     *
     * @return ScoreboardManager member obj
     */
    public Scoreboard getScoreboard() {
        if (scoreboard == null) {
            scoreboard = new Scoreboard(new ArrayList<User>());
        }
        return scoreboard;
    }

    /**
     * Initialize the view pager with an adapter instance from private class FragAdapter.
     */
    private void initializeViewPager() {
        ViewPager viewPager = findViewById(R.id.view_pager);
        FragAdapter adapter = new FragAdapter(getSupportFragmentManager());
        adapter.addFrag(new ByUserFragment(), getString(R.string.by_user_fragment_header));
        adapter.addFrag(new ByGameFragment(), getString(R.string.by_game_fragment_header));
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }

    /**
     * The FragmentPagerAdapter class FragAdapter to output information to the Scoreboard
     * Fragments: ByUserFragment and ByGameFragment.
     * FragmentPagerAdapter adopted from Android Docs.
     * Citation: https://developer.android.com/reference/android/support/v4/app/FragmentPagerAdapter
     */
    private class FragAdapter extends FragmentPagerAdapter {
        private final List<Fragment> fragments = new ArrayList<>();
        private final List<String> titles = new ArrayList<>();

        FragAdapter(FragmentManager fragManager) {
            super(fragManager);
        }

        @Override
        public Fragment getItem(int position) {
            if (position < getCount()) {
                return fragments.get(position);
            }
            return null;
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position < getCount()) {
                return titles.get(position);
            }
            return null;
        }

        void addFrag(Fragment frag, String title) {
            fragments.add(frag);
            titles.add(title);
        }

    }
}
