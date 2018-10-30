package com.racofix.develop.mvp;

import android.arch.lifecycle.Lifecycle;
import android.os.Bundle;

public interface LogicI<V extends Vo> {

    Bundle getStateBundle();

    void attachLifecycle(Lifecycle lifecycle);

    void detachLifecycle(Lifecycle lifecycle);

    void attachVo(V vo);

    void detachVo();

    V getVo();

    boolean isVoAttached();

    void onLogicCreated();

    void onLogicDestroy();
}