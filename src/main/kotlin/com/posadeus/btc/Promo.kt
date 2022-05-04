package com.posadeus.btc

data class Promo(val pieces: Int,
                 val price: Int) {

  fun applyPromotion(productQuantity: Int, productPrice: Int, promoPieces: Int) =
      if (isApplicable(productQuantity) || promoPieces < productQuantity)
        addProductToAppliedPromotion(productQuantity, productPrice, productQuantity / pieces)
      else
        productPrice * productQuantity

  private fun isApplicable(productQuantity: Int) =
      productQuantity % pieces == 0

  private fun apply(quantity: Int) =
      price * quantity

  private fun addProductToAppliedPromotion(productQuantity: Int,
                                           productPrice: Int,
                                           numPromotionsApplied: Int) =
      apply(numPromotionsApplied) + applyToSurplus(productPrice, productQuantity, numPromotionsApplied)

  private fun applyToSurplus(productPrice: Int,
                             productQuantity: Int,
                             numPromotionsApplied: Int) =
      productPrice * (productQuantity - numPromotionsApplied * pieces)
}
