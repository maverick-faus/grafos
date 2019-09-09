import java.util.HashMap;

public class Hola {
    public static void main(String args[]) {
        Grafo g1 = new Grafo();

        // aristas,nodos,dirigido,autociclo
        /*g1.ErdosRenyi(500, 50, false, false);
        g1 = new Grafo();

        // nodos,proba,dirigido,autociclo
        g1.Gilbert(50, (float) 0.5, false, false);
        g1 = new Grafo();
*/
        // nodos,distancia,dirigido,autociclo
        g1.Geo(1000, .1, false, false);
        g1 = new Grafo();

        // nodos,factor,dirigido,autociclo
        g1.BarabasiAlbert(1000, 5, false, false);
    }
}