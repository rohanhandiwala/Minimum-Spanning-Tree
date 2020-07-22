package apps;

import structures.*;
import java.util.ArrayList;

public class MST {
	
	/**
	 * Initializes the algorithm by building single-vertex partial trees
	 * 
	 * @param graph Graph for which the MST is to be found
	 * @return The initial partial tree list
	 */
	public static PartialTreeList initialize(Graph graph) {
	
		 PartialTreeList treelist = new PartialTreeList();
		   Vertex[] vertex =  graph.vertices;
		   PartialTree Tree;
		   boolean[] visited = new boolean[vertex.length];
		   PartialTree.Arc arc = null;
		   int count = 0;
		   for(int j =0 ; j < vertex.length; j++)
		   {
		       Vertex.Neighbor  nbr = vertex[j].neighbors;
		       Tree = new PartialTree(vertex[j]);
		       visited[j] = true;
		       
		       MinHeap<PartialTree.Arc> PartialTree = Tree.getArcs(); 
		      
		       while(nbr!= null)
		       {
		       arc = new PartialTree.Arc(vertex[j], nbr.vertex,nbr.weight);
		       

		       PartialTree.insert(arc);
		       PartialTree.siftDown(count);
		       nbr = nbr.next;
		  
		       }
		       if(visited[j] == true)
		       {
		           treelist.append(Tree);
		       }
		       count++;

		      
		   }
		  
		       return treelist;
		  
		}

	/**
	 * Executes the algorithm on a graph, starting with the initial partial tree list
	 * 
	 * @param ptlist Initial partial tree list
	 * @return Array list of all arcs that are in the MST - sequence of arcs is irrelevant
	 */
	public static ArrayList<PartialTree.Arc> execute(PartialTreeList ptlist) {
	
	
		ArrayList<PartialTree.Arc> tree = new ArrayList<PartialTree.Arc>();
		
		while(ptlist.size() > 1){
			PartialTree parTree = ptlist.remove();
			PartialTree.Arc arcMin= parTree.getArcs().deleteMin();
			
			Vertex vertex = arcMin.v2;
			
			while(!parTree.getArcs().isEmpty()){
				
				if (!parTree.getRoot().equals(vertex.getRoot())){
					break;
					
				} else {
					arcMin = parTree.getArcs().deleteMin();
					vertex = arcMin.v2;
					
				}
			}
			
			
			PartialTree ptl2 = ptlist.removeTreeContaining(vertex);
			parTree.merge(ptl2);
			ptlist.append(parTree);
			tree.add(arcMin);
		}
	
		
		return tree;
				
		
	}
}