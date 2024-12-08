package com.example.study_list;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.study_list.EditActivity;
import com.example.study_list.R;
import com.example.study_list.bean.Note;
import com.example.study_list.util.ToastUtil;

import java.sql.SQLException;
import java.util.List;

class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<Note> mBeanList;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private NoteDbOpenHelper mNoteDbOpenHelper;
    private MyViewHolder holder;
    private int position;

    public MyAdapter(Context context,List<Note> mBeanList){
        this.mBeanList = mBeanList;
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mNoteDbOpenHelper = new NoteDbOpenHelper(mContext);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.list_item_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Note note = mBeanList.get(position);

        holder.mTvTitle.setText(note.getTitle());
        holder.mTvContent.setText(note.getContent());
        holder.mTvTime.setText(note.getCreatedTime());
        holder.rlContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Note note = mBeanList.get(position);
                Intent intent = new Intent(mContext, EditActivity.class);
                intent.putExtra("note", note);
                mContext.startActivity(intent);
            }
        });

        holder.rlContainer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // 弹出弹窗
                Dialog dialog = new Dialog(mContext, android.R.style.ThemeOverlay_Material_Dialog_Alert);
                View dialogView = mLayoutInflater.inflate(R.layout.list_item_dialog_layout, null);
                TextView tvDelete = dialogView.findViewById(R.id.tv_delete);
                TextView tvEdit = dialogView.findViewById(R.id.tv_edit);

                tvDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int row = mNoteDbOpenHelper.deleteFromDbById(note.getId());
                        if (row > 0) {
                            removeData(position);
                            ToastUtil.toastShort(mContext, "删除成功！");
                        } else {
                            ToastUtil.toastShort(mContext, "删除失败！");
                        }
                        dialog.dismiss();
                    }
                });


                tvEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, EditActivity.class);
                        intent.putExtra("note", note);
                        mContext.startActivity(intent);
                        dialog.dismiss();
                    }
                });
                dialog.setContentView(dialogView);
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
                return true;
            }
        });

    }



    @Override
    public int getItemCount() {
        return mBeanList.size();
    }

    public void refreshData(List<Note> notes) {
        this.mBeanList = notes;
        notifyDataSetChanged();
    }

    public void removeData(int pos) {
        mBeanList.remove(pos);
        notifyItemRemoved(pos);
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView mTvTitle;
        TextView mTvContent;
        TextView mTvTime;
        ViewGroup rlContainer;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            this.mTvTitle = itemView.findViewById(R.id.tv_title);
            this.mTvContent = itemView.findViewById(R.id.tv_content);
            this.mTvTime = itemView.findViewById(R.id.tv_time);
            this.rlContainer = itemView.findViewById(R.id.rl_item_container);
        }
    }
}
