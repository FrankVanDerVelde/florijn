package com.hva.ewa.team2.backend.data.project;

import com.hva.ewa.team2.backend.domain.models.project.Project;
import com.hva.ewa.team2.backend.domain.models.project.ProjectFilter;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface ProjectRepository extends CrudRepository<Project, Integer> {

    /**
     * Find all {@link Project}s that match the given {@link ProjectFilter}.
     *
     * @param filter The filter to apply to the search.
     * @return A list of {@link Project}s that match the given {@link ProjectFilter}.
     */
    @Query("SELECT p FROM Project p WHERE p.archived = (CASE WHEN :filter = 'ARCHIVED' THEN true ELSE false END)")
    ArrayList<Project> findAll(@Param("filter") ProjectFilter filter);

    /**
     * Find all {@link Project}s that have a title or description that matches the given query.
     *
     * @param query The query to search for.
     * @return A list of {@link Project}s that match the given query.
     */
    @Query("SELECT p FROM Project p WHERE p.title LIKE CONCAT('%',:query,'%') OR p.description LIKE CONCAT('%',:query,'%')")
    ArrayList<Project> findAll(@Param("query") String query);

    /**
     * Find all {@link Project}s that match the given {@link ProjectFilter} and have a title or description that matches the given query.
     *
     * @param filter The filter to apply to the search.
     * @param query  The query to search for.
     * @return A list of {@link Project}s that match the given {@link ProjectFilter} and query.
     */
    @Query("SELECT p FROM Project p WHERE p.archived = (case WHEN :filter = 'ARCHIVED' THEN TRUE ELSE FALSE END) AND (p.title LIKE CONCAT('%',:query,'%') OR p.description LIKE CONCAT('%',:query,'%'))")
    ArrayList<Project> findAll(@Param("filter") ProjectFilter filter, @Param("query") String query);

}
