package com.hefei.sandroid.support.entity;

import android.net.Uri;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <pre>
 *     author: hefei
 *     time  : 2022/6/25
 *     desc  :
 * </pre>
 */
@Data
@NoArgsConstructor
public class MultimediaList {

    @SerializedName("type")
    private int type;
    @SerializedName("uri")
    private Uri uri;

}
