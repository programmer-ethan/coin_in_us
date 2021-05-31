package com.professionalandroid.apps.coin_in_us.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.professionalandroid.apps.coin_in_us.R;
import com.professionalandroid.apps.coin_in_us.MainActivity;

public class HomeFragment extends Fragment{

    private HomeViewModel homeViewModel;

    public void writeSharedPreference(View view){
//        EditText txtValue = (EditText) findViewById(R.id.saved_data);
//        String value = txtValue.getText().toString();
    // 1. get Shared Preference
    Context context = getActivity();
    SharedPreferences sharedPreference
            = context.getSharedPreferences("MYPREFRENCE", Context.MODE_MULTI_PROCESS | Context.MODE_PRIVATE);
    // 2. get Editor
    SharedPreferences.Editor editor = sharedPreference.edit();
    // 3. set Key values
    editor.putString("MYKEY","VALUE1");
    editor.putString("KEY2", "VALUE2");
    // 4. commit the values
    editor.commit();
}
//    public void readPreference(View view) throws Exception {
//        // get Context of other application
//        Context context = getActivity();
//
//        // getting Shared preference from other application
//        SharedPreferences pref
//                = context.getSharedPreferences("MYPREFRENCE", Context.MODE_PRIVATE | Context.MODE_MULTI_PROCESS);
//        String value = pref.getString("MYKEY", "NOTFOUND");
//        TextView txtValue = (TextView)findViewById(R.id.shared_data);
////        EditText txtValue = (EditText)findViewById(R.id.txtValue);
////        txtValue.setText(value);
//    }




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_interested, container, false);//false
        Context context = getActivity();
        SharedPreferences sharedPreference
                = context.getSharedPreferences("MYPREFRENCE", Context.MODE_MULTI_PROCESS | Context.MODE_PRIVATE);
        // 2. get Editor
        SharedPreferences.Editor editor = sharedPreference.edit();
        // 3. set Key values
        editor.putString("COIN_NAME_1","카카오");
        editor.putString("COIN_VALUE_1", "118,000");
        editor.putString("STOCK_NAME_1","비트코인");
        editor.putString("STOCK_VALUE_1", "59,440,000");
        // 4. commit the values
        editor.commit();
        // getting Shared preference from other application
        SharedPreferences pref
                = context.getSharedPreferences("MYPREFRENCE", Context.MODE_PRIVATE | Context.MODE_MULTI_PROCESS);
        String name1 = pref.getString("COIN_NAME_1", "NOTFOUND");
        String value1 = pref.getString("COIN_VALUE_1", "NOTFOUND");
        String name2 = pref.getString("STOCK_NAME_1", "NOTFOUND");
        String value2 = pref.getString("STOCK_VALUE_1", "NOTFOUND");
        TextView txtValue = root.findViewById(R.id.coin_name_1);
        txtValue.setText(name1);
        TextView txtValue2 = root.findViewById(R.id.coin_value_1);
        txtValue2.setText(value1);
        TextView txtValue3 = root.findViewById(R.id.stock_name_1);
        txtValue3.setText(name2);
        TextView txtValue4 = root.findViewById(R.id.stock_value_1);
        txtValue4.setText(value2);



//        final TextView textView = root.findViewById(R.id.text_title);
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }
}