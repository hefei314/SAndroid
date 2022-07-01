package com.hefei.sandroid.support.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * <pre>
 *     author: hefei
 *     time  : 2022/6/21
 *     desc  :
 * </pre>
 */
public abstract class BaseBindingFragment<VB extends ViewBinding> extends Fragment {

    protected Context mContext;

    protected VB mViewBinding;

    protected boolean isFirstLoad = true;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            initArguments();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        try {
            Class<?> thisClass = getClass();
            while (!(thisClass.getGenericSuperclass() instanceof ParameterizedType)) {
                thisClass = thisClass.getSuperclass();
            }
            Type superclass = thisClass.getGenericSuperclass();
            Class<?> aClass = (Class<?>) ((ParameterizedType) superclass).getActualTypeArguments()[0];
            Method method = aClass.getDeclaredMethod("inflate", LayoutInflater.class, ViewGroup.class, boolean.class);
            mViewBinding = (VB) method.invoke(null, inflater, container, false);
        } catch (Exception e) {
            throw new NullPointerException("ViewBinding is null");
        }
        initViews();
        return mViewBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isAlwaysUpdateViews()) {
            updateViews();
        } else {
            if (isFirstLoad) {
                updateViews();

                isFirstLoad = false;
            }
        }
    }

    //----------------------------------public----------------------------------//

    protected boolean isAlwaysUpdateViews() {
        return true;
    }

    protected void initArguments() {

    }

    protected abstract void initViews();

    protected abstract void updateViews();
}
