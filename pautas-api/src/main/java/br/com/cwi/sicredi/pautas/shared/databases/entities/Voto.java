package br.com.cwi.sicredi.pautas.shared.databases.entities;

import br.com.cwi.sicredi.pautas.shared.databases.enums.VotoResposta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table( name = "votos" )
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode( callSuper = false )
public class Voto extends Base {

    @Column(name = "cpf")
    @NotNull
    private String cpf;

    @Column(name = "resposta")
    @NotNull
    @Enumerated(EnumType.STRING)
    private VotoResposta resposta;

    @Column(name = "data_voto")
    @NotNull
    private Date dataVoto;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "votacao_id")
    private Votacao votacao;
}
