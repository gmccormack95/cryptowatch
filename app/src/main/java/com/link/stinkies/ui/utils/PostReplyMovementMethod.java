package com.link.stinkies.ui.utils;

import android.text.Layout;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

public class PostReplyMovementMethod extends LinkMovementMethod {

    private OnLinkClickedListener mOnLinkClickedListener;

    public PostReplyMovementMethod(OnLinkClickedListener onLinkClickedListener) {
        mOnLinkClickedListener = onLinkClickedListener;
    }

    public boolean onTouchEvent(TextView widget, android.text.Spannable buffer, android.view.MotionEvent event) {
        int action = event.getAction();

        if (action == MotionEvent.ACTION_UP) {
            int x = (int) event.getX();
            int y = (int) event.getY();

            x -= widget.getTotalPaddingLeft();
            y -= widget.getTotalPaddingTop();

            x += widget.getScrollX();
            y += widget.getScrollY();

            Layout layout = widget.getLayout();
            int line = layout.getLineForVertical(y);
            int off = layout.getOffsetForHorizontal(line, x);

            PostReplySpan[] objectSpans = buffer.getSpans(off, off, PostReplySpan.class);
            if (objectSpans.length != 0) {
                int postId = objectSpans[0].getPostId();

                try {
                    boolean handled = mOnLinkClickedListener.onLinkClicked(postId);
                    if (handled) {
                        return true;
                    }
                } catch (Exception e) {
                    Log.e("PostReplyMovementMethod", e.getMessage());
                }

                return super.onTouchEvent(widget, buffer, event);
            }
        }
        return super.onTouchEvent(widget, buffer, event);
    }

    public interface OnLinkClickedListener {
        boolean onLinkClicked(int postId);
    }
}
