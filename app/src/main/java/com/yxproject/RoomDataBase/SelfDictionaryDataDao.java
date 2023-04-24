package com.yxproject.RoomDataBase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SelfDictionaryDataDao {

    String tableName = "SelfDictionary";
    /**=======================================================================================*/
    /**簡易新增所有資料的方法*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)//預設萬一執行出錯怎麼辦，REPLACE為覆蓋
    void insertDictionaryData(SelfDictionary_entity Dictionaryentity);

    /**=======================================================================================*/
    /**撈取全部資料*/
    @Query("SELECT * FROM " + tableName)
    List<SelfDictionary_entity> displayAllfromDictionary();

    /**撈取所有標籤非"setting_values"的資料**/
    @Query("SELECT * FROM " + tableName + " WHERE tags NOT LIKE '%' || 'setting_values' || '%'")
    List<SelfDictionary_entity> displayAllfromDictionary_exsetting();

    /**撈取所有標籤為"setting_values"的資料**/
    @Query("SELECT * FROM " + tableName + " WHERE tags LIKE '%' || 'setting_values' || '%'")
    List<SelfDictionary_entity> displayAllfromDictionary_setting();

    /**撈取某些職務為??的資料**/
    @Query("SELECT * FROM " + tableName + " WHERE tags LIKE '%' || :tags || '%'")
    List<SelfDictionary_entity> findDictionaryByTags(String tags);

    /**撈取某些字詞為??的解釋資料**/
    @Query("SELECT annotation FROM " + tableName + " WHERE word LIKE '%' || :word || '%'")
    String findDictionaryByWords(String word);

    /**撈取某些字詞為??的解釋資料ID**/
    @Query("SELECT id FROM " + tableName + " WHERE word LIKE '%' || :word || '%'")
    Integer findDictionaryIdByWords(String word);

    @Query("SELECT EXISTS(SELECT * FROM " + tableName + " WHERE word LIKE '%' || :word || '%')")
    Boolean isRowIsExistByword(String word);

    /**=======================================================================================*/
    /**簡易更新資料的方法*/
    @Update
    void updateData(SelfDictionary_entity Dictionaryentity);

    /**複雜(?)更新資料的方法*/
    @Query("UPDATE " + tableName + " SET word = :word,annotation = :annotation,tags=:tags,elseInfo = :elseInfo WHERE id = :id" )
    void updateData(int id, String word, String annotation, String tags, String elseInfo);

    /**=======================================================================================*/
    /**簡單刪除資料的方法*/
    @Delete
    void deleteData(SelfDictionary_entity Dictionaryentity);

    /**複雜(?)刪除資料的方法*/
    @Query("DELETE  FROM " + tableName + " WHERE id = :id")
    void deleteDictionaryData(int id);

}
