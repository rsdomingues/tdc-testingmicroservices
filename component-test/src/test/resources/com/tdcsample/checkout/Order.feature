@FixtureLoad
@CleanDatabase
Feature: Place Order

    Scenario: Place an valid order
        Given I have stock from product
        And an order for "One Moto X4, paid with VISA"
        And that is being purchased by "sampleuser"
        When I place the order
        Then I receive an confirmantion

    Scenario: Place an invalid order with no item
        Given an order for "Invalid order with VISA"
        And that is being purchased by "sampleuser"
        When I place the order
        Then I receive an error

    Scenario: Place an invalid order with no payment
        Given an order for "Invalid order with no payment"
        And that is being purchased by "sampleuser"
        When I place the order
        Then I receive an error

    Scenario: Place an valid order with billet payment feature on
        Given I have stock from product
        And an order for "One Moto X4, paid with BILLET"
        And that is being purchased by "sampleuser"
        And the feature BILLET_PAYMENT is enabled
        When I place the order
        Then I receive an confirmantion

    Scenario: Place an valid order with billet payment feature off
        Given I have stock from product
        And an order for "One Moto X4, paid with BILLET"
        And that is being purchased by "sampleuser"
        And the feature BILLET_PAYMENT is disabled
        When I place the order
        Then I receive an error