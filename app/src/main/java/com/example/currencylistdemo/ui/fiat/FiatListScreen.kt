package com.example.currencylistdemo.ui.fiat

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.currencylistdemo.ui.components.HideableSearchTextField
import com.example.currencylistdemo.ui.components.Operations
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FiatListScreen(viewModel: FiatListViewViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.loadFiats()
    }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .onKeyEvent {
                    if (it.key == Key.Back && state.isSearchActive) {
                        viewModel.onToggleSearch()
                        true
                    } else false
                }
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center,
            ) {
                HideableSearchTextField(
                    text = state.searchText,
                    isSearchActive = state.isSearchActive,
                    onTextChange = viewModel::onSearchTextChange,
                    onSearchClick = viewModel::onToggleSearch,
                    onCloseClick = viewModel::onToggleSearch,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp)
                )
                this@Column.AnimatedVisibility(
                    visible = !state.isSearchActive,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Text(
                        text = "All Fiats",
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp
                    )
                }
            }

            if (state.fiats.isEmpty()) {
                if (state.searchText.isNotEmpty())// In search state
                    Icon(
                        imageVector = Icons.Filled.Warning,
                        contentDescription = "no results",
                        modifier = Modifier
                            .padding(top = 30.dp)
                            .size(200.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                else { // Init state
                    Operations(
                        add = viewModel::addNewFiat,
                        addAll = viewModel::addAllFiats,
                        deleteAll = viewModel::deleteAllFiats
                    )
                }
            } else {
                if (state.searchText.isBlank()) { // hide it up when user is searching.
                    Operations(
                        add = viewModel::addNewFiat,
                        addAll = viewModel::addAllFiats,
                        deleteAll = viewModel::deleteAllFiats
                    )
                }

                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(bottom = 100.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    items(
                        items = state.fiats,
                        key = { it.id }
                    ) { fiat -> FiatItem(fiat) }
                }
            }
        }

    }
}

