@file:Suppress("UnnecessaryVariable")


import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.*
import com.google.devtools.ksp.symbol.Variance.*
import com.google.devtools.ksp.validate
import java.io.OutputStream
import kotlin.random.Random


class InterfaceProcessor(
    private val options: Map<String, String>,
    private val logger: KSPLogger,
    private val codeGenerator: CodeGenerator,
    private val platform: String,
) : SymbolProcessor {

    operator fun OutputStream.plusAssign(str: String) {
        this.write(str.toByteArray())
    }

    @OptIn(KspExperimental::class)
    override fun process(resolver: Resolver): List<KSAnnotated> {
        val symbols = resolver
            .getSymbolsWithAnnotation("cz.cvut.veselj57.dt.graphql.model.Build")
            .filterIsInstance<KSClassDeclaration>()

        // Exit from the processor in case nothing is annotated with @Function.
        if (!symbols.iterator().hasNext()) return emptyList()


        val file = codeGenerator.createNewFile(
            dependencies = Dependencies(false, *resolver.getAllFiles().toList().toTypedArray()),
            packageName = "cvut.veselj57.dt.graphql.model",
            fileName = "Generated"
        )


        symbols.forEach {
            // The generated file will be located at:


            it.accept(Visitor(file), Unit)


        }

        file.close()

        val unableToProcess = symbols.filterNot { it.validate() }.toList()
        return unableToProcess
    }

    inner class Visitor(private val file: OutputStream) : KSVisitorVoid() {



        override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: Unit) {
            if (classDeclaration.classKind != ClassKind.CLASS) {
                logger.error("Only interface can be annotated with @DataClass", classDeclaration)
                return
            }


            // Getting the value of the 'name' argument.
            val className =  classDeclaration.simpleName.asString()

            // Getting the list of member properties of the annotated interface.
            val properties: Sequence<KSPropertyDeclaration> = classDeclaration.getAllProperties()
                .filter { it.validate() }

            // Generating function signature.
            file += "\n"

            if(!properties.iterator().hasNext())
                throw Exception("Interface must have at least one field")


            file +="@kotlin.js.JsExport \n"
            file += "external interface ${className}{ \n"


            // Iterating through each property to translate them to function arguments.
            properties.forEach { prop ->
                visitPropertyDeclaration(prop, Unit)
            }

            file += "}\n"
        }

        override fun visitPropertyDeclaration(property: KSPropertyDeclaration, data: Unit) {
            // Generating argument name.
            val argumentName = property.simpleName.asString()


            file += "    val $argumentName: "

            // Generating argument type.
            val resolvedType: KSType = property.type.resolve()

            file += if (resolvedType.declaration.simpleName?.asString() == "List"){
                "Array"
            }else{
                resolvedType.declaration.simpleName?.asString()!!
            }


           // file += if (resolvedType.nullability == Nullability.NULLABLE) "?" else ""

            // Generating generic parameters if any
            val genericArguments: List<KSTypeArgument> = property.type.element?.typeArguments ?: emptyList()

            if (genericArguments.isNotEmpty()){
                if(genericArguments.size != 1)
                    throw Exception("Only model entities are allowed")
                else
                    file += "<" + genericArguments.first().type?.resolve()?.declaration?.simpleName?.asString() + ">"
            }

            file += if (resolvedType.nullability == Nullability.NULLABLE) "?" else ""

            file += "\n"

        }


    }
}