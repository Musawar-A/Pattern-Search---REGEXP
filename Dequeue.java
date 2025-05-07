public class Dequeue{

    private class Node {
        State state;
        Node previous, next;

        Node(State state) {
            this.state = state;
        }
    }

    private Node head, tail, scanNode; // Declare scanNode as a class member

    public Dequeue() {
        scanNode = new Node(null);
        head = tail = scanNode;
    }

    public void push(State state) {
        Node newNode = new Node(state);
        newNode.next = head;
        
        if (head != null){
            head.previous = newNode;
        }
        head = newNode;
        if(scanNode == head.next){
            scanNode.previous = newNode;
        }
    }

    public State pop() {
        if (head == scanNode) {
            //no more current states to pop 
            return null;
        }
        State state = head.state;
        head = head.next;
        if (head != null) {
            head.previous = null;
        } 
        return state;
    }

    public void enqueue(State state) {
        Node newNode = new Node(state);
        tail.next = newNode;
        newNode.previous = tail;
        tail = newNode;
        tail.next = null;
    }

    public boolean reset(){
        if(isCurrentEmpty()){

           if(!isNextEmpty()){

                head = scanNode.next;
                head.previous = null;

                tail.next = scanNode; 
                scanNode.previous = tail;
                tail = scanNode;
                tail.next = null;
                return true;
           }
                
        } 
        return false;       

    }

    // public void resetStates(){
    //     Node current = head;
    //     while (current != null) {
    //         if(current != scanNode){
    //             current.state.setVisited(false);
    //         }
    //         current = current.next;
        
    //     }
    // }

    public void printDequeue() {
        Node current = head;
        while (current != null) {
            if (current.state != null) { // avoid NullPointerException for scanNode
                System.out.print(
                    current.state.getStateNum() + " " +
                    current.state.getCharacter() + " " +
                    current.state.getNext1() + " " +
                    current.state.getNext2() + " | ");
                    System.out.println();
            } else {
                System.out.println("scanNode | ");
            }
            current = current.next;
        }
    }
    


    public boolean isCurrentEmpty() {
        return head == scanNode;
    }

    public boolean isNextEmpty() {
        return scanNode.next == null;
    }
}
