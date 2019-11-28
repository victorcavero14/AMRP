package nodos;

public class NodoTres extends NodoDos{

	protected int _valor2;
	protected NodoDos _hijoCentralIzq;
	
	public NodoTres(int valor1, int valor2) {
		super(valor1);
		_valor2 = valor2;
		_hijoCentralIzq = null;
	}

	@Override
	public String toString() {
		return "NodoTres ( " + _valor1 + " , " + _valor2 + " )";
	}

}
