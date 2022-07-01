package com.hefei.sandroid.support.global;

import com.hefei.sandroid.R;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author: hefei
 *     time  : 2020/01/09
 *     desc  : 数据中心
 * </pre>
 */
public class DataRepository {

    private static final Object mLock = new Object();

    private static DataRepository mInstance;

    public static DataRepository getInstance() {
        if (mInstance == null) {
            synchronized (mLock) {
                if (mInstance == null) {
                    mInstance = new DataRepository();
                }
            }
        }
        return mInstance;
    }

    public List<Integer> getImageList() {
        List<Integer> list = new ArrayList<>();
        list.add(R.drawable.img01);
        list.add(R.drawable.img02);
        list.add(R.drawable.img03);
        list.add(R.drawable.img04);
        list.add(R.drawable.img05);
        list.add(R.drawable.img06);
        return list;
    }

    public List<String> getSimpleTextList() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("Simple Text " + i);
        }
        return list;
    }
}
