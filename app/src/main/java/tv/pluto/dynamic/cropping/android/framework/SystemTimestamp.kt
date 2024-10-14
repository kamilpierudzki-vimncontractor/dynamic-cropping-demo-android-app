package tv.pluto.dynamic.cropping.android.framework

import tv.pluto.dynamic.cropping.android.logic.TimestampProvider

class SystemTimestamp: TimestampProvider { // todo remove

    override val timestampMs: Long
        get() = System.currentTimeMillis()
}