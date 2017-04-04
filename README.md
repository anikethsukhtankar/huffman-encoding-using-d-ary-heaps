# Huffman-Encoding
Huffman coding is one of the most popular technique for removing coding redundancy. It has been used in various compression applications, including image compression. It is a simple, yet elegant, compression technique that can supplement other compression algorithms. It utilizes the statistical property of symbols in the source stream, and then produces respective codes for these symbols. These codes are of variable length using integral number of bits. In Huffman coding, you assign shorter codes to symbols that occur more frequently and longer codes to those that occur less frequently.

# Building a Huffman Tree 
The compression process is based on building a binary tree that holds all symbols in the source at its leaf nodes. The code word for each symbol is obtained traversing the binary tree from its root to the leaf corresponding to the symbol. Symbols with the highest frequencies end up at the top of the tree, and result in the shortest codes [4]. The tree is built by going through the following steps: 
1. Each of the symbols is laid out as leaf node which is going to be connected. The symbols are ranked according to their frequency, which represents the probabilities of their occurrences in the source. 
2. Two nodes with the lowest frequencies are combined to form a new node, which is a parent node of these two nodes. This parent node is then considered as a representative of the two nodes with a frequency equal to the sum of the frequencies of two nodes. Moreover, one of the children is assigned a "0" and the other is assigned a “1”. 
3. Nodes are then successively combined as above until a binary tree containing all of nodes is created. 
4. The code representing a given symbol can be determined by going from the root of the tree to the leaf node representing the symbol. The accumulation of symbol "0" and "1" is the code of that symbol. 
By using this procedure, the symbols are naturally assigned codes that reflect probability distribution. Highly probable symbols will be given short codes, and improbable symbols will have long codes. Therefore, the average code length will be reduced. If the statistic of symbols is very biased to some symbols, the reduction will be very significant [3]. For example, imagine we have a text file that uses only five characters (A, B, C, D, E). Before we can assign bit patterns to each character, we assign each character a weight based on its frequency of use. The algorithm is optimal in the sense that the average number of bits required to represent the source symbols is a minimum provided the prefix condition is met

# Encoder
The technique works by creating a binary tree of nodes. These can be stored in a regular array, the size of which depends on the number of symbols, n. A node can be either a leaf node or an internal node. Initially, all nodes are leaf nodes, which contain the symbol itself, the weight (frequency of appearance) of the symbol and optionally, a link to a parent node which makes it easy to read the code (in reverse) starting from a leaf node. Internal nodes contain a weight, links to two child nodes and an optional link to a parent node. As a common convention, bit '0' represents following the left child and bit '1' represents following the right child. A finished tree has up to n leaf nodes and n-1 internal nodes. A Huffman tree that omits unused symbols produces the most optimal code lengths.
The process begins with the leaf nodes containing the probabilities of the symbol they represent. Then, the process takes the two nodes with smallest probability, and creates a new internal node having these two nodes has children. The weight of the new node is set to the sum of the weight of the children. We then apply the process again, on the new internal node and on the remaining nodes (i.e., we exclude the two leaf nodes), we repeat this process until only one node remains, which is the root of the Huffman tree.
A Huffman encoding can be computed by first creating a tree of nodes:
 
1. Create a leaf node for each symbol and add it to the priority queue.
2. While there is more than one node in the queue:
a. Remove the node of highest priority (lowest probability) twice to get two nodes.
b. Create a new internal node with these two nodes as children and with probability equal to the        sum of the two nodes' probabilities.
c. Add the new node to the queue.
3. The remaining node is the root node and the tree is complete.
4. Traverse the constructed binary tree from root to leaves assigning and accumulating a '0' for one       branch and a '1' for the other at each node. The accumulated zeros and ones at each leaf constitute a Huffman encoding for those symbols and weights:

Since Four Way Heap data structures require O (log n) time per insertion, and a tree with n leaves has 2n−1 nodes, this algorithm operates in O(n log n) time, where n is the number of symbols. In many cases, time complexity is not very important in the choice of algorithm here, since n here is the number of symbols in the alphabet, which is typically a very small number (compared to the length of the message to be encoded); whereas complexity analysis concerns the behavior when n grows to be very large.

# Decoder
The process of decoding is simply a matter of converting the stream of prefix codes to individual byte values, usually by moving through the Huffman tree node by node as each bit is read from the input stream (reaching a leaf node necessarily terminates the search for that byte value). Before this can take place, however, the Huffman tree must be reconstructed from the data received in the form of code table.
1. Create a binary Huffman tree. Construct the Huffman tree from the code table by reading in the code for each symbol and traversing the tree while adding nodes. A left move is made for every “0” that is encountered and a right move for every “1” encountered until the code runs out. The symbol corresponding to this Huffman code is stored in the leaf terminating the path.
2. Read in the encoded binary file into a byte array 4096 elements at a time. While there are more elements to be processed:
a. Convert the corresponding byte array to String and process one character at a time.
b. Starting with the first bit in the stream, we then use successive bits from the stream to determine whether to go left or right in the decoding tree. 
c. When we reach a leaf of the tree, we've decoded a character, so we place that character onto the 	(uncompressed) output stream. The next bit in the input stream is the first bit of the next character.
3. Once the entire string in encoded binary is processed, the process is complete and the output will be generated.

Complexity of Decoder considering Reconstruction of Huffman tree (Size of code table * Log n) is part of Pre-Processing: No. of symbols in the decoded output (k) * Log n (Height of the Generated Huffman Tree)

