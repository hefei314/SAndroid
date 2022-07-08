package com.hefei.sandroid.modules.widget.autowrap;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * <pre>
 *     author: hefei
 *     time  : 2020/04/24
 *     desc  : 中英文混合的多行文本框
 *             1. 单行 => 不处理
 *             2. 多行 => 前n-1行手动计算插入换行符
 * </pre>
 */
public class AutoWrapTextView extends AppCompatTextView {

    private String originText;

    private int mTextWidth = 0;

    // 是否已绘制
    private boolean isDraw = false;
    // 是否中英文混合换行 
    private boolean isAutoWrap = false;

    public AutoWrapTextView(Context context) {
        super(context);
    }

    public AutoWrapTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (isAutoWrap && !isDraw) {
            isDraw = true;
            setText(splitText());
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mTextWidth = w - getPaddingStart() - getPaddingEnd();
    }

    public void setAutoWrapText(String originText) {
        this.isAutoWrap = true;
        this.originText = originText;

        invalidate();
    }

    public void setAutoWrap(boolean autoWrap) {
        this.isAutoWrap = autoWrap;
    }

    public String splitText() {
        if (TextUtils.isEmpty(originText))
            return "";
        Paint paint = getPaint();
        int lineCount = getMaxLines();

        if (paint.measureText(originText) <= mTextWidth) {
            return originText;
        } else {
            StringBuilder splitText = new StringBuilder();
            for (int i = 0; i < lineCount; i++) {
                if (paint.measureText(originText) <= mTextWidth || i == lineCount - 1) {
                    splitText.append(originText);
                    break;
                } else {
                    int charCount = paint.breakText(originText, true, mTextWidth, null);
                    splitText.append(originText.substring(0, charCount)).append("\n");

                    originText = originText.substring(charCount);
                }
            }
            return splitText.toString();
        }
    }
}
