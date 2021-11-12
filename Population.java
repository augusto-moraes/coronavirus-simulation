public class Population {
    private double tauxVaccines;
    private double tauxDeces;
    private double tauxContagion;
    private int daysBeforeDeath;
    private int daysBeforeGuerir;
    public int taille;

    public Personne [][] population;
    private Personne [][] newPop;

    public Population(int taille) {
        this.taille = taille;
        this.population = new Personne[taille][taille];
        this.newPop = new Personne[taille][taille];
        this.tauxVaccines = 0;
        this.tauxDeces = 0;
        this.tauxContagion = 0;
        this.daysBeforeGuerir = 0;
        this.daysBeforeDeath = 0;
        this.initialisation();
    }

    public Population(
            int taille, 
            double txVac, 
            double txDeces, 
            double txContag, 
            int daysBeforeGuerir, 
            int daysBeforeDeath
        ) {
        this.taille = taille;
        this.population = new Personne[taille][taille];
        this.newPop = new Personne[taille][taille];
        this.tauxVaccines = txVac;
        this.tauxDeces = txDeces;
        this.tauxContagion = txContag;
        this.daysBeforeGuerir = daysBeforeGuerir;
        this.daysBeforeDeath = daysBeforeDeath;
        this.initialisation();
    }

    public void initialisation() {
        // init de tout le tableau
        for(int i=0; i<this.population.length; i++) {
            for(int j=0; j<this.population[0].length; j++) {
                this.population[i][j] = new Personne(this.tauxVaccines, this.tauxDeces);
                this.newPop[i][j] = new Personne(this.population[i][j]);
            }
        }

        // choix aleatoire de la premiere personne infectée
        int x = (int) Math.floor(Math.random()*this.population.length);
        int y = (int) Math.floor(Math.random()*this.population[0].length);
        
        this.population[x][y].infect();
        this.newPop[x][y].infect();;
    }

    public boolean testVoisinage(int x, int y) {
        return x > 0 && y > 0 && x < this.population.length-1 && y < this.population[0].length-1;
    }

    public boolean isInside(int x, int y) {
        return x > -1 && y > -1 && x < this.population.length && y < this.population[0].length;
    }

    public void miseAJourSain(int x, int y) {
        int[] dir = {-1,0,1};
        int i=0;
        while(i<dir.length && !this.newPop[x][y].isMalade()){
            for(int j=0; j<dir.length; j++) {
                if((dir[i] != 0 || dir[j] != 0) &&
                 this.isInside(x+dir[i], y+dir[j]) &&
                 this.population[x+dir[i]][y+dir[j]].isMalade() &&
                 Math.random() <= this.tauxContagion) {
                    this.newPop[x][y].infect();
                }
            }
            i++;
        }
    }

    public void miseAJourMalade(int x, int y) {
        this.newPop[x][y].increaseJoursMalade();
        if(this.newPop[x][y].getJoursMalade() > this.daysBeforeGuerir) {
            this.newPop[x][y].tryToGuerir();
        } if(this.newPop[x][y].getJoursMalade() > this.daysBeforeDeath) {
            this.newPop[x][y].kill();
        }

    }

    public void miseAJour() {
        for(int i=0; i<this.population.length; i++) {
            for(int j=0; j<this.population[0].length; j++) {
                switch (this.population[i][j].getEtat()) {
                    case 1:
                        this.miseAJourSain(i,j);
                        break;
                    case 2:
                        this.miseAJourMalade(i,j);
                        break;
                    default:
                        break;
                }
            }
        }

        // update pop to newpop
        for(int i=0; i<this.population.length; i++) {
            for(int j=0; j<this.population[0].length; j++) {
                this.population[i][j] = new Personne(this.newPop[i][j]);
            }
        }
    }

    public boolean stillActive() {
        int i=0;
        while(i<this.newPop.length) {
            int j=0;
            while(j<this.newPop[0].length) {
                if(this.newPop[i][j].isMalade()) return true;
                j++;
            }
            i++;
        }
        return false;
    }

    public void affichePop() {
        for(int i=-1; i<=this.newPop.length; i++) {
            for(int j=-1; j<=this.newPop[0].length; j++) {
                if((i == -1 || i == this.newPop.length) && (j == -1 || j == this.newPop[0].length)) {
                    System.out.print("+ " );
                } else if(i == -1 || i == this.newPop.length) {
                    System.out.print("- ");
                } else if(j == -1 || j == this.newPop[0].length) {
                    System.out.print("| ");
                } else {
                    System.out.print(this.newPop[i][j].affichePersonne() + " ");
                }
            }
            System.out.println();
        }
    }
}
