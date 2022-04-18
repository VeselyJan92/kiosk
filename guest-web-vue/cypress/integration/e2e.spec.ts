/// <reference types="cypress" />

// Welcome to Cypress!
//
// This spec file contains a variety of sample tests
// for a todo list app that are designed to demonstrate
// the power of writing tests in Cypress.
//
// To learn more about how Cypress works and
// what makes it such an awesome testing tool,
// please read our getting started guide:
// https://on.cypress.io/introduction-to-cypress


import 'cypress-file-upload';
import {randomInt} from "crypto";


describe('example to-do app', () => {

    beforeEach(() => {
        // Cypress starts out with a blank slate for each test
        // so we must tell it to visit our website with the `cy.visit()` command.
        // Since we want to visit the same URL at the start of all our tests,
        // we include it in our beforeEach function so that it runs before each test
        //.visit('https://example.cypress.io/todo')
        cy.visit('/login')
        cy.viewport(1920, 1080)

        cy.get('button').click()
    })


    it('Add new Trip Category', () => {
        cy.get("#popup-edit-category").should("not.exist")

        cy.get("#add-new-trip-category").click()

        cy.get("#popup-edit-category").should("exist")

        const name = "New Category - " + Math.floor(Math.random() * 1000);

        cy.get("#popup-edit-category-name").type(name)

        cy.get("#popup-edit-category-submit").click()
        cy.contains(".categories", name).should("exist")
    })


    it('Add new Trip', () => {
        cy.get("#add-new-trip").click()

        const title = "New Trip - " + Math.floor(Math.random() * 1000);

        cy.get("#popup-edit-trip-title").type(title)
        cy.get("#popup-edit-trip-text").click().type(title + " - text")

        cy.get("#popup-edit-trip-tags").click().type("Car, Family")
        cy.get(".multiselect").click().type("{enter}")


        cy.get("#popup-edit-trip-imgs").attachFile("img.jpg")

        cy.get("#popup-edit-trip-imgs").attachFile("img.jpg")

        cy.contains(".trip-banner h4", title).should("not.exist")

        cy.get("#popup-edit-trip-submit").click()

        cy.contains(".trip-banner h4", title).should("exist")

        cy.contains(".desktop-trips .trip-banner h4", title).click()


        cy.get("#trip-popup").should("exist")


        cy.contains("#trip-popup h3", title).should("exist")
    })


    it('Add new Info', () => {
        cy.get("#add-new-trip-info").click()

        const title = "Travel tip - " + Math.floor(Math.random() * 1000);

        cy.get("#popup-edit-info-title").type(title)
        cy.get("#popup-edit-info-title").click().type(title + " - text")

        cy.get("#popup-edit-info-submit").click()

        cy.contains(".travel-info .travel-info-banner h4", title).should("exist")

    })
})
