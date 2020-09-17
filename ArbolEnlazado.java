package TDAArbol;

import java.util.Iterator;
import TDALista.*;

public class ArbolEnlazado<E> implements Tree<E> {
	// atributos

	protected TNodo<E> raiz;
	protected int size;

	// constructores

	public ArbolEnlazado() {
		raiz = null;
		size = 0;
	}

	public ArbolEnlazado(E r) {
		raiz = new TNodo<E>(r, null);
		size = 1;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return raiz == null;
	}

	@Override
	public Iterator<E> iterator() {
		PositionList<E> lista = new ListaDobleEnlazada<E>();
		if (!isEmpty())
			preOrden(raiz, lista);
		return lista.iterator();
	}

	private void preOrden(TNodo<E> v, PositionList<E> lista) {
		lista.addLast(v.element());
		for (TNodo<E> h : v.getHijos())
			preOrden(h, lista);
	}

	@Override
	public Iterable<Position<E>> positions() {
		PositionList<Position<E>> lista = new ListaDobleEnlazada<Position<E>>();
		if (!isEmpty())
			preOrdenP(raiz, lista);
		return lista;
	}

	private void preOrdenP(TNodo<E> v, PositionList<Position<E>> lista) {
		lista.addLast(v);
		for (TNodo<E> h : v.getHijos())
			preOrdenP(h, lista);
	}

	// ___________________CHEQUEO DE POSICIONES VALIDAS___________________________\\
	private TNodo<E> checkPos(Position<E> p) throws InvalidPositionException {
		TNodo<E> nodo = null;
		try {
			nodo = (TNodo<E>) p;
			if (nodo == null)
				throw new InvalidPositionException("error, posición nula");
			if (nodo.element() == null)
				throw new InvalidPositionException("error, elemento nulo ");

		} catch (ClassCastException e) {
			throw new InvalidPositionException("error, la posición no pertenece al árbol");
		}
		return nodo;
	}
	// ___________________________________________________________________________\\

	@Override
	// Reemplaza el elemento almacenado en la posición dada por el elemento pasado
	// por parámetro. Devuelve el elemento reemplazado.
	public E replace(Position<E> v, E e) throws InvalidPositionException {
		TNodo<E> n = checkPos(v);
		E aux = n.element();
		n.setElemento(e);
		return aux;
	}

	@Override
	public Position<E> root() throws EmptyTreeException {
		if (raiz == null)
			throw new EmptyTreeException("error, árbol vacío");
		return raiz;
	}

	@Override
	public Position<E> parent(Position<E> p) throws InvalidPositionException, BoundaryViolationException {
		TNodo<E> n = checkPos(p);
		if (n == raiz)
			throw new BoundaryViolationException("la posición es la raiz del arbol");
		return n.getPadre();
	}

	@Override
	public Iterable<Position<E>> children(Position<E> p) throws InvalidPositionException {
		TNodo<E> n = checkPos(p);
		PositionList<Position<E>> lista = new ListaDobleEnlazada<Position<E>>();
		for (TNodo<E> h : n.getHijos())
			lista.addLast(h);
		return lista;
	}

	@Override
	public boolean isInternal(Position<E> p) throws InvalidPositionException {
		TNodo<E> nodo = checkPos(p);
		return !nodo.getHijos().isEmpty();
	}

	@Override
	public boolean isExternal(Position<E> p) throws InvalidPositionException {
		TNodo<E> nodo = checkPos(p);
		return nodo.getHijos().isEmpty();
	}

	@Override
	public boolean isRoot(Position<E> p) throws InvalidPositionException {
		TNodo<E> nodo = checkPos(p);
		return nodo == raiz;
	}

	@Override
	public void createRoot(E e) throws InvalidOperationException {
		if (size != 0)
			throw new InvalidOperationException("El arbol ya tiene raiz");
		raiz = new TNodo<E>(e, null);
		size++;
	}

	@Override
	public Position<E> addFirstChild(Position<E> p, E e) throws InvalidPositionException {
		if (size == 0)
			throw new InvalidPositionException("error, el arbol esta vacio");
		TNodo<E> n = checkPos(p);
		TNodo<E> nuevo = new TNodo<E>(e, n);
		n.getHijos().addFirst(nuevo);
		size++;
		return nuevo;
	}

	@Override
	public Position<E> addLastChild(Position<E> p, E e) throws InvalidPositionException {
		if (size == 0)
			throw new InvalidPositionException("error, el arbol esta vacio");
		TNodo<E> nodo = checkPos(p);
		TNodo<E> nuevo = new TNodo<E>(e, nodo);
		nodo.getHijos().addLast(nuevo);
		size++;
		return nuevo;
	}

	@Override
	public Position<E> addBefore(Position<E> p, Position<E> rb, E e) throws InvalidPositionException {
		if (size == 0)
			throw new InvalidPositionException("error, árbol vacío");
		TNodo<E> n = checkPos(p);
		TNodo<E> hd = checkPos(rb); // hermano derecho
		TNodo<E> nuevo = new TNodo<E>(e, n);
		PositionList<TNodo<E>> hijos = n.getHijos();
		boolean encontre = false;
		try {
			if (hd.getPadre() != n)
				throw new InvalidPositionException("el primer argumento no es el padre del segundo argumento");
			TDALista.Position<TNodo<E>> pp = hijos.first();
			while (!encontre) {
				if (hd == pp.element())
					encontre = true;
				else
					pp = hijos.next(pp);
			}
			hijos.addBefore(pp, nuevo);
			size++;
		} catch (TDALista.EmptyListException | TDALista.InvalidPositionException
				| TDALista.BoundaryViolationException a) {
		}
		return nuevo;
	}

	@Override
	public Position<E> addAfter(Position<E> p, Position<E> lb, E e) throws InvalidPositionException {
		if (size == 0)
			throw new InvalidPositionException("El arbol esta vacio");
		TNodo<E> n = checkPos(p);
		TNodo<E> hi = checkPos(lb); // hermano izquierdo
		TNodo<E> nuevo = new TNodo<E>(e, n);
		PositionList<TNodo<E>> hijos = n.getHijos();
		boolean encontre = false;
		try {
			if (hi.getPadre() != n)
				throw new InvalidPositionException("el primer argumento no es el padre del segundo argumento");
			TDALista.Position<TNodo<E>> pp = hijos.first();
			while (!encontre) {
				if (hi == pp.element())
					encontre = true;
				else
					pp = hijos.next(pp);
			}
			hijos.addAfter(pp, nuevo);
			size++;
		} catch (TDALista.EmptyListException | TDALista.InvalidPositionException
				| TDALista.BoundaryViolationException a) {
		}
		return nuevo;
	}

	@Override
	public void removeExternalNode(Position<E> p) throws InvalidPositionException {
		if (size == 0)
			throw new InvalidPositionException("El arbol esta vacio");
		TNodo<E> nodo = checkPos(p);
		if (!nodo.getHijos().isEmpty())
			throw new InvalidPositionException("No es un nodo externo");
		removeNode(nodo);

	}

	@Override
	public void removeInternalNode(Position<E> p) throws InvalidPositionException {
		if (size == 0)
			throw new InvalidPositionException("El arbol esta vacio");
		TNodo<E> nodo = checkPos(p);
		if (nodo.getHijos().isEmpty())
			throw new InvalidPositionException("No es un nodo interno");
		removeNode(nodo);
	}

	@Override
	public void removeNode(Position<E> p) throws InvalidPositionException {
		if (size == 0)
			throw new InvalidPositionException("El arbol esta vacio");
		TNodo<E> nodoAEliminar = checkPos(p);
		TNodo<E> padre = nodoAEliminar.getPadre();
		PositionList<TNodo<E>> hijos = nodoAEliminar.getHijos();
		TNodo<E> hijo;
		PositionList<TNodo<E>> hermanos;
		TDALista.Position<TNodo<E>> cursorListaHermanos;
		try {
			if (nodoAEliminar == raiz) {
				if (hijos.size() == 0) {
					raiz = null;
				} else {
					if (hijos.size() == 1) {
						hijo = hijos.remove(hijos.first());
						hijo.setPadre(null);
						raiz = hijo;
					} else
						throw new InvalidPositionException("No se puede eliminar una raiz con mas de un hijo");
				}
			} else {
				// si no es raiz, nodoAEliminar tiene un padre y una lista de hermanos
				hermanos = padre.getHijos();
				// busco nodoAEliminar en esta lista
				cursorListaHermanos = hermanos.first();
				while (cursorListaHermanos.element() != nodoAEliminar) {
					cursorListaHermanos = hermanos.next(cursorListaHermanos);
				}

				while (!hijos.isEmpty()) {
					hijo = hijos.remove(hijos.first());
					hijo.setPadre(padre);
					hermanos.addBefore(cursorListaHermanos, hijo);
				}
				hermanos.remove(cursorListaHermanos);
			}
			nodoAEliminar.setPadre(null);
			nodoAEliminar.setElemento(null);
			size--;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//---------------------------------8)A)-------------------------------------------------
	public void listar(boolean p) {
		listarAux(raiz, p);
	}

	private void listarAux(TNodo<E> n, boolean p) {
		if (p) {
			System.out.print(n.element());
			for (TNodo<E> h : n.getHijos())
				listarAux(h, p);
		} else {
			System.out.print(n.element());
			for (TNodo<E> h : n.getHijos())
				listarAux(h, p);
		}
	}

//---------------------------------8)B)-------------------------------------------------
	public ListaDobleEnlazada<TNodo<E>> extremosIzquierdosNoHojas() {
		ListaDobleEnlazada<TNodo<E>> l = new ListaDobleEnlazada<TNodo<E>>();
		if (size != 0) {
			for (TNodo<E> h : raiz.getHijos())
				izquierdosAux(h, l);
		}
		return l;
	}

	private void izquierdosAux(TNodo<E> n, ListaDobleEnlazada<TNodo<E>> l) {
		try {
			if (n.getPadre().getHijos().first() == n && !n.getHijos().isEmpty())// es hijo izquierdo y no es hoja
				l.addLast(n);
			for (TNodo<E> h : n.getHijos())
				izquierdosAux(h, l);
		} catch (EmptyListException e) {
			e.printStackTrace();
		}
	}

//---------------------------------8)C)-------------------------------------------------	
	public void eliminarHojas() {
		if (!isEmpty()) {
			if (size == 1) {
				raiz = null;
				size--;
			} else {
				for (TDALista.Position<TNodo<E>> h : raiz.getHijos().positions())
					eliminarHojasAux(h);
			}
		}
	}

	private void eliminarHojasAux(TDALista.Position<TNodo<E>> n) {
		try {
			TNodo<E> p = n.element().getPadre();
			if (n.element().getHijos().isEmpty()) {
				p.getHijos().remove(n);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//---------------------------------8)D)-------------------------------------------------
	public void eliminarRotulo(E rotulo) {// elimina los nodos con el rotulo parametrizado si es posible.
		// si la raiz tiene ese rotulo pero sigue teniendo mas de un hijo de elimar el
		// resto no se eliminara
		try {
			if (!isEmpty()) {
				for (TNodo<E> n : raiz.getHijos())
					eliminarRotuloAux(n, rotulo);
				if (raiz.element().equals(rotulo) && raiz.getHijos().size() < 2)
					removeNode(raiz);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void eliminarRotuloAux(TNodo<E> n, E rotulo) {
		try {
			for (TNodo<E> h : n.getHijos())
				eliminarRotuloAux(h, rotulo);
			if (n.element().equals(rotulo))
				removeNode(n);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//---------------------------------8)E)-------------------------------------------------
	public void eliminarRotuloProfundidad(int p, E rotulo) {
		try {
			for (TNodo<E> n : nodosNivel(p)) {
				if (n.element().equals(rotulo))
					removeNode(n);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private PositionList<TNodo<E>> nodosNivel(int n) {
		ListaDobleEnlazada<TNodo<E>> l = new ListaDobleEnlazada<TNodo<E>>();
		l.addLast(raiz);
		for (int i = 0; i < n; i++) {
			nodosNivelAux(l);
		}
		return l;
	}

	private void nodosNivelAux(ListaDobleEnlazada<TNodo<E>> l) {
		try {
			for (TDALista.Position<TNodo<E>> n : l.positions()) {
				for (TNodo<E> h : n.element().getHijos()) {
					l.addLast(h);
				}
				l.remove(n);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//---------------------------------8)F)-------------------------------------------------}
	public void espejar() {
		if (!isEmpty()) {
			espejarAux(raiz);
		}
	}

	private void espejarAux(TNodo<E> n) {
		invertirLista(n.getHijos());
		for (TNodo<E> h : n.getHijos()) {
			espejarAux(h);
		}
	}

	private void invertirLista(PositionList<TNodo<E>> l) {
		try {
			if (!l.isEmpty()) {
				TNodo<E> aux = l.remove(l.first());
				invertirLista(l);
				l.addLast(aux);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
