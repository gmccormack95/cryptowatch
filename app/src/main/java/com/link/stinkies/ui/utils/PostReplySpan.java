package com.link.stinkies.ui.utils;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class PostReplySpan extends ClickableSpan {

    private int postId = -1;

    public static void span(TextView view) {
        int postId = -1;
        SpannableStringBuilder sb = new SpannableStringBuilder(view.getText());
        CharSequence text = view.getText();

        while (true) {
            String string = text.toString();
            int start = string.indexOf(">>");
            int end = start + 10;

            if (start == -1) {
                break;
            } else {
                try{
                    CharSequence idCheck = text.subSequence(start + 2, end);
                    postId = Integer.parseInt(idCheck.toString());
                } catch (Exception e) {
                    break;
                }
            }

            PostReplySpan span = new PostReplySpan(postId);

            int idPosition = view.getText().toString().indexOf(">>" + postId);
            sb.setSpan(span, idPosition, idPosition + 10, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            if(text.length() - 1 <= end) break;

            text = text.subSequence(end, text.length() - 1);
        }

        view.setText(sb);
    }

    public PostReplySpan(int postId) {
        this.postId = postId;
    }

    @Override
    public void onClick(@NonNull View view) { }

    @Override
    public void updateDrawState(TextPaint paint) {
        super.updateDrawState(paint);
        paint.setUnderlineText(true);
    }

    public int getPostId() {
        return postId;
    }

}

