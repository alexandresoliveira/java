package br.com.cwi.sicredi.pautas.shared.databases.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table( name = "pautas" )
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode( callSuper = false )
public class Pauta extends Base {

    @Column
    @NotNull
    @NotBlank
    @Size( min = 1, max = 80 )
    private String name;
}
