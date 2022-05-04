/// <reference types="cypress" />

import 'cypress-file-upload';


describe('General settings', () => {

    const title = "Travel tip - " + Math.floor(Math.random() * 1000);

    beforeEach(() => {
        cy.visit('/login')
        cy.viewport(1920, 1080)

        cy.get('button').click()
    })

    it('Edit hotel settings', () => {


        const hotelName = "Hotel Name"
        const hotelEmail = "example@example.com"
        const hotelPhone = "123456789"
        const hotelWebsite = "example.com"

        cy.get("#hotel-settings").click()

        cy.get(".popup-content #popuo-hotel-settings-name").clear().type(hotelName)
        cy.get(".popup-content #popuo-hotel-settings-email").clear().type(hotelEmail)
        cy.get(".popup-content #popuo-hotel-settings-phone").clear().type(hotelPhone)
        cy.get(".popup-content #popuo-hotel-settings-website").clear().type(hotelWebsite)


        cy.get(".popup-content button[type=submit]").click().wait(1000)

        cy.get("#hotel-name").contains(hotelName)
        cy.get("#hotel-email .text").contains(hotelEmail)
        cy.get("#hotel-phone .text").contains(hotelPhone)

        cy.get("#hotel-website").invoke('attr', 'href')
            .should('eq', hotelWebsite)

    })

})
