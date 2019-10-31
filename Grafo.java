import java.util.*;
import java.io.FileWriter;
import java.security.KeyStore.Entry;

public class Grafo {
    public HashMap<Integer, Nodo> nodos = null;
    public HashMap<Integer, Arista> aristas = null;
    String metodo;
    double suma = 0; // Suma de pesos del arbol de expansion minima

    public Grafo() {
        nodos = new HashMap<Integer, Nodo>();
        aristas = new HashMap<Integer, Arista>();
    }

    public Grafo(int n) {
        nodos = new HashMap<Integer, Nodo>();
        aristas = new HashMap<Integer, Arista>();

        for (int i = 1; i <= n; i++) {
            Nodo auxNodo = new Nodo(i);
            nodos.put(i, auxNodo);
        }
    }

    public void ErdosRenyi(int m, int n, boolean dirigido, boolean autociclo) {

        // Debe validar que en general, no se soliciten mas aristas que las que puede
        // sorportar
        // el grafo? ya sea dirigido o no dirigido.
        System.out.println("# of Nodes:" + n);
        System.out.println("# of Edges:" + m);
        boolean existeArista = false;

        // Generating n Nodes
        for (int i = 1; i <= n; i++) {
            Nodo auxNodo = new Nodo(i);
            nodos.put(i, auxNodo);
        }
        // Generating m random edges
        do {
            int randomNode1 = (int) (n * Math.random()) + 1;
            int randomNode2 = (int) (n * Math.random()) + 1;
            // System.out.println(randomNode1 + "," + randomNode2);

            Nodo auxNodo1 = new Nodo(randomNode1);
            Nodo auxNodo2 = new Nodo(randomNode2);

            Arista auxArista = new Arista();
            auxArista.setIdArista(m);

            if (randomNode1 != randomNode2 || autociclo == true) {
                // Allows autocycle
                auxArista.setn1(auxNodo1);
                auxArista.setn2(auxNodo2);

                if (aristas.isEmpty()) {
                    aristas.put(auxArista.idArista, auxArista);
                    nodos.get(randomNode1).hasArista = true;
                    nodos.get(randomNode2).hasArista = true;
                    m--;
                } else {
                    for (HashMap.Entry<Integer, Arista> entry : aristas.entrySet()) {
                        if (auxArista.mismaArista(auxArista, entry.getValue(), dirigido)) {
                            existeArista = true;
                            break;
                        }
                    }
                    if (!existeArista) {
                        aristas.put(auxArista.idArista, auxArista);
                        nodos.get(randomNode1).hasArista = true;
                        nodos.get(randomNode2).hasArista = true;
                        m--;
                    } else
                        existeArista = false;
                }
            }
        } while (m != 0);
        WriteFile("ErdosRenyi", dirigido, n);
        metodo = "ErdosRenyi";
    }

    public void Gilbert(int n, float p, boolean dirigido, boolean autociclo) {
        Random rand = new Random();
        int idA = 1;
        boolean existeArista = false;
        // Generating n Nodes
        for (int i = 1; i <= n; i++) {
            Nodo auxNodo = new Nodo(i);
            nodos.put(i, auxNodo);
        }
        // Deciding if we connect nodes
        for (HashMap.Entry<Integer, Nodo> firstEntry : nodos.entrySet()) {
            for (HashMap.Entry<Integer, Nodo> lastEntry : nodos.entrySet()) {
                Arista auxArista = new Arista();
                auxArista.setIdArista(idA);
                auxArista.setn1(firstEntry.getValue());
                auxArista.setn2(lastEntry.getValue());

                if (firstEntry.getValue().idNodo != lastEntry.getValue().idNodo || autociclo == true) {
                    // System.out.println(firstEntry.getValue().idNodo + "-" +
                    // lastEntry.getValue().idNodo);
                    if (aristas.isEmpty()) {
                        if (rand.nextDouble() < p) {

                            aristas.put(auxArista.idArista, auxArista);
                            firstEntry.getValue().hasArista = true;
                            lastEntry.getValue().hasArista = true;
                            idA++;
                        }
                    } else {
                        for (HashMap.Entry<Integer, Arista> entry : aristas.entrySet()) {
                            if (auxArista.mismaArista(auxArista, entry.getValue(), dirigido)) {
                                existeArista = true;
                                break;
                            }
                        }
                        if (!existeArista) {
                            if (rand.nextDouble() < p) {
                                aristas.put(auxArista.idArista, auxArista);
                                firstEntry.getValue().hasArista = true;
                                lastEntry.getValue().hasArista = true;
                                idA++;
                            }
                        } else
                            existeArista = false;

                    }
                }
            }
        }
        WriteFile("Gilbert", dirigido, n);
        metodo = "Gilbert";
    }

    public void Geo(int n, double r, boolean dirigido, boolean autociclo) {
        Random rand = new Random();
        double coordx = rand.nextFloat();
        double coordy = rand.nextFloat();
        boolean existeArista = false;
        int idA = 1;

        // Generating n Nodes
        for (int i = 1; i <= n; i++) {
            coordx = rand.nextFloat();
            coordy = rand.nextFloat();
            Nodo auxNodo = new Nodo(i);
            auxNodo.atributes.put("x", coordx);
            auxNodo.atributes.put("y", coordy);
            nodos.put(i, auxNodo);
        }
        for (HashMap.Entry<Integer, Nodo> firstEntry : nodos.entrySet()) {
            for (HashMap.Entry<Integer, Nodo> lastEntry : nodos.entrySet()) {
                Arista auxArista = new Arista();
                auxArista.setIdArista(idA);
                auxArista.setn1(firstEntry.getValue());
                auxArista.setn2(lastEntry.getValue());
                double distanciaNodos = distancia(firstEntry.getValue(), lastEntry.getValue());

                if (firstEntry.getValue().idNodo != lastEntry.getValue().idNodo || autociclo == true) {
                    if (aristas.isEmpty()) {
                        if (distanciaNodos <= r) {

                            aristas.put(auxArista.idArista, auxArista);
                            firstEntry.getValue().hasArista = true;
                            lastEntry.getValue().hasArista = true;
                            idA++;
                        }
                    } else {
                        for (HashMap.Entry<Integer, Arista> entry : aristas.entrySet()) {
                            if (auxArista.mismaArista(auxArista, entry.getValue(), dirigido)) {
                                existeArista = true;
                                break;
                            }
                        }
                        if (!existeArista) {
                            if (distanciaNodos <= r) {
                                aristas.put(auxArista.idArista, auxArista);
                                firstEntry.getValue().hasArista = true;
                                lastEntry.getValue().hasArista = true;
                                idA++;
                            }
                        } else
                            existeArista = false;
                    }
                }
            }
        }
        WriteFile("Geo", dirigido, n);
        metodo = "Geo";
    }

    public void BarabasiAlbert(int n, int d, boolean dirigido, boolean autociclo) {
        double prob = 0;
        int idA = 1;
        double gradoNodo;
        for (int i = 1; i <= n; i++) {
            Nodo auxNodo = new Nodo(i);

            // System.out.println("Nodo Actual: " + i);
            for (HashMap.Entry<Integer, Nodo> firstEntry : nodos.entrySet()) {
                Arista auxArista = new Arista();
                HashMap aristasAux = new HashMap<Integer, Arista>(this.aristas);
                auxArista.setIdArista(idA);
                auxArista.n1 = auxNodo;
                auxArista.n2 = firstEntry.getValue();
                gradoNodo = auxNodo.grado(aristasAux, firstEntry.getValue());
                // System.out.println("\nNodo a Probar: " + firstEntry.getValue().idNodo);
                prob = 1 - (gradoNodo / d);
                // System.out.println("Probabilidad: " + prob);
                if (Math.random() < prob) {
                    auxNodo.hasArista = true;
                    firstEntry.getValue().hasArista = true;
                    aristas.put(idA, auxArista);
                    idA++;
                }
                if (dirigido) {
                    auxArista.setIdArista(idA);
                    auxArista.n2 = auxNodo;
                    auxArista.n1 = firstEntry.getValue();
                    if (Math.random() < prob) {
                        auxNodo.hasArista = true;
                        firstEntry.getValue().hasArista = true;
                        aristas.put(idA, auxArista);
                        idA++;
                    }
                }
            }
            nodos.put(i, auxNodo);
            if (autociclo) {
                if (Math.random() < prob) {
                    Arista auxArista = new Arista();
                    auxArista.setIdArista(idA);
                    auxArista.n1 = auxNodo;
                    auxArista.n2 = auxNodo;
                    auxNodo.hasArista = true;
                    aristas.put(idA, auxArista);
                    idA++;
                }
            }
        }

        WriteFile("BarabasiAlbert", dirigido, n);
        metodo = "BarabasiAlbert";
    }

    public double distancia(Nodo n1, Nodo n2) {
        return Math.sqrt(Math.pow((n1.atributes.get("x") - n2.atributes.get("x")), 2)
                + Math.pow((n1.atributes.get("y") - n2.atributes.get("y")), 2));
    }

    public void imprimeNodos() {
        System.out.println("NODOS-----");
        for (HashMap.Entry<Integer, Nodo> entry : nodos.entrySet()) {
            System.out.println("Llave: " + entry.getKey());
            System.out.println("idNodo: " + entry.getValue().idNodo);
            System.out.println("");
        }
    }

    public void imprimeAristas() {
        System.out.println("ARISTAS-----");
        for (HashMap.Entry<Integer, Arista> entry : aristas.entrySet()) {
            System.out.println("Llave: " + entry.getKey());
            System.out.println("idArista: " + entry.getValue().idArista);
            System.out.println("Peso: " + entry.getValue().peso);
            System.out.println(entry.getValue().n1.idNodo + "--" + entry.getValue().n2.idNodo);
            System.out.println("");
        }
    }

    Comparator<Nodo> compCola = new Comparator<Nodo>() {
        @Override
        public int compare(Nodo n1, Nodo n2) {
            return Double.compare(n1.distancia, n2.distancia);
        }
    };

    public void WriteFileDijkstra(String name, boolean dir, int n) {
        String conector_gv;
        if (dir) {
            name = name + "_dir_" + n;
            conector_gv = "->";
        } else {
            name = name + "_" + n;

            conector_gv = "--";
        }
        try {
            FileWriter fw = new FileWriter(name + ".gv");
            if (dir)
                fw.write("digraph " + name + " {\n");
            else
                fw.write("graph " + name + " {\n");
            for (HashMap.Entry<Integer, Arista> entry : aristas.entrySet()) {
                fw.write(entry.getValue().n1.idNodo + conector_gv + entry.getValue().n2.idNodo + " [ label=\" "
                        + String.format("%.2f", entry.getValue().peso) + "\" ];\n");
            }

            for (HashMap.Entry<Integer, Nodo> entry : nodos.entrySet()) {
                fw.write(entry.getValue().idNodo + " [label=\"" + String.format("%.2f", entry.getValue().distancia)
                        + "\"]\n");

            }
            fw.write("}");
            fw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("File " + name + ".gv Generated Successfully...");
    }

    public void WriteFile(String name, boolean dir, int n) {
        String conector_gv;
        if (dir) {
            name = name + "_dir_" + n;
            conector_gv = "->";
        } else {
            name = name + "_noDir_" + n;

            conector_gv = "--";
        }
        try {
            FileWriter fw = new FileWriter(name + ".gv");
            if (dir)
                fw.write("digraph " + name + " {\n");
            else
                fw.write("graph " + name + " {\n");
            for (HashMap.Entry<Integer, Arista> entry : aristas.entrySet()) {
                fw.write(entry.getValue().n1.idNodo + conector_gv + entry.getValue().n2.idNodo + ";\n");
            }

            for (HashMap.Entry<Integer, Nodo> entry : nodos.entrySet()) {
                if (!entry.getValue().hasArista) {
                    fw.write(entry.getValue().idNodo + ";\n");
                }
            }
            fw.write("}");
            fw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("File " + name + ".gv Generated Successfully...");
    }

    public void WriteFile(String name, boolean dir, int n, double peso) {
        String conector_gv;
        if (dir) {
            name = name + "_dir_" + n;
            conector_gv = "->";
        } else {
            name = name + "_noDir_" + n;

            conector_gv = "--";
        }
        try {
            FileWriter fw = new FileWriter(name + ".gv");
            if (dir)
                fw.write("digraph " + name + " {\n");
            else
                fw.write("graph " + name + " {\n");
            fw.write("label = \"Peso del grafo: " + peso + "\";");
            fw.write("labelloc =\"t\";"); // place the label at the top
            for (HashMap.Entry<Integer, Arista> entry : aristas.entrySet()) {
                fw.write(entry.getValue().n1.idNodo + conector_gv + entry.getValue().n2.idNodo + ";\n");
            }

            for (HashMap.Entry<Integer, Nodo> entry : nodos.entrySet()) {
                if (!entry.getValue().hasArista) {
                    fw.write(entry.getValue().idNodo + ";\n");
                }
            }
            fw.write("}");
            fw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("File " + name + ".gv Generated Successfully...");
    }

    public int numNodos() {
        return this.nodos.size();
    }

    public HashSet<Nodo> obtieneAdyacentes(int i) {
        HashSet<Nodo> nodosAdyacentes = new HashSet<Nodo>();
        for (HashMap.Entry<Integer, Arista> entry : aristas.entrySet()) {
            if (entry.getValue().n1.idNodo == i) {
                nodosAdyacentes.add(entry.getValue().n2);
            } else {
                if (entry.getValue().n2.idNodo == i) {
                    nodosAdyacentes.add(entry.getValue().n1);
                }
            }
        }
        return nodosAdyacentes;
    }

    public HashMap<Integer, Nodo> mapaAdyacentes(int i) {
        HashMap<Integer, Nodo> nodosAdyacentes = new HashMap<Integer, Nodo>();
        for (HashMap.Entry<Integer, Arista> entry : aristas.entrySet()) {
            if (entry.getValue().n1.idNodo == i) {
                nodosAdyacentes.put(entry.getValue().n2.idNodo, entry.getValue().n2);
            } else {
                if (entry.getValue().n2.idNodo == i) {
                    nodosAdyacentes.put(entry.getValue().n1.idNodo, entry.getValue().n1);
                }
            }
        }
        return nodosAdyacentes;
    }

    public HashSet<Arista> obtieneIncidentes(int i) {
        HashSet<Arista> aristasIncidentes = new HashSet<Arista>();
        for (HashMap.Entry<Integer, Arista> entry : aristas.entrySet()) {
            if (entry.getValue().n1.idNodo == i) {
                aristasIncidentes.add(entry.getValue());
            } else {
                if (entry.getValue().n2.idNodo == i) {
                    aristasIncidentes.add(entry.getValue());
                }
            }
        }

        return aristasIncidentes;
    }

    public Grafo BFS(int s) {
        int nodoSize = this.numNodos();
        //// OJo aqui
        Grafo arbol = new Grafo();
        Boolean[] discovered = new Boolean[nodoSize + 1];
        discovered[s] = true;
        PriorityQueue<Integer> L = new PriorityQueue<Integer>();
        int idA = 1;

        for (int i = 1; i <= nodoSize; i++) {
            if (i != s) {
                discovered[i] = false;
            }
        }
        L.add(s);

        while (L.peek() != null) {
            int u = L.poll();
            HashSet<Nodo> adyacentes = this.obtieneAdyacentes(u);
            for (Nodo n : adyacentes) {
                if (!discovered[n.idNodo]) {

                    Arista auxArista = new Arista();
                    Nodo auxNodo = new Nodo(u);
                    Nodo auxNodo2 = new Nodo(n.idNodo);
                    arbol.nodos.put(u, auxNodo);
                    arbol.nodos.put(n.idNodo, auxNodo2);

                    auxArista.setIdArista(idA);
                    auxArista.n1 = new Nodo(u);
                    auxArista.n2 = new Nodo(n.idNodo);
                    arbol.nodos.get(u).hasArista = true;
                    arbol.nodos.get(n.idNodo).hasArista = true;
                    arbol.aristas.put(idA, auxArista);
                    idA++;
                    discovered[n.idNodo] = true;
                    L.add(n.idNodo);
                }
            }
        }
        return arbol;
    }

    public Grafo DFS_R(int s) {
        int nodoSize = this.numNodos();
        Grafo arbol = new Grafo();

        Boolean[] discovered = new Boolean[nodoSize + 1];
        for (int i = 1; i <= nodoSize; i++) {
            discovered[i] = false;
        }
        rDFS(s, discovered, arbol, 1);
        return arbol;
    }

    public void rDFS(int u, Boolean[] discovered, Grafo arbol, int idAr) {
        discovered[u] = true;
        HashSet<Nodo> adyacentes = this.obtieneAdyacentes(u);
        for (Nodo n : adyacentes) {
            if (!discovered[n.idNodo]) {

                while (arbol.aristas.get(idAr) != null) {
                    idAr++;
                }
                Arista auxArista = new Arista();
                Nodo auxNodo = new Nodo(u);
                Nodo auxNodo2 = new Nodo(n.idNodo);
                arbol.nodos.put(u, auxNodo);
                arbol.nodos.put(n.idNodo, auxNodo2);

                auxArista.setIdArista(idAr);
                auxArista.n1 = new Nodo(u);
                auxArista.n2 = new Nodo(n.idNodo);
                arbol.nodos.get(u).hasArista = true;
                arbol.nodos.get(n.idNodo).hasArista = true;
                arbol.aristas.put(idAr, auxArista);

                rDFS(n.idNodo, discovered, arbol, idAr + 1);
            }
        }
    }

    public Grafo DFS_I(int s) {
        int nodoSize = this.numNodos();
        int idA = 1;
        Grafo arbol = new Grafo();
        Boolean[] explored = new Boolean[nodoSize + 1];
        Stack<Integer> S = new Stack<Integer>();
        Integer[] parent = new Integer[nodoSize + 1];
        for (int i = 0; i <= nodoSize; i++) {
            explored[i] = false;
        }
        S.push(s);
        while (!S.isEmpty()) {

            int u = S.pop();
            if (!explored[u]) {
                explored[u] = true;
                if (u != s) {
                    Arista auxArista = new Arista();
                    Nodo auxNodo = new Nodo(u);
                    Nodo auxNodo2 = new Nodo(parent[u]);
                    arbol.nodos.put(u, auxNodo);
                    arbol.nodos.put(parent[u], auxNodo2);

                    auxArista.setIdArista(idA);
                    auxArista.n1 = new Nodo(u);
                    auxArista.n2 = new Nodo(parent[u]);
                    arbol.nodos.get(u).hasArista = true;
                    arbol.nodos.get(parent[u]).hasArista = true;
                    arbol.aristas.put(idA, auxArista);
                    idA++;
                }
                HashSet<Nodo> aristas = this.obtieneAdyacentes(u);
                for (Nodo n : aristas) {
                    S.push(n.idNodo);
                    parent[n.idNodo] = u;
                }
            }
        }
        return arbol;
    }

    public void RandomEdgeValues(double min, double max) {

        Random rand = new Random();
        double w;
        for (HashMap.Entry<Integer, Arista> entry : aristas.entrySet()) {
            w = rand.nextFloat() * (max - min) + min;
            entry.getValue().peso = w;

        }

    }

    public void printEdgeValues() {
        for (HashMap.Entry<Integer, Arista> entry : aristas.entrySet()) {
            System.out.println(entry.getValue().idArista + "-" + entry.getValue().peso);
        }
    }

    public double sumaTotalAristas() {
        double d = 0;
        for (HashMap.Entry<Integer, Arista> entry : aristas.entrySet()) {
            d = d + entry.getValue().peso;
        }
        return d;
    }

    public int aristan1n2(int n_1, int n_2) {
        int ida = 0;
        for (HashMap.Entry<Integer, Arista> entry : aristas.entrySet()) {
            if ((entry.getValue().n1.idNodo == n_1 && entry.getValue().n2.idNodo == n_2)
                    || (entry.getValue().n2.idNodo == n_1 && entry.getValue().n1.idNodo == n_2))
                ida = entry.getValue().idArista;
        }
        return ida;
    }

    public Grafo Dijkstra(int s) {
        Grafo arbol = new Grafo(this.numNodos());
        int nodoDestino;
        double infinito = Double.POSITIVE_INFINITY;
        Integer[][] padres = new Integer[arbol.numNodos()][2];

        for (HashMap.Entry<Integer, Nodo> entry : nodos.entrySet()) {
            entry.getValue().distancia = infinito;
            padres[entry.getValue().idNodo - 1][0] = null;
        }

        this.nodos.get(s).distancia = 0.0;
        padres[s - 1][0] = s;

        PriorityQueue<Nodo> distPQ = new PriorityQueue<>(compCola);
        for (HashMap.Entry<Integer, Nodo> entry : nodos.entrySet()) {

            distPQ.add(entry.getValue());
        }

        while (distPQ.peek() != null) {
            Nodo u = distPQ.poll();

            HashSet<Arista> aristas = this.obtieneIncidentes(u.idNodo);
            for (Arista e : aristas) {
                if (e.n1.idNodo == u.idNodo)
                    nodoDestino = e.n2.idNodo;
                else
                    nodoDestino = e.n1.idNodo;
                // System.out.println("ND "+ nodoDestino);

                if (this.nodos.get(nodoDestino).distancia > this.nodos.get(u.idNodo).distancia + e.peso) {
                    this.nodos.get(nodoDestino).distancia = this.nodos.get(u.idNodo).distancia + e.peso;
                    padres[nodoDestino - 1][0] = u.idNodo;
                    padres[nodoDestino - 1][1] = e.idArista;
                }
            }

        }

        for (int i = 1; i <= arbol.numNodos(); i++) {
            Arista auxArista = new Arista();
            auxArista.setIdArista(i);
            auxArista.n1 = arbol.nodos.get(i);
            auxArista.n2 = arbol.nodos.get(padres[i - 1][0]);
            if (padres[i - 1][1] != null)
                auxArista.peso = this.aristas.get(padres[i - 1][1]).peso;
            if (auxArista.n1.idNodo != auxArista.n2.idNodo)
                arbol.aristas.put(i, auxArista);
            arbol.nodos.get(i).hasArista = true;
            arbol.nodos.get(padres[i - 1][0]).hasArista = true;
            arbol.nodos.get(i).distancia = this.nodos.get(i).distancia;
        }
        return arbol;
    }

    public HashMap<Integer, Arista> ordenaAristas(HashMap<Integer, Arista> hm, boolean desc) {
        List<Map.Entry<Integer, Arista>> list = new LinkedList<Map.Entry<Integer, Arista>>(hm.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<Integer, Arista>>() {
            public int compare(Map.Entry<Integer, Arista> o1, Map.Entry<Integer, Arista> o2) {
                return Double.compare(o1.getValue().peso, o2.getValue().peso);
            }
        });
        if (desc)
            Collections.reverse(list);
        HashMap<Integer, Arista> temp = new LinkedHashMap<Integer, Arista>();
        for (Map.Entry<Integer, Arista> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    public Grafo Kruskal_D() {
        Grafo arbol = new Grafo(this.numNodos());

        HashMap<Integer, Arista> aristas_sort = ordenaAristas(this.aristas, false);

        for (HashMap.Entry<Integer, Arista> entry : aristas_sort.entrySet()) {
            int one = entry.getValue().n1.idNodo;
            int two = entry.getValue().n2.idNodo;
            // System.out.println(entry.getValue().idArista + "-" + entry.getValue().peso +
            // "[" + one + "," + two + "]");

            if (arbol.nodos.get(one).cnj != arbol.nodos.get(two).cnj) {
                Arista auxArista = new Arista();

                auxArista.atributes = entry.getValue().atributes;
                auxArista.idArista = entry.getValue().idArista;
                auxArista.n1 = entry.getValue().n1;
                auxArista.n2 = entry.getValue().n2;
                auxArista.peso = entry.getValue().peso;

                arbol.nodos.get(auxArista.n1.idNodo).hasArista = true;
                arbol.nodos.get(auxArista.n2.idNodo).hasArista = true;
                arbol.aristas.put(auxArista.idArista, auxArista);

                int oldcnj = arbol.nodos.get(auxArista.n2.idNodo).cnj;
                int newconj = arbol.nodos.get(auxArista.n1.idNodo).cnj;

                for (HashMap.Entry<Integer, Nodo> entry2 : arbol.nodos.entrySet()) {
                    if (entry2.getValue().cnj == oldcnj)
                        entry2.getValue().cnj = newconj;
                }

            }
        }
        arbol.suma = arbol.sumaTotalAristas();
        return arbol;
    }

    public Grafo Kruskal_I() {
        Grafo arbol = new Grafo(this.numNodos());
        int nodosTotales = this.numNodos();
        HashMap<Integer, Arista> aristas_sort = ordenaAristas(this.aristas, true);

        HashMap<Integer, Arista> aristas_originales = (this.aristas);
        arbol.aristas = aristas_originales;

        arbol.suma = arbol.sumaTotalAristas();

        for (HashMap.Entry<Integer, Arista> entry : aristas_sort.entrySet()) {

            Arista auxArista = new Arista();
            auxArista.atributes = entry.getValue().atributes;
            auxArista.idArista = entry.getValue().idArista;
            auxArista.n1 = entry.getValue().n1;
            auxArista.n2 = entry.getValue().n2;
            auxArista.peso = entry.getValue().peso;

            arbol.aristas.remove(entry.getKey());

            int numNodos = arbol.BFS(1).numNodos();

            if (numNodos != nodosTotales)
                arbol.aristas.put(auxArista.idArista, auxArista);
        }
        arbol.suma = arbol.sumaTotalAristas();
        return arbol;
    }

    public int minNode(HashMap<Integer, Nodo> n) {
        double peso = Double.POSITIVE_INFINITY;
        int min_node = 0;
        for (HashMap.Entry<Integer, Nodo> entry : n.entrySet()) {
            if (entry.getValue().distancia <= peso) {
                peso = entry.getValue().distancia;
                min_node = entry.getValue().idNodo;
            }
        }
        return min_node;
    }

    public Grafo Prim() 
    {
        double infinito = Double.POSITIVE_INFINITY;
        int nodosTotales = this.numNodos();

        Grafo arbol = new Grafo();
        HashMap<Integer, Nodo> nodosArbol = new HashMap<Integer, Nodo>();
        HashMap<Integer, Arista> aristasArbol = new HashMap<Integer, Arista>();

        int nodoMinimo;

        for (HashMap.Entry<Integer, Nodo> entry : this.nodos.entrySet()) {
            entry.getValue().distancia = infinito;
        }
        this.nodos.get(1).distancia = 0;

        while (nodosArbol.size() != nodosTotales) {
            nodoMinimo = minNode(this.nodos);

            nodosArbol.put(nodoMinimo, this.nodos.get(nodoMinimo));
            this.nodos.remove(nodoMinimo);

            HashMap<Integer, Nodo> adyacentes = this.mapaAdyacentes(nodoMinimo);
            HashSet<Arista> incidentes = this.obtieneIncidentes(nodoMinimo);

            for (Arista a: incidentes)
            {
                if (a.n1.idNodo == nodoMinimo ||a.n2.idNodo== nodoMinimo)
                {
                    aristasArbol.put(this.aristas.get(a.idArista).idArista,this.aristas.get(a.idArista));
                }
            }
          
            for (HashMap.Entry<Integer, Nodo> entry : nodosArbol.entrySet()) {
                adyacentes.remove(entry.getKey());
            }

            for (HashMap.Entry<Integer, Nodo> entry : adyacentes.entrySet()) {
                for (Arista a : incidentes) {
                    if (a.n1.idNodo == entry.getValue().idNodo || a.n2.idNodo == entry.getValue().idNodo) {
                        double dist = this.nodos.get(entry.getValue().idNodo).distancia;
                        if (a.peso < dist)
                            this.nodos.get(entry.getValue().idNodo).distancia = a.peso;
                    }
                }

            }
        
        }
        arbol.nodos=nodosArbol;
        arbol.aristas=aristasArbol;
        arbol.suma = arbol.sumaTotalAristas();
        return arbol;
    }
}
