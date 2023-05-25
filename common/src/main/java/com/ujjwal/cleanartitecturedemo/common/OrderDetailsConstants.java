package com.ujjwal.cleanartitecturedemo.common;

/**
 *A class that represents an ActivityPlan Constants.
 *
 */
public class OrderDetailsConstants {

  public static final String ORDER_DETAILS_DATA_NOT_FOUND_ERROR_MSG =
      "Order Details not found for orderid :: ";
  public static final String ORDER_DETAILS_BY_ORDER_ID =
          "/getOrderDetails/{order-id}";
  public static final String ORDER_DETAILS_INTERNAL_SERVER_ERROR_MSG =
  "Error occurred while retrieving Order Details with order id ::";

  /**
   * NoArgument Constructor.
   */
  private OrderDetailsConstants() {
  }
}
