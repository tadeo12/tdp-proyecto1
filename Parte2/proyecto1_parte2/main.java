package proyecto1_parte2;

public class main {

	public static void main(String[] args) {
		Graph grafo= new Graph();
		
		//addNode
		for(int i = 0;i<5;i++){
			grafo.addNode(i);
		}
		grafo.addNode(2);//ya se encuentra
		
		//addEdge
		grafo.addEdge(0, 0);
		grafo.addEdge(0, 1);
		grafo.addEdge(0, 2);
		grafo.addEdge(2, 4);
		grafo.addEdge(5, 1);//el nodo 5 no pertenece al grafo
		grafo.addEdge(0, 1);//arco ya existente
		
		//removeNode
		grafo.removeNode(-1);// nodo no existente
		grafo.removeNode(3);
		grafo.removeNode(2);//nodo con arcos asociados
		
		//removeEdge
		grafo.removeEdge(0, 0);
		grafo.removeEdge(2,4);//el nodo 2 ya fue eliminado (al remover el nodo 2)
		grafo.removeEdge(0, 4);//arco no existente
		
		
		
		
	}

}
