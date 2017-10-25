/*
 * Copyright (C) 2017 Samuel Wall
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package vn.ntlogistics.app.ordermanagement.Commons.MaterialTapTargetPrompt.extras.focals;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.view.View;

import vn.ntlogistics.app.ordermanagement.Commons.MaterialTapTargetPrompt.extras.PromptFocal;
import vn.ntlogistics.app.ordermanagement.Commons.MaterialTapTargetPrompt.extras.PromptOptions;
import vn.ntlogistics.app.ordermanagement.Commons.MaterialTapTargetPrompt.extras.PromptUtils;


/**
 * Prompt focal implementation to draw the focal as a rectangle.
 */
public class RectanglePromptFocal extends PromptFocal
{
    Paint mPaint;
    // Paint mBoundsPaint;
    int mRippleAlpha;
    RectF mBounds;
    RectF mBaseBounds;
    PointF mBaseBoundsCentre;
    RectF mRippleBounds;
    int mBaseAlpha;
    float mPadding = 40;
    private float mRx, mRy;
    private PointF mSize;

    /**
     * Constructor.
     */
    public RectanglePromptFocal()
    {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        /*mBoundsPaint = new Paint();
        mBoundsPaint.setColor(Color.BLUE);
        mBoundsPaint.setAlpha(100);*/
        mBounds = new RectF();
        mBaseBounds = new RectF();
        mBaseBoundsCentre = new PointF();
        mRippleBounds = new RectF();
        final float density = Resources.getSystem().getDisplayMetrics().density;
        mRx = mRy = 2 * density;
        mPadding = 8 * density;
    }

    /**
     * Set the radius for the rectangle corners.
     *
     * @param rx The x-radius of the oval used to round the corners
     * @param ry The y-radius of the oval used to round the corners
     * @return This prompt focal
     */
    public RectanglePromptFocal setCornerRadius(final float rx, final float ry)
    {
        mRx = rx;
        mRy = ry;
        return this;
    }

    /**
     * Set the padding between the target bounds and the rectangle edge.
     *
     * @param padding The distance from the target edge to the rectangle edge.
     * @return This prompt focal.
     */
    public RectanglePromptFocal setTargetPadding(final float padding)
    {
        mPadding = padding;
        return this;
    }

    /**
     * Sets the focal to a fixed size. Set as null to change it back to dynamic.
     *
     * @param size The fixed focal size or null to change back to the default dynamic size.
     * @return This prompt focal
     */
    public RectanglePromptFocal setSize(@Nullable final PointF size)
    {
        if (size == null)
        {
            mSize = null;
        }
        else
        {
            mSize = new PointF();
            mSize.x = size.x;
            mSize.y = size.y;
        }
        return this;
    }

    @Override
    public RectF getBounds()
    {
        return mBaseBounds;
    }

    @Override
    public void setColour(int colour)
    {
        mPaint.setColor(colour);
        mBaseAlpha = Color.alpha(colour);
        mPaint.setAlpha(mBaseAlpha);
    }

    @Override
    public void prepare(PromptOptions options, View target, final int[] promptViewPosition)
    {
        final int[] targetPosition = new int[2];
        target.getLocationInWindow(targetPosition);
        final float left = targetPosition[0] - promptViewPosition[0];
        final float top = targetPosition[1] - promptViewPosition[1];
        final int width = target.getWidth();
        final int height = target.getHeight();
        if (mSize == null)
        {
            mBaseBounds.left = left - mPadding;
            mBaseBounds.top = top - mPadding;
            mBaseBounds.right = left + width + mPadding;
            mBaseBounds.bottom = top + height + mPadding;
            mBaseBoundsCentre.x = left + (width / 2);
            mBaseBoundsCentre.y = top + (height / 2);
        }
        else
        {
            prepare(options, left + (width / 2), top + (height / 2));
        }
    }

    @Override
    public void prepare(PromptOptions options, float targetX, float targetY)
    {
        if (mSize != null)
        {
            final float halfWidth = mSize.x / 2;
            final float halfHeight = mSize.y / 2;
            mBaseBounds.left = targetX - halfWidth - mPadding;
            mBaseBounds.top = targetY - halfHeight - mPadding;
            mBaseBounds.right = targetX + halfWidth + mPadding;
            mBaseBounds.bottom = targetY + halfHeight + mPadding;
            mBaseBoundsCentre.x = targetX;
            mBaseBoundsCentre.y = targetY;
        }
        else
        {
            throw new UnsupportedOperationException("RectanglePromptFocal size must be set using setSize(PointF)");
        }
    }

    @Override
    public void update(PromptOptions options, float revealModifier,
                       float alphaModifier)
    {
        PromptUtils.scale(mBaseBoundsCentre, mBaseBounds, mBounds, revealModifier, true);
    }

    @Override
    public void updateRipple(float revealModifier, final float alphaModifier)
    {
        PromptUtils.scale(mBaseBoundsCentre, mBaseBounds, mRippleBounds, revealModifier, true);
        mRippleAlpha = (int) (mBaseRippleAlpha * alphaModifier);
    }

    @Override
    public void draw(Canvas canvas)
    {
        //Draw the ripple
        if (mDrawRipple)
        {
            final int oldAlpha = mPaint.getAlpha();
            mPaint.setAlpha(mRippleAlpha);
            canvas.drawRoundRect(mRippleBounds, mRx, mRy, mPaint);
            mPaint.setAlpha(oldAlpha);
        }

        canvas.drawRoundRect(mBounds, mRx, mRy, mPaint);

        // canvas.drawRoundRect(mBaseBounds, mRx, mRy, mBoundsPaint);
    }

    @Override
    public boolean contains(float x, float y)
    {
        return this.mBounds.contains(x, y);
    }
}