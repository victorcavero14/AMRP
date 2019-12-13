package nodos;

public class NodoCuatro extends Nodo{

	public NodoCuatro(int valor1, int valor2, int valor3)
	{
		_tipo = TiposNodos.NODOCUATRO;
		_v1 = valor1;
		_v2 = valor2;
		_v3 = valor3;
		_hi = null;
		_hd = null;	
		_ci = null;
		_cd = null;
	}
	
	public NodoCuatro(int valor1, int valor2, int valor3, Nodo hi, Nodo centralizq,
			Nodo centraldrc, Nodo hd) {
		
		_tipo = TiposNodos.NODOCUATRO;
		_v1 = valor1;
		_v2 = valor2;
		_v3 = valor3;
		_hi = hi;
		_ci = centralizq; //pongo como central el izquierdo
		_cd = centraldrc;
		_hd = hd;		
	}
	
	@Override
	public String toString() {
		return "NodoTres ( " + _v1 + " , " + _v2 + " , " + _v3 + " )";
	}

	@Override
	public boolean es_hoja() {
		return (_hi == null && _hd == null && _ci == null && _cd == null);
	}
	

}
