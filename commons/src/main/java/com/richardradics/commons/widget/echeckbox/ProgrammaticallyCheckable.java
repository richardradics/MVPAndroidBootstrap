package com.richardradics.commons.widget.echeckbox;

/**
 * Interface for UI widgets (particularly, checkable widgets) whose checked state can be changed programmatically, without triggering the {@code OnCheckedChangeListener}
 *
 */
public interface ProgrammaticallyCheckable {
    void setCheckedProgrammatically(boolean checked);
}