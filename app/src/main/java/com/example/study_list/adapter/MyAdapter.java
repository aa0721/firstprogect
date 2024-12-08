//package com.example.study_list.adapter;
//
//import android.content.Context;
//import android.content.Intent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.study_list.EditActivity;
//import com.example.study_list.R;
//import com.example.study_list.bean.Note;
//
//import java.util.List;
//
//public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
//
//    private List<Note> mBeanList;
//    private LayoutInflater mLayoutInflater;
//    private Context mContext;
//
//    public MyAdapter(Context context,List<Note> mBeanList){
//        this.mBeanList = mBeanList;
//        this.mContext = context;
//        mLayoutInflater = LayoutInflater.from(mContext);
//    }
//
//    @NonNull
//    @Override
//    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = mLayoutInflater.inflate(R.layout.list_item_layout,parent,false);
//        MyViewHolder myViewHolder = new MyViewHolder(view);
//        return myViewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//        Note note = mBeanList.get(position);
//
//        holder.mTvTitle.setText(note.getTitle());
//        holder.mTvContent.setText(note.getTitle());
//        holder.mTvTime.setText(note.getTitle());
//        holder.rlContainer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(mContext, EditActivity.class);
//                intent.putExtra("note",note);
//                mContext.startActivity(intent);
//            }
//        });
//
//        holder.rlContainer.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                return false;
//            }
//        });
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return mBeanList.size();
//    }
//
//    class MyViewHolder extends RecyclerView.ViewHolder{
//
//        TextView mTvTitle;
//        TextView mTvContent;
//        TextView mTvTime;
//        ViewGroup rlContainer;
//
//        public MyViewHolder(@NonNull View itemView){
//            super(itemView);
//            this.mTvTitle = itemView.findViewById(R.id.tv_title);
//            this.mTvContent = itemView.findViewById(R.id.tv_content);
//            this.mTvTime = itemView.findViewById(R.id.tv_time);
//            this.rlContainer = itemView.findViewById(R.id.rl_item_container);
//        }
//    }
//}
