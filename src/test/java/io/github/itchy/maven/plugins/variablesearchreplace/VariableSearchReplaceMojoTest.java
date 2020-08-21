package io.github.itchy.maven.plugins.variablesearchreplace;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.apache.maven.plugin.testing.stubs.MavenProjectStub;
import org.junit.jupiter.api.Test;

class VariableSearchReplaceMojoTest extends AbstractMojoTestCase {

    private static final String VARIABLE_NAME = "exampleVariableName";

    @Test
    public void testWhenNoReplacementIsRequired() throws Exception {
        VariableSearchReplaceMojo sut = createMojo("perfect text", "something", "anything");
        sut.execute();
        assertVariableIs(sut, "perfect text");
    }

    @Test
    public void testWhenPartialReplacementIsRequired() throws Exception {
        VariableSearchReplaceMojo sut = createMojo("example text", "example", "real");
        sut.execute();
        assertVariableIs(sut, "real text");
    }

    @Test
    public void testWhenSearchingUsingRegularExpression() throws Exception {
        VariableSearchReplaceMojo sut = createMojo("almost real text", ".+ (real)", "$1");
        sut.execute();
        assertVariableIs(sut, "real text");
    }

    @Test
    public void testWhenPartShouldBeRemoved() throws Exception {
        VariableSearchReplaceMojo sut = createMojo("example text", "example\\s*", null);
        sut.execute();
        assertVariableIs(sut, "text");
    }

    private VariableSearchReplaceMojo createMojo(String text, String search, String replacement) throws Exception {
        VariableSearchReplaceMojo sut = new VariableSearchReplaceMojo();
        setVariableValueToObject(sut, "text", text);
        setVariableValueToObject(sut, "search", search);
        setVariableValueToObject(sut, "replacement", replacement);
        setVariableValueToObject(sut, "variableName", VARIABLE_NAME);
        setVariableValueToObject(sut, "mavenSession", newMavenSession(new MavenProjectStub()));
        return sut;
    }

    private void assertVariableIs(VariableSearchReplaceMojo sut, String expected) throws IllegalAccessException {
        MavenSession mavenSession = (MavenSession) getVariableValueFromObject(sut, "mavenSession");
        assertEquals(expected, mavenSession.getSystemProperties().getProperty(VARIABLE_NAME));
    }
}