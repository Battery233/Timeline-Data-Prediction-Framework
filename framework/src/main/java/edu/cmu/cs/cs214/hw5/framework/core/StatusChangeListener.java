package edu.cmu.cs.cs214.hw5.framework.core;

/**
 * The interface of the listener for the changes of status.
 */
public interface StatusChangeListener {

    /**
     * This function will be called when a new plugin is registered.
     *
     * @param plugin The new plugin which is to be registered.
     */
    void onPluginRegistered(Plugin plugin);
}
