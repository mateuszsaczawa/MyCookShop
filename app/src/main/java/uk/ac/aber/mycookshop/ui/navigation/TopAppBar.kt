package uk.ac.aber.mycookshop.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import uk.ac.aber.mycookshop.R
import uk.ac.aber.mycookshop.ui.theme.MyCookShopTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    onClickMenu: () -> Unit = {},
    onClickOffice: () -> Unit = {}
){
    CenterAlignedTopAppBar(
        title = { Text(stringResource(id = R.string.app_name)) },
        navigationIcon = {
            IconButton(onClick = onClickMenu) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = stringResource(R.string.nav_drawer_menu)
                )
            }
        },
        actions = {
            IconButton(onClick = onClickOffice) {
                Icon(
                    imageVector = Icons.Filled.Store,
                    contentDescription = stringResource(R.string.office_icon_description)
                )
            }
        }
    )
}

@Preview
@Composable
private fun MainPageTopAppBarPreview() {
    MyCookShopTheme(dynamicColor = false) {
        TopAppBar()
    }
}