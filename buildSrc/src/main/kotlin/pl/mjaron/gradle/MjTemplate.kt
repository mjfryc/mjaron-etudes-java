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

package pl.mjaron.gradle

import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project

open class MjTemplateExt {

    val taskPrefix = "mjTemplate"

    val jobSets = ArrayList<TemplateJobSet>()

    /**
     * Creates custom job set which is not configured for any programming language.
     */
    fun custom(action: Action<TemplateJobSet>) {
        println("MjTemplateExt::custom()")
        val js = TemplateJobSet()
        action.execute(js)
        jobSets.add(js)
    }

    fun java(action: Action<TemplateJobSet>) {
        println("MjTemplateExt::java()")
        val js = JavaJobSet()
        action.execute(js)
        jobSets.add(js)
    }
}



class MjTemplate : Plugin<Project> {

    companion object {
        const val REPLACE = "replace" // Replace template engine.
    }

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
        this.addEngine(REPLACE, ReplacingTemplateEngine())
    }

    override fun apply(project: Project) {
        // Add the 'greeting' extension object
        val ext = project.extensions.create("mjTemplate", MjTemplateExt::class.java)

        val pluginTask = project.task(ext.taskPrefix)
        val jobRunner = JobRunner(project, ext.taskPrefix, engines)
        println("Job sets count: ${ext.jobSets.size}")
        for (jobSet in ext.jobSets) {
            val jobSetTask = jobSet.configure(jobRunner)
            pluginTask.dependsOn(jobSetTask)
        }

    }

}

