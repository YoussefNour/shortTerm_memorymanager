
package memorymanager;

import java.util.*;
public class EnhancedNode {
    protected int value;
    protected boolean ref=true;
    Random rand = new Random();
    protected boolean mod=rand.nextBoolean();

    public EnhancedNode(int value) {
        this.value = value;
    }
    
}
