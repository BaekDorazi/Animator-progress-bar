package in.blackpaper.animatorprogress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
//
//import static in.blackpaper.progress_download.ProgressReporter.ProgressTextVisibility.Invisible;
//import static in.blackpaper.progress_download.ProgressReporter.ProgressTextVisibility.Visible;

/**
 * Created by nitin on 2-9-18.
 */
public class ProgressReporter extends View {

    private int mMaxProgress = 100;

    /**
     * Current progress, can not exceed the max progress.
     */
    private int mCurrentProgress = 0;

    /**
     * The progress area bar color.
     */
    private int mReachedBarColor;

    /**
     * The bar unreached area color.
     */
    private int mUnreachedBarColor;

    /**
     * The progress text color.
     */
    private int mTextColor;

    /**
     * The progress text size.
     */
    private float mTextSize;

    /**
     * The height of the reached area.
     */
    private float mReachedBarHeight;

    /**
     * The height of the unreached area.
     */
    private float mUnreachedBarHeight;

    /**
     * The suffix of the number.
     */
    private String mSuffix = "%";

    /**
     * The prefix.
     */
    private String mPrefix = "";


    /**
     * The Reached Progress right radius rY.
     */
    private float mReachedYRadius;


    /**
     * The Reached Progress left radius rX.
     */
    private float mReachedXRadius;


    /**
     * The Unreached Progress right radius rY.
     */
    private float mUnreachedYRadius;


    /**
     * The Unreached Progress left radius rX.
     */
    private float mUnreachedXRadius;


    /**
     * Progree bar and text margin
     */
    private float mProgressTextMargin;

    //White smoke
    private final int default_text_color = Color.rgb(245, 245, 245);
    private final int default_reached_color = Color.rgb(102, 204, 102);
    private final int default_unreached_color = Color.rgb(102, 204, 102);
    private float default_progress_text_offset;
    private float default_text_size;
    private float default_reached_bar_height;
    private float default_unreached_bar_height;

    /**
     * For save and restore instance of progressbar.
     */
    private static final String INSTANCE_STATE = "saved_instance";
    private static final String INSTANCE_TEXT_COLOR = "text_color";
    private static final String INSTANCE_TEXT_SIZE = "text_size";
    private static final String INSTANCE_REACHED_BAR_HEIGHT = "reached_bar_height";
    private static final String INSTANCE_REACHED_BAR_COLOR = "reached_bar_color";
    private static final String INSTANCE_UNREACHED_BAR_HEIGHT = "unreached_bar_height";
    private static final String INSTANCE_UNREACHED_BAR_COLOR = "unreached_bar_color";
    private static final String INSTANCE_MAX = "max";
    private static final String INSTANCE_PROGRESS = "progress";
    private static final String INSTANCE_SUFFIX = "suffix";
    private static final String INSTANCE_PREFIX = "prefix";
    private static final String INSTANCE_TEXT_VISIBILITY = "text_visibility";

    private static final int PROGRESS_TEXT_VISIBLE = 0;


    /**
     * The width of the text that to be drawn.
     */
    private float mDrawTextWidth;

    /**
     * The drawn text start.
     */
    private float mDrawTextStart;

    /**
     * The drawn text end.
     */
    private float mDrawTextEnd;

    /**
     * The text that to be drawn in onDraw().
     */
    private String mCurrentDrawText;

    /**
     * The Paint of the reached area.
     */
    private Paint mReachedBarPaint;
    /**
     * The Paint of the unreached area.
     */
    private Paint mUnreachedBarPaint;
    /**
     * The Paint of the progress text.
     */
    private Paint mTextPaint;

    /**
     * Unreached bar area to draw rect.
     */
    private RectF mUnreachedRectF = new RectF(0, 0, 0, 0);
    /**
     * Reached bar area rect.
     */
    private RectF mReachedRectF = new RectF(0, 0, 0, 0);

    /**
     * Text area rect
     */

    private Rect textBounds = new Rect();
    /**
     * The progress text offset.
     */
    private float mOffset;
    /*prefix text color*/
    private int mPrefixTextColor;

    /*suffix text color*/
    private int mSuffixTextColor;
    /**
     * Determine if need to draw unreached area.
     */
    private boolean mDrawUnreachedBar = true;

    private boolean mDrawReachedBar = true;

    private boolean mIfDrawText = true;

    /**
     * Listener
     */
    private List<OnProgressBarListener> mListener = new ArrayList<>();


    public enum ProgressTextVisibility {
        Visible, Invisible
    }

    public ProgressReporter(Context context) {
        this(context, null);
    }

    public ProgressReporter(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressReporter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


    }

    public void init(AttributeSet attrs) {
        default_reached_bar_height = dp2px(1.5f);
        default_unreached_bar_height = dp2px(1.0f);
        default_text_size = sp2px(10);
        default_progress_text_offset = dp2px(3.0f);

        //load styled attributes.
        final TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.AnimatorView);

        mReachedBarColor = attributes.getColor(R.styleable.AnimatorView_progress_reached_color, default_reached_color);
        mUnreachedBarColor = attributes.getColor(R.styleable.AnimatorView_progress_unreached_color, default_unreached_color);
        mTextColor = attributes.getColor(R.styleable.AnimatorView_progress_text_color, default_text_color);
        mTextSize = attributes.getDimension(R.styleable.AnimatorView_progress_text_size, default_text_size);

        mReachedBarHeight = attributes.getDimension(R.styleable.AnimatorView_progress_reached_bar_height, default_reached_bar_height);
        mUnreachedBarHeight = attributes.getDimension(R.styleable.AnimatorView_progress_unreached_bar_height, default_unreached_bar_height);
        mOffset = attributes.getDimension(R.styleable.AnimatorView_progress_text_offset, default_progress_text_offset);
        mPrefix = attributes.getString(R.styleable.AnimatorView_progress_prefix) == null ? "" :
                attributes.getString(R.styleable.AnimatorView_progress_prefix) + " ";
        mSuffix = attributes.getString(R.styleable.AnimatorView_progress_suffix) == null ? "%" :
                " " + attributes.getString(R.styleable.AnimatorView_progress_suffix);
        mPrefixTextColor = attributes.getColor(R.styleable.AnimatorView_progress_prefix_color, default_text_color);
        mSuffixTextColor = attributes.getColor(R.styleable.AnimatorView_progress_suffix_color, default_text_color);
        mReachedXRadius = attributes.getDimension(R.styleable.AnimatorView_progress_reachedtext_leftradius, 10);
        mReachedYRadius = attributes.getDimension(R.styleable.AnimatorView_progress_reachedtext_rightradius, 10);
        mUnreachedXRadius = attributes.getDimension(R.styleable.AnimatorView_progress_unreachedtext_leftradius, 10);
        mUnreachedYRadius = attributes.getDimension(R.styleable.AnimatorView_progress_unreachedtext_rightradius, 10);
        mProgressTextMargin = attributes.getDimension(R.styleable.AnimatorView_progressbar_text_margin, 10);

//        int textVisible = attributes.getInt(R.styleable.AnimatorView_progress_text_visibility, PROGRESS_TEXT_VISIBLE);
//        if (textVisible != PROGRESS_TEXT_VISIBLE) {
//            mIfDrawText = false;
//        }

        setProgress(attributes.getInt(R.styleable.AnimatorView_progress_current, 0));
        setMax(attributes.getInt(R.styleable.AnimatorView_progress_max, 100));

        attributes.recycle();
        initializePainters();
    }

    @Override
    protected int getSuggestedMinimumWidth() {
        return (int) mTextSize;
    }

    @Override
    protected int getSuggestedMinimumHeight() {
        return Math.max((int) mTextSize, Math.max((int) mReachedBarHeight, (int) mUnreachedBarHeight));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measure(widthMeasureSpec, true), measure(heightMeasureSpec, false));
    }

    private int measure(int measureSpec, boolean isWidth) {
        int result;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        int padding = isWidth ? getPaddingLeft() + getPaddingRight() : getPaddingTop() + getPaddingBottom();
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = isWidth ? getSuggestedMinimumWidth() : getSuggestedMinimumHeight();
            result += padding;
            if (mode == MeasureSpec.AT_MOST) {
                if (isWidth) {
                    result = Math.max(result, size);
                } else {
                    result = Math.min(result, size);
                }
            }
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mIfDrawText) {
            calculateDrawRectF(canvas);
        } else {
            calculateDrawRectFWithoutProgressText();
        }

        if (mDrawReachedBar) {
            canvas.drawRoundRect(mReachedRectF, mReachedXRadius, mReachedYRadius, mReachedBarPaint);
        }

        if (mDrawUnreachedBar) {
            canvas.drawRoundRect(mUnreachedRectF, mUnreachedXRadius, mUnreachedYRadius, mUnreachedBarPaint);
        }

//        if (mIfDrawText)
//            drawCenter(canvas, mTextPaint, mCurrentDrawText);
    }

    private void initializePainters() {
        mReachedBarPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mReachedBarPaint.setColor(mReachedBarColor);

        mUnreachedBarPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mUnreachedBarPaint.setColor(mUnreachedBarColor);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);
    }


    private void calculateDrawRectFWithoutProgressText() {
        mReachedRectF.left = getPaddingLeft();
        mReachedRectF.top = getHeight() / 2.0f - mReachedBarHeight / 2.0f;
        mReachedRectF.right = (getWidth() - getPaddingLeft() - getPaddingRight()) / (getMax() * 1.0f) * getProgress() + getPaddingLeft();
        mReachedRectF.bottom = getHeight() / 2.0f + mReachedBarHeight / 2.0f;

        mUnreachedRectF.left = mReachedRectF.right - mReachedYRadius;
        mUnreachedRectF.right = getWidth() - getPaddingRight();
        mUnreachedRectF.top = getHeight() / 2.0f + -mUnreachedBarHeight / 2.0f;
        mUnreachedRectF.bottom = getHeight() / 2.0f + mUnreachedBarHeight / 2.0f;
    }


    private void drawCenter(Canvas canvas, Paint paint, String text) {

        canvas.getClipBounds(textBounds);
        int cHeight = textBounds.height();
        int cWidth = textBounds.width();
        paint.setTextAlign(Paint.Align.LEFT);
        paint.getTextBounds(text, 0, text.length(), textBounds);
        float x = cWidth / 2f - textBounds.width() / 2f - textBounds.left;
        float y = cHeight / 2f + textBounds.height() / 2f - textBounds.bottom;
        canvas.drawText(text, x, y, paint);
    }

    private void calculateDrawRectF(Canvas canvas) {
//        canvas.scale(getHeight() + mProgressTextMargin, getHeight() + mProgressTextMargin);
        mCurrentDrawText = String.format("%d", getProgress() * 100 / getMax());
        mCurrentDrawText = (mPrefix == null ? "" : mPrefix) + mCurrentDrawText + (mSuffix == null ? "%" : mSuffix);
        mDrawTextWidth = mTextPaint.measureText(mCurrentDrawText);

        mReachedRectF.left = getPaddingLeft();
        mReachedRectF.top = getHeight() / 2.0f - mReachedBarHeight / 2.0f;
        mReachedRectF.right = (getWidth() - getPaddingLeft() - getPaddingRight()) / (getMax() * 1.0f) * getProgress() + getPaddingLeft();
        mReachedRectF.bottom = getHeight() / 2.0f + mReachedBarHeight / 2.0f;

        mUnreachedRectF.left = mReachedRectF.right - mReachedYRadius;
        mUnreachedRectF.right = getWidth() - getPaddingRight() - mReachedYRadius;
        mUnreachedRectF.top = getHeight() / 2.0f + -mUnreachedBarHeight / 2.0f;
        mUnreachedRectF.bottom = getHeight() / 2.0f + mUnreachedBarHeight / 2.0f;

//        textBounds.top = (int) (mReachedRectF.bottom + mTextSize / 2.0f + mProgressTextMargin / 2.0f);
//        textBounds.bottom = (int) (mTextSize / 2.0f);


        if (getProgress() == 0) {
            mDrawReachedBar = false;
            mDrawTextStart = getPaddingLeft();
        } else {
            mDrawReachedBar = true;
            mReachedRectF.left = getPaddingLeft();
            mReachedRectF.top = getHeight() / 2.0f - mReachedBarHeight / 2.0f;
            mReachedRectF.right = (getWidth() - getPaddingLeft() - getPaddingRight()) / (getMax() * 1.0f) * getProgress() - mOffset + getPaddingLeft();
            mReachedRectF.bottom = getHeight() / 2.0f + mReachedBarHeight / 2.0f;
//            mDrawTextStart = (mReachedRectF.right + mOffset);

        }
//
//        mDrawTextEnd = (int) ((getHeight() / 2.0f) - ((mTextPaint.descent() + mTextPaint.ascent()) / 2.0f));
//
//        if ((mDrawTextStart + mDrawTextWidth) >= getWidth() - getPaddingRight()) {
//            mDrawTextStart = getWidth() - getPaddingRight() - mDrawTextWidth;
//            mReachedRectF.right = mDrawTextStart - mOffset;
//        }
//
//        float unreachedBarStart = mDrawTextStart + mDrawTextWidth + mOffset;
//        if (unreachedBarStart >= getWidth() - getPaddingRight()) {
//            mDrawUnreachedBar = false;
//        } else {
//            mDrawUnreachedBar = true;
//            mUnreachedRectF.left = unreachedBarStart;
//            mUnreachedRectF.right = getWidth() - getPaddingRight();
//            mUnreachedRectF.top = getHeight() / 2.0f + -mUnreachedBarHeight / 2.0f;
//            mUnreachedRectF.bottom = getHeight() / 2.0f + mUnreachedBarHeight / 2.0f;
//        }
    }

    /**
     * Get progress text color.
     *
     * @return progress text color.
     */
    public int getTextColor() {
        return mTextColor;
    }

    /**
     * Get progress text size.
     *
     * @return progress text size.
     */
    public float getProgressTextSize() {
        return mTextSize;
    }

    public int getUnreachedBarColor() {
        return mUnreachedBarColor;
    }

    public int getReachedBarColor() {
        return mReachedBarColor;
    }

    public int getProgress() {
        return mCurrentProgress;
    }

    public int getMax() {
        return mMaxProgress;
    }

    public float getReachedBarHeight() {
        return mReachedBarHeight;
    }

    public float getUnreachedBarHeight() {
        return mUnreachedBarHeight;
    }

    public void setProgressTextSize(float textSize) {
        this.mTextSize = textSize;
        mTextPaint.setTextSize(mTextSize);
        invalidate();
    }

    public void setProgressTextColor(int textColor) {
        this.mTextColor = textColor;
        mTextPaint.setColor(mTextColor);
        invalidate();
    }

    public void setUnreachedBarColor(int barColor) {
        this.mUnreachedBarColor = barColor;
        mUnreachedBarPaint.setColor(mUnreachedBarColor);
        invalidate();
    }

    public void setReachedBarColor(int progressColor) {
        this.mReachedBarColor = progressColor;
        mReachedBarPaint.setColor(mReachedBarColor);
        invalidate();
    }

    public void setReachedBarHeight(float height) {
        mReachedBarHeight = height;
    }

    public void setUnreachedBarHeight(float height) {
        mUnreachedBarHeight = height;
    }

    public void setMax(int maxProgress) {
        if (maxProgress > 0) {
            this.mMaxProgress = maxProgress;
            invalidate();
        }
    }

    public void setSuffix(String suffix) {
        if (suffix == null) {
            mSuffix = "";
        } else {
            mSuffix = suffix;
        }
    }

    public String getSuffix() {
        return mSuffix;
    }

    public void setPrefix(String prefix) {
        if (prefix == null)
            mPrefix = "";
        else {
            mPrefix = prefix;
        }
    }

    public String getPrefix() {
        return mPrefix;
    }

    public void incrementProgressBy(int by) {
        if (by > 0 && (getProgress() + by) <= getMax()) {
            setProgress(getProgress() + by);


            if (mListener != null) {
                for (int i = 0; i < mListener.size(); i++)
                    mListener.get(i).onProgressChange(getProgress(), getMax());
            }
        }
    }

    public void setProgress(int progress) {

        if (progress <= getMax() && progress >= 0) {
            this.mCurrentProgress = progress;
            invalidate();
        }
        if (mListener != null) {
            for (int i = 0; i < mListener.size(); i++)
                mListener.get(i).onProgressChange(getProgress(), getMax());
        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        final Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE_STATE, super.onSaveInstanceState());
        bundle.putInt(INSTANCE_TEXT_COLOR, getTextColor());
        bundle.putFloat(INSTANCE_TEXT_SIZE, getProgressTextSize());
        bundle.putFloat(INSTANCE_REACHED_BAR_HEIGHT, getReachedBarHeight());
        bundle.putFloat(INSTANCE_UNREACHED_BAR_HEIGHT, getUnreachedBarHeight());
        bundle.putInt(INSTANCE_REACHED_BAR_COLOR, getReachedBarColor());
        bundle.putInt(INSTANCE_UNREACHED_BAR_COLOR, getUnreachedBarColor());
        bundle.putInt(INSTANCE_MAX, getMax());
        bundle.putInt(INSTANCE_PROGRESS, getProgress());
        bundle.putString(INSTANCE_SUFFIX, getSuffix());
        bundle.putString(INSTANCE_PREFIX, getPrefix());

//        bundle.putBoolean(INSTANCE_TEXT_VISIBILITY, getProgressTextVisibility());
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            final Bundle bundle = (Bundle) state;
            mTextColor = bundle.getInt(INSTANCE_TEXT_COLOR);
            mTextSize = bundle.getFloat(INSTANCE_TEXT_SIZE);
            mReachedBarHeight = bundle.getFloat(INSTANCE_REACHED_BAR_HEIGHT);
            mUnreachedBarHeight = bundle.getFloat(INSTANCE_UNREACHED_BAR_HEIGHT);
            mReachedBarColor = bundle.getInt(INSTANCE_REACHED_BAR_COLOR);
            mUnreachedBarColor = bundle.getInt(INSTANCE_UNREACHED_BAR_COLOR);
            initializePainters();
            setMax(bundle.getInt(INSTANCE_MAX));
            setProgress(bundle.getInt(INSTANCE_PROGRESS));
            setPrefix(bundle.getString(INSTANCE_PREFIX));
            setSuffix(bundle.getString(INSTANCE_SUFFIX));
//            setProgressTextVisibility(bundle.getBoolean(INSTANCE_TEXT_VISIBILITY) ? Visible : Invisible);
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATE));
            return;
        }
        super.onRestoreInstanceState(state);
    }

    public float dp2px(float dp) {
        final float scale = getResources().getDisplayMetrics().density;
        return dp * scale + 0.5f;
    }

    public float sp2px(float sp) {
        final float scale = getResources().getDisplayMetrics().scaledDensity;
        return sp * scale;
    }

//    public void setProgressTextVisibility(ProgressTextVisibility visibility) {
//        mIfDrawText = visibility == Visible;
//        invalidate();
//    }

//    public boolean getProgressTextVisibility() {
//        return mIfDrawText;
//    }

    public void setOnProgressBarListener(OnProgressBarListener listener) {
        mListener.add(listener);
    }
}
