package br.com.cdbem.web.rest;

import br.com.cdbem.CdbemApp;
import br.com.cdbem.domain.Responsavel;
import br.com.cdbem.repository.ResponsavelRepository;
import br.com.cdbem.service.ResponsavelService;
import br.com.cdbem.web.rest.errors.ExceptionTranslator;
import br.com.cdbem.service.dto.ResponsavelCriteria;
import br.com.cdbem.service.ResponsavelQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static br.com.cdbem.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.cdbem.domain.enumeration.Vinculo;
import br.com.cdbem.domain.enumeration.Genero;
/**
 * Integration tests for the {@link ResponsavelResource} REST controller.
 */
@SpringBootTest(classes = CdbemApp.class)
public class ResponsavelResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_SOBRENOME = "AAAAAAAAAA";
    private static final String UPDATED_SOBRENOME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ENABLED = false;
    private static final Boolean UPDATED_ENABLED = true;

    private static final LocalDate DEFAULT_DATA_NASCIMENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_NASCIMENTO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATA_NASCIMENTO = LocalDate.ofEpochDay(-1L);

    private static final Vinculo DEFAULT_VINCULO = Vinculo.FILHO;
    private static final Vinculo UPDATED_VINCULO = Vinculo.CONJUGE;

    private static final String DEFAULT_CPF = "AAAAAAAAAA";
    private static final String UPDATED_CPF = "BBBBBBBBBB";

    private static final String DEFAULT_RG = "AAAAAAAAAA";
    private static final String UPDATED_RG = "BBBBBBBBBB";

    private static final String DEFAULT_ENDERECO = "AAAAAAAAAA";
    private static final String UPDATED_ENDERECO = "BBBBBBBBBB";

    private static final String DEFAULT_CIDADE = "AAAAAAAAAA";
    private static final String UPDATED_CIDADE = "BBBBBBBBBB";

    private static final Genero DEFAULT_GENERO = Genero.MASCULINO;
    private static final Genero UPDATED_GENERO = Genero.FEMININO;

    private static final String DEFAULT_CEP = "AAAAAAAAAA";
    private static final String UPDATED_CEP = "BBBBBBBBBB";

    private static final String DEFAULT_PHOTO = "AAAAAAAAAA";
    private static final String UPDATED_PHOTO = "BBBBBBBBBB";

    private static final String DEFAULT_OBS = "AAAAAAAAAA";
    private static final String UPDATED_OBS = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONES = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONES = "BBBBBBBBBB";

    @Autowired
    private ResponsavelRepository responsavelRepository;

    @Autowired
    private ResponsavelService responsavelService;

    @Autowired
    private ResponsavelQueryService responsavelQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restResponsavelMockMvc;

    private Responsavel responsavel;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ResponsavelResource responsavelResource = new ResponsavelResource(responsavelService, responsavelQueryService);
        this.restResponsavelMockMvc = MockMvcBuilders.standaloneSetup(responsavelResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Responsavel createEntity(EntityManager em) {
        Responsavel responsavel = new Responsavel()
            .nome(DEFAULT_NOME)
            .sobrenome(DEFAULT_SOBRENOME)
            .email(DEFAULT_EMAIL)
            .enabled(DEFAULT_ENABLED)
            .dataNascimento(DEFAULT_DATA_NASCIMENTO)
            .vinculo(DEFAULT_VINCULO)
            .cpf(DEFAULT_CPF)
            .rg(DEFAULT_RG)
            .endereco(DEFAULT_ENDERECO)
            .cidade(DEFAULT_CIDADE)
            .genero(DEFAULT_GENERO)
            .cep(DEFAULT_CEP)
            .photo(DEFAULT_PHOTO)
            .obs(DEFAULT_OBS)
            .telefones(DEFAULT_TELEFONES);
        return responsavel;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Responsavel createUpdatedEntity(EntityManager em) {
        Responsavel responsavel = new Responsavel()
            .nome(UPDATED_NOME)
            .sobrenome(UPDATED_SOBRENOME)
            .email(UPDATED_EMAIL)
            .enabled(UPDATED_ENABLED)
            .dataNascimento(UPDATED_DATA_NASCIMENTO)
            .vinculo(UPDATED_VINCULO)
            .cpf(UPDATED_CPF)
            .rg(UPDATED_RG)
            .endereco(UPDATED_ENDERECO)
            .cidade(UPDATED_CIDADE)
            .genero(UPDATED_GENERO)
            .cep(UPDATED_CEP)
            .photo(UPDATED_PHOTO)
            .obs(UPDATED_OBS)
            .telefones(UPDATED_TELEFONES);
        return responsavel;
    }

    @BeforeEach
    public void initTest() {
        responsavel = createEntity(em);
    }

    @Test
    @Transactional
    public void createResponsavel() throws Exception {
        int databaseSizeBeforeCreate = responsavelRepository.findAll().size();

        // Create the Responsavel
        restResponsavelMockMvc.perform(post("/api/responsavels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(responsavel)))
            .andExpect(status().isCreated());

        // Validate the Responsavel in the database
        List<Responsavel> responsavelList = responsavelRepository.findAll();
        assertThat(responsavelList).hasSize(databaseSizeBeforeCreate + 1);
        Responsavel testResponsavel = responsavelList.get(responsavelList.size() - 1);
        assertThat(testResponsavel.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testResponsavel.getSobrenome()).isEqualTo(DEFAULT_SOBRENOME);
        assertThat(testResponsavel.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testResponsavel.isEnabled()).isEqualTo(DEFAULT_ENABLED);
        assertThat(testResponsavel.getDataNascimento()).isEqualTo(DEFAULT_DATA_NASCIMENTO);
        assertThat(testResponsavel.getVinculo()).isEqualTo(DEFAULT_VINCULO);
        assertThat(testResponsavel.getCpf()).isEqualTo(DEFAULT_CPF);
        assertThat(testResponsavel.getRg()).isEqualTo(DEFAULT_RG);
        assertThat(testResponsavel.getEndereco()).isEqualTo(DEFAULT_ENDERECO);
        assertThat(testResponsavel.getCidade()).isEqualTo(DEFAULT_CIDADE);
        assertThat(testResponsavel.getGenero()).isEqualTo(DEFAULT_GENERO);
        assertThat(testResponsavel.getCep()).isEqualTo(DEFAULT_CEP);
        assertThat(testResponsavel.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testResponsavel.getObs()).isEqualTo(DEFAULT_OBS);
        assertThat(testResponsavel.getTelefones()).isEqualTo(DEFAULT_TELEFONES);
    }

    @Test
    @Transactional
    public void createResponsavelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = responsavelRepository.findAll().size();

        // Create the Responsavel with an existing ID
        responsavel.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restResponsavelMockMvc.perform(post("/api/responsavels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(responsavel)))
            .andExpect(status().isBadRequest());

        // Validate the Responsavel in the database
        List<Responsavel> responsavelList = responsavelRepository.findAll();
        assertThat(responsavelList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkEnabledIsRequired() throws Exception {
        int databaseSizeBeforeTest = responsavelRepository.findAll().size();
        // set the field null
        responsavel.setEnabled(null);

        // Create the Responsavel, which fails.

        restResponsavelMockMvc.perform(post("/api/responsavels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(responsavel)))
            .andExpect(status().isBadRequest());

        List<Responsavel> responsavelList = responsavelRepository.findAll();
        assertThat(responsavelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllResponsavels() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList
        restResponsavelMockMvc.perform(get("/api/responsavels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(responsavel.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].sobrenome").value(hasItem(DEFAULT_SOBRENOME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].dataNascimento").value(hasItem(DEFAULT_DATA_NASCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].vinculo").value(hasItem(DEFAULT_VINCULO.toString())))
            .andExpect(jsonPath("$.[*].cpf").value(hasItem(DEFAULT_CPF.toString())))
            .andExpect(jsonPath("$.[*].rg").value(hasItem(DEFAULT_RG.toString())))
            .andExpect(jsonPath("$.[*].endereco").value(hasItem(DEFAULT_ENDERECO.toString())))
            .andExpect(jsonPath("$.[*].cidade").value(hasItem(DEFAULT_CIDADE.toString())))
            .andExpect(jsonPath("$.[*].genero").value(hasItem(DEFAULT_GENERO.toString())))
            .andExpect(jsonPath("$.[*].cep").value(hasItem(DEFAULT_CEP.toString())))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(DEFAULT_PHOTO.toString())))
            .andExpect(jsonPath("$.[*].obs").value(hasItem(DEFAULT_OBS.toString())))
            .andExpect(jsonPath("$.[*].telefones").value(hasItem(DEFAULT_TELEFONES.toString())));
    }
    
    @Test
    @Transactional
    public void getResponsavel() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get the responsavel
        restResponsavelMockMvc.perform(get("/api/responsavels/{id}", responsavel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(responsavel.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.sobrenome").value(DEFAULT_SOBRENOME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.enabled").value(DEFAULT_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.dataNascimento").value(DEFAULT_DATA_NASCIMENTO.toString()))
            .andExpect(jsonPath("$.vinculo").value(DEFAULT_VINCULO.toString()))
            .andExpect(jsonPath("$.cpf").value(DEFAULT_CPF.toString()))
            .andExpect(jsonPath("$.rg").value(DEFAULT_RG.toString()))
            .andExpect(jsonPath("$.endereco").value(DEFAULT_ENDERECO.toString()))
            .andExpect(jsonPath("$.cidade").value(DEFAULT_CIDADE.toString()))
            .andExpect(jsonPath("$.genero").value(DEFAULT_GENERO.toString()))
            .andExpect(jsonPath("$.cep").value(DEFAULT_CEP.toString()))
            .andExpect(jsonPath("$.photo").value(DEFAULT_PHOTO.toString()))
            .andExpect(jsonPath("$.obs").value(DEFAULT_OBS.toString()))
            .andExpect(jsonPath("$.telefones").value(DEFAULT_TELEFONES.toString()));
    }

    @Test
    @Transactional
    public void getAllResponsavelsByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList where nome equals to DEFAULT_NOME
        defaultResponsavelShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the responsavelList where nome equals to UPDATED_NOME
        defaultResponsavelShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllResponsavelsByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultResponsavelShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the responsavelList where nome equals to UPDATED_NOME
        defaultResponsavelShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllResponsavelsByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList where nome is not null
        defaultResponsavelShouldBeFound("nome.specified=true");

        // Get all the responsavelList where nome is null
        defaultResponsavelShouldNotBeFound("nome.specified=false");
    }

    @Test
    @Transactional
    public void getAllResponsavelsBySobrenomeIsEqualToSomething() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList where sobrenome equals to DEFAULT_SOBRENOME
        defaultResponsavelShouldBeFound("sobrenome.equals=" + DEFAULT_SOBRENOME);

        // Get all the responsavelList where sobrenome equals to UPDATED_SOBRENOME
        defaultResponsavelShouldNotBeFound("sobrenome.equals=" + UPDATED_SOBRENOME);
    }

    @Test
    @Transactional
    public void getAllResponsavelsBySobrenomeIsInShouldWork() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList where sobrenome in DEFAULT_SOBRENOME or UPDATED_SOBRENOME
        defaultResponsavelShouldBeFound("sobrenome.in=" + DEFAULT_SOBRENOME + "," + UPDATED_SOBRENOME);

        // Get all the responsavelList where sobrenome equals to UPDATED_SOBRENOME
        defaultResponsavelShouldNotBeFound("sobrenome.in=" + UPDATED_SOBRENOME);
    }

    @Test
    @Transactional
    public void getAllResponsavelsBySobrenomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList where sobrenome is not null
        defaultResponsavelShouldBeFound("sobrenome.specified=true");

        // Get all the responsavelList where sobrenome is null
        defaultResponsavelShouldNotBeFound("sobrenome.specified=false");
    }

    @Test
    @Transactional
    public void getAllResponsavelsByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList where email equals to DEFAULT_EMAIL
        defaultResponsavelShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the responsavelList where email equals to UPDATED_EMAIL
        defaultResponsavelShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllResponsavelsByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultResponsavelShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the responsavelList where email equals to UPDATED_EMAIL
        defaultResponsavelShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllResponsavelsByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList where email is not null
        defaultResponsavelShouldBeFound("email.specified=true");

        // Get all the responsavelList where email is null
        defaultResponsavelShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    public void getAllResponsavelsByEnabledIsEqualToSomething() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList where enabled equals to DEFAULT_ENABLED
        defaultResponsavelShouldBeFound("enabled.equals=" + DEFAULT_ENABLED);

        // Get all the responsavelList where enabled equals to UPDATED_ENABLED
        defaultResponsavelShouldNotBeFound("enabled.equals=" + UPDATED_ENABLED);
    }

    @Test
    @Transactional
    public void getAllResponsavelsByEnabledIsInShouldWork() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList where enabled in DEFAULT_ENABLED or UPDATED_ENABLED
        defaultResponsavelShouldBeFound("enabled.in=" + DEFAULT_ENABLED + "," + UPDATED_ENABLED);

        // Get all the responsavelList where enabled equals to UPDATED_ENABLED
        defaultResponsavelShouldNotBeFound("enabled.in=" + UPDATED_ENABLED);
    }

    @Test
    @Transactional
    public void getAllResponsavelsByEnabledIsNullOrNotNull() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList where enabled is not null
        defaultResponsavelShouldBeFound("enabled.specified=true");

        // Get all the responsavelList where enabled is null
        defaultResponsavelShouldNotBeFound("enabled.specified=false");
    }

    @Test
    @Transactional
    public void getAllResponsavelsByDataNascimentoIsEqualToSomething() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList where dataNascimento equals to DEFAULT_DATA_NASCIMENTO
        defaultResponsavelShouldBeFound("dataNascimento.equals=" + DEFAULT_DATA_NASCIMENTO);

        // Get all the responsavelList where dataNascimento equals to UPDATED_DATA_NASCIMENTO
        defaultResponsavelShouldNotBeFound("dataNascimento.equals=" + UPDATED_DATA_NASCIMENTO);
    }

    @Test
    @Transactional
    public void getAllResponsavelsByDataNascimentoIsInShouldWork() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList where dataNascimento in DEFAULT_DATA_NASCIMENTO or UPDATED_DATA_NASCIMENTO
        defaultResponsavelShouldBeFound("dataNascimento.in=" + DEFAULT_DATA_NASCIMENTO + "," + UPDATED_DATA_NASCIMENTO);

        // Get all the responsavelList where dataNascimento equals to UPDATED_DATA_NASCIMENTO
        defaultResponsavelShouldNotBeFound("dataNascimento.in=" + UPDATED_DATA_NASCIMENTO);
    }

    @Test
    @Transactional
    public void getAllResponsavelsByDataNascimentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList where dataNascimento is not null
        defaultResponsavelShouldBeFound("dataNascimento.specified=true");

        // Get all the responsavelList where dataNascimento is null
        defaultResponsavelShouldNotBeFound("dataNascimento.specified=false");
    }

    @Test
    @Transactional
    public void getAllResponsavelsByDataNascimentoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList where dataNascimento is greater than or equal to DEFAULT_DATA_NASCIMENTO
        defaultResponsavelShouldBeFound("dataNascimento.greaterThanOrEqual=" + DEFAULT_DATA_NASCIMENTO);

        // Get all the responsavelList where dataNascimento is greater than or equal to UPDATED_DATA_NASCIMENTO
        defaultResponsavelShouldNotBeFound("dataNascimento.greaterThanOrEqual=" + UPDATED_DATA_NASCIMENTO);
    }

    @Test
    @Transactional
    public void getAllResponsavelsByDataNascimentoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList where dataNascimento is less than or equal to DEFAULT_DATA_NASCIMENTO
        defaultResponsavelShouldBeFound("dataNascimento.lessThanOrEqual=" + DEFAULT_DATA_NASCIMENTO);

        // Get all the responsavelList where dataNascimento is less than or equal to SMALLER_DATA_NASCIMENTO
        defaultResponsavelShouldNotBeFound("dataNascimento.lessThanOrEqual=" + SMALLER_DATA_NASCIMENTO);
    }

    @Test
    @Transactional
    public void getAllResponsavelsByDataNascimentoIsLessThanSomething() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList where dataNascimento is less than DEFAULT_DATA_NASCIMENTO
        defaultResponsavelShouldNotBeFound("dataNascimento.lessThan=" + DEFAULT_DATA_NASCIMENTO);

        // Get all the responsavelList where dataNascimento is less than UPDATED_DATA_NASCIMENTO
        defaultResponsavelShouldBeFound("dataNascimento.lessThan=" + UPDATED_DATA_NASCIMENTO);
    }

    @Test
    @Transactional
    public void getAllResponsavelsByDataNascimentoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList where dataNascimento is greater than DEFAULT_DATA_NASCIMENTO
        defaultResponsavelShouldNotBeFound("dataNascimento.greaterThan=" + DEFAULT_DATA_NASCIMENTO);

        // Get all the responsavelList where dataNascimento is greater than SMALLER_DATA_NASCIMENTO
        defaultResponsavelShouldBeFound("dataNascimento.greaterThan=" + SMALLER_DATA_NASCIMENTO);
    }


    @Test
    @Transactional
    public void getAllResponsavelsByVinculoIsEqualToSomething() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList where vinculo equals to DEFAULT_VINCULO
        defaultResponsavelShouldBeFound("vinculo.equals=" + DEFAULT_VINCULO);

        // Get all the responsavelList where vinculo equals to UPDATED_VINCULO
        defaultResponsavelShouldNotBeFound("vinculo.equals=" + UPDATED_VINCULO);
    }

    @Test
    @Transactional
    public void getAllResponsavelsByVinculoIsInShouldWork() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList where vinculo in DEFAULT_VINCULO or UPDATED_VINCULO
        defaultResponsavelShouldBeFound("vinculo.in=" + DEFAULT_VINCULO + "," + UPDATED_VINCULO);

        // Get all the responsavelList where vinculo equals to UPDATED_VINCULO
        defaultResponsavelShouldNotBeFound("vinculo.in=" + UPDATED_VINCULO);
    }

    @Test
    @Transactional
    public void getAllResponsavelsByVinculoIsNullOrNotNull() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList where vinculo is not null
        defaultResponsavelShouldBeFound("vinculo.specified=true");

        // Get all the responsavelList where vinculo is null
        defaultResponsavelShouldNotBeFound("vinculo.specified=false");
    }

    @Test
    @Transactional
    public void getAllResponsavelsByCpfIsEqualToSomething() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList where cpf equals to DEFAULT_CPF
        defaultResponsavelShouldBeFound("cpf.equals=" + DEFAULT_CPF);

        // Get all the responsavelList where cpf equals to UPDATED_CPF
        defaultResponsavelShouldNotBeFound("cpf.equals=" + UPDATED_CPF);
    }

    @Test
    @Transactional
    public void getAllResponsavelsByCpfIsInShouldWork() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList where cpf in DEFAULT_CPF or UPDATED_CPF
        defaultResponsavelShouldBeFound("cpf.in=" + DEFAULT_CPF + "," + UPDATED_CPF);

        // Get all the responsavelList where cpf equals to UPDATED_CPF
        defaultResponsavelShouldNotBeFound("cpf.in=" + UPDATED_CPF);
    }

    @Test
    @Transactional
    public void getAllResponsavelsByCpfIsNullOrNotNull() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList where cpf is not null
        defaultResponsavelShouldBeFound("cpf.specified=true");

        // Get all the responsavelList where cpf is null
        defaultResponsavelShouldNotBeFound("cpf.specified=false");
    }

    @Test
    @Transactional
    public void getAllResponsavelsByRgIsEqualToSomething() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList where rg equals to DEFAULT_RG
        defaultResponsavelShouldBeFound("rg.equals=" + DEFAULT_RG);

        // Get all the responsavelList where rg equals to UPDATED_RG
        defaultResponsavelShouldNotBeFound("rg.equals=" + UPDATED_RG);
    }

    @Test
    @Transactional
    public void getAllResponsavelsByRgIsInShouldWork() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList where rg in DEFAULT_RG or UPDATED_RG
        defaultResponsavelShouldBeFound("rg.in=" + DEFAULT_RG + "," + UPDATED_RG);

        // Get all the responsavelList where rg equals to UPDATED_RG
        defaultResponsavelShouldNotBeFound("rg.in=" + UPDATED_RG);
    }

    @Test
    @Transactional
    public void getAllResponsavelsByRgIsNullOrNotNull() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList where rg is not null
        defaultResponsavelShouldBeFound("rg.specified=true");

        // Get all the responsavelList where rg is null
        defaultResponsavelShouldNotBeFound("rg.specified=false");
    }

    @Test
    @Transactional
    public void getAllResponsavelsByEnderecoIsEqualToSomething() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList where endereco equals to DEFAULT_ENDERECO
        defaultResponsavelShouldBeFound("endereco.equals=" + DEFAULT_ENDERECO);

        // Get all the responsavelList where endereco equals to UPDATED_ENDERECO
        defaultResponsavelShouldNotBeFound("endereco.equals=" + UPDATED_ENDERECO);
    }

    @Test
    @Transactional
    public void getAllResponsavelsByEnderecoIsInShouldWork() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList where endereco in DEFAULT_ENDERECO or UPDATED_ENDERECO
        defaultResponsavelShouldBeFound("endereco.in=" + DEFAULT_ENDERECO + "," + UPDATED_ENDERECO);

        // Get all the responsavelList where endereco equals to UPDATED_ENDERECO
        defaultResponsavelShouldNotBeFound("endereco.in=" + UPDATED_ENDERECO);
    }

    @Test
    @Transactional
    public void getAllResponsavelsByEnderecoIsNullOrNotNull() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList where endereco is not null
        defaultResponsavelShouldBeFound("endereco.specified=true");

        // Get all the responsavelList where endereco is null
        defaultResponsavelShouldNotBeFound("endereco.specified=false");
    }

    @Test
    @Transactional
    public void getAllResponsavelsByCidadeIsEqualToSomething() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList where cidade equals to DEFAULT_CIDADE
        defaultResponsavelShouldBeFound("cidade.equals=" + DEFAULT_CIDADE);

        // Get all the responsavelList where cidade equals to UPDATED_CIDADE
        defaultResponsavelShouldNotBeFound("cidade.equals=" + UPDATED_CIDADE);
    }

    @Test
    @Transactional
    public void getAllResponsavelsByCidadeIsInShouldWork() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList where cidade in DEFAULT_CIDADE or UPDATED_CIDADE
        defaultResponsavelShouldBeFound("cidade.in=" + DEFAULT_CIDADE + "," + UPDATED_CIDADE);

        // Get all the responsavelList where cidade equals to UPDATED_CIDADE
        defaultResponsavelShouldNotBeFound("cidade.in=" + UPDATED_CIDADE);
    }

    @Test
    @Transactional
    public void getAllResponsavelsByCidadeIsNullOrNotNull() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList where cidade is not null
        defaultResponsavelShouldBeFound("cidade.specified=true");

        // Get all the responsavelList where cidade is null
        defaultResponsavelShouldNotBeFound("cidade.specified=false");
    }

    @Test
    @Transactional
    public void getAllResponsavelsByGeneroIsEqualToSomething() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList where genero equals to DEFAULT_GENERO
        defaultResponsavelShouldBeFound("genero.equals=" + DEFAULT_GENERO);

        // Get all the responsavelList where genero equals to UPDATED_GENERO
        defaultResponsavelShouldNotBeFound("genero.equals=" + UPDATED_GENERO);
    }

    @Test
    @Transactional
    public void getAllResponsavelsByGeneroIsInShouldWork() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList where genero in DEFAULT_GENERO or UPDATED_GENERO
        defaultResponsavelShouldBeFound("genero.in=" + DEFAULT_GENERO + "," + UPDATED_GENERO);

        // Get all the responsavelList where genero equals to UPDATED_GENERO
        defaultResponsavelShouldNotBeFound("genero.in=" + UPDATED_GENERO);
    }

    @Test
    @Transactional
    public void getAllResponsavelsByGeneroIsNullOrNotNull() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList where genero is not null
        defaultResponsavelShouldBeFound("genero.specified=true");

        // Get all the responsavelList where genero is null
        defaultResponsavelShouldNotBeFound("genero.specified=false");
    }

    @Test
    @Transactional
    public void getAllResponsavelsByCepIsEqualToSomething() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList where cep equals to DEFAULT_CEP
        defaultResponsavelShouldBeFound("cep.equals=" + DEFAULT_CEP);

        // Get all the responsavelList where cep equals to UPDATED_CEP
        defaultResponsavelShouldNotBeFound("cep.equals=" + UPDATED_CEP);
    }

    @Test
    @Transactional
    public void getAllResponsavelsByCepIsInShouldWork() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList where cep in DEFAULT_CEP or UPDATED_CEP
        defaultResponsavelShouldBeFound("cep.in=" + DEFAULT_CEP + "," + UPDATED_CEP);

        // Get all the responsavelList where cep equals to UPDATED_CEP
        defaultResponsavelShouldNotBeFound("cep.in=" + UPDATED_CEP);
    }

    @Test
    @Transactional
    public void getAllResponsavelsByCepIsNullOrNotNull() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList where cep is not null
        defaultResponsavelShouldBeFound("cep.specified=true");

        // Get all the responsavelList where cep is null
        defaultResponsavelShouldNotBeFound("cep.specified=false");
    }

    @Test
    @Transactional
    public void getAllResponsavelsByPhotoIsEqualToSomething() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList where photo equals to DEFAULT_PHOTO
        defaultResponsavelShouldBeFound("photo.equals=" + DEFAULT_PHOTO);

        // Get all the responsavelList where photo equals to UPDATED_PHOTO
        defaultResponsavelShouldNotBeFound("photo.equals=" + UPDATED_PHOTO);
    }

    @Test
    @Transactional
    public void getAllResponsavelsByPhotoIsInShouldWork() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList where photo in DEFAULT_PHOTO or UPDATED_PHOTO
        defaultResponsavelShouldBeFound("photo.in=" + DEFAULT_PHOTO + "," + UPDATED_PHOTO);

        // Get all the responsavelList where photo equals to UPDATED_PHOTO
        defaultResponsavelShouldNotBeFound("photo.in=" + UPDATED_PHOTO);
    }

    @Test
    @Transactional
    public void getAllResponsavelsByPhotoIsNullOrNotNull() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList where photo is not null
        defaultResponsavelShouldBeFound("photo.specified=true");

        // Get all the responsavelList where photo is null
        defaultResponsavelShouldNotBeFound("photo.specified=false");
    }

    @Test
    @Transactional
    public void getAllResponsavelsByObsIsEqualToSomething() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList where obs equals to DEFAULT_OBS
        defaultResponsavelShouldBeFound("obs.equals=" + DEFAULT_OBS);

        // Get all the responsavelList where obs equals to UPDATED_OBS
        defaultResponsavelShouldNotBeFound("obs.equals=" + UPDATED_OBS);
    }

    @Test
    @Transactional
    public void getAllResponsavelsByObsIsInShouldWork() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList where obs in DEFAULT_OBS or UPDATED_OBS
        defaultResponsavelShouldBeFound("obs.in=" + DEFAULT_OBS + "," + UPDATED_OBS);

        // Get all the responsavelList where obs equals to UPDATED_OBS
        defaultResponsavelShouldNotBeFound("obs.in=" + UPDATED_OBS);
    }

    @Test
    @Transactional
    public void getAllResponsavelsByObsIsNullOrNotNull() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList where obs is not null
        defaultResponsavelShouldBeFound("obs.specified=true");

        // Get all the responsavelList where obs is null
        defaultResponsavelShouldNotBeFound("obs.specified=false");
    }

    @Test
    @Transactional
    public void getAllResponsavelsByTelefonesIsEqualToSomething() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList where telefones equals to DEFAULT_TELEFONES
        defaultResponsavelShouldBeFound("telefones.equals=" + DEFAULT_TELEFONES);

        // Get all the responsavelList where telefones equals to UPDATED_TELEFONES
        defaultResponsavelShouldNotBeFound("telefones.equals=" + UPDATED_TELEFONES);
    }

    @Test
    @Transactional
    public void getAllResponsavelsByTelefonesIsInShouldWork() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList where telefones in DEFAULT_TELEFONES or UPDATED_TELEFONES
        defaultResponsavelShouldBeFound("telefones.in=" + DEFAULT_TELEFONES + "," + UPDATED_TELEFONES);

        // Get all the responsavelList where telefones equals to UPDATED_TELEFONES
        defaultResponsavelShouldNotBeFound("telefones.in=" + UPDATED_TELEFONES);
    }

    @Test
    @Transactional
    public void getAllResponsavelsByTelefonesIsNullOrNotNull() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList where telefones is not null
        defaultResponsavelShouldBeFound("telefones.specified=true");

        // Get all the responsavelList where telefones is null
        defaultResponsavelShouldNotBeFound("telefones.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultResponsavelShouldBeFound(String filter) throws Exception {
        restResponsavelMockMvc.perform(get("/api/responsavels?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(responsavel.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].sobrenome").value(hasItem(DEFAULT_SOBRENOME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].dataNascimento").value(hasItem(DEFAULT_DATA_NASCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].vinculo").value(hasItem(DEFAULT_VINCULO.toString())))
            .andExpect(jsonPath("$.[*].cpf").value(hasItem(DEFAULT_CPF)))
            .andExpect(jsonPath("$.[*].rg").value(hasItem(DEFAULT_RG)))
            .andExpect(jsonPath("$.[*].endereco").value(hasItem(DEFAULT_ENDERECO)))
            .andExpect(jsonPath("$.[*].cidade").value(hasItem(DEFAULT_CIDADE)))
            .andExpect(jsonPath("$.[*].genero").value(hasItem(DEFAULT_GENERO.toString())))
            .andExpect(jsonPath("$.[*].cep").value(hasItem(DEFAULT_CEP)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(DEFAULT_PHOTO)))
            .andExpect(jsonPath("$.[*].obs").value(hasItem(DEFAULT_OBS)))
            .andExpect(jsonPath("$.[*].telefones").value(hasItem(DEFAULT_TELEFONES)));

        // Check, that the count call also returns 1
        restResponsavelMockMvc.perform(get("/api/responsavels/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultResponsavelShouldNotBeFound(String filter) throws Exception {
        restResponsavelMockMvc.perform(get("/api/responsavels?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restResponsavelMockMvc.perform(get("/api/responsavels/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingResponsavel() throws Exception {
        // Get the responsavel
        restResponsavelMockMvc.perform(get("/api/responsavels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResponsavel() throws Exception {
        // Initialize the database
        responsavelService.save(responsavel);

        int databaseSizeBeforeUpdate = responsavelRepository.findAll().size();

        // Update the responsavel
        Responsavel updatedResponsavel = responsavelRepository.findById(responsavel.getId()).get();
        // Disconnect from session so that the updates on updatedResponsavel are not directly saved in db
        em.detach(updatedResponsavel);
        updatedResponsavel
            .nome(UPDATED_NOME)
            .sobrenome(UPDATED_SOBRENOME)
            .email(UPDATED_EMAIL)
            .enabled(UPDATED_ENABLED)
            .dataNascimento(UPDATED_DATA_NASCIMENTO)
            .vinculo(UPDATED_VINCULO)
            .cpf(UPDATED_CPF)
            .rg(UPDATED_RG)
            .endereco(UPDATED_ENDERECO)
            .cidade(UPDATED_CIDADE)
            .genero(UPDATED_GENERO)
            .cep(UPDATED_CEP)
            .photo(UPDATED_PHOTO)
            .obs(UPDATED_OBS)
            .telefones(UPDATED_TELEFONES);

        restResponsavelMockMvc.perform(put("/api/responsavels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedResponsavel)))
            .andExpect(status().isOk());

        // Validate the Responsavel in the database
        List<Responsavel> responsavelList = responsavelRepository.findAll();
        assertThat(responsavelList).hasSize(databaseSizeBeforeUpdate);
        Responsavel testResponsavel = responsavelList.get(responsavelList.size() - 1);
        assertThat(testResponsavel.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testResponsavel.getSobrenome()).isEqualTo(UPDATED_SOBRENOME);
        assertThat(testResponsavel.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testResponsavel.isEnabled()).isEqualTo(UPDATED_ENABLED);
        assertThat(testResponsavel.getDataNascimento()).isEqualTo(UPDATED_DATA_NASCIMENTO);
        assertThat(testResponsavel.getVinculo()).isEqualTo(UPDATED_VINCULO);
        assertThat(testResponsavel.getCpf()).isEqualTo(UPDATED_CPF);
        assertThat(testResponsavel.getRg()).isEqualTo(UPDATED_RG);
        assertThat(testResponsavel.getEndereco()).isEqualTo(UPDATED_ENDERECO);
        assertThat(testResponsavel.getCidade()).isEqualTo(UPDATED_CIDADE);
        assertThat(testResponsavel.getGenero()).isEqualTo(UPDATED_GENERO);
        assertThat(testResponsavel.getCep()).isEqualTo(UPDATED_CEP);
        assertThat(testResponsavel.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testResponsavel.getObs()).isEqualTo(UPDATED_OBS);
        assertThat(testResponsavel.getTelefones()).isEqualTo(UPDATED_TELEFONES);
    }

    @Test
    @Transactional
    public void updateNonExistingResponsavel() throws Exception {
        int databaseSizeBeforeUpdate = responsavelRepository.findAll().size();

        // Create the Responsavel

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResponsavelMockMvc.perform(put("/api/responsavels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(responsavel)))
            .andExpect(status().isBadRequest());

        // Validate the Responsavel in the database
        List<Responsavel> responsavelList = responsavelRepository.findAll();
        assertThat(responsavelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteResponsavel() throws Exception {
        // Initialize the database
        responsavelService.save(responsavel);

        int databaseSizeBeforeDelete = responsavelRepository.findAll().size();

        // Delete the responsavel
        restResponsavelMockMvc.perform(delete("/api/responsavels/{id}", responsavel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Responsavel> responsavelList = responsavelRepository.findAll();
        assertThat(responsavelList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Responsavel.class);
        Responsavel responsavel1 = new Responsavel();
        responsavel1.setId(1L);
        Responsavel responsavel2 = new Responsavel();
        responsavel2.setId(responsavel1.getId());
        assertThat(responsavel1).isEqualTo(responsavel2);
        responsavel2.setId(2L);
        assertThat(responsavel1).isNotEqualTo(responsavel2);
        responsavel1.setId(null);
        assertThat(responsavel1).isNotEqualTo(responsavel2);
    }
}
