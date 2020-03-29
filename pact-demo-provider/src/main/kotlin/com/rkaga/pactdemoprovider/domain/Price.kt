package com.rkaga.pactdemoprovider.domain

data class Price(
        val taxExclude: Int
) {
    val taxInclude: Int
        get() {
            return (taxExclude * 1.1).toInt()
        }
}
