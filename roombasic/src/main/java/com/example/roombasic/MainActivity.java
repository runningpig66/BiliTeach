package com.example.roombasic;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //    WordDatabase wordDatabase;
//    WordDao wordDao;
//    TextView textView;
    Button buttonInsert, buttonUpdate, buttonClear, buttonDelete;
    //    LiveData<List<Word>> allWordsLive;
    WordViewModel wordViewModel;

    RecyclerView recyclerView;
    //    MyAdapter myAdapter1;
//    MyAdapter myAdapter2;
    MyAdapterNew myAdapter1;
    MyAdapterNew myAdapter2;
    Switch switch1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        myAdapter1 = new MyAdapter(false);
//        myAdapter2 = new MyAdapter(true);
        wordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);
        myAdapter1 = new MyAdapterNew(false, wordViewModel);
        myAdapter2 = new MyAdapterNew(true, wordViewModel);
        recyclerView.setAdapter(myAdapter1);
        switch1 = findViewById(R.id.switch1);
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    recyclerView.setAdapter(myAdapter2);
                } else {
                    recyclerView.setAdapter(myAdapter1);
                }
            }
        });
//        wordDatabase = Room.databaseBuilder(this, WordDatabase.class, "word_database")
//                .allowMainThreadQueries()
//                .build();
//        wordDatabase = WordDatabase.getINSTANCE(this);
//        wordDao = wordDatabase.getWordDao();
//        allWordsLive = wordDao.getAllWordsLive();
//        textView = findViewById(R.id.textViewNumber);

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
//                myAdapter1.setAllWords(words);
//                myAdapter2.setAllWords(words);
//                myAdapter1.notifyDataSetChanged();
//                myAdapter2.notifyDataSetChanged();

                //在子项中切换switch后，switch执行切换动画，同时更新的数据，
                //数据更新后会执行到这里进行adapter的更新，导致刷新子项界面，
                //就会导致switch未执行完动画，界面就被刷新，UI过度不够流畅，
                //所以这里选择在数据源数量不变时，就不去刷新适配器了，
                //造成的缺陷就是：首页的UPDATE按钮更新某条数据时界面更新失效。
                int temp = myAdapter1.getItemCount();
                myAdapter1.setAllWords(words);
                myAdapter2.setAllWords(words);
                if (temp != words.size()) {
                    myAdapter1.notifyDataSetChanged();
                    myAdapter2.notifyDataSetChanged();
                }

//                StringBuilder builder = new StringBuilder();
//                for (Word word : words) {
//                    builder.append(word.getId()).append(":").append(word.getWord()).append("=").append(word.getChineseMeaning()).append("\n");
//                }
//                textView.setText(builder.toString());
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
