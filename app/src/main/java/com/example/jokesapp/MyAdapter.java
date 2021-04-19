package com.example.jokesapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context context;
    List<String> setup_adapter,punclines_adapter;
    public MyAdapter(Context ct, List<String> s1, List<String> s2){
        context=ct;
        setup_adapter=s1;
        punclines_adapter=s2;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.row,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setup_textView.setText(setup_adapter.get(position));
        holder.punchline_textView.setText(punclines_adapter.get(position));
    }

    @Override
    public int getItemCount() {
        return setup_adapter.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView setup_textView,punchline_textView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            setup_textView=itemView.findViewById(R.id.setup);
            punchline_textView=itemView.findViewById(R.id.punchline);
        }
    }
}
