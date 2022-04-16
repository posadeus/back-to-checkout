package com.posadeus.btc

class Checkout(private val rules: List<Rule>) {

  private val receipt: Receipt = Receipt(emptyMap())

  fun price(products: String): Int {

    when {
      products.isEmpty() -> return 0
      else -> products.chunked(1)
          .forEach { createReceipt(it) }
    }

    return receipt.getTotal()
  }

  private fun createReceipt(product: String) {

    receipt.products =
        if (receipt.isEmpty()) addNewProductToReceipt(product)
        else addQuantityAndPriceToProduct(product)
  }

  private fun addQuantityAndPriceToProduct(product: String) =
      if (receipt.products.containsKey(product)) {

        HashMap(receipt.products +
                mapOf(product to receipt.products[product]!!.copy(quantity = receipt.products[product]!!.quantity.plus(1),
                                                                  price = scan(product))))
      }
      else addNewProductToReceipt(product)

  private fun addNewProductToReceipt(product: String): Map<String, PromoPrice> =
      HashMap(receipt.products + mapOf(product to PromoPrice(1, scan(product))))

  private fun scan(product: String): Int {

    val productRule = rules.first { product == it.productName }

    return if (isProductInPromo(productRule, product))
      productRule.promo?.price ?: productRule.productPrice
    else
      receipt.products[product]?.price ?: 0 + productRule.productPrice
  }

  private fun isProductInPromo(productRule: Rule, product: String) =
      productRule.promo?.pieces == receipt.products[product]?.quantity?.plus(1)
}

data class PromoPrice(val quantity: Int,
                      val price: Int)

