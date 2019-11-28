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
import nodos.NodoCuatro;

public class Arbol234 {
	
	public NodoDos _raiz; //mal! necesito crear un NODO grande que englobe las otras 3 clases.
							//porque si no no podria acceder a una variable nodotres en este caso a su seg valor.
	public int _tam; //no necesario ??
	
	public Arbol234 ()
	{
		_raiz = null;
		_tam = 0;
	}
	
	//Busqueda:
	
	public buscar(int valor)
	{
		
	}
	
	//Insertar y borrar hacen uso de la busqueda
	//Borrado:
	
	public void borrar(int valor)
	{
		
	}
	
	
	//Insertado:
	
	public void insertar(int valor)
	{
		
	}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		NodoTres nt = new NodoTres(1,2);
		NodoDos nd = nt;
		
		System.out.println(nd.get_valor2());
		//instaceof para tomar decisiones en los metodos.
		//if (nt instanceof NodoTres) System.out.println(nt.toString());
		
	}

}
