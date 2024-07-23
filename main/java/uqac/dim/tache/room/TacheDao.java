package uqac.dim.tache.room;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TacheDao {

    @Query("SELECT * FROM tache")
    List<Tache> getAllTaches();

    @Query("SELECT * FROM tache WHERE id=:tacheId")
    Tache getTacheById(int tacheId);

    @Insert
    long addTache(Tache tache);

    @Update
    void updateTache(Tache tache);

    @Delete
    void deleteTache(Tache tache);

    @Query("DELETE FROM tache")
    void deleteAllTache();
}