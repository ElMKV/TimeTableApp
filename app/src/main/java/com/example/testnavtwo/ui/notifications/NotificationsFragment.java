package com.example.testnavtwo.ui.notifications;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.testnavtwo.R;

import static android.content.Context.MODE_PRIVATE;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;


    EditText editText;
    EditText editText1;
    final String SAVED_TEXT = "saved_text";
    private SharedPreferences mSettings;
    SharedPreferences sPref;





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        editText = root.findViewById(R.id.editText);





        return root;
    }


    @Override
    public void onStart() {

        super.onStart();
        sPref = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);

        String savedText = sPref.getString(SAVED_TEXT, "");
        editText.setText(savedText);
        Toast.makeText(getActivity(), "Text loaded", Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onStop() {
        super.onStop();
        SharedPreferences preferences = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);



        SharedPreferences.Editor ed = preferences.edit();
        ed.putString(SAVED_TEXT, editText.getText().toString());

        ed.commit();
        Toast.makeText(getActivity(), "Расписание сохранено", Toast.LENGTH_SHORT).show();
    }









}
