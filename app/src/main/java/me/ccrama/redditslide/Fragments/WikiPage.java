package me.ccrama.redditslide.Fragments;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import net.dean.jraw.managers.WikiManager;

import org.apache.commons.text.StringEscapeUtils;

import java.lang.ref.WeakReference;

import me.ccrama.redditslide.Activities.Wiki;
import me.ccrama.redditslide.BuildConfig;
import me.ccrama.redditslide.Constants;
import me.ccrama.redditslide.OpenRedditLink;
import me.ccrama.redditslide.R;
import me.ccrama.redditslide.Views.GeneralSwipeRefreshLayout;
import me.ccrama.redditslide.Visuals.Palette;


public class WikiPage extends Fragment {
    private String title;
    private String subreddit;
    private String wikiUrl;

    private WikiPageListener listener;

    private WebView webView;
    private GeneralSwipeRefreshLayout ref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.justtext, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ref = view.findViewById(R.id.ref);
        webView = view.findViewById(R.id.wiki_web_view);

        setUpRefresh();
        setUpWebView();

        if (getActivity() != null) {
            new WikiAsyncTask(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
                    ((Wiki) getActivity()).wiki, subreddit, title);
        }
    }

    private void setUpRefresh() {
        ref.setColorSchemeColors(Palette.getColors(subreddit, getActivity()));

        //If we use 'findViewById(R.id.header).getMeasuredHeight()', 0 is always returned.
        //So, we estimate the height of the header in dp
        //Something isn't right with the Wiki layout though, so use the SINGLE_HEADER instead.
        ref.setProgressViewOffset(false,
                Constants.SINGLE_HEADER_VIEW_OFFSET - Constants.PTR_OFFSET_TOP,
                Constants.SINGLE_HEADER_VIEW_OFFSET + Constants.PTR_OFFSET_BOTTOM);
        ref.post(new Runnable() {
            @Override
            public void run() {
                ref.setRefreshing(true);
            }
        });
    }

    private void setUpWebView() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new WikiPageJavaScriptInterface(), "Cash");

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.toLowerCase().startsWith(wikiUrl.toLowerCase()) && listener != null) {
                    String pagePiece = url.toLowerCase().replace(wikiUrl.toLowerCase(), "")
                            .split("\\?")[0]
                            .split("#")[0];
                    listener.embeddedWikiLinkClicked(pagePiece);
                } else {
                    OpenRedditLink.openUrl(getContext(), url, true);
                }
                return true;
            }

            @Override
            public void onPageFinished(WebView webView, String url) {
                super.onPageFinished(webView, url);
                if (getView() != null) {
                    getView().findViewById(R.id.wiki_web_view).setVisibility(View.VISIBLE);
                    ref.setRefreshing(false);
                    ref.setEnabled(false);
                }
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG);
        }
    }

    private void onDomRetrieved(String dom) {
        webView.loadDataWithBaseURL(
                wikiUrl,
                "<head>".concat(Wiki.getGlobalCustomCss())
                        .concat(Wiki.getGlobalCustomJavaScript())
                        .concat("</head>")
                        .concat(dom),
                "text/html",
                "utf-8",
                null);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        title = bundle.getString("title", "");
        subreddit = bundle.getString("subreddit", "");
        wikiUrl = "https://www.reddit.com/r/".concat(subreddit).concat("/wiki/");
    }

    public void setListener(WikiPageListener listener) {
        this.listener = listener;
    }

    private static class WikiAsyncTask extends AsyncTask<Object, Void, String> {
        WeakReference<WikiPage> wikiPageWeakReference;

        WikiAsyncTask(WikiPage wikiPage) {
            wikiPageWeakReference = new WeakReference<>(wikiPage);
        }

        @Override
        protected String doInBackground(Object[] params) {
            return StringEscapeUtils.unescapeHtml4(
                    ((WikiManager) params[0]).get((String) params[1], (String) params[2]).getDataNode().get("content_html").asText());
        }

        @Override
        protected void onPostExecute(String dom) {
            if (wikiPageWeakReference.get() != null) {
                wikiPageWeakReference.get().onDomRetrieved(dom);
            }
        }
    }

    private class WikiPageJavaScriptInterface {
        @JavascriptInterface
        public void overflowTouched() {
            listener.overflowTouched();
        }
    }

    public interface WikiPageListener {
        void embeddedWikiLinkClicked(String wikiPageTitle);

        void overflowTouched();
    }
}