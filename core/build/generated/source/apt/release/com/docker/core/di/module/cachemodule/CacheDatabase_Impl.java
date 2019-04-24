package com.docker.core.di.module.cachemodule;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Callback;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Configuration;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.RoomOpenHelper;
import android.arch.persistence.room.RoomOpenHelper.Delegate;
import android.arch.persistence.room.util.TableInfo;
import android.arch.persistence.room.util.TableInfo.Column;
import android.arch.persistence.room.util.TableInfo.ForeignKey;
import android.arch.persistence.room.util.TableInfo.Index;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;

@SuppressWarnings("unchecked")
public class CacheDatabase_Impl extends CacheDatabase {
  private volatile CacheEntityDao _cacheEntityDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `CacheEntity` (`cid` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `cachekey` TEXT, `data` BLOB)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"a1de8e97232e6eec5ac528c6936a55a1\")");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `CacheEntity`");
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsCacheEntity = new HashMap<String, TableInfo.Column>(3);
        _columnsCacheEntity.put("cid", new TableInfo.Column("cid", "INTEGER", true, 1));
        _columnsCacheEntity.put("cachekey", new TableInfo.Column("cachekey", "TEXT", false, 0));
        _columnsCacheEntity.put("data", new TableInfo.Column("data", "BLOB", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCacheEntity = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCacheEntity = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCacheEntity = new TableInfo("CacheEntity", _columnsCacheEntity, _foreignKeysCacheEntity, _indicesCacheEntity);
        final TableInfo _existingCacheEntity = TableInfo.read(_db, "CacheEntity");
        if (! _infoCacheEntity.equals(_existingCacheEntity)) {
          throw new IllegalStateException("Migration didn't properly handle CacheEntity(com.docker.core.di.module.cachemodule.CacheEntity).\n"
                  + " Expected:\n" + _infoCacheEntity + "\n"
                  + " Found:\n" + _existingCacheEntity);
        }
      }
    }, "a1de8e97232e6eec5ac528c6936a55a1", "b17fa40d4187d332338b559db529bafe");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "CacheEntity");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `CacheEntity`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public CacheEntityDao cacheEntityDao() {
    if (_cacheEntityDao != null) {
      return _cacheEntityDao;
    } else {
      synchronized(this) {
        if(_cacheEntityDao == null) {
          _cacheEntityDao = new CacheEntityDao_Impl(this);
        }
        return _cacheEntityDao;
      }
    }
  }
}
