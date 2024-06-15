package com.retur.composeclock.ui.screen.main.alarm.model

import android.content.Context
import com.retur.composeclock.R
import com.retur.composeclock.ui.core.model.getAppLocale
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.time.temporal.ChronoUnit

/**
 * Sealed class representing different alarm repetition models.
 *
 * @param id Unique identifier for the alarm repetition model.
 * @param startDate The start date of the alarm repetition.
 */
sealed class AlarmRepetitionUiModel(
    val id: Int,
    val startDate: LocalDate,
) {

    // Abstract properties and functions to be implemented by subclasses
    abstract val deleteAfterGoesOff: Boolean
    abstract val repetitionDays: List<DayOfWeek>
    abstract fun getAlarmRepetitionTag(context: Context): String

    /**
     * Calculates the number of days until the next repetition day from the current date.
     *
     * @param currentDate The current date.
     * @return The number of days to the next repetition day.
     */
    open fun getDaysToNextDay(currentDate: LocalDate): Int {
        val currentDay = currentDate.dayOfWeek
        var daysToNextDay = 0
        for (i in 1L..7L) {
            val nextDay = currentDay.plus(i)
            if (repetitionDays.contains(nextDay)) {
                daysToNextDay++
                break
            }
        }
        return daysToNextDay
    }

    /**
     * One-time alarm repetition model.
     *
     * @param id Unique identifier for the alarm repetition model.
     * @param startDate The start date of the alarm repetition.
     * @param deleteAfterGoesOff Flag indicating if the alarm should be deleted after it goes off.
     */
    class OneTime(
        id: Int,
        startDate: LocalDate,
        override var deleteAfterGoesOff: Boolean
    ) : AlarmRepetitionUiModel(id, startDate) {
        override val repetitionDays: List<DayOfWeek> = emptyList()

        override fun getAlarmRepetitionTag(context: Context): String {
            return context.getString(R.string.alarm_repetition_one_time)
        }

        override fun getDaysToNextDay(currentDate: LocalDate): Int {
            return ChronoUnit.DAYS.between(startDate, currentDate).toInt()
        }
    }

    /**
     * Daily alarm repetition model.
     *
     * @param id Unique identifier for the alarm repetition model.
     * @param startDate The start date of the alarm repetition.
     */
    class Daily(id: Int, startDate: LocalDate) : AlarmRepetitionUiModel(id, startDate) {
        override val deleteAfterGoesOff: Boolean = false
        override val repetitionDays: List<DayOfWeek> = DayOfWeek.entries

        override fun getAlarmRepetitionTag(context: Context): String {
            return context.getString(R.string.alarm_repetition_daily)
        }
    }

    /**
     * Monday to Friday alarm repetition model.
     *
     * @param id Unique identifier for the alarm repetition model.
     * @param startDate The start date of the alarm repetition.
     */
    class MondayToFriday(id: Int, startDate: LocalDate) : AlarmRepetitionUiModel(id, startDate) {
        override val deleteAfterGoesOff: Boolean = false
        override val repetitionDays: List<DayOfWeek> = listOf(
            DayOfWeek.MONDAY,
            DayOfWeek.TUESDAY,
            DayOfWeek.WEDNESDAY,
            DayOfWeek.THURSDAY,
            DayOfWeek.FRIDAY
        )

        override fun getAlarmRepetitionTag(context: Context): String {
            return context.getString(R.string.alarm_repetition_monday_to_friday)
        }
    }

    /**
     * Custom alarm repetition model.
     *
     * @param id Unique identifier for the alarm repetition model.
     * @param startDate The start date of the alarm repetition.
     * @param repetitionDays List of days on which the alarm should repeat.
     */
    class Custom(
        id: Int,
        startDate: LocalDate,
        override val repetitionDays: List<DayOfWeek>
    ) : AlarmRepetitionUiModel(id, startDate) {
        override val deleteAfterGoesOff: Boolean = false

        override fun getAlarmRepetitionTag(context: Context): String {
            val daysString = repetitionDays.joinToString(" ") {
                it.getDisplayName(TextStyle.SHORT, getAppLocale(context)).lowercase()
            }
            return daysString.replaceFirstChar { it.uppercase() }
        }
    }

    companion object {
        /**
         * Factory method to create an appropriate AlarmRepetitionUiModel instance based on the given parameters.
         *
         * @param id Unique identifier for the alarm repetition model.
         * @param startDate The start date of the alarm repetition.
         * @param repetitionDays List of days on which the alarm should repeat.
         * @param deleteAfterGoesOff Flag indicating if the alarm should be deleted after it goes off.
         * @return An instance of AlarmRepetitionUiModel.
         */
        fun getAlarmRepetitionUiClassByRepetitionDays(
            id: Int,
            startDate: LocalDate,
            repetitionDays: List<DayOfWeek>,
            deleteAfterGoesOff: Boolean = false
        ): AlarmRepetitionUiModel {
            return when {
                repetitionDays.isEmpty() -> OneTime(id, startDate, deleteAfterGoesOff)
                repetitionDays.size == 7 -> Daily(id, startDate)
                repetitionDays.containsAll(DayOfWeek.entries.subList(0, 5)) -> MondayToFriday(id, startDate)
                else -> Custom(id, startDate, repetitionDays)
            }
        }
    }
}
