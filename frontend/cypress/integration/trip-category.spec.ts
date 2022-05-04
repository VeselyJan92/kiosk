/// <reference types="cypress" />

import 'cypress-file-upload';

describe('Test TripCategory', () => {

    const name = "New Category - " + Math.floor(Math.random() * 1000);

    beforeEach(() => {

        cy.visit('/login')
        cy.viewport(1920, 1080)

        cy.get('button').click()
    })

    it('Add new TripCategory', () => {
        cy.get("#popup-edit-category").should("not.exist")

        cy.get("#add-new-trip-category").click()

        cy.get("#popup-edit-category").should("exist")

        cy.get("#popup-edit-category-name").type(name)

        cy.get("#popup-edit-category-submit").click()
        cy.contains(".categories", name).should("exist")
    })

    it('Delete TripCategory', () => {
        cy.contains(".category", name).should("exist").find(".edit").click()

        cy.get(".popup-content #popup-delete-category").click().wait(1000)

        cy.contains(".categories", name).should("not.exist")
    })

})
