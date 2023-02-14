package app.seals.weather.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.seals.weather.R
import app.seals.weather.UiIntent
import app.seals.weather.data.models.LocationDataModel
import app.seals.weather.domain.models.AutocompleteDomainModel
import app.seals.weather.ui.theme.Typography

private val list = listOf(
    AutocompleteDomainModel(),
    AutocompleteDomainModel(),
    AutocompleteDomainModel(),
    AutocompleteDomainModel(),
)

@Composable
@Preview
fun LocationDialog(
    modifier: Modifier = Modifier,
    current: LocationDataModel = LocationDataModel(),
    locations: List<AutocompleteDomainModel> = list,
    autocomplete: List<AutocompleteDomainModel> = list,
    reducer: (UiIntent) -> Unit = {}
) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Column {
            Surface(
               shape = RoundedCornerShape(5.dp),
               color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
               modifier = Modifier
                   .padding(horizontal = 64.dp, vertical = 4.dp)
                   .height(4.dp)
                   .fillMaxWidth()
            ) {}
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                LazyColumn() {
                    if(locations.isNotEmpty()) locations.forEach {
                        item { StoredLocation(item = it) { reducer(UiIntent.Delete.Location(it) ) } }
                    }
                }

                Spacer(Modifier.height(8.dp))

                CustomSearchField(
                    modifier = Modifier
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(20.dp)
                        ),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
                            modifier = Modifier.padding(end = 4.dp)
                        )
                    },
                    textUpdate = { if(it.isNotBlank()) reducer(UiIntent.Autocomplete(it)) }
                )
                Divider(thickness = 1.dp, modifier = Modifier.padding(horizontal = 36.dp))

                Spacer(Modifier.height(8.dp))

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .heightIn(max = 200.dp)
                ) {
                    if(autocomplete.isNotEmpty()) autocomplete.forEach {
                        item { AutocompleteItem(it) { reducer(UiIntent.Add.Location(it)) } }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun StoredLocation(
    item: AutocompleteDomainModel,
    delete: (Int) -> Unit = { }
) {

    var deleteIsVisible by remember { mutableStateOf(false) }

    Surface(
        shape = RoundedCornerShape(20.dp),
        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
        modifier = Modifier.padding(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .combinedClickable (
                    onLongClick = { deleteIsVisible = !deleteIsVisible },
                ) {}
        ) {
            Column {
                Text(
                    text = item.name.toString(),
                    style = Typography.titleMedium
                )
                Text(
                    text = "${item.region}, ${item.country}",
                    style = Typography.titleMedium
                )
            }
            if(deleteIsVisible) Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
                modifier = Modifier
                    .size(30.dp)
                    .weight(1f)
                    .clickable { delete(item.id) }
                    .clip(CircleShape)
            )
        }

    }
}

@Composable
private fun AutocompleteItem(
    item: AutocompleteDomainModel,
    onSelect: (AutocompleteDomainModel) -> Unit
) {
    Column(
        Modifier.padding(bottom = 4.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onSelect(item) }
        ) {
            Column {
                Text(text = item.name.toString(), style = Typography.titleMedium)
                Text(
                    text = "${item.region}, ${item.country}",
                    style = Typography.labelSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = String.format(stringResource(R.string.location_latitude),item.lat ?: 0.0),
                    style = Typography.labelSmall
                )
                Text(
                    text = String.format(stringResource(R.string.location_longitude),item.lat ?: 0.0),
                    style = Typography.labelSmall
                )
            }
        }
        Divider(Modifier.padding(vertical = 4.dp))
    }

}

@Composable
private fun CustomSearchField(
    modifier: Modifier = Modifier,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    placeholderText: String = stringResource(id = R.string.location_label),
    style: TextStyle = MaterialTheme.typography.labelMedium,
    textUpdate: (text: String) -> Unit = { }
) {

    var text by remember { mutableStateOf("") }
    BasicTextField(
        modifier = modifier,
        value = text,
        onValueChange = {
            text = it
        },
        singleLine = true,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
        textStyle = style,
        decorationBox = { innerTextField ->
            Row(
                modifier.padding(start = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (leadingIcon != null) leadingIcon()
                Box(Modifier.weight(1f)) {
                    if (text.isEmpty()) androidx.compose.material3.Text(
                        placeholderText,
                        style = style
                    )
                    innerTextField()
                }
                if (trailingIcon != null) trailingIcon()
            }
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Go),
        keyboardActions = KeyboardActions(
            onGo = {
//                kbController?.hide()
                textUpdate(text)
            }
        )
    )
}