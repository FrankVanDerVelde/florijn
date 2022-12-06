package com.hva.ewa.team2.backend.domain.models.project;

/**
 * An optional filter that applies to requesting projects.
 */
public enum ProjectFilter {

    /**
     * Retrieve all projects
     */
    ALL,
    /**
     * Retrieve only projects that have been marked as archived.
     */
    ARCHIVED,
    /**
     * Retrieve only projects that have not been archived.
     */
    UNARCHIVED

}
