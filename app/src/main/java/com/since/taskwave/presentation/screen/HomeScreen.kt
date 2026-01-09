package com.since.taskwave.presentation.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.since.taskwave.data.modal.database.Quotes
import com.since.taskwave.presentation.screen.component.QuotesComponent
import com.since.taskwave.presentation.screen.state_action.QuotesAction
import com.since.taskwave.ui.theme.TaskWaveTheme


@Composable
fun HomeScreenRoot(
    viewModel: HomeViewModel = hiltViewModel()
) {

    HomeScreen(
        items = viewModel.quotesItem.collectAsStateWithLifecycle().value,
        observeNetwork = viewModel.networkObserve.collectAsStateWithLifecycle().value
    ) {
        viewModel.onAction(it)
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    items:List<Quotes> = emptyList(),
    observeNetwork: Boolean?,
    action:(QuotesAction) -> Unit
) {


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Task wave",
                        style = TextStyle(
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = FontFamily.Serif,
                            fontSize = 22.sp,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    )
                }, actions = {
                    IconButton(
                        onClick = {
                            action(QuotesAction.RepeatTimeRequest)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Refresh,
                            contentDescription = null
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer
                )
            )
        },
        snackbarHost = {
            observeNetwork?.let { network->
                AnimatedVisibility(visible = !network) {
                    Snackbar(
                        shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
                        containerColor = if (network) Color.Green else Color.Red,
                        contentColor = MaterialTheme.colorScheme.onBackground,
                    ){
                        Text(text = "Check your internet connection",
                            color = MaterialTheme.colorScheme.background,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp)
                    }

                }
            }


        }
    ) {paddingValues ->

        if(items.isEmpty()){
            Box(
                modifier=Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }else{
                LazyColumn(
                    modifier= Modifier
                        .padding(paddingValues)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(items){quotes->
                        QuotesComponent(
                            quotes = quotes,
                            onDelete = {
                                action(QuotesAction.Delete(quotes))
                            }
                        )
                    }
                }
            }
    }

}

@Preview
@Composable
private fun HomeScreenPreview() {
    TaskWaveTheme {
        HomeScreen(
            observeNetwork = null
        ) { }
    }
}