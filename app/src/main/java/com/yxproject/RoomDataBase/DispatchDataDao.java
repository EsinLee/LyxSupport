package com.yxproject.RoomDataBase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DispatchDataDao {

    String tableName = "Dispatch";
    /**=======================================================================================*/
    /**簡易新增所有資料的方法*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)//預設萬一執行出錯怎麼辦，REPLACE為覆蓋
    void insertDispatchData(Dispatch_entity Dispatchentity);

    /**=======================================================================================*/
    /**撈取全部資料*/
    @Query("SELECT * FROM " + tableName)
    List<Dispatch_entity> displayAllfromDispatch();

    /**撈取某個名字的相關資料*/
    @Query("SELECT * FROM " + tableName + " WHERE event = :event")
    List<Dispatch_entity> findDispatchDataByName(String event);

    /**撈取某些職務為??的資料**/
    @Query("SELECT * FROM " + tableName + " WHERE elseInfo LIKE '%' || :tag || '%'")
    List<Dispatch_entity> findDispatchByPersonalTags(String tag);

    /**撈取某天的派車資料*/
    @Query("SELECT * FROM " + tableName + " WHERE time LIKE '%' || :time || '%'")
    List<Dispatch_entity> findDispatchDataByTime(String time);

    /**=======================================================================================*/
    /**簡易更新資料的方法*/
    @Update
    void updateData(Dispatch_entity Dispatchentity);

    /**複雜(?)更新資料的方法*/
    @Query("UPDATE " + tableName + " SET time = :time,event = :event,area=:area,conductor=:conductor,driver = :driver,car = :car,elseInfo = :elseInfo WHERE id = :id" )
    void updateData(int id, String time, String event, String area, Integer conductor, Integer driver, String car, String elseInfo);

    /**=======================================================================================*/
    /**簡單刪除資料的方法*/
    @Delete
    void deleteData(Dispatch_entity Dispatchentity);

    /**複雜(?)刪除資料的方法*/
    @Query("DELETE  FROM " + tableName + " WHERE id = :id")
    void deleteDispatchData(int id);

}
