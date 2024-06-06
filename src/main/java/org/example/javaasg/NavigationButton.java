package org.example.javaasg;

public class NavigationButton {
    public void showButtons(NavigationController navigationController, boolean isAdmin) {
        if (isAdmin) {
            // Admin
            navigationController.getNavMenuSalesReport().setVisible(true);
            navigationController.getNavMenuAdminNotification().setVisible(true);
            navigationController.getNavMenuManagement().setVisible(true);

            navigationController.getNavMenuHBox().getChildren().remove(navigationController.getNavMenuCustNotification());
            navigationController.getNavMenuHBox().getChildren().remove(navigationController.getNavMenuRentalHistory());
        } else {
            // Customer
            navigationController.getNavMenuCustNotification().setVisible(true);

            navigationController.getNavMenuHBox().getChildren().remove(navigationController.getNavMenuSalesReport());
            navigationController.getNavMenuHBox().getChildren().remove(navigationController.getNavMenuAdminNotification());
            navigationController.getNavMenuHBox().getChildren().remove(navigationController.getNavMenuManagement());
        }
    }
}
