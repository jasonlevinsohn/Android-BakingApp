package com.llamasontheloosefarm.bakingapp2.utilities;

import android.content.Context;
import android.net.Uri;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

public final class NetworkUtils {

    private static final String RECIPE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";


    public static URL buildUrl(Context context) {

        Uri builtUri = Uri.parse(RECIPE_URL).buildUpon().build();

        URL url = null;

        try {
            url = new URL(builtUri.toString());
            Timber.d("URL: " + url);
        } catch (MalformedURLException e) {
            Timber.d("URL Exception: The URL was not build correctly");
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {

        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();

        Request request = new Request.Builder()
                .url(url)
                .build();

        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException io) {
            Timber.d("Error making HTTP request to get the JSON Recipes");
            io.printStackTrace();
            return null;
        }


    }
}
