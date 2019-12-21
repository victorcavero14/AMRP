/*
 * Implementación en Java de un árbol 2-3-4
 * Incluye las operaciones de insertar, borrar y buscar una clave dada.
 * 
 * 
 * Desarrollado por: Víctor Manuel Cavero Gracia
 * 
 */

package maincode;
import nodos.TiposNodos;
import vista.Representacion;
import nodos.Nodo;

public class Arbol234 {
	
	private Nodo _raiz; 
	

	public Arbol234 () //Ojo solo implementado con Int!!
	{
		_raiz = null;
	}
	
	//Busqueda:
	
	public Nodo buscar(int valor) //Null si no lo encuentra y el nodo donde esta en caso de que si lo encuentre
	{
		Nodo buscar_i = _raiz;
		boolean existe = false;
		
		while(buscar_i != null && !existe)
		{
			if(buscar_i.get_v1() == valor) 
			{
				existe = true;
			}
			else if(buscar_i.get_tipo() == TiposNodos.NODODOS )
			{
				if(valor > buscar_i.get_v1()) buscar_i = buscar_i.get_hd();
				else buscar_i = buscar_i.get_hi();
			}
			else
			{
				if(buscar_i.get_v2() == valor) existe = true;
				
				else if(buscar_i.get_tipo() == TiposNodos.NODOTRES)
				{
					if(valor > buscar_i.get_v2()) buscar_i = buscar_i.get_hd();
					else if (valor < buscar_i.get_v1()) buscar_i = buscar_i.get_hi();
					else if ((valor > buscar_i.get_v1()) && (valor < buscar_i.get_v2())) buscar_i = buscar_i.get_ci(); 
				}
				else
				{
					if(buscar_i.get_v3() == valor) existe = true;
					else
					{
						if(valor > buscar_i.get_v3()) buscar_i = buscar_i.get_hd();
						else if (valor < buscar_i.get_v1()) buscar_i = buscar_i.get_hi();
						else if ((valor > buscar_i.get_v2()) && (valor < buscar_i.get_v3())) buscar_i = buscar_i.get_cd(); 
						else if ((valor > buscar_i.get_v1()) && (valor < buscar_i.get_v2())) buscar_i = buscar_i.get_ci(); 
					}
				}
			}
				
		}
		
		return buscar_i; 
	}
	
	//Insertar y borrar hacen uso de la busqueda
	//Borrado:
	
	public void borrar(int valor)
	{
		
	}
	
	
	/*
	 * INSERTAR:
	 * 
	 * He realizado el insertado en dos pasadas(top-down) por la rama donde debería ser insertado el elemento.
	 * 
	 * En la primera bajada por la rama correspondiente de la inserción comprobamos de que tipo es el último nodo
	 * donde va a ser insertado. Si es NODODOS o NODOTRES se inserta directamente, en caso de ser un NODOCUATRO sería
	 * nesaría otro pasada desde arriba hasta abajo de la rama descomponiendo todos los NODOCUATRO.
	 * 
	 * 
	 * 
	 */
	
	public void insertar(int valor)
	{
		
		Nodo buscar_i = _raiz;
		Nodo ultimo = _raiz;
		boolean existe = false;
		
		while(buscar_i != null && !existe) //Muy parecido al buscar pero en este caso nos quedamos con el ultimo nodo
		{
			ultimo = buscar_i;
			
			if(buscar_i.get_v1() == valor) 
			{
				existe = true;
			}
			else if(buscar_i.get_tipo() == TiposNodos.NODODOS )
			{
				if(valor > buscar_i.get_v1()) buscar_i = buscar_i.get_hd();
				else buscar_i = buscar_i.get_hi();
			}
			else
			{
				if(buscar_i.get_v2() == valor) existe = true;
				else if(buscar_i.get_tipo() == TiposNodos.NODOTRES)
				{
					if(valor > buscar_i.get_v2()) buscar_i = buscar_i.get_hd();
					else if (valor < buscar_i.get_v1()) buscar_i = buscar_i.get_hi();
					else if ((valor > buscar_i.get_v1()) && (valor < buscar_i.get_v2())) buscar_i = buscar_i.get_ci(); 
				}
				else
				{
					if(buscar_i.get_v3() == valor) existe = true;
					else
					{
						if(valor > buscar_i.get_v3()) buscar_i = buscar_i.get_hd();
						else if (valor < buscar_i.get_v1()) buscar_i = buscar_i.get_hi();
						else if ((valor > buscar_i.get_v2()) && (valor < buscar_i.get_v3())) buscar_i = buscar_i.get_cd(); 
						else if ((valor > buscar_i.get_v1()) && (valor < buscar_i.get_v2())) buscar_i = buscar_i.get_ci(); 
					}
				}
			}
		}
		
		if(!existe)
		{
			if(ultimo != null) //El ultimo solo es null cuando la raiz lo es
			{
				if(ultimo.get_tipo() != TiposNodos.NODOCUATRO) inserta_valor(ultimo, valor);
				else inserta_auxiliar(ultimo, valor);
			}
			else _raiz = new Nodo(valor);
		}
		
	}
	
	private void inserta_valor(Nodo i, int valor)
	{
		if(i.get_tipo() == TiposNodos.NODODOS)
		{
			i.set_tipo(TiposNodos.NODOTRES);
			if(valor > i.get_v1()) i.set_v2(valor);
			else
			{
				i.set_v2(i.get_v1());
				i.set_v1(valor);
			}
		}
		else if(i.get_tipo() == TiposNodos.NODOTRES)
		{
			i.set_tipo(TiposNodos.NODOCUATRO);
				
			if(valor > i.get_v2())
			{
				i.set_v3(valor);
			}
			else if (valor < i.get_v1()) 
			{
				i.set_v3(i.get_v2());
				i.set_v2(i.get_v1());
				i.set_v1(valor);
			}
			else 
			{
				i.set_v3(i.get_v2());
				i.set_v2(valor);
			}
		}
	}
	
	private void inserta_auxiliar(Nodo i, int valor)
	{
		if(_raiz.get_tipo() == TiposNodos.NODOCUATRO)
		{
			Nodo nizq, nder;
				
			nizq = new Nodo(_raiz.get_v1()); 
			nder = new Nodo(_raiz.get_v3());
			
			nizq.set_hi(_raiz.get_hi());
			nizq.set_hd(_raiz.get_ci());
			
			nder.set_hi(_raiz.get_cd());
			nder.set_hd(_raiz.get_hd());
			
			_raiz.set_v1(_raiz.get_v2());
			_raiz.set_tipo(TiposNodos.NODODOS);
			
			_raiz.set_hi(nizq);
			_raiz.set_hd(nder);
				
			insertar(valor);
			
		}
		else inserta_recursiva_auxiliar(valor);
	}
	
	private void inserta_recursiva_auxiliar(int valor) //Se cumple que la raiz es NodoDos o NodoTres y que es distinta de Null
	{
		Nodo ultimo = _raiz, i = _raiz;
		Nodo hijoiz = i.get_hi(), central = i.get_ci(), hijodr = i.get_hd();
		
		while(i != null)
		{
			ultimo = i;
			
			if(i.get_tipo() == TiposNodos.NODODOS)
			{
				if(valor > i.get_v1()) i = hijodr;
				else i = hijoiz;
				
				if(i != null)
				{
				
					if((hijoiz.get_tipo() == TiposNodos.NODOCUATRO) && i == hijoiz )
					{
						inserta_valor(ultimo,hijoiz.get_v2());
						
						int valoraux = hijoiz.get_v3();
						Nodo aux = new Nodo(valoraux); // se pone por defecto el tipo NODODOS
					
						hijoiz.set_tipo(TiposNodos.NODODOS);
						aux.set_hi(hijoiz.get_cd());
						aux.set_hd(hijoiz.get_hd());
						hijoiz.set_hd(hijoiz.get_ci());
		
						ultimo.set_ci(aux);
					}
					else if((hijodr.get_tipo() == TiposNodos.NODOCUATRO) && (i == hijodr))
					{
						inserta_valor(ultimo,hijodr.get_v2());
						//Diapositiva splitting a 4 node below a 2 node (Right)
					
						int valoraux = hijodr.get_v1();
						Nodo aux = new Nodo(valoraux); // se pone por defecto el tipo NODODOS
					
						hijodr.set_tipo(TiposNodos.NODODOS);
						hijodr.set_v1(hijodr.get_v3());
						aux.set_hi(hijodr.get_hi());
						aux.set_hd(hijodr.get_ci());
						hijodr.set_hi(hijodr.get_cd());
		
						ultimo.set_ci(aux);
					}
				}
			}
			else //obligatoriamente sera NODOTRES
			{
				if(valor > i.get_v2()) i = hijodr;
				else if (valor < i.get_v1()) i = hijoiz;
				else if ((valor > i.get_v1()) && (valor < i.get_v2())) i = central; 
				
				if(i != null)
				{
					if((hijoiz.get_tipo() == TiposNodos.NODOCUATRO) && (i == hijoiz))
					{
						inserta_valor(ultimo,hijoiz.get_v2());
						
						int valoraux = hijoiz.get_v3();
						Nodo aux = new Nodo(valoraux); // se pone por defecto el tipo NODODOS
					
						hijoiz.set_tipo(TiposNodos.NODODOS);
						aux.set_hi(hijoiz.get_cd());
						aux.set_hd(hijoiz.get_hd());
						hijoiz.set_hd(hijoiz.get_ci());
						
						ultimo.set_cd(ultimo.get_ci());
						ultimo.set_ci(aux);
					}
					else if((central.get_tipo() == TiposNodos.NODOCUATRO)  && (i == central))
					{
						inserta_valor(ultimo,central.get_v2());
						
						int valoraux = hijoiz.get_v3();
						Nodo aux = new Nodo(valoraux); // se pone por defecto el tipo NODODOS
					
						central.set_tipo(TiposNodos.NODODOS);
						aux.set_hi(central.get_cd());
						aux.set_hd(central.get_hd());
						central.set_hd(central.get_ci());
		
						ultimo.set_cd(aux);
					}
					else if((hijodr.get_tipo() == TiposNodos.NODOCUATRO) && (i == hijodr))
					{
						inserta_valor(ultimo,hijodr.get_v2());
						//Diapositiva splitting a 4 node below a 3 node (Right)
					
						int valoraux = hijodr.get_v1();
						Nodo aux = new Nodo(valoraux); // se pone por defecto el tipo NODODOS
					
						hijodr.set_tipo(TiposNodos.NODODOS);
						hijodr.set_v1(hijodr.get_v3());
						aux.set_hi(hijodr.get_hi());
						aux.set_hd(hijodr.get_ci());
						hijodr.set_hi(hijodr.get_cd());
		
						ultimo.set_cd(aux);
					}
				}
			}
			
			if(i != null)
			{
				hijoiz = i.get_hi();
				central = i.get_ci();
				hijodr = i.get_hd();
			}
		}
		
		inserta_valor(ultimo, valor); //insertamos el valor en el nodo final donde deberia estar una
		// vez deshechos los nodos-4
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
