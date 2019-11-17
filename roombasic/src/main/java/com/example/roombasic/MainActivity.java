package com.example.roombasic;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //    WordDatabase wordDatabase;
//    WordDao wordDao;
    TextView textView;
    Button buttonInsert, buttonUpdate, buttonClear, buttonDelete;
    //    LiveData<List<Word>> allWordsLive;
    WordViewModel wordViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);
//        wordDatabase = Room.databaseBuilder(this, WordDatabase.class, "word_database")
//                .allowMainThreadQueries()
//                .build();
//        wordDatabase = WordDatabase.getINSTANCE(this);
//        wordDao = wordDatabase.getWordDao();
//        allWordsLive = wordDao.getAllWordsLive();
        textView = findViewById(R.id.textView);
        buttonInsert = findViewById(R.id.buttonInsert);
        buttonUpdate = findViewById(R.id.buttonUpdate);
        buttonClear = findViewById(R.id.buttonClear);
        buttonDelete = findViewById(R.id.buttonDelete);
        buttonInsert.setOnClickListener(this);
        buttonUpdate.setOnClickListener(this);
        buttonClear.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);
        wordViewModel.getAllWordsLive().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                StringBuilder builder = new StringBuilder();
                for (Word word : words) {
                    builder.append(word.getId()).append(":").append(word.getWord()).append("=").append(word.getChineseMeaning()).append("\n");
                }
                textView.setText(builder.toString());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonInsert:
                String[] english = {
                        "Hello",
                        "World",
                        "Android",
                        "Google",
                        "Studio",
                        "Project",
                        "Database",
                        "Recycler",
                        "View",
                        "String",
                        "Value",
                        "Integer"
                };
                String[] chinese = {
                        "你好",
                        "世界",
                        "安卓系统",
                        "谷歌公司",
                        "工作室",
                        "项目",
                        "数据库",
                        "回收站",
                        "视图",
                        "字符串",
                        "价值",
                        "整数类型"
                };
                for (int i = 0; i < english.length; i++) {
                    wordViewModel.insertWords(new Word(english[i], chinese[i]));
                }
//2                Word word1 = new Word("Hello", "你好");
//2                Word word2 = new Word("World", "世界");
//1                wordDao.insertWords(word1, word2);
//1                new InsertAsyncTask(wordDao).execute(word1, word2);
//2                wordViewModel.insertWords(word1, word2);
                break;
            case R.id.buttonUpdate:
                Word word3 = new Word("Hi", "你好啊");
                word3.setId(20);//根据主键去更新该记录
//                wordDao.updateWords(word3);
//                new UpdateAsyncTask(wordDao).execute(word3);
                wordViewModel.updateWords(word3);
                break;
            case R.id.buttonClear:
//                wordDao.deleteAllWords();
//                new DeleteAllAsyncTask(wordDao).execute();
                wordViewModel.deleteAllWords();
                break;
            case R.id.buttonDelete:
                Word word4 = new Word("", "");
                word4.setId(20);
//                wordDao.deleteWords(word4);
//                new DeleteAsyncTask(wordDao).execute(word4);
                wordViewModel.deleteWords(word4);
                break;
        }
    }
}
