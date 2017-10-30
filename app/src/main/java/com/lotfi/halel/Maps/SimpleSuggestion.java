package com.lotfi.halel.Maps;

import android.os.Parcel;
import android.util.Log;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

/**
 * Created by Lotfi_pc on 26/08/2017.
 */
public class SimpleSuggestion implements SearchSuggestion {
    private String title;
    private boolean isHistory = false;

    public SimpleSuggestion(String title){
        this.title = title;
    }

    //从parcel中读取存储的数据
    public SimpleSuggestion(Parcel source){
        title = source.readString();
        //==1 : true ; ==0 : false
        isHistory = source.readInt() != 0;
    }

    public void setHistory(boolean history) {
        isHistory = history;
    }

    public boolean isHistory() {
        return isHistory;
    }

    @Override
    //getTitle
    public String getBody() {
        return title;
    }

    public static final Creator<SimpleSuggestion> CREATOR = new Creator<SimpleSuggestion>() {
        @Override
        public SimpleSuggestion createFromParcel(Parcel source) {
            return new SimpleSuggestion(source);
        }

        @Override
        public SimpleSuggestion[] newArray(int size) {
            return new SimpleSuggestion[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    //存储数据
    public void writeToParcel(Parcel dest, int flags) {
        Log.d("SuggesstionItem", "write to parcel");
        dest.writeString(title);
        dest.writeInt(isHistory ? 1 : 0);
    }
}