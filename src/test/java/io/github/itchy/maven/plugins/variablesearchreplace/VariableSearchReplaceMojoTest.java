package io.github.itchy.maven.plugins.variablesearchreplace;

import org.apache.maven.it.Verifier;
import org.apache.maven.it.util.ResourceExtractor;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VariableSearchReplaceMojoTest {

    @Test
    void testMyPlugin() throws Exception {
        File testDir = ResourceExtractor.simpleExtractResources(getClass(), "my-test-maven-project");

        Verifier verifier = new Verifier(testDir.getAbsolutePath());
        verifier.deleteArtifact("io.github.1tchy", "variable-search-replace-plugin-test", "1.0-SNAPSHOT", "pom");

        verifier.executeGoal("initialize");

        verifier.verifyErrorFreeLog();
        assertEquals("I want to create a world where the environment does not need protection.", verifier.getEnvironmentVariables().get("world"));

        verifier.resetStreams();
    }
}