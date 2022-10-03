package ir.alishi.circleprogressbar

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun CircleProgressBar(
    percentage: Float = 50f,
    progressColor: Color = Color.Red,
    progressStrokeWidth: Dp = 15.dp,
    maxProgressValue: Float = 100f,
) {

    var progress by remember {
        mutableStateOf(false)
    }

    val allowProgress = (percentage * 360 / maxProgressValue)

    val progressState by animateFloatAsState(
        targetValue = (if (progress) {
            if (percentage <= maxProgressValue) allowProgress else 360f
        }
        else {
            0f
        }),
        tween(1000)
    )

    LaunchedEffect(key1 = true) {
        progress = true
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(8.dp)
            .size(150.dp)
            .drawBehind {
                drawArc(
                    size = size,
                    color = progressColor,
                    startAngle = -90f,
                    sweepAngle = progressState,
                    useCenter = false,
                    topLeft = Offset.Zero,
                    style = Stroke(
                        width = progressStrokeWidth.toPx(),
                        cap = StrokeCap.Butt,
                    ),
                )
            }
    ) {
        Text(
            text = ((progressState * maxProgressValue) / 360)
                .toInt()
                .toString(),
            style = MaterialTheme.typography.h3.copy(
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .padding(4.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CircleProgressBarPreview() {
    CircleProgressBar(
        percentage = 50f,
        progressColor = Color.Blue.copy(
            alpha = 0.5f
        ),
        progressStrokeWidth = 8.dp
    )
}