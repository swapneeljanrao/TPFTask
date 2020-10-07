package com.mrcoder.tpftask.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mrcoder.tpftask.R;
import com.mrcoder.tpftask.activity.Detailed;
import com.mrcoder.tpftask.model.Articles;
import com.squareup.picasso.Picasso;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewModel> {

    Context context;
    List<Articles> articles;

    public Adapter(Context context, List<Articles> articles) {
        this.context = context;
        this.articles = articles;
    }

    @NonNull
    @Override
    public MyViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items, parent, false);

        return new MyViewModel(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewModel holder, int position) {

        final Articles a = articles.get(position);

        String imageURL = a.getUrlToImage();
        String url = a.getUrl();

        holder.tvTitle.setText(a.getTitle());
        holder.tvSource.setText(a.getSource().getName());
        holder.tvAuthor.setText(a.getAuthor());
        holder.tvDescription.setText(a.getDescription());
        holder.tvTime.setText(" \u2022 " + dateTime(a.getPublishedAt()));
        holder.tvPublishedAt.setText(dateTime(a.getPublishedAt()));
        Picasso.get().load(imageURL).into(holder.ivNewsImage);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Detailed.class);
                intent.putExtra("title", a.getTitle());
                intent.putExtra("source", a.getSource().getName());
                intent.putExtra("time", dateTime(a.getPublishedAt()));
                intent.putExtra("desc", a.getDescription());
                intent.putExtra("imageUrl", a.getUrlToImage());
                intent.putExtra("url", a.getUrl());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class MyViewModel extends RecyclerView.ViewHolder {
        TextView tvTitle, tvSource, tvDescription, tvPublishedAt, tvTime, tvAuthor;
        ImageView ivNewsImage;
        CardView cardView;
        ProgressBar progressBar;

        public MyViewModel(@NonNull View itemView) {
            super(itemView);

            tvAuthor = itemView.findViewById(R.id.author);
            tvTitle = itemView.findViewById(R.id.title);
            tvDescription = itemView.findViewById(R.id.description);
            tvSource = itemView.findViewById(R.id.source);
            tvPublishedAt = itemView.findViewById(R.id.publishedAt);
            tvTime = itemView.findViewById(R.id.time);
            cardView = itemView.findViewById(R.id.cardView);
            ivNewsImage = itemView.findViewById(R.id.img);

            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    public String dateTime(String t) {
        PrettyTime prettyTime = new PrettyTime(new Locale(getCountry()));
        String time = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:", Locale.ENGLISH);
            Date date = simpleDateFormat.parse(t);
            time = prettyTime.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;

    }

    public String getCountry() {
        Locale locale = Locale.getDefault();
        String country = locale.getCountry();
        return country.toLowerCase();
    }
}
