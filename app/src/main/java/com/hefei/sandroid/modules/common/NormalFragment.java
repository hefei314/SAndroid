package com.hefei.sandroid.modules.common;

import android.os.Bundle;

import com.hefei.sandroid.databinding.FragmentNormalPageBinding;
import com.hefei.sandroid.support.base.BaseBindingFragment;

import java.util.Locale;

/**
 * <pre>
 *     author: hefei
 *     time  : 2022/6/25
 *     desc  : the normal fragment
 * </pre>
 */
public class NormalFragment extends BaseBindingFragment<FragmentNormalPageBinding> {

    private static final String ARG_POSITION = "ARG_POSITION";

    private int mPosition;

    public static NormalFragment newInstance(int position) {
        NormalFragment fragment = new NormalFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initArguments() {
        super.initArguments();
        if (getArguments() != null) {
            mPosition = getArguments().getInt(ARG_POSITION);
        }
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void updateViews() {
        mViewBinding.tvContent.setText(String.format(Locale.CHINA, "Page %d", mPosition + 1));
    }
}