package com.since.taskwave.data.mapper

import com.since.taskwave.data.modal.database.Quotes
import com.since.taskwave.data.modal.network.QuoteNetwork

fun QuoteNetwork.toQuotes(typeRequest:String): Quotes{
    return Quotes(
        author = author,
        id=id,
        quote = quote,
        time = System.currentTimeMillis(),
        typeRequest = typeRequest
    )
}