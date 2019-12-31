
/*
* Desarrollado por: Víctor Manuel Cavero Gracia
*/

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random; // Necesario para probar casos de prueba.

public class Main {
    public static ArrayList<Double> tiempo_insertado(int n, Random rn, Arbol234 arbol) {
        int i = 0;
        ArrayList<Double> lista = new ArrayList<Double>();

        while (i < n) {
            Boolean insertar = false;
            long empezar, fin, duracion, media = 0;
            int valor = rn.nextInt(100000);

            if (arbol.buscar(valor) == null)
                insertar = true;

            if (insertar) {
                int j = 0;
                while (j < 1000) {
                    empezar = System.nanoTime();
                    arbol.insertar(valor);
                    fin = System.nanoTime();
                    duracion = (fin - empezar);
                    media += duracion;

                    arbol.borrar(valor);
                    j++;
                }

                media = media / 1000;

                i++;
                lista.add(media * 0.000001); // millisec
            }
        }
        return lista;
    }

    public static ArrayList<Double> tiempo_borrado(int n, Random rn, Arbol234 arbol) {

        int i = 0;
        ArrayList<Double> lista = new ArrayList<Double>();

        while (i < n) {
            Boolean insertar = false;
            long empezar, fin, duracion, media = 0;
            int valor = rn.nextInt(100000);

            if (arbol.buscar(valor) == null)
                insertar = true;

            if (insertar) {
                int j = 0;
                while (j < 1000) {
                    arbol.insertar(valor);

                    empezar = System.nanoTime();
                    arbol.borrar(valor);
                    fin = System.nanoTime();
                    duracion = (fin - empezar);
                    media += duracion;

                    j++;
                }

                media = media / 1000;

                i++;
                lista.add(media * 0.000001); // millisec
            }
        }
        return lista;
    }

    public static ArrayList<Double> tiempo_busqueda(int n, Random rn, Arbol234 arbol) {
        int i = 0;
        ArrayList<Double> lista = new ArrayList<Double>();

        while (i < n) {
            long empezar, fin, duracion, media = 0;
            int valor = rn.nextInt(100000);

            arbol.insertar(valor);

            int j = 0;
            while (j < 1000) {
                empezar = System.nanoTime();
                arbol.buscar(valor);
                fin = System.nanoTime();
                duracion = (fin - empezar);
                media += duracion;

                j++;
            }

            media = media / 1000;

            i++;
            lista.add(media * 0.000001); // millisec
        }
        return lista;
    }

    public static void main(String[] args) {

        Arbol234 arbol = new Arbol234();
        
        /*
        // Para probar las gráficas de tiempo
        Random rn = new Random();
        ArrayList<Double> lista1 = tiempo_insertado(5000, rn, arbol);
        ArrayList<Double> lista2 = tiempo_insertado(5000, rn, arbol);
        ArrayList<Double> lista3 = tiempo_insertado(5000, rn, arbol);

        String str = "";

        for (int i = 0; i < 5000; i++) {
        str += (i + 1) + " " + ((lista1.get(i) + lista2.get(i) + lista3.get(i)) / 3) + "\n";
        }

        BufferedWriter writer;
        try {
             writer = new BufferedWriter(new FileWriter("insertado.dat"));
             writer.write(str);
             writer.close();
        } catch (IOException e) {
             e.printStackTrace();
        }
        
        for (int i = 1; i < 22; i++) {
          arbol.insertar(i);
        }
        */
        
        // Representación gráfica
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Representacion rp = new Representacion(arbol);
                rp.setVisible(true);
            }
        });

    }
}
