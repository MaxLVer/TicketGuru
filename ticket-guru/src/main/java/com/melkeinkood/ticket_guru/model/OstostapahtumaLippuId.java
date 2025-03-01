package com.melkeinkood.ticket_guru.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable

public class OstostapahtumaLippuId implements Serializable {

    private Long ostostapahtumaId;
    private Long lippuId;

    public OstostapahtumaLippuId() {
    }

    public OstostapahtumaLippuId(Long ostostapahtumaId, Long lippuId) {
        this.ostostapahtumaId = ostostapahtumaId;
        this.lippuId = lippuId;
    }

    public Long getOstostapahtumaId() {
        return ostostapahtumaId;
    }

    public void setOstostapahtumaId(Long ostostapoahtumaId) {
        this.ostostapahtumaId = ostostapoahtumaId;
    }

    public Long getLippuId() {
        return lippuId;
    }

    public void setLippuId(Long lippuId) {
        this.lippuId = lippuId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        OstostapahtumaLippuId that = (OstostapahtumaLippuId) o;
        return Objects.equals(ostostapahtumaId, that.ostostapahtumaId) &&
                Objects.equals(lippuId, that.lippuId);
    }

}
