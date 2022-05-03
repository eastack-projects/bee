package me.eastack.model;

import java.lang.Integer;
import java.lang.String;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
class Tag {
  public Integer id;

  public String name;
}
