package com.alyndroid.postswithroom;

import android.database.Observable;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
interface PostsDao {
    // Observable of type Completable
    @Insert
    Completable insertPost(Post post);

    // Observable of type Single
    @Query("select * from posts_table")
    Single<List<Post>> getPosts();
}
