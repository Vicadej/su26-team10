
# Requirements – Starter Template

**Project Name:** MealPrep Match \
**Team:** Cameron - Provider, Victor - Customer \
**Course:** CSC 340\
**Version:** 1.0\
**Date:** 2026-06-25

---

## 1. Overview
**Vision.** One or two sentences: This is an app designed to help people who want healthy, structured meal plans. It can be used for people who want to subscribe to mealkits and those who just want extra recipes to try.

**Glossary** Terms used in the project
- **Term 1:** description.
- **Term 2:** description

**Primary Users / Roles.**
- **Customer (e.g., Student/Patient/Pet Owner/etc. )** — 1 line goal statement.
- **Provider (e.g., Teacher/Doctor/Pet Sitter/etc. )** — Provide customers with recipes, mealplans and mealkits.
- **SysAdmin (optional)** — 1 line goal statement.

**Scope (this semester).**
- <capability 1>
- <capability 2>
- <capability 3>

**Out of scope (deferred).**
- <deferred 1>
- <deferred 2>

> This document is **requirements‑level** and solution‑neutral; design decisions (UI layouts, API endpoints, schemas) are documented separately.

---

## 2. Functional Requirements (User Stories)
Write each story as: **As a `<role>`, I want `<capability>`, so that `<benefit>`.** Each story includes at least one **Given/When/Then** scenario.

### 2.1 Customer Stories
- **US‑1 — Create Customer Profile**  
  _Story:_ As a customer, I want to create a profile with my dietary preferences and goals, so that I can be shown services that match my needs.  
  _Acceptance:_
  ```gherkin
  Scenario: Customer registers and sets preferences
    Given I am on the registration page and select the Customer role
    When  I fill in my name, email, password, dietary preferences, and health goals
    Then  my profile is saved and I am directed to the browse screen filtered by my preferences
  ```

- **US‑2 — Browse and Filter Services**  
  _Story:_ As a customer, I want to filter available recipes, mealkits, and mealprep guides by preference tags, so that I only see content relevant to my diet.  
  _Acceptance:_
  ```gherkin
  Scenario: Customer filters by tag
    Given I am on the browse screen and services exist with tags (vegan, carnivore, etc.)
    When  I select the "vegan" filter tag
    Then  only services tagged as vegan are displayed
  ```

- **US‑3 — Subscribe to a Service**  
  _Story:_ As a customer, I want to select a provider and subscribe to one of their meal plans, so that I can access their paid content.  
  _Acceptance:_
  ```gherkin
  Scenario: Customer subscribes to a meal plan
    Given I am viewing a provider's paid mealkit and I have an account
    When  I click Subscribe and complete the payment flow
    Then  the mealkit is added to my active subscriptions and I can access its full content
  ```

- **US‑4 — Write a Review**  
  _Story:_ As a customer, I want to submit a written review for a service I have subscribed to, so that I can share feedback with other customers and the provider.  
  _Acceptance:_
  ```gherkin
  Scenario: Customer submits a review
    Given I have an active or past subscription to a service
    When  I navigate to that service and submit a review with written feedback
    Then  the review is posted publicly on the service page and the provider is notified
  ```

### 2.2 Provider Stories
- **US-20 — Create Provider Profile**  
  _Story:_ As a provider, I want to register and tag my profile with the types of content I offer, so that customers with matching preferences can find me.  
  _Acceptance:_
  ```gherkin
  Scenario: Provider registers and adds tags
    Given I am on the registration page and select the Provider role
    When  I fill in my profile details and select tags (e.g., vegan, mealprep)
    Then  my profile is submitted for verification and my content is queued until approved
  ```

- **US-21 — Publish a Service**  
  _Story:_ As a provider, I want to publish a recipe, mealkit, or mealprep guide with pricing, so that customers can discover and subscribe to my content.  
  _Acceptance:_
  ```gherkin
  Scenario: Provider publishes a mealkit
    Given I am a verified provider on my dashboard
    When  I create a new Mealkit with a title, ingredient list, steps, and set a price
    Then  the mealkit is published and visible to customers on the browse screen
  ```

- **US-22 — View Service Statistics**  
  _Story:_ As a provider, I want to see active subscriber counts, overall ratings, and views-to-saves ratios for my services, so that I can understand how my content is performing.  
  _Acceptance:_
  ```gherkin
  Scenario: Provider views dashboard stats
    Given I have at least one published service with customer interactions
    When  I open my provider dashboard
    Then  I see active subscriber count, average rating, and VTS ratio for each service
  ```

- **US-23 — Reply to a Review**  
  _Story:_ As a provider, I want to reply to customer reviews on my services, so that I can address feedback and engage with my subscribers.  
  _Acceptance:_
  ```gherkin
  Scenario: Provider replies to a review
    Given a customer has posted a review on one of my services
    When  I navigate to that review and submit a reply
    Then  my reply is displayed beneath the customer's review and the customer is notified
  ```

### 2.3 SysAdmin Stories
- **US‑30 — <short title>**  
  _Story:_ As a sysadmin, I want … so that …  
  _Acceptance:_
  ```gherkin
  Scenario: <happy path>
    Given <preconditions>
    When  <action>
    Then  <observable outcome>
  ```

- **US‑31 — <short title>**  
  _Story:_ As a sysadmin, I want … so that …  
  _Acceptance:_
  ```gherkin
  Scenario: <happy path>
    Given <preconditions>
    When  <action>
    Then  <observable outcome>
  ```

---

## 3. Non‑Functional Requirements (make them measurable)
- **Performance:** description 
- **Availability/Reliability:** description
- **Security/Privacy:** description
- **Usability:** description

---

## 4. Assumptions, Constraints, and Policies
- list any rules, policies, assumptions, etc.

---

## 5. Milestones (course‑aligned)
- **M1 Requirements** — this file + stories opened as issues. 
- **M2 High‑fidelity prototype** — core customer/provider flows fully interactive. 
- **M3 Design** — architecture, schema, API outline. 
- **M4 Backend API** — key endpoints + tests. 
- **M5 Increment** — ≥2 use cases end‑to‑end. 
- **M6 Final** — complete system & documentation. 

---

## 6. Change Management
- Stories are living artifacts; changes are tracked via repository issues and linked pull requests.  
- Major changes should update this SRS.