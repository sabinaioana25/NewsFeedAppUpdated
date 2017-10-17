package com.example.android.newsfeedapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

/**
 * Created by Sabina on 10/16/2017.
 */

public class SearchActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<Article>> {

    private final static int CARD_TYPE = 0;
    private final static int REQUEST_ID = 0;
    private final static String REQUES_URL_BASE = "http://content.guardianapis.com/search?q=";
    private final static String REQUEST_URL_KEY = "&api-key=test";

    private ArticleAdapter articleAdapter;
    private String urlSearchRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //Searched item
        Bundle extras = getIntent().getExtras();
        urlSearchRequest = REQUES_URL_BASE + extras.getString("search") + REQUEST_URL_KEY;

        // RecyclerView
        RecyclerView searchRecyclerView = (RecyclerView) findViewById(R.id.search_recycler_view);
        searchRecyclerView.setHasFixedSize(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        searchRecyclerView.setLayoutManager(linearLayoutManager);
        articleAdapter = new ArticleAdapter(CARD_TYPE);
        searchRecyclerView.setAdapter(articleAdapter);

        // InitLoader
        getSupportLoaderManager().initLoader(REQUEST_ID, null, this).forceLoad();
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
                Intent intent = new Intent(SearchActivity.this, SearchActivity.class);
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
    public Loader<List<Article>> onCreateLoader(int id, Bundle args) {
        return new ArticleLoader(this, urlSearchRequest);
    }

    @Override
    public void onLoadFinished(Loader<List<Article>> loader, List<Article> data) {
        articleAdapter.addAll(data);

    }

    @Override
    public void onLoaderReset(Loader<List<Article>> loader) {

    }
}
