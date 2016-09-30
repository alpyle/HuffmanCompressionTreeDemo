class HuffNode
{
    public Character symbol;
    public int weight;
    
    HuffNode left;
    HuffNode right;
    HuffNode parent;
    
    public HuffNode( Character data, int weight, HuffNode left, HuffNode right,HuffNode parent)
    {
        this.symbol=data;
        this.weight=weight;
        this.left=left;
        this.right=right;
        this.parent=parent;
    }
}