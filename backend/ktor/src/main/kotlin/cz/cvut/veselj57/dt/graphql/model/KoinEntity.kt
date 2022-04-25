package cz.cvut.veselj57.dt.graphql.model

import com.expediagroup.graphql.generator.annotations.GraphQLIgnore
import org.koin.core.Koin
import org.koin.core.component.KoinComponent

@GraphQLIgnore
interface KoinEntity: KoinComponent {

    @GraphQLIgnore
    override fun getKoin(): Koin {
        return super.getKoin()
    }

}