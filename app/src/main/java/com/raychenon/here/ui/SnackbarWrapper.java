package com.raychenon.here.ui;

import com.raychenon.here.R;

import android.app.Activity;

import android.content.Context;
import android.content.Intent;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.design.widget.Snackbar;

import android.view.View;

import android.widget.TextView;

/**
 * Wrapper of any underlying visual notifications.
 *
 * <p>The implementation of the notification is {@link Snackbar}.
 *
 * @author  Raymond Chenon
 */

public class SnackbarWrapper {
    public interface ActionCallback {
        void onActionPerformed();
    }

    @Nullable
    private ActionCallback actionCallback;

    @Nullable
    private final Snackbar snackbar;

    private SnackbarWrapper(@Nullable final Snackbar snackbar) {
        this.snackbar = snackbar;
    }

    @Nullable
    private Snackbar getSnackbar() {
        return snackbar;
    }

    @Nullable
    public View getView() {
        if (snackbar == null) {
            return null;
        }

        return snackbar.getView();
    }

    private Snackbar.Callback dismissCallback = new Snackbar.Callback() {
        @Override
        public void onDismissed(final Snackbar snackbar, final int event) {
            actionCallback = null;
        }
    };

    /**
     * Make a visual Notification to display a message.
     *
     * @param   view      The view to find a parent from.
     * @param   message   The text to show. Can be formatted text.
     * @param   duration  How long to display the message. See defined {@link SnackbarWrapper.Duration}
     *
     * @return  SnackbarWrapper
     */
    @SuppressWarnings("ResourceType")
    public static SnackbarWrapper make(@Nullable final View view, final CharSequence message, final Duration duration) {

        // the annotation @SuppressWarnings("ResourceType") is necessary because Snackbar expects  (@Duration int
        // duration)
        Snackbar snackbar = null;
        if (view != null) {
            snackbar = Snackbar.make(view, message, duration.getDuration());
            styleMainText(snackbar);
        }

        return new SnackbarWrapper(snackbar);
    }

    public static SnackbarWrapper make(@NonNull final Activity activity, final CharSequence message,
            final Duration duration) {
        return make(activity.getWindow().getDecorView().findViewById(android.R.id.content), message, duration);
    }

    public static SnackbarWrapper make(@Nullable final View view, final CharSequence message) {
        return make(view, message, Duration.SHORT);
    }

    public static void show(@Nullable final View view, final CharSequence message) {
        make(view, message).show();
    }

    public static void show(final View view, final CharSequence message, final Duration duration) {
        make(view, message, duration).show();
    }

    /**
     * Add an anchor link.
     *
     * @param   actionMessage  The message on the right displayed in Orange
     * @param   intent         The intent where you to redirect the user
     *
     * @return  SnackbarWrapper
     */
    public SnackbarWrapper addAction(final String actionMessage, final Intent intent) {
        View view = getView();

        if (view != null) {
            styleActionText(view.getContext(), this.snackbar, actionMessage, intent, actionCallback);
        }

        return this;
    }

    /**
     * Call this to add a callback action.
     */
    public SnackbarWrapper addCallback(final ActionCallback actionCallback) {
        this.actionCallback = actionCallback;
        this.snackbar.setCallback(dismissCallback);
        return this;
    }

    public SnackbarWrapper setTextMaxLines(final int maxLines) {
        View snackBarView = snackbar.getView();

        // this is the snackbar's TextView id
        TextView textView = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        if (textView != null) {
            textView.setSingleLine(false);
            textView.setMaxLines(maxLines);
        }

        return this;
    }

    public void show() {
        if (getSnackbar() != null) {
            getSnackbar().show();
        }
    }

    public void dismiss() {
        if (getSnackbar() != null) {
            getSnackbar().dismiss();
        }
    }

    /**
     * modify the snackbar to set a default background color.
     */
    private static void styleMainText(final Snackbar snackbar) {
        snackbar.getView().setBackgroundResource(R.color.snackbar_background);
    }

    /**
     * modify the snackbar to add an Action.
     */
    private static void styleActionText(final Context context, final Snackbar snackbar, final String actionMessage,
            final Intent intent, final ActionCallback actionCallback) {

        // Changing action button text color
        snackbar.setActionTextColor(context.getResources().getColor(R.color.snackbar_text));
        snackbar.setAction(actionMessage, new View.OnClickListener() {
                @Override
                public void onClick(final View v) {

                    if (actionCallback != null) {
                        actionCallback.onActionPerformed();
                    }

                    snackbar.dismiss();
                    context.startActivity(intent);
                }
            });
    }

    public enum Duration {
        SHORT(Snackbar.LENGTH_SHORT),
        LONG(Snackbar.LENGTH_LONG),
        INDEFINITE(Snackbar.LENGTH_INDEFINITE);

        private final int duration;

        Duration(final int code) {
            this.duration = code;
        }

        public int getDuration() {
            return this.duration;
        }
    }

}
