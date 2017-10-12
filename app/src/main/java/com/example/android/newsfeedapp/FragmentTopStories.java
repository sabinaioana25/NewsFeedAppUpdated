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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTopStories extends Fragment implements LoaderManager.LoaderCallbacks<List<Article>> {

    private RecyclerView recyclerView;
    private ArticleAdapter articleAdapter =null;
    private static final String LOG_TAG = FragmentTopStories.class.getSimpleName();
    private static final String ARTICLE_REQUEST_TOP_URL =
            "https://content.guardianapis.com/business/2014/feb/18/uk-inflation-falls-below-bank-england-target";
    private static final int NEWS_LOADER_ID = 1;
    private List<Article> articles;
    public FragmentTopStories() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_fragment_top_stories, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_top_stories);
        articleAdapter = new ArticleAdapter(getContext(), articles
        );
        recyclerView.setAdapter(articleAdapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        if (isConnected()) {
            getLoaderManager().initLoader(NEWS_LOADER_ID, null, this).forceLoad();
        }

        articles = new ArrayList<>();
        for (int i=0; i<12;i++) {
            Article article = new Article("heading" +(i+1), "Lorem ipsum dummy text");
            articles.add(article);
        }
        articleAdapter = new ArticleAdapter(getContext(), articles);
        recyclerView.setAdapter(articleAdapter);
        return rootView;
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
    }

    @Override
    public void onLoaderReset(Loader<List<Article>> loader) {
    }
}