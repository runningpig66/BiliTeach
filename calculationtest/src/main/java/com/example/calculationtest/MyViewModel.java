package com.example.calculationtest;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;

import java.util.Random;

public class MyViewModel extends AndroidViewModel {
    private SavedStateHandle handle;
    boolean win_flat = false;
    private static final String KEY_HIGH_SCORE = "key_high_score";
    private static final String KEY_CURRENT_SCORE = "key_current_score";
    private static final String KEY_LEFT_NUMBER = "key_left_number";
    private static final String KEY_RIGHT_NUMBER = "key_right_number";
    private static final String KEY_OPERATOR = "key_operator";
    private static final String KEY_ANSWER = "key_answer";
    private static final String SAVE_SHP_DATA_NAME = "save_shp_data_name";
    private static final String KEY_EDIT_TEXT = "edit_text";

    public MyViewModel(@NonNull Application application, SavedStateHandle handle) {
        super(application);
        if (!handle.contains(KEY_HIGH_SCORE)) {
            SharedPreferences sharedPreferences = getApplication().getSharedPreferences(
                    SAVE_SHP_DATA_NAME, Context.MODE_PRIVATE);
            handle.set(KEY_HIGH_SCORE, sharedPreferences.getInt(KEY_HIGH_SCORE, 0));
            handle.set(KEY_CURRENT_SCORE, 0);
            handle.set(KEY_LEFT_NUMBER, 0);
            handle.set(KEY_RIGHT_NUMBER, 0);
            handle.set(KEY_OPERATOR, "+");
            handle.set(KEY_ANSWER, 0);
            handle.set(KEY_EDIT_TEXT, "");
        }
        this.handle = handle;
    }

    public MutableLiveData<Integer> getHighScore() {
        return handle.getLiveData(KEY_HIGH_SCORE);
    }

    public MutableLiveData<Integer> getCurrentScore() {
        return handle.getLiveData(KEY_CURRENT_SCORE);
    }

    public MutableLiveData<Integer> getLeftNumber() {
        return handle.getLiveData(KEY_LEFT_NUMBER);
    }

    public MutableLiveData<Integer> getRightNumber() {
        return handle.getLiveData(KEY_RIGHT_NUMBER);
    }

    public MutableLiveData<String> getOperator() {
        return handle.getLiveData(KEY_OPERATOR);
    }

    public MutableLiveData<Integer> getAnswer() {
        return handle.getLiveData(KEY_ANSWER);
    }

    public MutableLiveData<String> getEditText() {
        return handle.getLiveData(KEY_EDIT_TEXT);
    }

    /**
     * 生成操作符、运算数、运算结果
     */
    void generator() {
        int LEVEL = 20;//难度系数
        Random random = new Random();
        int x, y;
        x = random.nextInt(LEVEL) + 1;
        y = random.nextInt(LEVEL) + 1;
        if (x % 2 == 0) {
            getOperator().setValue("+");
            if (x > y) {
                getAnswer().setValue(x);
                getLeftNumber().setValue(y);
                getRightNumber().setValue(x - y);
            } else {
                getAnswer().setValue(y);
                getLeftNumber().setValue(x);
                getRightNumber().setValue(y - x);
            }
        } else {
            getOperator().setValue("-");
            if (x > y) {
                getAnswer().setValue(x - y);
                getLeftNumber().setValue(x);
                getRightNumber().setValue(y);
            } else {
                getAnswer().setValue(y - x);
                getLeftNumber().setValue(y);
                getRightNumber().setValue(x);
            }
        }
    }

    /**
     * 保存最高纪录
     */
    void save() {
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences(SAVE_SHP_DATA_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //noinspection ConstantConditions
        editor.putInt(KEY_HIGH_SCORE, getHighScore().getValue());
        editor.apply();
    }

    /**
     * 回答正确
     */
    @SuppressWarnings("ConstantConditions")
    void answerCorrect() {
        getCurrentScore().setValue(getCurrentScore().getValue() + 1);
        if (getCurrentScore().getValue() > getHighScore().getValue()) {
            getHighScore().setValue(getCurrentScore().getValue());
            win_flat = true;
        }
        generator();
    }
}
