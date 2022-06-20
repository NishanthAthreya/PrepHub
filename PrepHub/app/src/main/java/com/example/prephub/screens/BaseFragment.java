package com.example.prephub.screens;

import static java.util.Objects.requireNonNull;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Generic base fragment to reduce boiler plate code.
 */
public abstract class BaseFragment extends Fragment {

    private View view;

    public BaseFragment() {
        // empty constructor
    }

    /**
     * Returns fragment view.
     */
    protected abstract View view(@NonNull Context context);

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        view = view(requireNonNull(container).getContext());
        return view;
    }

    @Override
    public void onDestroyView() {
        view = null;
        super.onDestroyView();
    }
}
