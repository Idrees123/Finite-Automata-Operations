/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finieautomata;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author DELL
 */
class state {

    int x, y;

    void show() {
        System.out.println(x + "or" + y);
    }
    boolean statecheck(state t){
        return t.x==this.x && t.y== this.y;
    }
}
class state_concat{
    int x;
    ArrayList<Integer> y=new ArrayList<>();
    void show(){
        System.out.print(x);
        for (int y1 : y) {
            System.out.print("+"+y1);
            
        }
    }
}

public class DFA {

  

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("enter no. of states for fa1");
        FA fa1 = new FA(in.nextInt());
        fa1.initFA();
        System.out.println("enter no. of states for fa1");
        FA fa2 = new FA(in.nextInt());
        fa2.initFA();
        FA fa1not = fa1.DFA_Complement();
        FA fa2not=fa2.DFA_Complement();
        FA xor=new FA();
        xor=xor.DFA_union(fa1.DFA_Intersection(fa1, fa2not), fa2.DFA_Intersection(fa1not, fa2));
        xor.show();
        
        
               
        
        
         
        
      
     

}

} 

