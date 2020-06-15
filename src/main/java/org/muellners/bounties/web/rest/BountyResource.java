package org.muellners.bounties.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import io.github.jhipster.web.util.HeaderUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import org.muellners.bounties.service.BountyService;
import org.muellners.bounties.service.dto.BountyDTO;
import org.muellners.bounties.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing {@link org.muellners.bounties.domain.Bounty}.
 */
@RestController
@RequestMapping("/api")
public class BountyResource {

  private final Logger log = LoggerFactory.getLogger(BountyResource.class);

  private static final String ENTITY_NAME = "bounty";

  @Value("${jhipster.clientApp.name}") private String applicationName;

  private final BountyService bountyService;

  public BountyResource(BountyService bountyService) {
    this.bountyService = bountyService;
  }

  /**
   * {@code POST  /bounties} : Create a new bounty.
   *
   * @param bounty the bounties to create.
   * @return the {@link ResponseEntity} with status {@code 201 (Created)} and
   *     with body the new bounties, or with status {@code 400 (Bad Request)} if
   *     the bounties has already an ID.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PostMapping("/bounties")
  public ResponseEntity<BountyDTO> createBounties(@RequestBody BountyDTO bounty)
      throws URISyntaxException {
    log.debug("REST request to save Bounty : {}", bounty);
    if (bounty.getId() != null) {
      throw new BadRequestAlertException(
          "A new bounty cannot already have an ID", ENTITY_NAME, "idexists");
    }
    BountyDTO result = bountyService.save(bounty);
    return ResponseEntity.created(new URI("/api/bounties/" + result.getId()))
        .headers(HeaderUtil.createEntityCreationAlert(
            applicationName, true, ENTITY_NAME, result.getId().toString()))
        .body(result);
  }

  /**
   * {@code PUT  /bounties} : Updates an existing bounty.
   *
   * @param bounty the bounty to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with
   *     body the updated bounty,
   * or with status {@code 400 (Bad Request)} if the bounty is not valid,
   * or with status {@code 500 (Internal Server Error)} if the bounty couldn't
   * be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PutMapping("/bounties")
  public ResponseEntity<BountyDTO> updateBounties(@RequestBody BountyDTO bounty)
      throws URISyntaxException {
    log.debug("REST request to update Bounty : {}", bounty);
    if (bounty.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    BountyDTO result = bountyService.save(bounty);
    return ResponseEntity.ok()
        .headers(HeaderUtil.createEntityUpdateAlert(
            applicationName, true, ENTITY_NAME, bounty.getId().toString()))
        .body(result);
  }

  /**
   * {@code GET  /bounties} : get all the bounty.
   *
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the
   *     list of bounty in body.
   */
  @GetMapping("/bounties")
  public List<BountyDTO> getAllBounties() {
    log.debug("REST request to get all Bounty");
    return bountyService.findAll();
  }

  /**
   * {@code GET  /bounties/:id} : get the "id" bounty.
   *
   * @param id the id of the bounty to retrieve.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with
   *     body the bounty, or with status {@code 404 (Not Found)}.
   */
  @GetMapping("/bounties/{id}")
  public ResponseEntity<BountyDTO> getBounty(@PathVariable Long id) {
    log.debug("REST request to get Bounty : {}", id);
    BountyDTO bountyDTO = bountyService.findOne(id);
    if (bountyDTO == null) {
      return ResponseEntity.notFound().build();
    } else {
      return ResponseEntity.ok(bountyDTO);
    }
  }

  /**
   * {@code DELETE  /bounties/:id} : delete the "id" bounty.
   *
   * @param id the id of the bounty to delete.
   * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
   */
  @DeleteMapping("/bounties/{id}")
  public ResponseEntity<Void> deleteBounties(@PathVariable Long id) {
    log.debug("REST request to delete Bounty : {}", id);

    bountyService.delete(id);
    return ResponseEntity.noContent()
        .headers(HeaderUtil.createEntityDeletionAlert(
            applicationName, true, ENTITY_NAME, id.toString()))
        .build();
  }

  /**
   * {@code SEARCH  /_search/bounties?query=:query} : search for the bounty
   * corresponding to the query.
   *
   * @param query the query of the bounty search.
   * @return the result of the search.
   */
  @GetMapping("/_search/bounties")
  public List<BountyDTO> searchBounties(@RequestParam String query) {
    log.debug("REST request to search Bounty for query {}", query);
    return bountyService.search(query);
  }
}
