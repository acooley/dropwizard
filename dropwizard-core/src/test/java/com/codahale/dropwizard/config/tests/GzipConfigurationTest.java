package com.codahale.dropwizard.config.tests;

import com.codahale.dropwizard.config.GzipConfiguration;
import com.codahale.dropwizard.json.ObjectMapperFactory;
import com.codahale.dropwizard.util.Size;
import com.google.common.collect.ImmutableSet;
import com.google.common.io.Resources;
import com.codahale.dropwizard.config.ConfigurationFactory;
import org.junit.Before;
import org.junit.Test;

import javax.validation.Validation;
import java.io.File;

import static org.fest.assertions.api.Assertions.assertThat;

public class GzipConfigurationTest {
    private GzipConfiguration gzip;

    @Before
    public void setUp() throws Exception {
        final ConfigurationFactory<GzipConfiguration> factory =
                new ConfigurationFactory<>(Validation.buildDefaultValidatorFactory()
                                                                      .getValidator(),
                                                            GzipConfiguration.class,
                                                            new ObjectMapperFactory().build(),
                                                            "dw");

        this.gzip = factory.build(new File(Resources.getResource("yaml/gzip.yml").toURI()));
    }

    @Test
    public void canBeEnabled() throws Exception {
        assertThat(gzip.isEnabled())
                .isFalse();
    }

    @Test
    public void hasAMinimumEntitySize() throws Exception {
        assertThat(gzip.getMinimumEntitySize())
                .isEqualTo(Size.kilobytes(12));
    }

    @Test
    public void hasABufferSize() throws Exception {
        assertThat(gzip.getBufferSize())
                .isEqualTo(Size.kilobytes(32));
    }

    @Test
    public void hasExcludedUserAgents() throws Exception {
        assertThat(gzip.getExcludedUserAgents())
                .isEqualTo(ImmutableSet.of("IE"));
    }

    @Test
    public void hasCompressedMimeTypes() throws Exception {
        assertThat(gzip.getCompressedMimeTypes())
                .isEqualTo(ImmutableSet.of("text/plain"));
    }
}
