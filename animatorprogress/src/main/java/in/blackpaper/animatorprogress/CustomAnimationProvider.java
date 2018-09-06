package in.blackpaper.animatorprogress;

import android.view.View;

public class CustomAnimationProvider implements AnimationProvider {
    @Override
    public android.animation.ObjectAnimator fadeOutAnimation(View view) {
        final android.animation.ObjectAnimator animation =
                android.animation.ObjectAnimator.ofFloat(view, View.ALPHA, 1f, 0f);

        return animation;
    }

    @Override
    public android.animation.ObjectAnimator fadeInAnimation(View view) {
        final android.animation.ObjectAnimator animation =
                android.animation.ObjectAnimator.ofFloat(view, View.ALPHA, 0f, 1f);

        return animation;

    }

    @Override
    public android.animation.ObjectAnimator squeezeAnimation(View view) {
        return null;
    }

}
