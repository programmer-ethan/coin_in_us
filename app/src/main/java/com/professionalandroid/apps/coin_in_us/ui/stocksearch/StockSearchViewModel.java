package com.professionalandroid.apps.coin_in_us.ui.stocksearch;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StockSearchViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public StockSearchViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("");
    }

    public LiveData<String> getText() {
        return mText;
    }
}