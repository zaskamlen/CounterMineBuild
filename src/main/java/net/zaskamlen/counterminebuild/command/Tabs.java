package net.zaskamlen.counterminebuild.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tabs {
    private final Map<String, List<String>> tabOptions;

    private Tabs() {
        this.tabOptions = new HashMap<>();
    }

    public static Tabs create() {
        return new Tabs();
    }

    public Tabs addTabs(String option, Tabs tabs) {
        this.tabOptions.put(option, tabs.getTabs());
        return this;
    }

    public List<String> getTabs() {
        return new ArrayList<>(tabOptions.keySet());
    }

    public List<String> getTabs(String option) {
        if (tabOptions.containsKey(option)) {
            return tabOptions.get(option);
        }
        return new ArrayList<>();
    }
}
