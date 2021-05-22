package com.professionalandroid.apps.coin_in_us.ui.coinsearch;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CoinSearchViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CoinSearchViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("");
    }

    public LiveData<String> getText() {
        return mText;
    }
}