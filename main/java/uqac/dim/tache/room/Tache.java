package uqac.dim.tache.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tache")
public class Tache {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    @ColumnInfo(name = "nom")
    public String nom;

    @ColumnInfo(name = "description")
    public String description;

    @NonNull
    @ColumnInfo(name = "date")
    public String date;

    @NonNull
    @ColumnInfo(name = "heure")
    public String heure;

    @NonNull
    @ColumnInfo(name = "type")
    public String type;

    @NonNull
    @ColumnInfo(name = "categorie")
    public String categorie;

    @ColumnInfo(name = "nombre_de_point")
    public int nombreDePoint;

    @NonNull
    @ColumnInfo(name = "duree")
    public String duree;

    public boolean estComplete;

    public Tache(@NonNull String nom, String description, @NonNull String date, @NonNull String heure, @NonNull String type, @NonNull String categorie, int nombreDePoint, @NonNull String duree){
        this.nom  = nom;
        this.description = description;
        this.date = date;
        this.heure = heure;
        this.type = type;
        this.categorie = categorie;
        this.nombreDePoint = nombreDePoint;
        this.duree = duree;

    }
}
