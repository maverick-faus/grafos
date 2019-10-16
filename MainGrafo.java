import java.util.HashMap;
import java.util.HashSet;

public class MainGrafo {
  public static void main(String args[]) {
    Grafo g1 = new Grafo();
    // aristas,nodos,dirigido,autociclo
   // g1.ErdosRenyi(10000, 500, false, false);
    


    // nodos,proba,dirigido,autociclo
    // g1.Gilbert(500, (float) 0.5, false, false);
    // g1 = new Grafo();

    // nodos,distancia,dirigido,autociclo
    // g1.Geo(500, .5, false, false);

    // nodos,factor,dirigido,autociclo
     g1.BarabasiAlbert(500, 30, false, false);



    //-----------------------------
    // Grafo arbolbfs = g1.BFS(1);
    // arbolbfs.WriteFile("Barabasi_BFS", false, arbolbfs.numNodos());

    // Grafo arboldfsr = g1.DFS_R(1);
    // arboldfsr.WriteFile("Barabasi_DFS_R", false, arboldfsr.numNodos());

    // Grafo arboldfsi = g1.DFS_I(1);
    // arboldfsi.WriteFile("Barabasi_DFS_I", false, arboldfsi.numNodos());

    //-----------------------------
    g1.RandomEdgeValues(1, 50);
    Grafo dijk1 = g1.Dijkstra(1);

    dijk1.WriteFileDijkstra("Dijkstra_Geo", false, dijk1.numNodos());


  }
}
