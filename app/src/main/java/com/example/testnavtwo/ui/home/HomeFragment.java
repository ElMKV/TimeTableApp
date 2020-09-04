package com.example.testnavtwo.ui.home;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testnavtwo.MainActivity;
import com.example.testnavtwo.R;
import com.example.testnavtwo.firstStepActivity;

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


    public String filename;
    final String SAVED_TEXTDocx = "SaveTextDocx";
    SharedPreferences sPref;










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

        SharedPreferences preferences = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);

        textViewMain.setText(preferences.getString(SAVED_TEXTDocx,"Перейди на сайт и скачай интересующую тебя замену пар"));

        super.onStart();
    }

    @Override
    public void onResume() {
        //Для сохранения состояния активити
        sPref = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        boolean hasVisited = sPref.getBoolean("hasVisited", false);//Переменная для получения значения первого запуска приложения

        if (!hasVisited) { //Смени на !hasVisited или hasVisited == true
            // выводим нужную активность
            Intent intent = new Intent(getActivity(), firstStepActivity.class);
            startActivity(intent);
            SharedPreferences.Editor e = sPref.edit();
            e.putBoolean("hasVisited", true);
            e.commit(); // не забудьте подтвердить изменения

        }
        String savedTextEditTextDocxFile = sPref.getString(SAVED_TEXTDocx, "Перейди на сайт и скачай интересующую тебя замену пар");


        //Получение даты
        SimpleDateFormat sdf = new SimpleDateFormat("dd-EEEE-yyyy");
        String date = sdf.format(Calendar.getInstance().getTime());
        dataTextView.setText("Сегодня - " + date);
        MainActivity xxx = (MainActivity) getActivity();
        filename = xxx.textOnWebActivity;

        if (xxx.textOnWebActivity != null)
        {


            filename = xxx.textOnWebActivity;
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

                fileNameTextView.setText(filename);//удаляем все копии знаков боле 1
                DocxText.replaceAll("[\\s&&[^\r?\n]]{2,}", " ")
                        .replaceAll("( ?(\r\n)){2,}", "\r\n").replaceAll("( ?\n){2,}", "\n")
                        .replaceAll("^ ", "");



                textViewMain.setText(DocxText);
                System.out.println(DocxText);
                SharedPreferences preferences = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);



                SharedPreferences.Editor ed = preferences.edit();
                ed.putString(SAVED_TEXTDocx, textViewMain.getText().toString());



                ed.commit();



                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), filename);
                file.delete();
                System.out.println(filename);

            } catch (FileNotFoundException e) {

                e.printStackTrace();
            } catch (IOException e) {

                e.printStackTrace();
            } catch (InvalidFormatException e) {
                e.printStackTrace();
            }
        }
        else
        {
            textViewMain.setText(savedTextEditTextDocxFile);
        }
        super.onResume();





    }
    @Override
    public void onStop() {
        super.onStop();


    }

}
