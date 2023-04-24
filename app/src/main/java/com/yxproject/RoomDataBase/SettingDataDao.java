package com.yxproject.RoomDataBase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SettingDataDao {

    String tableName = "Setting";
    /**=======================================================================================*/
    /**簡易新增所有資料的方法*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)//預設萬一執行出錯怎麼辦，REPLACE為覆蓋
    void insertSettingData(Setting_entity Settingentity);

    /**=======================================================================================*/
    /**撈取全部資料*/
    @Query("SELECT * FROM " + tableName)
    List<Setting_entity> displayAllfromSetting();

    /**撈取某個名字的相關資料*/
    @Query("SELECT * FROM " + tableName + " WHERE name = :name")
    List<Setting_entity> findSettingDataByName(String name);

    /**撈取某些職務為??的資料**/
    @Query("SELECT * FROM " + tableName + " WHERE elseInfo LIKE '%' || :elseinfo || '%'")
    List<Setting_entity> findSettingByTags(String elseinfo);

    /**=======================================================================================*/
    /**簡易更新資料的方法*/
    @Update
    void updateData(Setting_entity Settingentity);

    /**複雜(?)更新資料的方法*/
    @Query("UPDATE " + tableName + " SET name = :name,value_a = :value_a,value_b = :value_b,elseInfo = :elseInfo WHERE id = :id" )
    void updateData(int id, String name, String value_a,String value_b, String elseInfo);

    /**=======================================================================================*/
    /**簡單刪除資料的方法*/
    @Delete
    void deleteData(Setting_entity Settingentity);

    /**複雜(?)刪除資料的方法*/
    @Query("DELETE  FROM " + tableName + " WHERE id = :id")
    void deleteSettingData(int id);

}
