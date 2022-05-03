package com.posadeus.btc

import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class CheckoutTest {

  private val rules = listOf(Rule("", 0, null),
                             Rule("A", 50, Promo(3, 130)),
                             Rule("B", 30, Promo(2, 45)),
                             Rule("C", 20, null),
                             Rule("D", 15, null))

  private val checkout = Checkout(rules)

  @Test
  internal fun `price of empty cart is zero`() {

    assertTrue { checkout.price("") == 0 }
  }

  @Test
  internal fun `price of cart with A product is 50`() {

    assertTrue { checkout.price("A") == 50 }
  }

  @Test
  internal fun `price of cart with B product is 30`() {

    assertTrue { checkout.price("B") == 30 }
  }

  @Test
  internal fun `price of cart with A,B product is 80`() {

    assertTrue { checkout.price("AB") == 80 }
  }

  @Test
  internal fun `price of cart with A,B,C,D product is 115`() {

    assertTrue { checkout.price("CDBA") == 115 }
  }

  @Test
  internal fun `price of cart with three A product is 130`() {

    assertTrue { checkout.price("AAA") == 130 }
  }

  @Test
  internal fun `price of cart with six A product is 260`() {

    assertTrue { checkout.price("AAAAAA") == 260 }
  }

  @Test
  internal fun `price of cart with two A product is 100`() {

    assertTrue { checkout.price("AA") == 100 }
  }

  @Test
  internal fun `price of cart with four A product is 180`() {

    assertTrue { checkout.price("AAAA") == 180 }
  }

  @Test
  internal fun `price of cart with three A and B product is 160`() {

    assertTrue { checkout.price("AAAB") == 160 }
  }

  @Test
  internal fun `price of cart with three A and two B product is 175`() {

    assertTrue { checkout.price("AAABB") == 175 }
  }

  @Test
  internal fun `incremental price`() {

    assertTrue { checkout.total() == 0 }

    checkout.scan("A")
    assertTrue { checkout.total() == 50 }

    checkout.scan("B")
    assertTrue { checkout.total() == 80 }

    checkout.scan("A")
    assertTrue { checkout.total() == 130 }

    checkout.scan("A")
    assertTrue { checkout.total() == 160 }

    checkout.scan("B")
    assertTrue { checkout.total() == 175 }
  }
}