package nodos;

public class NodoTres extends Nodo{

	public NodoTres(int valor1, int valor2)
	{
		_tipo = TiposNodos.NODOTRES;
		_v1 = valor1;
		_v2 = valor2;
		_hi = null;
		_hd = null;	
		_ci = null;
	}
	
	public NodoTres(int valor1, int valor2, Nodo hi, Nodo central, Nodo hd)
	{
		_tipo = TiposNodos.NODOTRES;
		_v1 = valor1;
		_v2 = valor2;
		_hi = hi;
		_ci = central; //pongo como central el izquierdo
		_hd = hd;		
	}
	
	@Override
	public String toString() {
		return "NodoTres ( " + _v1 + " , " + _v2 +  " )";
	}

	@Override
	public boolean es_hoja() {
		return (_hi == null && _hd == null && _ci == null);
	}
	
	

}
