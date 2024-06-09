package com.retur.composeclock.ui.screen.main.alarm.component.item

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.retur.composeclock.ui.core.composable.checkbox.CircularCheckbox
import com.retur.composeclock.ui.core.composable.clock.ClockText

/**
 * A composable function to display an alarm item with a switch or checkbox based on the selection mode.
 *
 * @param modifier The modifier to be applied to the component. Default is Modifier.
 * @param alarmName The name of the alarm.
 * @param alarmState The state description of the alarm.
 * @param alarmTime A lambda that returns the time string of the alarm.
 * @param isAlarmActive A boolean value indicating whether the alarm is active.
 * @param isSelectionMode A boolean value indicating whether the selection mode is enabled.
 * @param isSelected A boolean value indicating whether the alarm item is selected.
 * @param onSelectionChange A callback that is triggered when the selection state changes.
 * @param onStateChange A callback that is triggered when the alarm active state changes.
 */
@Composable
fun AlarmItemComposable(
    modifier: Modifier = Modifier,
    alarmName: String,
    alarmState: String,
    alarmTime: () -> String,
    isAlarmActive: Boolean,
    isSelectionMode: Boolean,
    isSelected: Boolean,
    onSelectionChange: (Boolean) -> Unit,
    onStateChange: (Boolean) -> Unit
) {
    AlarmItem(
        modifier = modifier,
        alarmName = alarmName,
        alarmState = alarmState,
        alarmTime = alarmTime
    ) {
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
}

/**
 * A private composable function to display the layout of an alarm item.
 *
 * @param modifier The modifier to be applied to the component. Default is Modifier.
 * @param alarmName The name of the alarm.
 * @param alarmState The state description of the alarm.
 * @param alarmTime A lambda that returns the time string of the alarm.
 * @param changeStateContent A composable lambda that displays the switch or checkbox.
 */
@Composable
private fun AlarmItem(
    modifier: Modifier = Modifier,
    alarmName: String,
    alarmState: String,
    alarmTime: () -> String,
    changeStateContent: @Composable (Modifier) -> Unit
) {
    Card(
        modifier = modifier
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            val (timeCons, tagCons, changeStateCons, stateCons) = createRefs()
            val barrier = createStartBarrier(changeStateCons, margin = (-4).dp)

            ClockText(
                modifier = Modifier.constrainAs(timeCons) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                },
                time = alarmTime,
                textMeasurer = rememberTextMeasurer(),
                textStyle = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )

            Text(
                modifier = Modifier.constrainAs(tagCons) {
                    start.linkTo(timeCons.end, margin = 4.dp)
                    end.linkTo(barrier)
                    bottom.linkTo(timeCons.bottom, margin = 2.dp)
                    width = Dimension.fillToConstraints
                },
                text = alarmName,
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
}
