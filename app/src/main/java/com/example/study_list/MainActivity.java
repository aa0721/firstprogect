package com.example.study_list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.study_list.bean.Note;
import com.example.study_list.MyAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static boolean isLoggedIn;
    private RecyclerView mRecyclerView;
    //mRecyclerView：用于显示列表数据的RecyclerView
    private FloatingActionButton mBtnAdd;
    private List<Note> mNotes;
    private NoteDbOpenHelper mNoteDbOpenHelper;
    private MyAdapter mMyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        if (!isLoggedIn) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            // 结束当前Activity启动流程，避免后续初始化代码执行（因为还没登录）
            finish();
            return;
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

            initView();
            initData();
            initEvent();

    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshDataFromDb();
    }

    private void refreshDataFromDb() {
        mNotes = getDataFromDB();
        mMyAdapter.refreshData(mNotes);
    }

    public void initEvent() {
        mMyAdapter = new MyAdapter(this, mNotes);
        mRecyclerView.setAdapter(mMyAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }

    private void initData() {
        mNotes = new ArrayList<>();
        mNoteDbOpenHelper = new NoteDbOpenHelper(this);

//        for (int i = 0;i < 30; i++){
//            Note note = new Note();
//            note.setTitle("这是标题"+i);
//            note.setContent("这是内容"+i);
//            note.setCreatedTime(getCurrentTimeFormat());
//            mNotes.add(note);
//        }

//        mNotes = getDataFromDB();
        if (mNotes == null) {
            // 如果从数据库获取数据失败（比如数据库连接问题等原因），可以进行一些兜底处理，比如初始化一个空列表
            mNotes = new ArrayList<>();
        }
    }
    private List<Note> getDataFromDB() {
        return mNoteDbOpenHelper.queryAllFromDb();
    }
    private String getCurrentTimeFormat() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd HH:mm:ss");
        Date date = new Date();
        return simpleDateFormat.format(date);
    }
    public void initView() {
        mRecyclerView = findViewById(R.id.rlv);
    }
    public void add(View view) {
        Intent intent = new Intent(this, AddActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mean_main, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mNotes = mNoteDbOpenHelper.queryFromDbByTitle(newText);
                mMyAdapter.refreshData(mNotes);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}