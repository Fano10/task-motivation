package uqac.dim.tache.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface CategorieDao {
    @Query("SELECT * FROM categorie")
    List<Categorie> getAllCategories();

    @Query("SELECT * FROM categorie WHERE id=:CategorieId")
    Categorie getTacheById(int CategorieId);

    @Query("SELECT * FROM categorie WHERE nom=:categorieNom")
    Categorie getTacheByNom(String categorieNom);

    @Insert
    long addCategorie(Categorie categorie);

    @Update
    void updateCategorie(Categorie categorie);

    @Delete
    void deleteCategorie(Categorie categorie);

    @Query("DELETE FROM categorie")
    void deleteAllCategorie();
}
