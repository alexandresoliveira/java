package br.com.cwi.sicredi.pautas.shared.databases.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table( name = "votacoes" )
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode( callSuper = false )
public class Votacao extends Base {

    @Column(name = "data_encerramento")
    @Temporal( TemporalType.TIMESTAMP )
    @NotNull
    private Date dataEncerramento;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pauta_id")
    private Pauta pauta;
}
