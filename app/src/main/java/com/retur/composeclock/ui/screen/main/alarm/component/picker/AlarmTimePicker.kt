package com.retur.composeclock.ui.screen.main.alarm.component.picker

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.retur.composeclock.ui.core.model.TimeUtil
import github.returdev.multipickers.core.PickerDefaults
import github.returdev.multipickers.core.PickerLength
import github.returdev.multipickers.core.PickerState
import github.returdev.multipickers.types.text.TextPicker

/**
 * A composable function for displaying a picker for selecting alarm time.
 *
 * @param modifier The modifier to be applied to the component. Default is Modifier.
 * @param pickerState The state of the alarm time picker.
 * @param showDivider Whether to show a divider between the hour and minute pickers. Default is false.
 * @param textStyle The style to be applied to the text. Default is PickerDefaults.pickerTextStyle.
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AlarmTimePicker(
    modifier: Modifier = Modifier,
    pickerState: AlarmTimePickerState,
    showDivider: Boolean = false,
    textStyle: TextStyle = PickerDefaults.pickerTextStyle
) {
    Row(
        modifier = modifier.height(IntrinsicSize.Min)
    ) {
        TextPicker(
            modifier = Modifier.weight(1f),
            items = TimeUtil.hoursRangeString,
            pickerState = pickerState.hourState,
            textStyle = textStyle
        )

        if (showDivider) {
            Divider(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .fillMaxHeight()
                    .width(1.dp)
            )
        }

        TextPicker(
            modifier = Modifier.weight(1f),
            items = TimeUtil.minutesAndSecondsRangeString,
            pickerState = pickerState.minuteState,
            textStyle = textStyle
        )
    }
}

/**
 * Represents the state of the alarm time picker.
 *
 * @property hourState The state of the hour picker.
 * @property minuteState The state of the minute picker.
 */
data class AlarmTimePickerState internal constructor(
    internal val hourState: PickerState,
    internal val minuteState: PickerState
) {
    /**
     * Returns the selected index of the hour picker.
     */
    val hourSelectedIndex
        get() = hourState.itemSelectedIndex

    /**
     * Returns the selected index of the minute picker.
     */
    val minuteSelectedIndex
        get() = minuteState.itemSelectedIndex

}

/**
 * Remembers the state of the alarm time picker.
 *
 * @param initialHour The initial hour to be selected. Default is 0.
 * @param initialMinute The initial minute to be selected. Default is 0.
 * @param pickersLength The length of the pickers. Default is PickerLength.SHORT.
 * @param key An optional key to control when the state should be recreated. Default is Unit.
 * @return The remembered state of the alarm time picker.
 */
@Composable
fun rememberAlarmTimePickerState(
    initialHour: Int = 0,
    initialMinute: Int = 0,
    pickersLength: PickerLength = PickerLength.SHORT,
    key : Any? = Unit
): AlarmTimePickerState {
    return remember(key1 = key){
        AlarmTimePickerState(
            hourState = PickerState(
                itemsCount = TimeUtil.hoursRange.size,
                initialItemSelectedIndex = TimeUtil.hoursRange.indexOf(initialHour),
                pickerLength = pickersLength
            ),
            minuteState = PickerState(
                itemsCount = TimeUtil.minutesAndSecondsRange.size,
                initialItemSelectedIndex = TimeUtil.minutesAndSecondsRange.indexOf(initialMinute),
                pickerLength = pickersLength
            )
        )
    }
}
