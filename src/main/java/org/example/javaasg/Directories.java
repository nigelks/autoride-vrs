package org.example.javaasg;

public class Directories {
    private static final String USERS_DB = "./db/users.txt";
    private static final String CAR_LIST_DB = "./db/vehicles/car_list.txt";
    private static final String CUSTOMER_RENTAL_HISTORY_DB = "./db/userorder/";
    private static final String CREDIT_CARD_LOGO_DIR = "/assets/static/cc-logos";
    private static final String ALL_ORDERS = "./db/all_orders.txt";


    // Getters
    public static String getUsersDB() {
        return USERS_DB;
    }

    public static String getCarList() {
        return CAR_LIST_DB;
    }

    public static String getMastercardLogo() {
        return CREDIT_CARD_LOGO_DIR + "/Mastercard.png";
    }

    public static String getVisaLogo() {
        return CREDIT_CARD_LOGO_DIR + "/Visa.png";
    }

    // Get the customer rental history database
    public static String getCustomerRentalHistoryDB() {
        return CUSTOMER_RENTAL_HISTORY_DB + User.getLoggedInUserEmail() + "_order.txt";
    }

    public static String getOrderDB() {
        return ALL_ORDERS;
    }
}
