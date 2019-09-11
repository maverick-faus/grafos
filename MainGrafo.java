import java.util.HashMap;

public class MainGrafo {
    public static void main(String args[]) {
        Grafo g1 = new Grafo();
        // aristas,nodos,dirigido,autociclo
        g1.ErdosRenyi(10, 5, true, true);

        // nodos,proba,dirigido,autociclo
        g1.Gilbert(5, (float) 0.5, true, true);
        g1 = new Grafo();

        // nodos,distancia,dirigido,autociclo
        g1.Geo(5, .1, true, true);
        g1 = new Grafo();

        // nodos,factor,dirigido,autociclo
        g1.BarabasiAlbert(5, 5, true, true);
    }
}