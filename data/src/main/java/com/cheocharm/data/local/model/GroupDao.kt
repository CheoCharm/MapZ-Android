package com.cheocharm.data.local.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GroupDao {
    @Query("SELECT * FROM `group`")
    fun getAll(): List<Group>

    @Query("SELECT * FROM `group` WHERE id = :groupId")
    fun findById(groupId: Int): Group

    @Insert
    fun insertAll(vararg groups: Group)

    @Delete
    fun delete(group: Group)

    @Query("DELETE FROM `Group`")
    fun clear()
}
