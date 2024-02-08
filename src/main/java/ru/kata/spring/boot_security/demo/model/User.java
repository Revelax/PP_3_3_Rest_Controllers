package ru.kata.spring.boot_security.demo.model;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.List;


@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;


  @Column(name = "user_name")
  @NotEmpty(message = "имя не должно быть пустым")
  @Size(min = 2, max = 60, message = "имя должно быть от 2 до 60 символов")
  private String username;


  @Column(name = "last_name")
  @NotEmpty(message = "фамилия не может быть пустой")
  @Size(min = 2, max = 60, message = "фамилия должна быть от 2 до 60 символов")
  private String lastname;


  @Column(name = "age")
  @Min(value = 1, message = "Возраст не может быть меньше 1 лет!")
  @Max(value = 122, message = "Возраст не может быть больше 122 лет!")
  private Byte age;

  @Email
  @Column(name = "email")
  private String email;

  @Column(name = "password")
  @NotEmpty(message = "Пароль не может отсутсвовать")
  private String password;

  @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
  @JoinTable(name = "users_roles",
          joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
          inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
  private Collection<Role> roles;

  public User() {
  }

  public User(String username, String lastname, Byte age, String email, String password, List<Role> roles) {
    this.username = username;
    this.lastname = lastname;
    this.age = age;
    this.email = email;
    this.password = password;
    this.roles = roles;
  }

  public Long getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }


  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }


  public Byte getAge() {
    return age;
  }

  public void setAge(Byte age) {
    this.age = age;
  }


  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }


  public Collection<Role> getRoles() {
    return roles;
  }

  public void setRoles(Collection<Role> roles) {
    this.roles = roles;
  }

}
