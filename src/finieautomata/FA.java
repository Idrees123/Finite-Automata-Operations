/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finieautomata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Scanner;

/**
 *
 * @author DELL
 */
class FA {

    ArrayList<state> state_comb;
    ArrayList<state_concat> st_comb;

    int[][] transtable;
    int no_ofstate;
    int IS = 0;
    int[] FS;
    char[] inpchar = {'a', 'b'};

    FA() {

    }

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
        if (this.state_comb == null) {
            int i = 0;
            for (int[] transtable1 : transtable) {

                System.out.print("state:" + i + " " + transtable1[0]);
                System.out.print(" " + transtable1[1]);
                System.out.println("");
                i++;

            }
        } else {
            for (int i = 0; i < state_comb.size(); i++) {
                System.out.print("state:" + i + " " + transtable[i][0]);
                System.out.print(" " + transtable[i][1]);
                System.out.println("");

            }
        }
    }

    FA DFA_Complement() {
        FA f = new FA(this.no_ofstate);
        f.inpchar = this.inpchar;
        f.transtable = this.transtable;
        f.IS = this.IS;
        int g;
        int fs[] = new int[this.transtable.length - this.FS.length];
        outer:
        for (int i = 0, j = 0; j < fs.length; i++) {
            for (g = 0; g < FS.length; g++) {
                if (i == FS[g]) {
                    break;
                }
            }
            if (g == FS.length) {
                fs[j] = i;
                j++;
            }
        }
        f.FS = fs;

        return f;
    }

    FA DFA_Intersection(FA fa1, FA fa2) {
        FA t = new FA();
        fa1 = fa1.DFA_Complement();
        fa2 = fa2.DFA_Complement();
        FA f = t.DFA_union(fa1, fa2);
        f = f.DFA_Complement();
        return f;
    }

    FA DFA_union(FA fa1, FA fa2) {
        FA f = new FA();
        ArrayList<state> TT = new ArrayList();
        f.IS = 0;
        f.inpchar = fa1.inpchar;
        f.state_comb = new ArrayList<>();

        state st = new state();
        st.x = fa1.IS;
        st.y = fa2.IS;
        f.state_comb.add(st);
        int i = 0, k, b;
        while (true) {
            st = f.state_comb.get(i);
            state temp = new state();
            for (int j = 0; j < inpchar.length; j++) {
                state st2 = new state();
                st2.x = fa1.transition(st.x, inpchar[j]);
                st2.y = fa2.transition(st.y, inpchar[j]);
                for (k = 0; k < f.state_comb.size(); k++) {
                    if (st2.statecheck(f.state_comb.get(k))) {
                        break;
                    }

                }
                if (k == f.state_comb.size()) {
                    f.state_comb.add(st2);

                }
                //System.out.println("st2"+ st.x + " "+ st2.y);
                //System.out.println(f.state_comb.indexOf(st2));
                for (b = 0; b < f.state_comb.size(); b++) {
                    if (st2.statecheck(f.state_comb.get(b))) {
                        break;
                    }
                }
                if (j == 0) {
                    temp.x = b;
                } else {
                    temp.y = b;
                }
            }
            TT.add(temp);
            i++;

            if (i == f.state_comb.size()) {
                break;
            }
        }
        f.transtable = new int[TT.size()][2];
        for (int j = 0; j < TT.size(); j++) {
            f.transtable[j][0] = TT.get(j).x;
            f.transtable[j][1] = TT.get(j).y;
        }
        ArrayList<Integer> fs = new ArrayList<>();
        for (int a = 0; a < f.state_comb.size(); a++) {
            for (int b1 = 0; b1 < fa1.FS.length; b1++) {
                if (fa1.FS[b1] == f.state_comb.get(a).x) {
                    fs.add(a);
                }

            }
            for (int c = 0; c < fa2.FS.length; c++) {
                if (fa2.FS[c] == f.state_comb.get(a).y && fs.contains(a) == false) {
                    fs.add(a);
                }

            }
        }
        int[] t = new int[fs.size()];
        for (int j = 0; j < t.length; j++) {
            t[j] = fs.get(j);

        }
        f.FS = t;

        return f;
    }

    void showstate_comb() {
        int i = 0;
        for (state state_comb1 : state_comb) {
            System.out.print("state" + i);
            System.out.println(state_comb1.x + " " + state_comb1.y);
            i++;
        }
    }

    void showstateconcat_comb() {
        int i = 0;
        for (state_concat state_comb1 : st_comb) {
            System.out.print("state" + i + " ");
            state_comb1.show();
            System.out.println("");
            //  System.out.println(state_comb1.x + " " + state_comb1.y);
            i++;
        }
    }

    FA DFA_concatenate(FA fa1, FA fa2) {
        FA f = new FA();
        ArrayList<state> TT = new ArrayList();
        f.IS = 0;
        f.inpchar = fa1.inpchar;
        f.st_comb = new ArrayList<>();
        state_concat st = new state_concat();
        st.x = fa1.IS;
        for (int t : fa1.FS) {
            if (t == st.x) {
                st.y.add(fa2.IS);
                break;
            }

        }
        f.st_comb.add(st);
        int i = 0;
        while (true) {
            boolean inp = false;
            st = f.st_comb.get(i);
            state trans = new state();
            for (char ch : f.inpchar) {
                state_concat st2 = new state_concat();
                st2.x = fa1.transition(st.x, ch);
                for (int t : fa1.FS) {
                    if (t == st2.x) {
                        st2.y.add(fa2.IS);
                        break;
                    }
                }
                for (int s : st.y) {
                    int t1 = fa2.transition(s, ch);
                    if (!st2.y.contains(t1)) {
                        st2.y.add(t1);
                    }

                }
                Collections.sort(st2.y);
                int a = 0, k;
                for (; a < f.st_comb.size(); a++) {
                    state_concat st3 = f.st_comb.get(a);
                    if (st2.x == st3.x) {
                        for (k = 0; k < st2.y.size(); k++) {

                            if (k == st3.y.size()) {
                                break;
                            }
                            if (!Objects.equals(st3.y.get(k), st2.y.get(k))) {
                                break;
                            }

                        }
                        if (k == st2.y.size()) {
                            break;
                        }
                    }
                };
                if (a == f.st_comb.size()) {
                    f.st_comb.add(st2);

                }
                int h;
                for (int b = 0; b < f.st_comb.size(); b++) {
                    state_concat b1 = f.st_comb.get(b);
                    if (st2.x == b1.x) {
                        for (h = 0; h < st2.y.size(); h++) {
                            if (h == b1.y.size()) {
                                break;
                            }
                            if (!Objects.equals(st2.y.get(h), b1.y.get(h))) {
                                break;
                            }
                        }

                        if (h == st2.y.size()) {
                            if (inp == false) {
                                trans.x = b;
                                inp = true;
                            } else {
                                trans.y = b;
                            }

                        }
                    }

                }
            }
            TT.add(trans);
            i++;
            if (i == f.st_comb.size()) {
                break;
            }
        }
        f.transtable = new int[TT.size()][2];
        for (int j = 0; j < TT.size(); j++) {
            f.transtable[j][0] = TT.get(j).x;
            f.transtable[j][1] = TT.get(j).y;
        }
        ArrayList<Integer> fs = new ArrayList<>();
        for (int a = 0; a < f.st_comb.size(); a++) {
            for (int b = 0; b < f.st_comb.get(a).y.size(); b++) {
                for (int c = 0; c < fa2.FS.length; c++) {
                    if (fa2.FS[c] == f.st_comb.get(a).y.get(b) && fs.contains(a) == false) {
                        fs.add(a);
                    }

                }
            }
        }
        int[] t = new int[fs.size()];
        for (int j = 0; j < t.length; j++) {
            t[j] = fs.get(j);

        }
        f.FS = t;

        return f;
    }

    FA DFA_closure(FA fa) {
        FA f = new FA();
        ArrayList<state> TT = new ArrayList();
        f.IS = 0;
        f.inpchar = fa.inpchar;
        f.st_comb = new ArrayList<>();
        int z1 = 0;
        boolean fd = false;
        state_concat st = new state_concat();
        st.y.add(fa.IS);
        for (int t : fa.FS) {
            if (t == st.y.get(0) && !st.y.contains(fa.IS)) {
                st.y.add(fa.IS);
                break;
            }

        }
        f.st_comb.add(st);
        int i = 0;
        while (true) {
            boolean inp = false;
            st = f.st_comb.get(i);
            state trans = new state();
            for (char ch : f.inpchar) {
                state_concat st2 = new state_concat();
                st2.y.add(fa.transition(st.x, ch));
                for (int t : fa.FS) {
                    if (t == st2.y.get(0) && !st2.y.contains(fa.IS)) {
                        st2.y.add(fa.IS);
                        break;
                    }
                }
                for (int s : st.y) {
                    int t1 = fa.transition(s, ch);
                    if (!st2.y.contains(t1)) {
                        st2.y.add(t1);
                    }

                }
                if (st2.y.get(0) == fa.IS && st2.y.size() == 1 && fd == false) {
                    state_concat temp = new state_concat();
                    temp.x = st2.y.get(0);
                    f.st_comb.add(temp);
                    fd = true;
                    z1 = f.st_comb.size() - 1;
                    if (inp == false) {
                        trans.x = z1;
                        inp = true;
                    } else {
                        trans.y = z1;
                    }

                } else if (st2.y.get(0) == fa.IS && st2.y.size() == 1) {
                    st2 = f.st_comb.get(z1);
                    if (inp == false) {
                        trans.x = z1;
                        inp = true;
                    } else {
                        trans.y = z1;
                    }

                } else {
                    Collections.sort(st2.y);
                    int a = 0, k;
                    for (; a < f.st_comb.size(); a++) {
                        state_concat st3 = f.st_comb.get(a);
                        {
                            for (k = 0; k < st2.y.size(); k++) {

                                if (k == st3.y.size()) {
                                    break;
                                }
                                if (!Objects.equals(st3.y.get(k), st2.y.get(k))) {
                                    break;
                                }

                            }
                            if (k == st2.y.size()) {
                                break;
                            }
                        }
                    }
                    if (a == f.st_comb.size()) {
                        f.st_comb.add(st2);

                    }
                    int h;
                    for (int b = 0; b < f.st_comb.size(); b++) {
                        state_concat b1 = f.st_comb.get(b);
                        {
                            for (h = 0; h < st2.y.size(); h++) {
                                if (h == b1.y.size()) {
                                    break;
                                }
                                if (!Objects.equals(st2.y.get(h), b1.y.get(h))) {
                                    break;
                                }
                            }

                            if (h == st2.y.size()) {
                                if (inp == false) {
                                    trans.x = b;
                                    inp = true;
                                } else {
                                    trans.y = b;
                                }

                            }
                        }

                    }
                }
            }

            TT.add(trans);
            i++;
            if (i == f.st_comb.size()) {
                break;
            }
        }
        f.transtable = new int[TT.size()][2];
        for (int j = 0; j < TT.size(); j++) {
            f.transtable[j][0] = TT.get(j).x;
            f.transtable[j][1] = TT.get(j).y;
        }
        ArrayList<Integer> fs = new ArrayList<>();
        fs.add(0);
        for (int a = 0; a < f.st_comb.size(); a++) {
            for (int b = 0; b < f.st_comb.get(a).y.size(); b++) {
                for (int c = 0; c < fa.FS.length; c++) {
                    if (fa.FS[c] == f.st_comb.get(a).y.get(b) && fs.contains(a) == false) {
                        fs.add(a);
                    }

                }
            }
        }
        int[] t = new int[fs.size()];
        for (int j = 0; j < t.length; j++) {
            t[j] = fs.get(j);

        }
        f.FS = t;

        return f;
    }

}
