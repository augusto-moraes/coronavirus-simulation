public class Personne {
    protected int etat;
    protected int sickSince;
    protected boolean resistent;

    public Personne() {
        this.etat = 1;
        this.sickSince = 0;
        this.resistent = false;
    }

    public Personne(double tauxVaccines, double tauxDeces) {
        this.sickSince = 0;
        this.etat = Math.random() <= tauxVaccines ? 4 : 1;
        this.resistent = Math.random() > tauxDeces;
    }

    public Personne(Personne clone) {
        this.sickSince = clone.sickSince;
        this.etat = clone.getEtat();
        this.resistent = clone.isResistent();
    }

    public boolean isMalade() {
        return this.etat == 2;
    }

    public boolean isResistent() {
        return this.resistent;
    }

    public void infect() {
        this.etat = 2;
    }

    public void tryToGuerir() {
        this.etat = this.resistent ? 4 : this.etat;
    }

    public void kill() {
        this.etat = 3;
    }

    public int getEtat() {
        return this.etat;
    }

    public void setEtat(int n) {
        this.etat = n;
    }

    public int getJoursMalade() {
        return this.sickSince;
    }

    public void increaseJoursMalade() {
        this.sickSince++;
    }

    public char affichePersonne() {
        switch(etat) {
            case 1:
                return 'S';
            case 2:
                return 'M';
            case 3:
                return 'X';
            case 4:
                return 'I';
            default:
                return '0';
        }
    };

    public String toString() {
        switch(etat) {
            case 1:
                return "sain";
            case 2:
                return "malade";
            case 3:
                return "décédée";
            case 4:
                return "immunisée";
            default:
                return "case vide";
        }
    }
}
