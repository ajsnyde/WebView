package com.asaddisonsnyder.webview;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


public class BrowserTest extends AppCompatActivity {
    WebView webview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

    String url = "https://regexcrossword.com/challenges/hexagonal/puzzles/1";
    super.onCreate(savedInstanceState);
    webview = new WebView(this);
    WebSettings webSettings = webview.getSettings();
    webSettings.setJavaScriptEnabled(true);
    webview.setWebViewClient(new WebViewClient());
    setContentView(webview);
    new DownloadHTMLTask().execute(url);
    //webview.loadData("", "text/html", "UTF-8");
    //webview.loadUrl("http://addisonsnyder.com");
}

    private class DownloadHTMLTask extends AsyncTask<String, Void, String> {
        /** The system calls this to perform work in a worker thread and
         * delivers it the parameters given to AsyncTask.execute() */
        protected String doInBackground(String... urls) {
            String html = "";
            try {
                URL oracle = new URL(urls[0]);
                URLConnection yc = oracle.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));

                String inputLine;
                while ((inputLine = in.readLine()) != null)
                    html += inputLine;
                in.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            html = html.replaceAll("<footer\\b[^>]*>(.*?)</footer>", "");

            return html;
        }
        protected void onPostExecute(String result) {
            webview.loadDataWithBaseURL("https://regexcrossword.com/challenges/hexagonal/puzzles/1", result, "text/html", "utf-8", "");
            //webview.loadData(result, "text/html", "UTF-8");
        }
    }
}
