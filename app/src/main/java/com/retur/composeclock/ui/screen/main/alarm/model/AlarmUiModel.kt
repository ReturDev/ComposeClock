package com.retur.composeclock.ui.screen.main.alarm.model

import android.content.Context
import com.retur.composeclock.R
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.ChronoUnit

/**
 * Data class representing an alarm with various properties and methods to retrieve its state and time until it sounds.
 *
 * @param id Unique identifier for the alarm.
 * @param name Name of the alarm.
 * @param isActive Flag indicating if the alarm is active.
 * @param time The time at which the alarm is set to go off.
 * @param vibrationEnabled Flag indicating if vibration is enabled for the alarm.
 * @param repetition The repetition model for the alarm.
 */
data class AlarmUiModel(
    val id: Int,
    val name: String,
    val isActive: Boolean,
    val time: LocalTime,
    val vibrationEnabled: Boolean,
    val repetition: AlarmRepetitionUiModel
) {

    /**
     * Returns the repetition state of the alarm as a string.
     *
     * @param context The context for accessing resources.
     * @param currentDateTime The current date and time.
     * @return The repetition state as a string.
     */
    fun getRepetitionState(context: Context, currentDateTime: LocalDateTime): String {
        val stringBuilder = StringBuilder(repetition.getAlarmRepetitionTag(context))
        if (isActive) {
            stringBuilder.append(" | ")
            stringBuilder.append(
                getTimeUntilSoundMsg(context, currentDateTime.toLocalDate(), currentDateTime.toLocalTime())
            )
        }
        return stringBuilder.toString()
    }

    /**
     * Returns the message indicating the time until the alarm sounds.
     *
     * @param context The context for accessing resources.
     * @param currentDate The current date.
     * @param currentTime The current time.
     * @return The message as a string.
     */
    fun getTimeUntilSoundMsg(context: Context, currentDate: LocalDate, currentTime: LocalTime): String {
        return context.getString(
            R.string.alarm_will_go_off_in_msg,
            getRepetitionStateTime(context, currentDate, currentTime)
        )
    }

    /**
     * Calculates the time until the alarm sounds and returns it as a formatted string.
     *
     * @param context The context for accessing resources.
     * @param currentDate The current date.
     * @param currentTime The current time.
     * @return A StringBuilder containing the formatted time until the alarm sounds.
     */
    private fun getRepetitionStateTime(context: Context, currentDate: LocalDate, currentTime: LocalTime): StringBuilder {
        return StringBuilder().apply {
            val daysUntilNextSound = repetition.getDaysToNextDay(currentDate)

            if (daysUntilNextSound > 0) {
                append(
                    context.resources.getQuantityString(R.plurals.days, daysUntilNextSound, daysUntilNextSound),
                    " "
                )
            }

            val hoursUntilSound = ChronoUnit.HOURS.between(time, currentTime).toInt()
            val minutesUntilSound = ChronoUnit.MINUTES.between(time, currentTime).toInt()

            if (hoursUntilSound > 0) {
                append(
                    context.resources.getQuantityString(R.plurals.hours, hoursUntilSound, hoursUntilSound),
                    " "
                )
            }

            if (minutesUntilSound > 0) {
                append(
                    context.resources.getQuantityString(R.plurals.minutes, minutesUntilSound, minutesUntilSound)
                )
            } else {
                append(
                    context.resources.getString(R.string.alarm_in_minus_of_one_minute)
                )
            }
        }
    }
}
