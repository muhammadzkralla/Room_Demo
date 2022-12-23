package com.zkrallah.postswithroom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostsViewHolder> {
    Context context;
    List<Post> postsList;

    public PostsAdapter(Context context, List<Post> postsList) {
        this.context = context;
        this.postsList = postsList;
    }

    @NonNull
    @Override
    public PostsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final PostsViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final Post post = postsList.get(position);

        holder.titleTV.setText(post.getTitle());
        holder.bodyTV.setText(post.getBody());

        final PostsDatabase postsDatabase = PostsDatabase.getInstance(context);

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postsDatabase.postsDao().deleteItem(post).subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new CompletableObserver() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onComplete() {
                                Log.d("TAG", "onComplete: ");
                                postsList.remove(post);
                                notifyItemRemoved(position);
                            }

                            @Override
                            public void onError(Throwable e) {

                            }
                        });

                Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return postsList.size();
    }


    public static class PostsViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleTV;
        private final TextView bodyTV;
        Button button;
        public PostsViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTV = itemView.findViewById(R.id.item_title_textView);
            bodyTV = itemView.findViewById(R.id.item_body_textView);
            button = itemView.findViewById(R.id.button);
        }
    }
}
