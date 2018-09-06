package in.blackpaper.animatorprogress;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v7.widget.AppCompatTextView;
import android.text.Html;
import android.util.AttributeSet;

public class AnimatorTextView extends AppCompatTextView implements OnProgressBarListener {
    //onCompleted Text
    private String onCompletedText;

    //preProcess Text
    private String whileProcessingText;

    /*
    progress title text*/
    private String progressTitle;
    /*
    isshow progress*/
    private boolean showProgress = true;

    /*process title color*/
    private int processTitleColor;

    /*while process text color*/
    private int whileProcessTextColor;

    /*onCompleted process text color*/
    private int onCompletedProcessTextColor;

    /*animatorTextView object*/
    private AnimatorTextView animatorTextView;

    /*textview position*/
    private int textViewPosition;

    /*mPrefix text*/
    private String mPrefix;

    /*mSuffix text*/
    private String mSuffix;

    /*mPrefix text color*/
    private int mPrefixTextColor;

    /*mSuffix text color*/
    private int mSuffixTextColor;

    /*current process text color*/
    private int mCurrentProcessTextColor;

    /*custom animation provider*/
    private CustomAnimationProvider customAnimationProvider = new CustomAnimationProvider();


    /*default values*/
    private final int default_text_color = Color.rgb(245, 245, 245);
    private final String WHILE_PROCESSINGTEXT = "Downloading...";

    public AnimatorTextView(Context context) {
        super(context);
    }

    public AnimatorTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AnimatorTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init(AttributeSet attrs, AnimatorTextView animatorTextView, int textViewPosition) {

        this.animatorTextView = animatorTextView;
        this.textViewPosition = textViewPosition;
        TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.AnimatorView);
        onCompletedText = attributes.getString(R.styleable.AnimatorView_onCompleted_text) == null ? "Completed" :
                attributes.getString(R.styleable.AnimatorView_onCompleted_text);

        progressTitle = attributes.getString(R.styleable.AnimatorView_process_title) == null ? "Download" :
                attributes.getString(R.styleable.AnimatorView_process_title);
        whileProcessingText = attributes.getString(R.styleable.AnimatorView_while_progress_text) == null ?
                WHILE_PROCESSINGTEXT :
                attributes.getString(R.styleable.AnimatorView_while_progress_text);
        onCompletedProcessTextColor = attributes.getColor(R.styleable.AnimatorView_onCompleted_text_color, default_text_color);

        whileProcessTextColor = attributes.getColor(R.styleable.AnimatorView_while_progress_text_color, default_text_color);
        processTitleColor = attributes.getColor(R.styleable.AnimatorView_process_title_color, default_text_color);
        showProgress = attributes.getBoolean(R.styleable.AnimatorView_show_progress, true);
        mPrefix = attributes.getString(R.styleable.AnimatorView_progress_prefix) == null ? "" :
                attributes.getString(R.styleable.AnimatorView_progress_prefix) + " ";
        mSuffix = attributes.getString(R.styleable.AnimatorView_progress_suffix) == null ? "%" :
                " " + attributes.getString(R.styleable.AnimatorView_progress_suffix);
        mPrefixTextColor = attributes.getColor(R.styleable.AnimatorView_progress_prefix_color, default_text_color);
        mSuffixTextColor = attributes.getColor(R.styleable.AnimatorView_progress_suffix_color, default_text_color);
        mCurrentProcessTextColor = attributes.getColor(R.styleable.AnimatorView_current_progress_text_color, default_text_color);

        if (textViewPosition == 0)
            animatorTextView.setText(progressTitle);
        attributes.recycle();
    }

    @Override
    public void onProgressChange(int current, int max) {
        if (textViewPosition == 0) {
            if (current == 0) {
                animatorTextView.setText(progressTitle);
                animatorTextView.setTextColor(processTitleColor);
            } else if (current == max) {
                animatorTextView.setText(onCompletedText);
                animatorTextView.setTextColor(onCompletedProcessTextColor);
                animatorTextView.clearAnimation();
                ObjectAnimator animator = customAnimationProvider.fadeInAnimation(animatorTextView);
                animator.setAutoCancel(true);
                animator.start();
            } else {
                animatorTextView.setText(whileProcessingText);
                animatorTextView.setTextColor(whileProcessTextColor);

            }

        } else if (textViewPosition == 1) {
            if (current == max) {
                animatorTextView.clearAnimation();
//                ObjectAnimator animator = customAnimationProvider.fadeOutAnimation(animatorTextView);
//                animator.setAutoCancel(true);
//                animator.setDuration(300);
//                animator.start();
                animatorTextView.setVisibility(GONE);
            } else if (current != 0) {
                animatorTextView.clearAnimation();
                animatorTextView.setVisibility(VISIBLE);
                String mTExt = String.format("<font color=%d>%s</font><font color=%d>%d</font><font color=%d>%s</font>",
                        mPrefixTextColor, mPrefix, mCurrentProcessTextColor, current, mSuffixTextColor, mSuffix);
                animatorTextView.setText(showProgress ?
                        (Html.fromHtml(mTExt)) : whileProcessingText);

            }
        }

    }
}

