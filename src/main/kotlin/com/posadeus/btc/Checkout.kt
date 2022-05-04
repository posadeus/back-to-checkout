package com.posadeus.btc

class Checkout(private val rules: List<Rule>) {

  private val receipt: Receipt = Receipt(mutableMapOf())

  fun price(products: String): Int {

    products.chunked(1).forEach { scan(it) }

    return total()
  }

  fun scan(product: String) {
    receipt.addProduct(product, getProductInfo(product))
  }

  fun total(): Int =
      when {
        receipt.products.isEmpty() -> 0
        else -> receipt.getTotal()
      }

  private fun getProductInfo(product: String): ProductInfo =
      when {
        receipt.hasProduct(product) -> updateProductInfo(product)
        else -> newProductInfo(product)
      }

  private fun newProductInfo(product: String): ProductInfo =
      ProductInfo(1, getPrice(product))

  private fun updateProductInfo(product: String): ProductInfo =
      receipt.products[product]!!
          .copy(quantity = receipt.products[product]!!.quantity.plus(1),
                price = getPrice(product))

  private fun getPrice(product: String): Int {

    val productRule = rules.first { product == it.productName }
    val promoPieces = productRule.promo?.pieces
    val productQuantity = receipt.products[product]?.quantity?.plus(1) ?: 1

    return if (promoPieces != null)
      if (productRule.promo.isApplicable(productQuantity))
        productRule.promo.applyPromotion(productQuantity)
      else if (promoPieces < productQuantity)
        halfPromotionApplied(productQuantity,
                             promoPieces,
                             productRule.promo.price,
                             productRule.productPrice,
                             productQuantity / promoPieces)
      else
        noPromotionApplied(productQuantity, productRule.productPrice)
    else
      productRule.productPrice
  }

  private fun noPromotionApplied(productQuantity: Int, productPrice: Int) =
      productPrice * productQuantity

  private fun halfPromotionApplied(productQuantity: Int,
                                   promoPieces: Int,
                                   promoPrice: Int,
                                   productPrice: Int,
                                   promotionsApplied: Int) =
      promotionsApplied * promoPrice + productPrice * (productQuantity - promotionsApplied * promoPieces)
}

data class ProductInfo(val quantity: Int,
                       val price: Int)

