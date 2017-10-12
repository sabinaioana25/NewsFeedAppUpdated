package com.example.android.newsfeedapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;

import java.util.List;

/**
 * Created by Sabina on 10/5/2017.
 */

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {

    private List<Article> articles = null;
    private Context context;
    private String url;

//    public ArticleAdapter(Context context) {
//        this.context = context;
//    }

    public ArticleAdapter(Context context, List<Article> articles) {
        this.context = context;
        this.articles = articles;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_item, parent, false);
        try {
            return new ViewHolder(view);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Article article = articles.get(position);
        holder.cardViewTitle.setText(article.getTitle());
        holder.cardViewAuthor.setText(article.getAuthor());
    }

    @Override
    public int getItemCount() {
        if (articles != null) {
            return articles.size();
        } return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView cardViewTitle;
        private TextView cardViewAuthor;


        public ViewHolder(View itemView) throws JSONException {
            super(itemView);
            cardViewTitle = (TextView) itemView.findViewById(R.id.article_title);
            cardViewAuthor = (TextView) itemView.findViewById(R.id.article_author);
        }
    }

    public void addAll(List<Article> articles) {
        this.articles = articles;
        notifyDataSetChanged();
    }
}
