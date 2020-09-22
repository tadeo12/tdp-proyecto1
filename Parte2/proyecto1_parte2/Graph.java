package proyecto1_parte2;

import java.util.Iterator;
import java.util.LinkedList;

import java.util.logging.*;

public class Graph {

	private LinkedList<Edge> arcos;
	private LinkedList<Integer> nodos;

	private static Logger logger;

	public Graph() {
		nodos = new LinkedList<Integer>();
		arcos = new LinkedList<Edge>();

		if (logger == null) {
			logger = Logger.getLogger(Graph.class.getName());
			Handler hnd = new ConsoleHandler();
			hnd.setLevel(Level.FINE);
			logger.addHandler(hnd);
			logger.setLevel(Level.WARNING);
			Logger rootLogger = logger.getParent();
			for (Handler h : rootLogger.getHandlers()) {
				// para evitar mensajes repetidos
				h.setLevel(Level.OFF);
			}
		}
	}

	public void addNode(int node) {
		if (nodos.indexOf(node) != -1) {
			logger.info("El nodo " + node + " ya se encontraba en el grafo");
		} else {
			nodos.add(node);
			logger.fine("se añadió al grafo el nodo: " + node);
		}
	}

	public void addEdge(int node1, int node2) {
		boolean esta1 = nodos.indexOf(node1) != -1, esta2 = nodos.indexOf(node2) != -1;
		Edge arco;
		boolean encontre = false;
		Iterator<Edge> i = arcos.iterator();
		if (esta1 && esta2) {
			while (!encontre && i.hasNext()) {
				arco = i.next();
				encontre = arco.getNodo1() == node1 && arco.getNodo2() == node2;
			}
			if (!encontre) {
				arco = new Edge(node1, node2);
				arcos.add(arco);
				logger.fine("se añadio al grafo el arco (" + node1 + "," + node2 + ")");
			} else {
				logger.info("el arco ya se encontraba en el grafo");
			}
		} else {
			if (esta1)
				logger.warning("no se añadió ningun arco ya que el nodo " + node2 + " no pertenece al grafo");
			else if (esta2)
				logger.warning("no se añadió ningun arco ya que el nodo " + node1 + " no pertenece al grafo");
			else
				logger.warning("no se añadió ningun arco ya que los nodos no pertenecen al grafo");
		}
	}

	public void removeNode(int node) {
		Integer nodo = node; Edge arco;
		Iterator<Edge> i = arcos.iterator();
		if (nodos.indexOf(node) != -1) {
			while (i.hasNext()) {
				arco=i.next();
				if (arco.getNodo1() == node || arco.getNodo2() == node) {
					i.remove();
					logger.info("se elimino el arco (" + arco.getNodo1() + "," +arco.getNodo2() + ")");
				}
			}
			nodos.remove(nodo);
			logger.fine("se elimino el nodo " + node);
		} else
			logger.warning("el nodo " + node + " no se encontraba en el grafo");
	}

	public void removeEdge(int node1, int node2) {
		Iterator<Edge> i = arcos.iterator();
		Edge arco;
		boolean encontre = false;
		while (!encontre && i.hasNext()) {
			arco = i.next();
			if (arco.getNodo1() == node1 && arco.getNodo2() == node2) {
				encontre = true;
				arcos.remove(arco);
				logger.fine("se elimino el arco (" + node1 + "," + node2 + ")");
			}
		}
		if (!encontre)
			logger.warning("el arco(" + node1 + "," + node2 + ") no pertenecia al grafo");
	}
}
