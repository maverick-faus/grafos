import java.util.HashMap;
public class Nodo
{
    public int idNodo;
    public int cnj;
    public HashMap<String, Double> atributes;
    public boolean hasArista;
    public double distancia;
    public Nodo()
    {

    }
    public Nodo(int id)
    {
        idNodo=id;
        cnj=id;
        hasArista=false;
        atributes =new HashMap <String, Double> ();
    }
    public void setIdNodo(int newidNodo)
    {
        idNodo=newidNodo;
    }
    public int getIdNodo()
    {
        return idNodo;
    }

    public boolean tieneArista()
    {
        return hasArista;
    }


    public int grado(HashMap<Integer, Arista> aristas, Nodo n) {
        int gradoNodo = 0;
        for (HashMap.Entry<Integer, Arista> entry : aristas.entrySet()) {
            if (entry.getValue().n1.idNodo == n.idNodo || entry.getValue().n2.idNodo == n.idNodo) {
                gradoNodo++;
            }
        }
        return gradoNodo;
    }

    
}