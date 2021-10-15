package com.algaworks.ecommerce.extension;

import org.junit.jupiter.api.extension.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerExtension implements BeforeEachCallback, BeforeAllCallback,
        AfterEachCallback, AfterAllCallback, ParameterResolver{

    private EntityManagerFactory entityManagerFactory;

    private EntityManager entityManager;

    @Override
    public void beforeAll(final ExtensionContext context) throws Exception {
        entityManagerFactory = Persistence.createEntityManagerFactory("Ecommerce-PU");
    }

    @Override
    public void beforeEach(final ExtensionContext context) {
        entityManager = entityManagerFactory.createEntityManager();
    }

    @Override
    public void afterEach(final ExtensionContext context) {
        entityManager.close();
    }

    @Override
    public void afterAll(final ExtensionContext context) {
        entityManagerFactory.close();
    }

    @Override
    public boolean supportsParameter(final ParameterContext parameterContext, final ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().isAnnotationPresent(EMFactory.class) ||
                parameterContext.getParameter().isAnnotationPresent(EManager.class);
    }

    @Override
    public Object resolveParameter(final ParameterContext parameterContext, final ExtensionContext extensionContext) throws ParameterResolutionException {
        if (parameterContext.getParameter().isAnnotationPresent(EMFactory.class)){
            return entityManagerFactory;
        } else if (parameterContext.getParameter().isAnnotationPresent(EManager.class)){
            return entityManager;
        } else{
            return null;
        }
    }
}
