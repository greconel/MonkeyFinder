package be.ucll.monkeyfinder.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import be.ucll.monkeyfinder.R
import be.ucll.monkeyfinder.models.Monkey
import be.ucll.monkeyfinder.viewmodels.MonkeyDetailsViewModel
import coil.compose.rememberAsyncImagePainter

@Composable
fun DetailsScreen(
    viewModel: MonkeyDetailsViewModel,
    modifier: Modifier = Modifier
) {
    val monkey: Monkey? = viewModel.monkey

    if (monkey != null) {
        Column(modifier = modifier.verticalScroll(rememberScrollState())) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .background(colorResource(R.color.yellow))
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(monkey.image),
                    contentDescription = "monkey image",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(200.dp)
                        .clip(CircleShape)
                        .border(width = 4.dp, color = Color.White, shape = CircleShape)
                )
                Text(
                    text = monkey.name,
                    color = Color.White,
                    style = MaterialTheme.typography.headlineSmall
                )
            }
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = monkey.details,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Location: " + monkey.location,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Population: " + monkey.population,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

