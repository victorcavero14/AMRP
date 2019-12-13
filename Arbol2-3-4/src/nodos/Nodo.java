package nodos;

public abstract class Nodo {
	
	protected TiposNodos _tipo;
	
	protected Nodo _hi; //Hijo izquierdo
	protected Nodo _ci;
	protected Nodo _cd;
	protected Nodo _hd;
	
	protected int _v1;
	protected int _v2;
	protected int _v3;

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

	public void set_v1(int _v1) {
		this._v1 = _v1;
	}

	public int get_v2() {
		return _v2;
	}

	public void set_v2(int _v2) {
		this._v2 = _v2;
	}

	public int get_v3() {
		return _v3;
	}

	public void set_v3(int _v3) {
		this._v3 = _v3;
	}

	public abstract boolean es_hoja();

	public abstract String toString();
	
	
}
