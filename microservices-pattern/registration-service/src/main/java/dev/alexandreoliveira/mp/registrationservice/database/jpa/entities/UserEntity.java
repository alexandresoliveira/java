package dev.alexandreoliveira.mp.registrationservice.database.jpa.entities;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "users", schema = "registration")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
public class UserEntity extends BaseEntity implements Serializable {

  @NotNull
  @NotEmpty
  @Size(min = 5, max = 100)
  @Column(name = "name", nullable = false, length = 100)
  private String name;

  @NotNull
  @NotEmpty
  @Email
  @Column(name = "email", nullable = false, length = 100)
  private String email;

  @NotNull
  @NotEmpty
  @Size(min = 8, max = 200)
  @Column(name = "password", nullable = false, length = 200)
  private String password;
}
