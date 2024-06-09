package com.retur.composeclock.ui.core.composable.clickable

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
 * A composable function to display a long text option button with customizable end content.
 *
 * @param modifier The modifier to be applied to the component. Default is Modifier.
 * @param text The text to be displayed in the button.
 * @param onClick A callback that is triggered when the button is clicked.
 * @param endContent A composable function for additional content to be displayed at the end of the row. Default is an empty lambda.
 */
@Composable
fun LongTextOptionButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    endContent: @Composable RowScope.() -> Unit = {}
) {
    TextButton(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
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
