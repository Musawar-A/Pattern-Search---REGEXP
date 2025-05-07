import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class REsearch {

    ArrayList<State> states = new ArrayList<State>();
    ArrayList<Boolean> visitedStatus = new ArrayList<Boolean>();
    int mark = 0;



    public static void main(String[] args) throws IOException {

        REsearch reSearch = new REsearch();

        if (args.length != 1) {
            System.out.println("Usage: java ReSearch <filename>");
            return;
        }
        String filename = args[0];

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line; 

         while ((line = reader.readLine()) != null &&!line.trim().isEmpty()) {

            String[] parts = line.split(",");
            if (parts.length != 4) {
                System.out.println("Invalid input format. Expected 4 comma-separated values.");
                continue;
            }
            int stateNum = Integer.parseInt(parts[0]);
            String character= parts[1];
            int next1 = Integer.parseInt(parts[2]);
            int next2 = Integer.parseInt(parts[3]);
            State state = new State(stateNum, character, next1, next2);
            //System.out.println("State Number: " + state.getStateNum() + ", Character: " + state.getCharacter() + ", Next1: " + state.getNext1() + ", Next2: " + state.getNext2());

           reSearch.states.add(state);
           reSearch.visitedStatus.add(false);
            
        }

        // String hello = "helloz";
        // ArrayList<String> list = reSearch.splitLine(hello);
        // reSearch.search(list, reSearch.mark);
        


        //  You could also do something with the file (simple.txt)
        File file = new File(filename);
        BufferedReader fileReader = new BufferedReader(new FileReader(file));
        String fileLine;
        while ((fileLine = fileReader.readLine()) != null) {
            //System.out.println("Processing file: " + fileLine);
            reSearch.mark = 0;
            reSearch.resetStatus();
            ArrayList<String> list = reSearch.splitLine(fileLine);
            reSearch.search(list, reSearch.mark);
        }
        fileReader.close();


        
    }

    public ArrayList<String> splitLine(String line) {
        ArrayList<String> characters = new ArrayList<>(Arrays.asList(line.split("")));
        return characters;

    }

    public String toString(ArrayList<String> line) {
        StringBuilder sb = new StringBuilder();
        for (String s : line) {
            sb.append(s);
        }
        return sb.toString();
    }


    public boolean process(ArrayList<String> line, int mark){
        Dequeue dequeue = new Dequeue();
        
        dequeue.push(states.get(0));

        System.out.println("Dequeue after push:");
        dequeue.printDequeue();

        //loads the initial starting state
        State poppedState = dequeue.pop();
        printVisitStatus();

        boolean isFail = false; 
        int point = mark; 

        while (!isFail) {
            // System.out.println("Do i get back up here: ");

            if(poppedState.getNext1() == -1){   
                System.out.println(toString(line));
                return true;
            }
            if(!visitedStatus.get(poppedState.getStateNum())){

                //mark as visited
                visitedStatus.set(poppedState.getStateNum(), true);

                //check if they are branched or not 
                if(poppedState.isBranch()){
                    System.out.println("BR:");
        
                    pushPossibleCurrent(poppedState, dequeue);
                    dequeue.printDequeue();
                    printVisitStatus();
                } else{
                    System.out.println("Got here");

                    if(point >= line.size()){
                        System.out.println("point is out of bounds");
                        return false;
                    }
                    String characteString = line.get(point);
                    System.out.println("Character: " + characteString + "vs. " + poppedState.getCharacter());
                    boolean isMatch = poppedState.isMatch(line.get(point));
                    System.out.println("Are they a match?" + isMatch);

                    //!!!! also match wc 
                    if(isMatch){

                        State nextState = states.get(poppedState.getNext1());
                        System.out.println(nextState.getCharacter());
                        boolean nextStateFinal = nextState.isFinal();   
                        System.out.println("Is it final?" + nextStateFinal);
                        if (nextState != null && nextState.isFinal()) {
                            //successful match 
                            System.out.println(toString(line));
                            return true;
                        }
                                    
                        enqueueNextPossibble(poppedState, dequeue);
                        dequeue.printDequeue();

                    } 
                }

               

                if(dequeue.reset()){
                    resetStatus();
                    System.out.println("after reset:");
                    point++;
                    dequeue.printDequeue();
                    //puts nodes at the back only if the scaan node is popped off
                }

                if(!dequeue.isCurrentEmpty()){
                    System.out.println("popped it off:");
                    poppedState = dequeue.pop();
                    System.out.println("popped state: " + poppedState.getCharacter());
                    dequeue.printDequeue();
                    continue;
                }
                
                if(dequeue.isNextEmpty()){
                    resetStatus();
                    isFail = true;
        
                }

                
            } else {
                poppedState = dequeue.pop();
            }
            System.out.println("i fail," + isFail);
 
        }
        System.out.println("howwww");   
        return false;
    }

    public void search(ArrayList<String> line, int mark){
        
        while(mark < line.size()){
            System.out.println("Mark: " + mark);
            System.out.println(toString(line));
            if(process(line, mark)){
                System.out.println("Found a match! End of line");
                return;
            } else {
                mark++;
                resetStatus();
            }
        }
        System.out.println("No match found.");

    }
    public void printVisitStatus(){
        for (int i = 0; i < visitedStatus.size(); i++) {
            System.out.println("State " + i + ": " + visitedStatus.get(i));
        }
    }

    public void resetStatus(){
        for (int i = 0; i < visitedStatus.size(); i++) {
            visitedStatus.set(i, false);
        }
    }
    
    
    public void enqueueNextPossibble(State state, Dequeue dequeue){
       
        if(state.getNext1() == state.getNext2()){
            dequeue.enqueue(states.get(state.getNext1()));
        } else {
            dequeue.enqueue(states.get(state.getNext1()));
            dequeue.enqueue(states.get(state.getNext2()));
        }

    }

    public void pushPossibleCurrent(State state, Dequeue dequeue){
        if(state.getNext1() == state.getNext2()){
            dequeue.push(states.get(state.getNext1()));
        } else {
            dequeue.push(states.get(state.getNext1()));
            dequeue.push(states.get(state.getNext2()));
        }
    }
    
}
