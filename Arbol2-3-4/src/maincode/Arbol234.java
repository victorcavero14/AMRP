/*
 * Implementación en Java de un árbol 2-3-4
 * Incluye las operaciones de insertar, borrar y buscar una clave dada.
 * 
 * 
 * Desarrollado por: Víctor Manuel Cavero Gracia
 * 
 */

package maincode;
import nodos.NodoDos;
import nodos.NodoTres;
import nodos.TiposNodos;

import java.util.Scanner;

import nodos.Nodo;
import nodos.NodoCuatro;

public class Arbol234 {
	
	public Nodo _raiz; 
	
	public Arbol234 ()
	{
		_raiz = null;
	}
	
	//Busqueda:
	
	public Nodo buscar(int valor)
	{
		return null;
	}
	
	//Insertar y borrar hacen uso de la busqueda
	//Borrado:
	
	public void borrar(int valor)
	{
		
	}
	
	
	//Insertado:
	
	public void insertar(int valor)
	{
		if(_raiz == null)
		{
			_raiz = new NodoDos(valor);
		}
		else
		{
			if(_raiz.get_tipo() == TiposNodos.NODODOS)
			{
				_raiz = new NodoTres(_raiz.get_v1(), valor);
			}
			else if(_raiz.get_tipo() == TiposNodos.NODOTRES)
			{
				_raiz = new NodoCuatro(_raiz.get_v1(), _raiz.get_v2(), valor);
			}
		}
		
		
	}
	
	
	@Override
	public String toString() {
		return "Arbol234 " + _raiz.toString();
	}

	public static void main(String[] args) {
		
		Arbol234 arbol = new Arbol234();

		while(true)
		{
		String entrada = "";
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Valor a insertar: ");
		entrada = sc.nextLine();
		
		arbol.insertar(Integer.parseInt(entrada)); //try catch numberFormatException
		
		System.out.println(arbol.toString());
		
		//instaceof para tomar decisiones en los metodos.
		//if (nt instanceof NodoTres) System.out.println(nt.toString());
		}
		
	}

}
