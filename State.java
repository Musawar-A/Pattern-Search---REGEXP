public class State {

    private int stateNum;
    private String character;
    private int next1;
    private int next2;

    public State (int stateNum, String character, int next1, int next2) {
        this.stateNum = stateNum;
        this.character = character;
        this.next1 = next1;
        this.next2 = next2;
    }

    public int getStateNum() {
        return stateNum;
    }
    public String getCharacter() {
        return character;
    }
    public int getNext1() {
        return next1;
    }
    public int getNext2() {
        return next2;
    }

   public void setNext1(int next1) {
        this.next1 = next1;
    }
    public void setNext2(int next2) {
        this.next2 = next2;
    }

    public boolean isFinal() {
        return next1 == -1 && next2 == -1;
    }

    public boolean isBranch(){
        if (character.equals("BR")) {
            return true;
        }
        return false;
    }

    //has to be fromm our grammar
    public boolean isWildcard(){
        if (character.equals("WC")) {
            return true;
        }
        return false;
    }
    
    public boolean isMatch(String match){
        if (character.equals(match)) {
            return true;
        }
        return false;
    }

}