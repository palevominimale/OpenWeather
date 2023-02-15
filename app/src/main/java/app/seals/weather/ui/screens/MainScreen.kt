package app.seals.weather.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TabRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toIntRect
import androidx.compose.ui.unit.toRect
import app.seals.weather.UiIntent
import app.seals.weather.ui.theme.Typography
import app.seals.weather.R
import app.seals.weather.domain.models.*
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import java.time.*
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.*

@Composable
@Preview
fun MainScreen(
    modifier: Modifier = Modifier,
    weather: WeatherResponseDomainModel = WeatherResponseDomainModel(),
    isRefreshing: Boolean = false,
    reducer: (UiIntent) -> Unit = {},
) {

    val cs = ConditionSelector(LocalContext.current, Locale.getDefault().language)
    val uriHandler = LocalUriHandler.current
    val swipeState = rememberSwipeRefreshState(isRefreshing)

    SwipeRefresh(
        state = swipeState,
        onRefresh = { reducer(UiIntent.Refresh) }
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Temp(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    description = weather.current,
                    cs = cs
                )
                AQI(
                    aqi = weather.current?.airQuality,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                RowForecast(forecast = weather.forecast?.forecastday)
                WeekForecast(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    forecast = weather.forecast,
                    cs = cs
                )
                val astro = if(weather.forecast?.forecastday != null && weather.forecast!!.forecastday.size > 0) weather.forecast?.forecastday?.get(0)?.astro else null
                SunMoveProgress(astro = astro)
                MiscInfo(modifier = Modifier.padding(16.dp), current = weather.current)
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "powered by weatherAPI.com",
                    style = Typography.labelMedium.copy(fontWeight = FontWeight.Light, textDecoration = TextDecoration.Underline),
                    modifier = Modifier
                        .padding(bottom = 4.dp)
                        .alpha(0.6f)
                        .clickable(
                            indication = null,
                            interactionSource = MutableInteractionSource()
                        ) { uriHandler.openUri("https://weatherapi.com") }
                )
            }
        }
    }
}

@Composable
private fun Temp(
    modifier: Modifier = Modifier,
    description: CurrentWeatherDomainModel?,
    cs: ConditionSelector
) {
    Column(
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(horizontal = 16.dp)
            .placeholder(
                shape = RoundedCornerShape(20.dp),
                visible = description?.tempC == null,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
            )
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                text = (description?.tempC?.roundToInt() ?: 0).toString(),
                style = Typography.displayLarge,
                modifier = Modifier.alpha(0.6f)
            )
            Text(
                text = "°C",
                style = Typography.titleLarge,
                modifier = Modifier
                    .alpha(0.6f)
                    .padding(top = 22.dp)
            )
        }
        Text(
            text = cs.descr(
                code = description?.condition?.code ?: 1000,
                day = description?.isDay == 1
            ),
            style = Typography.titleMedium,
            modifier = Modifier.alpha(0.6f)
        )
    }
}

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun AQI(
    modifier: Modifier = Modifier,
    aqi: AqiDomainModel?
) {

    var isFolded by remember { mutableStateOf(true) }

    Surface(
        shape = RoundedCornerShape(40.dp),
        color = MaterialTheme.colorScheme.primary,
        onClick = { isFolded = !isFolded } ,
        modifier = modifier
            .alpha(0.6f)
    ) {
        AnimatedContent(
            targetState = isFolded,
            contentAlignment = Alignment.Center,
            transitionSpec = {
                fadeIn(animationSpec = tween(150, 150)) with
                    fadeOut(animationSpec = tween(150)) using
                    SizeTransform { initialSize, targetSize ->
                        if (targetState) {
                            keyframes {
                                IntSize(initialSize.width, targetSize.height) at 150
                                durationMillis = 300
                            }
                        } else {
                            keyframes {
                                IntSize(targetSize.width, initialSize.height) at 150
                                durationMillis = 300
                            }
                        }
                    }
            }
        ) { targetState ->
            if(targetState) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .alpha(0.6f)
                        .placeholder(
                            shape = RoundedCornerShape(40.dp),
                            visible = aqi == null,
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(start = 4.dp, top = 4.dp, bottom = 4.dp)
                            .size(16.dp)
                            .clip(CircleShape)
                    )
                    Text(
                        text = String.format(
                            stringResource(R.string.AQI),
                            max(aqi?.pm25 ?: 0.0, aqi?.pm10 ?: 0.0)),
                        style = Typography.labelMedium,
                        modifier = Modifier
                            .padding(vertical = 4.dp, horizontal = 8.dp)
                    )
                }
            } else {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .padding(vertical = 4.dp, horizontal = 16.dp)
                        .alpha(0.6f)
                        .placeholder(
                            shape = RoundedCornerShape(40.dp),
                            visible = aqi == null,
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                        )
                ) {
                    Column(
                        verticalArrangement = Arrangement.SpaceAround,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(horizontal = 6.dp)
                    ) {
                        Text(
                            text = String.format("%.1f",aqi?.pm25),
                            style = Typography.labelMedium
                        )
                        Text(
                            text = stringResource(id = R.string.AQI_pm25),
                            style = Typography.titleSmall
                        )
                    }
                    Column(
                        verticalArrangement = Arrangement.SpaceAround,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(horizontal = 6.dp)
                    ) {
                        Text(
                            text = String.format("%.1f",aqi?.pm10),
                            style = Typography.labelMedium
                        )
                        Text(
                            text = stringResource(id = R.string.AQI_pm10),
                            style = Typography.titleSmall
                        )
                    }
                    Column(
                        verticalArrangement = Arrangement.SpaceAround,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(horizontal = 6.dp)
                    ) {
                        Text(
                            text = String.format("%.1f",aqi?.so2),
                            style = Typography.labelMedium
                        )
                        Text(
                            text = stringResource(id = R.string.AQI_so2),
                            style = Typography.titleSmall
                        )
                    }
                    Column(
                        verticalArrangement = Arrangement.SpaceAround,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(horizontal = 6.dp)
                    ) {
                        Text(
                            text = String.format("%.1f",aqi?.no2),
                            style = Typography.labelMedium
                        )
                        Text(
                            text = stringResource(id = R.string.AQI_no2),
                            style = Typography.titleSmall
                        )
                    }
                    Column(
                        verticalArrangement = Arrangement.SpaceAround,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(horizontal = 6.dp)
                    ) {
                        Text(
                            text = String.format("%.1f",aqi?.o3),
                            style = Typography.labelMedium
                        )
                        Text(
                            text = stringResource(id = R.string.AQI_o3),
                            style = Typography.titleSmall
                        )
                    }
                    Column(
                        verticalArrangement = Arrangement.SpaceAround,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(horizontal = 6.dp)
                    ) {
                        Text(
                            text = String.format("%.1f",aqi?.co),
                            style = Typography.labelMedium
                        )
                        Text(
                            text = stringResource(id = R.string.AQI_co),
                            style = Typography.titleSmall
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun WeekForecast(
    modifier: Modifier = Modifier,
    forecast: ForecastDomainModel?,
    cs: ConditionSelector
) {
    var shortForecast by remember { mutableStateOf(true) }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
    ) {
        AnimatedContent(
            targetState = shortForecast,
            contentAlignment = Alignment.Center,
            transitionSpec = {
                fadeIn(animationSpec = tween(150, 150)) with
                    fadeOut(animationSpec = tween(150)) using
                    SizeTransform { initialSize, targetSize ->
                        if (targetState) {
                            keyframes {
                                IntSize(initialSize.width, targetSize.height) at 150
                                durationMillis = 300
                            }
                        } else {
                            keyframes {
                                IntSize(targetSize.width, initialSize.height) at 150
                                durationMillis = 300
                            }
                        }
                    }
            }
        ) { targetState ->
            if(targetState) {
                Column {
                    if(forecast?.forecastday?.isNotEmpty() == true) {
                        for (i in 0..2) DayWeatherItem(day = forecast.forecastday[i], cs = cs)
                    } else {
                        repeat(3) { DayWeatherItem(day = ForecastDayDomainModel(), cs = cs) }
                    }
                }
            } else {
                if(forecast != null) DetailedForecast(forecast = forecast, cs = cs)
            }
        }
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .alpha(0.6f)
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .clickable {
                    shortForecast = !shortForecast
                }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if(shortForecast) stringResource(id = R.string.full_forecast)
                        else stringResource(R.string.short_forecast),
                    modifier = Modifier.padding(top = 8.dp, bottom = 10.dp)
                )
            }
        }
    }
}

@Composable
private fun DayWeatherItem(
    modifier: Modifier = Modifier,
    day: ForecastDayDomainModel?,
    cs: ConditionSelector
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .placeholder(
                shape = RoundedCornerShape(20.dp),
                visible = day?.day == null,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                painter = painterResource(icon(day?.day?.condition?.code ?: 0, true)),
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(30.dp)
                    .alpha(0.6f)
            )
            val now = LocalDate.now()
            val date = LocalDate.parse(day?.date ?: "2023-02-02")
            val text = if(now.dayOfMonth == date.dayOfMonth) stringResource(id = R.string.today)
                else if((now.dayOfYear - date.dayOfYear).absoluteValue == 1) stringResource(id = R.string.tomorrow)
                else date.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault()).replaceFirstChar { it.uppercase() }
            Text(
                text = text + ": " + cs.descr(day?.day?.condition?.code ?: 1063, true),
                style = Typography.labelMedium,
                modifier = Modifier.alpha(0.6f)
            )
        }
        Text(
            text = (day?.day?.mintempC?.toInt() ?: 0).toString() + "° / " + (day?.day?.maxtempC?.toInt() ?: 1).toString()  + "°",
            modifier = Modifier.alpha(0.6f)
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun DetailedForecast(
    forecast: ForecastDomainModel,
    cs: ConditionSelector
) {
    var tabIndex by remember { mutableStateOf(0) }
    val tabTitles = mutableListOf<String>().apply {
        forecast.forecastday.forEach {
            val label = LocalDateTime.ofEpochSecond((it.dateEpoch ?: 0).toLong(), 0, ZoneOffset.UTC).dayOfWeek.getDisplayName(TextStyle.NARROW, Locale.getDefault())
            this.add(label)
        }
    }
    val pagerState = rememberPagerState()
    Column {
        TabRow(
            selectedTabIndex = tabIndex,
            backgroundColor = Color.Transparent,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier
                        .pagerTabIndicatorOffset(pagerState, tabPositions)
                        .alpha(0.6f)
                )
            },
            modifier = Modifier.height(30.dp)
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = tabIndex == index,
                    onClick = { tabIndex = index },
                    text = { Text(text = title) }
                )
            }
        }
        HorizontalPager(
            count = tabTitles.size,
            state = pagerState,
        ) { tabIndex ->
            LazyColumn(
                modifier = Modifier.height(400.dp)
            ) {
                forecast.forecastday[tabIndex].hour.forEach {
                    item {
                        Spacer(Modifier.padding(8.dp))
                        HourForecastItem(it, cs)
                    }
                }
            }
        }
    }
}

@Composable
private fun HourForecastItem(
    item: HourDomainModel?,
    cs: ConditionSelector
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                painter = painterResource(icon(item?.condition?.code ?: 0, item?.isDay == 1)),
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(30.dp)
                    .alpha(0.6f)
            )
            Column {
                val time = if(item?.time != null) item.time!!.subSequence(item.time!!.length-5, item.time!!.length) else "0:0"
                Text(
                    text = time.toString(), style = Typography.labelMedium.copy(fontWeight = FontWeight.Light),
                    modifier = Modifier.alpha(0.6f)
                )
                Text(
                    text = (item?.tempC ?: 0).toInt().toString() + "°C",
                    style = Typography.labelLarge,
                    modifier = Modifier.alpha(0.6f)
                )
            }
        }
        Text(
            text = cs.descr(item?.condition?.code ?: 1000, item?.isDay == 1 ),
            style = Typography.labelMedium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.alpha(0.6f)
        )
        Column(
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.End
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_wi_wind_deg),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 2.dp)
                        .size(14.dp)
                        .rotate(item?.windDegree?.toFloat() ?: 90.0f)
                        .alpha(0.6f)
                )
                val windSpeed = String
                    .format("%.1f m/s", item?.windKph?.div(3.6) ?: 0.0)
                    .replace(",", ".")
                Text(
                    text = windSpeed,
                    style = Typography.labelMedium,
                    modifier = Modifier.alpha(0.6f)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.wi_raindrop),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 2.dp, top = 2.dp)
                        .size(14.dp)
                        .alpha(0.6f)
                )
                val chanceOfPrecipitation =
                    max((item?.chanceOfSnow ?: 0.0), (item?.chanceOfRain?: 0.0)).toString() + " %"
                Text(
                    text = chanceOfPrecipitation,
                    style = Typography.labelMedium,
                    modifier = Modifier.alpha(0.6f)
                )
            }
        }
    }
}

@Composable
private fun RowForecast(
    forecast: ArrayList<ForecastDayDomainModel>?
) {

    val list48h = mutableListOf<HourDomainModel>()
    val currentHour = Instant.now().atZone(ZoneId.systemDefault()).hour

    if(forecast?.isNotEmpty() == true) {
        list48h.addAll(forecast[0].hour)
        list48h.addAll(forecast[1].hour)
    }

    LazyRow(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .placeholder(
                shape = RoundedCornerShape(20.dp),
                visible = forecast?.isNotEmpty() != true,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
            )
    ) {
        item { Spacer(Modifier.padding(horizontal = 4.dp)) }
        if(list48h.isNotEmpty()) {
            for(i in currentHour..currentHour+24) {
                item { RowForecastItem(item = list48h[i]) }
            }
        } else {
            repeat(5){
                item {RowForecastItem(item = HourDomainModel()) }
            }
        }
        item { Spacer(Modifier.padding(horizontal = 4.dp)) }
    }

}

@Composable
private fun RowForecastItem(
    item: HourDomainModel
) {
    Column(
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        val time = if(item.time != null) item.time!!.subSequence(item.time!!.length-5, item.time!!.length) else "0:0"
        Text(
            text = time.toString(), style = Typography.labelMedium.copy(fontWeight = FontWeight.Light),
            modifier = Modifier.alpha(0.6f)
        )
        Text(
            text = (item.tempC?:0).toInt().toString() + "°",
            style = Typography.labelLarge,
            modifier = Modifier.alpha(0.6f)
        )
        LocalContext.current.assets.open("langs.json")
        Icon(
            painter = painterResource(icon(item.condition?.code ?: 0, (item.isDay == 1))),
            contentDescription = null,
            modifier = Modifier
                .padding(8.dp)
                .size(30.dp)
                .alpha(0.6f)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_wi_wind_deg),
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 2.dp)
                    .size(14.dp)
                    .rotate(item.windDegree?.toFloat() ?: 90.0f)
                    .alpha(0.6f)
            )
            val windSpeed = String
                .format("%.1f m/s", item.windKph?.div(3.6) ?: 0.0)
                .replace(",", ".")
            Text(
                text = windSpeed,
                style = Typography.labelMedium,
                modifier = Modifier.alpha(0.6f)
            )
        }
    }
}

@Composable
private fun SunMoveProgress(
    modifier: Modifier = Modifier,
    astro: AstroDomainModel?,
) {
    val primary = MaterialTheme.colorScheme.primary
    val secondary = MaterialTheme.colorScheme.secondary
    var size = IntSize(1000,300)
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .placeholder(
                visible = astro == null,
                shape = RoundedCornerShape(20.dp),
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
            )
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned {
                    size = it.size
                }
        ) {
            val now = LocalTime.now().toSecondOfDay()
            val ssr = LocalTime.parse(astro?.sunrise ?: "00:34 AM", DateTimeFormatter.ofPattern("hh:mm a")).apply {atOffset(
                ZoneOffset.UTC)}.toSecondOfDay()
            val sss = LocalTime.parse(astro?.sunset ?: "11:30 PM", DateTimeFormatter.ofPattern("hh:mm a")).apply {atOffset(
                ZoneOffset.UTC)}.toSecondOfDay()
            val step = (sss - ssr)/120
            val position = if(now<ssr) 0f else if(now in ssr..sss) ((now-ssr)/step).toFloat() else 120f

            val basePath = Path().apply {
                arcTo(
                    rect = size.toIntRect().toRect(),
                    startAngleDegrees = 210f,
                    sweepAngleDegrees = 120f,
                    forceMoveTo = true
                )
            }
            val wentPath = Path().apply {
                arcTo(
                    rect = size.toIntRect().toRect(),
                    startAngleDegrees = 210f,
                    sweepAngleDegrees = position,
                    forceMoveTo = true
                )
            }
            val x = -(size.width / 2 * sin(Math.toRadians(120+position.toDouble()))).toFloat() + (size.width / 2)
            val y = (size.height / 2 * cos(Math.toRadians(120+position.toDouble()))).toFloat() + (size.height / 2)

            Column(
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .alpha(0.6f)
                        .padding(vertical = 16.dp)
                ) {
                    drawPath(
                        path = basePath,
                        color = secondary,
                        style = Stroke(width = 2f, cap = StrokeCap.Round,
                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 20f), 1f)),
                    )
                    drawPath(
                        path = wentPath,
                        color = primary,
                        style = Stroke(width = 6f, cap = StrokeCap.Round),
                    )
                    drawCircle(
                        color = Color(0xFFE6C628),
                        radius = 28f,
                        center = Offset(x,y),
                        style = Stroke(10f, 0f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(2f,5f), 1f))
                    )
                    drawCircle(
                        color = Color(0xFFE6B028),
                        radius = 20f,
                        center = Offset(x,y)
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                ) {
                    val sr = LocalTime.parse(astro?.sunrise ?: "12:34 AM", DateTimeFormatter.ofPattern("hh:mm a"))
                    val ss = LocalTime.parse(astro?.sunset ?: "12:34 PM", DateTimeFormatter.ofPattern("hh:mm a"))
                    Text(
                        text = sr.toString(),
                        style = Typography.labelMedium,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                    )
                    Text(
                        text = ss.toString(),
                        style = Typography.labelMedium,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                    )
                }
            }
        }
    }
}

@Composable
private fun MiscInfo(
    modifier: Modifier = Modifier,
    current: CurrentWeatherDomainModel?
) {
    val light = Typography.labelMedium.copy(fontWeight = FontWeight.Light)
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .placeholder(
                visible = current == null,
                shape = RoundedCornerShape(20.dp),
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
            )
    ) {
        if(current != null) {
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = String.format(stringResource(R.string.humidity), current.humidity ?: 0.0) + "%",
                    style = light,
                    modifier = Modifier.alpha(0.6f)
                )
                Text(
                    text = String.format(stringResource(R.string.pressure), current.pressureMb ?: 0.0),
                    style = light,
                    modifier = Modifier.alpha(0.6f)
                )
                Text(
                    text = String.format(stringResource(R.string.wind_speed), (current.windKph?.div(3.6)) ?: 0.0),
                    style = light,
                    modifier = Modifier.alpha(0.6f)
                )
            }
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = String.format(stringResource(R.string.feels_like), current.feelslikeC ?: 0.0),
                    style = light,
                    modifier = Modifier.alpha(0.6f)
                )
                Text(
                    text = String.format(stringResource(R.string.uv_index), current.uv ?: 0.0),
                    style = light,
                    modifier = Modifier.alpha(0.6f)
                )
                Text(
                    text = String.format(stringResource(R.string.visibility_range), current.visKm ?: 0.0),
                    style = light,
                    modifier = Modifier.alpha(0.6f)
                )
            }
        }
    }
}
