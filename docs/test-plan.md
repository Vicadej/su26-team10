# Test Plan — MealPrep Match

**Project Name:** MealPrep Match
**Version:** Milestone 3
**Date:** 2026-07-22
**Purpose:** This document defines simple scenarios used to demonstrate that MealPrep Match's customer-facing features work as intended. Provider-side testing is out of scope for this milestone since the provider UI hasn't been converted to server-rendered views yet.

## Actors

**Customer C:** An end user who registers an account, browses and filters recipes, subscribes/unsubscribes, manages their profile, and leaves reviews. "C1" refers to an example customer used to walk through each scenario.

**Service S:** A recipe a customer discovers, subscribes to, and reviews.

## Use Cases

### 1. Customer: US-CUST-001 — Register & manage profile
Customer C1 logs in for the first time and creates a profile.
C1 edits their profile to add preferences.
C1 exits.

**Expected Outcome:** Account is created, password is never shown in plain text, and profile edits are saved.

### 2. Customer: US-CUST-002 — Browse & filter services
Customer C1 opens their dashboard and browses all available recipes.
C1 clicks a tag (e.g. "Vegan") to filter the list.
C1 clicks "All" to see everything again.

**Expected Outcome:** Only recipes with the matching tag are shown while filtered, filtering isn't broken by upper/lower case tags, and "All" restores the full list.

### 3. Customer: US-CUST-003 — Subscribe to a service
Customer C1 finds a recipe they like.
C1 clicks "Subscribe".

**Expected Outcome:** The button changes to "Unsubscribe" and the recipe now shows up on C1's Subscriptions page.

### 4. Customer: US-CUST-004 — Unsubscribe from a service
Customer C1 opens their Subscriptions page.
C1 clicks "Unsubscribe" on a recipe they no longer want.

**Expected Outcome:** The recipe disappears from the Subscriptions page and goes back to showing "Subscribe" on the dashboard.

### 5. Customer: US-CUST-005 — Write & edit a review
Customer C1 opens the Reviews page for a recipe they're subscribed to.
C1 submits a rating and comment.
C1 comes back later and changes the rating/comment.

**Expected Outcome:** The first submission creates the review; the second submission updates it instead of creating a duplicate. The recipe's average rating updates too.

## Cross-Cutting Test Scenarios (Non-Functional Requirements)

### Performance Requirements

**Scenario P1: Discover page response time < 1.5 seconds**
- **Setup:** Server under typical load, 10+ recipes available.
- **Steps:** Load the dashboard 10 times and time each load.
- **Expected Outcome:** 95% of loads finish in ≤ 1.5 seconds.

**Scenario P2: Subscribe/unsubscribe response time < 1 second**
- **Setup:** Customer logged in and viewing the dashboard.
- **Steps:** Click Subscribe, then Unsubscribe, and time each action.
- **Expected Outcome:** 95% of actions finish in ≤ 1 second.

### Security & Privacy Requirements

**Scenario S1: Passwords are never stored in plain text**
- **Setup:** New customer registers an account.
- **Steps:** Check the database record for that customer.
- **Expected Outcome:** Only a hashed password is stored — never the plain text password.

**Scenario S2: A customer can't view another customer's private data**
- **Setup:** Two customers, C1 and C2, are registered.
- **Steps:** While logged in as C1, try to open C2's profile or subscriptions page directly.
- **Expected Outcome:** Access is denied — C1 cannot see C2's data.

### Usability Requirements

**Scenario U1: Filtering feels instant**
- **Setup:** Dashboard loaded with recipes across several tags.
- **Steps:** Click through a few tag filters, then click "All".
- **Expected Outcome:** The page updates immediately with no reload; a "no results" message only shows when nothing matches.

**Scenario U2: Failed login/registration shows a clear error**
- **Setup:** Existing customer account.
- **Steps:** Try logging in with the wrong password; try registering with an email that's already used.
- **Expected Outcome:** A plain, readable error message is shown each time — no stack traces or crashes.
