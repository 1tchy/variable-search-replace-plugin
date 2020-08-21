package io.github.itchy.maven.plugins.variablesearchreplace;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.util.regex.PatternSyntaxException;

import static org.apache.maven.plugins.annotations.LifecyclePhase.INITIALIZE;

@Mojo(name = "replace", defaultPhase = INITIALIZE)
public class VariableSearchReplaceMojo extends AbstractMojo {

    @Parameter(property = "text", required = true)
    private String text;
    @Parameter(property = "search", required = true)
    private String search;
    @Parameter(property = "replacement")
    private String replacement;
    @Parameter(property = "variablename", required = true)
    private String variableName;
    @Parameter(readonly = true, defaultValue = "${session}")
    private MavenSession mavenSession;

    public void execute() throws MojoFailureException {
        String replacedText = getReplacedText();
        getLog().info("Setting variable: " + variableName + " = " + replacedText);
        mavenSession.getSystemProperties().setProperty(variableName, replacedText);
    }

    private String getReplacedText() throws MojoFailureException {
        String replacement = this.replacement == null ? "" : this.replacement;
        try {
            return text.replaceAll(search, replacement);
        } catch (PatternSyntaxException e) {
            throw new MojoFailureException("Pattern '" + search + "' is not a valid regex", e);
        }
    }
}
