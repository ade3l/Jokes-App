package com.example.jokesapp;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
        holder.copy_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard=(ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip=ClipData.newPlainText("Joke text",setup_adapter.get(position)+"  "+punclines_adapter.get(position));
                clipboard.setPrimaryClip(clip);
                Toast.makeText(context, "Joke copied onto clipboard", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return setup_adapter.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView setup_textView,punchline_textView;
        FloatingActionButton copy_button;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            setup_textView=itemView.findViewById(R.id.setup);
            punchline_textView=itemView.findViewById(R.id.punchline);
            copy_button=itemView.findViewById(R.id.copy);

        }
    }
}
