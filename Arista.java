import java.util.HashMap;
public class Arista
{
    public int idArista;
    public Nodo n1;
    public Nodo n2;
    public double peso;
    public HashMap<String, String> atributes;

    public void setIdArista(int newId)
    {
        idArista=newId;
    }

    public void setn1(Nodo newn1)
    {
        n1=newn1;
    }

    public void setn2(Nodo newn2)
    {
        n2=newn2;
    }

    public int getIdArista()
    {
        return idArista;
    }

    public Nodo getn1()
    {
        return n1;
    }
    public Nodo getn2()
    {
        return n2;
    }

    public boolean mismaArista(Arista a1,Arista a2,boolean dirigido)
    {
        if(a1.n1.idNodo == a2.n1.idNodo && a1.n2.idNodo==a2.n2.idNodo)
            return true;
        else
        {
            if (a1.n1.idNodo==a2.n2.idNodo && a1.n2.idNodo==a2.n1.idNodo && !dirigido)
                return true;
            else
                return false;

        }
    }
}
        