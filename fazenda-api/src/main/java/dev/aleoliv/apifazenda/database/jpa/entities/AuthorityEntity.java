package dev.aleoliv.apifazenda.database.jpa.entities;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "authorities")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = false)
public class AuthorityEntity extends BaseEntity implements GrantedAuthority {

  private static final long serialVersionUID = 1L;

  @NotNull
  @NotEmpty
  @Size(min = 3, max = 100)
  @Column(name = "name")
  private String name;

  @PrePersist
  public void prePersist() {
    if (!name.startsWith("ROLE_"))
      this.name = String.format("ROLE_%s", this.name);
  }

  @Override
  public String getAuthority() {
    return this.name;
  }
}
