package edu.cmu.cs.cs214.team24.framework.core;

import java.util.ArrayList;
import java.util.List;

public class FrameworkImpl implements Framework{
    private final List<Plugin> dataPlugin;
    private final List<Plugin> displayPlugin;

    public FrameworkImpl(){
        dataPlugin = PluginLoader.registerDataPlugin();
        displayPlugin = PluginLoader.registerDisplayPlugin();
    }

    @Override
    public int getDataPluginNumber(){
        return dataPlugin.size();
    }

    @Override
    public int getDisplayPluginNumber(){
        return displayPlugin.size();
    }

}