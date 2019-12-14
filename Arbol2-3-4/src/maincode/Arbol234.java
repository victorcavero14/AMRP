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
import vista.Representacion;

import nodos.Nodo;
import nodos.NodoCuatro;

public class Arbol234 {
	
	private Nodo _raiz; 
	

	public Arbol234 () //Ojo solo implementado con Int!!
	{
		_raiz = null;
	}
	
	//Busqueda:
	
	public Nodo buscar(int valor) //Null si no lo encuentra
	{
		Nodo buscar_i = _raiz;
		Nodo encontrado = null;
		
		while(buscar_i != null && encontrado != buscar_i)
		{
			if(buscar_i.get_tipo() == TiposNodos.NODODOS)
			{
				if(buscar_i.get_v1() == valor) encontrado = buscar_i;
				else
				{
					if(valor > buscar_i.get_v1()) buscar_i = buscar_i.get_hd();
					else buscar_i = buscar_i.get_hi();
				}
			}
			else if(buscar_i.get_tipo() == TiposNodos.NODOTRES)
			{
				if(buscar_i.get_v1() == valor) encontrado = buscar_i;
				else if (buscar_i.get_v2() == valor) encontrado = buscar_i;
				else
				{
					if(valor > buscar_i.get_v2()) buscar_i = buscar_i.get_hd();
					else if (valor < buscar_i.get_v1()) buscar_i = buscar_i.get_hi();
					else if ((valor > buscar_i.get_v1()) && (valor < buscar_i.get_v2())) buscar_i = buscar_i.get_ci(); 
				}
			}
			else if(buscar_i.get_tipo() == TiposNodos.NODOCUATRO)
			{
				if(buscar_i.get_v1() == valor) encontrado = buscar_i;
				else if (buscar_i.get_v2() == valor) encontrado = buscar_i;
				else if (buscar_i.get_v3() == valor) encontrado = buscar_i;
				else
				{
					if(valor > buscar_i.get_v3()) buscar_i = buscar_i.get_hd();
					else if (valor < buscar_i.get_v1()) buscar_i = buscar_i.get_hi();
					else if ((valor > buscar_i.get_v2()) && (valor < buscar_i.get_v3())) buscar_i = buscar_i.get_cd(); 
					else if ((valor > buscar_i.get_v1()) && (valor < buscar_i.get_v2())) buscar_i = buscar_i.get_ci(); 

				}
			}
		}
		
		return encontrado; 
	}
	
	//Insertar y borrar hacen uso de la busqueda
	//Borrado:
	
	public void borrar(int valor)
	{
		
	}
	
	
	//Insertado:
	
	public void insertar(int valor) //utilizamos el buscar para comprobar si existe el valor a insertar
	{
		if(buscar(valor) == null) //El valor a insertar no se encuentra en el arbol
		{
			if(_raiz == null)
			{
				_raiz = new NodoDos(valor);
			}
			else
			{
				if(_raiz.get_tipo() == TiposNodos.NODODOS)
				{
					if(valor > _raiz.get_v1()) _raiz = new NodoTres(_raiz.get_v1(), valor);
					else  _raiz = new NodoTres(valor, _raiz.get_v1());
				}
				else if(_raiz.get_tipo() == TiposNodos.NODOTRES)
				{
					if(valor > _raiz.get_v2()) _raiz = new NodoCuatro(_raiz.get_v1(), _raiz.get_v2(), valor);
					else if (valor < _raiz.get_v1()) _raiz = new NodoCuatro(valor, _raiz.get_v1(), _raiz.get_v2());
					else _raiz = new NodoCuatro(_raiz.get_v1(), valor, _raiz.get_v2());
				}
			}
		}
	
	}
	
	public Nodo get_raiz() {
		return _raiz;
	}

	public void set_raiz(Nodo _raiz) {
		this._raiz = _raiz;
	}

	public static void main(String[] args) {
		
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            	Arbol234 arbol = new Arbol234();
        		
        		Representacion rp = new Representacion(arbol);
        		
        		rp.setVisible(true);
            }
        });
		
		
	}

}
