package me.eastack.model;

import java.lang.Boolean;
import java.lang.Integer;
import java.lang.String;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
class Order {
  /**
   * 用户唯一ID
   */
  public Integer id;

  /**
   * Pet ID
   */
  public Integer petId;

  /**
   * 用户购买数量{@link String}
   */
  public Integer quantity;

  public LocalDateTime shipDate;

  public String status;

  public Boolean complete;
}
