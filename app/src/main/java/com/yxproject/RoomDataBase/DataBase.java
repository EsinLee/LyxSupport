package com.yxproject.RoomDataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.Dictionary;

@Database(entities = {Members_entity.class,Items_entity.class,Dispatch_entity.class,SelfDictionary_entity.class},version = 4,exportSchema = true)//資料綁定的Getter-Setter,資料庫版本,是否將資料導出至文件
public abstract class DataBase extends RoomDatabase {
    public static final String DB_NAME = "RecordData.db";//資料庫名稱
    private static volatile DataBase instance;

    public static synchronized DataBase getInstance(Context context){
        /*if(instance == null){
            instance = create(context);//創立新的資料庫
        }*/

        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),DataBase.class,DB_NAME).addMigrations(MIGRATION_1_2,MIGRATION_2_3,MIGRATION_3_4,MIGRATION_4_5).build();
        }
        return instance;
    }

    private static DataBase create(final Context context){
        return Room.databaseBuilder(context, DataBase.class,DB_NAME).build();
    }

    //新增Members欄位
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE Members ADD COLUMN personaltags TEXT DEFAULT \"無\"");
        }
    };

    //新增Items資料表
    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS Items (id INTEGER NOT NULL, Name TEXT NOT NULL, ItemId TEXT NOT NULL, Quantity INTEGER NOT NULL DEFAULT 0,Indecreade INTEGER NOT NULL DEFAULT 0,StoragePlace TEXT NOT NULL,ItemType TEXT NOT NULL, Source TEXT NOT NULL, ElseInfo TEXT NOT NULL, PRIMARY KEY(id))");
        }
    };

    //新增Dispatch及SelfDictionary資料表
    static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS Dispatch (id INTEGER NOT NULL, Time TEXT NOT NULL, Event TEXT NOT NULL, Area TEXT NOT NULL,Conductor TEXT NOT NULL, Driver TEXT NOT NULL, Car TEXT NOT NULL, ElseInfo TEXT NOT NULL, PRIMARY KEY(id))");
            database.execSQL("CREATE TABLE IF NOT EXISTS SelfDictionary (id INTEGER NOT NULL, Word TEXT NOT NULL, Annotation TEXT NOT NULL, Tag TEXT NOT NULL, ElseInfo TEXT NOT NULL, PRIMARY KEY(id))");
        }
    };

    //新增Setting資料表
    static final Migration MIGRATION_4_5 = new Migration(4, 5) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS Setting (id INTEGER NOT NULL, Name TEXT NOT NULL, ValueA TEXT NOT NULL, ValueB TEXT NOT NULL, ElseInfo TEXT NOT NULL, PRIMARY KEY(id))");
        }
    };

    public abstract MembersDataDao getMembersDataDao();//設置對外接口
    public abstract ItemsDataDao getItemsDataDao();//設置對外接口
    public abstract DispatchDataDao getDispatchDataDao();//設置對外接口
    public abstract SelfDictionaryDataDao getSelfDictionaryDataDao();//設置對外接口
    //public abstract SettingDataDao getSettingDataDao();//設置對外接口
}
