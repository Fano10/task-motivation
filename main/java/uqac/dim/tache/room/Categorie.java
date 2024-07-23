package uqac.dim.tache.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "categorie")
public class Categorie {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "nom")
    public String nom;

    @ColumnInfo(name = "nombre_de_point",defaultValue = "0")
    public int nombreDePoint;

    @ColumnInfo(name = "couleur")
    public String couleur;

    public Categorie(String nom, int nombreDePoint,String couleur){
        this.nom = nom;
        this.nombreDePoint = nombreDePoint;
        this.couleur = couleur;
    }
}
