package com.example.grocery.faq;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocery.R;
import com.example.grocery.helperclass.FaqModel;

import java.util.ArrayList;

public class FaqAdapter extends RecyclerView.Adapter<FaqAdapter.FaqAdapterViewHolder> {
    ArrayList<FaqModel> models;

    public FaqAdapter(ArrayList<FaqModel> models) {
        this.models = models;
    }

    @NonNull
    @Override
    public FaqAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.faqcard,parent,false);
        return new FaqAdapterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FaqAdapterViewHolder holder, int position) {
        FaqModel item=models.get(position);
        holder.question.setText(item.getQuestion());
        holder.answer.setText(item.getAnswer());
        boolean isExpandable=models.get(position).isExpandable();
        holder.expandablelayout.setVisibility(isExpandable?View.VISIBLE:View.GONE);

    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    class FaqAdapterViewHolder extends RecyclerView.ViewHolder{
        TextView question,answer;
        RelativeLayout expandablelayout;
        LinearLayout linearLayout;
        public FaqAdapterViewHolder(@NonNull final View itemView) {
            super(itemView);
            question=itemView.findViewById(R.id.question);
            answer=itemView.findViewById(R.id.answer);
            expandablelayout=itemView.findViewById(R.id.expandable);
            linearLayout=itemView.findViewById(R.id.linearlayout);

      linearLayout.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              FaqModel model=models.get(getAdapterPosition());
              model.setExpandable(!model.isExpandable());
              notifyDataSetChanged();
          }
      });

        }
    }
}
