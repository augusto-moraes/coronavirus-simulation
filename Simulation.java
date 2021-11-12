public class Simulation {
    public static void main(String[] args) {
        Population lyon = new Population(300, 0.4, 0.4, 0.05, 25, 200);

        Affichage.setDelay(1);

        while(lyon.stillActive()) {
            Affichage.affichePop(lyon);
            lyon.miseAJour();
        }
        Affichage.affichePop(lyon);
    }
}
