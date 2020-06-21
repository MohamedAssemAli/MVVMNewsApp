package com.androiddevs.mvvmnewsapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.androiddevs.mvvmnewsapp.data.models.Article


/**
 * Created by Mohamed Assem on 21-Jun-20.
 * mo7mad.assim@gmail.com
 */

@Database(entities = [Article::class], version = 1)
@TypeConverters(Converters::class)
abstract class ArticleDatabase : RoomDatabase() {

    abstract fun getArticleDao(): ArticleDao

    companion object {
        // instance of ArticleDatabase, which is nullable
        // @Volatile => means other threads can immediately see when a thread changes this instance
        @Volatile
        private var instance: ArticleDatabase? = null

        // will use that to synchronize setting this instance,
        // we will  make sure there's only  single instance of database at once
        private val LOCK = Any()

        /*
        call this invoke that is called whenever we create an instance of our database.
        so, whenever we write something like article database and call the constructor
        on that data base then also this invoke function will be called.
        so, basically when we initialize or instantiate that object and this function will take a context
        and we will set it to our instance and if the instance is null in that case we want to start a synchronized block with our lock object
        so that means that everything that happens inside of this block of code here can't be accessed by other threads
        at the same time so we really make sure that we don't set that there is not another thread that sets this instance to something while we already said
        and in this synchronous block we want to return our instance again so we make that null check again
        and if it is still null then we want to call a function that we create afterwards which is create database which will take our context and we call dot also on that
        which sets our current instance to it
         */
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ArticleDatabase::class.java,
                "article_db.db"
            ).build()
    }

}