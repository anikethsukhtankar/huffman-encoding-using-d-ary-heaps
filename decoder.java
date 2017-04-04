import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

class HuffmanNode
{    
    HuffmanNode left, right;
    int value;
    boolean isLeaf;

    /* Constructor */
    public HuffmanNode()
    {
        left = null;
        right = null;
        value = -1;
        isLeaf = false;
    }
    /* Constructor */
    public HuffmanNode(int n)
    {
        left = null;
        right = null;
        value = n;
        isLeaf = false;
    }
    }

class HuffmanTree{
	HuffmanNode root;
	
	HuffmanTree(){
		this.root=null;
	}
}

public class decoder {

	public static HuffmanNode createHuffmanTree(HuffmanNode node, String codePair, int currentPos, int codeLength){
        if(currentPos!=codeLength){
            
        	if(codePair.split(" ")[1].charAt(currentPos)=='0'){
                
            	if(node.left==null){
                	
                    HuffmanNode tmp = new HuffmanNode();
                    node.left = createHuffmanTree(tmp,codePair, currentPos+1, codeLength);
                
                }else{
                	
                    node.left= createHuffmanTree(node.left,codePair, currentPos+1, codeLength);
                
                }
            }
            else if(codePair.split(" ")[1].charAt(currentPos)=='1'){
            	
            	if(node.right==null){
                    
            		HuffmanNode tmp = new HuffmanNode();
                    node.right = createHuffmanTree(tmp,codePair, currentPos+1, codeLength);
                
            	}else{
                    
            		node.right= createHuffmanTree(node.right,codePair, currentPos+1, codeLength);
                
            	}
            }
        }
        else{
            node.value = Integer.parseInt(codePair.split(" ")[0]);
            node.isLeaf = true;
        }
        return node;
    }
	
	public static void inOrder(HuffmanNode node) {
	  if(node !=  null) {
	   inOrder(node.left);
	   //Visit the node by Printing the node data  
	   System.out.printf("%d ",node.value);
	   inOrder(node.right);
	  }
	 }
	
	public static void preOrder(HuffmanNode node) {
		  if(node !=  null) {
		   System.out.printf("%d ",node.value);
		   preOrder(node.left);
		   preOrder(node.right);
		  }
		 }
	
	public static void postOrder(HuffmanNode node) {
		  if(node !=  null) {
		   postOrder(node.left);
		   postOrder(node.right);
		   System.out.printf("%d ",node.value);
		  }
		 }
	
	public static void main(String[] args) {
		//File codetable = new File("D:\\workspace\\Huffman Encoding\\src\\code_table.txt");
		File codetable = new File(args[1]);
        //File encoded = new File("D:\\workspace\\Huffman Encoding\\src\\encoded.bin");
		File encoded = new File(args[0]);
        //File decoded = new File("D:\\workspace\\Huffman Encoding\\src\\decoded.txt");
        File decoded = new File("decoded.txt");
        HuffmanNode root = new HuffmanNode();
        HuffmanTree tree = new HuffmanTree();
        try{
	        BufferedReader br = new BufferedReader(new FileReader(codetable));
	        String codeline;
	        while( (codeline = br.readLine()) != null){
	            int l = codeline.split(" ")[1].length();
	            tree.root = createHuffmanTree(root, codeline, 0, l);
	            
	        }
	        /*
	        inOrder(tree.root);
	        System.out.println("\n");
	        preOrder(tree.root);
	        System.out.println("\n");
	        postOrder(tree.root);
	        System.out.println("\n");
	        */
	        br.close();
	        
        }
        catch (Exception e){//Catch exception if any
      	  System.err.println("There was a problem opening and processing the code_table file. Error: " + e.getMessage());
      	  }
       try{ 
	        InputStream input = new BufferedInputStream(new FileInputStream(encoded));
	        Writer bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(decoded)));
	        //FileWriter fw = new FileWriter(decoded);
	        //BufferedWriter bw = new BufferedWriter(fw);
	        byte[] result = new byte[4096];
	        HuffmanNode ptr = tree.root;
	        int l=0;
	        while((l = input.read(result)) != -1){
	        	for(int i=0;i<l;i++){
	        			String s=String.format("%8s", Integer.toBinaryString(result[i] & 0xFF)).replace(' ', '0');
	        			//System.out.println(s);
	        			for(char ch: s.toCharArray()){
	        				//System.out.println(ch);
				        		if(ch=='0'){
				        			ptr = ptr.left;
				        			if(ptr.isLeaf == true){
				        				bw.write(String.valueOf(ptr.value)+"\n");
				        				//System.out.print(ptr.value+"  ");
				        				ptr = tree.root;
				        			}
				        		}
				        		else{
				        			ptr = ptr.right;
				        			if(ptr.isLeaf == true){
				        				bw.write(String.valueOf(ptr.value)+"\n");
				        				//System.out.print(ptr.value+"  ");
				        				ptr = tree.root;
				        			}
				        		}
	        			}
	        	}
	        }
	        input.close();
	        bw.close();
       }
       catch (Exception e){//Catch exception if any
       	  System.err.println("There was a problem opening and processing the encoded.bin file. Error: " + e.getMessage());
       	  }


	}

}
