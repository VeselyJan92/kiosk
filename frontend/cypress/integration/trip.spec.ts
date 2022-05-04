/// <reference types="cypress" />

import 'cypress-file-upload';


describe('Test Trip', () => {

    const title = "New Trip - " + Math.floor(Math.random() * 1000);

    beforeEach(() => {
        cy.visit('/login')
        cy.viewport(1920, 1080)

        cy.get('button').click()
    })

    it('Add new Trip', () => {
        cy.get("#add-new-trip").click()

        cy.get("#popup-edit-trip-title").type(title)
        cy.get("#popup-edit-trip-text").click().type(title + " - text")

        cy.get("#popup-edit-trip-tags").click().type("Car, Family")
        cy.get(".multiselect").click().type("{enter}")


        cy.get("#popup-edit-trip-imgs").attachFile("img.jpg")

        cy.get("#popup-edit-trip-imgs").attachFile("img.jpg")

        cy.contains(".trip-banner h4", title).should("not.exist")

        cy.get("#popup-edit-trip-submit").click()

        cy.contains(".dynamic-trips .trip-banner h4", title)
            .should("exist")
            .wait(500)
            .scrollIntoView()
            .click()


        cy.get("#trip-popup").should("exist")


        cy.contains("#trip-popup h3", title).should("exist")
    })

    it('Delete Trip', () => {

        cy.contains(".dynamic-trips .trip-banner h4", title).should("exist").parent().find(".edit").click()

        cy.get(".popup-content #popup-delete-trip")
            .click()
            .wait(1000)

        cy.contains(".dynamic-trips .trip-banner h4", title)
            .should("not.exist")
    })

})
