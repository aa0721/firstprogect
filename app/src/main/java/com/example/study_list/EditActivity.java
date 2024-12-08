package com.example.study_list;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.study_list.bean.Note;
import com.example.study_list.util.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EditActivity extends AppCompatActivity {

    private Note note;
    private EditText etTitle,etContent;
    private NoteDbOpenHelper mNoteDbOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        etTitle = findViewById(R.id.et_title);
        etContent = findViewById(R.id.et_content);

        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        note = (Note) intent.getSerializableExtra("note");
        if (note != null) {
            etTitle.setText(note.getTitle());
            etContent.setText(note.getContent());
        }
        mNoteDbOpenHelper = new NoteDbOpenHelper(this);
    }

    public void save(View view) {
        String title = etTitle.getText().toString();
        String content = etContent.getText().toString();
        if (TextUtils.isEmpty(title)) {
            ToastUtil.toastShort(this, "标题不能为空！");
            return;
        }

        note.setTitle(title);
        note.setContent(content);
        note.setCreatedTime(getCurrentTimeFormat());
        long rowId = mNoteDbOpenHelper.updateData(note);
        if (rowId != -1) {
            ToastUtil.toastShort(this, "修改成功！");
            this.finish();
        }else{
            ToastUtil.toastShort(this, "修改失败！");
        }
    }
    private String getCurrentTimeFormat() {
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY年MM月dd日 HH:mm:ss");
        Date date = new Date();
        return sdf.format(date);
    }
}