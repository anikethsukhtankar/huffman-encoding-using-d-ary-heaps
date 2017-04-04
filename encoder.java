import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;

class Bin4WayHeap {
	private CodeTree[] MinHeap;
	private int heapSize;
	private int order; //Order of the tree. Change to 2 for Binary Tree
	
	public Bin4WayHeap() {
		this.order = 4;
		this.MinHeap = new CodeTree[100];
		this.heapSize = 0;		
	}
	
	public int size() {
		return heapSize;
	}
	
	public void insert(CodeTree newElem) {
		if (heapSize==0) {
			MinHeap[0] = newElem;
			heapSize++;
			return;
		}
		// Array Expansion		
		if (heapSize == MinHeap.length) {
			CodeTree[] newArray = new CodeTree[heapSize*2];
			for(int tmp=0; tmp<heapSize; tmp=tmp+1) {
				newArray[tmp] = MinHeap[tmp];
			}			
			MinHeap = newArray;
		}
		// Find a spot to insert new element		
		int tmp = heapSize;
		for(; MinHeap[(tmp-1)/order].frequency > newElem.frequency; tmp=(tmp-1)/order) {
			// Assignment to be done at the top
			if (tmp==0) break;			
			// Heapify Down
			MinHeap[tmp] = MinHeap[(tmp-1)/order];
		}
		MinHeap[tmp] = newElem;
		heapSize++;
		
	}
	
	public CodeTree removeMin() {
		if (heapSize == 0) { throw new java.lang.IllegalStateException("Empty Heap"); }	
		CodeTree minElem = MinHeap[0];		
		CodeTree swapElem = MinHeap[heapSize-1];
		int minChild;
		int tmp=0;
		for(; (tmp*order)+1 < heapSize; tmp=minChild) {
			// Initialize smallest child to first one
			minChild = (tmp*order)+1;
			// No children
			if (minChild > heapSize) { break; }
			// Find smallest child
			int j=1, currentSmallestChild = minChild;
			for(; j<order; j++) {
				if (minChild+j == heapSize) break;
				if(MinHeap[currentSmallestChild].frequency > MinHeap[minChild+j].frequency)
					currentSmallestChild = minChild+j;
			}
			minChild = currentSmallestChild;
			// Heapify Up
			if (swapElem.frequency > MinHeap[minChild].frequency) {
				MinHeap[tmp] = MinHeap[minChild];
			} else {
				break;
			}
		}
		
		MinHeap[tmp] = swapElem;
		heapSize--;
		return minElem;
	}
}

/* Class PairNode */
class PairNode
{
	CodeTree element;
    PairNode leftChild;
    PairNode rightNeighbour;
    PairNode leftNeighbour;
 
    /* Constructor */
    public PairNode(CodeTree x)
    {
        element = x;
        leftChild = null;
        rightNeighbour = null;
        leftNeighbour = null;
    }
}
 
/* Class PairHeap */
class PairHeap
{
    private PairNode topElem; 
    private PairNode [ ] minHeap = new PairNode[ 5 ];
    /* Constructor */
    public PairHeap( )
    {
        topElem = null;
      }
    public int size( )
    {
    	if(topElem.leftChild!=null)
    		return 2;
    	else
    		return -1;
    }
    /* Check if heap is empty */
    public boolean isEmpty() 
    {
        return topElem == null;
    }
    /* Make heap logically empty */ 
    public void makeEmpty( )
    {
        topElem = null;
    }
    /* Function to insert data */
    public PairNode insert(CodeTree x)
    {
        PairNode newNode = new PairNode( x );
        if (topElem == null)
            topElem = newNode;
        else
            topElem = linkTwoNodes(topElem, newNode);
        return newNode;
    }
    /* Function linkTwoNodes */
    private PairNode linkTwoNodes(PairNode a, PairNode b)
    {
        if (b == null)
            return a;
 
        if (b.element.frequency > a.element.frequency)
        {
        	/* Attach b as leftmost child of a */
            b.leftNeighbour = a;
            a.rightNeighbour = b.rightNeighbour;
            if (a.rightNeighbour != null)
                a.rightNeighbour.leftNeighbour = a;
            b.rightNeighbour = a.leftChild;
            if (b.rightNeighbour != null)
                b.rightNeighbour.leftNeighbour = b;
            a.leftChild = b;
            return a;
            
        }
        else
        {
        	/* Attach a as leftmost child of b */
            b.leftNeighbour = a.leftNeighbour;
            a.leftNeighbour = b;
            a.rightNeighbour = b.leftChild;
            if (a.rightNeighbour != null)
                a.rightNeighbour.leftNeighbour = a;
            b.leftChild = a;
            return b;
        }
    }
    private PairNode mergeNeighbours(PairNode aNeighbour)
    {
        if( aNeighbour.rightNeighbour == null )
            return aNeighbour;
        /* Save the subtrees in array */
        int numNeighbours = 0;
        for ( ; aNeighbour != null; numNeighbours++)
        {
            minHeap = increaseCapacity( minHeap, numNeighbours );
            minHeap[ numNeighbours ] = aNeighbour;
            aNeighbour.leftNeighbour.rightNeighbour = null;  
            aNeighbour = aNeighbour.rightNeighbour;
        }
        minHeap = increaseCapacity( minHeap, numNeighbours );
        minHeap[ numNeighbours ] = null;
        /* Combine subtrees two at a time, going left to right */
        int i = 0;
        for ( ; i + 1 < numNeighbours; i = i + 2)
            minHeap[ i ] = linkTwoNodes(minHeap[i], minHeap[i + 1]);
        int j = i - 2;
        /* j has the result of last linkTwoNodes */
        /* If an odd number of trees, get the last one */
        if (j == numNeighbours - 3)
            minHeap[ j ] = linkTwoNodes( minHeap[ j ], minHeap[ j + 2 ] );
        /* Now go right to left, merging last tree with */
        /* next to last. The result becomes the new last */
        for ( ; j >= 2; j = j - 2)
            minHeap[j - 2] = linkTwoNodes(minHeap[j-2], minHeap[j]);
        return minHeap[0];
    }
    private PairNode[] increaseCapacity(PairNode [ ] array, int index)
    {
        if (index == array.length)
        {
            PairNode [ ] oldArray = array;
            array = new PairNode[index * 2];
            for( int i = 0; i < index; i++ )
                array[i] = oldArray[i];
        }
        return array;
    }
    /* Delete min element */
    public CodeTree removeMin( )
    {
        if (isEmpty( ) )
            System.out.println("Empty Huffman Tree");;
        CodeTree x = topElem.element;
        if (topElem.leftChild == null)
            topElem = null;
        else
            topElem = mergeNeighbours( topElem.leftChild );
        return x;
    }
    
}
 
abstract class CodeTree implements Comparable<CodeTree> {
    public final int frequency; // the frequency of this tree
    public CodeTree(int freqValue) { frequency = freqValue; }
    // compares on the frequency
    public int compareTo(CodeTree x) {
        return frequency - x.frequency;
    }
}
 
class TreeLeaf extends CodeTree {
    public final int value; // the character stored at this leaf
 
    public TreeLeaf(int freq, int val) {
        super(freq);
        value = val;
    }
}
 
class InternalNode extends CodeTree {
    public final CodeTree leftChild, rightChild; // subtrees
    
    public InternalNode(CodeTree left, CodeTree right) {
        super(left.frequency + right.frequency);
        leftChild = left;
        rightChild = right;
    }
}
 
public class encoder {
	public static HashMap<Integer, String> hmap = new HashMap<Integer, String>();
    public static CodeTree buildTree(int[] charFreqs) {
        Bin4WayHeap hufftree = new Bin4WayHeap();
        //PairHeap hufftree = new PairHeap(); Use This For Pairing Heap Implementation

        for (int tmp = 0; tmp < charFreqs.length; tmp++)
            if (charFreqs[tmp] > 0)
                hufftree.insert(new TreeLeaf(charFreqs[tmp], (int)tmp));
 
        assert hufftree.size() > 0;

        while (hufftree.size() > 1) {
            CodeTree a = hufftree.removeMin();
            CodeTree b = hufftree.removeMin();
            hufftree.insert(new InternalNode(a, b));
        }
        return hufftree.removeMin();
    }
 
    public static void codePrinter(CodeTree hufftree, StringBuffer codeBuffer, Writer writer) {
        assert hufftree != null;
        if (hufftree instanceof TreeLeaf) {
            TreeLeaf leaf = (TreeLeaf)hufftree;
            // write out character and code for this leaf which the value in codeBuffer
            //System.out.println(leaf.value + "\t" + leaf.frequency + "\t" + codeBuffer);
            hmap.put(Integer.valueOf(leaf.value), String.valueOf(codeBuffer).trim());
            try {
            	writer.write(leaf.value + " "+ codeBuffer+System.lineSeparator());
            }
            catch (Exception e){//Catch exception if any
            	System.err.println("There was a problem opening and processing the file. Error: " + e.getMessage());
      	  	}
            
        } else if (hufftree instanceof InternalNode) {
            InternalNode node = (InternalNode)hufftree;
            // go left
            codeBuffer.append('0');
            codePrinter(node.leftChild, codeBuffer, writer);
            codeBuffer.deleteCharAt(codeBuffer.length()-1);
            // go right
            codeBuffer.append('1');
            codePrinter(node.rightChild, codeBuffer, writer);
            codeBuffer.deleteCharAt(codeBuffer.length()-1);
        }
    }
 
    public static void main(String[] args) {
    	int j = 0;
    	String s = new String();
        int[] charFreqs = new int[1000000];
        for(int tmp=0;tmp<1000000;tmp=tmp+1){
        	charFreqs[tmp] = 0;
        }
        try{
        	  //String filename = "D:\\workspace\\Huffman Encoding\\src\\input.txt";
        	  //String filename = "D:\\workspace\\Huffman Encoding\\src\\sample_input_large.txt";
        	  //String filename = "D:\\workspace\\Huffman Encoding\\src\\sample_input_small.txt";
        	  String filename = args[0];
        	  BufferedReader reader = new BufferedReader(new FileReader(filename));
        	  String strLine;
        	  //Read File Line By Line
        	  while ((strLine = reader.readLine()) != null && !strLine.isEmpty())   {
        		  		strLine = strLine.trim();
        		  		j=Integer.valueOf(strLine);
        		  		//System.out.println("Line Data"+j);
        		  		charFreqs[j]++;
        	  }
        	  //Close the input stream
        	  reader.close();
        	    }catch (Exception e){//Catch exception if any
        	  System.err.println("There was a problem opening and processing the input file. Error: " + e.getMessage());
        	  }
 
        // build tree
        CodeTree hufftree = buildTree(charFreqs);
        Writer writer =null;
        try{
        	//String filename = "D:\\workspace\\Huffman Encoding\\src\\code_table.txt";
        	String filename = "code_table.txt";
        	writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), "utf-8"));
        }
        catch (Exception e){//Catch exception if any
      	  System.err.println("There was a problem opening and processing the code table. Error: " + e.getMessage());
      	 }
		
        //HashMap<Integer, String> hmap = new HashMap<Integer, String>();
        // print out results
        //System.out.println("Symbol\tFrequency\tHuffman Code");
        codePrinter(hufftree, new StringBuffer(), writer);
        try {writer.close();} catch (Exception ex) {/*ignore*/}
        
        try{
        	//String filepath = "D:\\workspace\\Huffman Encoding\\src\\input.txt";
            //String filepath = "D:\\workspace\\Huffman Encoding\\src\\sample_input_large.txt";
            //String filepath = "D:\\workspace\\Huffman Encoding\\src\\sample_input_small.txt";
        	String filepath = args[0];
            //String outfilepath = "D:\\workspace\\Huffman Encoding\\src\\encoded.bin";
        	String outfilepath = "encoded.bin";
            BufferedReader encode = new BufferedReader(new FileReader(filepath));
            FileOutputStream encodingwrite = new FileOutputStream(outfilepath);
            String draw;
            //Read File Line By Line
            while ((draw = encode.readLine()) != null && !draw.isEmpty())   {
                      draw = draw.trim();
                      j=Integer.valueOf(draw);
                      s=s+hmap.get(j);
                      if(s.length() % 8 == 0){
                          //System.out.println(s);
                      for (int x = 0; x < s.length(); x += 8) {
                          String byteString = s.substring(x, x + 8); // grab a byte
                          int parsedByte = Integer.parseInt(byteString, 2);
                          encodingwrite.write(parsedByte); // write a byte
                        }
                      s = "";
                      }
                      //encodingwrite.write(hmap.get(j));
            }
            
            while (s.length() % 8 != 0)
                s += "0"; // lets add some extra bits until we have full bytes
            for (int x = 0; x < s.length(); x += 8) {
              String byteString = s.substring(x, x + 8); // grab a byte
              int parsedByte = Integer.parseInt(byteString, 2);
              encodingwrite.write(parsedByte); // write a byte
            }
          //Close the input and output stream
            encode.close();
            encodingwrite.close();
      	  }
        catch (Exception e){
        		//Catch exception if any
      	  		System.err.println("There was a problem opening and processing the encoded file. Error: " + e.getMessage());
      	  }
          
    }
}