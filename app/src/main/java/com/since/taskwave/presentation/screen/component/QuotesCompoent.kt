package com.since.taskwave.presentation.screen.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.since.taskwave.data.modal.database.Quotes
import com.since.taskwave.ui.theme.TaskWaveTheme
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun QuotesComponent(
    quotes: Quotes,
    onDelete:(Quotes) -> Unit
) {

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onBackground
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp,
            pressedElevation = 4.dp
        ),
        modifier=Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 6.dp)
    ) {

        Row(
            modifier=Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Column(
                modifier= Modifier
                    .weight(1f)
            ) {

                Text(
                    text = quotes.id.toString(),
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.SemiBold
                    )
                )

                Spacer(
                    modifier=Modifier
                        .height(4.dp)
                )

                Text(
                    text = quotes.author,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = FontFamily.Serif
                    )
                )


                Spacer(modifier=Modifier
                    .height(4.dp))

                Text(
                    text = quotes.quote,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily.Serif
                    )
                )

                Spacer(modifier=Modifier
                    .height(4.dp))

                Text(
                    text = quotes.typeRequest,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily.Serif
                    )
                )

                HorizontalDivider(
                    modifier=Modifier
                        .fillMaxWidth()
                )

                Spacer(modifier= Modifier
                    .height(4.dp))

                Text(
                    text = quotes.time.formatTime(),
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = FontFamily.Serif,
                        textAlign = TextAlign.Right
                    ),
                    modifier=Modifier
                        .fillMaxWidth()
                )
            }


            IconButton(
                onClick = {
                    onDelete(quotes)
                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = null
                )
            }

        }

    }

}

private fun Long.formatTime():String{
    val sdf = SimpleDateFormat("hh:mm a")
    val date = Date(this)
    return sdf.format(date)
}

@Preview
@Composable
private fun QuotesCompoentPreview() {
    TaskWaveTheme {
        QuotesComponent(quotes = Quotes(
            author = "fd",
            id=12,
            quote = "af",
            time = System.currentTimeMillis(),
            typeRequest = ""
        )) { }
    }
}