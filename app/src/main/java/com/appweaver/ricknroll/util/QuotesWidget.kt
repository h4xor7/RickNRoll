package com.appweaver.ricknroll.util

import android.content.Context
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.action.ActionParameters
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.provideContent
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.Box
import androidx.glance.layout.padding
import androidx.glance.state.PreferencesGlanceStateDefinition
import  androidx.glance.text.Text


private val quotes = listOf(
    "Nobody exists on purpose, nobody belongs anywhere, everybody's gonna die. Come watch TV. ― Morty",
    "To live is to risk it all; otherwise, you're just an inert chunk of randomly assembled molecules drifting wherever the universe blows you. ― Rick",
    "Sometimes science is more art than science, Morty. A lot of people don’t get that. ― Rick",
    "Listen, Morty, I hate to break it to you, but what people call 'love' is just a chemical reaction that compels animals to breed. ― Rick",

    )

private val currentQuoteKey = stringPreferencesKey("currentQuote")

class QuotesWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            val preferences = currentState<Preferences>()
            val currentQuote = preferences[currentQuoteKey] ?: quotes.random()

            MaterialTheme {
                Box(
                    modifier = GlanceModifier
                        .background(Color.White)
                        .padding(16.dp)
                        .clickable(actionRunCallback<RefreshQuoteAction>())
                ) {
                    Text(text = currentQuote)
                }
            }


        }
    }
}

class RefreshQuoteAction : ActionCallback {

    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {

        updateAppWidgetState(context, PreferencesGlanceStateDefinition, glanceId) { preferences ->
            preferences.toMutablePreferences().apply {
                this[currentQuoteKey] = quotes.random()
            }
        }
        QuotesWidget().update(context, glanceId)
    }
}