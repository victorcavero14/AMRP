package nodos;

public class NodoDos {

	protected NodoDos _hijoIzq;
	protected NodoDos _hijoDrc;
	protected int _valor1;
	
	
	public NodoDos(int valor)
	{
		_hijoIzq = null;
		_hijoDrc = null;
		_valor1 = valor;
	}
	
	
	//getters and setters
	
	public NodoDos get_hijoIzq() {
		return _hijoIzq;
	}

	public void set_hijoIzq(NodoDos _hijoIzq) {
		this._hijoIzq = _hijoIzq;
	}

	public NodoDos get_hijoDrc() {
		return _hijoDrc;
	}

	public void set_hijoDrc(NodoDos _hijoDrc) {
		this._hijoDrc = _hijoDrc;
	}

	public int get_valor1() {
		return _valor1;
	}

	public void set_valor1(int _valor1) {
		this._valor1 = _valor1;
	}

	@Override
	public String toString() {
		return "NodoDos ( " + _valor1 + " )";
	}
	
	
}
