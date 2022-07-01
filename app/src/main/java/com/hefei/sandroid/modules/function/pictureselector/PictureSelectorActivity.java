package com.hefei.sandroid.modules.function.pictureselector;

import android.content.Intent;
import android.net.Uri;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;

import com.hefei.sandroid.databinding.ActivityPictureSelectorBinding;
import com.hefei.sandroid.support.base.BaseBindingActivity;
import com.hefei.sandroid.support.entity.MultimediaList;
import com.hefei.sandroid.support.image.PictureSelector;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author: hefei
 *     time  : 2022/6/20
 *     desc  : Picture Selector
 * </pre>
 */
public class PictureSelectorActivity extends BaseBindingActivity<ActivityPictureSelectorBinding> {

    private MultimediaListAdapter multimediaListAdapter;

    @Override
    protected void initViews() {
        initToolbar(mViewBinding.toolbar, mTitle, true);

        multimediaListAdapter = new MultimediaListAdapter(null);
        mViewBinding.rvMultimedia.setLayoutManager(new GridLayoutManager(this, 2));
        mViewBinding.rvMultimedia.setAdapter(multimediaListAdapter);

        mViewBinding.tvOnlyImage.setOnClickListener(v
                -> PictureSelector.chooseMultiImage(PictureSelectorActivity.this, 9));

        mViewBinding.tvOnlyVideo.setOnClickListener(v
                -> PictureSelector.chooseMultiVideo(PictureSelectorActivity.this, 9));

        mViewBinding.tvAll.setOnClickListener(v
                -> PictureSelector.chooseMultiAll(PictureSelectorActivity.this, 9));
    }

    @Override
    protected void updateViews() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PictureSelector.REQUEST_CODE_CHOOSE_IMAGE && resultCode == RESULT_OK && data != null) {
            List<Uri> uris = PictureSelector.obtainResult(data);
            List<String> paths = PictureSelector.obtainPathResult(data);

            multimediaListAdapter.setNewInstance(tranMultimediaList(uris));
        } else if (requestCode == PictureSelector.REQUEST_CODE_CHOOSE_VIDEO && resultCode == RESULT_OK && data != null) {
            List<Uri> uris = PictureSelector.obtainResult(data);
            List<String> paths = PictureSelector.obtainPathResult(data);

            multimediaListAdapter.setNewInstance(tranMultimediaList(uris));
        } else if (requestCode == PictureSelector.REQUEST_CODE_CHOOSE_All && resultCode == RESULT_OK && data != null) {
            List<Uri> uris = PictureSelector.obtainResult(data);
            List<String> paths = PictureSelector.obtainPathResult(data);

            multimediaListAdapter.setNewInstance(tranMultimediaList(uris));
        }
    }

    private List<MultimediaList> tranMultimediaList(List<Uri> uris) {
        List<MultimediaList> result = new ArrayList<>();
        for (Uri item : uris) {
            MultimediaList multimediaList = new MultimediaList();
            multimediaList.setUri(item);
            multimediaList.setType(item.toString().contains("video") ? 2 : 1);
            result.add(multimediaList);
        }
        return result;
    }
}
