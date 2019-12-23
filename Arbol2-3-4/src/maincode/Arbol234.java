/*
 * Implementación en Java de un árbol 2-3-4
 * Incluye las operaciones de insertar, borrar y buscar una clave dada.
 * 
 * Invariente: Todas las ramas tienen la misma altura.
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
	
	/* BUSQUEDA:
	 * 
	 * Algoritmo de búsqueda que recorre una de las ramas del árbol,
	 * decidiendo según los valores de los nodos el camino a seguir.
	 * 
	 * Al finalizar la busqueda devuelve: 
	 * 
	 * "Null" en caso de no haber encontrado el valor en el árbol,
	 * o el nodo donde se encuentra el valor.
	 * 	
	 */
	
	public Nodo buscar(int valor)
	{
		Nodo buscar_i = _raiz;
		boolean existe = false;
		
		while(buscar_i != null && !existe)
		{
			existe = buscar_i.existe_valor(valor);
			
			if(!existe)
			{
				if(buscar_i.get_tipo() == TiposNodos.NODODOS )
				{
					if(valor > buscar_i.get_v1()) buscar_i = buscar_i.get_hd();
					else buscar_i = buscar_i.get_hi();
				}
				else
				{
					if(buscar_i.get_tipo() == TiposNodos.NODOTRES)
					{
						if(valor > buscar_i.get_v2()) buscar_i = buscar_i.get_hd();
						else if (valor < buscar_i.get_v1()) buscar_i = buscar_i.get_hi();
						else buscar_i = buscar_i.get_ci(); 
					}
					else
					{
						if(valor > buscar_i.get_v3()) buscar_i = buscar_i.get_hd();
						else if (valor < buscar_i.get_v1()) buscar_i = buscar_i.get_hi();
						else if ((valor > buscar_i.get_v2()) && (valor < buscar_i.get_v3())) buscar_i = buscar_i.get_cd(); 
						else buscar_i = buscar_i.get_ci(); 
					}	
				}
			}
		}
		
		return buscar_i; 
	}
	
	//Borrado:
	
	public void borrar(int valor)
	{
		if(_raiz != null)
		{
			Nodo busqueda = buscar(valor);
			
			if(busqueda != null)
			{
				boolean es_hoja = busqueda.es_hoja();
				
				if(es_hoja)
				{
					if((busqueda.get_tipo() == TiposNodos.NODODOS) && busqueda == _raiz) _raiz = null;
					else if(busqueda.get_tipo() == TiposNodos.NODOTRES) //Nodo donde se encuentra es tres
						borra_valor_en_nodo3(busqueda,valor);
					else if (busqueda.get_tipo() == TiposNodos.NODOCUATRO) //Nodo donde se encuentra es cuatro
						borra_valor_en_nodo4(busqueda, valor);
					else borrar_aux_recursivo(_raiz,valor);
				}
				else //no es hoja tenemos que reajustar el árbol
				{
					borrar_aux_recursivo(_raiz,valor);
				}
			}
		}
	}
	
	private void borrar_aux_recursivo(Nodo i, int valor)
	{
	    if(i != null)
	    {
			if(i.es_hoja() && i.existe_valor(valor))
			{
				if(i.get_tipo() == TiposNodos.NODOTRES) //Nodo donde se encuentra es tres
					borra_valor_en_nodo3(i,valor);
				else if (i.get_tipo() == TiposNodos.NODOCUATRO) //Nodo donde se encuentra es cuatro
					borra_valor_en_nodo4(i, valor);
			}
			else if(!i.es_hoja())
			{
	            Nodo siguiente = null, hermano = null, hermano2 = null;
	            //calculas el siguiente

	            if(!i.existe_valor(valor))
	            {
	                if(i.get_tipo() == TiposNodos.NODODOS)
	                {
	                    if(valor > i.get_v1()) siguiente = i.get_hd();
	                    else siguiente = i.get_hi();
	                                                                // i no es hoja
	                    if(siguiente.get_tipo() == TiposNodos.NODODOS) //i no tiene el valor y el siguiente es nododos
	                    {
	                        if(siguiente == i.get_hd())
	                        {
	                        	hermano = i.get_hi();
	                        	
	                            if(hermano.get_tipo() == TiposNodos.NODODOS)
	                            {
	                                i.set_tipo(TiposNodos.NODOCUATRO);
									i.set_v2(i.get_v1());
									i.set_v3(siguiente.get_v1());
									i.set_v1(hermano.get_v1());
									
									i.set_hd(siguiente.get_hd());
									i.set_cd(siguiente.get_hi());
									i.set_ci(hermano.get_hd());
									i.set_hi(hermano.get_hi());
									
									siguiente = i;
	                            }
	                            else if(hermano.get_tipo() == TiposNodos.NODOTRES)
	                            {
	                                siguiente.set_tipo(TiposNodos.NODOTRES);
	                                siguiente.set_v2(siguiente.get_v1());
	                                siguiente.set_v1(i.get_v1());
	                                
	                                i.set_v1(hermano.get_v2());

	                                siguiente.set_ci(siguiente.get_hi());
	                                siguiente.set_hi(borra_valor_en_nodo3(hermano, hermano.get_v2()));

	                            }
	                            else
	                            {
	                                siguiente.set_tipo(TiposNodos.NODOTRES);
	                                siguiente.set_v2(siguiente.get_v1());
	                                siguiente.set_v1(i.get_v1());
	                                
	                                i.set_v1(hermano.get_v3());

	                                siguiente.set_ci(siguiente.get_hi());
	                                siguiente.set_hi(borra_valor_en_nodo4(hermano, hermano.get_v3()));

	                            }
	                        }
	                        else if(siguiente == i.get_hi())
	                        {
	                        	hermano = i.get_hd();
	                        	
	                            if(hermano.get_tipo() == TiposNodos.NODODOS)
	                            {
	                                i.set_tipo(TiposNodos.NODOCUATRO);
									i.set_v2(i.get_v1());
									i.set_v1(siguiente.get_v1());
									i.set_v3(hermano.get_v1());
									
									i.set_hd(hermano.get_hd());
									i.set_cd(hermano.get_hi());
									i.set_ci(siguiente.get_hd());
									i.set_hi(siguiente.get_hi());
									
									siguiente = i;
	                            }
	                            else if(hermano.get_tipo() == TiposNodos.NODOTRES)
	                            {
	                                siguiente.set_tipo(TiposNodos.NODOTRES);
	                                siguiente.set_v2(i.get_v1());
	                                
	                                i.set_v1(hermano.get_v1());

	                                siguiente.set_ci(siguiente.get_hd());
	                                siguiente.set_hd(borra_valor_en_nodo3(hermano, hermano.get_v1()));

	                            }
	                            else
	                            {
	                                siguiente.set_tipo(TiposNodos.NODOTRES);
	                                siguiente.set_v2(i.get_v1());
	                                
	                                i.set_v1(hermano.get_v1());

	                                siguiente.set_ci(siguiente.get_hd());
	                                siguiente.set_hd(borra_valor_en_nodo4(hermano, hermano.get_v1()));

	                            }
	                        }
	                    }
	                }
	                else if(i.get_tipo() == TiposNodos.NODOTRES)
	                {
	                    if(valor > i.get_v2()) siguiente = i.get_hd();
	                    else if (valor < i.get_v1()) siguiente = i.get_hi();
	                    else siguiente = i.get_ci();
	                    
	                    if(siguiente.get_tipo() == TiposNodos.NODODOS) //i no tiene el valor y el siguiente es nododos
	                    {
	                        //miramos cual es el siguiente escogido

	                        if(siguiente == i.get_hi())
	                        {
	                            hermano = i.get_ci();

	                            if(hermano.get_tipo() == TiposNodos.NODODOS)
	                            {
	                                siguiente.set_tipo(TiposNodos.NODOCUATRO);
	                                siguiente.set_v2(i.get_v1());
	                                siguiente.set_v3(hermano.get_v1());

	                                i.set_tipo(TiposNodos.NODODOS);
	                                i.set_v1(i.get_v2());
	                                
	                                siguiente.set_ci(siguiente.get_hd());
	                                siguiente.set_cd(hermano.get_hi());
	                                siguiente.set_hd(hermano.get_hd());

	                                i.set_ci(null);

	                            }
	                            else if(hermano.get_tipo() == TiposNodos.NODOTRES)
	                            {
	                                siguiente.set_tipo(TiposNodos.NODOTRES);
	                                siguiente.set_v2(i.get_v1());

	                                i.set_v1(hermano.get_v1());

	                                hermano.set_tipo(TiposNodos.NODODOS);
	                                hermano.set_v1(hermano.get_v2());
	                                
	                                siguiente.set_ci(siguiente.get_hd());
	                                siguiente.set_hd(hermano.get_hi());

	                                hermano.set_hi(hermano.get_ci());
	                                hermano.set_ci(null);

	                            }
	                            else
	                            {
	                                siguiente.set_tipo(TiposNodos.NODOTRES);
	                                siguiente.set_v2(i.get_v1());

	                                i.set_v1(hermano.get_v1());

	                                hermano.set_tipo(TiposNodos.NODOTRES);
	                                hermano.set_v1(hermano.get_v2());
	                                hermano.set_v2(hermano.get_v3());
	                                
	                                siguiente.set_ci(siguiente.get_hd());
	                                siguiente.set_hd(hermano.get_hi());

	                                hermano.set_hi(hermano.get_ci());
	                                hermano.set_ci(hermano.get_cd());
	                                hermano.set_cd(null);

	                            }
	                        }
	                        else if(siguiente == i.get_ci())
	                        {
	                            hermano = i.get_hi();
	                            hermano2 = i.get_hd();

	                            if(hermano.get_tipo() == TiposNodos.NODODOS && hermano2.get_tipo() == TiposNodos.NODODOS) //rep
	                            {
	                                hermano.set_tipo(TiposNodos.NODOCUATRO);
	                                hermano.set_v2(i.get_v1());
	                                hermano.set_v3(siguiente.get_v1());

	                                i.set_tipo(TiposNodos.NODODOS);
	                                i.set_v1(i.get_v2());
	                                
	                                hermano.set_ci(hermano.get_hd());
	                                hermano.set_cd(siguiente.get_hi());
	                                hermano.set_hd(siguiente.get_hd());

	                                i.set_ci(null);

	                                siguiente = hermano;
	                            }
	                            else if(hermano.get_tipo() == TiposNodos.NODOTRES) //rep
	                            {
	                                siguiente.set_tipo(TiposNodos.NODOTRES);
	                                siguiente.set_v2(siguiente.get_v1());
	                                siguiente.set_v1(i.get_v1());

	                                i.set_v1(hermano.get_v2());

	                                hermano.set_tipo(TiposNodos.NODODOS);
	                                
	                                siguiente.set_ci(siguiente.get_hi());
	                                siguiente.set_hi(hermano.get_hd());

	                                hermano.set_hd(hermano.get_ci());
	                                hermano.set_ci(null);
	                                
	                            }
	                            else if(hermano.get_tipo() == TiposNodos.NODOCUATRO)
	                            {
	                                siguiente.set_tipo(TiposNodos.NODOTRES);
	                                siguiente.set_v2(siguiente.get_v1());
	                                siguiente.set_v1(i.get_v1());

	                                i.set_v1(hermano.get_v3());

	                                hermano.set_tipo(TiposNodos.NODOTRES);
	                                
	                                siguiente.set_ci(siguiente.get_hi());
	                                siguiente.set_hi(hermano.get_hd());

	                                hermano.set_hd(hermano.get_cd());
	                                hermano.set_cd(null);

	                            }
	                            else if(hermano.get_tipo() == TiposNodos.NODODOS && hermano2.get_tipo() == TiposNodos.NODOTRES)
	                            {
	                                siguiente.set_tipo(TiposNodos.NODOTRES);
	                                siguiente.set_v2(i.get_v2());

	                                i.set_v2(hermano2.get_v1());

	                                hermano2.set_tipo(TiposNodos.NODODOS);
	                                hermano2.set_v1(hermano2.get_v2());
	                                
	                                siguiente.set_ci(siguiente.get_hd());
	                                siguiente.set_hd(hermano2.get_hi());

	                                hermano2.set_hi(hermano2.get_ci());
	                                hermano2.set_ci(null);

	                            }
	                            else if(hermano.get_tipo() == TiposNodos.NODODOS && hermano2.get_tipo() == TiposNodos.NODOCUATRO)
	                            {
	                                siguiente.set_tipo(TiposNodos.NODOTRES);
	                                siguiente.set_v2(i.get_v2());

	                                i.set_v2(hermano.get_v1());

	                                hermano2.set_tipo(TiposNodos.NODOTRES);
	                                hermano2.set_v1(hermano2.get_v2());
	                                hermano2.set_v2(hermano2.get_v3());
	                                
	                                siguiente.set_ci(siguiente.get_hd());
	                                siguiente.set_hd(hermano2.get_hi());

	                                hermano2.set_hi(hermano2.get_ci());
	                                hermano2.set_ci(hermano2.get_cd());
	                                hermano2.set_cd(null);

	                            }
	                        }
	                        else //siguiente es hd
	                        {
	                            hermano = i.get_ci();

	                            if(hermano.get_tipo() == TiposNodos.NODODOS)
	                            {
	                                siguiente.set_tipo(TiposNodos.NODOCUATRO);
	                                siguiente.set_v3(siguiente.get_v1());
	                                siguiente.set_v2(i.get_v2());
	                                siguiente.set_v1(hermano.get_v1());

	                                i.set_tipo(TiposNodos.NODODOS);
	                                
	                                siguiente.set_cd(siguiente.get_hi());
	                                siguiente.set_ci(hermano.get_hd());
	                                siguiente.set_hi(hermano.get_hi());

	                                i.set_ci(null);

	                            }
	                            else if(hermano.get_tipo() == TiposNodos.NODOTRES)
	                            {
	                                siguiente.set_tipo(TiposNodos.NODOTRES);
	                                siguiente.set_v2(siguiente.get_v1());
	                                siguiente.set_v1(i.get_v2());

	                                i.set_v2(hermano.get_v2());

	                                hermano.set_tipo(TiposNodos.NODODOS);
	                                
	                                siguiente.set_ci(siguiente.get_hi());
	                                siguiente.set_hi(hermano.get_hd());

	                                hermano.set_hd(hermano.get_ci());
	                                hermano.set_ci(null);

	                            }
	                            else
	                            {
	                                siguiente.set_tipo(TiposNodos.NODOTRES);
	                                siguiente.set_v2(siguiente.get_v1());
	                                siguiente.set_v1(i.get_v2());

	                                i.set_v2(hermano.get_v3());

	                                hermano.set_tipo(TiposNodos.NODOTRES);
	                                
	                                siguiente.set_ci(siguiente.get_hi());
	                                siguiente.set_hi(hermano.get_hd());

	                                hermano.set_hd(hermano.get_cd());
	                                hermano.set_cd(null);

	                            }
	                        }
	                    }
	                }
	                else
	                {
	                    if(valor > i.get_v3()) siguiente = i.get_hd();
	                    else if (valor < i.get_v1()) siguiente = i.get_hi();
	                    else if ((valor > i.get_v2()) && (valor < i.get_v3())) siguiente = i.get_cd(); 
	                    else siguiente = i.get_ci(); 
	                    
	                    if(siguiente.get_tipo() == TiposNodos.NODODOS) //i no tiene el valor y el siguiente es nododos
	                    {
	                        //mirar cual es el siguiente escogido

	                        if(siguiente == i.get_hi())
	                        {
	                            hermano = i.get_ci();

	                            if(hermano.get_tipo() == TiposNodos.NODODOS)
	                            {
	                                siguiente.set_tipo(TiposNodos.NODOCUATRO);
	                                siguiente.set_v2(i.get_v1());
	                                siguiente.set_v3(hermano.get_v1());

	                                i.set_tipo(TiposNodos.NODOTRES);
	                                i.set_v1(i.get_v2());
	                                i.set_v2(i.get_v3());
	                                
	                                siguiente.set_ci(siguiente.get_hd());
	                                siguiente.set_cd(hermano.get_hi());
	                                siguiente.set_hd(hermano.get_hd());

	                                i.set_ci(i.get_cd());
	                                i.set_cd(null);

	                            }
	                            else if(hermano.get_tipo() == TiposNodos.NODOTRES)
	                            {
	                                siguiente.set_tipo(TiposNodos.NODOTRES);
	                                siguiente.set_v2(i.get_v1());

	                                i.set_v1(hermano.get_v1());

	                                hermano.set_tipo(TiposNodos.NODODOS);
	                                hermano.set_v1(hermano.get_v2());
	                                
	                                siguiente.set_ci(siguiente.get_hd());
	                                siguiente.set_hd(hermano.get_hi());

	                                hermano.set_hi(hermano.get_ci());
	                                hermano.set_ci(null);

	                            }
	                            else
	                            {
	                                siguiente.set_tipo(TiposNodos.NODOTRES);
	                                siguiente.set_v2(i.get_v1());

	                                i.set_v1(hermano.get_v1());

	                                hermano.set_tipo(TiposNodos.NODOTRES);
	                                hermano.set_v1(hermano.get_v2());
	                                hermano.set_v2(hermano.get_v3());
	                                
	                                siguiente.set_ci(siguiente.get_hd());
	                                siguiente.set_hd(hermano.get_hi());

	                                hermano.set_hi(hermano.get_ci());
	                                hermano.set_ci(hermano.get_cd());
	                                hermano.set_cd(null);

	                            }
	                        }
	                        else if(siguiente == i.get_ci())
	                        {
	                            hermano = i.get_hi();
	                            hermano2 = i.get_cd();

	                            if(hermano.get_tipo() == TiposNodos.NODODOS && hermano2.get_tipo() == TiposNodos.NODODOS)
	                            {
	                                hermano.set_tipo(TiposNodos.NODOCUATRO);
	                                hermano.set_v2(i.get_v1());
	                                hermano.set_v3(siguiente.get_v1());

	                                i.set_tipo(TiposNodos.NODOTRES);
	                                i.set_v1(i.get_v2());
	                                i.set_v2(i.get_v3());
	                                
	                                hermano.set_ci(hermano.get_hd());
	                                hermano.set_cd(siguiente.get_hi());
	                                hermano.set_hd(siguiente.get_hd());

	                                i.set_ci(i.get_cd());
	                                i.set_cd(null);

	                                siguiente = hermano;
	                            }
	                            else if(hermano.get_tipo() == TiposNodos.NODOTRES)
	                            {
	                                siguiente.set_tipo(TiposNodos.NODOTRES);
	                                siguiente.set_v2(siguiente.get_v1());
	                                siguiente.set_v1(i.get_v1());

	                                i.set_v1(hermano.get_v2());

	                                hermano.set_tipo(TiposNodos.NODODOS);
	                                
	                                siguiente.set_ci(siguiente.get_hi());
	                                siguiente.set_hi(hermano.get_hd());

	                                hermano.set_hd(hermano.get_ci());
	                                hermano.set_ci(null);

	                            }
	                            else if(hermano.get_tipo() == TiposNodos.NODOCUATRO)
	                            {
	                                siguiente.set_tipo(TiposNodos.NODOTRES);
	                                siguiente.set_v2(siguiente.get_v1());
	                                siguiente.set_v1(i.get_v1());

	                                i.set_v1(hermano.get_v3());

	                                hermano.set_tipo(TiposNodos.NODOTRES);
	                                
	                                siguiente.set_ci(siguiente.get_hi());
	                                siguiente.set_hi(hermano.get_hd());

	                                hermano.set_hd(hermano.get_cd());
	                                hermano.set_cd(null);

	                            }
	                            else if(hermano.get_tipo() == TiposNodos.NODODOS && hermano2.get_tipo() == TiposNodos.NODOTRES)
	                            {
	                                siguiente.set_tipo(TiposNodos.NODOTRES);
	                                siguiente.set_v2(i.get_v2());

	                                i.set_v2(hermano2.get_v1());

	                                hermano2.set_tipo(TiposNodos.NODODOS);
	                                hermano2.set_v1(hermano2.get_v2());
	                                
	                                siguiente.set_ci(siguiente.get_hd());
	                                siguiente.set_hd(hermano2.get_hi());

	                                hermano2.set_hi(hermano2.get_ci());
	                                hermano2.set_ci(null);

	                            }
	                            else if(hermano.get_tipo() == TiposNodos.NODODOS && hermano2.get_tipo() == TiposNodos.NODOCUATRO)
	                            {
	                                siguiente.set_tipo(TiposNodos.NODOTRES);
	                                siguiente.set_v2(i.get_v2());

	                                i.set_v2(hermano2.get_v1());

	                                hermano2.set_tipo(TiposNodos.NODOTRES);
	                                hermano2.set_v1(hermano2.get_v2());
	                                hermano2.set_v2(hermano2.get_v3());
	                                
	                                siguiente.set_ci(siguiente.get_hd());
	                                siguiente.set_hd(hermano2.get_hi());

	                                hermano2.set_hi(hermano2.get_ci());
	                                hermano2.set_ci(hermano2.get_cd());
	                                hermano2.set_cd(null);

	                            }
	                        }
	                        else if(siguiente == i.get_cd())
	                        {
	                            hermano = i.get_ci();
	                            hermano2 = i.get_hd();

	                            if(hermano.get_tipo() == TiposNodos.NODODOS && hermano2.get_tipo() == TiposNodos.NODODOS)
	                            {
	                                hermano.set_tipo(TiposNodos.NODOCUATRO);
	                                hermano.set_v2(i.get_v2());
	                                hermano.set_v3(siguiente.get_v1());

	                                i.set_tipo(TiposNodos.NODOTRES);
	                                i.set_v2(i.get_v3());
	                                
	                                hermano.set_ci(hermano.get_hd());
	                                hermano.set_cd(siguiente.get_hi());
	                                hermano.set_hd(siguiente.get_hd());

	                                i.set_cd(null);

	                                siguiente = hermano;
	                            }
	                            else if(hermano.get_tipo() == TiposNodos.NODOTRES)
	                            {
	                                siguiente.set_tipo(TiposNodos.NODOTRES);
	                                siguiente.set_v2(siguiente.get_v1());
	                                siguiente.set_v1(i.get_v2());

	                                i.set_v2(hermano.get_v2());

	                                hermano.set_tipo(TiposNodos.NODODOS);
	                                
	                                siguiente.set_ci(siguiente.get_hi());
	                                siguiente.set_hi(hermano.get_hd());

	                                hermano.set_hd(hermano.get_ci());
	                                hermano.set_ci(null);

	                            }
	                            else if(hermano.get_tipo() == TiposNodos.NODOCUATRO)
	                            {
	                                siguiente.set_tipo(TiposNodos.NODOTRES);
	                                siguiente.set_v2(siguiente.get_v1());
	                                siguiente.set_v1(i.get_v2());

	                                i.set_v2(hermano.get_v3());

	                                hermano.set_tipo(TiposNodos.NODOTRES);
	                                
	                                siguiente.set_ci(siguiente.get_hi());
	                                siguiente.set_hi(hermano.get_hd());

	                                hermano.set_hd(hermano.get_cd());
	                                hermano.set_cd(null);

	                            }
	                            else if(hermano.get_tipo() == TiposNodos.NODODOS && hermano2.get_tipo() == TiposNodos.NODOTRES)
	                            {
	                                siguiente.set_tipo(TiposNodos.NODOTRES);
	                                siguiente.set_v2(i.get_v3());

	                                i.set_v3(hermano2.get_v1());

	                                hermano2.set_tipo(TiposNodos.NODODOS);
	                                hermano2.set_v1(hermano2.get_v2());
	                                
	                                siguiente.set_ci(siguiente.get_hd());
	                                siguiente.set_hd(hermano2.get_hi());

	                                hermano2.set_hi(hermano2.get_ci());
	                                hermano2.set_ci(null);

	                            }
	                            else if(hermano.get_tipo() == TiposNodos.NODODOS && hermano2.get_tipo() == TiposNodos.NODOCUATRO)
	                            {
	                                siguiente.set_tipo(TiposNodos.NODOTRES);
	                                siguiente.set_v2(i.get_v3());

	                                i.set_v3(hermano2.get_v1());

	                                hermano2.set_tipo(TiposNodos.NODOTRES);
	                                hermano2.set_v1(hermano2.get_v2());
	                                hermano2.set_v2(hermano2.get_v3());
	                                
	                                siguiente.set_ci(siguiente.get_hd());
	                                siguiente.set_hd(hermano2.get_hi());

	                                hermano2.set_hi(hermano2.get_ci());
	                                hermano2.set_ci(hermano2.get_cd());
	                                hermano2.set_cd(null);

	                            }
	                        }
	                        else //siguiente es el hijo derecho
	                        {
	                            hermano = i.get_cd();

	                            if(hermano.get_tipo() == TiposNodos.NODODOS)
	                            {
	                                siguiente.set_tipo(TiposNodos.NODOCUATRO);
	                                siguiente.set_v3(siguiente.get_v1());
	                                siguiente.set_v2(i.get_v2());
	                                siguiente.set_v1(hermano.get_v1());

	                                i.set_tipo(TiposNodos.NODOTRES);
	                                
	                                siguiente.set_cd(siguiente.get_hi());
	                                siguiente.set_ci(hermano.get_hd());
	                                siguiente.set_hi(hermano.get_hi());

	                                i.set_cd(null);

	                            }
	                            else if(hermano.get_tipo() == TiposNodos.NODOTRES)
	                            {
	                                siguiente.set_tipo(TiposNodos.NODOTRES);
	                                siguiente.set_v2(siguiente.get_v1());
	                                siguiente.set_v1(i.get_v2());

	                                i.set_v2(hermano.get_v2());

	                                hermano.set_tipo(TiposNodos.NODODOS);
	                                
	                                siguiente.set_ci(siguiente.get_hi());
	                                siguiente.set_hi(hermano.get_hd());

	                                hermano.set_hd(hermano.get_ci());
	                                hermano.set_ci(null);

	                            }
	                            else
	                            {
	                                siguiente.set_tipo(TiposNodos.NODOTRES);
	                                siguiente.set_v2(siguiente.get_v1());
	                                siguiente.set_v1(i.get_v2());

	                                i.set_v2(hermano.get_v3());

	                                hermano.set_tipo(TiposNodos.NODOTRES);
	                                
	                                siguiente.set_ci(siguiente.get_hi());
	                                siguiente.set_hi(hermano.get_hd());

	                                hermano.set_hd(hermano.get_cd());
	                                hermano.set_cd(null);

	                            }
	                        }
	                    }
	                }
	                borrar_aux_recursivo(siguiente,valor);
	            }
	            else //existe valor en nodo y no es hoja. COMO EL 2 ya programado
	            {
	                int pos = 1;
	                Nodo hijoizq = i.get_hi(), hijoderc = i.get_hd();
	                
	                if(i.get_v1() == valor)
	                {
	                   if((i.get_tipo() == TiposNodos.NODOTRES) || (i.get_tipo() == TiposNodos.NODOCUATRO)) //Nodo donde se encuentra es tres
	                           hijoderc = i.get_ci();
	                }
	                else if(i.get_v2() == valor)
	                {
	                    pos=2;
	                    if(i.get_tipo() == TiposNodos.NODOTRES)	hijoizq = i.get_ci();
	                    else if (i.get_tipo() == TiposNodos.NODOCUATRO) 
	                    {
	                        hijoizq = i.get_ci();
	                        hijoderc = i.get_cd();
	                    }
	                }
	                else
	                {
	                    pos=3;
	                    hijoizq = i.get_cd();
	                }
	                
	                //2.1
	                if(hijoizq.get_tipo() == TiposNodos.NODOTRES)
	                {
	                    sustituye(i, pos, hijoizq.get_v2());
	                    borrar_aux_recursivo(hijoizq, hijoizq.get_v2());
	                }
	                else if (hijoizq.get_tipo() == TiposNodos.NODOCUATRO)
	                {
	                   sustituye(i, pos, hijoizq.get_v3());
	                   borrar_aux_recursivo(hijoizq, hijoizq.get_v3());
	                }

	                //2.2
	                if((hijoderc.get_tipo() == TiposNodos.NODOTRES) || (hijoderc.get_tipo() == TiposNodos.NODOCUATRO))
	                {
	                   sustituye(i, pos, hijoderc.get_v1());
	                   borrar_aux_recursivo(hijoderc, hijoderc.get_v1());
	                }
	                //2.3 RODEADO POR NODOSDOS
	                if((hijoizq.get_tipo() == TiposNodos.NODODOS) && (hijoderc.get_tipo() == TiposNodos.NODODOS))
	                {
	                    if(i.get_tipo() == TiposNodos.NODODOS)
	                    {
	                        i.set_tipo(TiposNodos.NODOCUATRO);
	                        i.set_v2(i.get_v1());
	                        i.set_v1(hijoizq.get_v1());
	                        i.set_v3(hijoderc.get_v1());

	                        i.set_hi(hijoizq.get_hi());
	                        i.set_ci(hijoizq.get_hd());
	                        i.set_cd(hijoderc.get_hi());
	                        i.set_hd(hijoderc.get_hd());

	                        borrar_aux_recursivo(i, valor);
	                    }
	                    else if(i.get_tipo() == TiposNodos.NODOTRES)
	                    {
	                        if(pos == 1)
	                        {
	                            hijoizq.set_tipo(TiposNodos.NODOCUATRO);
	                            hijoizq.set_v2(i.get_v1());
	                            hijoizq.set_v3(hijoderc.get_v1());

	                            i.set_tipo(TiposNodos.NODODOS);
	                            i.set_ci(null);
	                            i.set_v1(i.get_v2());

	                            borrar_aux_recursivo(i.get_hi(), valor);
	                        }
	                        else if(pos == 2)
	                        {
	                            hijoderc.set_tipo(TiposNodos.NODOCUATRO);
	                            hijoderc.set_v3(hijoderc.get_v1());
	                            hijoderc.set_v2(i.get_v1());
	                            hijoderc.set_v1(hijoizq.get_v1());

	                            i.set_tipo(TiposNodos.NODODOS);
	                            i.set_ci(null);

	                            borrar_aux_recursivo(i.get_hd(), valor);
	                        }
	                    }
	                    else
	                    {
	                        if(pos == 1)
	                        {
	                            hijoizq.set_tipo(TiposNodos.NODOCUATRO);
	                            hijoizq.set_v2(i.get_v1());
	                            hijoizq.set_v3(hijoderc.get_v1());

	                            i.set_tipo(TiposNodos.NODOTRES);
	                            i.set_ci(i.get_cd());
	                            i.set_cd(null);
	                            i.set_v1(i.get_v2());
	                            i.set_v2(i.get_v3());

	                            borrar_aux_recursivo(i.get_hi(), valor);
	                        }
	                        else if(pos == 2)
	                        {
	                            hijoizq.set_tipo(TiposNodos.NODOCUATRO);
	                            hijoizq.set_v2(i.get_v2());
	                            hijoizq.set_v3(hijoderc.get_v1());

	                            i.set_tipo(TiposNodos.NODOTRES);
	                            i.set_cd(null);
	                            i.set_v2(i.get_v3());

	                            borrar_aux_recursivo(i.get_ci(), valor);
	                        }
	                        else //pos es 3
	                        {
	                            hijoizq.set_tipo(TiposNodos.NODOCUATRO);
	                            hijoizq.set_v2(i.get_v3());
	                            hijoizq.set_v3(hijoderc.get_v1());

	                            i.set_tipo(TiposNodos.NODOTRES);
	                            
	                            i.set_hd(i.get_cd());

	                            borrar_aux_recursivo(i.get_hd(), valor);
	                        }
	                    }
	                }
	            }
	        }
	    }
	}
	
	public void sustituye(Nodo i, int pos, int valor)
	{
		if(pos == 1)
		{
			i.set_v1(valor);
		}
		else if(pos == 2)
		{
			i.set_v2(valor);
		}
		else
		{
			i.set_v3(valor);
		}
	}
	
	
	private Nodo borra_valor_en_nodo3(Nodo i, int valor) //Sabemos que el valor existe en el nodo i, devuelve el hijo q sobra
	{
		Nodo ret = null;

		i.set_tipo(TiposNodos.NODODOS);
		
		if(valor == i.get_v1())
		{
			ret = i.get_hi();
			i.set_hi(i.get_ci());
			i.set_v1(i.get_v2());
		}
		else
		{
			ret = i.get_hd();
			i.set_hd(i.get_ci());
		}
		
		return ret;
	}
	
	//hacer lo mismo que la de arriba
	private Nodo borra_valor_en_nodo4(Nodo i, int valor) //Sabemos que el valor existe en el nodo i
	{
		Nodo ret = null;
		i.set_tipo(TiposNodos.NODOTRES);
	
		if(valor == i.get_v1())
		{
			ret = i.get_hi();
				
			i.set_hi(i.get_ci());
			i.set_ci(i.get_cd());
				
			i.set_v1(i.get_v2());
			i.set_v2(i.get_v3());
		}
		else if(valor == i.get_v3())
		{
			ret = i.get_cd();
				
			i.set_hd(i.get_cd());
		}
		else
		{
			i.set_v2(i.get_v3());
		}
		
		return ret;
		
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
	 * Caso peor en O(2*Log(n))
	 * 
	 */
	
	public void insertar(int valor)
	{
		if(_raiz != null)
		{
			/*Algoritmo semejante al método buscar pero en este caso nos quedamos con
			* el ultimo nodo aunque este no contenga el valor
			*/
			Nodo buscar_i = _raiz, ultimo = _raiz;
			boolean existe = false;
			
			while(buscar_i != null && !existe)
			{
				ultimo = buscar_i;
				existe = buscar_i.existe_valor(valor);
				
				if(buscar_i.get_tipo() == TiposNodos.NODODOS )
				{
					if(valor > buscar_i.get_v1()) buscar_i = buscar_i.get_hd();
					else buscar_i = buscar_i.get_hi();
				}
				else
				{
					if(buscar_i.get_tipo() == TiposNodos.NODOTRES)
					{
						if(valor > buscar_i.get_v2()) buscar_i = buscar_i.get_hd();
						else if (valor < buscar_i.get_v1()) buscar_i = buscar_i.get_hi();
						else if ((valor > buscar_i.get_v1()) && (valor < buscar_i.get_v2())) buscar_i = buscar_i.get_ci(); 
					}
					else
					{
						if(valor > buscar_i.get_v3()) buscar_i = buscar_i.get_hd();
						else if (valor < buscar_i.get_v1()) buscar_i = buscar_i.get_hi();
						else if ((valor > buscar_i.get_v2()) && (valor < buscar_i.get_v3())) buscar_i = buscar_i.get_cd(); 
						else if ((valor > buscar_i.get_v1()) && (valor < buscar_i.get_v2())) buscar_i = buscar_i.get_ci(); 
					}
				}
			}
			
			if(!existe) //Si no existe el valor en el árbol podemos insertarlo
			{
				if(ultimo.get_tipo() == TiposNodos.NODODOS) inserta_valor_en_nodo2(ultimo, valor);
				else if(ultimo.get_tipo() == TiposNodos.NODOTRES) inserta_valor_en_nodo3(ultimo, valor);
				else inserta_aux(valor); //Al ser un nodo cuatro tenemos que reajustar el arbol desde arriba
			}
		}
		else _raiz = new Nodo(valor); //Creamos la raiz del arbol
		
	}
	
	private void inserta_aux(int valor)
	{
		if(_raiz.get_tipo() == TiposNodos.NODOCUATRO) //Deshacemos el nodo raiz si es nodocuatro para cumplir el invariante de deshace_hijos_nodo4()
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
		}
		
		deshace_hijos_nodo4(valor); 
	}
	
	/*
	 * Este algoritmo vuelve a realizar la busqueda desde la raiz rehaciendo los hijos que sean nodo4.
	 * 
	 * Se cumple como invariente que Nodo i nunca es un nodo 4.
	 * La raiz además será NodoDos o NodoTres y que distinta de null.
	 * 
	 * Casos para deshacer los nodos  4:
	 * 
	 * 1) Nodo 4 debajo de un nodo 2.
	 * 		a) Como hijo izquierdo
	 * 		b) Como hijo derecho
	 * 
	 * 2) Nodo 4 debajo de un nodo 3.
	 * 		a) Como hijo izquierdo
	 * 		b) Como hijo central
	 * 		c) Como hijo derecho
	 * 
	 */
	
	private void deshace_hijos_nodo4(int valor)
	{
		Nodo i = _raiz, siguiente_i = _raiz, aux = null;
		Nodo hijoiz = i.get_hi(), central = i.get_ci(), hijodr = i.get_hd();
		int valoraux;
		
		while((i != null) && (siguiente_i != null))
		{			
			
			if(i.get_tipo() == TiposNodos.NODODOS)
			{
				if(valor > i.get_v1()) siguiente_i = hijodr;
				else siguiente_i = hijoiz;
				
				if(siguiente_i != null)
				{
					// ------- CASO 1 - a
					if((hijoiz.get_tipo() == TiposNodos.NODOCUATRO) && (siguiente_i == hijoiz))
					{
						inserta_valor_en_nodo2(i,hijoiz.get_v2());
						
						valoraux = hijoiz.get_v3();
						aux = new Nodo(valoraux); // Creamos el nuevo nodo2
					
						hijoiz.set_tipo(TiposNodos.NODODOS); //Reutilizamos el puntero al hijo izquierdo de la i redistribuyendo el nodo4
						aux.set_hi(hijoiz.get_cd());
						aux.set_hd(hijoiz.get_hd());
						hijoiz.set_hd(hijoiz.get_ci());
		
						i.set_ci(aux); //Usamos como central el nuevo nodo creado
					}
					// ------- CASO 1 - b
					else if((hijodr.get_tipo() == TiposNodos.NODOCUATRO) && (siguiente_i == hijodr))
					{
						inserta_valor_en_nodo2(i,hijodr.get_v2());
					
						valoraux = hijodr.get_v1();
						aux = new Nodo(valoraux); // Creamos el nuevo nodo2
					
						hijodr.set_tipo(TiposNodos.NODODOS); //Reutilizamos el puntero al hijo derecho de la i redistribuyendo el nodo4
						hijodr.set_v1(hijodr.get_v3());
						aux.set_hi(hijodr.get_hi());
						aux.set_hd(hijodr.get_ci());
						hijodr.set_hi(hijodr.get_cd());
		
						i.set_ci(aux); //Como central el nuevo creado
					}
				}
			}
			else //obligatoriamente será NODOTRES la i
			{
				if(valor > i.get_v2()) siguiente_i = hijodr;
				else if (valor < i.get_v1()) siguiente_i = hijoiz;
				else siguiente_i = central; 
				
				if(siguiente_i != null)
				{
					// ------- CASO 2 - a
					if((hijoiz.get_tipo() == TiposNodos.NODOCUATRO) && (siguiente_i == hijoiz))
					{
						inserta_valor_en_nodo3(i,hijoiz.get_v2());
						
						valoraux = hijoiz.get_v3();
						aux = new Nodo(valoraux);
					
						hijoiz.set_tipo(TiposNodos.NODODOS);
						aux.set_hi(hijoiz.get_cd());
						aux.set_hd(hijoiz.get_hd());
						hijoiz.set_hd(hijoiz.get_ci());
						
						i.set_cd(i.get_ci());
						i.set_ci(aux);
					}
					// ------- CASO 2 - b
					else if((central.get_tipo() == TiposNodos.NODOCUATRO)  && (siguiente_i == central))
					{
						inserta_valor_en_nodo3(i,central.get_v2());
						
						valoraux = hijoiz.get_v3();
						aux = new Nodo(valoraux);
					
						central.set_tipo(TiposNodos.NODODOS);
						aux.set_hi(central.get_cd());
						aux.set_hd(central.get_hd());
						central.set_hd(central.get_ci());
		
						i.set_cd(aux);
					}
					// ------- CASO 2 - c
					else if((hijodr.get_tipo() == TiposNodos.NODOCUATRO) && (siguiente_i == hijodr))
					{
						inserta_valor_en_nodo3(i,hijodr.get_v2());
					
						valoraux = hijodr.get_v1();
						aux = new Nodo(valoraux);
					
						hijodr.set_tipo(TiposNodos.NODODOS);
						hijodr.set_v1(hijodr.get_v3());
						aux.set_hi(hijodr.get_hi());
						aux.set_hd(hijodr.get_ci());
						hijodr.set_hi(hijodr.get_cd());
		
						i.set_cd(aux);
					}
				}
			}
			
			if(siguiente_i != null) //ERROR CON EL SIGUIENTE
			{
				i = siguiente_i;
				hijoiz = i.get_hi();
				central = i.get_ci();  //por si acaso i es nodotres
				hijodr = i.get_hd();
			}
		}
		
		//insertamos el valor en el nodo final donde debería estar el valor una vez deshechos los nodos-4
		
		if(i.get_tipo() == TiposNodos.NODODOS)
			inserta_valor_en_nodo2(i, valor); 
		else inserta_valor_en_nodo3(i,valor);
	}
	
	private void inserta_valor_en_nodo2(Nodo i, int valor)
	{
		i.set_tipo(TiposNodos.NODOTRES);
		if(valor > i.get_v1()) 
			i.set_v2(valor);
		else
		{
			i.set_v2(i.get_v1());
			i.set_v1(valor);
		}
	}
	
	private void inserta_valor_en_nodo3(Nodo i, int valor)
	{
		i.set_tipo(TiposNodos.NODOCUATRO);
				
		if(valor > i.get_v2()) 
			i.set_v3(valor);
		else if (valor < i.get_v1()) 
		{
			i.set_v3(i.get_v2());
			i.set_v2(i.get_v1());
			i.set_v1(valor);
		}
		else //el nuevo valor va entre v1 y v2
		{
			i.set_v3(i.get_v2());
			i.set_v2(valor);
		}
	}
	
	// Necesario para la representación del arbol2-3-4
	
	public Nodo get_raiz() {
		return _raiz;
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
