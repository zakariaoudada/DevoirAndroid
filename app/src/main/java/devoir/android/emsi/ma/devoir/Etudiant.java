package devoir.android.emsi.ma.devoir;

import java.util.List;

/**
 * Created by Zakaria on 19/11/2016.
 */

public class Etudiant {
    private String nom;
    private String prenom;
    private String classe;
    private List<Note> notes;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }
}
