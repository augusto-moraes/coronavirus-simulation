public class TestPopulation {

    public static void effaceEcran() {
        String ESC = "\033["; 
        System.out.print(ESC+"2J"); 
        System.out.print(ESC+"0;0H"); 
        System.out.flush();
    }
    
    public static void pause(int ms) { 
        try{
            Thread.sleep(ms); 
        } catch(InterruptedException e) {}
    }

    public static void main(String[] args) {
        Population lyon = new Population(40,0.5,0.5, 0.4, 4, 4);

        while(lyon.stillActive()) {
            effaceEcran();
            lyon.affichePop();
            lyon.miseAJour();
            pause(100);
        }
	effaceEcran();
    lyon.affichePop();
    }
}
