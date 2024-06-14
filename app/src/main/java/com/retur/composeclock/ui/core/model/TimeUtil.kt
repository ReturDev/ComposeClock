package com.retur.composeclock.ui.core.model


/**
 * Utility object for handling and formatting time-related data.
 */
object TimeUtil {

    // Constants for time formatting
    private const val TIME_FORMAT = "%02d"
    private const val HOUR_AND_MINUTE_FORMAT = "$TIME_FORMAT:$TIME_FORMAT"

    // Ranges for hours and minutes
    val hoursRange = (0..23).toList()
    val minutesAndSecondsRange = (0..59).toList()

    // Formatted ranges as strings
    val hoursRangeString = hoursRange.map { formatTime(it) }
    val minutesAndSecondsRangeString = minutesAndSecondsRange.map { formatTime(it) }

    /**
     * Formats the given time unit to a two-digit string with leading zero if necessary.
     *
     * @param time The time unit to be formatted.
     * @return The formatted time unit as a string.
     */
    fun formatTime(time : Int) : String {
        return TIME_FORMAT.format(time)
    }

    /**
     * Formats the given hour and minute into a string in the format "HH:mm".
     *
     * @param hour The hour to be formatted.
     * @param minute The minute to be formatted.
     * @return The formatted hour and minute as a string.
     */
    fun formatHourAndMinute(hour : Int, minute : Int) : String {
        return HOUR_AND_MINUTE_FORMAT.format(hour, minute)
    }

}

