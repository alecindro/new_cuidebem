package br.com.cdbem.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import br.com.cdbem.domain.enumeration.Vinculo;
import br.com.cdbem.domain.enumeration.Genero;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link br.com.cdbem.domain.Responsavel} entity. This class is used
 * in {@link br.com.cdbem.web.rest.ResponsavelResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /responsavels?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ResponsavelCriteria implements Serializable, Criteria {
    /**
     * Class for filtering Vinculo
     */
    public static class VinculoFilter extends Filter<Vinculo> {

        public VinculoFilter() {
        }

        public VinculoFilter(VinculoFilter filter) {
            super(filter);
        }

        @Override
        public VinculoFilter copy() {
            return new VinculoFilter(this);
        }

    }
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

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private StringFilter sobrenome;

    private StringFilter email;

    private BooleanFilter enabled;

    private LocalDateFilter dataNascimento;

    private VinculoFilter vinculo;

    private StringFilter cpf;

    private StringFilter rg;

    private StringFilter endereco;

    private StringFilter cidade;

    private GeneroFilter genero;

    private StringFilter cep;

    private StringFilter photo;

    private StringFilter obs;

    private StringFilter telefones;

    public ResponsavelCriteria(){
    }

    public ResponsavelCriteria(ResponsavelCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.sobrenome = other.sobrenome == null ? null : other.sobrenome.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.enabled = other.enabled == null ? null : other.enabled.copy();
        this.dataNascimento = other.dataNascimento == null ? null : other.dataNascimento.copy();
        this.vinculo = other.vinculo == null ? null : other.vinculo.copy();
        this.cpf = other.cpf == null ? null : other.cpf.copy();
        this.rg = other.rg == null ? null : other.rg.copy();
        this.endereco = other.endereco == null ? null : other.endereco.copy();
        this.cidade = other.cidade == null ? null : other.cidade.copy();
        this.genero = other.genero == null ? null : other.genero.copy();
        this.cep = other.cep == null ? null : other.cep.copy();
        this.photo = other.photo == null ? null : other.photo.copy();
        this.obs = other.obs == null ? null : other.obs.copy();
        this.telefones = other.telefones == null ? null : other.telefones.copy();
    }

    @Override
    public ResponsavelCriteria copy() {
        return new ResponsavelCriteria(this);
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

    public StringFilter getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(StringFilter sobrenome) {
        this.sobrenome = sobrenome;
    }

    public StringFilter getEmail() {
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
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

    public VinculoFilter getVinculo() {
        return vinculo;
    }

    public void setVinculo(VinculoFilter vinculo) {
        this.vinculo = vinculo;
    }

    public StringFilter getCpf() {
        return cpf;
    }

    public void setCpf(StringFilter cpf) {
        this.cpf = cpf;
    }

    public StringFilter getRg() {
        return rg;
    }

    public void setRg(StringFilter rg) {
        this.rg = rg;
    }

    public StringFilter getEndereco() {
        return endereco;
    }

    public void setEndereco(StringFilter endereco) {
        this.endereco = endereco;
    }

    public StringFilter getCidade() {
        return cidade;
    }

    public void setCidade(StringFilter cidade) {
        this.cidade = cidade;
    }

    public GeneroFilter getGenero() {
        return genero;
    }

    public void setGenero(GeneroFilter genero) {
        this.genero = genero;
    }

    public StringFilter getCep() {
        return cep;
    }

    public void setCep(StringFilter cep) {
        this.cep = cep;
    }

    public StringFilter getPhoto() {
        return photo;
    }

    public void setPhoto(StringFilter photo) {
        this.photo = photo;
    }

    public StringFilter getObs() {
        return obs;
    }

    public void setObs(StringFilter obs) {
        this.obs = obs;
    }

    public StringFilter getTelefones() {
        return telefones;
    }

    public void setTelefones(StringFilter telefones) {
        this.telefones = telefones;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ResponsavelCriteria that = (ResponsavelCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(sobrenome, that.sobrenome) &&
            Objects.equals(email, that.email) &&
            Objects.equals(enabled, that.enabled) &&
            Objects.equals(dataNascimento, that.dataNascimento) &&
            Objects.equals(vinculo, that.vinculo) &&
            Objects.equals(cpf, that.cpf) &&
            Objects.equals(rg, that.rg) &&
            Objects.equals(endereco, that.endereco) &&
            Objects.equals(cidade, that.cidade) &&
            Objects.equals(genero, that.genero) &&
            Objects.equals(cep, that.cep) &&
            Objects.equals(photo, that.photo) &&
            Objects.equals(obs, that.obs) &&
            Objects.equals(telefones, that.telefones);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nome,
        sobrenome,
        email,
        enabled,
        dataNascimento,
        vinculo,
        cpf,
        rg,
        endereco,
        cidade,
        genero,
        cep,
        photo,
        obs,
        telefones
        );
    }

    @Override
    public String toString() {
        return "ResponsavelCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nome != null ? "nome=" + nome + ", " : "") +
                (sobrenome != null ? "sobrenome=" + sobrenome + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (enabled != null ? "enabled=" + enabled + ", " : "") +
                (dataNascimento != null ? "dataNascimento=" + dataNascimento + ", " : "") +
                (vinculo != null ? "vinculo=" + vinculo + ", " : "") +
                (cpf != null ? "cpf=" + cpf + ", " : "") +
                (rg != null ? "rg=" + rg + ", " : "") +
                (endereco != null ? "endereco=" + endereco + ", " : "") +
                (cidade != null ? "cidade=" + cidade + ", " : "") +
                (genero != null ? "genero=" + genero + ", " : "") +
                (cep != null ? "cep=" + cep + ", " : "") +
                (photo != null ? "photo=" + photo + ", " : "") +
                (obs != null ? "obs=" + obs + ", " : "") +
                (telefones != null ? "telefones=" + telefones + ", " : "") +
            "}";
    }

}
