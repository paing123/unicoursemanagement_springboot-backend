package edu.cpp.backend.config;
import edu.cpp.backend.entity.Course;
import edu.cpp.backend.entity.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

    // EntityManager manages persistent entity objects, such as create, delete, find entity objects.
    private EntityManager entityManager;
    @Autowired
    public MyDataRestConfig(EntityManager theEntityManager) {
        entityManager = theEntityManager;
    }

    @Override
    // public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
    // The second parameter is added due to method update
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        HttpMethod[] theUnsupportedActions = {HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE,
                HttpMethod.PATCH};
        // disable HTTP methods for Course: PUT, POST, DELETE and PATCH
        config.getExposureConfiguration()
                .forDomainType(Course.class)
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions));
        // disable HTTP methods for Department: PUT, POST, DELETE and PATCH
        config.getExposureConfiguration()
                .forDomainType(Department.class)
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions));
        // call an internal user-defined helper method
        exposeIds(config);
    }

    // expose entity ids
    private void exposeIds(RepositoryRestConfiguration config) {
        // get a list of all entity classes from the entity manager
        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();
        // create an ArrayList object of the entity types
        List<Class> entityClasses = new ArrayList<>();
        // get the entity types for the entities
        for (EntityType tempEntityType : entities) {
            entityClasses.add(tempEntityType.getJavaType());
        }
        // entityClasses.toArray(new Class[0]) returns a Class array containing all elements in entityClasses
        Class[] domainTypes = entityClasses.toArray(new Class[0]);
        // exposeIdsFor() sets the list of domain types for which we will expose the ID value as a normal property.
        config.exposeIdsFor(domainTypes);
    }
}