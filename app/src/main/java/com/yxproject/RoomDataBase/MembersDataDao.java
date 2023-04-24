package com.yxproject.RoomDataBase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MembersDataDao {

    String tableName = "Members";
    /**=======================================================================================*/
    /**簡易新增所有資料的方法*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)//預設萬一執行出錯怎麼辦，REPLACE為覆蓋
    void insertMembersData(Members_entity membersentity);

    /*@Insert(onConflict = OnConflictStrategy.REPLACE)//預設萬一執行出錯怎麼辦，REPLACE為覆蓋
    void insertItemsData(Items_entity itemsentity);*/

    /**=======================================================================================*/
    /**撈取全部資料*/
    @Query("SELECT * FROM " + tableName)
    List<Members_entity> displayAllfromMembers();

    /**撈取某個名字的相關資料*/
    @Query("SELECT * FROM " + tableName + " WHERE name = :name")
    List<Members_entity> findMembersDataByName(String name);

    /**撈取某些職務為??的資料**/
    @Query("SELECT * FROM " + tableName + " WHERE jobfunctions = :jobfunc")
    List<Members_entity> findMembersByJobfunc(String jobfunc);

    /**撈取某些職務為??的資料**/
    @Query("SELECT * FROM " + tableName + " WHERE personaltags LIKE '%' || :tag || '%'")
    List<Members_entity> findMembersByPersonalTags(String tag);

    /**=======================================================================================*/
    /**簡易更新資料的方法*/
    @Update
    void updateData(Members_entity membersentity);

    /**複雜(?)更新資料的方法*/
    @Query("UPDATE " + tableName + " SET name = :name,phone=:phone,rank=:rank,elseInfo = :elseInfo WHERE id = :id" )
    void updateData(int id, String name, String phone, String rank, String elseInfo);

    /**=======================================================================================*/
    /**簡單刪除資料的方法*/
    @Delete
    void deleteData(Members_entity membersentity);

    /**複雜(?)刪除資料的方法*/
    @Query("DELETE  FROM " + tableName + " WHERE id = :id")
    void deleteMembersData(int id);

}
