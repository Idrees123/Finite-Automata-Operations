/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finieautomata;

import java.util.Scanner;

/**
 *
 * @author DELL
 */
class FA {

    int[][] transtable;
    int no_ofstate;
    int IS = 0;
    int[] FS;
    char[] inpchar = {'a', 'b'};

    FA(int n) {

        no_ofstate = n;
        transtable = new int[no_ofstate][inpchar.length];

    }

    void initFA() {
        Scanner in = new Scanner(System.in);
        for (int i = 0; i < transtable.length; i++) {
            System.out.println("enter next state for a @ state" + i);
            transtable[i][0] = in.nextInt();
            System.out.println("enter next state for b @ state" + i);
            transtable[i][1] = in.nextInt();

        }
        System.out.println("enter no. of final state");
        int f = in.nextInt();
        FS = new int[f];
        for (int i = 0; i < f; i++) {
            System.out.println("enter final state no." + i);
            FS[i] = in.nextInt();

        }

    }

    boolean validate(String inp) {
        int state = IS;
        for (int a = 0; a < inp.length(); a++) {
            state = transition(state, inp.charAt(a));
        }
        for (int b = 0; b < FS.length; b++) {
            if (state == FS[b]) {
                return true;
            }
        }
        return false;

    }

    int transition(int st, char ch) {
        for (int a = 0; a < inpchar.length; a++) {
            if (inpchar[a] == ch) {
                return transtable[st][a];
            }
        }
        return -1;

    }

    void show() {
        int i = 0;
        for (int[] transtable1 : transtable) {

            System.out.print("state:" + i + " " + transtable1[0]);
            System.out.print(" " + transtable1[1]);
            System.out.println("");
            i++;

        }
    }
}

public class Finieautomata {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("enter no. of states for fa1");
        FA fa1 = new FA(in.nextInt());
        fa1.initFA();
        System.out.println("enter no. of states for fa2");
        FA fa2 = new FA(in.nextInt());
        fa2.initFA();
        DFA t = new DFA();
        //FA fa1c=t.Complement(fa1);
        //System.out.println( fa1.validate("aab"));
        //System.out.println(fa1c.validate("aab"));

        FA r = t.DFA_union(fa1, fa2);
        for (int a : r.FS) {
            System.out.println(a);

        }
     //r.show();
        //t.show();

        // System.out.println(f.validate("abababababab"));
    }

}
