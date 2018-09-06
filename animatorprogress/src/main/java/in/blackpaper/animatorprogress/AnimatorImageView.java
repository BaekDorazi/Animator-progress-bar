package in.blackpaper.animatorprogress;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

public class AnimatorImageView extends ImageView implements OnProgressBarListener {
    /*
    pre process drawable*/
    private Drawable mPreProcessDrawable;
    /*
    post process drawable*/
    private Drawable mPostProcessDrawable;
    /*
    during process drawable*/
    private Drawable mDuringProcessDrawable;
    /*
    pre process drawable*/
    private int mPreProcessDrawableColor;
    /*
    post process drawable*/
    private int mPostProcessDrawableColor;
    /*
    during process drawable*/
    private int mDuringProcessDrawableColor;
    /*
    self refrence*/
    private AnimatorImageView animatorImageView;

    /*animated block height*/
    private float mAnimatedBlockHeight;

    /*animated block widht*/
    private float mAnimatedBlockWidth;

    /*custom animation provider object*/
    CustomAnimationProvider customAnimationProvider = new CustomAnimationProvider();

    /*default values*/
    private int defaultImageColor = Color.rgb(102, 204, 102);
    private float default_reached_bar_height;
    private float default_reached_bar_width;

    public AnimatorImageView(Context context) {
        super(context);
    }


    public AnimatorImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public AnimatorImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init(AttributeSet attributeSet, AnimatorImageView animatorImageView) {

        this.animatorImageView = animatorImageView;
//        default_reached_bar_height = dp2px(animatorImageView.getHeight() * 2);
//        default_reached_bar_width = dp2px(animatorImageView.getWidth());

        TypedArray attributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.AnimatorView);

//        mAnimatedBlockHeight = attributes.getDimension(R.styleable.AnimatorView_animation_block_height, default_reached_bar_height);
//        mAnimatedBlockWidth = attributes.getDimension(R.styleable.AnimatorView_animation_block_width, default_reached_bar_width);
        mPreProcessDrawable = attributes.getDrawable(R.styleable.AnimatorView_pre_drawable) == null ?
                getContext().getResources().getDrawable(R.drawable.ic_file) :
                attributes.getDrawable(R.styleable.AnimatorView_pre_drawable);
//        mPostProcessDrawableColor = attributes.getColor(R.styleable.AnimatorView_pre_drawable_color, defaultImageColor);
        mPostProcessDrawable = attributes.getDrawable(R.styleable.AnimatorView_pre_drawable) == null ?
                getContext().getResources().getDrawable(R.drawable.ic_correct_mark) :
                attributes.getDrawable(R.styleable.AnimatorView_post_drawable);
//        mPostProcessDrawableColor = attributes.getColor(R.styleable.AnimatorView_post_drawable_color, defaultImageColor);
        mDuringProcessDrawable = attributes.getDrawable(R.styleable.AnimatorView_pre_drawable) == null ?
                getContext().getResources().getDrawable(R.drawable.ic_file) :
                attributes.getDrawable(R.styleable.AnimatorView_pre_drawable);
//        mPostProcessDrawableColor = attributes.getColor(R.styleable.AnimatorView_during_process_drawable_color, defaultImageColor);

        animatorImageView.setMeasuredDimension((int) mAnimatedBlockHeight, (int) mAnimatedBlockWidth);
        animatorImageView.setBackgroundDrawable(mPreProcessDrawable);
        attributes.recycle();
    }

    @Override
    public void onProgressChange(int current, int max) {
        ObjectAnimator imageViewAnimator = (ObjectAnimator) AnimatorInflater.loadAnimator(getContext(), R.animator.image_fall_animation);
        imageViewAnimator.setTarget(this);

        if (current == 0) {
            animatorImageView.setBackgroundDrawable(mPreProcessDrawable);
        } else if (current == max) {
            animatorImageView.setVisibility(VISIBLE);
            animatorImageView.setBackgroundDrawable(mPostProcessDrawable);
            animatorImageView.clearAnimation();
            ObjectAnimator animator = customAnimationProvider.fadeInAnimation(animatorImageView);
            animator.setAutoCancel(true);
            animator.start();
        } else {
            animatorImageView.setBackgroundDrawable(mDuringProcessDrawable);
//            imageViewAnimator.setTarget(animatorImageView);
//            imageViewAnimator.start();
        }


    }

    public float dp2px(float dp) {
        final float scale = getResources().getDisplayMetrics().density;
        return dp * scale + 0.5f;
    }

}
