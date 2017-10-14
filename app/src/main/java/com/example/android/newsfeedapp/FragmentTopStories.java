package com.example.android.newsfeedapp;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

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
            "http://content.guardianapis.com/search?q=film&api-key=c76396a7-7a54-46d9-a768-3f0cc1fedc5c" ;
    private static final int NEWS_LOADER_ID = 0;
    ArticleAdapter articleAdapter;

    private TextView emptyTextView;
    private ProgressBar progressBar;

    public FragmentTopStories() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_top_stories, container, false);
        setHasOptionsMenu(true);
        articleAdapter = new ArticleAdapter(CARD_TYPE);

        RecyclerView.LayoutManager layoutManagerTop = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        RecyclerView recyclerViewTopStories = (RecyclerView) rootView.findViewById(R.id.recycler_view_top_stories);
        recyclerViewTopStories.setAdapter(articleAdapter);
        recyclerViewTopStories.setHasFixedSize(false);
        recyclerViewTopStories.setNestedScrollingEnabled(true);
        recyclerViewTopStories.setLayoutManager(layoutManagerTop);

        progressBar = (ProgressBar) rootView.findViewById(R.id.progress_bar);
        emptyTextView = (TextView) rootView.findViewById(R.id.empty_text_view);

        if (isConnected()) {
            getLoaderManager().initLoader(NEWS_LOADER_ID, null, this).forceLoad();
            emptyTextView.setText("Fetching articles...");
        } else {
            progressBar.setVisibility(View.GONE);
            emptyTextView.setText("No internet connection");
        }
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        return;
    }

    public boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    @Override
    public Loader<List<Article>> onCreateLoader(int id, Bundle args) {
        return new ArticleLoader(getContext(), ARTICLE_REQUEST_TOP_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Article>> loader, List<Article> data) {
        if (data != null && !data.isEmpty()) {
            articleAdapter.addAll(data);
            progressBar.setVisibility(View.GONE);
            emptyTextView.setVisibility(View.GONE);
        }
        Log.v("FragmentTopStories", "Loader completed the task");
    }

    @Override
    public void onLoaderReset(Loader<List<Article>> loader) {
    }
}