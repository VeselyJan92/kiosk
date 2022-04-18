import { mount } from '@cypress/vue'
import Popup from './admin/PopupEditActivity.vue'
import {createPinia, setActivePinia} from "pinia";
import {useHotelStore} from "@/stores/hotel";


describe('HelloWorld component', () => {
    /*beforeEach(async () => {

        setActivePinia(createPinia())

        const store = useHotelStore();
        store.setHotelId("624837e9e377490f7e93f3e5")
        await store.reload()
    })


    it('works', async () => {
        cy.viewport(1000, 1000) // Set viewport to 550px x 750px

        mount(Popup, {
            props:{
                tripId: "625310538b0c2c7bfed61704"
            }
        });

        cy.get("button[type=submit]").click()


        // now use standard Cypress commands
       // cy.contains('test').should('be.visible')
    })*/


    it('should asd', function () {
        cy.visit("/");
    });
})