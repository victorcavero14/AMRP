/*
 * Implementación en Java de un árbol 2-3-4
 * Incluye las operaciones de insertar, borrar y buscar una clave dada.
 * 
 * 
 * Desarrollado por: Víctor Manuel Cavero Gracia
 * 
 */

public class Arbol234 {

	private Nodo _raiz;

	public Arbol234() {
		_raiz = null;
	}

	/*
	 * INSERTAR:
	 * 
	 * He realizado el insertado en dos pasadas(top-down) por la rama donde debería
	 * ser insertado el elemento.
	 * 
	 * En la primera bajada por la rama correspondiente de la inserción comprobamos
	 * de que tipo es el último nodo donde va a ser insertado. Si es NODODOS o
	 * NODOTRES se inserta directamente, en caso de ser un NODOCUATRO sería nesaría
	 * otro pasada desde arriba hasta abajo de la rama descomponiendo todos los
	 * NODOCUATRO.
	 * 
	 * Estos son el mejor y peor de los casos respectivamente, de cualquier manera
	 * su complejidad es O(log n).
	 * 
	 * return true si se ha realizado la operación correctamente, false en caso
	 * contrario (el único caso donde no se puede insertar es porque el valor ya
	 * existia en el árbol).
	 */

	public boolean insertar(int valor) {
		boolean insertado = true;
		if (_raiz != null) {
			Nodo ultimo = buscar_ultimo(valor);

			if (!ultimo.existe_valor(valor)) // Si no existe el valor en el árbol podemos insertarlo
			{
				boolean insert = inserta_valor_en_nodo(ultimo, valor);

				/*
				 * Como es un nodo cuatro no podemos insertar el valor, por lo que es necesario
				 * reestructurar el árbol. Flotamos los valores necesarios desde arriba hacia
				 * abajo, la función necesita el valor a insertar como referencia.
				 */

				if (!insert)
					flotar(valor);
			} else
				insertado = false;
		} else
			_raiz = new Nodo(valor); // Creamos la raiz del arbol
		return insertado;
	}

	private void flotar(int valor) {
		/*
		 * Introducimos este caso base para deshacer la raiz en caso de que sea
		 * NODOCUATRO y su siguiente sea NODOCUATRO o sea null,así poder cumplir el
		 * invariante de deshace_hijos_nodo4(valor) en el cual la i solo puede ser
		 * NODODOS o NODOTRES.
		 */
		if (_raiz.get_tipo() == TiposNodos.NODOCUATRO) {
			Nodo nizq, nder, siguiente = null;

			if (valor > _raiz.get_v3())
				siguiente = _raiz.get_hd();
			else if (valor < _raiz.get_v1())
				siguiente = _raiz.get_hi();
			else if ((valor > _raiz.get_v2()) && (valor < _raiz.get_v3()))
				siguiente = _raiz.get_cd();
			else
				siguiente = _raiz.get_ci();

			if (_raiz.es_hoja() || siguiente.get_tipo() == TiposNodos.NODOCUATRO) {
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
				_raiz.set_ci(null);
				_raiz.set_cd(null);
				_raiz.set_v2(null);
				_raiz.set_v3(null);
			}
		}

		deshace_hijos_nodo4(valor);
	}

	/*
	 * Este algoritmo vuelve a realizar la busqueda desde la raiz rehaciendo los
	 * hijos que sean nodo4.
	 * 
	 * Se cumple como invariente que Nodo i nunca es un nodo 4. La raiz además será
	 * NodoDos o NodoTres y distinta de null. El algoritmo es el que se corresponde
	 * con el "flotar".
	 * 
	 * Casos para deshacer los nodos 4:
	 * 
	 * 1) Nodo 4 debajo de un nodo 2: 
	 * 	a) Como hijo izquierdo. 
	 * 	b) Como hijo derecho.
	 * 
	 * 2) Nodo 4 debajo de un nodo 3: 
	 * 	a) Como hijo izquierdo. 
	 * 	b) Como hijo central.
	 *      c) Como hijo derecho.
	 * 
	 */

	private void deshace_hijos_nodo4(int valor) {
		Nodo i = _raiz, siguiente_i = _raiz, aux = null, ultimo = _raiz;
		Nodo hijoiz, central, hijodr;

		while (i != null) {
			ultimo = i;
			hijoiz = i.get_hi();
			central = i.get_ci();
			hijodr = i.get_hd();

			if (i.get_tipo() == TiposNodos.NODODOS) {
				if (valor > i.get_v1())
					siguiente_i = hijodr;
				else
					siguiente_i = hijoiz;

				if (siguiente_i != null) {
					if (siguiente_i.get_tipo() == TiposNodos.NODOCUATRO) {
						i.set_tipo(TiposNodos.NODOTRES);
						siguiente_i.set_tipo(TiposNodos.NODODOS);

						// ------- CASO 1 - a
						if (siguiente_i == hijoiz) {
							i.set_v2(i.get_v1());
							i.set_v1(siguiente_i.get_v2());
							aux = new Nodo(siguiente_i.get_v3()); // Creamos el nuevo nodo2

							// Reutilizamos el puntero al hijo izquierdo de la i
							// redistribuyendo el nodo4

							aux.set_hi(siguiente_i.get_cd());
							aux.set_hd(siguiente_i.get_hd());
							siguiente_i.set_hd(siguiente_i.get_ci());
						}
						// ------- CASO 1 - b
						else if (siguiente_i == hijodr) {
							i.set_v2(siguiente_i.get_v2());
							aux = new Nodo(siguiente_i.get_v1()); // Creamos el nuevo nodo2

							// Reutilizamos el puntero al hijo derecho de la i
							// redistribuyendo el nodo4

							siguiente_i.set_v1(siguiente_i.get_v3());
							aux.set_hi(siguiente_i.get_hi());
							aux.set_hd(siguiente_i.get_ci());
							siguiente_i.set_hi(siguiente_i.get_cd());
						}

						siguiente_i.set_ci(null);
						siguiente_i.set_cd(null);
						siguiente_i.set_v2(null);
						siguiente_i.set_v3(null);

						i.set_ci(aux); // Usamos como central el nuevo nodo creado

						if ((valor > i.get_v1()) && (valor < i.get_v2()))
							siguiente_i = i.get_ci();
					}

				}
			} else {
				/*
				 * Si no es NODODOS obligatoriamente la i sera NODOTRES ya que gracias al caso
				 * base lo hemos asegurado
				 */

				if (valor > i.get_v2())
					siguiente_i = hijodr;
				else if (valor < i.get_v1())
					siguiente_i = hijoiz;
				else
					siguiente_i = central;

				if (siguiente_i != null) {
					if (siguiente_i.get_tipo() == TiposNodos.NODOCUATRO) {
						i.set_tipo(TiposNodos.NODOCUATRO);
						siguiente_i.set_tipo(TiposNodos.NODODOS);

						// ------- CASO 2 - a
						if (siguiente_i == hijoiz) {
							i.set_v3(i.get_v2());
							i.set_v2(i.get_v1());
							i.set_v1(siguiente_i.get_v2());

							aux = new Nodo(siguiente_i.get_v3());

							aux.set_hi(hijoiz.get_cd());
							aux.set_hd(hijoiz.get_hd());
							siguiente_i.set_hd(siguiente_i.get_ci());

							i.set_cd(i.get_ci());
							i.set_ci(aux);

						}

						// ------- CASO 2 - b
						else if (siguiente_i == central) {
							i.set_v3(i.get_v2());
							i.set_v2(siguiente_i.get_v2());

							aux = new Nodo(siguiente_i.get_v3());

							aux.set_hi(siguiente_i.get_cd());
							aux.set_hd(siguiente_i.get_hd());
							siguiente_i.set_hd(siguiente_i.get_ci());

							i.set_cd(aux);

						}

						// ------- CASO 2 - c
						else if (siguiente_i == hijodr) {
							i.set_v3(siguiente_i.get_v2());

							aux = new Nodo(siguiente_i.get_v1());

							siguiente_i.set_v1(siguiente_i.get_v3());
							aux.set_hi(siguiente_i.get_hi());
							aux.set_hd(siguiente_i.get_ci());
							siguiente_i.set_hi(siguiente_i.get_cd());

							i.set_cd(aux);
						}

						siguiente_i.set_ci(null);
						siguiente_i.set_cd(null);
						siguiente_i.set_v2(null);
						siguiente_i.set_v3(null);

						if ((valor > i.get_v1()) && (valor < i.get_v2()))
							siguiente_i = i.get_ci();
						else if ((valor > i.get_v2()) && (valor < i.get_v3()))
							siguiente_i = i.get_cd();
					}
				}
			}

			i = siguiente_i;

		}

		/*
		 * insertamos el valor en el nodo final donde debería estar una vez deshechos
		 * los NODOSCUATRO.
		 */

		inserta_valor_en_nodo(ultimo, valor);
	}

	/*
	 * Función auxiliar de inserción que se hace sobre nodos HOJA por lo que no
	 * tenemos que preocuparnos de los punteros a los hijos.
	 * 
	 * return true si se ha insertado correctamente, false en caso contrario.
	 */
	private boolean inserta_valor_en_nodo(Nodo i, int valor) {

		Boolean insertado = false;

		if (i.get_tipo() == TiposNodos.NODODOS) {
			insertado = true;
			i.set_tipo(TiposNodos.NODOTRES);
			if (valor > i.get_v1())
				i.set_v2(valor);
			else {
				i.set_v2(i.get_v1());
				i.set_v1(valor);
			}
		} else if (i.get_tipo() == TiposNodos.NODOTRES) {
			insertado = true;
			i.set_tipo(TiposNodos.NODOCUATRO);
			if (valor > i.get_v2())
				i.set_v3(valor);
			else if (valor < i.get_v1()) {
				i.set_v3(i.get_v2());
				i.set_v2(i.get_v1());
				i.set_v1(valor);
			} else // el nuevo valor va entre v1 y v2
			{
				i.set_v3(i.get_v2());
				i.set_v2(valor);
			}
		}

		return insertado;
	}
	/*
	 * BUSQUEDA:
	 * 
	 * Algoritmo de búsqueda que recorre una de las ramas del árbol, decidiendo
	 * según los valores de los nodos la rama a seguir.
	 * 
	 * return: null en caso de no haber encontrado el valor en el árbol o el nodo
	 * donde se encuentra el valor buscado.
	 * 
	 * La complejidad de la búsqueda en cualquier caso es O(log n) ya que solo se
	 * recorren una de las ramas del árbol.
	 * 
	 */

	public Nodo buscar(int valor) {
		Nodo buscar_i = _raiz;
		boolean existe = false;

		while (buscar_i != null && !existe) {

			existe = buscar_i.existe_valor(valor);

			if (!existe) {
				if (buscar_i.get_tipo() == TiposNodos.NODODOS) {
					if (valor > buscar_i.get_v1())
						buscar_i = buscar_i.get_hd();
					else
						buscar_i = buscar_i.get_hi();
				} else if (buscar_i.get_tipo() == TiposNodos.NODOTRES) {
					if (valor > buscar_i.get_v2())
						buscar_i = buscar_i.get_hd();
					else if (valor < buscar_i.get_v1())
						buscar_i = buscar_i.get_hi();
					else
						buscar_i = buscar_i.get_ci();
				} else {
					if (valor > buscar_i.get_v3())
						buscar_i = buscar_i.get_hd();
					else if (valor < buscar_i.get_v1())
						buscar_i = buscar_i.get_hi();
					else if ((valor > buscar_i.get_v2()) && (valor < buscar_i.get_v3()))
						buscar_i = buscar_i.get_cd();
					else
						buscar_i = buscar_i.get_ci();
				}

			}
		}

		return buscar_i;
	}

	/*
	 * Función auxiliar para el insertado: buscar_ultimo te devuelve el nodo donde
	 * se encuentra el valor y si no lo encuentra te devuelve el último nodo donde
	 * debería ser insertado.
	 */
	private Nodo buscar_ultimo(int valor) {
		Nodo buscar_i = _raiz, ultimo = _raiz;
		boolean existe = false;

		while (buscar_i != null && !existe) {
			ultimo = buscar_i;
			existe = buscar_i.existe_valor(valor);

			if (buscar_i.get_tipo() == TiposNodos.NODODOS) {
				if (valor > buscar_i.get_v1())
					buscar_i = buscar_i.get_hd();
				else
					buscar_i = buscar_i.get_hi();
			} else if (buscar_i.get_tipo() == TiposNodos.NODOTRES) {
				if (valor > buscar_i.get_v2())
					buscar_i = buscar_i.get_hd();
				else if (valor < buscar_i.get_v1())
					buscar_i = buscar_i.get_hi();
				else
					buscar_i = buscar_i.get_ci();
			} else {
				if (valor > buscar_i.get_v3())
					buscar_i = buscar_i.get_hd();
				else if (valor < buscar_i.get_v1())
					buscar_i = buscar_i.get_hi();
				else if ((valor > buscar_i.get_v2()) && (valor < buscar_i.get_v3()))
					buscar_i = buscar_i.get_cd();
				else
					buscar_i = buscar_i.get_ci();
			}
		}

		return ultimo;
	}

	/*
	 * BORRADO:
	 * 
	 * Es muy parecido al insertado pero a la inversa, en vez de flotar es necesario
	 * hundir. Para ello primero hacemos un primer recorrido sobre la rama buscando
	 * el valor (return false si no lo encuentra) si este se encuentra en una hoja
	 * que sea un NODOTRES o NODOCUATRO simplemente borramos el valor que
	 * corresponda. En cualquier otro caso será necesario hundir el valor/valores
	 * correspondientes en la rama seguida.
	 *
	 * La complejidad será O(log n).
	 *
	 */

	public boolean borrar(int valor) {
		Boolean borrado = false;
		if (_raiz != null) {
			Nodo busqueda = buscar(valor);

			if (busqueda != null) {
				borrado = true;
				if (busqueda.es_hoja()) {
					if ((busqueda.get_tipo() == TiposNodos.NODODOS) && busqueda == _raiz)
						_raiz = null;
					else if (busqueda.get_tipo() == TiposNodos.NODOTRES)
						borra_valor_en_nodo3(busqueda, valor);
					else if (busqueda.get_tipo() == TiposNodos.NODOCUATRO)
						borra_valor_en_nodo4(busqueda, valor);
					else
						borrar_aux_recursivo(_raiz, valor);
				} else {
					// al no ser hoja tenemos que reajustar el árbol
					borrar_aux_recursivo(_raiz, valor);
				}
			} else
				borrado = false;
		}
		return borrado;
	}

	/*
	 * Debido a la gran casuística presente en el borrado de un árbol 2-3-4 la
	 * longitud del código se hace muy extensa, a pesar de esto he tratado de ser lo
	 * más exacto posible.
	 * 
	 * C.BASE: El nodo i es una hoja en la que existe el valor que deseamos borrar.
	 * (Al final del desarrollo del algoritmo podemos asegurar que el último nodo no
	 * va ser un NODODOS, ya que realizamos un hundimiento)
	 * 
	 * C.RECURSIVO: El nodo i en cuestión no es una hoja por lo que necesita que su
	 * valor sea hundido. De esta manera podemos separar dos casos distintos: valor
	 * en nodo (8 casos) y no es está el valor en el nodo (33 casos).
	 * 
	 */
	private void borrar_aux_recursivo(Nodo i, int valor) {
		if (i != null) {
			Boolean existe = i.existe_valor(valor);
			if (i.es_hoja() && existe) {
				if (i.get_tipo() == TiposNodos.NODOTRES)
					borra_valor_en_nodo3(i, valor);
				else
					borra_valor_en_nodo4(i, valor);
			} else if (!i.es_hoja()) {

				Nodo siguiente = null, hermano = null, hermano2 = null;

				if (!existe) {
					// Si el siguiente es NODODOS tendremos que realizar el hundimiento.
					if (i.get_tipo() == TiposNodos.NODODOS) {
						// Calculamos el siguiente nodo
						if (valor > i.get_v1())
							siguiente = i.get_hd();
						else
							siguiente = i.get_hi();

						if (siguiente.get_tipo() == TiposNodos.NODODOS) {
							/*
							 * Al ser el siguiente NODODOS y i NODODOS tenemos que ajustar el árbol y
							 * existen 6 Casos:
							 * 
							 * 1- Siguiente es hijo izquierdo : hermano NODODOS o NODOTRES o NODOCUATRO. 
							 * 2- Siguiente es hijo derecho : hermano NODODOS o NODOTRES o NODOCUATRO.
							 * 
							 */
							if (siguiente == i.get_hd()) {
								hermano = i.get_hi();

								if (hermano.get_tipo() == TiposNodos.NODODOS) {
									i.set_tipo(TiposNodos.NODOCUATRO);
									i.set_v2(i.get_v1());
									i.set_v3(siguiente.get_v1());
									i.set_v1(hermano.get_v1());

									i.set_hd(siguiente.get_hd());
									i.set_cd(siguiente.get_hi());
									i.set_ci(hermano.get_hd());
									i.set_hi(hermano.get_hi());

									siguiente = i;
								} else if (hermano.get_tipo() == TiposNodos.NODOTRES) {
									siguiente.set_tipo(TiposNodos.NODOTRES);
									siguiente.set_v2(siguiente.get_v1());
									siguiente.set_v1(i.get_v1());

									i.set_v1(hermano.get_v2());

									siguiente.set_ci(siguiente.get_hi());
									siguiente.set_hi(borra_valor_en_nodo3(hermano, hermano.get_v2()));

								} else {
									siguiente.set_tipo(TiposNodos.NODOTRES);
									siguiente.set_v2(siguiente.get_v1());
									siguiente.set_v1(i.get_v1());

									i.set_v1(hermano.get_v3());

									siguiente.set_ci(siguiente.get_hi());
									siguiente.set_hi(borra_valor_en_nodo4(hermano, hermano.get_v3()));

								}
							} else if (siguiente == i.get_hi()) {
								hermano = i.get_hd();

								if (hermano.get_tipo() == TiposNodos.NODODOS) {
									i.set_tipo(TiposNodos.NODOCUATRO);
									i.set_v2(i.get_v1());
									i.set_v1(siguiente.get_v1());
									i.set_v3(hermano.get_v1());

									i.set_hd(hermano.get_hd());
									i.set_cd(hermano.get_hi());
									i.set_ci(siguiente.get_hd());
									i.set_hi(siguiente.get_hi());

									siguiente = i;
								} else if (hermano.get_tipo() == TiposNodos.NODOTRES) {
									siguiente.set_tipo(TiposNodos.NODOTRES);
									siguiente.set_v2(i.get_v1());

									i.set_v1(hermano.get_v1());

									siguiente.set_ci(siguiente.get_hd());
									siguiente.set_hd(borra_valor_en_nodo3(hermano, hermano.get_v1()));

								} else {
									siguiente.set_tipo(TiposNodos.NODOTRES);
									siguiente.set_v2(i.get_v1());

									i.set_v1(hermano.get_v1());

									siguiente.set_ci(siguiente.get_hd());
									siguiente.set_hd(borra_valor_en_nodo4(hermano, hermano.get_v1()));

								}
							}
						}
					} else if (i.get_tipo() == TiposNodos.NODOTRES) {
						if (valor > i.get_v2())
							siguiente = i.get_hd();
						else if (valor < i.get_v1())
							siguiente = i.get_hi();
						else
							siguiente = i.get_ci();

						if (siguiente.get_tipo() == TiposNodos.NODODOS) // i no tiene el valor y el siguiente es nododos
						{
							/*
							 * Al ser el siguiente NODODOS y i NODOTRES tenemos que ajustar el árbol y
							 * existen 11 Casos:
							 * 
							 * 1- Siguiente es hijo izquierdo : hermano NODODOS o NODOTRES o NODOCUATRO. 
							 * 2- Siguiente es hijo central : los dos hermanos NODODOS, hermano NODOTRES o
							 * NODOCUATRO, hermano2 NODOTRES o NODOCUATRO. 
							 * 3- Siguiente es hijo derecho : hermano NODODOS o NODOTRES o NODOCUATRO.
							 * 
							 */

							if (siguiente == i.get_hi()) {
								hermano = i.get_ci();

								if (hermano.get_tipo() == TiposNodos.NODODOS) {
									siguiente.set_tipo(TiposNodos.NODOCUATRO);
									siguiente.set_v2(i.get_v1());
									siguiente.set_v3(hermano.get_v1());

									i.set_tipo(TiposNodos.NODODOS);
									i.set_v1(i.get_v2());

									siguiente.set_ci(siguiente.get_hd());
									siguiente.set_cd(hermano.get_hi());
									siguiente.set_hd(hermano.get_hd());

									i.set_ci(null);

								} else if (hermano.get_tipo() == TiposNodos.NODOTRES) {
									siguiente.set_tipo(TiposNodos.NODOTRES);
									siguiente.set_v2(i.get_v1());

									i.set_v1(hermano.get_v1());

									hermano.set_tipo(TiposNodos.NODODOS);
									hermano.set_v1(hermano.get_v2());

									siguiente.set_ci(siguiente.get_hd());
									siguiente.set_hd(hermano.get_hi());

									hermano.set_hi(hermano.get_ci());
									hermano.set_ci(null);

								} else {
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
							} else if (siguiente == i.get_ci()) {
								hermano = i.get_hi();
								hermano2 = i.get_hd();

								if (hermano.get_tipo() == TiposNodos.NODODOS
										&& hermano2.get_tipo() == TiposNodos.NODODOS)
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
								} else if (hermano.get_tipo() == TiposNodos.NODOTRES)
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

								} else if (hermano.get_tipo() == TiposNodos.NODOCUATRO) {
									siguiente.set_tipo(TiposNodos.NODOTRES);
									siguiente.set_v2(siguiente.get_v1());
									siguiente.set_v1(i.get_v1());

									i.set_v1(hermano.get_v3());

									hermano.set_tipo(TiposNodos.NODOTRES);

									siguiente.set_ci(siguiente.get_hi());
									siguiente.set_hi(hermano.get_hd());

									hermano.set_hd(hermano.get_cd());
									hermano.set_cd(null);

								} else if (hermano.get_tipo() == TiposNodos.NODODOS
										&& hermano2.get_tipo() == TiposNodos.NODOTRES) {
									siguiente.set_tipo(TiposNodos.NODOTRES);
									siguiente.set_v2(i.get_v2());

									i.set_v2(hermano2.get_v1());

									hermano2.set_tipo(TiposNodos.NODODOS);
									hermano2.set_v1(hermano2.get_v2());

									siguiente.set_ci(siguiente.get_hd());
									siguiente.set_hd(hermano2.get_hi());

									hermano2.set_hi(hermano2.get_ci());
									hermano2.set_ci(null);

								} else if (hermano.get_tipo() == TiposNodos.NODODOS
										&& hermano2.get_tipo() == TiposNodos.NODOCUATRO) {
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
							} else // siguiente es hd
							{
								hermano = i.get_ci();

								if (hermano.get_tipo() == TiposNodos.NODODOS) {
									siguiente.set_tipo(TiposNodos.NODOCUATRO);
									siguiente.set_v3(siguiente.get_v1());
									siguiente.set_v2(i.get_v2());
									siguiente.set_v1(hermano.get_v1());

									i.set_tipo(TiposNodos.NODODOS);

									siguiente.set_cd(siguiente.get_hi());
									siguiente.set_ci(hermano.get_hd());
									siguiente.set_hi(hermano.get_hi());

									i.set_ci(null);

								} else if (hermano.get_tipo() == TiposNodos.NODOTRES) {
									siguiente.set_tipo(TiposNodos.NODOTRES);
									siguiente.set_v2(siguiente.get_v1());
									siguiente.set_v1(i.get_v2());

									i.set_v2(hermano.get_v2());

									hermano.set_tipo(TiposNodos.NODODOS);

									siguiente.set_ci(siguiente.get_hi());
									siguiente.set_hi(hermano.get_hd());

									hermano.set_hd(hermano.get_ci());
									hermano.set_ci(null);

								} else {
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
					} else {
						if (valor > i.get_v3())
							siguiente = i.get_hd();
						else if (valor < i.get_v1())
							siguiente = i.get_hi();
						else if ((valor > i.get_v2()) && (valor < i.get_v3()))
							siguiente = i.get_cd();
						else
							siguiente = i.get_ci();

						if (siguiente.get_tipo() == TiposNodos.NODODOS) {
							/*
							 * Al ser el siguiente NODODOS y i NODOCUATRO tenemos que ajustar el árbol y
							 * existen 16 Casos:
							 * 
							 * 1- Siguiente es hijo izquierdo : hermano NODODOS o NODOTRES o NODOCUATRO. 
							 * 2- Siguiente es hijo central izquierdo: los dos hermanos NODODOS, hermano
							 * NODOTRES o NODOCUATRO, hermano2 NODOTRES o NODOCUATRO. 
							 * 3- Siguiente es hijo central derecho: los dos hermanos NODODOS, hermano NODOTRES o NODOCUATRO,
							 * hermano2 NODOTRES o NODOCUATRO. 
							 * 4 - Siguiente es hijo derecho : hermano NODODOS o NODOTRES o NODOCUATRO.
							 * 
							 */

							if (siguiente == i.get_hi()) {
								hermano = i.get_ci();

								if (hermano.get_tipo() == TiposNodos.NODODOS) {
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

								} else if (hermano.get_tipo() == TiposNodos.NODOTRES) {
									siguiente.set_tipo(TiposNodos.NODOTRES);
									siguiente.set_v2(i.get_v1());

									i.set_v1(hermano.get_v1());

									hermano.set_tipo(TiposNodos.NODODOS);
									hermano.set_v1(hermano.get_v2());

									siguiente.set_ci(siguiente.get_hd());
									siguiente.set_hd(hermano.get_hi());

									hermano.set_hi(hermano.get_ci());
									hermano.set_ci(null);

								} else {
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
							} else if (siguiente == i.get_ci()) {
								hermano = i.get_hi();
								hermano2 = i.get_cd();

								if (hermano.get_tipo() == TiposNodos.NODODOS
										&& hermano2.get_tipo() == TiposNodos.NODODOS) {
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
								} else if (hermano.get_tipo() == TiposNodos.NODOTRES) {
									siguiente.set_tipo(TiposNodos.NODOTRES);
									siguiente.set_v2(siguiente.get_v1());
									siguiente.set_v1(i.get_v1());

									i.set_v1(hermano.get_v2());

									hermano.set_tipo(TiposNodos.NODODOS);

									siguiente.set_ci(siguiente.get_hi());
									siguiente.set_hi(hermano.get_hd());

									hermano.set_hd(hermano.get_ci());
									hermano.set_ci(null);

								} else if (hermano.get_tipo() == TiposNodos.NODOCUATRO) {
									siguiente.set_tipo(TiposNodos.NODOTRES);
									siguiente.set_v2(siguiente.get_v1());
									siguiente.set_v1(i.get_v1());

									i.set_v1(hermano.get_v3());

									hermano.set_tipo(TiposNodos.NODOTRES);

									siguiente.set_ci(siguiente.get_hi());
									siguiente.set_hi(hermano.get_hd());

									hermano.set_hd(hermano.get_cd());
									hermano.set_cd(null);

								} else if (hermano.get_tipo() == TiposNodos.NODODOS
										&& hermano2.get_tipo() == TiposNodos.NODOTRES) {
									siguiente.set_tipo(TiposNodos.NODOTRES);
									siguiente.set_v2(i.get_v2());

									i.set_v2(hermano2.get_v1());

									hermano2.set_tipo(TiposNodos.NODODOS);
									hermano2.set_v1(hermano2.get_v2());

									siguiente.set_ci(siguiente.get_hd());
									siguiente.set_hd(hermano2.get_hi());

									hermano2.set_hi(hermano2.get_ci());
									hermano2.set_ci(null);

								} else if (hermano.get_tipo() == TiposNodos.NODODOS
										&& hermano2.get_tipo() == TiposNodos.NODOCUATRO) {
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
							} else if (siguiente == i.get_cd()) {
								hermano = i.get_ci();
								hermano2 = i.get_hd();

								if (hermano.get_tipo() == TiposNodos.NODODOS
										&& hermano2.get_tipo() == TiposNodos.NODODOS) {
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
								} else if (hermano.get_tipo() == TiposNodos.NODOTRES) {
									siguiente.set_tipo(TiposNodos.NODOTRES);
									siguiente.set_v2(siguiente.get_v1());
									siguiente.set_v1(i.get_v2());

									i.set_v2(hermano.get_v2());

									hermano.set_tipo(TiposNodos.NODODOS);

									siguiente.set_ci(siguiente.get_hi());
									siguiente.set_hi(hermano.get_hd());

									hermano.set_hd(hermano.get_ci());
									hermano.set_ci(null);

								} else if (hermano.get_tipo() == TiposNodos.NODOCUATRO) {
									siguiente.set_tipo(TiposNodos.NODOTRES);
									siguiente.set_v2(siguiente.get_v1());
									siguiente.set_v1(i.get_v2());

									i.set_v2(hermano.get_v3());

									hermano.set_tipo(TiposNodos.NODOTRES);

									siguiente.set_ci(siguiente.get_hi());
									siguiente.set_hi(hermano.get_hd());

									hermano.set_hd(hermano.get_cd());
									hermano.set_cd(null);

								} else if (hermano.get_tipo() == TiposNodos.NODODOS
										&& hermano2.get_tipo() == TiposNodos.NODOTRES) {
									siguiente.set_tipo(TiposNodos.NODOTRES);
									siguiente.set_v2(i.get_v3());

									i.set_v3(hermano2.get_v1());

									hermano2.set_tipo(TiposNodos.NODODOS);
									hermano2.set_v1(hermano2.get_v2());

									siguiente.set_ci(siguiente.get_hd());
									siguiente.set_hd(hermano2.get_hi());

									hermano2.set_hi(hermano2.get_ci());
									hermano2.set_ci(null);

								} else if (hermano.get_tipo() == TiposNodos.NODODOS
										&& hermano2.get_tipo() == TiposNodos.NODOCUATRO) {
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
							} else // siguiente es el hijo derecho
							{
								hermano = i.get_cd();

								if (hermano.get_tipo() == TiposNodos.NODODOS) {
									siguiente.set_tipo(TiposNodos.NODOCUATRO);
									siguiente.set_v3(siguiente.get_v1());
									siguiente.set_v2(i.get_v2());
									siguiente.set_v1(hermano.get_v1());

									i.set_tipo(TiposNodos.NODOTRES);

									siguiente.set_cd(siguiente.get_hi());
									siguiente.set_ci(hermano.get_hd());
									siguiente.set_hi(hermano.get_hi());

									i.set_cd(null);

								} else if (hermano.get_tipo() == TiposNodos.NODOTRES) {
									siguiente.set_tipo(TiposNodos.NODOTRES);
									siguiente.set_v2(siguiente.get_v1());
									siguiente.set_v1(i.get_v2());

									i.set_v2(hermano.get_v2());

									hermano.set_tipo(TiposNodos.NODODOS);

									siguiente.set_ci(siguiente.get_hi());
									siguiente.set_hi(hermano.get_hd());

									hermano.set_hd(hermano.get_ci());
									hermano.set_ci(null);

								} else {
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
					borrar_aux_recursivo(siguiente, valor);
				} else // existe valor en el nodo y no es hoja.
				{
					// hermano es el hijo izquierdo del valor y hermano2 es el hijo derecho
					// del valor a borrar
					int pos = 1, valor_sig = valor;
					siguiente = i;
					hermano = i.get_hi();
					hermano2 = i.get_hd();

					// Buscar los nodos entre los que se encuentra el valor y la pos del valor.
					if (i.get_v1() == valor) {
						if ((i.get_tipo() == TiposNodos.NODOTRES) || (i.get_tipo() == TiposNodos.NODOCUATRO))
							hermano2 = i.get_ci();
					} else if (i.get_v2() == valor) {
						pos = 2;
						hermano = i.get_ci();
						if (i.get_tipo() == TiposNodos.NODOCUATRO) {
							hermano2 = i.get_cd();
						}
					} else {
						pos = 3;
						hermano = i.get_cd();
					}

					/*
					 * Caso 2.1: Si el hijo izquierdo del valor es NODOTRES o NODOCUATRO sustituimos
					 * su valor más a la derecha por nuestro valor en cuestión a borrar y ese nuevo
					 * valor lo borramos del hijo izquierdo.
					 */
					if (hermano.get_tipo() == TiposNodos.NODOTRES) {
						sustituye(i, pos, hermano.get_v2());
						siguiente = hermano;
						valor_sig = hermano.get_v2();
					} else if (hermano.get_tipo() == TiposNodos.NODOCUATRO) {
						sustituye(i, pos, hermano.get_v3());
						siguiente = hermano;
						valor_sig = hermano.get_v3();
					}

					/*
					 * Caso 2.2: Si el hijo derecho del valor es NODOTRES o NODOCUATRO sustituimos
					 * su valor más a la izquierda por nuestro valor en cuestión a borrar y ese
					 * nuevo valor lo borramos del hijo derecho.
					 */
					if ((hermano2.get_tipo() == TiposNodos.NODOTRES)
							|| (hermano2.get_tipo() == TiposNodos.NODOCUATRO)) {
						sustituye(i, pos, hermano2.get_v1());
						siguiente = hermano2;
						valor_sig = hermano2.get_v1();
					}
					/*
					 * Caso 2.3: El valor esta rodeado por dos. a) i es un NODODOS: Valor en v1. b)
					 * i es un NODOTRES: Valor en v1 o v2. c) i es un NODOCUATRO: Valor en v1 o v2 o
					 * v3.
					 */
					if ((hermano.get_tipo() == TiposNodos.NODODOS) && (hermano2.get_tipo() == TiposNodos.NODODOS)) {
						if (i.get_tipo() == TiposNodos.NODODOS) {
							// CASO a) en V1
							i.set_tipo(TiposNodos.NODOCUATRO);
							i.set_v2(i.get_v1());
							i.set_v1(hermano.get_v1());
							i.set_v3(hermano2.get_v1());

							i.set_hi(hermano.get_hi());
							i.set_ci(hermano.get_hd());
							i.set_cd(hermano2.get_hi());
							i.set_hd(hermano2.get_hd());
						} else if (i.get_tipo() == TiposNodos.NODOTRES) {
							if (pos == 1) {
								// CASO b) en V1
								hermano.set_tipo(TiposNodos.NODOCUATRO);
								hermano.set_v2(i.get_v1());
								hermano.set_v3(hermano2.get_v1());

								i.set_tipo(TiposNodos.NODODOS);
								i.set_ci(null);
								i.set_v1(i.get_v2());

								siguiente = hermano;
							} else {
								// CASO b) en V2
								hermano2.set_tipo(TiposNodos.NODOCUATRO);
								hermano2.set_v3(hermano2.get_v1());
								hermano2.set_v2(i.get_v1());
								hermano2.set_v1(hermano.get_v1());

								i.set_tipo(TiposNodos.NODODOS);
								i.set_ci(null);

								siguiente = hermano2;
							}
						} else {
							if (pos == 1) {
								// CASO c) en V1
								hermano.set_tipo(TiposNodos.NODOCUATRO);
								hermano.set_v2(i.get_v1());
								hermano.set_v3(hermano2.get_v1());

								i.set_tipo(TiposNodos.NODOTRES);
								i.set_ci(i.get_cd());
								i.set_cd(null);
								i.set_v1(i.get_v2());
								i.set_v2(i.get_v3());

								siguiente = i.get_hi();
							} else if (pos == 2) {
								// CASO c) en V2
								hermano.set_tipo(TiposNodos.NODOCUATRO);
								hermano.set_v2(i.get_v2());
								hermano.set_v3(hermano2.get_v1());

								i.set_tipo(TiposNodos.NODOTRES);
								i.set_cd(null);
								i.set_v2(i.get_v3());

								siguiente = i.get_ci();
							} else {
								// CASO c) en V3
								hermano.set_tipo(TiposNodos.NODOCUATRO);
								hermano.set_v2(i.get_v3());
								hermano.set_v3(hermano2.get_v1());

								i.set_tipo(TiposNodos.NODOTRES);

								i.set_hd(i.get_cd());

								siguiente = i.get_hd();
							}
						}
					}
					borrar_aux_recursivo(siguiente, valor_sig);
				}
			}
		}
	}

	public void sustituye(Nodo i, int pos, int valor) {
		if (pos == 1) {
			i.set_v1(valor);
		} else if (pos == 2) {
			i.set_v2(valor);
		} else {
			i.set_v3(valor);
		}
	}

	/*
	 * Sabemos que existe el valor en el nodo i. return: el hijo que sobra al borrar
	 * un valor, en caso de usarlo en un nodo hoja devolvería null.
	 * 
	 */
	private Nodo borra_valor_en_nodo3(Nodo i, int valor) {
		Nodo ret = null;

		i.set_tipo(TiposNodos.NODODOS);

		if (valor == i.get_v1()) {
			ret = i.get_hi();
			i.set_hi(i.get_ci());
			i.set_v1(i.get_v2());
		} else {
			ret = i.get_hd();
			i.set_hd(i.get_ci());
		}

		i.set_ci(null);
		i.set_v2(null);

		return ret;
	}

	/*
	 * Sabemos que existe el valor en el nodo i. return: el hijo que sobra al borrar
	 * un valor, en caso de usarlo en un nodo hoja devolvería null.
	 * 
	 */
	private Nodo borra_valor_en_nodo4(Nodo i, int valor) {
		Nodo ret = null;
		i.set_tipo(TiposNodos.NODOTRES);

		if (valor == i.get_v1()) {
			ret = i.get_hi();
			i.set_hi(i.get_ci());
			i.set_ci(i.get_cd());

			i.set_v1(i.get_v2());
			i.set_v2(i.get_v3());
		} else if (valor == i.get_v3()) {
			ret = i.get_cd();
			i.set_hd(i.get_cd());
		} else {
			ret = i.get_cd();
			i.set_v2(i.get_v3());
		}

		i.set_cd(null);
		i.set_v3(null);

		return ret;

	}

	// Necesario para la representación del arbol2-3-4

	public Nodo get_raiz() {
		return _raiz;
	}

}
