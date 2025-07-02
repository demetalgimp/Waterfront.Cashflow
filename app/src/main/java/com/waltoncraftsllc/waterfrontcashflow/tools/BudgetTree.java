package com.waltoncraftsllc.waterfrontcashflow.tools;

import java.util.ArrayList;

public class BudgetTree {
    String mName = "";
    ArrayList<BudgetTree> mChildren = new ArrayList<>();
    BudgetItem mBudgetItem = null;
    Money mWeekSum = null, mMonthSum = null;

    public BudgetTree root = new BudgetTree(".");

    public BudgetTree(String name) {
        mName = name;
    }
    public BudgetTree(String name, BudgetItem budgetItem) {
        mName = name;
        mBudgetItem = budgetItem;
    }

    public static void add(BudgetTree tree, ArrayList<String> path, BudgetItem budgetItem) {
        int index = tree.mChildren.indexOf(path.get(0));
        if ( index > 0 ) { // <-- found -- no need to create a node in tree
            path.remove(0);
            add(tree.mChildren.get(index), path, budgetItem);

        } else {
            BudgetTree temp_tree = tree;
            for ( int i = 0; i < path.size() - 1; i++ ) {
                temp_tree.mChildren.add(new BudgetTree(path.get(0), null));
                temp_tree = temp_tree.mChildren.get(0);
                path.remove(0);
            }
            temp_tree.mChildren.add(new BudgetTree(path.get(0), budgetItem));
        }
    }
}
