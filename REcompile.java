public class REcompile {
    static String[] p;
    static int j = 0;
    public static void main(String[] args){
        System.out.println("REGEXP: " + args[0]);
        p = args[0].split("");
        expression();
        System.out.println("Success");
    }

    // public static void parse(){
    //     expression();
    //     if(p[j].equals('!')){
    //         System.err.println();
    //     }
    // }

    public static boolean isVocab(String s){
        char c = s.charAt(0);
        if((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')){
            return true;
        }
        return false;
    }

    public static void expression(){
        term();
        
        if(p[j].equals("|")){
            j++;
            term();
        }

        if(p[j].equals("!")){
            return;
        }
        
        if(p[j].equals(")")){
            return;
        }
        expression();        
    }

    public static void term(){
        factor();
        if(p[j].equals("*") || p[j].equals("+") || p[j].equals("?")){
            j++;
        }
        return;
    }

    public static void factor(){
        if(p[j].equals("\\")){
            j += 2;
            return;
        }
        else if(isVocab(p[j]) || p[j].equals(".")){
            j++;
            return;
        }
        else if(p[j].equals("(")){
            j++;
            expression();
            if(!p[j].equals(")")){
                System.out.println("Expecting closing bracket");
                System.exit(0);
            }
            j++;
            return;
        }
        System.out.println("AAAAAHHHHHHHH");
        System.exit(0);
    }



    // public static void term(){
    //     factor();
    //     if(p[j].equals('*')){
    //         j++;
    //         return;
    //     }
    //     else if(p[j].equals('+')){
    //         j++;
    //         factor();
    //         // return;
    //     }
    //     return;
    // }

    // public static void factor(){
    //     if(isVocab(p[j])){
    //         j++;
    //         return;
    //     }
    //     else if(p[j].equals('(')){
    //         j++;
    //         expression();

    //         if(!p[j].equals(')')){
    //             System.out.println("Expecting bracket");
    //             System.exit(0);
    //         }
    //         j++;
    //         return;
    //     }
    //     else{
    //         System.out.println("aaaaaaahhhh");
    //         System.exit(0);
    //     }        
    // }


}