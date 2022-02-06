package com.checkmooney.naeats.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.checkmooney.naeats.components.BottomNavigationBar
import com.checkmooney.naeats.components.NaEatsScaffold
import com.checkmooney.naeats.components.NavigationItem
import com.checkmooney.naeats.components.NavigationItem.*
import com.checkmooney.naeats.components.TopBar
import com.checkmooney.naeats.ui.theme.NaEatsTheme

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                var currentViewItem by rememberSaveable { mutableStateOf(Recommend) }
                NaEatsTheme {
                    NaEatsScaffold(
                        topBar = { TopBar(title = currentViewItem.description) },
                        bottomBar = {
                            BottomNavigationBar(
                                currentViewItem,
                                onClick = { item -> currentViewItem = item }
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
