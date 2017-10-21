package com.example.android.newsfeedapp;

import android.content.Context;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sabina on 10/8/2017.
 */

public class QueryUtils {

    /**
     * Specific Keys for JSON Parsing
     **/
    private static final String KEY_RESPONSE = "response";
    private static final String KEY_RESULTS = "results";
    private static final String KEY_SECTION = "sectionName";
    private static final String KEY_DATE = "webPublicationDate";
    private static final String KEY_TITLE = "webTitle";
    private static final String KEY_WEB_URL = "webUrl";

    private QueryUtils() {
    }

    public static List<Article> fetchArticleData(String requestUrl, Context context) {
        URL url = createUrl(requestUrl);
        String jsonResponse = null;

        try {
            jsonResponse = makeHttpRequest(url, context);
        } catch (IOException e) {
        }

        List<Article> articles = extractFeatureFromJson(jsonResponse);
        return articles;
    }

    public static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
        }
        return url;
    }

    public static String makeHttpRequest(URL url, Context context) throws IOException {
        String jsonResponse = null;
        if (url == null) {
            return jsonResponse;
        }
        final Context context1 = context;
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
            }
        } catch (IOException e) {

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }

            if (inputStream != null) {
                inputStream.close();
            }
            return jsonResponse;
        }
    }

    public static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    public static List<Article> extractFeatureFromJson(String jsonResponse) {
        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }

        // Create an empty List<NewsItem>
        List<Article> articles = new ArrayList<Article>();

        try {
            // Build the list of Article Objects
            JSONObject baseJsonResponse = new JSONObject(jsonResponse);
            JSONObject responseJsonObject = baseJsonResponse.getJSONObject(KEY_RESPONSE);
            JSONArray newsResult = responseJsonObject.getJSONArray(KEY_RESULTS);

            // Variables for JSON parsing
            String title;
            String section;
            String publishedDate;
            String webUrl;

            for (int i = 0; i < newsResult.length(); i++) {
                JSONObject newsArticle = newsResult.getJSONObject(i);

                // Check if title exists
                if (newsArticle.has(KEY_TITLE)) {
                    title = newsArticle.getString(KEY_TITLE);
                } else {
                    title = null;
                }

                // Check if section exists
                if (newsArticle.has(KEY_SECTION)) {
                    section = newsArticle.getString(KEY_SECTION);
                } else {
                    section = null;
                }

                // Check if publishing date exists
                if (newsArticle.has(KEY_DATE)) {
                    publishedDate = newsArticle.getString(KEY_DATE);
                } else {
                    publishedDate = null;
                }

                // Check if webUrl exists
                if (newsArticle.has(KEY_WEB_URL)) {
                    webUrl = newsArticle.getString(KEY_WEB_URL);
                } else {
                    webUrl = null;
                }

                String newDate;
                newDate = publishedDate.substring(0, 10) + ", " + publishedDate.substring(11, 16);

                Article article = new Article(title, section, newDate, webUrl);
                articles.add(article);
            }
        } catch (JSONException e) {
        }
        return articles;
    }
}






























