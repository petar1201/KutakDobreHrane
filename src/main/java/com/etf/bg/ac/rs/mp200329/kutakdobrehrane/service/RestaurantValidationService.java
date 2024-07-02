package com.etf.bg.ac.rs.mp200329.kutakdobrehrane.service;

import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.model.restaurant.Kitchen;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.model.restaurant.RestaurantLayout;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.model.restaurant.Table;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.model.restaurant.Toalet;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RestaurantValidationService {

    public boolean isLayoutValid(RestaurantLayout layout) {
        if (!checkTablesKitchensOverlap(layout.getTables(), layout.getKitchens())) {
            return false;
        }

        if (!checkKitchensToiletsOverlap(layout.getKitchens(), layout.getToalets())) {
            return false;
        }

        if (!checkTablesToiletsOverlap(layout.getTables(), layout.getToalets())) {
            return false;
        }

        if (!checkFitWithinRestaurant(layout)) {
            return false;
        }

        return true;
    }


    private boolean checkTablesKitchensOverlap(List<Table> tables, List<Kitchen> kitchens) {
        for (Table table : tables) {
            for (Kitchen kitchen : kitchens) {
                if (doRectanglesOverlap(
                        table.getX() - table.getR(),
                        table.getY() - table.getR(),
                        2 * table.getR(),
                        2 * table.getR(),
                        kitchen.getX(),
                        kitchen.getY(),
                        kitchen.getWidth(),
                        kitchen.getHeight()
                )) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkKitchensToiletsOverlap(List<Kitchen> kitchens, List<Toalet> toalets) {
        for (Kitchen kitchen : kitchens) {
            for (Toalet toalet : toalets) {
                if (doRectanglesOverlap(
                        kitchen.getX(),
                        kitchen.getY(),
                        kitchen.getWidth(),
                        kitchen.getHeight(),
                        toalet.getX(),
                        toalet.getY(),
                        toalet.getWidth(),
                        toalet.getHeight()
                )) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkTablesToiletsOverlap(List<Table> tables, List<Toalet> toalets) {
        for (Table table : tables) {
            for (Toalet toalet : toalets) {
                if (doRectanglesOverlap(
                        table.getX() - table.getR(),
                        table.getY() - table.getR(),
                        2 * table.getR(),
                        2 * table.getR(),
                        toalet.getX(),
                        toalet.getY(),
                        toalet.getWidth(),
                        toalet.getHeight()
                )) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkFitWithinRestaurant(RestaurantLayout layout) {
        double totalAreaOccupied = 0;

        for (Table table : layout.getTables()) {
            totalAreaOccupied += Math.PI * table.getR() * table.getR(); // Area of circle
        }

        for (Kitchen kitchen : layout.getKitchens()) {
            totalAreaOccupied += kitchen.getWidth() * kitchen.getHeight(); // Area of rectangle
        }

        for (Toalet toalet : layout.getToalets()) {
            totalAreaOccupied += toalet.getWidth() * toalet.getHeight(); // Area of rectangle
        }

        double restaurantArea = layout.getWidth() * layout.getHeight();
        return totalAreaOccupied <= restaurantArea;
    }

    private boolean doRectanglesOverlap(int x1, int y1, int w1, int h1, int x2, int y2, int w2, int h2) {
        return (
                x1 < x2 + w2 &&
                        x1 + w1 > x2 &&
                        y1 < y2 + h2 &&
                        y1 + h1 > y2
        );
    }
}
