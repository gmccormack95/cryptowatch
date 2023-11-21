package com.link.stinkies.view;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class PostReplySpan extends ClickableSpan {

    private boolean withUnderline;

    private static int postId = -1;

    public static void span(TextView view) {
        span(view, true);
    }

    public static void span(TextView view, boolean withUnderline) {
        CharSequence text = view.getText();
        String string = text.toString();
        PostReplySpan span = new PostReplySpan(withUnderline);

        int start = string.indexOf(">>");
        int end = start + 10;

        if (start == -1) {
            return;
        } else {
            try{
                CharSequence idCheck = text.subSequence(start + 2, end);
                postId = Integer.parseInt(idCheck.toString());
            } catch (NumberFormatException e) {
                return;
            }
        }

        if (text instanceof Spannable) {
            ((Spannable) text).setSpan(span, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {
            SpannableString s = SpannableString.valueOf(text);
            s.setSpan(span, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            view.setText(s);
        }
    }

    public PostReplySpan(boolean withUnderline) {
        this.withUnderline = withUnderline;
    }

    @Override
    public void onClick(@NonNull View view) { }

    @Override
    public void updateDrawState(TextPaint paint) {
        super.updateDrawState(paint);
        paint.setUnderlineText(withUnderline);
    }

    public int getPostId() {
        return postId;
    }

}

