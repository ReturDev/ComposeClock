package com.retur.composeclock.ui.core.composable.checkbox

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import kotlin.math.floor

/**
 * A composable function to display a circular checkbox with animation and customizable colors.
 *
 * @param modifier The modifier to be applied to the component. Default is Modifier.
 * @param isChecked A boolean value indicating whether the checkbox is checked.
 * @param checkedColor The color of the checkbox when it is checked. Default is the primary color of the current Material theme.
 * @param checkedMarkColor The color of the check mark when the checkbox is checked. Default is the onPrimary color of the current Material theme.
 * @param borderColor The color of the checkbox border when it is not checked. Default is the outline color of the current Material theme.
 * @param onCheckedChange A callback that is triggered when the checkbox state changes.
 */
@Composable
fun CircularCheckbox(
    modifier: Modifier = Modifier,
    isChecked: Boolean,
    checkedColor: Color = MaterialTheme.colorScheme.primary,
    checkedMarkColor: Color = MaterialTheme.colorScheme.onPrimary,
    borderColor: Color = MaterialTheme.colorScheme.outline,
    onCheckedChange: (Boolean) -> Unit
) {
    // Updates the color states based on the checkbox's checked state
    val checkBoxColor = rememberUpdatedState(
        newValue = if (isChecked) checkedColor else Color.Transparent
    )

    val checkBoxBorderColor = rememberUpdatedState(
        newValue = if (isChecked) checkedColor else borderColor
    )

    // Animates the checkbox state change
    val checkedAnimation = animateFloatAsState(
        targetValue = if (isChecked) 1f else 0f,
        label = "checked_animation",
        animationSpec = tween(CheckAnimationDuration)
    )

    // Remember a draw cache to store paths
    val drawCache = remember {
        CircularCheckboxCache()
    }

    // Canvas to draw the checkbox
    Canvas(
        modifier = modifier
            .toggleable(
                value = isChecked,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = false, radius = ToggleIndicationRadius),
                role = Role.Checkbox,
                onValueChange = onCheckedChange
            )
            .minimumInteractiveComponentSize()
            .padding(CheckboxDefaultPadding)
            .requiredSize(CircularCheckBoxSize)
    ) {
        val strokeWidthPx = floor(StrokeWidth.toPx())

        // Draw the circular box and check mark
        drawCircularBox(
            boxColor = checkBoxColor.value,
            borderColor = checkBoxBorderColor.value,
            strokeWidthPx = strokeWidthPx
        )
        drawCheck(
            checkColor = checkedMarkColor,
            strokeWidthPx = strokeWidthPx,
            checkFraction = checkedAnimation.value,
            drawCache = drawCache
        )
    }
}

/**
 * Draws the check mark inside the circular checkbox.
 *
 * @param checkColor The color of the check mark.
 * @param strokeWidthPx The width of the stroke used to draw the check mark.
 * @param checkFraction The fraction of the check mark to be drawn, used for animation.
 * @param drawCache A cache for storing paths and measurements for drawing the checkbox.
 */
private fun DrawScope.drawCheck(
    checkColor: Color,
    strokeWidthPx: Float,
    checkFraction: Float,
    drawCache: CircularCheckboxCache
) {
    val stroke = Stroke(width = strokeWidthPx, cap = StrokeCap.Square)
    val width = size.width
    val checkCrossX = 0.4f
    val checkCrossY = 0.7f
    val leftX = 0.2f
    val leftY = 0.5f
    val rightX = 0.8f
    val rightY = 0.3f

    with(drawCache) {
        checkPath.reset()
        checkPath.moveTo(x = width * leftX, y = width * leftY)
        checkPath.lineTo(x = width * checkCrossX, y = width * checkCrossY)
        checkPath.lineTo(x = width * rightX, y = width * rightY)
        pathMeasure.setPath(checkPath, false)
        pathToDraw.reset()
        pathMeasure.getSegment(0f, pathMeasure.length * checkFraction, pathToDraw, true)
    }

    drawPath(drawCache.pathToDraw, checkColor, style = stroke)
}

/**
 * Draws the circular box of the checkbox.
 *
 * @param boxColor The fill color of the circular box.
 * @param borderColor The border color of the circular box.
 * @param strokeWidthPx The width of the border stroke.
 */
private fun DrawScope.drawCircularBox(
    boxColor: Color,
    borderColor: Color,
    strokeWidthPx: Float
) {
    val radius = size.width / 2
    val center = Offset(size.width / 2, size.height / 2)

    if (boxColor == borderColor) {
        drawCircle(color = boxColor, radius = radius, center = center, style = Fill)
    } else {
        drawCircle(color = boxColor, radius = radius - strokeWidthPx, center = center, style = Fill)
        drawCircle(color = borderColor, radius = radius - strokeWidthPx / 2, center = center, style = Stroke(strokeWidthPx))
    }
}

/**
 * A cache for storing paths and measurements for drawing the circular checkbox.
 *
 * @property checkPath The path used to draw the check mark.
 * @property pathMeasure The path measure used to measure the check mark path.
 * @property pathToDraw The path to be drawn for the check mark, updated during animation.
 */
@Immutable
private class CircularCheckboxCache(
    val checkPath: Path = Path(),
    val pathMeasure: PathMeasure = PathMeasure(),
    val pathToDraw: Path = Path()
)


private const val CheckAnimationDuration = 100
private val ToggleIndicationRadius = 20.dp
private val StrokeWidth = 2.dp
private val CircularCheckBoxSize = 20.dp
private val CheckboxDefaultPadding = 2.dp