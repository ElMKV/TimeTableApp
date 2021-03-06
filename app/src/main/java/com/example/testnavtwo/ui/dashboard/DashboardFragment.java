package com.example.testnavtwo.ui.dashboard;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.Gravity;
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
import androidx.navigation.ui.NavigationUI;

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

    public String filenameFromWeb;

    Intent toInfoClass;
    ProgressBar progressBar;







    private DashboardViewModel dashboardViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);


        webView = view.findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true); // allow pinch to zooom
        webView.getSettings().setDisplayZoomControls(false); // disable the default zoom controls on the page

        progressBar = view.findViewById(R.id.progressBar3);






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
                        downloadDialog(url, userAgent, contentDisposition, mimetype);


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
        builder.setMessage("Вы хотите сохранить " + filenameFromWeb);//Имя файла расписания

        //if Yes button clicks.
        builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (filenameFromWeb.endsWith("docx")) {




                    System.out.println("Загрузка");
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
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
                    //DownloadManager created



                    final DownloadManager downloadManager = (DownloadManager) getActivity().getSystemService(DOWNLOAD_SERVICE);

                    final long downloadId = downloadManager.enqueue(request);




                    new Thread(new Runnable() {

                        @Override
                        public void run() {

                            boolean downloading = true;

                            while (downloading) {

                                DownloadManager.Query q = new DownloadManager.Query();
                                q.setFilterById(downloadId);

                                Cursor cursor = downloadManager.query(q);
                                cursor.moveToFirst();
                                int bytes_downloaded = cursor.getInt(cursor
                                        .getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                                int bytes_total = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));


                                if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
                                    downloading = false;
                                }

                                final double dl_progress = (bytes_downloaded / bytes_total)*100 ;

                                getActivity().runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {

                                        progressBar.setProgress((int) dl_progress);
                                        if (dl_progress==100)
                                        {


                                            //создаём и отображаем текстовое уведомление
                                            Toast toast = Toast.makeText(getContext(),
                                                    "Файл" + filenameFromWeb + "загружен!",
                                                    Toast.LENGTH_SHORT);
                                            toast.setGravity(Gravity.CENTER, 0, 0);
                                            toast.show();




                                        }


                                    }
                                });


                                cursor.close();
                            }

                        }
                    }).start();


                    //Saving files in Download folder
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filenameFromWeb);

                    //download enqued
                    downloadManager.enqueue(request);





                }
                else {
                    Toast toast = Toast.makeText(getContext(),
                            "Мы работаем только с форматом docx",
                            Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
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


        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

            super.onPageStarted(view, url, favicon);

        }

        @Override
        public void onPageFinished(WebView view, String url) {

        }
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





































