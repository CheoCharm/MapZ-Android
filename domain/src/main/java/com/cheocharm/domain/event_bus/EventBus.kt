package com.cheocharm.domain.event_bus

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.filter

object EventBus {

    private val _events = MutableSharedFlow<MapZEvent>()
    val events = _events.asSharedFlow()

    suspend fun invokeEvent(event: MapZEvent) = _events.emit(event)

    suspend fun subscribeEvent(event: MapZEvent, onEvent: () -> Unit) {
        events.filter { it == event }.collect { onEvent() }
    }
}
