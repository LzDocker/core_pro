package com.docker.core.di.module.cachemodule;

import android.arch.lifecycle.ComputableLiveData;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.InvalidationTracker.Observer;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.database.Cursor;
import android.support.annotation.NonNull;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Set;

@SuppressWarnings("unchecked")
public class CacheEntityDao_Impl implements CacheEntityDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfCacheEntity;

  public CacheEntityDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCacheEntity = new EntityInsertionAdapter<CacheEntity>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `CacheEntity`(`cid`,`cachekey`,`data`) VALUES (nullif(?, 0),?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, CacheEntity value) {
        stmt.bindLong(1, value.getCid());
        if (value.getKey() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getKey());
        }
        if (value.getData() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindBlob(3, value.getData());
        }
      }
    };
  }

  @Override
  public Long[] insertCache(CacheEntity... cacheEntities) {
    __db.beginTransaction();
    try {
      Long[] _result = __insertionAdapterOfCacheEntity.insertAndReturnIdsArrayBox(cacheEntities);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<CacheEntity> LoadCache(String key) {
    final String _sql = "SELECT * FROM CacheEntity WHERE cachekey = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (key == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, key);
    }
    return new ComputableLiveData<CacheEntity>() {
      private Observer _observer;

      @Override
      protected CacheEntity compute() {
        if (_observer == null) {
          _observer = new Observer("CacheEntity") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfCid = _cursor.getColumnIndexOrThrow("cid");
          final int _cursorIndexOfKey = _cursor.getColumnIndexOrThrow("cachekey");
          final int _cursorIndexOfData = _cursor.getColumnIndexOrThrow("data");
          final CacheEntity _result;
          if(_cursor.moveToFirst()) {
            _result = new CacheEntity();
            final long _tmpCid;
            _tmpCid = _cursor.getLong(_cursorIndexOfCid);
            _result.setCid(_tmpCid);
            final String _tmpKey;
            _tmpKey = _cursor.getString(_cursorIndexOfKey);
            _result.setKey(_tmpKey);
            final byte[] _tmpData;
            _tmpData = _cursor.getBlob(_cursorIndexOfData);
            _result.setData(_tmpData);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    }.getLiveData();
  }
}
