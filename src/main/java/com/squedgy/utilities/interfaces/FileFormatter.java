package com.squedgy.utilities.interfaces;


import java.io.File;

public interface FileFormatter <WriteType> extends Formatter<WriteType, Void> {

    public abstract void setWorkingFile(String file);
    public abstract boolean shouldCreateFiles();
    public abstract boolean isAppending();

}
