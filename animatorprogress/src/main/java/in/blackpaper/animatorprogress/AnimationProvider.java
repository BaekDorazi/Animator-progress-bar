package in.blackpaper.animatorprogress;

import android.view.View;

public interface AnimationProvider {
    public android.animation.ObjectAnimator fadeOutAnimation(View view);
    public android.animation.ObjectAnimator fadeInAnimation(View view);
    public android.animation.ObjectAnimator squeezeAnimation(View view);
}
