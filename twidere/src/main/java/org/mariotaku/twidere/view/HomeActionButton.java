/*
 * 				Twidere - Twitter client for Android
 * 
 *  Copyright (C) 2012-2014 Mariotaku Lee <mariotaku.lee@gmail.com>
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.mariotaku.twidere.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Outline;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import org.mariotaku.twidere.R;
import org.mariotaku.twidere.util.Utils;
import org.mariotaku.twidere.view.iface.IHomeActionButton;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class HomeActionButton extends FrameLayout implements IHomeActionButton {

    private final ImageView mIconView;
    private final ProgressBar mProgressBar;

    public HomeActionButton(final Context context) {
        this(context, null);
    }

    public HomeActionButton(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomeActionButton(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
        inflate(context, R.layout.action_item_home_actions, this);
        mIconView = (ImageView) findViewById(android.R.id.icon);
        mProgressBar = (ProgressBar) findViewById(android.R.id.progress);
        setOutlineProvider(new HomeActionButtonOutlineProvider());
        setClipToOutline(true);
    }

    @Override
    public void setColor(int color) {
        final Drawable drawable = getBackground();
        if (drawable != null) {
            drawable.setColorFilter(color, Mode.MULTIPLY);
        }
        final int contrastColor = Utils.getContrastYIQ(color, 192);
        mIconView.setColorFilter(contrastColor, Mode.SRC_ATOP);
        mProgressBar.setIndeterminateTintList(ColorStateList.valueOf(contrastColor));
    }

    @Override
    public void setIcon(final Bitmap bm) {
        mIconView.setImageBitmap(bm);
    }

    @Override
    public void setIcon(final Drawable drawable) {
        mIconView.setImageDrawable(drawable);
    }

    @Override
    public void setIcon(final int resId) {
        mIconView.setImageResource(resId);
    }

    @Override
    public void setShowProgress(final boolean showProgress) {
        mProgressBar.setVisibility(showProgress ? View.VISIBLE : View.GONE);
        mIconView.setVisibility(showProgress ? View.GONE : View.VISIBLE);
    }

    @Override
    public void setTitle(final CharSequence title) {
        setContentDescription(title);
    }

    @Override
    public void setTitle(final int title) {
        setTitle(getResources().getText(title));
    }

    private static class HomeActionButtonOutlineProvider extends ViewOutlineProvider {
        @Override
        public void getOutline(View view, Outline outline) {
            final int width = view.getWidth(), height = view.getHeight();
            final int size = Math.min(width, height);
            final int left = (width - size) / 2, top = (height - size) / 2;
            outline.setOval(left, top, left + size, top + size);
        }
    }
}
