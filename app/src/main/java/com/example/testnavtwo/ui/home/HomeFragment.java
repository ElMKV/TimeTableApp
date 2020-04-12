package com.example.testnavtwo.ui.home;

import android.content.ClipData;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testnavtwo.MainActivity;
import com.example.testnavtwo.R;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class HomeFragment extends Fragment {

    private TextView textViewMain;
    private TextView fileNameTextView;
    private TextView dataTextView;
    private WebView webView;
    public String filename;







    private HomeViewModel homeViewModel;
    private Bundle savedInstanceState;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View homeView = inflater.inflate(R.layout.fragment_home, container, false);

        fileNameTextView = homeView.findViewById(R.id.fileNameTextView);
        dataTextView = homeView.findViewById(R.id.dataTextVie);

        textViewMain = homeView.findViewById(R.id.textViewMain);




        return homeView;
    }

    @Override
    public void onStart() {

        //Получение даты
        SimpleDateFormat sdf = new SimpleDateFormat("dd-EEEE-yyyy");
        String date = sdf.format(Calendar.getInstance().getTime());
        dataTextView.setText("Сегодня - " + date);
        MainActivity xxx = (MainActivity) getActivity();
        filename = xxx.textOnWebActivity;
        if (xxx.textOnWebActivity != null)
        {
            filename = xxx.textOnWebActivity;
        }
        else
        {
            filename = "";
        }
        System.setProperty("org.apache.poi.javax.xml.stream.XMLInputFactory", "com.fasterxml.aalto.stax.InputFactoryImpl");
        System.setProperty("org.apache.poi.javax.xml.stream.XMLOutputFactory", "com.fasterxml.aalto.stax.OutputFactoryImpl");
        System.setProperty("org.apache.poi.javax.xml.stream.XMLEventFactory", "com.fasterxml.aalto.stax.EventFactoryImpl");
        try (FileInputStream fileInputStream = new FileInputStream(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), filename))) {


            // открываем файл и считываем его содержимое в объект XWPFDocument
            XWPFDocument docxFile = new XWPFDocument(OPCPackage.open(fileInputStream));
            XWPFHeaderFooterPolicy headerFooterPolicy = new XWPFHeaderFooterPolicy(docxFile);

            // печатаем все содержимое Word файла
            XWPFWordExtractor extractor = new XWPFWordExtractor(docxFile);

            //получаем текст из экстратора

            String DocxText = extractor.getText();

            fileNameTextView.setText(filename);
            DocxText.replaceAll("[\\s&&[^\r?\n]]{2,}", " ")
                    .replaceAll("( ?(\r\n)){2,}", "\r\n").replaceAll("( ?\n){2,}", "\n")
                    .replaceAll("^ ", "");
            textViewMain.setText(DocxText);

            System.out.println(DocxText);


        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }

        super.onStart();

    }

}
