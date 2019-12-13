package nodos;

public class NodoDos extends Nodo{

	
	
	public NodoDos(int valor)
	{
		_tipo = TiposNodos.NODODOS;
		_v1 = valor;
		_hi = null;
		_hd = null;		
	}
	
	public NodoDos(int valor, Nodo hi, Nodo hd)
	{
		_tipo = TiposNodos.NODODOS;
		_v1 = valor;
		_hi = hi;
		_hd = hd;		
	}
	
	@Override
	public String toString() {
		return "NodoDos ( " + _v1 + " )";
	}

	@Override
	public boolean es_hoja() {
		return (_hi == null && _hd == null);
	}
	
	
}
