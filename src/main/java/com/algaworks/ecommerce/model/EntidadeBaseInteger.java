package com.algaworks.ecommerce.model;

import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@ToString
@MappedSuperclass
public class EntidadeBaseInteger {

    @Id
    @GeneratedValue(strategy = SEQUENCE/*IDENTITY*/)
    private Integer id;

    @Version
    private Integer versao;

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final EntidadeBaseInteger that = (EntidadeBaseInteger) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
