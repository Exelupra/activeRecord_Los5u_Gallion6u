package activeRecord;

public class Personne {

    int id;
    String nom;
    String prenom;

    public Personne(String nom,String prenom){
        this.nom=nom;
        this.prenom=prenom;
        this.id=-1;
    }
}
