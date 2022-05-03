package me.eastack.model;

import java.lang.Integer;
import java.lang.String;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
class User {
  public Integer id;

  public String username;

  public String firstName;

  public String lastName;

  public String email;

  public String password;

  public String phone;

  /**
   * User Status
   */
  public Integer userStatus;
}
