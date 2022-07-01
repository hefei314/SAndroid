package com.hefei.sandroid.support.entity;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <pre>
 *     author: hefei
 *     time  : 2020/04/15
 *     desc  :
 * </pre>
 */
@Data
@NoArgsConstructor
public class ViewRoute {

    @SerializedName("title")
    private String title;
    @SerializedName("target")
    private String target;
}
