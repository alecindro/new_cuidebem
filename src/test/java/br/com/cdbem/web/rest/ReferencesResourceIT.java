package br.com.cdbem.web.rest;

import br.com.cdbem.CdbemApp;
import br.com.cdbem.domain.References;
import br.com.cdbem.repository.ReferencesRepository;
import br.com.cdbem.service.ReferencesService;
import br.com.cdbem.web.rest.errors.ExceptionTranslator;
import br.com.cdbem.service.dto.ReferencesCriteria;
import br.com.cdbem.service.ReferencesQueryService;

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
import java.util.List;

import static br.com.cdbem.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ReferencesResource} REST controller.
 */
@SpringBootTest(classes = CdbemApp.class)
public class ReferencesResourceIT {

    private static final String DEFAULT_TIPO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private ReferencesRepository referencesRepository;

    @Autowired
    private ReferencesService referencesService;

    @Autowired
    private ReferencesQueryService referencesQueryService;

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

    private MockMvc restReferencesMockMvc;

    private References references;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ReferencesResource referencesResource = new ReferencesResource(referencesService, referencesQueryService);
        this.restReferencesMockMvc = MockMvcBuilders.standaloneSetup(referencesResource)
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
    public static References createEntity(EntityManager em) {
        References references = new References()
            .tipo(DEFAULT_TIPO)
            .value(DEFAULT_VALUE);
        return references;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static References createUpdatedEntity(EntityManager em) {
        References references = new References()
            .tipo(UPDATED_TIPO)
            .value(UPDATED_VALUE);
        return references;
    }

    @BeforeEach
    public void initTest() {
        references = createEntity(em);
    }

    @Test
    @Transactional
    public void createReferences() throws Exception {
        int databaseSizeBeforeCreate = referencesRepository.findAll().size();

        // Create the References
        restReferencesMockMvc.perform(post("/api/references")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(references)))
            .andExpect(status().isCreated());

        // Validate the References in the database
        List<References> referencesList = referencesRepository.findAll();
        assertThat(referencesList).hasSize(databaseSizeBeforeCreate + 1);
        References testReferences = referencesList.get(referencesList.size() - 1);
        assertThat(testReferences.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testReferences.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createReferencesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = referencesRepository.findAll().size();

        // Create the References with an existing ID
        references.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReferencesMockMvc.perform(post("/api/references")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(references)))
            .andExpect(status().isBadRequest());

        // Validate the References in the database
        List<References> referencesList = referencesRepository.findAll();
        assertThat(referencesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllReferences() throws Exception {
        // Initialize the database
        referencesRepository.saveAndFlush(references);

        // Get all the referencesList
        restReferencesMockMvc.perform(get("/api/references?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(references.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }
    
    @Test
    @Transactional
    public void getReferences() throws Exception {
        // Initialize the database
        referencesRepository.saveAndFlush(references);

        // Get the references
        restReferencesMockMvc.perform(get("/api/references/{id}", references.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(references.getId().intValue()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getAllReferencesByTipoIsEqualToSomething() throws Exception {
        // Initialize the database
        referencesRepository.saveAndFlush(references);

        // Get all the referencesList where tipo equals to DEFAULT_TIPO
        defaultReferencesShouldBeFound("tipo.equals=" + DEFAULT_TIPO);

        // Get all the referencesList where tipo equals to UPDATED_TIPO
        defaultReferencesShouldNotBeFound("tipo.equals=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void getAllReferencesByTipoIsInShouldWork() throws Exception {
        // Initialize the database
        referencesRepository.saveAndFlush(references);

        // Get all the referencesList where tipo in DEFAULT_TIPO or UPDATED_TIPO
        defaultReferencesShouldBeFound("tipo.in=" + DEFAULT_TIPO + "," + UPDATED_TIPO);

        // Get all the referencesList where tipo equals to UPDATED_TIPO
        defaultReferencesShouldNotBeFound("tipo.in=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void getAllReferencesByTipoIsNullOrNotNull() throws Exception {
        // Initialize the database
        referencesRepository.saveAndFlush(references);

        // Get all the referencesList where tipo is not null
        defaultReferencesShouldBeFound("tipo.specified=true");

        // Get all the referencesList where tipo is null
        defaultReferencesShouldNotBeFound("tipo.specified=false");
    }

    @Test
    @Transactional
    public void getAllReferencesByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        referencesRepository.saveAndFlush(references);

        // Get all the referencesList where value equals to DEFAULT_VALUE
        defaultReferencesShouldBeFound("value.equals=" + DEFAULT_VALUE);

        // Get all the referencesList where value equals to UPDATED_VALUE
        defaultReferencesShouldNotBeFound("value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllReferencesByValueIsInShouldWork() throws Exception {
        // Initialize the database
        referencesRepository.saveAndFlush(references);

        // Get all the referencesList where value in DEFAULT_VALUE or UPDATED_VALUE
        defaultReferencesShouldBeFound("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE);

        // Get all the referencesList where value equals to UPDATED_VALUE
        defaultReferencesShouldNotBeFound("value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllReferencesByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        referencesRepository.saveAndFlush(references);

        // Get all the referencesList where value is not null
        defaultReferencesShouldBeFound("value.specified=true");

        // Get all the referencesList where value is null
        defaultReferencesShouldNotBeFound("value.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultReferencesShouldBeFound(String filter) throws Exception {
        restReferencesMockMvc.perform(get("/api/references?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(references.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));

        // Check, that the count call also returns 1
        restReferencesMockMvc.perform(get("/api/references/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultReferencesShouldNotBeFound(String filter) throws Exception {
        restReferencesMockMvc.perform(get("/api/references?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restReferencesMockMvc.perform(get("/api/references/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingReferences() throws Exception {
        // Get the references
        restReferencesMockMvc.perform(get("/api/references/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReferences() throws Exception {
        // Initialize the database
        referencesService.save(references);

        int databaseSizeBeforeUpdate = referencesRepository.findAll().size();

        // Update the references
        References updatedReferences = referencesRepository.findById(references.getId()).get();
        // Disconnect from session so that the updates on updatedReferences are not directly saved in db
        em.detach(updatedReferences);
        updatedReferences
            .tipo(UPDATED_TIPO)
            .value(UPDATED_VALUE);

        restReferencesMockMvc.perform(put("/api/references")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedReferences)))
            .andExpect(status().isOk());

        // Validate the References in the database
        List<References> referencesList = referencesRepository.findAll();
        assertThat(referencesList).hasSize(databaseSizeBeforeUpdate);
        References testReferences = referencesList.get(referencesList.size() - 1);
        assertThat(testReferences.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testReferences.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingReferences() throws Exception {
        int databaseSizeBeforeUpdate = referencesRepository.findAll().size();

        // Create the References

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReferencesMockMvc.perform(put("/api/references")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(references)))
            .andExpect(status().isBadRequest());

        // Validate the References in the database
        List<References> referencesList = referencesRepository.findAll();
        assertThat(referencesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteReferences() throws Exception {
        // Initialize the database
        referencesService.save(references);

        int databaseSizeBeforeDelete = referencesRepository.findAll().size();

        // Delete the references
        restReferencesMockMvc.perform(delete("/api/references/{id}", references.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<References> referencesList = referencesRepository.findAll();
        assertThat(referencesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(References.class);
        References references1 = new References();
        references1.setId(1L);
        References references2 = new References();
        references2.setId(references1.getId());
        assertThat(references1).isEqualTo(references2);
        references2.setId(2L);
        assertThat(references1).isNotEqualTo(references2);
        references1.setId(null);
        assertThat(references1).isNotEqualTo(references2);
    }
}
