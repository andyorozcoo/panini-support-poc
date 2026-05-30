package com.panini.support.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.panini.support.ui.screens.login.LoginScreen
import com.panini.support.ui.screens.tickets.CreateTicketScreen
import com.panini.support.ui.screens.tickets.TicketDetailScreen
import com.panini.support.ui.screens.tickets.TicketListScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppDestinations.LOGIN
    ) {
        composable(AppDestinations.LOGIN) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(AppDestinations.TICKET_LIST) {
                        popUpTo(AppDestinations.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        composable(AppDestinations.TICKET_LIST) {
            TicketListScreen(
                onCreateTicketClick = { navController.navigate(AppDestinations.CREATE_TICKET) },
                onTicketClick = { ticketId ->
                    navController.navigate(AppDestinations.ticketDetailRoute(ticketId))
                }
            )
        }

        composable(AppDestinations.CREATE_TICKET) {
            CreateTicketScreen(
                onBackClick = { navController.popBackStack() },
                onTicketCreated = { ticketId ->
                    navController.navigate(AppDestinations.ticketDetailRoute(ticketId)) {
                        popUpTo(AppDestinations.TICKET_LIST)
                    }
                }
            )
        }

        composable(
            route = AppDestinations.TICKET_DETAIL,
            arguments = listOf(navArgument("ticketId") { type = NavType.StringType })
        ) { backStackEntry ->
            TicketDetailScreen(
                ticketId = backStackEntry.arguments?.getString("ticketId").orEmpty(),
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}