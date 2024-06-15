package com.retur.composeclock.ui.core.composable.clickable

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

/**
 * A composable function for displaying a button with long text content.
 *
 * @param modifier The modifier to be applied to the component. Default is Modifier.
 * @param text The text content of the button.
 * @param colors The colors to be applied to the button. Default is ButtonDefaults.textButtonColors().
 * @param onClick The callback to be invoked when the button is clicked.
 * @param endContent An optional composable lambda to be placed at the end of the button content.
 */
@Composable
fun LongTextOptionButton(
    modifier: Modifier = Modifier,
    text: String,
    colors : ButtonColors = ButtonDefaults.textButtonColors(),
    onClick: () -> Unit,
    endContent: @Composable RowScope.() -> Unit = {}
) {
    TextButton(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = colors,
        onClick = onClick,
        shape = RectangleShape
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = text,
                fontWeight = FontWeight.Bold,
                softWrap = false,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyLarge
            )
            endContent()
        }
    }
}
