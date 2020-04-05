package edu.cmu.cs.cs214.team24.framework.core;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class PluginLoader {
    public static int listPlugins() {
        int result = 0;
        for (Plugin p : ServiceLoader.load(Plugin.class)) {
            System.out.println("Load plugin:" + p.name());
            result++;
        }
        return result;
    }

    public static List<Plugin> registerDataPlugin(){
        List<Plugin> result = new ArrayList<>();
        for (Plugin p : ServiceLoader.load(Plugin.class)) {
            if(p.isDataPlugin()){
                result.add(p);
            }
        }
        return result;
    }

    public static List<Plugin> registerDisplayPlugin(){
        List<Plugin> result = new ArrayList<>();
        for (Plugin p : ServiceLoader.load(Plugin.class)) {
            if(p.isDisplayPlugin()){
                result.add(p);
            }
        }
        return result;
    }
}
