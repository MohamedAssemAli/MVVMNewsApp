package com.androiddevs.mvvmnewsapp.data.db

import androidx.room.TypeConverter
import com.androiddevs.mvvmnewsapp.data.models.Source


/**
 * Created by Mohamed Assem on 21-Jun-20.
 * mo7mad.assim@gmail.com
 */

class Converters {

    @TypeConverter
    fun fromSource(source: Source): String {
        return source.name
    }

    @TypeConverter
    fun toSource(name: String): Source {
        return Source(name, name)
    }
}