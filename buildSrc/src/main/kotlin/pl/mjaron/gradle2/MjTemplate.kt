/*
 * Copyright  2021  Michał Jaroń <m.jaron@protonmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the
 * following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY
 * KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 * CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT
 * OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package pl.mjaron.gradle2

import org.gradle.api.*
import org.gradle.api.plugins.JavaPluginConvention
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.create
import java.io.File
import java.nio.file.Paths

class TaskNameGenerator(private val project: Project) {

    fun generateName(base: String): String {
        var idx = 0
        while (true) {
            val candidate = if (idx == 0) "$base" else "$base-$idx"
            if (project.tasks.findByName(candidate) == null) {
                return candidate
            }
            ++idx
        }
    }
}

/**
 * Describes any single template task.
 */
abstract class AnyTemplate : DefaultTask() {

    @get:Input
    abstract val templateGroup: Property<AnyTemplateGroup>

    /**
     * Name of template engine used to fill template.
     * If not specified, default replacing engine will be used.
     */
    @get:Input
    abstract val engine: Property<String>

    @TaskAction
    fun doTemplate() {
        println("MJT:  doTemplate()")
    }
}

abstract class AnyTemplateGroup : DefaultTask() {

    /**
     * Source directory base of any child in this template group.
     */
    @get:Input
    abstract val src: Property<String>

    /**
     * Destination directory base of any child in this template group.
     */
    @get:Input
    abstract val dst: Property<String>

    /**
     * Defines name prefix of all templates related to this group.
     */
    @get:Input
    abstract val logicalGroupName: Property<String>

    /**
     * Required by implementation. Defines unique name of this group, without a name prefix.
     */
    @get:Input
    abstract val uniqueGroupName: Property<String>

    fun absoluteSrcBase(): String {
        return if (!src.isPresent) {
            project.projectDir.absolutePath
        } else if (File(src.get()).isAbsolute) {
            src.get()
        } else {
            Paths.get(project.projectDir.absolutePath, src.get()).toString()
        }
    }

    fun absoluteDstBase(): String {
        return if (!dst.isPresent) {
            project.buildDir.absolutePath
        } else if (File(dst.get()).isAbsolute) {
            dst.get()
        } else {
            Paths.get(project.buildDir.absolutePath, dst.get()).toString()
        }
    }

    /**
     * Creates given template type with name and group. Executes given action. This group depends on created template task.
     */
    inline fun <reified T : AnyTemplate> createTemplate(templateName: String, action: Action<T>): T {
        println("template processing: [${templateName}].")
        val registered = project.tasks.create<T>(
            MjTemplate.getOptions(project).generator.generateName(this.uniqueGroupName.get() + "-$templateName")
        ) {
            group = MjTemplate.getOptions(project).pluginGroup
            templateGroup.set(this@AnyTemplateGroup)
            action.execute(this)
        }
        this.dependsOn(registered)
        return registered
    }

    open fun template(templateName: String, action: Action<AnyTemplate>) {
        createTemplate(templateName, action)
    }

    open fun template(action: Action<AnyTemplate>) {
        createTemplate("template", action)
    }
}

abstract class JavaGroup : AnyTemplateGroup() {

    init {
        this.configure()
    }

    private fun configure() {
        this.src.set("src/main/java-templates")
        this.dst.set("generated/sourceTemplate/java")
    }

    override fun template(templateName: String, action: Action<AnyTemplate>) {
        createTemplate(templateName, action)
    }
}

abstract class MjTemplateExt {

    /**
     * For plugin internal use. Set while plugin invocation. Allows creating template tasks.
     */
    abstract val p: Property<Project>

    /**
     * For plugin internal use. Set while plugin invocation.
     */
    abstract val rootTask: Property<Task>

    /**
     * For plugin internal use. Set while plugin invocation.
     */
    abstract val pluginGroupName: Property<String>

    inline fun <reified T : AnyTemplateGroup> createTemplateGroup(groupName: String, action: Action<T>): T {
        val uniqueName = MjTemplate.getOptions(p.get()).generator.generateName(groupName)
        val registered = p.get().tasks.create<T>(
            uniqueName
        ) {
            group = MjTemplate.getOptions(p.get()).pluginGroup
            logicalGroupName.set(groupName)
            uniqueGroupName.set(uniqueName)
            action.execute(this)
        }
        rootTask.get().dependsOn(registered)
        return registered
    }

    /**
     * Create custom task template group.
     */
    fun custom(groupName: String, action: Action<AnyTemplateGroup>) {
        createTemplateGroup(groupName, action)
    }

    /**
     * Create custom task template group.
     */
    fun custom(action: Action<AnyTemplateGroup>) {
        val taskName: String = MjTemplate.getOptions(p.get()).generator.generateName("custom")
        createTemplateGroup(taskName, action)
    }

    fun java(groupName: String, action: Action<JavaGroup>) {
        val task = this.createTemplateGroup(groupName, action)

        // Source: https://stackoverflow.com/a/44408986/6835932
        val mainSourceSet = this.p.get().convention.getPlugin(JavaPluginConvention::class.java).sourceSets.getByName("main")
        mainSourceSet.java.setSrcDirs(listOf(mainSourceSet.java.srcDirs, task.absoluteDstBase()))
        println("MainSourceSet:" + mainSourceSet.java.sourceDirectories.asPath)
//        this.p.get().convention.getPlugin(JavaPluginConvention::class.java).sourceSets.create("java-templates").java {
//            srcDir(task.absoluteSrcBase())
//            exclude("*")
//        }
    }

    fun java(action: Action<JavaGroup>) {
        this.java("java", action)
    }
}

class MjTemplate : Plugin<Project> {

    data class Options(val generator: TaskNameGenerator, val pluginGroup: String)

    companion object {
        private val metadata = HashMap<Project, Options>()

        fun getOptions(project: Project): Options {
            return metadata[project]!!
        }
    }

    override fun apply(project: Project) {
        metadata[project] = Options(TaskNameGenerator(project), "mjt")
        val ext = project.extensions.create("mjt", MjTemplateExt::class.java)
        ext.p.set(project)
        ext.pluginGroupName.set("mjt")
        ext.rootTask.set(project.task(ext.pluginGroupName.get()).apply { group = ext.pluginGroupName.get() })

        println("MJT: Plugin applied.")
    }
}
