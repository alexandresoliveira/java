package dev.aleoliv.apifazenda.database.jpa.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class UserEntity extends BaseEntity implements UserDetails {

  @NotNull
  @NotEmpty
  @Size(max = 100)
  @Column(name = "name", columnDefinition = "varchar(100) not null", nullable = false, length = 100)
  private String name;

  @NotNull
  @NotEmpty
  @Email
  @Size(max = 100)
  @Column(name = "email", columnDefinition = "varchar(100) not null unique", nullable = false, length = 100, unique = true)
  private String email;

  @NotNull
  @NotEmpty
  @Size(max = 256)
  @Column(name = "password", columnDefinition = "varchar(256) not null", nullable = false, length = 256, unique = true)
  private String pass;

  @Column(name = "enabled")
  private Boolean enabled = true;

  @ManyToMany(fetch = FetchType.EAGER)
  private Set<AuthorityEntity> authorities;

  public String getPassword() {
    return pass;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return false;
  }

  @Override
  public boolean isAccountNonLocked() {
    return false;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return false;
  }

  @Override
  public boolean isEnabled() {
    return enabled;
  }
}
