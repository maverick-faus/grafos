import java.util.HashMap;
import java.util.HashSet;

public class MainGrafo {
  public static void main(String args[]) {
    Grafo g1 = new Grafo();
    // aristas,nodos,dirigido,autociclo
    g1.ErdosRenyi(10000,500, false, false);

    // nodos,proba,dirigido,autociclo
    // g1.Gilbert(500, (float) 0.5, false, false);
    // g1 = new Grafo();

    // nodos,distancia,dirigido,autociclo
    // g1.Geo(30, .5, false, false);

    // nodos,factor,dirigido,autociclo
     //g1.BarabasiAlbert(10, 3, false, false);

    //-----------------------------

    g1.RandomEdgeValues(1, 50);
    System.out.println("Peso total grafo: " + g1.sumaTotalAristas());

    Grafo krD = g1. Kruskal_D();
    krD.WriteFile("Erdos_KruskalD",false,krD.numNodos(),krD.suma);
    System.out.println("Peso MSP KruskalD: " + krD.suma);
    

    Grafo krI = g1. Kruskal_I();
    krI.WriteFile("Erdos_KruskalI",false,krI.numNodos(),krI.suma);
    System.out.println("Peso MSP KruskalI: " + krI.suma);
    
    Grafo prim = g1.Prim();
    prim.WriteFile("Erdos_Prim",false,prim.numNodos(),prim.suma);
    System.out.println("Peso MSP Prim: " + prim.suma);
 
  }
}
