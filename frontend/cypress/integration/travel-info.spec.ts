/// <reference types="cypress" />

import 'cypress-file-upload';


describe('Test TravelInfo', () => {

    const title = "Travel tip - " + Math.floor(Math.random() * 1000);

    beforeEach(() => {
        cy.visit('/login')
        cy.viewport(1920, 1080)

        cy.get('button').click()
    })

    it('Add new TravelInfo', () => {
        cy.get("#add-new-trip-info").click()
        cy.get("#popup-edit-info-title").type(title)
        cy.get("#popup-edit-info-text").click().type(title + " - text")

        cy.get("#popup-edit-info-submit").click()
    })


    it('Delete TravelInfo', () => {

        cy.contains(".travel-info .travel-info-banner h4", title)
            .should("exist")
            .find(".edit")
            .click()


        cy.get(".popup-content #popup-edit-info-delete")
            .click()
            .wait(1000)

        cy.contains(".travel-info .travel-info-banner h4", title).should("not.exist")

    })
})
