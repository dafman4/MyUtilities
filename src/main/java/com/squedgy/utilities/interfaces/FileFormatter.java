package com.squedgy.utilities.interfaces;

public interface FileFormatter <WriteType> extends Formatter<WriteType, Void> {
    public abstract boolean shouldCreateFiles();
    public abstract void setWorkingFile(String file);
    public abstract boolean isAppending();
}
