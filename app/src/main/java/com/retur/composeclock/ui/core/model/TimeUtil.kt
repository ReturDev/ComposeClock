package com.retur.composeclock.ui.core.model


/**
 * Utility object for handling time-related operations.
 */
object TimeUtil {

    private const val TIME_FORMAT = "%02d"

    val hoursRange = (0..23).toList()
    val minutesAndSecondsRange = (0..59).toList()
    val hoursRangeString = hoursRange.map { formatTime(it) }
    val minutesAndSecondsRangeString = minutesAndSecondsRange.map { formatTime(it) }

    /**
     * Formats the given time to ensure it has a length of 2, padding with a leading 0 if necessary.
     *
     * @param time The time to be formatted.
     * @return The formatted time as a string.
     */
    fun formatTime(time: Int): String {
        return TIME_FORMAT.format(time)
    }
}
