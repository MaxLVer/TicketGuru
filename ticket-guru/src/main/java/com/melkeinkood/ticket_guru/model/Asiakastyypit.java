package com.melkeinkood.ticket_guru.model;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "asiakastyypit")
public class Asiakastyypit {

        
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "asiakastyyppiId")
    private Long asiakastyyppiId;


    @NotNull
    @Size(min=1 , max=100)
    @Column(name = "asiakastyyppi")
    private String asiakastyyppi;

    
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "asiakastyyppi")
    @JsonIgnore
    private List<TapahtumaLipputyypit> tapahtumaLipputyypit;

    



    public Asiakastyypit(Long asiakastyyppiId, @NotNull @Size(min = 1, max = 100) String asiakastyyppi,
            List<TapahtumaLipputyypit> tapahtumaLipputyypit) {
        this.asiakastyyppiId = asiakastyyppiId;
        this.asiakastyyppi = asiakastyyppi;
        this.tapahtumaLipputyypit = tapahtumaLipputyypit;
    }




    public Asiakastyypit(@NotNull @Size(min = 1, max = 100) String asiakastyyppi,
            List<TapahtumaLipputyypit> tapahtumaLipputyypit) {
        this.asiakastyyppi = asiakastyyppi;
        this.tapahtumaLipputyypit = tapahtumaLipputyypit;
    }




    public Asiakastyypit() {
        super();
    }

    

    public Long getasiakastyyppiId() {
        return asiakastyyppiId;
    }

    public void setasiakastyyppiId(Long asiakastyyppiId) {
        this.asiakastyyppiId = asiakastyyppiId;
    }
    public String getAsiakastyyppi() {
        return asiakastyyppi;
    }



    public void setAsiakastyyppi(String asiakastyyppi) {
        this.asiakastyyppi = asiakastyyppi;
    }
    
}
