package app.seals.weather.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.seals.weather.UiIntent
import app.seals.weather.domain.models.AutocompleteDomainModel
import app.seals.weather.domain.models.LocationDomainModel
import app.seals.weather.ui.theme.Typography
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPagerIndicator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private val list = listOf(
    AutocompleteDomainModel(),
    AutocompleteDomainModel(),
    AutocompleteDomainModel(),
)

@OptIn(ExperimentalPagerApi::class)
@Composable
@Preview
fun TopBar(
    modifier: Modifier = Modifier,
    locations: List<AutocompleteDomainModel> = list,
    location: LocationDomainModel? = LocationDomainModel(name = "Digoin", region = "Bourgogne-Franche-Comté"),
    reducer: (UiIntent) -> Unit = {},
    openSearch: () -> Unit = {},
    openMenu: () -> Unit = {},
    selectForecast: (String) -> Unit = {}
) {

    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(pagerState.currentPage) {
        delay(100)
        selectForecast("${locations[pagerState.currentPage].lat ?: 0},${locations[pagerState.currentPage].lon ?: 0}")
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            tint = MaterialTheme.colorScheme.onBackground,
            contentDescription = null,
            modifier = Modifier
                .size(30.dp)
                .clip(CircleShape)
                .alpha(0.6f)
                .clickable { openSearch() }
        )

        if(locations.isNotEmpty()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    tint = MaterialTheme.colorScheme.onBackground,
                    contentDescription = null,
                    modifier = Modifier
                        .size(22.dp)
                        .clip(CircleShape)
                        .alpha(0.6f)
                        .clickable {
                            scope.launch {
                                val targetPage =
                                    if (pagerState.currentPage - 1 >= 0) pagerState.currentPage - 1 else 0
                                pagerState.animateScrollToPage(targetPage, pageOffset = 0f)
                            }
                        }
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.widthIn(min = 60.dp, max = 220.dp)
                ) {
                    HorizontalPager(
                        userScrollEnabled = true,
                        count = locations.size,
                        state = pagerState,
                    ) {
                        LocationItem(locations[it])
                    }
                    HorizontalPagerIndicator(
                        pagerState = pagerState,
                        modifier = Modifier.padding(4.dp),
                        indicatorHeight = 4.dp,
                        indicatorWidth = 4.dp,
                    )
                }
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    tint = MaterialTheme.colorScheme.onBackground,
                    contentDescription = null,
                    modifier = Modifier
                        .size(22.dp)
                        .clip(CircleShape)
                        .alpha(0.6f)
                        .clickable {
                            scope.launch {
                                val targetPage =
                                    if (pagerState.currentPage + 1 <= pagerState.pageCount) pagerState.currentPage + 1
                                    else pagerState.currentPage
                                pagerState.animateScrollToPage(targetPage, pageOffset = 0f)
                            }
                        }
                )
            }
        }

        Icon(
            imageVector = Icons.Default.Menu,
            tint = MaterialTheme.colorScheme.onBackground,
            contentDescription = null,
            modifier = Modifier
                .size(30.dp)
                .clip(CircleShape)
                .alpha(0.6f)
                .clickable { openMenu() }
        )
    }
}

@Composable
private fun LocationItem(
    item: AutocompleteDomainModel
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = item.name.toString(),
            style = Typography.titleLarge,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = "${item.region}, ${item.country}",
            style = Typography.labelSmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}