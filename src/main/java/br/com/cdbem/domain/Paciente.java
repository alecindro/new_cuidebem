package br.com.cdbem.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import br.com.cdbem.domain.enumeration.Genero;

import br.com.cdbem.domain.enumeration.TipoEstadia;

/**
 * A Paciente.
 */
@Entity
@Table(name = "paciente")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Paciente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "apelido")
    private String apelido;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "genero", nullable = false)
    private Genero genero;

    @NotNull
    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_estadia")
    private TipoEstadia tipoEstadia;

    @Column(name = "photo")
    private String photo;

    @Column(name = "patologias")
    private String patologias;

    @Column(name = "data_cadastro")
    private LocalDate dataCadastro;

    @NotNull
    @Column(name = "checkin", nullable = false)
    private Boolean checkin;

    @OneToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Responsavel> responsavels = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Paciente nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getApelido() {
        return apelido;
    }

    public Paciente apelido(String apelido) {
        this.apelido = apelido;
        return this;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public Genero getGenero() {
        return genero;
    }

    public Paciente genero(Genero genero) {
        this.genero = genero;
        return this;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public Paciente enabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public Paciente dataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
        return this;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public TipoEstadia getTipoEstadia() {
        return tipoEstadia;
    }

    public Paciente tipoEstadia(TipoEstadia tipoEstadia) {
        this.tipoEstadia = tipoEstadia;
        return this;
    }

    public void setTipoEstadia(TipoEstadia tipoEstadia) {
        this.tipoEstadia = tipoEstadia;
    }

    public String getPhoto() {
        return photo;
    }

    public Paciente photo(String photo) {
        this.photo = photo;
        return this;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPatologias() {
        return patologias;
    }

    public Paciente patologias(String patologias) {
        this.patologias = patologias;
        return this;
    }

    public void setPatologias(String patologias) {
        this.patologias = patologias;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public Paciente dataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
        return this;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Boolean isCheckin() {
        return checkin;
    }

    public Paciente checkin(Boolean checkin) {
        this.checkin = checkin;
        return this;
    }

    public void setCheckin(Boolean checkin) {
        this.checkin = checkin;
    }

    public Set<Responsavel> getResponsavels() {
        return responsavels;
    }

    public Paciente responsavels(Set<Responsavel> responsavels) {
        this.responsavels = responsavels;
        return this;
    }

    public Paciente addResponsavel(Responsavel responsavel) {
        this.responsavels.add(responsavel);
        return this;
    }

    public Paciente removeResponsavel(Responsavel responsavel) {
        this.responsavels.remove(responsavel);
        return this;
    }

    public void setResponsavels(Set<Responsavel> responsavels) {
        this.responsavels = responsavels;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Paciente)) {
            return false;
        }
        return id != null && id.equals(((Paciente) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Paciente{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", apelido='" + getApelido() + "'" +
            ", genero='" + getGenero() + "'" +
            ", enabled='" + isEnabled() + "'" +
            ", dataNascimento='" + getDataNascimento() + "'" +
            ", tipoEstadia='" + getTipoEstadia() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", patologias='" + getPatologias() + "'" +
            ", dataCadastro='" + getDataCadastro() + "'" +
            ", checkin='" + isCheckin() + "'" +
            "}";
    }
}
