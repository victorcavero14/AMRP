package nodos;

public class NodoCuatro extends NodoTres{

	protected int _valor3;
	protected NodoDos _hijoCentralDrc;
	
	public NodoCuatro(int valor1, int valor2, int valor3) {
		super(valor1, valor2);
		_valor3 = valor3;
		_hijoCentralDrc = null;
	}

}
