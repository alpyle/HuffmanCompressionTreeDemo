/**
 *Creates an optimal Huffman tree, and demonstrates using it to encode and decode a string.
 * @author Adam Pyle
 * Project name: huffman tree
 * Filename:alpyle_prj.java
 * Student Name:Adam Pyle
 * Date:03/31/2014
 * Tasks
 * 1. create a huffman tree from given symbols and associated weights
 * 2. print the resulting tree
 * 3. encode the word tea, print the resulting code, and decode it back to the word tea
 * Computing requirements:
 * Language: Java
 * Platform: Linux
 * I have abided by the uncg academic integrity policy for this and all assignments.
 */
class HuffmanTree
{
   int[] weights={10,15,12,3,4,13,1};
   char[] symbols={'a','e','i','s','t','_','n'};
    //the root of the tree
   HuffNode RootNode;
   
   //default constructor. simply makes the tree.
   public HuffmanTree(){
      RootNode= createTree();
   }
   
   /**
    * creates an optimal tree with the specified weights and corresponding symbols.
    * @return the root node of the constructed tree.
    */
   private HuffNode createTree()
   {
       int rootNodeLocation=0;
       int nodesLeft=7;
       //holds the nodes while the tree is being built
       HuffNode[] forest=new HuffNode[7];
       //keep on combining the lowest weighted nodes, placing the smallest on the right and the second smallest on the left of a new node.
       for(int i=0;i<nodesLeft;i++)
       {
           forest[i]=new HuffNode(symbols[i], weights[i], null, null, null);
       }
       do
       {
           int smallest=0;
           int secondSmallest=0;
           if(nodesLeft==2)
           {
               for(int i=0;i<forest.length;i++)
               {
                   if(forest[i]!=null)
                   {
                       secondSmallest=i;
                       for(int i2=i;i2<forest.length;i2++)
                       {
                           if (forest[i2]!=null)
                           {
                               secondSmallest=i2;
                           }
                       }
                       break;
                   }
               }
               if(forest[smallest].weight>forest[secondSmallest].weight)
               {
                   int temp=smallest;
                   smallest=secondSmallest;
                   secondSmallest=temp;
               }
           }else{
               
                for(int i=0;i<forest.length;i++)
                {
                    if (forest[i]!=null)
                    {
                        if (forest[smallest].weight>forest[i].weight)
                        {
                            smallest=i;
                        }
                    }
                }
                for(int i=0;i<forest.length;i++)
                {
                    if (i!=smallest && forest[i]!=null)
                    {
                        if (forest[secondSmallest].weight>forest[i].weight)
                        {
                            secondSmallest=i;
                        }
                    }
                }
            }
           //combine the nodes
           HuffNode temp=new HuffNode('-', forest[smallest].weight+forest[secondSmallest].weight, forest[secondSmallest], forest[smallest], null);
           if(smallest>secondSmallest)
           {
               int temporary=smallest;
               smallest=secondSmallest;
               secondSmallest=temporary;
           }
           forest[smallest].parent=temp;
           forest[secondSmallest].parent=temp;
           forest[smallest]=temp;
           forest[secondSmallest]=null;
           nodesLeft-=1;
       }while(nodesLeft>1);
       //locate the root node and return it.
       for(int i=0;i<forest.length;i++)
       {
           if(forest[i]!=null)
           {
               rootNodeLocation=i;
           }
       }
       return forest[rootNodeLocation];
   }
   
   public void PrintTree(HuffNode toPrint)
   {
       //node is not terminal
       if(toPrint.symbol=='-')
       {
           HuffNode curr=toPrint;
           //print out dashes to indicate depth
            while(curr.parent!=null)
            {
                curr=curr.parent;
                System.out.print("-");
            }
            //print the weight
            System.out.println(toPrint.weight);
       //node is terminal
       }else{
           //print out dashes to indicate level, weight, then the symbol contained in the node.
           HuffNode curr=toPrint;
            while(curr.parent!=null)
            {
                curr=curr.parent;
                System.out.print("-");
            }
            System.out.println(toPrint.weight + ":" + toPrint.symbol);
       }
       //repeat process on the left node, if applicable
       if(toPrint.left!=null)
       {
           PrintTree(toPrint.left);
       }
       //repeat process on the right node, if applicable
       if(toPrint.right!=null)
       {
           PrintTree(toPrint.right);
       }
   }
   /**
    * wrapper method for encodeChar, enabling the encoding of an entire string instead.
    * @param Input the string to encode
    * @return the series of 1's and 0's specifying the encoded text
    */
   public String EncodeString(String Input)
   {
       String resultToReturn="";
       char[] InputChars=Input.toCharArray();
       //encode each character in input
       for(int i=0;i<InputChars.length;i++)
       {
           //add encoded character to result string
           resultToReturn=resultToReturn+EncodeChar(InputChars[i], RootNode);
       }
       return resultToReturn;
       
   }
   /**
    * encodes a single character
    * @param inputSym the character to encode
    * @param curr the root node of the Huffman tree to be used in the encoding process.
    * @return the code for the character in question. 
    */
   public String EncodeChar(char inputSym, HuffNode curr)
    {
        String ReturnVal=null;
        //current node does not contain the symbol, so first check the left node, then the right.
        if(curr.symbol!=inputSym)
        {
            if(curr.left!=null)
            {
                ReturnVal=EncodeChar(inputSym,curr.left);
            }
            if(ReturnVal==null && curr.right!=null)
            {
                ReturnVal=EncodeChar(inputSym,curr.right);
            }
        //current node does contain the symbol, so retrace the path to get to it and return the resulting code
        }else{
            HuffNode temp=curr;
            StringBuilder toReturn=new StringBuilder();
            while(temp.parent!=null)
            {
                if (temp.parent.left==temp)
                {
                    //went left to get here
                    toReturn.insert(0, "0");
                }else{
                    //went right to get here
                    toReturn.insert(0,"1");
                }
                temp=temp.parent;
            }
            return toReturn.toString();
        }
        return ReturnVal;
    }
   
   /**
    * takes a previously encoded sequence of 1's and 0's, and decodes it into the original text
    * @param Input the string, produced by method @encodeString, to decode.
    * @return the original text corresponding to @Input
    */
   public String decodeString(String Input)
   {
       HuffNode curr=RootNode;
       int symbolPOS=0;
       String SymbolName=null;
       boolean endofInputFLAG=false;
       for(int i=0;i<Input.length();i++)
       {
           //not a leaf, so continue according to the input string
           if(curr.symbol=='-')
           {
                if(Input.charAt(i)=='0')
                {
                    curr=curr.left;
                    
                }else{
                    curr=curr.right;
                }
                //last character in the input string, so we have to continue.
                if(i==Input.length()-1)
                {
                    SymbolName=curr.symbol.toString();
                    endofInputFLAG=true;
                }
           //we've hit a leaf, so indicate the symbol we've found
           }else{
               SymbolName=curr.symbol.toString();
               symbolPOS=i;
               break;
           }
           
       }
       if(endofInputFLAG)
       {
           //we have hit the last character in the string, so just return it
           return SymbolName;
       }else{
           //we've found a character, but there is still more to decode, so return the character and continue decoding with the remaining input.
           return SymbolName+decodeString(Input.substring(symbolPOS));
       }
       
    }
}



