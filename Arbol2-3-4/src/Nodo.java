/*
* Desarrollado por: Víctor Manuel Cavero Gracia
*/

public class Nodo {

	protected TiposNodos _tipo;

	protected Nodo _hi; // Hijo izquierdo
	protected Nodo _ci; // Hijo central izquierdo
	protected Nodo _cd; // Hijo central derecho
	protected Nodo _hd; // Hijo derecho

	protected Integer _v1; // valor 1 - Para los nodos dos-tres-cuatro
	protected Integer _v2; // valor 2 - Para los nodos tres-cuatro
	protected Integer _v3; // valor 3 - Para los nodos cuatro

	public Nodo(int valor) // Crear un NODODOS hoja
	{
		_tipo = TiposNodos.NODODOS;
		_v1 = valor;
		_v2 = null;
		_v3 = null;
		_hi = null;
		_ci = null;
		_cd = null;
		_hd = null;
	}

	public boolean es_hoja() {
		return ((_hi == null) && (_hd == null) && (_ci == null) && (_cd == null));
	}

	public boolean existe_valor(int valor) {
		if (_tipo == TiposNodos.NODODOS) {
			return (_v1 == valor);
		} else if (_tipo == TiposNodos.NODOTRES) {
			return (_v1 == valor) || (_v2 == valor);
		} else {
			return (_v1 == valor) || (_v2 == valor) || (_v3 == valor);
		}
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder("");

		if (_tipo == TiposNodos.NODODOS)
			sb.append("NodoDos ( " + _v1 + " )");
		else if (_tipo == TiposNodos.NODOTRES)
			sb.append("NodoTres ( " + _v1 + " , " + _v2 + " )");
		else if (_tipo == TiposNodos.NODOCUATRO)
			sb.append("NodoCuatro ( " + _v1 + " , " + _v2 + " , " + _v3 + " )");

		return sb.toString();
	}

	// getters y setters necesarios para las CONSULTAS AL NODO.

	public TiposNodos get_tipo() {
		return _tipo;
	}

	public void set_tipo(TiposNodos _tipo) {
		this._tipo = _tipo;
	}

	public Nodo get_hi() {
		return _hi;
	}

	public void set_hi(Nodo _hi) {
		this._hi = _hi;
	}

	public Nodo get_ci() {
		return _ci;
	}

	public void set_ci(Nodo _ci) {
		this._ci = _ci;
	}

	public Nodo get_cd() {
		return _cd;
	}

	public void set_cd(Nodo _cd) {
		this._cd = _cd;
	}

	public Nodo get_hd() {
		return _hd;
	}

	public void set_hd(Nodo _hd) {
		this._hd = _hd;
	}

	public int get_v1() {
		return _v1;
	}

	public void set_v1(Integer _v1) {
		this._v1 = _v1;
	}

	public int get_v2() {
		return _v2;
	}

	public void set_v2(Integer _v2) {
		this._v2 = _v2;
	}

	public int get_v3() {
		return _v3;
	}

	public void set_v3(Integer _v3) {
		this._v3 = _v3;
	}

}
