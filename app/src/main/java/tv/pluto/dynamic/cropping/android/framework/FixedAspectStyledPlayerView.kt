package tv.pluto.dynamic.cropping.android.framework

import android.content.Context
import android.util.AttributeSet
import com.google.android.exoplayer2.ui.StyledPlayerView

class FixedAspectStyledPlayerView(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int,
) : StyledPlayerView(context, attrs, defStyleAttr) {

    /**
     * Desired width/height ratio
     */
    private var ratioWidth = 0
    private var ratioHeight = 0

    constructor(c: Context) : this(c, null, 0)
    constructor(c: Context, attrs: AttributeSet?) : this(c, attrs, 0)

    /**
     * Sets the aspect ratio for this view. The size of the view will be measured based on the ratio
     * calculated from the parameters. Note that the actual sizes of parameters don't matter, that
     * is, calling setAspectRatio(2, 3) and setAspectRatio(4, 6) make the same result.
     *
     * @param width  Relative horizontal size
     * @param height Relative vertical size
     */
    fun setAspectRatio(width: Int, height: Int) {
        require(width >= 0 && height >= 0) { "Size cannot be negative." }
        ratioWidth = width
        ratioHeight = height
        requestLayout()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        if (0 == ratioWidth || 0 == ratioHeight) {
            setMeasuredDimension(width, height)
        } else {
            if (width < height * ratioWidth / ratioHeight) {
                setMeasuredDimension(width, width * ratioHeight / ratioWidth)
            } else {
                setMeasuredDimension(height * ratioWidth / ratioHeight, height)
            }
        }
    }
}