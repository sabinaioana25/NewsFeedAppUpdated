package com.example.android.newsfeedapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        FragmentAdapter fragmentAdapter = new FragmentAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(fragmentAdapter);

        final TabLayout fragmentTab = (TabLayout) findViewById(R.id.tab_layout);
        fragmentTab.setupWithViewPager(viewPager);

        for (int i = 0; i < fragmentTab.getTabCount(); i++) {
            fragmentTab.setTabGravity(TabLayout.GRAVITY_FILL);

            viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position) {
                    fragmentTab.getTabAt(position).select();
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });

            fragmentTab.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    super.onTabSelected(tab);
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) item.getActionView();
        searchView.setIconified(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                intent.putExtra("search", query);
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (item.getItemId()) {
            case R.id.action_settings:
                openSettingsScreen();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openSettingsScreen() {
        startActivity(new Intent(MainActivity.this, SettingsActivity.class));
    }
}