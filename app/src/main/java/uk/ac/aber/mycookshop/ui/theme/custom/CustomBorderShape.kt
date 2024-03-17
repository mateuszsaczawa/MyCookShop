package uk.ac.aber.mycookshop.model

import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size

/**
 * Custom shape representing the bottom border of a UI element.
 * It is used to draw the bottom edge with a 1-pixel spacing from the bottom.
 */

object BottomBorderShape : Shape {
    override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline {
        return Outline.Rectangle(Rect(left = 0f, top = size.height - 1f, right = size.width, bottom = size.height))
    }
}

/**
 * Custom shape representing the top border of a UI element.
 * It is used to draw the top edge with a 1-pixel spacing from the top.
 */

object TopBorderShape : Shape {
    override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline {
        return Outline.Rectangle(Rect(left = 0f, top = 0f, right = size.width, bottom = 1f))
    }
}
