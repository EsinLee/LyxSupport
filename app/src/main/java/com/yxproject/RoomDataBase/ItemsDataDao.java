package com.yxproject.RoomDataBase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ItemsDataDao {

    String tableName = "Items";
    /**=======================================================================================*/
    /**簡易新增所有資料的方法*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)//預設萬一執行出錯怎麼辦，REPLACE為覆蓋
    void insertItemsData(Items_entity itemsentity);

    /*@Insert(onConflict = OnConflictStrategy.REPLACE)//預設萬一執行出錯怎麼辦，REPLACE為覆蓋
    void insertItemsData(Items_entity itemsentity);*/

    /**=======================================================================================*/
    /**撈取全部資料*/
    @Query("SELECT * FROM " + tableName)
    List<Items_entity> displayAllfromItems();

    /**撈取某個名字的相關資料*/
    @Query("SELECT * FROM " + tableName + " WHERE name = :name")
    List<Items_entity> findItemsDataByName(String name);

    /**撈取某些職務為??的資料**/
    @Query("SELECT * FROM " + tableName + " WHERE itemtype = :itemtype")
    List<Items_entity> findItemsByitemtype(String itemtype);

    /**撈取某些職務為??的資料**/
    @Query("SELECT * FROM " + tableName + " WHERE elseInfo LIKE '%' || :tag || '%'")
    List<Items_entity> findItemsByPersonalTags(String tag);

    /**=======================================================================================*/
    /**簡易更新資料的方法*/
    @Update
    void updateData(Items_entity itemsentity);

    /**複雜(?)更新資料的方法*/
    @Query("UPDATE " + tableName + " SET name = :name,itemid=:itemid,quantity=:quantity,indecrease = :indecrease,storageplace = :storageplace,itemtype = :itemtype,source = :source,elseInfo = :elseInfo WHERE id = :id" )
    void updateData(int id, String name, String itemid, Integer quantity, Integer indecrease, String storageplace, String itemtype, String source, String elseInfo);

    /**=======================================================================================*/
    /**簡單刪除資料的方法*/
    @Delete
    void deleteData(Items_entity itemsentity);

    /**複雜(?)刪除資料的方法*/
    @Query("DELETE  FROM " + tableName + " WHERE id = :id")
    void deleteItemsData(int id);

}
