package com.checkmooney.naeats.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.checkmooney.naeats.ui.components.BottomNavigationBar
import com.checkmooney.naeats.ui.components.NaEatsScaffold
import com.checkmooney.naeats.ui.components.NavigationItem.*
import com.checkmooney.naeats.ui.components.TopBar
import com.checkmooney.naeats.ui.theme.NaEatsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                NaEatsTheme {
                    viewModel.viewItem.observeAsState().value?.let { currentViewItem ->
                        NaEatsScaffold(
                            topBar = { TopBar(title = currentViewItem.description) },
                            bottomBar = {
                                BottomNavigationBar(
                                    currentViewItem,
                                    onClick = { viewModel.updateViewItem(it) }
                                )
                            }) {
                            Box(modifier = Modifier.padding(it)) {
                                when (currentViewItem) {
                                    Recommend -> {
                                        TodayRecommend()
                                    }
                                    TodayEats -> {
                                        TodayEats()
                                    }
                                    Setting -> {
                                        Setting()
                                    }
                                }
                            }
                        }
                    }

                }
            }
        }
    }
}
