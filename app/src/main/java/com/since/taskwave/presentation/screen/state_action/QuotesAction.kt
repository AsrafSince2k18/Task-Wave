package com.since.taskwave.presentation.screen.state_action

import com.since.taskwave.data.modal.database.Quotes

sealed interface QuotesAction {

    data class Delete(val quotes: Quotes) : QuotesAction
    data object RepeatTimeRequest : QuotesAction

}