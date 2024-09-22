Feature: Order a coffee

  In order to save time when I pick up my morning coffee,
  as a coffee lover I want to be able to order my coffee in advance.

  Background:
    Given Cathy is a CaffeinateMe customer

  Rule: Orders placed close to the store should be considered as Urgent
    Example: Buyer orders a coffee when they are close to the coffee shop
      Given Cathy is 100 meters from the coffee shop
      When Cathy orders a "large cappuccino"
      Then Barry should receive the order
      And Barry should know that the order is Urgent

    Example: Buyer orders a coffee when they are not close to the coffee shop
      Given Cathy is 300 meters from the coffee shop
      When Cathy orders a "large cappuccino"
      Then Barry should receive the order
      And Barry should know that the order is Normal

  Rule: Buyer can specify their preferences when he orders
    Example: Buyer ca add a comment with their order
      When Cathy orders a "large cappuccino" with a comment "Double sugar"
      Then Barry should receive the order
      And the order should have the comment "Double sugar"

  Rule: Buyer can order many items in the same order
    Example: Buyer orders two items in an order
      When Cathy places an order for the following items:
        | Product          | Quantity |
        | Large Cappuccino | 1        |
        | Espresso         | 2        |
      Then Barry should receive the order
      And the order should contain 2 line items
      And the order should contain the following products:
        | Large Cappuccino |
        | Espresso         |
