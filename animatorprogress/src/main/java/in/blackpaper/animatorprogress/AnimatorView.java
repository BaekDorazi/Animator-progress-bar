package in.blackpaper.animatorprogress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

public class AnimatorView extends RelativeLayout implements OnProgressBarListener {
    private AnimatorTextView mOnCompletedText, mWhileLoadingText;
    private AnimatorImageView mProcessImageView;
    private ProgressReporter mProgressReporter;


    /*process title text*/
    private String progressTitle;

    /*process title color*/
    private int processTitleColor;

    //preProcessing Drawable
    private Drawable preProcessingDrawable;

    //postProcessing Drawable
    private Drawable postProcessingDrawable;

    /*default values*/
    private final int default_text_color = Color.rgb(245, 245, 245);

    public AnimatorView(Context context) {
        super(context);

    }

    public AnimatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public AnimatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }


    public AnimatorView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);


    }

    public void init(AttributeSet attrs) {
        LayoutInflater mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = mInflater.inflate(R.layout.item_progress_layout, this);

        /*custom view initiaization*/
        mOnCompletedText = view.findViewById(R.id.completed_text);
        mOnCompletedText.init(attrs, mOnCompletedText, 0);
        mWhileLoadingText = view.findViewById(R.id.while_process_text);
        mWhileLoadingText.init(attrs, mWhileLoadingText, 1);
        mProcessImageView = view.findViewById(R.id.action_drawable);
        mProcessImageView.init(attrs, mProcessImageView);
        mProgressReporter = view.findViewById(R.id.progress_reporter);
        mProgressReporter.init(attrs);


        /*onProcess Change listener*/
        mProgressReporter.setOnProgressBarListener(this);
        mProgressReporter.setOnProgressBarListener(mOnCompletedText);
        mProgressReporter.setOnProgressBarListener(mWhileLoadingText);
        mProgressReporter.setOnProgressBarListener(mProcessImageView);


        TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.AnimatorView);

        progressTitle = attributes.getString(R.styleable.AnimatorView_process_title) == null ? "Download" :
                attributes.getString(R.styleable.AnimatorView_process_title);
        processTitleColor = attributes.getColor(R.styleable.AnimatorView_process_title_color, default_text_color);
        preProcessingDrawable = attributes.getDrawable(R.styleable.AnimatorView_pre_drawable) == null ?
                getContext().getResources().getDrawable(R.drawable.ic_file) : attributes.getDrawable(R.styleable.AnimatorView_pre_drawable);
        postProcessingDrawable = attributes.getDrawable(R.styleable.AnimatorView_post_drawable) == null ?
                getContext().getResources().getDrawable(R.drawable.ic_correct_mark) : attributes.getDrawable(R.styleable.AnimatorView_post_drawable);

        if (mProgressReporter.getProgress() == 0)
            mProgressReporter.setVisibility(GONE);

        mOnCompletedText.setText(progressTitle);
        mOnCompletedText.setTextColor(processTitleColor);
        mProcessImageView.setBackgroundDrawable(preProcessingDrawable);
        attributes.recycle();
    }


    @Override
    public void onProgressChange(int current, int max) {
        //TODO: float value support
//        ObjectAnimator imageViewAnimator = (ObjectAnimator) AnimatorInflater.loadAnimator(getContext(), R.animator.image_fall_animation);

        if (current == max) {
            mProgressReporter.setVisibility(VISIBLE);

        } else if (current == 0) {
            mProgressReporter.setVisibility(VISIBLE);
        } else {
            mProgressReporter.setVisibility(VISIBLE);

        }
    }

    public ProgressReporter getProgressReporter() {
        return mProgressReporter;
    }

    public AppCompatImageView getActionView() {
        return mProcessImageView;
    }


}
