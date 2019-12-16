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
	
	
	//Insertado:
	
	public void insertar(int valor) //utilizamos el buscar para comprobar si existe el valor a insertar
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
			Nodo ndos1,ndos2,ndos3;
				
			ndos3 = new Nodo(_raiz.get_v1()); 
			ndos2 = new Nodo(_raiz.get_v3());
			ndos1 = new Nodo(_raiz.get_v2());
				
			_raiz = ndos1;
			_raiz.set_hi(ndos3);
			_raiz.set_hd(ndos2);
				
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
					
					}
					else if((hijodr.get_tipo() == TiposNodos.NODOCUATRO) && (i == hijodr))
					{
						inserta_valor(ultimo,hijodr.get_v2());
						//Diapositiva splitting a 4 node below a 2 node (Right)
					
						int valoraux = hijodr.get_v1();
						Nodo aux = new Nodo(valoraux);
					
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
						
					}
					else if((central.get_tipo() == TiposNodos.NODOCUATRO)  && (i == central))
					{
						
					}
					else if((hijodr.get_tipo() == TiposNodos.NODOCUATRO) && (i == hijodr))
					{
						
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
		
		inserta_valor(ultimo, valor);
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
