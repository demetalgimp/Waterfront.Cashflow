package com.waltoncraftsllc.waterfrontcashflow.tools;

import com.waltoncraftsllc.waterfrontcashflow.contaIners.Budget;

import java.util.ArrayList;

public class BudgetTree {
    String mName;
    ArrayList<BudgetTree> mChildren = new ArrayList<>();
    Budget mBudget = null;
    Money mWeekSum = null, mMonthSum = null;

    public BudgetTree root = new BudgetTree(".");

    public BudgetTree(String name) {
        mName = name;
    }
    public BudgetTree(String name, Budget budget) {
        mName = name;
        mBudget = budget;
    }

    public static void add(BudgetTree tree, ArrayList<String> path, Budget budget) {
        int index = tree.mChildren.indexOf(path.get(0));
        if ( index > 0 ) { // <-- found -- no need to create a node in tree
            path.remove(0);
            add(tree.mChildren.get(index), path, budget);

        } else {
            BudgetTree temp_tree = tree;
            for ( int i = 0; i < path.size() - 1; i++ ) {
                temp_tree.mChildren.add(new BudgetTree(path.get(0), null));
                temp_tree = temp_tree.mChildren.get(0);
                path.remove(0);
            }
            temp_tree.mChildren.add(new BudgetTree(path.get(0), budget));
        }
    }
}
