package com.alejandrorios.art_catalog_app.ui.components

import android.graphics.Typeface
import android.text.Html
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StrikethroughSpan
import android.text.style.StyleSpan
import android.text.style.URLSpan
import android.text.style.UnderlineSpan
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import androidx.core.text.getSpans
import com.alejandrorios.art_catalog_app.R
import com.alejandrorios.art_catalog_app.ui.theme.Art_catalog_appTheme
import com.alejandrorios.art_catalog_app.ui.theme.BlueLink

const val DEFAULT_MINIMUM_TEXT_LINE = 3

/**
 * An expandable text component that provides access to truncated text with a dynamic ... Show More/ Show Less button.
 * It also has the ability to show HTML text, it's a combination of two different resources
 * Resources:
 * https://medium.com/@munbonecci/how-to-implement-expandable-text-in-jetpack-compose-ca9ba35b645c
 * https://github.com/ch4rl3x/HtmlText/blob/main/material3-html-text/src/main/java/de/charlex/compose/material3/HtmlText.kt
 *
 * @param modifier Modifier for the Box containing the text.
 * @param textModifier Modifier for the Text composable.
 * @param style The TextStyle to apply to the text.
 * @param fontStyle The FontStyle to apply to the text.
 * @param text The text to be displayed.
 * @param collapsedMaxLine The maximum number of lines to display when collapsed.
 * @param showMoreText The text to display for "... Show More" button.
 * @param showMoreStyle The SpanStyle for "... Show More" button.
 * @param showLessText The text to display for "Show Less" button.
 * @param showLessStyle The SpanStyle for "Show Less" button.
 * @param textAlign The alignment of the text.
 * @param fontSize The font size of the text.
 */
@Composable
fun ExpandableText(
    modifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
    fontStyle: FontStyle? = null,
    text: String,
    collapsedMaxLine: Int = DEFAULT_MINIMUM_TEXT_LINE,
    showMoreText: String = "... Show More",
    showMoreStyle: SpanStyle = SpanStyle(fontWeight = FontWeight.W500),
    showLessText: String = " Show Less",
    showLessStyle: SpanStyle = showMoreStyle,
    textAlign: TextAlign? = null,
    fontSize: TextUnit,
    urlSpanStyle: SpanStyle = SpanStyle(
        color = MaterialTheme.colorScheme.tertiary,
        textDecoration = TextDecoration.Underline
    ),
    colorMapping: Map<Color, Color> = emptyMap(),
) {
    var isExpanded by remember { mutableStateOf(false) }
    var clickable by remember { mutableStateOf(false) }
    var lastCharIndex by remember { mutableIntStateOf(0) }
    val annotatedString = Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY).toAnnotatedString(urlSpanStyle, colorMapping)

    // Box composable containing the Text composable.
    Box(modifier = Modifier
        .clickable(clickable) {
            isExpanded = !isExpanded
        }
        .then(modifier)
    ) {
        Text(
            modifier = textModifier
                .fillMaxWidth()
                .animateContentSize(),
            text = buildAnnotatedString {
                if (clickable) {
                    if (isExpanded) {
                        // Display the full text and "Show Less" button when expanded.
                        append(annotatedString)
                        withStyle(style = showLessStyle) { append(showLessText) }
                    } else {
                        // Display truncated text and "Show More" button when collapsed.
                        val adjustText = annotatedString.substring(startIndex = 0, endIndex = lastCharIndex)
                            .dropLast(showMoreText.length)
                            .dropLastWhile { Character.isWhitespace(it) || it == '.' }
                        append(adjustText)
                        withStyle(style = showMoreStyle) { append(showMoreText) }
                    }
                } else {
                    // Display the full text when not clickable.
                    append(annotatedString)
                }
            },
            // Set max lines based on the expanded state.
            maxLines = if (isExpanded) Int.MAX_VALUE else collapsedMaxLine,
            fontStyle = fontStyle,
            // Callback to determine visual overflow and enable click ability.
            onTextLayout = { textLayoutResult ->
                if (!isExpanded && textLayoutResult.hasVisualOverflow) {
                    clickable = true
                    lastCharIndex = textLayoutResult.getLineEnd(collapsedMaxLine - 1)
                }
            },
            style = style,
            textAlign = textAlign,
            fontSize = fontSize
        )
    }
}

fun Spanned.toAnnotatedString(
    urlSpanStyle: SpanStyle = SpanStyle(
        color = BlueLink,
        textDecoration = TextDecoration.Underline
    ),
    colorMapping: Map<Color, Color> = emptyMap(),
): AnnotatedString {
    return buildAnnotatedString {
        append(this@toAnnotatedString.toString())
        val urlSpans = getSpans<URLSpan>()
        val styleSpans = getSpans<StyleSpan>()
        val colorSpans = getSpans<ForegroundColorSpan>()
        val underlineSpans = getSpans<UnderlineSpan>()
        val strikethroughSpans = getSpans<StrikethroughSpan>()
        urlSpans.forEach { urlSpan ->
            val start = getSpanStart(urlSpan)
            val end = getSpanEnd(urlSpan)
            addStyle(urlSpanStyle, start, end)
            addStringAnnotation("url", urlSpan.url, start, end)
        }
        colorSpans.forEach { colorSpan ->
            val start = getSpanStart(colorSpan)
            val end = getSpanEnd(colorSpan)
            addStyle(
                SpanStyle(color = colorMapping.getOrElse(Color(colorSpan.foregroundColor)) { Color(colorSpan.foregroundColor) }),
                start,
                end
            )
        }
        styleSpans.forEach { styleSpan ->
            val start = getSpanStart(styleSpan)
            val end = getSpanEnd(styleSpan)
            when (styleSpan.style) {
                Typeface.BOLD -> addStyle(SpanStyle(fontWeight = FontWeight.Bold), start, end)
                Typeface.ITALIC -> addStyle(SpanStyle(fontStyle = FontStyle.Italic), start, end)
                Typeface.BOLD_ITALIC -> addStyle(
                    SpanStyle(fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic),
                    start,
                    end
                )
            }
        }
        underlineSpans.forEach { underlineSpan ->
            val start = getSpanStart(underlineSpan)
            val end = getSpanEnd(underlineSpan)
            addStyle(SpanStyle(textDecoration = TextDecoration.Underline), start, end)
        }
        strikethroughSpans.forEach { strikethroughSpan ->
            val start = getSpanStart(strikethroughSpan)
            val end = getSpanEnd(strikethroughSpan)
            addStyle(SpanStyle(textDecoration = TextDecoration.LineThrough), start, end)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExpandableTextPreview() {
    Art_catalog_appTheme {
        ExpandableText(
            text = stringResource(R.string.lorem_ipsum),
            fontSize = 16.sp,
            collapsedMaxLine = 20,
            showMoreStyle = SpanStyle(color = BlueLink),
            showLessStyle = SpanStyle(color = BlueLink),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ExpandableTextWithHMLPreview() {
    Art_catalog_appTheme {
        ExpandableText(
            text = stringResource(R.string.html_text_sample),
            fontSize = 16.sp,
            collapsedMaxLine = 20,
            showMoreStyle = SpanStyle(color = BlueLink),
            showLessStyle = SpanStyle(color = BlueLink),
        )
    }
}
