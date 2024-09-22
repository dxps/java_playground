Feature: Prioritizing orders

  In order to organize my team better,
  As a head barista preparing order in a large coffee shop,
  I need to have an idea of how many drink orders are coming and in what order

  Background:
    Given Cathy is a CaffeinateMe customer

  Rule: Orders placed close to the store should be considered as Urgent
    Scenario Outline: Order priority depends on the distance from the shop
      Given Cathy is <Distance> meters from the coffee shop
      When Cathy orders a "large cappuccino"
      Then Barry should receive the order
      And Barry should know that the order is <Status>
      Examples:
        | Rule                          | Distance | Status |
        | Less than 5 minutes away      | 100      | Urgent |
        | Between 5 and 10 minutes away | 300      | Normal |
        | More than 10 minutes away     | 1000     | Low    |
