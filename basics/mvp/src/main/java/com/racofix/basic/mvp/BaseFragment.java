package com.racofix.basic.mvp;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import java.lang.ref.WeakReference;

public class BaseFragment<T extends LogicI> extends Fragment implements LogicI.Vo {

    private WeakReference<T> mLogicWrf;

    protected T getLogicImpl() {
        return this.mLogicWrf.get();
    }

    private T providerLogic() {
        return (T) LogicProviders.init(this.getClass());
    }

    private boolean checkLogicNonNull() {
        return this.mLogicWrf != null && this.mLogicWrf.get() != null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (providerLogic() == null) return;

        LogicViewModel<T> viewModel = ViewModelProviders.of(this).get(LogicViewModel.class);
        boolean isLogicCreated = false;
        if (viewModel.getLogicImpl() == null) {
            viewModel.setLogicImpl(providerLogic());
            isLogicCreated = true;
        }

        this.mLogicWrf = new WeakReference<>(viewModel.getLogicImpl());
        if (checkLogicNonNull()) {
            getLogicImpl().bindLifecycle(this.getLifecycle());
            getLogicImpl().bindVo(this);

            if (isLogicCreated) getLogicImpl().onLogicCreated();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (checkLogicNonNull()) {
            getLogicImpl().unbindLifecycle(this.getLifecycle());
            getLogicImpl().unbindVo();
        }
    }
}
