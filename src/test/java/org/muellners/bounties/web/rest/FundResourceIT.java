package org.muellners.bounties.web.rest;

import org.muellners.bounties.BountiesApp;
import org.muellners.bounties.config.TestSecurityConfiguration;
import org.muellners.bounties.domain.Fund;
import org.muellners.bounties.repository.FundRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link FundResource} REST controller.
 */
@SpringBootTest(classes = { BountiesApp.class, TestSecurityConfiguration.class })
@ExtendWith({ MockitoExtension.class })
@AutoConfigureMockMvc
@WithMockUser
public class FundResourceIT {

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    private static final String DEFAULT_MODE = "AAAAAAAAAA";
    private static final String UPDATED_MODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PAYMENT_AUTH = false;
    private static final Boolean UPDATED_PAYMENT_AUTH = true;

    @Autowired
    private FundRepository fundRepository;

    @Autowired
    private MockMvc restFundMockMvc;

    private Fund fund;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fund createEntity() {
        Fund fund = new Fund()
            .amount(DEFAULT_AMOUNT)
            .mode(DEFAULT_MODE)
            .paymentAuth(DEFAULT_PAYMENT_AUTH);
        return fund;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fund createUpdatedEntity() {
        Fund fund = new Fund()
            .amount(UPDATED_AMOUNT)
            .mode(UPDATED_MODE)
            .paymentAuth(UPDATED_PAYMENT_AUTH);
        return fund;
    }

    @BeforeEach
    public void initTest() {
        fund = createEntity();
    }

    @Test
    @Transactional
    public void createFund() throws Exception {
        int databaseSizeBeforeCreate = fundRepository.findAll().size();
        // Create the Fund
        restFundMockMvc.perform(post("/api/funds").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fund)))
            .andExpect(status().isCreated());

        // Validate the Fund in the database
        List<Fund> fundList = fundRepository.findAll();
        assertThat(fundList).hasSize(databaseSizeBeforeCreate + 1);
        Fund testFund = fundList.get(fundList.size() - 1);
        assertThat(testFund.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testFund.getMode()).isEqualTo(DEFAULT_MODE);
        assertThat(testFund.isPaymentAuth()).isEqualTo(DEFAULT_PAYMENT_AUTH);
    }

    @Test
    @Transactional
    public void createFundWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fundRepository.findAll().size();

        // Create the Fund with an existing ID
        fund.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFundMockMvc.perform(post("/api/funds").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fund)))
            .andExpect(status().isBadRequest());

        // Validate the Fund in the database
        List<Fund> fundList = fundRepository.findAll();
        assertThat(fundList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFunds() throws Exception {
        // Initialize the database
        fundRepository.saveAndFlush(fund);

        // Get all the fundList
        restFundMockMvc.perform(get("/api/funds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fund.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].mode").value(hasItem(DEFAULT_MODE)))
            .andExpect(jsonPath("$.[*].paymentAuth").value(hasItem(DEFAULT_PAYMENT_AUTH.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getFund() throws Exception {
        // Initialize the database
        fundRepository.saveAndFlush(fund);

        // Get the fund
        restFundMockMvc.perform(get("/api/funds/{id}", fund.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fund.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.mode").value(DEFAULT_MODE))
            .andExpect(jsonPath("$.paymentAuth").value(DEFAULT_PAYMENT_AUTH.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingFund() throws Exception {
        // Get the fund
        restFundMockMvc.perform(get("/api/funds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFund() throws Exception {
        // Initialize the database
        fundRepository.saveAndFlush(fund);

        int databaseSizeBeforeUpdate = fundRepository.findAll().size();

        // Update the fund
        Fund updatedFund = fundRepository.findById(fund.getId()).get();
        
        updatedFund
            .amount(UPDATED_AMOUNT)
            .mode(UPDATED_MODE)
            .paymentAuth(UPDATED_PAYMENT_AUTH);

        restFundMockMvc.perform(put("/api/funds").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFund)))
            .andExpect(status().isOk());

        // Validate the Fund in the database
        List<Fund> fundList = fundRepository.findAll();
        assertThat(fundList).hasSize(databaseSizeBeforeUpdate);
        Fund testFund = fundList.get(fundList.size() - 1);
        assertThat(testFund.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testFund.getMode()).isEqualTo(UPDATED_MODE);
        assertThat(testFund.isPaymentAuth()).isEqualTo(UPDATED_PAYMENT_AUTH);
    }

    @Test
    @Transactional
    public void updateNonExistingFund() throws Exception {
        int databaseSizeBeforeUpdate = fundRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFundMockMvc.perform(put("/api/funds").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fund)))
            .andExpect(status().isBadRequest());

        // Validate the Fund in the database
        List<Fund> fundList = fundRepository.findAll();
        assertThat(fundList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFund() throws Exception {
        // Initialize the database
        fundRepository.saveAndFlush(fund);

        int databaseSizeBeforeDelete = fundRepository.findAll().size();

        // Delete the fund
        restFundMockMvc.perform(delete("/api/funds/{id}", fund.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Fund> fundList = fundRepository.findAll();
        assertThat(fundList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @org.junit.jupiter.api.Disabled
    @Test
    @Transactional
    public void searchFund() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        fundRepository.saveAndFlush(fund);

        // Search the fund
        restFundMockMvc.perform(get("/api/_search/funds?query=id:" + fund.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fund.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].mode").value(hasItem(DEFAULT_MODE)))
            .andExpect(jsonPath("$.[*].paymentAuth").value(hasItem(DEFAULT_PAYMENT_AUTH.booleanValue())));
    }
}
