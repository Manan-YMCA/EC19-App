package com.elementsculmyca.ec19_app.DataSources.LocalServices;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM tb_users")
    List<UserLocalModel> getAll();

    @Query("SELECT * FROM tb_users WHERE eventid LIKE :search ")
    public List<UserLocalModel> getTicketbyId(String search);

    @Insert
    void insertAll(UserLocalModel... articles);

    @Delete
    void delete(UserLocalModel articles);

    @Query("DELETE FROM tb_users")
    void deleteAll();

}
