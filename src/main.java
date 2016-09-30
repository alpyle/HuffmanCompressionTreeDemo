public class main {
    public static void main(String[] args)
    {
        String EncodedBits;
        HuffmanTree willItCompile=new HuffmanTree();
        willItCompile.PrintTree(willItCompile.RootNode);
        EncodedBits=willItCompile.EncodeString("tea");
        System.out.println("The encoded word, tea: " + EncodedBits);
        System.out.println("The decoded word, from last step:" + willItCompile.decodeString(EncodedBits));
    }
}