package uk.ac.aber.mycookshop.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import uk.ac.aber.myknajpa.ui.theme.fontFamilyLexend

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = fontFamilyLexend,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)