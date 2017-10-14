package com.example.android.newsfeedapp;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import org.json.JSONException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Sabina on 10/5/2017.
 */

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {

    private List<Article> articles;
    public int cardType = 0;
    public static final String LOG_TAG = FragmentTopStories.class.getSimpleName();

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
        holder.cardViewPublishedDate.setText(getFormatedDate(articles.get(position).getPublishedDate()));
        holder.cardViewSection.setText(articles.get(position).getSection());
    }

    public String getFormatedDate(String publishedDate) {
        if (publishedDate != null && publishedDate.isEmpty()) {
            String jsonDatePattern = "yyyy-MM-dd'T'HH:mm:ssZ";
            SimpleDateFormat jsonFormatter = new SimpleDateFormat(jsonDatePattern, Locale.getDefault());

            try {
                Date parsedJsonDate = jsonFormatter.parse(publishedDate);
                String finalDatePattern = "yyyy-MM-dd HH:mm";
                SimpleDateFormat finalDateFormatter = new SimpleDateFormat(finalDatePattern, Locale.getDefault());
                return finalDateFormatter.format(parsedJsonDate);
            } catch (ParseException e) {
                Log.e(LOG_TAG, "Error parsing the JSON date", e);
            }
        }
        return " ";
    }

    @Override
    public int getItemCount() {
        if (articles != null) {
            return articles.size();
        }
        return 0;
    }

    public void addAll(List<Article> articles) {
        this.articles = articles;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView cardViewTitle;
        public TextView cardViewPublishedDate;
        public TextView cardViewSection;

        public ViewHolder(View itemView) throws JSONException {
            super(itemView);

            cardViewTitle = (TextView) itemView.findViewById(R.id.article_title);
            cardViewPublishedDate = (TextView) itemView.findViewById(R.id.article_date);
            cardViewSection = (TextView) itemView.findViewById(R.id.article_section);


        }
    }

    public void clearAll() {
        this.articles = null;
        notifyDataSetChanged();
    }
}
