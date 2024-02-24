package uk.ac.aber.mycookshop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.mycookshop.ui.navigation.Navig
import uk.ac.aber.mycookshop.ui.screens.CookScreen
import uk.ac.aber.mycookshop.ui.screens.OrderScreen
import uk.ac.aber.mycookshop.ui.theme.MyCookShopTheme


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyCookShopTheme(dynamicColor = false) {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BuildNavigationGraph()
                }
            }
        }
    }
}

@Composable
private fun BuildNavigationGraph(

) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Navig.Cook.route
    ) {
        composable(Navig.Orders.route) { OrderScreen(navController) }
        composable(Navig.Cook.route) {
                CookScreen(navController, "cook") }
        composable(Navig.Mid.route) { CookScreen(navController, "mid") }
        composable(Navig.Front.route) { CookScreen(navController, "front") }

        }
    }
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyCookShopTheme(dynamicColor = false) {
        BuildNavigationGraph()
    }
}
