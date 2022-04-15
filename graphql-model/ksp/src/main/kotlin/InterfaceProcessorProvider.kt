
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider


class InterfaceProcessorProvider : SymbolProcessorProvider {

    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {

        return InterfaceProcessor(
            options = environment.options,
            logger = environment.logger,
            codeGenerator = environment.codeGenerator,
            platform = environment.platforms[0].platformName
        )
    }
}