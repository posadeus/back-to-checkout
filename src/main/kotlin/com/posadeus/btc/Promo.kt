package com.posadeus.btc

data class Promo(val pieces: Int,
                 val price: Int) {

  fun isApplicable(productQuantity: Int) =
      productQuantity % pieces == 0

  fun applyPromotion(productQuantity: Int) =
      price * (productQuantity / pieces)
}
