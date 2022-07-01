package com.hefei.sandroid.support.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewbinding.ViewBinding;

import com.hefei.sandroid.R;
import com.hefei.sandroid.support.global.DataConstants;

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
public abstract class BaseBindingActivity<VB extends ViewBinding> extends AppCompatActivity {

    protected VB mViewBinding;

    protected String mTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Class<?> thisClass = getClass();
            while (!(thisClass.getGenericSuperclass() instanceof ParameterizedType)) {
                thisClass = thisClass.getSuperclass();
            }
            Type superclass = thisClass.getGenericSuperclass();
            Class<?> aClass = (Class<?>) ((ParameterizedType) superclass).getActualTypeArguments()[0];
            Method method = aClass.getDeclaredMethod("inflate", LayoutInflater.class);
            mViewBinding = (VB) method.invoke(null, getLayoutInflater());
            setContentView(mViewBinding.getRoot());
        } catch (Exception e) {
            throw new NullPointerException("ViewBinding is null");
        }
        initArguments();
        initViews();
        updateViews();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void initToolbar(Toolbar toolbar, boolean showHomeAsUp) {
        initToolbar(toolbar, "", showHomeAsUp);
    }

    protected void initToolbar(Toolbar toolbar, @StringRes int resTitle, boolean showHomeAsUp) {
        initToolbar(toolbar, getString(resTitle), showHomeAsUp);
    }

    protected void initToolbar(Toolbar toolbar, String title, boolean showHomeAsUp) {
        toolbar.setTitle(title);
        if (toolbar.getNavigationIcon() == null) {
            toolbar.setNavigationIcon(R.drawable.icon_back);
        }
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(showHomeAsUp);
        }
    }

    protected void setToolbarTitle(@StringRes int resTitle) {
        setToolbarTitle(getString(resTitle));
    }

    protected void setToolbarTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    //----------------------------------public----------------------------------//

    protected void initArguments() {
        mTitle = getIntent().getStringExtra(DataConstants.ARG_TITLE);
    }

    protected abstract void initViews();

    protected abstract void updateViews();

}
