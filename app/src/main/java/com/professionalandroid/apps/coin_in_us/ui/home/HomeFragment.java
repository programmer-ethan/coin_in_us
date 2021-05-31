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
//    writeSharedPreference(View view);
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

//    SharedPreferences interestedList;
//    SharedPreferences.Editor editor;

//    Context context = getActivity();
//    SharedPreferences sharedPref = context.getSharedPreferences(
//            getString(R.string.preference_file_key), Context.MODE_PRIVATE);

//    int myInt;
//    String myStr;
//    TextView tabletext1, tabletext2;
//
//    SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
//    SharedPreferences.Editor editor = sharedPref.edit();

//    editor.putInt(
//
//    void getString(R.string.saved_data), newHighScore);
//    editor.commit();

//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);


        // 5. 각 버튼 클릭시 새로운 값 저장
//        btn01.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                myInt = Integer.parseInt(et01.getText().toString()); // int Max값 넘게 입력하면 오류 주의.
//                editor.putInt("MyInt", myInt);
//                editor.apply(); // 저장
//            }
//        });
//        btn02.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                myStr = et02.getText().toString();
//                editor.putString("MyStr", myStr);
//                editor.apply(); // 저장
//            }
//        });



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
        editor.putString("MYKEY","VALUE1");
        editor.putString("KEY2", "VALUE2");
        // 4. commit the values
        editor.commit();
        // getting Shared preference from other application
        SharedPreferences pref
                = context.getSharedPreferences("MYPREFRENCE", Context.MODE_PRIVATE | Context.MODE_MULTI_PROCESS);
        String value = pref.getString("MYKEY", "NOTFOUND");
        TextView txtValue = root.findViewById(R.id.shared_data);
        txtValue.setText(value);
//        // 1. Shared Preference 초기화
//        interestedList = getSharedPreferences("interestedList", Activity.MODE_PRIVATE);
//        editor = interestedList.edit();
//
//        // 2. 저장해둔 값 불러오기 ("식별값", 초기값) -> 식별값과 초기값은 직접 원하는 이름과 값으로 작성.
//        myInt = interestedList.getInt("MyInt", 0);        // int 불러오기 (저장해둔 값 없으면 초기값인 0으로 불러옴)
//        myStr = interestedList.getString("MyStr", "_");   // String 불러오기 (저장해둔 값 없으면 초기값인 _으로 불러옴)
//
//        // 3. 레이아웃 변수 초기화
//        tabletext1 = findViewById(R.id.textView2); et02 = findViewById(R.id.textView2);
//        tabletext2 = findViewById(R.id.textView3); btn02 = findViewById(R.id.textView3);
//
//        // 4. 앱을 새로 켜면 이전에 저장해둔 값이 표시됨
//        tabletext1.setText(String.valueOf(myInt)); tabletext2.setText(myStr);



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