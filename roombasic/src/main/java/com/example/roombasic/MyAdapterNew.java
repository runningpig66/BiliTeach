package com.example.roombasic;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapterNew extends RecyclerView.Adapter<MyAdapterNew.MyViewHolder> {
    private List<Word> allWords;
    private boolean useCardView;
    private WordViewModel wordViewModel;

    MyAdapterNew(boolean useCardView, WordViewModel wordViewModel) {
        this.useCardView = useCardView;
        this.wordViewModel = wordViewModel;
    }

    void setAllWords(List<Word> allWords) {
        this.allWords = allWords;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView;
        if (useCardView) {
            itemView = layoutInflater.inflate(R.layout.cell_card2, parent, false);
        } else {
            itemView = layoutInflater.inflate(R.layout.cell_normal2, parent, false);
        }
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final Word word = allWords.get(position);
//        holder.textViewNumber.setText(String.valueOf(word.getId()));
        holder.textViewNumber.setText(String.valueOf(position + 1));
        holder.textViewEnglish.setText(word.getWord());
        holder.textViewChinese.setText(word.getChineseMeaning());
        //由于switch是viewholder复用之前的，所以可能会驱动之前设置的监听器，导致界面数据错误
        holder.switchChineseInvisible.setOnCheckedChangeListener(null);
        if (word.isChineseInvisible()) {
            holder.textViewChinese.setVisibility(View.GONE);
            holder.switchChineseInvisible.setChecked(true);
        } else {
            holder.textViewChinese.setVisibility(View.VISIBLE);
            holder.switchChineseInvisible.setChecked(false);
        }
        holder.switchChineseInvisible.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    holder.textViewChinese.setVisibility(View.GONE);
                    word.setChineseInvisible(true);
                } else {
                    holder.textViewChinese.setVisibility(View.VISIBLE);
                    word.setChineseInvisible(false);
                }
                //更新数据库
                wordViewModel.updateWords(word);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://m.youdao.com/dict?le=eng&q=" + holder.textViewEnglish.getText());
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(uri);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return allWords == null ? 0 : allWords.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNumber, textViewEnglish, textViewChinese;
        Switch switchChineseInvisible;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNumber = itemView.findViewById(R.id.textViewNumber);
            textViewEnglish = itemView.findViewById(R.id.textViewEnglish);
            textViewChinese = itemView.findViewById(R.id.textViewChinese);
            switchChineseInvisible = itemView.findViewById(R.id.switchChineseInvisible);
        }
    }
}
