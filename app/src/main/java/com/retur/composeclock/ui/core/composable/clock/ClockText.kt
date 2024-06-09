package com.retur.composeclock.ui.core.composable.clock

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize

private val extraTextWidth = 4.dp

/**
 * A composable function to display a clock time as text with a dynamic size spacer that adjusts based on the text width.
 *
 * @param modifier The modifier to be applied to the component. Default is Modifier.
 * @param time A lambda that returns the time string to be displayed.
 * @param textStyle The style to be applied to the text. Default is LocalTextStyle.current.
 * @param textMeasurer The TextMeasurer instance used for measuring text size. Default is a remembered instance.
 */
@Composable
fun ClockText(
    modifier: Modifier = Modifier,
    time: () -> String,
    textStyle: TextStyle = LocalTextStyle.current,
    textMeasurer: TextMeasurer = rememberTextMeasurer()
) {
    // Get the current content color and density from the composition's context
    val textColor = LocalContentColor.current
    val density = LocalDensity.current

    // Remember the size of the text with an extra width
    val clockTextSize = remember {
        with(density) {
            // Measure the size of the text using the textMeasurer
            val size = textMeasurer.measure(
                time(),
                textStyle
            ).size
                .toSize() // Convert the IntSize to Size
                .toDpSize() // Convert the Size to DpSize

            // Add extra width to the size
            size.copy(
                width = size.width + extraTextWidth
            )
        }
    }

    // Create a spacer with the size of the text and draw the text on it
    Spacer(
        modifier = modifier
            .defaultMinSize(minWidth = 48.dp) // Ensure minimum width
            .size(clockTextSize) // Set the size of the spacer
            .drawBehind {
                // Measure the layout of the text again to draw it
                val layoutResult = textMeasurer.measure(
                    time(),
                    textStyle
                )

                // Draw the text at the center of the spacer
                drawText(
                    color = textColor,
                    textLayoutResult = layoutResult,
                    topLeft = Offset(
                        x = size.width / 2 - (layoutResult.size.width / 2), // Center horizontally
                        y = 0f // Align to the top
                    )
                )
            }
    )
}
