package com.retur.composeclock.ui.screen.main.alarm.component.item

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.retur.composeclock.R
import com.retur.composeclock.ui.core.composable.clickable.CircularCheckbox
import com.retur.composeclock.ui.core.composable.clock.ClockText
import com.retur.composeclock.ui.core.model.TimeUtil
import com.retur.composeclock.ui.screen.main.alarm.component.picker.AlarmTimePicker
import com.retur.composeclock.ui.screen.main.alarm.component.picker.AlarmTimePickerState
import com.retur.composeclock.ui.screen.main.alarm.component.picker.rememberAlarmTimePickerState
import github.returdev.multipickers.core.PickerDefaults


/**
 * A composable function to display an alarm item with various configurations including a time picker and additional settings.
 *
 * @param modifier The modifier to be applied to the component. Default is Modifier.
 * @param alarmName The name of the alarm.
 * @param alarmRepetition The repetition setting of the alarm.
 * @param alarmHour The initial hour of the alarm.
 * @param alarmMinute The initial minute of the alarm.
 * @param isAlarmActive Whether the alarm is active.
 * @param isSelectionMode Whether the selection mode is enabled.
 * @param isSelected Whether the alarm is selected.
 * @param isItemExpanded Whether the alarm item is expanded.
 * @param onSelectionChange A lambda to handle selection changes.
 * @param onStateChange A lambda to handle state changes.
 * @param onDoneBtn A lambda to handle the done button click.
 * @param onAdditionalSettingsBtn A lambda to handle the additional settings button click.
 */
@Composable
fun AlarmItem(
    modifier: Modifier = Modifier,
    alarmName: String,
    alarmRepetition: String,
    alarmHour: Int,
    alarmMinute: Int,
    isAlarmActive: Boolean,
    isSelectionMode: Boolean,
    isSelected: Boolean,
    isItemExpanded: Boolean,
    onSelectionChange: (Boolean) -> Unit,
    onStateChange: (Boolean) -> Unit,
    onDoneBtn: () -> Unit,
    onAdditionalSettingsBtn: () -> Unit
) {
    val pickerState = rememberAlarmTimePickerState(
        initialHour = alarmHour,
        initialMinute = alarmMinute,
        key = isItemExpanded
    )

    AlarmItemComposable(
        modifier = modifier,
        pickerState = pickerState,
        alarmName = alarmName,
        alarmRepetition = alarmRepetition,
        alarmTime = {
            if (isItemExpanded) {
                TimeUtil.formatHourAndMinute(
                    TimeUtil.hoursRange[pickerState.hourSelectedIndex],
                    TimeUtil.minutesAndSecondsRange[pickerState.minuteSelectedIndex]
                )
            } else {
                TimeUtil.formatHourAndMinute(alarmHour, alarmMinute)
            }
        },
        isItemExpanded = isItemExpanded,
        isAlarmActive = isAlarmActive,
        isSelectionMode = isSelectionMode,
        isSelected = isSelected,
        onAdditionalSettings = onAdditionalSettingsBtn,
        onSelectionChange = onSelectionChange,
        onStateChange = onStateChange,
        onDone = onDoneBtn
    )
}

/**
 * A composable function that represents the content of the alarm item.
 *
 * @param modifier The modifier to be applied to the component. Default is Modifier.
 * @param pickerState The state of the alarm time picker.
 * @param alarmName The name of the alarm.
 * @param alarmRepetition The repetition setting of the alarm.
 * @param alarmTime A lambda to return the formatted alarm time string.
 * @param isAlarmActive Whether the alarm is active.
 * @param isSelectionMode Whether the selection mode is enabled.
 * @param isSelected Whether the alarm is selected.
 * @param isItemExpanded Whether the alarm item is expanded.
 * @param onSelectionChange A lambda to handle selection changes.
 * @param onStateChange A lambda to handle state changes.
 * @param onAdditionalSettings A lambda to handle the additional settings button click.
 * @param onDone A lambda to handle the done button click.
 */
@Composable
private fun AlarmItemComposable(
    modifier: Modifier = Modifier,
    pickerState: AlarmTimePickerState,
    alarmName: String,
    alarmRepetition: String,
    alarmTime: () -> String,
    isAlarmActive: Boolean,
    isSelectionMode: Boolean,
    isSelected: Boolean,
    isItemExpanded: Boolean,
    onSelectionChange: (Boolean) -> Unit,
    onStateChange: (Boolean) -> Unit,
    onAdditionalSettings: () -> Unit,
    onDone: () -> Unit
) {
    Card(
        modifier = modifier.animateContentSize()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            AlarmItemContent(
                modifier = Modifier.padding(if (isItemExpanded) 16.dp else 0.dp),
                alarmName = alarmName,
                alarmState = alarmRepetition,
                alarmTime = alarmTime,
                isAlarmActive = isAlarmActive,
                changeStateContent = {
                    if (isSelectionMode) {
                        CircularCheckbox(
                            modifier = it,
                            isChecked = isSelected,
                            onCheckedChange = onSelectionChange
                        )
                    } else {
                        Switch(
                            modifier = it,
                            checked = isAlarmActive,
                            onCheckedChange = onStateChange
                        )
                    }
                }
            )

            if (isItemExpanded) {
                AlarmItemExpandedContent(
                    pickerState = pickerState,
                    onAdditionalSettings = onAdditionalSettings,
                    onDone = onDone
                )
            }
        }
    }
}

/**
 * A composable function that represents the main content of the alarm item.
 *
 * @param modifier The modifier to be applied to the component. Default is Modifier.
 * @param alarmName The name of the alarm.
 * @param alarmState The state of the alarm.
 * @param alarmTime A lambda to return the formatted alarm time string.
 * @param isAlarmActive Whether the alarm is active.
 * @param changeStateContent A composable lambda to change the state content.
 */
@Composable
private fun AlarmItemContent(
    modifier: Modifier = Modifier,
    alarmName: String,
    alarmState: String,
    alarmTime: () -> String,
    isAlarmActive: Boolean,
    changeStateContent: @Composable (Modifier) -> Unit
) {
    val activeTextColor = LocalContentColor.current
    val inactiveTextColor = remember {
        activeTextColor.copy(0.7f)
    }
    val activeClockTextStyle = remember {
        TextStyle(
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = activeTextColor
        )
    }
    val inactiveClockTextStyle = remember {
        activeClockTextStyle.copy(
            color = inactiveTextColor
        )
    }

    ConstraintLayout(
        modifier = modifier.fillMaxWidth()
    ) {
        val (timeCons, tagCons, changeStateCons, stateCons) = createRefs()
        val barrier = createStartBarrier(changeStateCons, margin = (-4).dp)

        ClockText(
            modifier = Modifier.constrainAs(timeCons) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            },
            time = alarmTime,
            textStyle = if (isAlarmActive) activeClockTextStyle else inactiveClockTextStyle
        )

        Text(
            modifier = Modifier.constrainAs(tagCons) {
                start.linkTo(timeCons.end)
                end.linkTo(barrier)
                bottom.linkTo(timeCons.bottom, margin = 4.dp)
                width = Dimension.fillToConstraints
            },
            text = alarmName,
            color = if (isAlarmActive) activeTextColor else inactiveTextColor,
            overflow = TextOverflow.Ellipsis,
            softWrap = false
        )

        Text(
            modifier = Modifier.constrainAs(stateCons) {
                top.linkTo(timeCons.bottom)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(changeStateCons.start, margin = 4.dp)
                width = Dimension.fillToConstraints
            },
            text = alarmState,
            color = if (isAlarmActive) activeTextColor else inactiveTextColor,
            style = MaterialTheme.typography.bodySmall,
            overflow = TextOverflow.Ellipsis,
            softWrap = false
        )

        changeStateContent(
            Modifier.constrainAs(changeStateCons) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                end.linkTo(parent.end)
            }
        )
    }
}

/**
 * A composable function that represents the expanded content of the alarm item, including the time picker and additional settings button.
 *
 * @param modifier The modifier to be applied to the component. Default is Modifier.
 * @param pickerState The state of the alarm time picker.
 * @param onAdditionalSettings A lambda to handle the additional settings button click.
 * @param onDone A lambda to handle the done button click.
 */
@Composable
private fun AlarmItemExpandedContent(
    modifier: Modifier = Modifier,
    pickerState: AlarmTimePickerState,
    onAdditionalSettings: () -> Unit,
    onDone: () -> Unit
) {
    val pickerTextStyle = remember {
        PickerDefaults.pickerTextStyle.copy(
            fontSize = 40.sp
        )
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AlarmTimePicker(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .padding(vertical = 16.dp),
            pickerState = pickerState,
            showDivider = true,
            textStyle = pickerTextStyle
        )
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                modifier = Modifier.weight(1f),
                onClick = onAdditionalSettings
            ) {
                Text(text = stringResource(R.string.expanded_alarm_item_additional_settings))
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                modifier = Modifier.weight(1f),
                onClick = onDone
            ) {
                Text(text = stringResource(R.string.expanded_alarm_item_done))
            }
        }
    }
}
