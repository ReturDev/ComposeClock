package com.retur.composeclock.ui.screen.main.alarm.component.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.retur.composeclock.R
import com.retur.composeclock.ui.core.composable.clickable.LongTextOptionButton

/**
 * A composable function to display a bottom sheet dialog for turning off a repetition alarm.
 *
 * @param dialogState The state of the sheet.
 * @param turnOffDate The date of the next repetition to be turned off.
 * @param onTurnOffNextRepetition A callback that is triggered when the user chooses to turn off the next repetition.
 * @param onTotalTurnOff A callback that is triggered when the user chooses to turn off the alarm completely.
 * @param onDismissRequest A callback that is triggered when the dialog is dismissed.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TurnOffRepetitionAlarmBottomDialog(
    dialogState: SheetState,
    turnOffDate: String,
    onTurnOffNextRepetition: () -> Unit,
    onTotalTurnOff: () -> Unit,
    onDismissRequest: () -> Unit
) {
    ModalBottomSheet(
        sheetState = dialogState,
        onDismissRequest = onDismissRequest,
        dragHandle = null,
        windowInsets = WindowInsets.navigationBars
    ) {

        Column(
            modifier = Modifier.padding(vertical = 16.dp)
        ) {

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.repetition_alarm_deactivation_dialog_title),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )

            Column(
                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                LongTextOptionButton(
                    text = stringResource(
                        id = R.string.repetition_alarm_deactivation_dialog_one_time_deactivation,
                        turnOffDate
                    ),
                    onClick = onTurnOffNextRepetition
                )

                LongTextOptionButton(
                    text = stringResource(id = R.string.repetition_alarm_deactivation_dialog_total_deactivation),
                    onClick = onTotalTurnOff
                )
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth(0.95f)
                    .padding(horizontal = 16.dp)
                    .align(Alignment.CenterHorizontally),
                onClick = onDismissRequest
            ) {
                Text(text = stringResource(id = R.string.cancel_button))
            }

        }

    }
}

