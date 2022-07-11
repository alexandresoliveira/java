package br.com.cwi.sicredi.pautas.shared.databases.entities;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@MappedSuperclass
@EntityListeners( AuditingEntityListener.class )
@Data
public class Base {

    @Id
    @Column
    @GeneratedValue( generator = "hibernate-uuid" )
    @GenericGenerator( name = "uuid", strategy = "uuid2" )
    private UUID id;

    @Column( name = "created_at" )
    @Temporal( TemporalType.TIMESTAMP )
    @CreatedDate
    private Date createdAt;

    @Column( name = "updated_at", nullable = false )
    @Temporal( TemporalType.TIMESTAMP )
    @LastModifiedDate
    private Date updatedAt;
}
