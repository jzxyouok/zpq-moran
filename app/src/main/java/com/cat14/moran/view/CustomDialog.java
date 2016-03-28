package com.cat14.moran.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cat14.moran.R;

/**
 * 组定义提示框
 */
public class CustomDialog extends Dialog {

    public CustomDialog(Context context) {
        super(context);
    }

    public CustomDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private Context mContext;                // 上下文
        private String mTitle;                   // 提示标题
        private String mMessage;                 // 提示内容
        private String mPositiveButtonText;      // 确认键文本
        private String mNegativeButtonText;      // 取消键文本
        private View mContentView;               // 内容
        private OnClickListener mPositiveButtonClickListener;   // 确认键监听
        private OnClickListener mNegativeButtonClickListener;   // 取消键监听

        /**
         * Constructor using a context for this builder and the {@link CustomDialog} it creates.
         */
        public Builder(Context context) {
            this.mContext = context;
        }

        /**
         * 设置显示信息
         *
         * @return 返回的builder允许链接调用Set方法
         */
        public Builder setMessage(String message) {
            this.mMessage = message;
            return this;
        }

        /**
         * Set the message to display using the given resource id.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setMessage(int message) {
            this.mMessage = (String) mContext.getText(message);
            return this;
        }

        /**
         * 设置显示标题
         *
         * @return 返回的builder允许链接调用Set方法
         */
        public Builder setTitle(String title) {
            this.mTitle = title;
            return this;
        }

        /**
         * Set the title using the given resource id.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setTitle(int title) {
            this.mTitle = (String) mContext.getText(title);
            return this;
        }

        /**
         * 设置显示View
         *
         * @param v view信息
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setContentView(View v) {
            this.mContentView = v;
            return this;
        }

        /**
         * 设置当按下确认键时调用的监听
         *
         * @param positiveButtonText 显示在确认键上的文本
         * @param listener           链接在确认键上的监听
         * @return 返回的builder允许链接调用Set方法
         */
        public Builder setPositiveButton(String positiveButtonText, final DialogInterface.OnClickListener listener) {
            this.mPositiveButtonText = positiveButtonText;
            this.mPositiveButtonClickListener = listener;
            return this;
        }

        /**
         * Set a listener to be invoked when the positive button of the dialog is pressed.
         *
         * @param positiveButtonText The resource id of the text to display in the positive button
         * @param listener           The {@link DialogInterface.OnClickListener} to use.
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setPositiveButton(int positiveButtonText, final DialogInterface.OnClickListener listener) {
            this.mPositiveButtonText = (String) mContext.getText(positiveButtonText);
            this.mPositiveButtonClickListener = listener;
            return this;
        }


        /**
         * 设置当按下取消键时调用的监听
         *
         * @param negativeButtonText 显示在取消键上的文本
         * @param listener           链接在取消键上的监听
         * @return 返回的builder允许链接调用Set方法
         */
        public Builder setNegativeButton(String negativeButtonText, final DialogInterface.OnClickListener listener) {
            this.mNegativeButtonText = negativeButtonText;
            this.mNegativeButtonClickListener = listener;
            return this;
        }

        /**
         * Set a listener to be invoked when the negative button of the dialog is pressed.
         *
         * @param negativeButtonText The resource id of the text to display in the negative button
         * @param listener           The {@link DialogInterface.OnClickListener} to use.
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setNegativeButton(int negativeButtonText, final DialogInterface.OnClickListener listener) {
            this.mNegativeButtonText = (String) mContext.getText(negativeButtonText);
            this.mNegativeButtonClickListener = listener;
            return this;
        }

        public CustomDialog create() {
            LayoutInflater inflater = (LayoutInflater) mContext.                                    // 给打气筒赋予布局服务
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final CustomDialog dialog = new CustomDialog(mContext, R.style.Dialog);                 // 创建本体用于返回最终结果
            View layout = inflater.inflate(R.layout.dialog_normal_layout, null);                    // 给view设置布局
            dialog.addContentView(layout, new LinearLayout.LayoutParams(                            // 本体设置宽高
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
            ));

            if (mTitle != null) {                                                                   // 标题可以为空
                ((TextView) layout.findViewById(R.id.tv_dialog_title)).setText(mTitle);             // 找到布局中的标题并赋值
                mTitle = null;
            } else {
                layout.findViewById(R.id.tv_dialog_title).setVisibility(View.GONE);                 // 不显示不占位
            }

            if (mMessage != null) {                                                                 // 内容可以为空
                ((TextView) layout.findViewById(R.id.tv_dialog_message)).setText(mMessage);
                mMessage = null;
            } else if (mContentView != null){                                                       // 可以自定义View填充内容
                ((LinearLayout)layout.findViewById(R.id.ll_dialog_content)).removeAllViews();
                ((LinearLayout)layout.findViewById(R.id.ll_dialog_content)).addView(
                        mContentView, new LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT));
            }

            if (mPositiveButtonText != null) {                                                      // 判断是否需要确定键
                ((Button) layout.findViewById(R.id.btn_dialog_positive)).setText(mPositiveButtonText);
                if (mPositiveButtonClickListener != null) {
                    layout.findViewById(R.id.btn_dialog_positive)
                            .setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mPositiveButtonClickListener.onClick(dialog, BUTTON_POSITIVE);
                                }
                            });
                }
                mPositiveButtonText = null;                                                         // 添加完设置为空
            } else {
                layout.findViewById(R.id.btn_dialog_positive)                                       // 不显示不占位
                        .setVisibility(View.GONE);
                layout.findViewById(R.id.view_dialog_line).setVisibility(View.INVISIBLE);
            }

            if (mNegativeButtonText != null) {                                                      // 判断是否需要取消键
                ((Button) layout.findViewById(R.id.btn_dialog_negative)).setText(mNegativeButtonText);
                if (mNegativeButtonClickListener != null) {
                    layout.findViewById(R.id.btn_dialog_negative)
                            .setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mNegativeButtonClickListener.onClick(dialog, BUTTON_NEGATIVE);
                                }
                            });
                }
                mNegativeButtonText = null;
            } else {
                layout.findViewById(R.id.btn_dialog_negative)                                       // 不显示不占位
                        .setVisibility(View.GONE);
                layout.findViewById(R.id.view_dialog_line).setVisibility(View.INVISIBLE);
            }

            return dialog;
        }
    }


}
