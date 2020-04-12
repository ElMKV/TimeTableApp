package com.example.testnavtwo.ui.dashboard;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.testnavtwo.MainActivity;
import com.example.testnavtwo.R;
import com.example.testnavtwo.ui.home.HomeFragment;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;

import java.io.File;
import java.util.ArrayList;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.content.ContentValues.TAG;
import static android.content.Context.DOWNLOAD_SERVICE;
import static org.apache.xmlbeans.XmlBeans.getTitle;

public class DashboardFragment extends Fragment {

    private WebView webView;
    private ProgressBar progressBar;
    public String filenameFromWeb;
    private Button download;
    Intent toInfoClass;






    private DashboardViewModel dashboardViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        webView = view.findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());


        webView.loadUrl("http://www.permaviat.ru/raspisanie-zamen/");
        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(final String url, final String userAgent, String contentDisposition, String mimetype, long contentLength) {
                //Checking runtime permission for devices above Marshmallow.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ActivityCompat.checkSelfPermission(getContext(),android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_GRANTED) {
                        Log.v(TAG, "Permission is granted");
                        downloadDialog(url, userAgent, contentDisposition, mimetype);



                    } else {

                        Log.v(TAG, "Permission is revoked");
                        //requesting permissions.
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

                    }
                } else {
                    //Code for devices below API 23 or Marshmallow
                    Log.v(TAG, "Permission is granted");
                    downloadDialog(url, userAgent, contentDisposition, mimetype);

                }
            }
        });




        return view;
    }

    public void downloadDialog(final String url,final String userAgent,String contentDisposition,String mimetype) {
        //getting filename from url.
        filenameFromWeb = URLUtil.guessFileName(url, contentDisposition, mimetype);
        //alertdialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //title of alertdialog
        builder.setTitle("Загрузка");
        //message of alertdialog
        builder.setMessage("Вы хотите сохранить " + filenameFromWeb);//Имя файла расаеписания
        //if Yes button clicks.
        builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //DownloadManager.Request created with url.
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                //cookie
                String cookie = CookieManager.getInstance().getCookie(url);
                //Add cookie and User-Agent to request
                request.addRequestHeader("Cookie", cookie);
                request.addRequestHeader("User-Agent", userAgent);
                //file scanned by MediaScannar
                request.allowScanningByMediaScanner();
                //Download is visible and its progress, after completion too.
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                //DownloadManager created
                DownloadManager downloadManager = (DownloadManager) getActivity().getSystemService(DOWNLOAD_SERVICE);
                //Saving files in Download folder
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filenameFromWeb);
                //download enqued
                downloadManager.enqueue(request);
            }
        });
        builder.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //cancel the dialog if Cancel clicks
                dialog.cancel();
            }

        });
        //alertdialog shows.
        builder.create().show();




    }

    WebViewClient webViewClient = new WebViewClient() {
        @SuppressWarnings("deprecation") @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @TargetApi(Build.VERSION_CODES.N) @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            return true;
        }
    };

    private class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(final WebView myWebView, final String url,
                                  final Bitmap favicon) {
            ProgressBar bar = (ProgressBar) myWebView.findViewById(R.id.progressBar);

            bar.setVisibility(View.VISIBLE);
            super.onPageStarted(myWebView, url, favicon);

        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String url) {
            webView.loadUrl(url);
            return true;
        }
        @Override
        public void onPageFinished(WebView webView, String url) {
            super.onPageFinished(webView, url);
            progressBar.setVisibility(View.GONE);
        }
    }

    public interface OnFragment1DataListener {
        void onFragment1DataListener(String string);
    }
    private OnFragment1DataListener mListener;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragment1DataListener) {
            mListener = (OnFragment1DataListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragment1DataListener");
        }
    }
    //Передача названия файла в MainActivity
    @Override
    public void onPause() {


        mListener.onFragment1DataListener(filenameFromWeb);

        super.onPause();
    }

}





































