/**
 * Copyright (C) 2012 Jean-Christophe Gay (contact@jeanchristophegay.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fr.jcgay.maven.plugin.buildplan;

import com.google.common.base.Strings;
import com.google.common.collect.Multimap;
import fr.jcgay.maven.plugin.buildplan.display.ListPluginTableDescriptor;
import fr.jcgay.maven.plugin.buildplan.display.MojoExecutionDisplay;
import fr.jcgay.maven.plugin.buildplan.display.TableDescriptor;
import org.apache.maven.plugin.MojoExecution;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.util.Collection;
import java.util.Map;

import static fr.jcgay.maven.plugin.buildplan.display.Output.lineSeparator;

/**
 * List plugin executions by plugin for the current project.
 */
@Mojo(name = "list-plugin",
      threadSafe = true,
      requiresProject = true)
public class ListPluginMojo extends AbstractLifecycleMojo {

    /** Display plugin executions only for the specified plugin. */
    @Parameter(property = "buildplan.plugin")
    private String plugin;

    public void executeInternal() throws MojoFailureException {

        Multimap<String,MojoExecution> plan = Groups.ByPlugin.of(calculateExecutionPlan().getMojoExecutions(), plugin);

        if (plan.isEmpty()) {
            getLog().warn("No plugin found with artifactId: " + plugin);
        } else {
            StringBuilder output = new StringBuilder();
            TableDescriptor descriptor = ListPluginTableDescriptor.of(plan.values(), defaultLifecycles);
            for (Map.Entry<String, Collection<MojoExecution>> executions : plan.asMap().entrySet()) {
                output.append(lineSeparator())
                        .append(pluginTitleLine(descriptor, executions.getKey()));
                for (MojoExecution execution : executions.getValue()) {
                    output.append(lineSeparator())
                            .append(line(descriptor.rowFormat(), execution));
                }
            }
            handleOutput(output.toString());
        }
    }

    private String line(String rowFormat, MojoExecution execution) {

        MojoExecutionDisplay display = new MojoExecutionDisplay(execution);

        return String.format(rowFormat, display.getPhase(),
                                        display.getExecutionId(),
                                        display.getGoal());
    }

    private String pluginTitleLine(TableDescriptor descriptor, String key) {
        return key + " " + Strings.repeat("-", descriptor.width() - key.length());
    }
}
