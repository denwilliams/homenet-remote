package net.denwilliams.homenet.domain;

public interface Callback<T> {
    void onSuccess(T result);
    void onFail(Throwable t);
}
