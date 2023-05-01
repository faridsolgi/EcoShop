package com.faridsolgi.ecoshop.model

data class CheckOut(
    var cardNumber: String="",
    var totalAmount: Float=0f,
    var trackingCode: String="",
):java.io.Serializable