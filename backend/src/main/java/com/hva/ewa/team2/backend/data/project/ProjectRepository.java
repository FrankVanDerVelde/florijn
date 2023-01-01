package com.hva.ewa.team2.backend.data.project;

import com.hva.ewa.team2.backend.domain.models.project.Project;
import com.hva.ewa.team2.backend.domain.models.project.ProjectFilter;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

public interface ProjectRepository extends CrudRepository<Project, Integer> {

    /**
     * Get all projects
     *
     * @return List of projects
     */
    @Query("SELECT p FROM Project p")
    ArrayList<Project> findAll();

    /**
     * Find all projects for a specific user.
     *
     * @param userId The user id.
     * @return A list of projects.
     */
    @Query("SELECT p FROM Project p INNER JOIN p.participants participants WHERE participants.specialist.id = :userId OR p.client.id = :userId GROUP BY p.id")
    ArrayList<Project> findAll(Integer userId);

    /**
     * Find all {@link Project}s that match the given {@link ProjectFilter}.
     *
     * @param filter The filter to apply to the search.
     * @return A list of {@link Project}s that match the given {@link ProjectFilter}.
     */
    @Query("""
            SELECT p FROM Project p
                WHERE p.archived = (CASE WHEN :#{#filter?.name()} = 'ARCHIVED' THEN true ELSE false END)""")
    ArrayList<Project> findAll(@Param("filter") ProjectFilter filter);


    /**
     * Find all {@link Project}s that match the given {@link ProjectFilter} for a specific user.
     *
     * @param filter The filter to apply to the search.
     * @param userId The id of the user to search for.
     * @return A list of {@link Project}s that match the given {@link ProjectFilter}.
     */
    @Query("""
            SELECT p FROM Project p
                LEFT JOIN p.participants participants
                WHERE (p.client.id = :userId OR participants.specialist.id = :userId)
                    AND p.archived = (CASE WHEN :#{#filter?.name()} = 'ARCHIVED' THEN true ELSE false END) 
                GROUP BY p.id""")
    ArrayList<Project> findAll(@Param("filter") ProjectFilter filter, @Param("userId") Integer userId);

    /**
     * Find all {@link Project}s that have a title or description that matches the given query.
     *
     * @param query The query to search for.
     * @return A list of {@link Project}s that match the given query.
     */
    @Query("""
            SELECT p FROM Project p
                WHERE p.title LIKE CONCAT('%',:query,'%')
                    OR p.description LIKE CONCAT('%',:query,'%')""")
    ArrayList<Project> findAllByQuery(@Param("query") String query);

    /**
     * Find all {@link Project}s that have a title or description that matches the given query for a specific user.
     *
     * @param query  The query to search for.
     * @param userId The id of the user to search for.
     * @return A list of {@link Project}s that match the given query.
     */
    @Query("""
            SELECT p FROM Project p
                LEFT JOIN p.participants participants
                WHERE (p.client.id = :userId OR participants.specialist.id = :userId)
                    AND p.title LIKE CONCAT('%',:query,'%')
                    OR p.description LIKE CONCAT('%',:query,'%')
                GROUP BY p.id""")
    ArrayList<Project> findAllByQuery(@Param("query") String query, @Param("userId") Integer userId);

    /**
     * Find all {@link Project}s that match the given {@link ProjectFilter} and have a title or description that matches the given query.
     *
     * @param filter The filter to apply to the search.
     * @param query  The query to search for.
     * @return A list of {@link Project}s that match the given {@link ProjectFilter} and query.
     */
    @Query("""
            SELECT p FROM Project p
                WHERE p.archived = (case WHEN :#{#filter?.name()} = 'ARCHIVED' THEN TRUE ELSE FALSE END)
                    AND (p.title LIKE CONCAT('%',:query,'%') OR p.description LIKE CONCAT('%',:query,'%'))""")
    ArrayList<Project> findAllByQuery(@Param("filter") ProjectFilter filter, @Param("query") String query);

    /**
     * Find all {@link Project}s that match the given {@link ProjectFilter} and have a title or description that matches the given query for a specific user.
     *
     * @param filter The filter to apply to the search.
     * @param query  The query to search for.
     * @param userId The id of the user to search for.
     * @return A list of {@link Project}s that match the given {@link ProjectFilter} and query.
     */
    @Query("""
            SELECT p FROM Project p
                LEFT JOIN p.participants participants
                WHERE (p.client.id = :userId OR participants.specialist.id = :userId)
                    AND p.archived = (case WHEN :#{#filter?.name()} = 'ARCHIVED' THEN TRUE ELSE FALSE END)
                    AND (p.title LIKE CONCAT('%',:query,'%') OR p.description LIKE CONCAT('%',:query,'%'))
                GROUP BY p.id""")
    ArrayList<Project> findAllByQuery(@Param("filter") ProjectFilter filter, @Param("query") String query, @Param("userId") Integer userId);

    /**
     * Get the total number of {@link Project}s.
     *
     * @return The total number of {@link Project}s.
     */
    @Query("SELECT COUNT(p) FROM Project p")
    int getCount();

    /**
     * Get the total number of projects for a specific user.
     *
     * @param userId The id of the user to search for.
     * @return The total number of projects for a specific user.
     */
    @Query("""
            SELECT count(DISTINCT p) from Project p
                LEFT JOIN p.participants participants
                WHERE p.client.id = :userId OR participants.specialist.id = :userId""")
    int getCount(Integer userId);

}
