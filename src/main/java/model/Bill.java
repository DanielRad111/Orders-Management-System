package model;

import java.time.LocalDateTime;

public record Bill(int id, int clientId, int productId, int quantity) {
}
