package net.denwilliams.homenet.domain.query;

import net.denwilliams.homenet.domain.Callback;
import net.denwilliams.homenet.domain.State;

import java.util.List;

public interface GetStatesQuery {
    void getStates(Callback<List<State>> callback);
    void getState(String stateId, Callback<State> callback);
}
