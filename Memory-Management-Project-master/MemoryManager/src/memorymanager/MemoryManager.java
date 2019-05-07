package memorymanager;

import java.util.*;

public class MemoryManager {

    public static void main(String[] args) {
          Random rand=new Random();
          
        int nframes = 5;
        int size=71;
        int[] req=new int[size];
        for(int i=0;i<size;i++)
        {
        req[i]=rand.nextInt(100);
        }
        System.out.println("the page-reference string size is  "+size);
        System.out.println("The Number Of Page Frames =  "+nframes);
        FIFO(nframes,req);
        optimal(nframes, req);
        LRU(nframes,req);
        LFU(nframes, req);
        SecondChance(nframes, req);
        Enhanced(nframes, req);
    }

    public static void FIFO(int nframes, int[] req) {
        int npagefault = 0;
        int counter = 0;
        Queue<Integer> memory = new LinkedList<>();
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("FIFO Algorithm");
        System.out.println("");
        for (int i = 0; i < req.length; i++) {
            System.out.println("");
            System.out.println("The Request is  "+req[i]);
            if (!(memory.contains(req[i])) && (counter < nframes)) {
                memory.add(req[i]);
                counter++;
                npagefault++;
            } else if (!(memory.contains(req[i])) && (counter == nframes)) {
                memory.remove();
                memory.add(req[i]);
                npagefault++;
            }

            for (int j = 0; j < memory.size(); j++) {
                System.out.print(memory.toArray()[j] + " ");
            }
            System.out.println();
        }
        System.out.println("number of page faults : " + npagefault);
    }

    public static void optimal(int nframes, int[] req) {
         System.out.println("-------------------------------------------------------------------------");
        System.out.println("Optimal Algorithm");
        System.out.println("");
        int npagefault = 0;
        ArrayList<Integer> memory = new ArrayList<>();
        int counter = 0;
        int[] met = new int[nframes];
        int c = 0;
        for (int i = 0; i < req.length; i++) {
            System.out.println("");
            System.out.println("The Request is  "+req[i]);
            if (!(memory.contains(req[i])) && (counter < nframes)) {
                memory.add(req[i]);
                counter++;
                npagefault++;
            } else if (!(memory.contains(req[i])) && (counter == nframes)) {
                for (int j = 0; j < met.length; j++) {
                    met[j] = 0;
                }
                for (int k = 0; k < memory.size(); k++) {
                    c = 0;
                    for (int j = i; j < req.length; j++) {
                        if (!memory.get(k).equals(req[j])) {
                            c++;
                        } else {
                            met[k] = c;
                            break;
                        }
                    }
                    met[k] = c + 1;
                }
                int max = -1000;
                int index = 0;
                for (int j = 0; j < met.length; j++) {
                    if (max < met[j]) {
                        max = met[j];
                        index = j;
                    }
                }
                memory.remove(index);
                memory.add(req[i]);
                npagefault++;
            }
            for (int j = 0; j < memory.size(); j++) {
                System.out.print(memory.toArray()[j] + " ");
            }
            System.out.println("");

        }
        System.out.println("number of page faults : " + npagefault);

    }

    public static void LRU(int nframes, int[] req) {
         System.out.println("-------------------------------------------------------------------------");
        System.out.println("LRU Algorithm");
        System.out.println("");
        int npagefault = 0;
        ArrayList<Integer> memory = new ArrayList<>();
        int counter = 0;
        int[] met = new int[nframes];
        int c = 0;
        for (int i = 0; i < req.length; i++) {
            System.out.println("");
            System.out.println("The Request is  "+req[i]);
            if (!(memory.contains(req[i])) && (counter < nframes)) {
                memory.add(req[i]);
                counter++;
                npagefault++;
            } else if (!(memory.contains(req[i])) && (counter == nframes)) {
                for (int j = 0; j < met.length; j++) {
                    met[j] = 0;
                }
                for (int k = 0; k < memory.size(); k++) {
                    c = 0;
                    for (int j = i; j >= 0; j--) {
                        if (!memory.get(k).equals(req[j])) {
                            c++;
                        } else {
                            met[k] = c;
                            break;
                        }
                    }
                    met[k] = c + 1;
                }
                int max = -1000;
                int index = 0;
                for (int j = 0; j < met.length; j++) {
                    if (max < met[j]) {
                        max = met[j];
                        index = j;
                    }
                }
                memory.remove(index);
                memory.add(req[i]);
                npagefault++;
            }
            for (int j = 0; j < memory.size(); j++) {
                System.out.print(memory.toArray()[j] + " ");
            }
            System.out.println("");
        }
        System.out.println("number of page faults : " + npagefault);

    }

    public static void LFU(int nframes, int[] req) {
         System.out.println("-------------------------------------------------------------------------");
        System.out.println("LFU Algorithm");
        System.out.println("");
        int npagefault = 0;
        boolean found = false;
        int counter = 0;
        int index = 0;
        int anotherIndex=0;
        LinkedList<Integer> memory = new LinkedList<>();
        ArrayList<Integer> frequency = new ArrayList<>();
        for (int j = 0; j < req.length; j++) {
            for (int i = 0; i < frequency.size(); i++) {
                if (req[j] == frequency.get(i)) {
                    found = true;
                    break;
                }
            }
            if (found) {
                found = false;
            } else {
                frequency.add(req[j]);
            }
        }
        int[] count = new int[frequency.size()];
        for (int i = 0; i < frequency.size(); i++) {
            count[i] = 0;
        }
        for (int i = 0; i < req.length; i++) {
            System.out.println("");
            System.out.println("The Request is  "+req[i]);
            if (!(memory.contains(req[i])) && (counter < nframes)) {
                memory.add(req[i]);
                counter++;
                npagefault++;
                for (int k = 0; k < frequency.size(); k++) {
                    if (frequency.get(k) == req[i]) {
                        index = k;
                        break;
                    }
                }
                count[index]++;
            } else if (!(memory.contains(req[i])) && (counter == nframes)) {
                int min = 1000;
                int[] memoryCount = new int[nframes];
                for (int z = 0; z < memory.size(); z++) {
                    for (int y = 0; y < frequency.size(); y++) {
                        if (memory.get(z) == frequency.get(y)) {
                            memoryCount[z] = count[y];
                            break;
                        }
                    }
                }
                for (int j = 0; j < nframes; j++) {
                    if (min > memoryCount[j]) {
                        min = memoryCount[j];
                        index = j;
                    }
                }
                 for (int k = 0; k < frequency.size(); k++) {
                    if (frequency.get(k) == memory.get(index)) {
                        anotherIndex = k;
                        break;
                    }
                }
                count[anotherIndex]=0;
                memory.remove(index);
                memory.add(req[i]);
                npagefault++;
                for (int k = 0; k < frequency.size(); k++) {
                    if (frequency.get(k) == req[i]) {
                        index = k;
                        break;
                    }
                }
                count[index]++;
            } else {
                for (int k = 0; k < frequency.size(); k++) {
                    if (frequency.get(k) == req[i]) {
                        index = k;
                        break;
                    }
                }
                count[index]++;
            }
            for (int j = 0; j < memory.size(); j++) {
                System.out.print(memory.toArray()[j] + " ");
            }
              System.out.println("");
              System.out.println("Frequencies:  ");
            for(int k=0;k<frequency.size();k++)
            {
                System.out.print(frequency.get(k)+" : "+count[k]+"    "); 
            }
            System.out.println();
            System.out.println();
        }
        System.out.println("number of page faults : " + npagefault);
    }

    public static void SecondChance(int nframes, int[] req) {
         System.out.println("-------------------------------------------------------------------------");
        System.out.println("Second Chance Algorithm");
        System.out.println("");
        int npagefault = 0;
        int counter = 0;
        boolean found = false;
        LinkedList<Integer> memory = new LinkedList<>();
        ArrayList<Integer> frequency = new ArrayList<>();
        for (int j = 0; j < req.length; j++) {
            for (int i = 0; i < frequency.size(); i++) {
                if (req[j] == frequency.get(i)) {
                    found = true;
                    break;
                }
            }
            if (found) {
                found = false;
            } else {
                frequency.add(req[j]);
            }
        }
        boolean bits[] = new boolean[frequency.size()];
        int index = 0;
        for (int i = 0; i < frequency.size(); i++) {
            bits[i] = false;
        }
        for (int i = 0; i < req.length; i++) {
            System.out.println("");
            System.out.println("The Request is  "+req[i]);
            if (!(memory.contains(req[i])) && (counter < nframes)) {
                memory.add(req[i]);
                counter++;
                npagefault++;
            } else if (!(memory.contains(req[i])) && (counter == nframes)) {
                for (int k = 0; k < frequency.size(); k++) {
                    if (frequency.get(k) == memory.peek()) {
                        index = k;
                        break;
                    }
                }
                if (bits[index]) {
                    bits[index] = false;
                    for (int y = 1; y < memory.size(); y++) {
                        for (int k = 0; k < frequency.size(); k++) {
                            if (frequency.get(k) == memory.toArray()[y]) {
                                index = k;
                                break;
                            }
                        }
                        if (!bits[index]) {
                            memory.remove(y);
                            memory.add(req[i]);
                            break;
                        }
                    }
                } else {
                    memory.remove();
                    memory.add(req[i]);
                }
                npagefault++;
            } else {
                for (int k = 0; k < frequency.size(); k++) {
                    if (frequency.get(k) == req[i]) {
                        index = k;
                        break;
                    }
                }
                bits[index] = true;
            }
            for (int j = 0; j < memory.size(); j++) {
                System.out.print(memory.toArray()[j] + " ");
            }
             System.out.println("");
              System.out.println("R BIT:  ");
            for(int k=0;k<frequency.size();k++)
            {
                int check=0;
                if(bits[k])check=1;
                else check=0;
                System.out.print(frequency.get(k)+" : "+check+"    "); 
            }
            System.out.println();
            System.out.println();
        }
        System.out.println("number of page faults : " + npagefault);
    }

    public static void Enhanced(int nframes, int[] req) {
         System.out.println("-------------------------------------------------------------------------");
        System.out.println("Enhanced Second Chance Algorithm");
        System.out.println("");
        LinkedList<EnhancedNode> memory = new LinkedList<>();
        int npagefault = 0;
        int counter = 0;
        boolean finish = false;
        boolean zerozero;
        boolean zeroone;
        for (int i = 0; i < req.length; i++) {
            finish = false;
            EnhancedNode myNode=new EnhancedNode(req[i]);
            int checkR=0;
                int checkM=0;
                if(myNode.ref)checkR=1;
                else checkR=0;
                if(myNode.mod)checkM=1;
                else checkM=0;
                System.out.println("");
                System.out.println("The Request is  : "+myNode.value+ " : "+"< "+checkR+" , "+checkM+" > "+"      "); 
            if (!(enhancedContain(memory, req[i])) && (counter < nframes)) {
                memory.add(myNode);
                counter++;
                npagefault++;
            } else if (!(enhancedContain(memory, req[i])) && (counter == nframes)) {
                while (!finish) {
                    zerozero = false;
                    zeroone = false;
                    for (int k = 0; k < memory.size(); k++) {
                        if (memory.get(k).mod == false && memory.get(k).ref == false) {
                            memory.remove(k);
                            memory.add(myNode);
                            finish = true;
                            zerozero = true;
                            zeroone = true;
                            break;
                        }
                    }

                    if (zerozero == false) {
                        for (int k = 0; k < memory.size(); k++) {
                            if (memory.get(k).mod == true && memory.get(k).ref == false) {
                                memory.remove(k);
                                memory.add(myNode);
                                zeroone = true;
                                finish = true;
                                break;
                            }
                        }
                    }

                    if (zeroone == false) {
                        for (int k = 0; k < memory.size(); k++) {
                            memory.get(k).ref = false;
                        }
             System.out.println("<R,M> : ");
            for(int k=0;k<memory.size();k++)
            {
                checkR=0;
                checkM=0;
                if(memory.get(k).ref)checkR=1;
                else checkR=0;
                if(memory.get(k).mod)checkM=1;
                else checkM=0;
               System.out.print(memory.get(k).value+ " : "+"< "+checkR+" , "+checkM+" > "+"      "); 
            }
                        System.out.println("");
                    }
                }
                npagefault++;
            }
  System.out.println("");
            for (int j = 0; j < memory.size(); j++) {
                System.out.print(memory.get(j).value + " ");
            }
            System.out.println("");
              System.out.println("<R,M> : ");
            for(int k=0;k<memory.size();k++)
            {
                checkR=0;
                checkM=0;
                if(memory.get(k).ref)checkR=1;
                else checkR=0;
                if(memory.get(k).mod)checkM=1;
                else checkM=0;
                System.out.print(memory.get(k).value+ " : "+"< "+checkR+" , "+checkM+" > "+"      "); 
            }
            System.out.println();
        }
        System.out.println("number of page faults : " + npagefault);
    }

    public static boolean enhancedContain(LinkedList<EnhancedNode> x, int val) {
        for (int i = 0; i < x.size(); i++) {
            if (x.get(i).value == val) {
                return true;
            }
        }
        return false;
    }
}
