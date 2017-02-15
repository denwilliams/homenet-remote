package net.denwilliams.homenet.domain;

public interface State {
    String getId();
    String getState();
    String[] getAvailable();
}
