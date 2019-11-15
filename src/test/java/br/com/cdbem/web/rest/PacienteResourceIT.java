package br.com.cdbem.web.rest;

import br.com.cdbem.CdbemApp;
import br.com.cdbem.domain.Paciente;
import br.com.cdbem.domain.Responsavel;
import br.com.cdbem.repository.PacienteRepository;
import br.com.cdbem.service.PacienteService;
import br.com.cdbem.web.rest.errors.ExceptionTranslator;
import br.com.cdbem.service.dto.PacienteCriteria;
import br.com.cdbem.service.PacienteQueryService;

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

import br.com.cdbem.domain.enumeration.Genero;
import br.com.cdbem.domain.enumeration.TipoEstadia;
/**
 * Integration tests for the {@link PacienteResource} REST controller.
 */
@SpringBootTest(classes = CdbemApp.class)
public class PacienteResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_APELIDO = "AAAAAAAAAA";
    private static final String UPDATED_APELIDO = "BBBBBBBBBB";

    private static final Genero DEFAULT_GENERO = Genero.MASCULINO;
    private static final Genero UPDATED_GENERO = Genero.FEMININO;

    private static final Boolean DEFAULT_ENABLED = false;
    private static final Boolean UPDATED_ENABLED = true;

    private static final LocalDate DEFAULT_DATA_NASCIMENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_NASCIMENTO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATA_NASCIMENTO = LocalDate.ofEpochDay(-1L);

    private static final TipoEstadia DEFAULT_TIPO_ESTADIA = TipoEstadia.PERIODICO;
    private static final TipoEstadia UPDATED_TIPO_ESTADIA = TipoEstadia.RESIDENTE;

    private static final String DEFAULT_PHOTO = "AAAAAAAAAA";
    private static final String UPDATED_PHOTO = "BBBBBBBBBB";

    private static final String DEFAULT_PATOLOGIAS = "AAAAAAAAAA";
    private static final String UPDATED_PATOLOGIAS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA_CADASTRO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_CADASTRO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATA_CADASTRO = LocalDate.ofEpochDay(-1L);

    private static final Boolean DEFAULT_CHECKIN = false;
    private static final Boolean UPDATED_CHECKIN = true;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private PacienteQueryService pacienteQueryService;

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

    private MockMvc restPacienteMockMvc;

    private Paciente paciente;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PacienteResource pacienteResource = new PacienteResource(pacienteService, pacienteQueryService);
        this.restPacienteMockMvc = MockMvcBuilders.standaloneSetup(pacienteResource)
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
    public static Paciente createEntity(EntityManager em) {
        Paciente paciente = new Paciente()
            .nome(DEFAULT_NOME)
            .apelido(DEFAULT_APELIDO)
            .genero(DEFAULT_GENERO)
            .enabled(DEFAULT_ENABLED)
            .dataNascimento(DEFAULT_DATA_NASCIMENTO)
            .tipoEstadia(DEFAULT_TIPO_ESTADIA)
            .photo(DEFAULT_PHOTO)
            .patologias(DEFAULT_PATOLOGIAS)
            .dataCadastro(DEFAULT_DATA_CADASTRO)
            .checkin(DEFAULT_CHECKIN);
        return paciente;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Paciente createUpdatedEntity(EntityManager em) {
        Paciente paciente = new Paciente()
            .nome(UPDATED_NOME)
            .apelido(UPDATED_APELIDO)
            .genero(UPDATED_GENERO)
            .enabled(UPDATED_ENABLED)
            .dataNascimento(UPDATED_DATA_NASCIMENTO)
            .tipoEstadia(UPDATED_TIPO_ESTADIA)
            .photo(UPDATED_PHOTO)
            .patologias(UPDATED_PATOLOGIAS)
            .dataCadastro(UPDATED_DATA_CADASTRO)
            .checkin(UPDATED_CHECKIN);
        return paciente;
    }

    @BeforeEach
    public void initTest() {
        paciente = createEntity(em);
    }

    @Test
    @Transactional
    public void createPaciente() throws Exception {
        int databaseSizeBeforeCreate = pacienteRepository.findAll().size();

        // Create the Paciente
        restPacienteMockMvc.perform(post("/api/pacientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paciente)))
            .andExpect(status().isCreated());

        // Validate the Paciente in the database
        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeCreate + 1);
        Paciente testPaciente = pacienteList.get(pacienteList.size() - 1);
        assertThat(testPaciente.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testPaciente.getApelido()).isEqualTo(DEFAULT_APELIDO);
        assertThat(testPaciente.getGenero()).isEqualTo(DEFAULT_GENERO);
        assertThat(testPaciente.isEnabled()).isEqualTo(DEFAULT_ENABLED);
        assertThat(testPaciente.getDataNascimento()).isEqualTo(DEFAULT_DATA_NASCIMENTO);
        assertThat(testPaciente.getTipoEstadia()).isEqualTo(DEFAULT_TIPO_ESTADIA);
        assertThat(testPaciente.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testPaciente.getPatologias()).isEqualTo(DEFAULT_PATOLOGIAS);
        assertThat(testPaciente.getDataCadastro()).isEqualTo(DEFAULT_DATA_CADASTRO);
        assertThat(testPaciente.isCheckin()).isEqualTo(DEFAULT_CHECKIN);
    }

    @Test
    @Transactional
    public void createPacienteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pacienteRepository.findAll().size();

        // Create the Paciente with an existing ID
        paciente.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPacienteMockMvc.perform(post("/api/pacientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paciente)))
            .andExpect(status().isBadRequest());

        // Validate the Paciente in the database
        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkGeneroIsRequired() throws Exception {
        int databaseSizeBeforeTest = pacienteRepository.findAll().size();
        // set the field null
        paciente.setGenero(null);

        // Create the Paciente, which fails.

        restPacienteMockMvc.perform(post("/api/pacientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paciente)))
            .andExpect(status().isBadRequest());

        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEnabledIsRequired() throws Exception {
        int databaseSizeBeforeTest = pacienteRepository.findAll().size();
        // set the field null
        paciente.setEnabled(null);

        // Create the Paciente, which fails.

        restPacienteMockMvc.perform(post("/api/pacientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paciente)))
            .andExpect(status().isBadRequest());

        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCheckinIsRequired() throws Exception {
        int databaseSizeBeforeTest = pacienteRepository.findAll().size();
        // set the field null
        paciente.setCheckin(null);

        // Create the Paciente, which fails.

        restPacienteMockMvc.perform(post("/api/pacientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paciente)))
            .andExpect(status().isBadRequest());

        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPacientes() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList
        restPacienteMockMvc.perform(get("/api/pacientes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paciente.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].apelido").value(hasItem(DEFAULT_APELIDO.toString())))
            .andExpect(jsonPath("$.[*].genero").value(hasItem(DEFAULT_GENERO.toString())))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].dataNascimento").value(hasItem(DEFAULT_DATA_NASCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].tipoEstadia").value(hasItem(DEFAULT_TIPO_ESTADIA.toString())))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(DEFAULT_PHOTO.toString())))
            .andExpect(jsonPath("$.[*].patologias").value(hasItem(DEFAULT_PATOLOGIAS.toString())))
            .andExpect(jsonPath("$.[*].dataCadastro").value(hasItem(DEFAULT_DATA_CADASTRO.toString())))
            .andExpect(jsonPath("$.[*].checkin").value(hasItem(DEFAULT_CHECKIN.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getPaciente() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get the paciente
        restPacienteMockMvc.perform(get("/api/pacientes/{id}", paciente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(paciente.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.apelido").value(DEFAULT_APELIDO.toString()))
            .andExpect(jsonPath("$.genero").value(DEFAULT_GENERO.toString()))
            .andExpect(jsonPath("$.enabled").value(DEFAULT_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.dataNascimento").value(DEFAULT_DATA_NASCIMENTO.toString()))
            .andExpect(jsonPath("$.tipoEstadia").value(DEFAULT_TIPO_ESTADIA.toString()))
            .andExpect(jsonPath("$.photo").value(DEFAULT_PHOTO.toString()))
            .andExpect(jsonPath("$.patologias").value(DEFAULT_PATOLOGIAS.toString()))
            .andExpect(jsonPath("$.dataCadastro").value(DEFAULT_DATA_CADASTRO.toString()))
            .andExpect(jsonPath("$.checkin").value(DEFAULT_CHECKIN.booleanValue()));
    }

    @Test
    @Transactional
    public void getAllPacientesByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where nome equals to DEFAULT_NOME
        defaultPacienteShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the pacienteList where nome equals to UPDATED_NOME
        defaultPacienteShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllPacientesByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultPacienteShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the pacienteList where nome equals to UPDATED_NOME
        defaultPacienteShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllPacientesByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where nome is not null
        defaultPacienteShouldBeFound("nome.specified=true");

        // Get all the pacienteList where nome is null
        defaultPacienteShouldNotBeFound("nome.specified=false");
    }

    @Test
    @Transactional
    public void getAllPacientesByApelidoIsEqualToSomething() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where apelido equals to DEFAULT_APELIDO
        defaultPacienteShouldBeFound("apelido.equals=" + DEFAULT_APELIDO);

        // Get all the pacienteList where apelido equals to UPDATED_APELIDO
        defaultPacienteShouldNotBeFound("apelido.equals=" + UPDATED_APELIDO);
    }

    @Test
    @Transactional
    public void getAllPacientesByApelidoIsInShouldWork() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where apelido in DEFAULT_APELIDO or UPDATED_APELIDO
        defaultPacienteShouldBeFound("apelido.in=" + DEFAULT_APELIDO + "," + UPDATED_APELIDO);

        // Get all the pacienteList where apelido equals to UPDATED_APELIDO
        defaultPacienteShouldNotBeFound("apelido.in=" + UPDATED_APELIDO);
    }

    @Test
    @Transactional
    public void getAllPacientesByApelidoIsNullOrNotNull() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where apelido is not null
        defaultPacienteShouldBeFound("apelido.specified=true");

        // Get all the pacienteList where apelido is null
        defaultPacienteShouldNotBeFound("apelido.specified=false");
    }

    @Test
    @Transactional
    public void getAllPacientesByGeneroIsEqualToSomething() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where genero equals to DEFAULT_GENERO
        defaultPacienteShouldBeFound("genero.equals=" + DEFAULT_GENERO);

        // Get all the pacienteList where genero equals to UPDATED_GENERO
        defaultPacienteShouldNotBeFound("genero.equals=" + UPDATED_GENERO);
    }

    @Test
    @Transactional
    public void getAllPacientesByGeneroIsInShouldWork() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where genero in DEFAULT_GENERO or UPDATED_GENERO
        defaultPacienteShouldBeFound("genero.in=" + DEFAULT_GENERO + "," + UPDATED_GENERO);

        // Get all the pacienteList where genero equals to UPDATED_GENERO
        defaultPacienteShouldNotBeFound("genero.in=" + UPDATED_GENERO);
    }

    @Test
    @Transactional
    public void getAllPacientesByGeneroIsNullOrNotNull() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where genero is not null
        defaultPacienteShouldBeFound("genero.specified=true");

        // Get all the pacienteList where genero is null
        defaultPacienteShouldNotBeFound("genero.specified=false");
    }

    @Test
    @Transactional
    public void getAllPacientesByEnabledIsEqualToSomething() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where enabled equals to DEFAULT_ENABLED
        defaultPacienteShouldBeFound("enabled.equals=" + DEFAULT_ENABLED);

        // Get all the pacienteList where enabled equals to UPDATED_ENABLED
        defaultPacienteShouldNotBeFound("enabled.equals=" + UPDATED_ENABLED);
    }

    @Test
    @Transactional
    public void getAllPacientesByEnabledIsInShouldWork() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where enabled in DEFAULT_ENABLED or UPDATED_ENABLED
        defaultPacienteShouldBeFound("enabled.in=" + DEFAULT_ENABLED + "," + UPDATED_ENABLED);

        // Get all the pacienteList where enabled equals to UPDATED_ENABLED
        defaultPacienteShouldNotBeFound("enabled.in=" + UPDATED_ENABLED);
    }

    @Test
    @Transactional
    public void getAllPacientesByEnabledIsNullOrNotNull() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where enabled is not null
        defaultPacienteShouldBeFound("enabled.specified=true");

        // Get all the pacienteList where enabled is null
        defaultPacienteShouldNotBeFound("enabled.specified=false");
    }

    @Test
    @Transactional
    public void getAllPacientesByDataNascimentoIsEqualToSomething() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where dataNascimento equals to DEFAULT_DATA_NASCIMENTO
        defaultPacienteShouldBeFound("dataNascimento.equals=" + DEFAULT_DATA_NASCIMENTO);

        // Get all the pacienteList where dataNascimento equals to UPDATED_DATA_NASCIMENTO
        defaultPacienteShouldNotBeFound("dataNascimento.equals=" + UPDATED_DATA_NASCIMENTO);
    }

    @Test
    @Transactional
    public void getAllPacientesByDataNascimentoIsInShouldWork() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where dataNascimento in DEFAULT_DATA_NASCIMENTO or UPDATED_DATA_NASCIMENTO
        defaultPacienteShouldBeFound("dataNascimento.in=" + DEFAULT_DATA_NASCIMENTO + "," + UPDATED_DATA_NASCIMENTO);

        // Get all the pacienteList where dataNascimento equals to UPDATED_DATA_NASCIMENTO
        defaultPacienteShouldNotBeFound("dataNascimento.in=" + UPDATED_DATA_NASCIMENTO);
    }

    @Test
    @Transactional
    public void getAllPacientesByDataNascimentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where dataNascimento is not null
        defaultPacienteShouldBeFound("dataNascimento.specified=true");

        // Get all the pacienteList where dataNascimento is null
        defaultPacienteShouldNotBeFound("dataNascimento.specified=false");
    }

    @Test
    @Transactional
    public void getAllPacientesByDataNascimentoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where dataNascimento is greater than or equal to DEFAULT_DATA_NASCIMENTO
        defaultPacienteShouldBeFound("dataNascimento.greaterThanOrEqual=" + DEFAULT_DATA_NASCIMENTO);

        // Get all the pacienteList where dataNascimento is greater than or equal to UPDATED_DATA_NASCIMENTO
        defaultPacienteShouldNotBeFound("dataNascimento.greaterThanOrEqual=" + UPDATED_DATA_NASCIMENTO);
    }

    @Test
    @Transactional
    public void getAllPacientesByDataNascimentoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where dataNascimento is less than or equal to DEFAULT_DATA_NASCIMENTO
        defaultPacienteShouldBeFound("dataNascimento.lessThanOrEqual=" + DEFAULT_DATA_NASCIMENTO);

        // Get all the pacienteList where dataNascimento is less than or equal to SMALLER_DATA_NASCIMENTO
        defaultPacienteShouldNotBeFound("dataNascimento.lessThanOrEqual=" + SMALLER_DATA_NASCIMENTO);
    }

    @Test
    @Transactional
    public void getAllPacientesByDataNascimentoIsLessThanSomething() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where dataNascimento is less than DEFAULT_DATA_NASCIMENTO
        defaultPacienteShouldNotBeFound("dataNascimento.lessThan=" + DEFAULT_DATA_NASCIMENTO);

        // Get all the pacienteList where dataNascimento is less than UPDATED_DATA_NASCIMENTO
        defaultPacienteShouldBeFound("dataNascimento.lessThan=" + UPDATED_DATA_NASCIMENTO);
    }

    @Test
    @Transactional
    public void getAllPacientesByDataNascimentoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where dataNascimento is greater than DEFAULT_DATA_NASCIMENTO
        defaultPacienteShouldNotBeFound("dataNascimento.greaterThan=" + DEFAULT_DATA_NASCIMENTO);

        // Get all the pacienteList where dataNascimento is greater than SMALLER_DATA_NASCIMENTO
        defaultPacienteShouldBeFound("dataNascimento.greaterThan=" + SMALLER_DATA_NASCIMENTO);
    }


    @Test
    @Transactional
    public void getAllPacientesByTipoEstadiaIsEqualToSomething() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where tipoEstadia equals to DEFAULT_TIPO_ESTADIA
        defaultPacienteShouldBeFound("tipoEstadia.equals=" + DEFAULT_TIPO_ESTADIA);

        // Get all the pacienteList where tipoEstadia equals to UPDATED_TIPO_ESTADIA
        defaultPacienteShouldNotBeFound("tipoEstadia.equals=" + UPDATED_TIPO_ESTADIA);
    }

    @Test
    @Transactional
    public void getAllPacientesByTipoEstadiaIsInShouldWork() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where tipoEstadia in DEFAULT_TIPO_ESTADIA or UPDATED_TIPO_ESTADIA
        defaultPacienteShouldBeFound("tipoEstadia.in=" + DEFAULT_TIPO_ESTADIA + "," + UPDATED_TIPO_ESTADIA);

        // Get all the pacienteList where tipoEstadia equals to UPDATED_TIPO_ESTADIA
        defaultPacienteShouldNotBeFound("tipoEstadia.in=" + UPDATED_TIPO_ESTADIA);
    }

    @Test
    @Transactional
    public void getAllPacientesByTipoEstadiaIsNullOrNotNull() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where tipoEstadia is not null
        defaultPacienteShouldBeFound("tipoEstadia.specified=true");

        // Get all the pacienteList where tipoEstadia is null
        defaultPacienteShouldNotBeFound("tipoEstadia.specified=false");
    }

    @Test
    @Transactional
    public void getAllPacientesByPhotoIsEqualToSomething() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where photo equals to DEFAULT_PHOTO
        defaultPacienteShouldBeFound("photo.equals=" + DEFAULT_PHOTO);

        // Get all the pacienteList where photo equals to UPDATED_PHOTO
        defaultPacienteShouldNotBeFound("photo.equals=" + UPDATED_PHOTO);
    }

    @Test
    @Transactional
    public void getAllPacientesByPhotoIsInShouldWork() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where photo in DEFAULT_PHOTO or UPDATED_PHOTO
        defaultPacienteShouldBeFound("photo.in=" + DEFAULT_PHOTO + "," + UPDATED_PHOTO);

        // Get all the pacienteList where photo equals to UPDATED_PHOTO
        defaultPacienteShouldNotBeFound("photo.in=" + UPDATED_PHOTO);
    }

    @Test
    @Transactional
    public void getAllPacientesByPhotoIsNullOrNotNull() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where photo is not null
        defaultPacienteShouldBeFound("photo.specified=true");

        // Get all the pacienteList where photo is null
        defaultPacienteShouldNotBeFound("photo.specified=false");
    }

    @Test
    @Transactional
    public void getAllPacientesByPatologiasIsEqualToSomething() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where patologias equals to DEFAULT_PATOLOGIAS
        defaultPacienteShouldBeFound("patologias.equals=" + DEFAULT_PATOLOGIAS);

        // Get all the pacienteList where patologias equals to UPDATED_PATOLOGIAS
        defaultPacienteShouldNotBeFound("patologias.equals=" + UPDATED_PATOLOGIAS);
    }

    @Test
    @Transactional
    public void getAllPacientesByPatologiasIsInShouldWork() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where patologias in DEFAULT_PATOLOGIAS or UPDATED_PATOLOGIAS
        defaultPacienteShouldBeFound("patologias.in=" + DEFAULT_PATOLOGIAS + "," + UPDATED_PATOLOGIAS);

        // Get all the pacienteList where patologias equals to UPDATED_PATOLOGIAS
        defaultPacienteShouldNotBeFound("patologias.in=" + UPDATED_PATOLOGIAS);
    }

    @Test
    @Transactional
    public void getAllPacientesByPatologiasIsNullOrNotNull() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where patologias is not null
        defaultPacienteShouldBeFound("patologias.specified=true");

        // Get all the pacienteList where patologias is null
        defaultPacienteShouldNotBeFound("patologias.specified=false");
    }

    @Test
    @Transactional
    public void getAllPacientesByDataCadastroIsEqualToSomething() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where dataCadastro equals to DEFAULT_DATA_CADASTRO
        defaultPacienteShouldBeFound("dataCadastro.equals=" + DEFAULT_DATA_CADASTRO);

        // Get all the pacienteList where dataCadastro equals to UPDATED_DATA_CADASTRO
        defaultPacienteShouldNotBeFound("dataCadastro.equals=" + UPDATED_DATA_CADASTRO);
    }

    @Test
    @Transactional
    public void getAllPacientesByDataCadastroIsInShouldWork() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where dataCadastro in DEFAULT_DATA_CADASTRO or UPDATED_DATA_CADASTRO
        defaultPacienteShouldBeFound("dataCadastro.in=" + DEFAULT_DATA_CADASTRO + "," + UPDATED_DATA_CADASTRO);

        // Get all the pacienteList where dataCadastro equals to UPDATED_DATA_CADASTRO
        defaultPacienteShouldNotBeFound("dataCadastro.in=" + UPDATED_DATA_CADASTRO);
    }

    @Test
    @Transactional
    public void getAllPacientesByDataCadastroIsNullOrNotNull() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where dataCadastro is not null
        defaultPacienteShouldBeFound("dataCadastro.specified=true");

        // Get all the pacienteList where dataCadastro is null
        defaultPacienteShouldNotBeFound("dataCadastro.specified=false");
    }

    @Test
    @Transactional
    public void getAllPacientesByDataCadastroIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where dataCadastro is greater than or equal to DEFAULT_DATA_CADASTRO
        defaultPacienteShouldBeFound("dataCadastro.greaterThanOrEqual=" + DEFAULT_DATA_CADASTRO);

        // Get all the pacienteList where dataCadastro is greater than or equal to UPDATED_DATA_CADASTRO
        defaultPacienteShouldNotBeFound("dataCadastro.greaterThanOrEqual=" + UPDATED_DATA_CADASTRO);
    }

    @Test
    @Transactional
    public void getAllPacientesByDataCadastroIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where dataCadastro is less than or equal to DEFAULT_DATA_CADASTRO
        defaultPacienteShouldBeFound("dataCadastro.lessThanOrEqual=" + DEFAULT_DATA_CADASTRO);

        // Get all the pacienteList where dataCadastro is less than or equal to SMALLER_DATA_CADASTRO
        defaultPacienteShouldNotBeFound("dataCadastro.lessThanOrEqual=" + SMALLER_DATA_CADASTRO);
    }

    @Test
    @Transactional
    public void getAllPacientesByDataCadastroIsLessThanSomething() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where dataCadastro is less than DEFAULT_DATA_CADASTRO
        defaultPacienteShouldNotBeFound("dataCadastro.lessThan=" + DEFAULT_DATA_CADASTRO);

        // Get all the pacienteList where dataCadastro is less than UPDATED_DATA_CADASTRO
        defaultPacienteShouldBeFound("dataCadastro.lessThan=" + UPDATED_DATA_CADASTRO);
    }

    @Test
    @Transactional
    public void getAllPacientesByDataCadastroIsGreaterThanSomething() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where dataCadastro is greater than DEFAULT_DATA_CADASTRO
        defaultPacienteShouldNotBeFound("dataCadastro.greaterThan=" + DEFAULT_DATA_CADASTRO);

        // Get all the pacienteList where dataCadastro is greater than SMALLER_DATA_CADASTRO
        defaultPacienteShouldBeFound("dataCadastro.greaterThan=" + SMALLER_DATA_CADASTRO);
    }


    @Test
    @Transactional
    public void getAllPacientesByCheckinIsEqualToSomething() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where checkin equals to DEFAULT_CHECKIN
        defaultPacienteShouldBeFound("checkin.equals=" + DEFAULT_CHECKIN);

        // Get all the pacienteList where checkin equals to UPDATED_CHECKIN
        defaultPacienteShouldNotBeFound("checkin.equals=" + UPDATED_CHECKIN);
    }

    @Test
    @Transactional
    public void getAllPacientesByCheckinIsInShouldWork() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where checkin in DEFAULT_CHECKIN or UPDATED_CHECKIN
        defaultPacienteShouldBeFound("checkin.in=" + DEFAULT_CHECKIN + "," + UPDATED_CHECKIN);

        // Get all the pacienteList where checkin equals to UPDATED_CHECKIN
        defaultPacienteShouldNotBeFound("checkin.in=" + UPDATED_CHECKIN);
    }

    @Test
    @Transactional
    public void getAllPacientesByCheckinIsNullOrNotNull() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where checkin is not null
        defaultPacienteShouldBeFound("checkin.specified=true");

        // Get all the pacienteList where checkin is null
        defaultPacienteShouldNotBeFound("checkin.specified=false");
    }

    @Test
    @Transactional
    public void getAllPacientesByResponsavelIsEqualToSomething() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);
        Responsavel responsavel = ResponsavelResourceIT.createEntity(em);
        em.persist(responsavel);
        em.flush();
        paciente.addResponsavel(responsavel);
        pacienteRepository.saveAndFlush(paciente);
        Long responsavelId = responsavel.getId();

        // Get all the pacienteList where responsavel equals to responsavelId
        defaultPacienteShouldBeFound("responsavelId.equals=" + responsavelId);

        // Get all the pacienteList where responsavel equals to responsavelId + 1
        defaultPacienteShouldNotBeFound("responsavelId.equals=" + (responsavelId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPacienteShouldBeFound(String filter) throws Exception {
        restPacienteMockMvc.perform(get("/api/pacientes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paciente.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].apelido").value(hasItem(DEFAULT_APELIDO)))
            .andExpect(jsonPath("$.[*].genero").value(hasItem(DEFAULT_GENERO.toString())))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].dataNascimento").value(hasItem(DEFAULT_DATA_NASCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].tipoEstadia").value(hasItem(DEFAULT_TIPO_ESTADIA.toString())))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(DEFAULT_PHOTO)))
            .andExpect(jsonPath("$.[*].patologias").value(hasItem(DEFAULT_PATOLOGIAS)))
            .andExpect(jsonPath("$.[*].dataCadastro").value(hasItem(DEFAULT_DATA_CADASTRO.toString())))
            .andExpect(jsonPath("$.[*].checkin").value(hasItem(DEFAULT_CHECKIN.booleanValue())));

        // Check, that the count call also returns 1
        restPacienteMockMvc.perform(get("/api/pacientes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPacienteShouldNotBeFound(String filter) throws Exception {
        restPacienteMockMvc.perform(get("/api/pacientes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPacienteMockMvc.perform(get("/api/pacientes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingPaciente() throws Exception {
        // Get the paciente
        restPacienteMockMvc.perform(get("/api/pacientes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePaciente() throws Exception {
        // Initialize the database
        pacienteService.save(paciente);

        int databaseSizeBeforeUpdate = pacienteRepository.findAll().size();

        // Update the paciente
        Paciente updatedPaciente = pacienteRepository.findById(paciente.getId()).get();
        // Disconnect from session so that the updates on updatedPaciente are not directly saved in db
        em.detach(updatedPaciente);
        updatedPaciente
            .nome(UPDATED_NOME)
            .apelido(UPDATED_APELIDO)
            .genero(UPDATED_GENERO)
            .enabled(UPDATED_ENABLED)
            .dataNascimento(UPDATED_DATA_NASCIMENTO)
            .tipoEstadia(UPDATED_TIPO_ESTADIA)
            .photo(UPDATED_PHOTO)
            .patologias(UPDATED_PATOLOGIAS)
            .dataCadastro(UPDATED_DATA_CADASTRO)
            .checkin(UPDATED_CHECKIN);

        restPacienteMockMvc.perform(put("/api/pacientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPaciente)))
            .andExpect(status().isOk());

        // Validate the Paciente in the database
        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeUpdate);
        Paciente testPaciente = pacienteList.get(pacienteList.size() - 1);
        assertThat(testPaciente.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testPaciente.getApelido()).isEqualTo(UPDATED_APELIDO);
        assertThat(testPaciente.getGenero()).isEqualTo(UPDATED_GENERO);
        assertThat(testPaciente.isEnabled()).isEqualTo(UPDATED_ENABLED);
        assertThat(testPaciente.getDataNascimento()).isEqualTo(UPDATED_DATA_NASCIMENTO);
        assertThat(testPaciente.getTipoEstadia()).isEqualTo(UPDATED_TIPO_ESTADIA);
        assertThat(testPaciente.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testPaciente.getPatologias()).isEqualTo(UPDATED_PATOLOGIAS);
        assertThat(testPaciente.getDataCadastro()).isEqualTo(UPDATED_DATA_CADASTRO);
        assertThat(testPaciente.isCheckin()).isEqualTo(UPDATED_CHECKIN);
    }

    @Test
    @Transactional
    public void updateNonExistingPaciente() throws Exception {
        int databaseSizeBeforeUpdate = pacienteRepository.findAll().size();

        // Create the Paciente

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPacienteMockMvc.perform(put("/api/pacientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paciente)))
            .andExpect(status().isBadRequest());

        // Validate the Paciente in the database
        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePaciente() throws Exception {
        // Initialize the database
        pacienteService.save(paciente);

        int databaseSizeBeforeDelete = pacienteRepository.findAll().size();

        // Delete the paciente
        restPacienteMockMvc.perform(delete("/api/pacientes/{id}", paciente.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Paciente.class);
        Paciente paciente1 = new Paciente();
        paciente1.setId(1L);
        Paciente paciente2 = new Paciente();
        paciente2.setId(paciente1.getId());
        assertThat(paciente1).isEqualTo(paciente2);
        paciente2.setId(2L);
        assertThat(paciente1).isNotEqualTo(paciente2);
        paciente1.setId(null);
        assertThat(paciente1).isNotEqualTo(paciente2);
    }
}
