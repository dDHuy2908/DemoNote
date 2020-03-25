package com.ddhuy4298.note.callbacks;

import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

import com.ddhuy4298.note.R;

public abstract class MainActionModeCallBack implements ActionMode.Callback {

    private ActionMode actionMode;
    private MenuItem countItem;
    private MenuItem shareItem;

    public ActionMode getActionMode() {
        return actionMode;
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        mode.getMenuInflater().inflate(R.menu.menu_action_mode, menu);
        actionMode = mode;
        countItem = menu.findItem(R.id.action_mode_checked_count);
        shareItem = menu.findItem(R.id.action_mode_share);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        actionMode = null;
    }

    public void setCountItem(String string) {
        if (countItem != null)
            this.countItem.setTitle(string);
    }

    public void changeShareItemVisible(boolean b) {
        shareItem.setVisible(b);
    }
}
