package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * This class is used to generate an immutable bill object every time an order is created.
 * @param id
 * @param clientName
 * @param productName
 * @param quantity
 * @param date
 */
public record Bill(int id, String clientName, String productName, int quantity, LocalDate date) {
}
