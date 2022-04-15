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

    receipt.products = if (receipt.isEmpty()) {

      addProductToReceipt(product)
    }
    else {

      addQuantityAndPriceToProduct(product)
    }
  }

  private fun addQuantityAndPriceToProduct(product: String) =
      if (receipt.products.containsKey(product)) {

        HashMap(receipt.products +
                mapOf(product to receipt.products[product]!!.copy(quantity = receipt.products[product]!!.quantity.plus(1),
                                                                  price = priceWithPromo(product))))
      }
      else {

        addProductToReceipt(product)
      }

  private fun addProductToReceipt(product: String): Map<String, PromoPrice> =
      HashMap(receipt.products + mapOf(product to PromoPrice(1, scan(product))))

  private fun priceWithPromo(product: String): Int =
      if (rules.first { product == it.productName }.promo?.pieces == receipt.products[product]!!.quantity.plus(1))
        rules.first { product == it.productName }.promo!!.price
      else receipt.products[product]!!.price +
           rules.first { product == it.productName }.productPrice

  private fun scan(product: String): Int =
      rules.first { product == it.productName }
          .productPrice
}

data class PromoPrice(val quantity: Int,
                      val price: Int)

