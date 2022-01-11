package com.algaworks.ecommerce.extension;

import org.junit.jupiter.api.extension.*;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerFactoryExtension implements BeforeAllCallback, AfterAllCallback, ParameterResolver {

    private EntityManagerFactory entityManagerFactory;

    @Override
    public void beforeAll(final ExtensionContext context) throws Exception {
        entityManagerFactory = Persistence.createEntityManagerFactory("Ecommerce-PU");
    }

    @Override
    public void afterAll(final ExtensionContext context) throws Exception {
        entityManagerFactory.close();
    }

    @Override
    public boolean supportsParameter(final ParameterContext parameterContext, final ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().isAnnotationPresent(EMFactory.class);
    }

    @Override
    public Object resolveParameter(final ParameterContext parameterContext, final ExtensionContext extensionContext) throws ParameterResolutionException {
        if (parameterContext.getParameter().isAnnotationPresent(EMFactory.class)){
            return entityManagerFactory;
        } else {
            return null;
        }
    }
}
