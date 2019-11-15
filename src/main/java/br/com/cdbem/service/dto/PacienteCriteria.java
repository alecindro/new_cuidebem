package br.com.cdbem.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import br.com.cdbem.domain.enumeration.Genero;
import br.com.cdbem.domain.enumeration.TipoEstadia;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link br.com.cdbem.domain.Paciente} entity. This class is used
 * in {@link br.com.cdbem.web.rest.PacienteResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /pacientes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PacienteCriteria implements Serializable, Criteria {
    /**
     * Class for filtering Genero
     */
    public static class GeneroFilter extends Filter<Genero> {

        public GeneroFilter() {
        }

        public GeneroFilter(GeneroFilter filter) {
            super(filter);
        }

        @Override
        public GeneroFilter copy() {
            return new GeneroFilter(this);
        }

    }
    /**
     * Class for filtering TipoEstadia
     */
    public static class TipoEstadiaFilter extends Filter<TipoEstadia> {

        public TipoEstadiaFilter() {
        }

        public TipoEstadiaFilter(TipoEstadiaFilter filter) {
            super(filter);
        }

        @Override
        public TipoEstadiaFilter copy() {
            return new TipoEstadiaFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private StringFilter apelido;

    private GeneroFilter genero;

    private BooleanFilter enabled;

    private LocalDateFilter dataNascimento;

    private TipoEstadiaFilter tipoEstadia;

    private StringFilter photo;

    private StringFilter patologias;

    private LocalDateFilter dataCadastro;

    private BooleanFilter checkin;

    private LongFilter responsavelId;

    public PacienteCriteria(){
    }

    public PacienteCriteria(PacienteCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.apelido = other.apelido == null ? null : other.apelido.copy();
        this.genero = other.genero == null ? null : other.genero.copy();
        this.enabled = other.enabled == null ? null : other.enabled.copy();
        this.dataNascimento = other.dataNascimento == null ? null : other.dataNascimento.copy();
        this.tipoEstadia = other.tipoEstadia == null ? null : other.tipoEstadia.copy();
        this.photo = other.photo == null ? null : other.photo.copy();
        this.patologias = other.patologias == null ? null : other.patologias.copy();
        this.dataCadastro = other.dataCadastro == null ? null : other.dataCadastro.copy();
        this.checkin = other.checkin == null ? null : other.checkin.copy();
        this.responsavelId = other.responsavelId == null ? null : other.responsavelId.copy();
    }

    @Override
    public PacienteCriteria copy() {
        return new PacienteCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNome() {
        return nome;
    }

    public void setNome(StringFilter nome) {
        this.nome = nome;
    }

    public StringFilter getApelido() {
        return apelido;
    }

    public void setApelido(StringFilter apelido) {
        this.apelido = apelido;
    }

    public GeneroFilter getGenero() {
        return genero;
    }

    public void setGenero(GeneroFilter genero) {
        this.genero = genero;
    }

    public BooleanFilter getEnabled() {
        return enabled;
    }

    public void setEnabled(BooleanFilter enabled) {
        this.enabled = enabled;
    }

    public LocalDateFilter getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDateFilter dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public TipoEstadiaFilter getTipoEstadia() {
        return tipoEstadia;
    }

    public void setTipoEstadia(TipoEstadiaFilter tipoEstadia) {
        this.tipoEstadia = tipoEstadia;
    }

    public StringFilter getPhoto() {
        return photo;
    }

    public void setPhoto(StringFilter photo) {
        this.photo = photo;
    }

    public StringFilter getPatologias() {
        return patologias;
    }

    public void setPatologias(StringFilter patologias) {
        this.patologias = patologias;
    }

    public LocalDateFilter getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateFilter dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public BooleanFilter getCheckin() {
        return checkin;
    }

    public void setCheckin(BooleanFilter checkin) {
        this.checkin = checkin;
    }

    public LongFilter getResponsavelId() {
        return responsavelId;
    }

    public void setResponsavelId(LongFilter responsavelId) {
        this.responsavelId = responsavelId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PacienteCriteria that = (PacienteCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(apelido, that.apelido) &&
            Objects.equals(genero, that.genero) &&
            Objects.equals(enabled, that.enabled) &&
            Objects.equals(dataNascimento, that.dataNascimento) &&
            Objects.equals(tipoEstadia, that.tipoEstadia) &&
            Objects.equals(photo, that.photo) &&
            Objects.equals(patologias, that.patologias) &&
            Objects.equals(dataCadastro, that.dataCadastro) &&
            Objects.equals(checkin, that.checkin) &&
            Objects.equals(responsavelId, that.responsavelId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nome,
        apelido,
        genero,
        enabled,
        dataNascimento,
        tipoEstadia,
        photo,
        patologias,
        dataCadastro,
        checkin,
        responsavelId
        );
    }

    @Override
    public String toString() {
        return "PacienteCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nome != null ? "nome=" + nome + ", " : "") +
                (apelido != null ? "apelido=" + apelido + ", " : "") +
                (genero != null ? "genero=" + genero + ", " : "") +
                (enabled != null ? "enabled=" + enabled + ", " : "") +
                (dataNascimento != null ? "dataNascimento=" + dataNascimento + ", " : "") +
                (tipoEstadia != null ? "tipoEstadia=" + tipoEstadia + ", " : "") +
                (photo != null ? "photo=" + photo + ", " : "") +
                (patologias != null ? "patologias=" + patologias + ", " : "") +
                (dataCadastro != null ? "dataCadastro=" + dataCadastro + ", " : "") +
                (checkin != null ? "checkin=" + checkin + ", " : "") +
                (responsavelId != null ? "responsavelId=" + responsavelId + ", " : "") +
            "}";
    }

}
