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

import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginConvention
import org.gradle.api.provider.ListProperty
import org.gradle.api.tasks.compile.JavaCompile
import java.io.File
import java.nio.file.Paths

/**
 * Contains names of preconfigured template engines.
 */
object BuildInEngines {
    const val REPLACE = "replace" // Just replaces key to value
}

/**
 * Describes single file template processing.
 */
@Suppress("unused", "MemberVisibilityCanBePrivate")
class Job(val jobSet: JobSet, var source: File, var destination: File) {
    var engineName = BuildInEngines.REPLACE
    val params = HashMap<String, String>()
    var sourceText = ""
    var destinationText = ""

    @Suppress("unused")
    fun engine(what: String): Job {
        engineName = what
        return this
    }

    /**
     * Sets source file location related to job set's base directory.
     */
    fun src(what: String): Job {
        source = File(what)
        return this
    }

    /**
     * Sets destination file location related to job set's base directory.
     */
    fun dst(what: String): Job {
        destination = File(what)
        return this
    }

    /**
     * Sets parameter of template.
     */
    fun param(what: String, value: String): Job {
        params[what] = value
        return this
    }

    /**
     * Sets parameter of template.
     */
    fun <T> param(what: String, value: T): Job {
        params[what] = value.toString()
        return this
    }
    /**
     * Human-readable description of this job.
     */
    override fun toString(): String {
        return "${source}->${destination}:${params}"
    }

    fun getAbsoluteSrc(project: Project): String {
        if (this.source.isAbsolute) {
            return this.source.absolutePath
        }
        return Paths.get(this.jobSet.absoluteSrcBase(project), this.source.path).toString()
    }

    fun getAbsoluteDst(project: Project): String {
        if (this.destination.isAbsolute) {
            return this.destination.absolutePath
        }
        return Paths.get(this.jobSet.absoluteDstBase(project), this.destination.path).toString()
    }
}

/**
 * Represents jobs related to single base source and base destination directories.
 * Creates job entries.
 */
@Suppress("MemberVisibilityCanBePrivate")
open class JobSet {

    /**
     * Base directory of each job source file.
     */
    var srcBase: String? = null

    /**
     * Base directory of each job destination file.
     */
    var dstBase: String? = null

    /**
     * Creates a new job.
     * If destination is empty, it means the same destination as source.
     * Note that if JobSet  has different source and destination bases, it will cause different paths, so source will not be overridden.
     */
    fun job(src: String, dst: String): Job {
        val j = Job(this, File(src), File(dst))
        jobs.add(j)
        return j
    }

    fun job(src: String): Job {
        val j = Job(this, File(src), File(src))
        jobs.add(j)
        return j
    }

    fun absoluteSrcBase(project: Project): String {
        if (srcBase == null) {
            return project.projectDir.absolutePath
        } else if (File(srcBase!!).isAbsolute) {
            return srcBase!!
        } else {
            return Paths.get(project.projectDir.absolutePath, srcBase!!).toString()
        }
    }

    fun absoluteDstBase(project: Project): String {
        if (dstBase == null) {
            return project.buildDir.absolutePath
        } else if (File(dstBase!!).isAbsolute) {
            return dstBase!!
        } else {
            return Paths.get(project.buildDir.absolutePath, dstBase!!).toString()
        }
    }

    open fun configure(project: Project) {
    }

    val jobs = ArrayList<Job>()
}

abstract class MjaronSourceTemplateExtension {
    abstract val jobSets: ListProperty<JobSet>

    /**
     * Creates custom job set which is not configured for any programming language.
     */
    fun custom(closure: Action<JobSet>) {
        val js = JobSet()
        closure.execute(js)
        jobSets.add(js)
    }

    fun java(action: Action<JobSet>) {
        val js = object : JobSet() {
            override fun configure(project: Project) {

                this.srcBase = "src/main/java-templates"
                this.dstBase = "generated/sourceTemplate/java"

                // Source: https://stackoverflow.com/a/44408986/6835932
                val mainSourceSet =
                    project.convention.getPlugin(JavaPluginConvention::class.java).sourceSets.getByName("main")
                mainSourceSet.java.setSrcDirs(listOf(mainSourceSet.java.srcDirs, this.absoluteDstBase(project)))
                println("MainSourceSet:" + mainSourceSet.java.sourceDirectories.asPath)

                val thisTask = project.task("java") {

                }

            }
        }
        action.execute(js)
        jobSets.add(js)
    }
}

/**
 * Defines operations of each template engine.
 * Each template should read job.sourceText and set job.destinationText depending on job.params.
 */
interface ITemplateEngine {

    /**
     * Each template should read job.sourceText and set job.destinationText depending on job.params.
     */
    fun process(job: Job)
}

/**
 * Simplest template engine. Just replaces params with values.
 */
class ReplacingTemplateEngine : ITemplateEngine {
    override fun process(job: Job) {
        var text = job.sourceText
        for (param in job.params) {
            text = text.replace(param.key, param.value)
        }
        job.destinationText = text
    }
}

class MjaronSourceTemplate : Plugin<Project> {

    /**
     * List of all available engines. It contains predefined engines and user engines added with addEngine() function.
     */
    private val engines = HashMap<String, ITemplateEngine>()

    /**
     * User can write custom template and add it here.
     */
    @Suppress("MemberVisibilityCanBePrivate")
    fun addEngine(name: String, engine: ITemplateEngine) {
        engines[name] = engine
    }

    init {
        // Let's add predefined engines.
        this.addEngine(BuildInEngines.REPLACE, ReplacingTemplateEngine())
    }

    override fun apply(project: Project) {
        // Add the 'greeting' extension object
        val extension = project.extensions.create("sourceTemplate", MjaronSourceTemplateExtension::class.java)

        // Add a task that uses configuration from the extension object
        val pluginTask = project.task("mjaronSourceTemplate") {
            doLast {
                for (jobSet in extension.jobSets.get()) {
                    jobSet.configure(project)
                    println("Processing a job set.")
                    for (job in jobSet.jobs) {
                        doJob(job, project)
                    }
                }

            }
        }
//        project.getTasksByName("buildSrc", false).forEach()  {
//            it.dependsOn(pluginTask)
//        }
        project.tasks.withType(JavaCompile::class.java).forEach { compileTask ->
            compileTask.dependsOn.add(pluginTask)
        }
    }

    private fun preJob(job: Job, project: Project) {
        val sourcePath = job.getAbsoluteSrc(project)
        println("Job source path: $sourcePath")
        job.sourceText = File(sourcePath).readText()
    }

    private fun postJob(job: Job, project: Project) {
        val destinationPath = job.getAbsoluteDst(project)
        val destinationFile = File(destinationPath)
        println("Job destination path: $destinationPath")
        destinationFile.parentFile.mkdirs()
        destinationFile.writeText(job.destinationText)
    }

    private fun doJob(job: Job, project: Project) {
        preJob(job, project)
        val engine: ITemplateEngine =
            engines[job.engineName] ?: throw Exception("No such template engine: [${job.engineName}].")
        engine.process(job)
        postJob(job, project)
    }
}

