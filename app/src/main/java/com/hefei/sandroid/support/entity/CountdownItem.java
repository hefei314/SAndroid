package com.hefei.sandroid.support.entity;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <pre>
 *     author: hefei
 *     time  : 2020/07/21
 *     desc  :
 * </pre>
 */
@Data
@NoArgsConstructor
public class CountdownItem {

    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("leftTime")
    private long leftTime;
    @SerializedName("end_at")
    private String endAt;
}
