package edu.cmu.cs.cs214.team24.framework.core;

public interface Plugin {
    String name();

    boolean isDataPlugin();

    boolean isDisplayPlugin();
}
