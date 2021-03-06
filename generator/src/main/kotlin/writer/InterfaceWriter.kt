package writer

import parse.AnalyzedJavaFile
import parse.AnalyzedJavaMethod
import java.io.File


fun writeInterface(outPath: String,
                   javaFile: AnalyzedJavaFile,
                   javaPackage: String,
                   javaClassName: String,
                   extends: String? = null,
                   additionalImports: String = "",
                   transform: ((String) -> String)? = null,
                   superClassPluginNames: List<String> = listOf(),
                   superClassDelegateName: String = "",
                   superClassInfputFile: AnalyzedJavaFile? = null,
                   addCodeToClass: String? = null) {

    val sb = StringBuilder("""
    |package com.pascalwelsch.compositeandroid.$javaPackage;
    |
    |import com.pascalwelsch.compositeandroid.core.*;
    |
    |${javaFile.imports}
    |
    |$additionalImports
    |
    |public interface $javaClassName ${extends?.let { "extends $extends " } ?: ""}{
    |
    |
    """.replaceIndentByMargin())

    for (method in javaFile.methods) with(method) {
        sb.appendln(toInterface())
        sb.appendln(toSuperInterface())
    }

    addCodeToClass?.let { sb.appendln(it) }

    sb.appendln("}")

    var output = sb.toString()
    if (transform != null) {
        output = transform(output)
    }

    val out = File("$outPath${javaPackage.replace('.', '/')}/$javaClassName.java")
    out.parentFile.mkdirs()
    out.printWriter().use { it.write(output) }
    System.out.println("wrote ${out.absolutePath}")
}


private fun AnalyzedJavaMethod.toInterface(): String {
    return """
            |
            |$returnType $name($rawParameters) $exceptions;
            """.replaceIndentByMargin("    ")
}

private fun AnalyzedJavaMethod.toSuperInterface(): String {
    return """
            |
            |$returnType super_$name($rawParameters) $exceptions;
            """.replaceIndentByMargin("    ")
}