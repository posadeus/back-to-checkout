# Back to the Checkout

___________________

### Credits

Inspired by [codekata.com](http://codekata.com/kata/kata09-back-to-the-checkout/)
___

### Goal

Implement the code for a supermarket checkout that calculates the total price of a number of items.<br/>
In our store, we’ll use individual letters of the alphabet (A, B, C, and so on) to identify products.
Our goods are priced individually.
In addition, some items are multi-priced: buy n of them, and they’ll cost you y cents.

### Rules

- Use TDD
- No red phases while refactoring.
- Our checkout accepts items in any order.
- Follow the tests from the link.

### Requirements

- The interface to the checkout should look like:

```
co = CheckOut.new(pricing_rules)
co.scan(item)
co.scan(item)
:    :
price = co.total
```