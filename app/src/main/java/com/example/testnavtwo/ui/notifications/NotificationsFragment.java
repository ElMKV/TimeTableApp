package com.example.testnavtwo.ui.notifications;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.testnavtwo.R;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;


    EditText editTextPn;

    final String SAVED_TEXTPnd = "SaveTextPnd";
    final String SAVED_TEXTVtr = "SaveTextVtr";
    final String SAVED_TEXTSrda = "SaveTextSrda";
    final String SAVED_TEXTCht = "SaveTextCht";
    final String SAVED_TEXTPtn = "SaveTextPtn";
    final String SAVED_TEXTSbb = "SaveTextSbb";
    private SharedPreferences mSettings;
    SharedPreferences sPref;
    private EditText editTextPnd;
    private EditText editTextVtr;
    private EditText editTextSrd;
    private EditText editTextCht;
    private EditText editTextPtn;
    private EditText editTextSbb;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        editTextPnd = root.findViewById(R.id.editTextPn);
        editTextVtr = root.findViewById(R.id.editTextVt);
        editTextSrd = root.findViewById(R.id.editTextSr);
        editTextCht = root.findViewById(R.id.editTextCh);
        editTextPtn = root.findViewById(R.id.editTextPt);
        editTextSbb = root.findViewById(R.id.editTextSb);

        TabHost tabHost = root.findViewById(R.id.tabHost);

        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("tag1");
        tabSpec = tabHost.newTabSpec("pnTab");
        tabSpec.setContent(R.id.pnTab);
        tabSpec.setIndicator("Пн");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("vtTab");
        tabSpec.setContent(R.id.vtTab);
        tabSpec.setIndicator("Вт");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("srTab");
        tabSpec.setContent(R.id.srTab);
        tabSpec.setIndicator("Ср");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("chTab");
        tabSpec.setContent(R.id.chTab);
        tabSpec.setIndicator("Чт");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("ptTab");
        tabSpec.setContent(R.id.ptTab);
        tabSpec.setIndicator("Пт");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("sbTab");
        tabSpec.setContent(R.id.sbTab);
        tabSpec.setIndicator("Сб");
        tabHost.addTab(tabSpec);

        tabHost.setCurrentTab(0);

        return root;
    }


    @Override
    public void onStart() {

        super.onStart();
        //Загрузка данных пользователя
        sPref = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        String savedTextEditTextPnd = sPref.getString(SAVED_TEXTPnd, "1.\n\n2.\n\n3.\n\n4.\n\n5.\n\n6.");
        String savedTextEditTextVtr = sPref.getString(SAVED_TEXTVtr, "1.\n\n2.\n\n3.\n\n4.\n\n5.\n\n6.");
        String savedTextEditTextSrd = sPref.getString(SAVED_TEXTSrda, "1.\n\n2.\n\n3.\n\n4.\n\n5.\n\n6.");


        String savedTextEditTextCht = sPref.getString(SAVED_TEXTCht, "1.\n\n2.\n\n3.\n\n4.\n\n5.\n\n6.");
        String savedTextEditTextPtn = sPref.getString(SAVED_TEXTPtn, "1.\n\n2.\n\n3.\n\n4.\n\n5.\n\n6.");
        String savedTextEditTextSbb = sPref.getString(SAVED_TEXTSbb, "1.\n\n2.\n\n3.\n\n4.\n\n5.\n\n6.");

        editTextPnd.setText(savedTextEditTextPnd);
        editTextVtr.setText(savedTextEditTextVtr);
        editTextSrd.setText(savedTextEditTextSrd);
        editTextCht.setText(savedTextEditTextCht);
        editTextPtn.setText(savedTextEditTextPtn);
        editTextSbb.setText(savedTextEditTextSbb);

    }

    @Override
    public void onStop() {
        super.onStop();
        SharedPreferences preferences = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = preferences.edit();
        ed.putString(SAVED_TEXTPnd, editTextPnd.getText().toString());
        ed.putString(SAVED_TEXTVtr, editTextVtr.getText().toString());
        ed.putString(SAVED_TEXTSrda, editTextSrd.getText().toString());
        ed.putString(SAVED_TEXTCht, editTextCht.getText().toString());

        ed.putString(SAVED_TEXTPtn, editTextPtn.getText().toString());
        ed.putString(SAVED_TEXTSbb, editTextSbb.getText().toString());


        ed.commit();

    }

}
