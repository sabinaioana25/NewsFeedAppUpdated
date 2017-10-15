package com.example.android.newsfeedapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTopStories extends Fragment
        implements LoaderManager.LoaderCallbacks<List<Article>> {
    /**
     * Tag for the log messages
     */
    private static final String LOG_TAG = FragmentTopStories.class.getSimpleName();
    private static final int CARD_TYPE = 1;
    private static final String ARTICLE_REQUEST_TOP_URL =
            "https://content.guardianapis.com/search?q=cinema&format=json&&show-tags=contributor&show-fields=starRating,headline,thumbnail,short-url&show-refinements=all&order-by=relevance&api-key=c76396a7-7a54-46d9-a768-3f0cc1fedc5c";
    private static final int NEWS_LOADER_ID = 0;
    ArticleAdapter articleAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView emptyTextView;
    private ProgressBar progressBar;

    private final String API_KEY = "api-key";
    private static final String KEY = "c76396a7-7a54-46d9-a768-3f0cc1fedc5c";

    public FragmentTopStories() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_top_stories, container, false);
        setHasOptionsMenu(true);
        articleAdapter = new ArticleAdapter(CARD_TYPE);

        RecyclerView.LayoutManager layoutManagerTop = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        RecyclerView recyclerViewTopStories = (RecyclerView) rootView.findViewById(R.id.recycler_view_top_stories);
        recyclerViewTopStories.setAdapter(articleAdapter);
        recyclerViewTopStories.setHasFixedSize(false);
        recyclerViewTopStories.setNestedScrollingEnabled(true);
        recyclerViewTopStories.setLayoutManager(layoutManagerTop);


        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_recycler_view);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progress_bar);
        emptyTextView = (TextView) rootView.findViewById(R.id.empty_text_view);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshRecyclerView();
            }
        });

        if (isConnected()) {
            getLoaderManager().initLoader(NEWS_LOADER_ID, null, this).forceLoad();
            emptyTextView.setText(R.string.fetching_articles);
        } else {
            progressBar.setVisibility(View.GONE);
            emptyTextView.setText(R.string.no_internet_connection);
        }
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        return;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            refreshRecyclerView();
            return true;
        } else if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(getContext(), SettingsActivity.class);
        }

        return true;
    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        if (id == R.id.action_refresh) {
//            refreshRecyclerView();
//            return true;
//        } else if (id == R.id.action_settings) {
//            Intent settingsIntent = new Intent(getActivity(), SettingsActivity.class);
//            startActivity(settingsIntent);
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    public boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    @Override
    public Loader<List<Article>> onCreateLoader(int id, Bundle args) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        return new ArticleLoader(getContext(), ARTICLE_REQUEST_TOP_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Article>> loader, List<Article> data) {
        if (data != null && !data.isEmpty()) {
            articleAdapter.addAll(data);
            progressBar.setVisibility(View.GONE);
            emptyTextView.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
        }
        Log.v("FragmentTopStories", "Loader completed the task");
    }

    @Override
    public void onLoaderReset(Loader<List<Article>> loader) {
        articleAdapter.clearAll();
    }

    public void refreshRecyclerView() {
        if (isConnected()) {
            getLoaderManager().restartLoader(0, null, FragmentTopStories.this);
        } else {
            swipeRefreshLayout.setRefreshing(false);

            if (articleAdapter instanceof ArticleAdapter) {
                ((ArticleAdapter) articleAdapter).swapData(new ArrayList<Article>());
            }

            emptyTextView.setText(R.string.no_internet_connection);
            emptyTextView.setVisibility(View.VISIBLE);
        }
    }
}