package com.example.android.newsfeedapp;

import android.content.Context;

import java.util.List;

/**
 * Created by Sabina on 10/8/2017.
 */

public class ArticleLoader extends android.support.v4.content.AsyncTaskLoader<List<Article>> {
    private static final String LOG_TAG = FragmentTopStories.class.getName();
    private String url;

    public ArticleLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Article> loadInBackground() {

        if (url == null) {
            return null;
        }

        List<Article> articles = QueryUtils.fetchArticleData(url);
        return articles;
    }
}
