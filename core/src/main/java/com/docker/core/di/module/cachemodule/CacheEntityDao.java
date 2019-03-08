package com.docker.core.di.module.cachemodule;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;


@Dao
public interface CacheEntityDao {

    @Query("SELECT * FROM CacheEntity WHERE cachekey = :key")
    LiveData<CacheEntity> LoadCache(String key);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long[] insertCache(CacheEntity... cacheEntities);

}
