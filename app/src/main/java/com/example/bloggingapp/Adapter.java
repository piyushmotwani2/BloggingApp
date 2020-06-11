package com.example.bloggingapp;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.myViewHolder> {

    Context mContext;
    ArrayList<Item> mData;
    private OnArticleListener mOnArticleListener;


    public Adapter(Context mContext, ArrayList<Item> mData, OnArticleListener onArticleListener) {
        this.mContext = mContext;
        this.mData = mData;
        this.mOnArticleListener = onArticleListener;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.card_item,parent,false);
        return new myViewHolder(v, mOnArticleListener);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

        holder.cardBackground.setBackgroundColor(Integer.parseInt(mData.get(position).getCardBackground(),16));
        holder.title.setText(mData.get(position).getArticleName());
        holder.author.setText(mData.get(position).getAuthorName());
        String noLikes = mData.get(position).getNumoflikes();
        holder.numofLikes.setText(noLikes);
        holder.tag1.setText("#" + mData.get(position).tags[0]);
        holder.tag2.setText("#" + mData.get(position).tags[1]);
        holder.tag3.setText("#" + mData.get(position).tags[2]);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView cardBackground;
        TextView title,author,numofLikes,tag1,tag2,tag3;
        OnArticleListener onArticleListener;

        public myViewHolder(View itemView,OnArticleListener onArticleListener){
            super(itemView);
            cardBackground = itemView.findViewById(R.id.cardBackground);
            title = itemView.findViewById(R.id.title);
            author = itemView.findViewById(R.id.author);
            numofLikes = itemView.findViewById(R.id.numofLikes);
            tag1 = itemView.findViewById(R.id.tag1);
            tag2 = itemView.findViewById(R.id.tag2);
            tag3 = itemView.findViewById(R.id.tag3);
            this.onArticleListener = onArticleListener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            onArticleListener.onArticleClicked(getAdapterPosition(),view);
        }
    }

    public interface OnArticleListener{
        void onArticleClicked(int position,View view);
    }
}
