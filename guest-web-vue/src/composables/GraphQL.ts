import {GraphQLClient} from "graphql-request";
import {useKioskStore} from "@/stores/kiosk";
import {useUserStore} from "@/stores/user";







export const graphQLClient = new GraphQLClient(import.meta.env.VITE_SERVER_URL + "/graphql", {
    headers: {
        "Content-Type": "application/json; charset=utf-8",
    }
})


export function getGraphQLClient(){

    const store = useUserStore()

    const headers: any = {
        "Content-Type": "application/json; charset=utf-8",
    }

    const token = store.token


    console.log(token)

    if (token)
        headers["Authorization"] = "Bearer " + token

    return new GraphQLClient(import.meta.env.VITE_SERVER_URL + "/graphql", {headers})
}