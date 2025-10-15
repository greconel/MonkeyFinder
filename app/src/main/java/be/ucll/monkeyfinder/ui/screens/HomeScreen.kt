package be.ucll.monkeyfinder.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.ucll.monkeyfinder.R
import be.ucll.monkeyfinder.models.Monkey
import be.ucll.monkeyfinder.ui.theme.MonkeyFinderTheme
import be.ucll.monkeyfinder.viewmodels.MonkeyUiState
import be.ucll.monkeyfinder.viewmodels.MonkeysViewModel
import coil.compose.rememberAsyncImagePainter

@Composable
fun HomeScreen(
    viewModel: MonkeysViewModel,
    onListItemClick: (Monkey) -> Unit,
    modifier: Modifier = Modifier
) {
    val monkeyUiState = viewModel.monkeyUiState

    when (monkeyUiState) {
        is MonkeyUiState.Loading -> LoadingScreen(modifier = modifier)
        is MonkeyUiState.Success -> MonkeyListScreen(
            monkeys = monkeyUiState.monkeys,
            retryAction = { viewModel.getMonkeys() },
            onListItemClick = onListItemClick,
            modifier = modifier
        )
        is MonkeyUiState.Error -> ErrorScreen(
            retryAction = { viewModel.getMonkeys() },
            modifier = modifier
        )
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun ErrorScreen(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error),
            contentDescription = ""
        )
        Text(
            text = stringResource(R.string.loading_failed),
            modifier = Modifier.padding(16.dp)
        )
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun MonkeyListScreen(
    monkeys: List<Monkey>,
    retryAction: () -> Unit,
    onListItemClick: (Monkey) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Box(modifier = Modifier.weight(weight = 0.9f, fill = true)) {
            LazyColumn {
                items(monkeys) { monkey ->
                    MonkeyCard(
                        monkey = monkey,
                        onItemClick = onListItemClick
                    )
                }
            }
        }
        Box(
            modifier = Modifier.weight(weight = 0.1f, fill = true),
            contentAlignment = Alignment.CenterStart
        ) {
            Button(
                onClick = retryAction,
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text(stringResource(R.string.search))
            }
        }
    }
}

@Composable
fun MonkeyCard(
    monkey: Monkey,
    onItemClick: (Monkey) -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor =  MaterialTheme.colorScheme.background
        ),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onItemClick(monkey) },
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    )
    {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = rememberAsyncImagePainter(monkey.image),
                contentDescription = "monkey image",
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(125.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = monkey.name,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(8.dp)
                )
                Text(
                    text = monkey.location,
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoadingScreenPreview() {
    MonkeyFinderTheme {
        LoadingScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ErrorScreenPreview() {
    MonkeyFinderTheme {
        ErrorScreen({})
    }
}