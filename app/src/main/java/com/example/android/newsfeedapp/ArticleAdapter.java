package com.example.android.newsfeedapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sabina on 10/5/2017.
 */

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {

    private List<Article> articles;
    public int cardType = 0;

    /**
     * Tag for the log messages
     */

    public ArticleAdapter(int cardType) {
        this.cardType = cardType;
    }

    @Override
    public ArticleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_item, parent, false);
        try {
            return new ViewHolder(view);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onBindViewHolder(ArticleAdapter.ViewHolder holder, int position) {
        holder.cardViewTitle.setText(articles.get(position).getTitle());
        holder.cardViewPublishedDate.setText(articles.get(position).getPublishedDate());
        holder.cardViewSection.setText(articles.get(position).getSection());
        holder.webUrl = articles.get(position).getWebUrl();
    }

    @Override
    public int getItemCount() {
        if (articles != null) {
            return articles.size();
        }
        return 0;
    }

    public void swapData(List<Article> newArticleList) {
        if (newArticleList != null && newArticleList.size() > 0) {
            articles.clear();
            if (newArticleList == null) {
                articles = new ArrayList<Article>();
            } else {
                articles.addAll(newArticleList);
            }
        } else {
            articles = (ArrayList<Article>) newArticleList;
        }
        notifyDataSetChanged();
    }

    public void addAll(List<Article> articles) {
        this.articles = articles;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView cardViewTitle;
        public TextView cardViewPublishedDate;
        public TextView cardViewSection;
        public String webUrl;
        private final Context context;
        public ImageView bookmarkIconFull;
        public ImageView bookMarkIconBorder;

        public ViewHolder(final View itemView) throws JSONException {
            super(itemView);
            context = itemView.getContext();

            cardViewTitle = (TextView) itemView.findViewById(R.id.article_title);
            cardViewPublishedDate = (TextView) itemView.findViewById(R.id.article_date);
            cardViewSection = (TextView) itemView.findViewById(R.id.article_section);

            bookmarkIconFull = (ImageView) itemView.findViewById(R.id.article_bookmark_image_full);
            bookMarkIconBorder = (ImageView) itemView.findViewById(R.id.article_bookmark_image);

            bookMarkIconBorder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bookMarkIconBorder.setVisibility(View.GONE);
                    bookmarkIconFull.setVisibility(View.VISIBLE);
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri articleUri = Uri.parse(webUrl);
                    Intent webIntent = new Intent(Intent.ACTION_VIEW, articleUri);
                    if (webIntent.resolveActivity(context.getPackageManager()) !=null) {
                        context.startActivity(webIntent);
                    }
                }
            });
        }
    }

    public void clearAll() {
        this.articles = null;
        notifyDataSetChanged();
    }
}
